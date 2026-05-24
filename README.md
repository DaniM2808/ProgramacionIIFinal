# Gestión de Eventos - Programación II

## Integrantes del Grupo
* Daniel Felipe Montes Villán
* John Edward Medina Sifuentes
* Luisa María Gómez Gómez

## Descripción del Proyecto
Este proyecto es una aplicación desarrollada en Java y JavaFX que permite la gestión completa de eventos. Incluye funcionalidades para usuarios clientes y administradores, permitiendo registro e inicio de sesión seguro, visualización de eventos por ciudad y tipo, compra de tiquetes por zonas y localidades (sillas), y un carrito de compras. El sistema cuenta con control de accesos para administradores y envía notificaciones sobre nuevos eventos, aplicando múltiples patrones de diseño para resolver problemas comunes de software en la arquitectura MVC.

## Instrucciones de Compilación y Ejecución
Este proyecto utiliza **Maven** para la gestión de dependencias y el ciclo de vida de compilación.

**Requisitos previos:**
* JDK 17 o superior.
* Maven instalado (o usar el wrapper `mvnw` incluido en el proyecto).

**Desde la terminal:**
1. Navegar a la raíz del proyecto (donde se encuentra el archivo `pom.xml`).
2. Compilar el proyecto ejecutando:
   ```bash
   ./mvnw clean install
   ```
   *(En Windows usar `mvnw.cmd clean install`)*
3. Ejecutar la aplicación JavaFX mediante:
   ```bash
   ./mvnw javafx:run
   ```

**Desde un IDE (IntelliJ IDEA / Eclipse):**
1. Abrir el directorio del proyecto como un proyecto de Maven.
2. Sincronizar las dependencias del `pom.xml`.
3. Buscar la clase principal `Launcher` o `Application` ubicada en `src/main/java/co/edu/uniquindio/manejoEventos`.
4. Ejecutar el método `main` en Launcher.java.

---

## Patrones de Diseño Implementados

### 1. Singleton
* **Requisito que resuelve:** Proveer un único punto de acceso global al estado de la aplicación.
* **Problema:** Múltiples controladores y vistas necesitan acceder a la misma lista de usuarios, eventos y mantener el estado de sesión del usuario actual. Instanciar el gestor varias veces crearía inconsistencias y pérdida de datos.
* **Propósito:** Garantizar que la clase tenga una única instancia y proporcionar un punto de acceso global a ella.
* **Solución:** La clase `EventManager` define un constructor privado y expone la instancia estática única a través del método `getInstance()`, asegurando que toda la aplicación manipule las mismas colecciones en memoria.

### 2. Builder
* **Requisito que resuelve:** Creación de objetos complejos paso a paso.
* **Problema:** La clase `Ticket` (y otras similares) tiene múltiples atributos (ID, costo, evento, zona, silla, estado, compra), lo que requeriría constructores con largas listas de parámetros (telescopic constructors), propensos a errores.
* **Propósito:** Separar la construcción de un objeto complejo de su representación, permitiendo construir objetos paso a paso.
* **Solución:** Se implementó la anotación `@Builder` de Lombok en la clase `Ticket` (y se simula en la estructura). Esto permite crear tickets indicando solo los atributos necesarios en cualquier orden, de forma legible: `Ticket.builder().theEvent(e).theZone(z).build()`.

### 3. Prototype
* **Requisito que resuelve:** Creación de nuevos objetos clonando un objeto existente.
* **Problema:** Al gestionar el carrito de compras o duplicar pedidos recurrentes, instanciar un objeto de compra desde cero puede ser costoso computacionalmente o requerir repetir una extensa inicialización de propiedades.
* **Propósito:** Especificar los tipos de objetos a crear a través de una instancia prototípica y crear nuevos objetos copiando este prototipo.
* **Solución:** La clase `Purchase` implementa la interfaz `PurchaseClone`. Provee el método `cloneObject()` que retorna una nueva instancia de compra copiando los valores clave sin acoplarse al resto del sistema.

### 4. Decorator
* **Requisito que resuelve:** Añadir funcionalidades o servicios extra a la compra de forma dinámica.
* **Problema:** Los usuarios deben poder adquirir servicios adicionales (Catering, Parqueadero, Seguro, etc.) junto con sus boletos. Crear subclases de compra para cada combinación posible (`CompraConSeguroYParqueadero`, etc.) causaría una explosión de clases.
* **Propósito:** Asignar responsabilidades adicionales a un objeto de manera dinámica y flexible.
* **Solución:** Se creó una interfaz `PurchaseComponent` y una clase abstracta `PurchaseDecorator` (en la carpeta `PurchaseDecorators`). Luego se crearon decoradores concretos (`CateringDecorator`, `InsuranceDecorator`, etc.) que envuelven la compra original para sumar precios y descripciones.

### 5. Proxy
* **Requisito que resuelve:** Control de acceso a las funcionalidades de administración de eventos y usuarios.
* **Problema:** Cualquier usuario logueado en el sistema no debería poder invocar los métodos para agregar o eliminar eventos y lugares; esto debe ser restringido solo a usuarios con el flag `rootAccess = true`.
* **Propósito:** Proporcionar un sustituto o representante de otro objeto para controlar el acceso a este.
* **Solución:** La clase `AdminProxy` implementa la interfaz `ServiceProxy` e intercepta las llamadas al `AdminController`. En cada método, verifica `checkAccess()`: si el usuario tiene privilegios, delega al controlador real; si no, deniega la acción.

### 6. Composite
* **Requisito que resuelve:** Tratar objetos individuales y composiciones de objetos de forma uniforme.
* **Problema:** Las ubicaciones de los eventos tienen una estructura de árbol: `Place` -> `Zone` -> `Chair`. El sistema requiere consultar las "sillas disponibles" tanto de un escenario completo como de una zona específica.
* **Propósito:** Componer objetos en estructuras de árbol para representar jerarquías parte-todo. 
* **Solución:** Tanto `Place` como `Zone` implementan la interfaz `ZoneComposite`. Al llamar a `getAvailableChairs()` sobre un `Place`, este itera sobre su lista interna de `ZoneComposite` (Zonas) delegando el llamado, permitiendo al cliente ignorar la diferencia entre un nodo intermedio y una hoja de la jerarquía.

### 7. Strategy
* **Requisito que resuelve:** Intercambiabilidad de los métodos de pago.
* **Problema:** El sistema soporta el pago por tarjeta, Apple Pay y PayPal. Incluir toda la lógica en un gran `switch` o `if-else` en la compra violaría principios de diseño y dificultaría añadir nuevos métodos.
* **Propósito:** Definir una familia de algoritmos, encapsular cada uno de ellos y hacerlos intercambiables.
* **Solución:** En el paquete `UserPayments` se definieron estrategias concretas (`ApplePayment`, `CardPayment`, `PayPalPayment`) que implementan la interfaz común `PaymentStrategy`. El método `managePayment(PaymentType)` en `User` instancia la estrategia elegida y ejecuta el pago genéricamente.

### 8. Observer
* **Requisito que resuelve:** Notificar a los usuarios cuando se publica un nuevo evento en la plataforma.
* **Problema:** Los usuarios deben enterarse de los nuevos eventos. Tener a los usuarios consultando constantemente la lista de eventos es ineficiente.
* **Propósito:** Definir una dependencia uno-a-muchos entre objetos, de manera que cuando uno cambie su estado, notifique a los dependientes.
* **Solución:** La clase `User` implementa la interfaz `EventObserver`. La clase `EventManager` (el Subject) mantiene la lista de usuarios y ejecuta `notifyObservers()` iterando sobre ellos y llamando a su método `update(message)` cuando se llama a `addEvent()`.

### 9. Memento
* **Requisito que resuelve:** Guardar el estado de una compra antes de realizar el pago por si se requiere restaurarla o cancelarla.
* **Problema:** Durante el proceso de check-out, el usuario podría modificar su orden, pero luego arrepentirse y querer regresar al estado anterior del carrito. Exponer los atributos internos de la compra para guardarlos externamente violaría el encapsulamiento.
* **Propósito:** Capturar y externalizar el estado interno de un objeto sin violar el encapsulamiento, de manera que el objeto pueda ser restaurado a ese estado posteriormente.
* **Solución:** Se creó un registro inmutable (o clase) `PurchaseMemento`. `Purchase` tiene los métodos `saveToMemento()` que devuelve una instantánea de sus datos críticos (total, lista de tickets, adicionales), y `restoreFromMemento()` que toma el memento y sobrescribe sus campos internos, recuperando el estado.

---

## Principios SOLID Aplicados

1. **Single Responsibility Principle (SRP):**
   * *Ejemplo:* La separación clara de responsabilidades entre clases de modelo como `Ticket` (que solo guarda y representa los datos de un pasaje/entrada) y las clases de Controladores (como `AdminController`), que se encargan netamente de la lógica de negocio de edición. El `Ticket` no es responsable de guardarse en base de datos o de generar interfaces gráficas.

2. **Open/Closed Principle (OCP):**
   * *Ejemplo:* La implementación de los métodos de pago en `UserPayments`. Al usar el patrón Strategy, si la empresa decide añadir "BitcoinPayment" en el futuro, no es necesario modificar el código de la interfaz `Payment` ni de las demás estrategias existentes, simplemente se añade la nueva clase.

3. **Liskov Substitution Principle (LSP):**
   * *Ejemplo:* En la implementación del patrón Decorator, cualquier clase hija o decorador como `ParkingDecorator` puede sustituir a la interfaz/clase base `PurchaseComponent` en el flujo de cálculos. El código que recibe un `PurchaseComponent` funciona igual independientemente de si es una compra plana o una compra altamente decorada con múltiples servicios.

4. **Interface Segregation Principle (ISP):**
   * *Ejemplo:* La aplicación define múltiples interfaces atómicas y específicas como `EventObserver`, `ServiceProxy`, `PaymentStrategy` y `PurchaseClone`. En vez de crear una gran interfaz `IEventSystem` que obligue a `User` a implementar clonado o patrones abstractos que no usa, cada clase solo implementa la interfaz de los comportamientos que requiere.

5. **Dependency Inversion Principle (DIP):**
   * *Ejemplo:* La clase `AdminProxy` no depende concretamente de una implementación para autorizar, sino que respeta la firma de `ServiceProxy`. Igualmente, la validación y ejecución del pago depende de la abstracción `PaymentStrategy` y no de las clases `ApplePayment` directamente, delegando los detalles de bajo nivel a cada implementación individual.

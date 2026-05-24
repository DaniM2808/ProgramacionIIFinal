package co.edu.uniquindio.manejoEventos;

import co.edu.uniquindio.manejoEventos.controller.AdminController;
import co.edu.uniquindio.manejoEventos.model.*;
import co.edu.uniquindio.manejoEventos.model.Enums.*;
import co.edu.uniquindio.manejoEventos.viewController.LoginView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Application extends javafx.application.Application {

    //Main view - delete
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Move to unit test
        Purchase purchase = Purchase.builder().total(7.500).dateCreated(LocalDate.now()).idPurchase("727").build();
         */
        LoginView.createLoginMenu();
        /*
        Take this as Main
         */
        System.out.println("lmao");
        // Admin admin = new Admin("Juan,)
        User user = new User("Danny", "dan@email.com", "572257", "WhenISeeIt");
        EventManager.getInstance().addUser(user);
        Zone zone = Zone.builder().idZone("Z-01").name("VIP / Front Stage").capacity(50).startingPrice(60000.0).build();
        for (int i = 1; i <= zone.getCapacity(); i++) {
            zone.addChair(new Chair("V" + i, (i - 1) / 10 + 1, (i - 1) % 10 + 1, ChairStatus.AVAILABLE, zone));
        }
        Zone zone1 = Zone.builder().idZone("Z-02").name("General Admission").capacity(15).startingPrice(42000.0).build();

        Place place = new Place("P-001", "Makuhari Messe", "Chiba, Japan");
        Place place2 = new Place("P-002", "Zepp Tokyo", "Tokyo, Japan");
        place.addZone(zone);
        place.addZone(zone1);
        place2.addZone(zone);
        place2.addZone(zone1);

        EventManager.getInstance().addPlace(place);
        EventManager.getInstance().addPlace(place2);

        Event event1 = new Event(
                "EVT-001",
                "xi Concert",
                "xi Live Set: A chaotic blend of symphonic hardcore and speedcore",
                "Tokyo",
                LocalDateTime.now(),
                place,
                EventType.CONCERT,
                EventPolicy.CANCELLATION
        );

        Event event2 = new Event(
                "EVT-002",
                "Camellia concert",
                "Camellia's Bassdrop Experience: Insane dubstep and complex electro",
                "Osaka",
                LocalDateTime.now().plusDays(3),
                place,
                EventType.CONCERT,
                EventPolicy.REFUND
        );
        place.addEvent(event1);
        place.addEvent(event2);

        Event event3 = new Event(
                "EVT-003",
                "t+pazolite Concert",
                "t+pazolite OMAKE: Hyperactive hardstyle and speedcore beats",
                "Kyoto",
                LocalDateTime.now().plusWeeks(1),
                place,
                EventType.CONCERT,
                EventPolicy.CANCELLATION
        );

        Event event4 = new Event(
                "EVT-004",
                "BEMANI",
                "BEMANI Symphony: Orchestral arrangements of classic rhythm game songs",
                "Yokohama",
                LocalDateTime.now().plusDays(10),
                place,
                EventType.THEATER,
                EventPolicy.REFUND
        );

        Event event5 = new Event(
                "EVT-005",
                "Arcaea",
                "Arcaea Sound Collection: Live performances of the Arcaea soundtrack",
                "Nagoya",
                LocalDateTime.now().plusMonths(1),
                place,
                EventType.CONCERT,
                EventPolicy.CANCELLATION
        );

        EventManager.getInstance().addEvent(event1);
        EventManager.getInstance().addEvent(event2);
        EventManager.getInstance().addEvent(event3);
        EventManager.getInstance().addEvent(event4);
        EventManager.getInstance().addEvent(event5);

        // Cancel one event for the chart
        event5.setEventStatus(EventStatus.CANCELLED);



        // Create mock Tickets using Builder
        Ticket t1 = Ticket.builder().idTicket("T1").finalCost(100).theEvent(event1).theZone(zone).ticketStatus(TicketStatus.ACTIVE).build();
        Ticket t2 = Ticket.builder().idTicket("T2").finalCost(150).theEvent(event2).theZone(zone1).ticketStatus(TicketStatus.ACTIVE).build();
        Ticket t3 = Ticket.builder().idTicket("T3").finalCost(200).theEvent(event1).theZone(zone).ticketStatus(TicketStatus.ACTIVE).build();

        ArrayList<Ticket> list1 = new ArrayList<>(); list1.add(t1); list1.add(t3);
        ArrayList<Ticket> list2 = new ArrayList<>(); list2.add(t2);

        // Create mock Purchases
        Purchase p1 = new Purchase(user, 300, list1, PaymentType.CARD);
        p1.getAdditionalServices().add("Preferencial");
        p1.getAdditionalServices().add("Catering");
        EventManager.getInstance().addPurchase(p1);
        user.addPurchase(p1);
        Purchase p2 = new Purchase(user, 150, list2, PaymentType.PAYPAL);
        p2.getAdditionalServices().add("Merchandising");
        p2.setDateCreated(LocalDateTime.now().minusMonths(1));
        EventManager.getInstance().addPurchase(p2);
        user.addPurchase(p2);



        // Add some more to make charts look nice
        for(int i=0; i<50; i++){
             ArrayList<Ticket> tl = new ArrayList<>();
             Ticket ticket = Ticket.builder().idTicket("TX"+i).finalCost(100).theEvent(event3).theZone(zone1).ticketStatus(TicketStatus.ACTIVE).build();
             tl.add(ticket);
             Purchase px = new Purchase(user, 100,  tl, PaymentType.APPLE);
             px.setDateCreated(LocalDateTime.now().minusMonths(i%3));
             if(i%2==0) px.getAdditionalServices().add("Parking");
             user.addPurchase(px);
             EventManager.getInstance().addPurchase(px);

        }

        AdminController adminController = new AdminController();
        Admin admin = new Admin("AdminUser","admin@gmail.com","6767","admin");
        Proxy proxy = new Proxy(adminController,admin);

    //    Purchase purchase = new Purchase(user, 100, "132",null, PaymentType.APPLE);
      //  user.addPurchase(purchase);
        System.out.println(EventManager.getInstance().getEventList().size());
        EventManager.getInstance().addUser(admin);
        user.generateReceipt();
    }
}

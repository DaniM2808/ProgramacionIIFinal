package co.edu.uniquindio.manejoEventos.model.PurchaseDecorators;

import co.edu.uniquindio.manejoEventos.model.Interfaces.PurchaseComponent;

public class ParkingDecorator extends PurchaseDecorator {

    public ParkingDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 10000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Parqueadero";
    }
}

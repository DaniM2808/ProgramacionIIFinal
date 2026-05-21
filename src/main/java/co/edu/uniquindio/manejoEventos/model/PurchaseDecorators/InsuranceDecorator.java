package co.edu.uniquindio.manejoEventos.model.PurchaseDecorators;

import co.edu.uniquindio.manejoEventos.model.Interfaces.PurchaseComponent;

public class InsuranceDecorator extends PurchaseDecorator {

    public InsuranceDecorator(PurchaseComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double getTotal() {
        return super.getTotal() + 15000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Seguro";
    }
}

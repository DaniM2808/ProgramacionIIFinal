package co.edu.uniquindio.manejoEventos.model.UserPayments;

import co.edu.uniquindio.manejoEventos.model.Interfaces.PaymentStrategy;

public class PayPalPayment implements PaymentStrategy {

    @Override
    public boolean executePayment() {
        return false;
    }
}

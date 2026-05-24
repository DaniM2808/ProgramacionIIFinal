package co.edu.uniquindio.manejoEventos.model.UserPayments;

import co.edu.uniquindio.manejoEventos.model.Interfaces.PaymentStrategy;

public class CardPayment implements PaymentStrategy {
    @Override
    public boolean executePayment() {
        return true;
    }
}

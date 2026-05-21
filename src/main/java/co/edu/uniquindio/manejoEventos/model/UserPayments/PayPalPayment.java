package co.edu.uniquindio.manejoEventos.model.UserPayments;

import co.edu.uniquindio.manejoEventos.model.Interfaces.Payment;

public class PayPalPayment implements Payment {

    @Override
    public boolean executePayment() {
        return false;
    }
}

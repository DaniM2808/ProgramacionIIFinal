package co.edu.uniquindio.manejoEventos.model;

import co.edu.uniquindio.manejoEventos.model.Enums.PaymentType;

import java.util.ArrayList;

public record PurchaseMemento(double total, ArrayList<Ticket> ticketList, PaymentType paymentType,
                              ArrayList<String> additionalServices) {
}

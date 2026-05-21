package co.edu.uniquindio.manejoEventos.model.Interfaces;

import co.edu.uniquindio.manejoEventos.model.Ticket;

import java.util.ArrayList;

public interface PurchaseComponent {
    double getTotal();
    String getDescription();
    ArrayList<Ticket> getTicketList();
}

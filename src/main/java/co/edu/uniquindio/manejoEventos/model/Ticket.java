package co.edu.uniquindio.manejoEventos.model;

import co.edu.uniquindio.manejoEventos.model.Enums.TicketStatus;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Ticket {
    private String idTicket;
    private double finalCost;

    private Event theEvent;
    private Zone theZone;
    private Chair theChair;

    private TicketStatus ticketStatus;
    private Purchase thePurchase;


}

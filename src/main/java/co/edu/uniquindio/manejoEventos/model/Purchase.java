package co.edu.uniquindio.manejoEventos.model;
import co.edu.uniquindio.manejoEventos.model.Enums.PaymentType;
import co.edu.uniquindio.manejoEventos.model.Enums.PurchaseStatus;
import co.edu.uniquindio.manejoEventos.model.Interfaces.PurchaseClone;
import co.edu.uniquindio.manejoEventos.model.Interfaces.PurchaseComponent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
@ToString
@Getter
@Setter
public class Purchase implements PurchaseClone, PurchaseComponent {
    private String idPurchase;
    private LocalDateTime dateCreated;
    private double total;

    private ArrayList<Ticket> ticketList;
    private final User theUser;
    private PaymentType paymentType;
    private PurchaseStatus purchaseStatus;
    private ArrayList<String> additionalServices;

    public Purchase(User theUser, double total, ArrayList<Ticket> ticketList, PaymentType paymentType) {
        this.theUser = theUser;
        this.ticketList = ticketList;
        this.total = total;
        this.dateCreated = LocalDateTime.now();
        this.idPurchase = generateId();
        this.paymentType = paymentType;
        this.purchaseStatus = PurchaseStatus.CREATED;
        this.additionalServices = new ArrayList<>();
    }

    @Override
    public Purchase cloneObject() {
        return new Purchase(this.theUser, this.total, this.ticketList, this.paymentType);
    }

    @Override
    public String getDescription() {
        return "Compra Base";
    }

    private String generateId(){
        int n = EventManager.getInstance().getPurchaseList().size();
        String id = "PUR-";
        if(n<10){
            id += "00" + n;
        } else if(n<100){
            id += "0" + n;
        } else {
            id += n;
        }
        System.out.println(n);
        return id;
    }


    public PurchaseMemento saveToMemento() {
        return new PurchaseMemento(total, new ArrayList<>(ticketList), paymentType, new ArrayList<>(additionalServices));
    }

    public void restoreFromMemento(PurchaseMemento memento) {
        this.total = memento.total();
        this.ticketList = memento.ticketList();
        this.paymentType = memento.paymentType();
        this.additionalServices = memento.additionalServices();
    }
}

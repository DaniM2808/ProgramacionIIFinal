package co.edu.uniquindio.manejoEventos.model;

import co.edu.uniquindio.manejoEventos.model.Enums.ChairStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Chair {
    private String idChair;
    private int row, number;
    private Zone theZone;
    private ChairStatus chairStatus;

    public Chair(String idChair, int row, int number, ChairStatus chairStatus, Zone theZone) {
        this.idChair = idChair;
        this.row = row;
        this.number = number;
        this.chairStatus = chairStatus;
        this.theZone = theZone;
    }

    /*
    changeStatus();
     */

    @Override
    public String toString() {
        return
                        "   ID: " + idChair + "\n" +
                        "   Row: " + row + "\n" +
                        "   Number: " + number + "\n";
    }
}

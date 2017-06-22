package sample;


import javafx.beans.property.*;

import java.util.Date;

public class Filliere {

    private final IntegerProperty id_filliere;
    private final StringProperty intitule;
    private final StringProperty dateaccr;

    public Filliere(int id, String nom, String dateaccr){
        this.intitule = new SimpleStringProperty(nom);
        this.dateaccr = new SimpleStringProperty(dateaccr);
        this.id_filliere = new SimpleIntegerProperty(id);

    }

    public String getNom(){ return this.intitule.getValue(); }
    public String getDate(){ return this.dateaccr.getValue(); }
    public int getId_filliere(){ return this.id_filliere.getValue(); }

}

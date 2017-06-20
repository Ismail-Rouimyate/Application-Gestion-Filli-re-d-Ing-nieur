package sample;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Filliere {

    private final StringProperty nom;

    public Filliere(String nom){
        this.nom = new SimpleStringProperty(nom);
    }

    public String getNom(){ return this.nom.getValue(); }

}

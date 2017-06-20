package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ismail1 on 18/05/2017.
 */
public class Departement {

    private final StringProperty nom;

    public Departement(String nom){
        this.nom = new SimpleStringProperty(nom);
    }

    public String getNom(){
        return this.nom.getValue();
    }
}

package sample;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Module {
    private final StringProperty nom;

    public Module(String nom){
        this.nom = new SimpleStringProperty(nom);
    }

    public String getNom(){ return this.nom.getValue(); }


}

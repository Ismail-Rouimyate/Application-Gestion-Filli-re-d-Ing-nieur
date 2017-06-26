package sample;


import javafx.beans.property.*;

import java.util.Date;

public class Filliere {

    private final IntegerProperty id_filliere;
    private final StringProperty intitule;
    private final StringProperty dateaccr;
    private final StringProperty nomProf;
    private final StringProperty prenomProf;

    public Filliere(int id, String nom, String dateaccr, String nomProf, String prenomProf){
        this.intitule = new SimpleStringProperty(nom);
        this.dateaccr = new SimpleStringProperty(dateaccr);
        this.id_filliere = new SimpleIntegerProperty(id);
        this.nomProf = new SimpleStringProperty(nomProf);
        this.prenomProf = new SimpleStringProperty(prenomProf);
    }

    public Filliere() { this(0,null,null,null,null); }

    public String getNom(){ return this.intitule.get(); }
    public String getDate(){ return this.dateaccr.get(); }
    public int getId_filliere(){ return this.id_filliere.get(); }
    public String getNomProf() { return this.nomProf.get(); }
    public String getPrenomProf() { return this.prenomProf.get(); }

    public StringProperty intituleProperty(){
        return intitule;
    }

    public StringProperty dateaccrProperty(){
        return dateaccr;
    }

    public IntegerProperty idProperty(){
        return id_filliere;
    }

    public StringProperty nomProfProperty() { return nomProf; }

    public StringProperty prenomProfProperty() { return prenomProf; }

    public void setId_filliere(int id) { this.id_filliere.set(id); }

    public void setIntitule(String intitule) { this.intitule.set(intitule); }

    public void setDateaccr(String dateaccr) { this.dateaccr.set(dateaccr); }

    public void setNomProf(String nomProf) { this.nomProf.set(nomProf); }

    public void setPrenomProf(String prenomProf) { this.prenomProf.set(prenomProf);}
}

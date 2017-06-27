package sample;


import javafx.beans.property.*;

public class Seance {

    private final IntegerProperty id_seance;
    private final IntegerProperty num_planing;
    private final StringProperty date;
    private final StringProperty heure;
    private final StringProperty groupe;
    private final StringProperty nomProf;
    private final StringProperty prenomProf;
    private final StringProperty element;
    private final StringProperty type;
    private final StringProperty salle;

    public Seance(int id, int num_planing, String date, String heure, String groupe, String nomProf, String prenomProf, String element, String type, String salle){
        this.id_seance = new SimpleIntegerProperty(id);
        this.num_planing = new SimpleIntegerProperty(num_planing);
        this.date = new SimpleStringProperty(date);
        this.heure = new SimpleStringProperty(heure);
        this.groupe = new SimpleStringProperty(groupe);
        this.nomProf = new SimpleStringProperty(nomProf);
        this.prenomProf = new SimpleStringProperty(prenomProf);
        this.element = new SimpleStringProperty(element);
        this.type = new SimpleStringProperty(type);
        this.salle = new SimpleStringProperty(salle);

    }

    public  Seance() { this(0,0,null,null,null,null,null,null,null,null); }

    // Getters
    public int getIdSeance(){ return this.id_seance.get(); }
    public int getNumPlaning(){ return this.num_planing.get(); }
    public String getDate(){ return this.date.get(); }
    public String getHeure(){ return this.heure.get(); }
    public String getGroupe(){ return this.groupe.get(); }
    public String getNomProf(){ return this.nomProf.get(); }
    public String getPrenomProf(){ return this.prenomProf.get(); }
    public String getElement(){ return this.element.get(); }
    public String getType(){ return this.type.get(); }
    public String getSalle(){ return this.salle.get(); }

    // Properties
    public IntegerProperty idSeanceProperty(){ return id_seance; }
    public IntegerProperty numPlaningProperty(){ return num_planing; }
    public StringProperty dateProperty(){ return date; }
    public StringProperty heureProperty(){ return heure; }
    public StringProperty groupeProperty(){ return groupe; }
    public StringProperty nomProfProperty(){ return nomProf; }
    public StringProperty prenomProfProperty(){ return prenomProf; }
    public StringProperty elementProperty(){ return element; }
    public StringProperty typeProperty(){ return type; }
    public StringProperty salleProperty(){ return salle; }

    // Setters
    public void setId_seance(int id_seance) { this.id_seance.set(id_seance);}
    public void setNum_planing(int num_planing) { this.num_planing.set(num_planing);}
    public void setDate(String date){ this.date.set(date);}
    public void setHeure(String heure){ this.heure.set(heure);}
    public void setGroupe(String groupe){this.groupe.set(groupe);}
    public void setNomProf(String nomProf){ this.nomProf.set(nomProf);}
    public void setPrenomProf(String prenomProf){ this.prenomProf.set(prenomProf);}
    public void setElement(String element){this.element.set(element);}
    public void setType(String type){this.type.set(type);}
    public void setSalle(String salle){ this.salle.set(salle);}

}

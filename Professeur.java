package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.lang.String;



/* Model class
    pour professeur
 */
public class Professeur {
    private final StringProperty email;
    private final StringProperty motDePasse;
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty specialite;
    private final ObservableList<Module> module;
    private final ObservableList<Filliere> filliere;
    private final StringProperty departement;


    // construtor without parameters
    public Professeur(){
        this(0,null, null,null,null,null,null,null,null);
    }

    /* constructor with parameters
    * @param id
    * @param email
    * @param motDePasse
    * @param nom
    * @param prenom
    * @param specialite
     */

    public Professeur(String email, String motDePasse){
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.id = null;
        this.nom = null;
        this.prenom = null;
        this.specialite = null;
        this.module = null;
        this.filliere = null;
        this.departement = null;
    }

    public Professeur(int id, String email, String motDePasse, String nom, String prenom, String specialite,ObservableList<Module> module, ObservableList<Filliere> filliere, String departement){
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.specialite = new SimpleStringProperty(specialite);
        this.module = module;
        this.filliere = filliere;
        this.departement = new SimpleStringProperty(departement);
    }
    // Start getters
    public String getEmail(){
        return email.get();
    }

    public String getMotDePasse(){
        return motDePasse.get();
    }

    public String getNom(){
        return nom.get();
    }

    public String getPrenom(){
        return prenom.get();
    }

    public String getSpecialite(){
        return specialite.get();
    }

    public int getId(){
        return id.get();
    }

    public ObservableList<Module> getModule(){
        return module;
    }

    public ObservableList<Filliere> getFilliere(){
        return filliere;
    }

    public String getDepratement(){
        return departement.get();
    }
    // End getters

    // Start setters
    public void setEmail(String email){
        this.email.set(email);
    }

    public void setMotDePasse(String motDePasse){
        this.motDePasse.set(motDePasse);
    }

    public void setId(int id){
        this.id.set(id);
    }

    public void setNom(String nom){
        this.nom.set(nom);
    }

    public void setPrenom(String prenom){
        this.prenom.set(prenom);
    }

    public void setSpecialite(String specialite){
        this.specialite.set(specialite);
    }

    public void setDepartement(String departement) { this.departement.set(departement); }






    // end setters

    // start properities
    public StringProperty emailProperty(){
        return email;
    }

    public StringProperty motDePasseProperty(){
        return motDePasse;
    }

    public IntegerProperty idProperty(){
        return id;
    }

    public StringProperty nomProperty(){
        return nom;
    }

    public StringProperty prenomProperty(){
        return prenom;
    }

    public StringProperty specialiteProperty(){
        return specialite;
    }



    // end Properties
}

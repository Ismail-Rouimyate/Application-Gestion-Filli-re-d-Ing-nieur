package sample;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Module {
    private final StringProperty intitule;
    private final IntegerProperty id;
    private final IntegerProperty vhc;
    private final IntegerProperty vhtd;
    private final IntegerProperty vhtp;
    private final IntegerProperty vhap;
    private final ObservableList<ElementModule> listElem;

    public Module(int id, String nom, int vhc, int vhtd, int vhtp, int vhap, ObservableList<ElementModule> listElem){
        this.id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(nom);
        this.vhc = new SimpleIntegerProperty(vhc);
        this.vhtd = new SimpleIntegerProperty(vhtd);
        this.vhtp = new SimpleIntegerProperty(vhtp);
        this.vhap = new SimpleIntegerProperty(vhap);
        this.listElem = listElem;

    }

    public String getNom(){ return this.intitule.getValue(); }


}

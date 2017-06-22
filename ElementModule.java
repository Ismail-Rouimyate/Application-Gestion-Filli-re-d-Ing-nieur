package sample;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ElementModule {

    private final IntegerProperty id;
    private final StringProperty intitule;
    private final IntegerProperty id_prof;
    private final IntegerProperty id_module;

    public ElementModule(int id, String intitule, int id_prof, int id_module){
        this.id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(intitule);
        this.id_prof = new SimpleIntegerProperty(id_prof);
        this.id_module = new SimpleIntegerProperty(id_module);
    }

}

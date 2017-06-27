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
    private final ObservableList<String> listElem;
    private final StringProperty nomProf;
    private final StringProperty prenomProf;


    public Module(){ this(0,null,0,0,0,0,null,null,null); }

    public Module(int id, String nom, int vhc, int vhtd, int vhtp, int vhap, ObservableList<String> listElem, String nomProf, String prenomProf){
        this.id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(nom);
        this.vhc = new SimpleIntegerProperty(vhc);
        this.vhtd = new SimpleIntegerProperty(vhtd);
        this.vhtp = new SimpleIntegerProperty(vhtp);
        this.vhap = new SimpleIntegerProperty(vhap);
        this.listElem = listElem;
        this.nomProf = new SimpleStringProperty(nomProf);
        this.prenomProf = new SimpleStringProperty(prenomProf);

    }

    public IntegerProperty idProperty(){ return id; }

    public StringProperty intituleProperty(){
        return intitule;
    }

    public IntegerProperty vhcProperty(){
        return vhc;
    }

    public IntegerProperty vhtdProperty(){ return vhtd; }

    public IntegerProperty vhtpProperty(){
        return vhtp;
    }

    public IntegerProperty vhapProperty(){
        return vhap;
    }

    public ObservableList<String> listElemProperty(){
        return listElem;
    }

    public StringProperty nomProfProperty() { return nomProf; }

    public StringProperty prenomProfProperty() { return prenomProf; }

    public String getNom(){ return this.intitule.get(); }

    public int getId(){ return this.id.get(); }

    public int getvhc(){ return this.vhc.get(); }

    public int getvhtd(){ return this.vhtd.get(); }

    public int getvhtp(){ return this.vhtp.get(); }

    public int getvhap(){ return this.vhap.get(); }

    public String getNomProf(){ return this.nomProf.get(); }

    public String getPrenomProf(){ return this.prenomProf.get(); }

    public void setIntitule(String intitule) { this.intitule.set(intitule);}

    public void setVhc (int vhc) { this.vhc.set(vhc); }

    public void setVhtd (int vhtd) { this.vhtd.set(vhtd); }

    public void setVhtp (int vhtp) { this.vhtp.set(vhtp); }

    public void setVhap (int vhap) { this.vhap.set(vhap); }

    public void setListElem (String elem1, String elem2, String elem3) {
        this.listElem.set(0,elem1);
        this.listElem.add(elem2);
        this.listElem.add(elem3);
    }

    public void setNomProf (String nomProf){ this.nomProf.set(nomProf);}

    public void setPrenomProf (String prenomProf) { this.prenomProf.set(prenomProf);}

}

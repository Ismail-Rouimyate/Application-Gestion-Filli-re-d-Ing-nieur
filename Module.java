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
    private final StringProperty elem1;
    private final StringProperty elem2;
    private final StringProperty elem3;
    private final StringProperty nomProf;
    private final StringProperty prenomProf;


    public Module(){ this(0,null,0,0,0,0,null,null,null,null,null); }

    public Module(int id, String nom, int vhc, int vhtd, int vhtp, int vhap, String elem1, String elem2, String elem3, String nomProf, String prenomProf){
        this.id = new SimpleIntegerProperty(id);
        this.intitule = new SimpleStringProperty(nom);
        this.vhc = new SimpleIntegerProperty(vhc);
        this.vhtd = new SimpleIntegerProperty(vhtd);
        this.vhtp = new SimpleIntegerProperty(vhtp);
        this.vhap = new SimpleIntegerProperty(vhap);
        this.elem1 = new SimpleStringProperty(elem1);
        this.elem2 = new SimpleStringProperty(elem2);
        this.elem3 = new SimpleStringProperty(elem3);
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

    public StringProperty elem1Property(){
        return elem1;
    }

    public StringProperty elem2Property() { return elem2; }

    public StringProperty elem3Property() { return elem3; }

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

    public String getElem1() { return this.elem1.get(); }

    public String getElem2() { return this.elem2.get(); }

    public String getElem3() { return this.elem3.get(); }

    public void setIntitule(String intitule) { this.intitule.set(intitule);}

    public void setVhc (int vhc) { this.vhc.set(vhc); }

    public void setVhtd (int vhtd) { this.vhtd.set(vhtd); }

    public void setVhtp (int vhtp) { this.vhtp.set(vhtp); }

    public void setVhap (int vhap) { this.vhap.set(vhap); }

    public void setElem1 (String elem1) { this.elem1.set(elem1); }

    public void setElem2 (String elem2) { this.elem2.set(elem2); }

    public void setElem3 (String elem3) { this.elem3.set(elem3); }

    public void setNomProf (String nomProf){ this.nomProf.set(nomProf);}

    public void setPrenomProf (String prenomProf) { this.prenomProf.set(prenomProf);}

}

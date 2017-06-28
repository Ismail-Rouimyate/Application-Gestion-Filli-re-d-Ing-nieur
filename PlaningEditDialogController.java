package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlaningEditDialogController {

    @FXML
    private TextField semaineField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField heureField;
    @FXML
    private TextField groupeField;
    @FXML
    private TextField nomProfField;
    @FXML
    private TextField prenomProfField;
    @FXML
    private TextField elementField;
    @FXML
    private TextField salleField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField jourField;


    Stage dialogStage = new Stage();
    Seance seance = new Seance();

    private boolean okClicked = false;

    public boolean IsOkClicked(){
        return okClicked;
    }

    public void setSeance(Seance seance){
        this.seance = seance;
        nbSeance();
        semaineField.setText(Integer.toString(seance.getNumPlaning()));
        dateField.setText(seance.getDate());
        heureField.setText(seance.getHeure());
        groupeField.setText(seance.getGroupe());
        nomProfField.setText(seance.getNomProf());
        prenomProfField.setText(seance.getPrenomProf());
        elementField.setText(seance.getElement());
        salleField.setText(seance.getSalle());
        typeField.setText(seance.getType());
        jourField.setText(seance.getJour());
    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * called when the user clicks on OK
     */
    @FXML
    public void handleOk() {
        if (isInputValid()) {
            seance.setNum_planing(Integer.parseInt(semaineField.getText()));
            seance.setDate(dateField.getText());
            seance.setHeure(heureField.getText());
            seance.setGroupe(groupeField.getText());
            seance.setNomProf(nomProfField.getText());
            seance.setPrenomProf(prenomProfField.getText());
            seance.setElement(elementField.getText());
            seance.setSalle(salleField.getText());
            seance.setType(typeField.getText());
            // TODO : problème avec le edit de seance
            if (verifSeance(seance.getIdSeance())){
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("UPDATE seance SET type = ?, heure_debut = ?, id_prof = ?, id_planing = ?, id_elem = ?, date = ?, salle = ?, groupe = ?, jour = ? WHERE id_seance = ?");
                    statement.setString(1,typeField.getText());
                    statement.setString(2,heureField.getText());
                    statement.setString(3,Integer.toString(getProf(nomProfField.getText(), prenomProfField.getText())));
                    statement.setString(4,semaineField.getText());
                    statement.setString(5,Integer.toString(getElem(elementField.getText())));
                    statement.setString(6,dateField.getText());
                    statement.setString(7,salleField.getText());
                    statement.setString(8,groupeField.getText());
                    statement.setString(9,jourField.getText());
                    statement.setString(10,Integer.toString(seance.getIdSeance()));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                int maxId = 0;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("SELECT max(id_seance) FROM seance");
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        maxId = resultSet.getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                maxId++;
                try {
                    Connection con = MySqlJDBC.connection;
                    PreparedStatement statement = con.prepareStatement("INSERT INTO seance VALUES (?,?,?,false,?,0,?,0,?,?,?,?,?)");
                    statement.setString(1,Integer.toString(maxId));
                    statement.setString(2,typeField.getText());
                    statement.setString(3,heureField.getText());
                    statement.setString(4,Integer.toString(getProf((nomProfField.getText()), prenomProfField.getText())));
                    statement.setString(5,semaineField.getText());
                    statement.setString(6,Integer.toString(getElem((elementField.getText()))));
                    statement.setString(7,dateField.getText());
                    statement.setString(8,salleField.getText());
                    statement.setString(9,groupeField.getText());
                    statement.setString(10,jourField.getText());
                    statement.executeUpdate();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleCancle(){ dialogStage.close(); }

    private boolean isInputValid(){
        String errorMessage = "";
        if(semaineField.getText() == null || semaineField.getText().length() == 0){
            errorMessage += "Pas de semaine valide! \n";
        }
        if(dateField.getText() == null || dateField.getText().length() == 0){
            errorMessage += "Pas de valide! \n";
        }
        if(heureField.getText() == null || heureField.getText().length() == 0){
            errorMessage += "Pas d'heure valide! \n";
        }
        if(groupeField.getText() == null || groupeField.getText().length() == 0){
            errorMessage += "Pas de groupe valide! \n";
        }
        if(elementField.getText() == null || elementField.getText().length() == 0){
            errorMessage += "Pas d'element valide! \n";
        }
        if(salleField.getText() == null || salleField.getText().length() == 0){
            errorMessage += "Pas de salle valide! \n";
        }
        if(typeField.getText() == null || typeField.getText().length() == 0){
            errorMessage += "Pas de type valide! \n";
        }
        if(jourField.getText() == null || jourField.getText().length() == 0){
            errorMessage += "Pas de jour valide! \n";
        }
        if (errorMessage.length() == 0){
            return true;
        }
        else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    private int getProf(String nom, String prenom){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM professeur WHERE nom = ? AND prenom = ?");
            statement.setString(1,nom);
            statement.setString(2,prenom);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return result.getInt("id_prof");
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private boolean verifSeance(int id){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT * FROM seance WHERE id_seance = ?");
            statement.setString(1,Integer.toString(id));
            ResultSet result = statement.executeQuery();
            while (result.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private int getElem(String nom){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("SELECT id_elem FROM element_module WHERE intitule = ?");
            statement.setString(1,nom);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public void setNbSeance(int nb, String heure, String jour){
        try{
            Connection con = MySqlJDBC.connection;
            PreparedStatement statement = con.prepareStatement("UPDATE seance SET nb_seance = ? WHERE heure_debut LIKE ? AND jour LIKE ?");
            statement.setString(1,Integer.toString(nb));
            statement.setString(2,heure);
            statement.setString(3,jour);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void nbSeance(){
        setNbSeance(1,"8h","lundi");
        setNbSeance(2,"10h","lundi");
        setNbSeance(3,"14h","lundi");
        setNbSeance(4,"16h","lundi");
        setNbSeance(5,"8h","mardi");
        setNbSeance(6,"10h","mardi");
        setNbSeance(7,"14h","mardi");
        setNbSeance(8,"16h","mardi");
        setNbSeance(9,"8h","mercredi");
        setNbSeance(10,"10h","mercredi");
        setNbSeance(11,"14h","mercredi");
        setNbSeance(12,"16h","mercredi");
        setNbSeance(13,"8h","jeudi");
        setNbSeance(14,"10h","jeudi");
        setNbSeance(15,"14h","jeudi");
        setNbSeance(16,"16h","jeudi");
        setNbSeance(17,"8h","vendredi");
        setNbSeance(18,"10h","vendredi");
        setNbSeance(19,"14h","vendredi");
        setNbSeance(20,"16h","vendredi");
        setNbSeance(21,"8h","samedi");
        setNbSeance(22,"10h","samedi");
        setNbSeance(23,"14h","samedi");
        setNbSeance(24,"16h","samedi");
    }
}

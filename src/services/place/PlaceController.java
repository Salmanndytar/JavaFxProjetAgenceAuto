package services.place;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.classeImple.*;
import sourse.classes.*;
import sourse.interphaces.*;
import sourse.outils.DB;
import sourse.outils.Outil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import static main.MainController.*;

public class PlaceController implements Initializable {

    @FXML
    private Button validebt;
    @FXML
    private Button annulerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button imprimerbt;
    @FXML
    private Button modifierbt;

    @FXML
    private TextField codetxt;
    @FXML
    private int id;

    @FXML
    private TableView<Place> tableplace;
    @FXML
    private TableColumn<Place, Integer> colid;
    @FXML
    private TableColumn<Place, String>colcode;
    @FXML
    private TableColumn<Place, String>coletat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colcode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        ChargeRecu();
    }

    IPlace iPlace = new PlaceImpl();
   Place  place = new Place();
    DB db = new DB();
    public void insertion() {
        if (codetxt.getText().equals(""))
            Alerte("Veuillez saisire le code de la place ");
        else {
            place.setCode(codetxt.getText());
            place.setEtat(0);
            boolean ok = VerifPlace();
            if (ok != true){
                iPlace.addPlace(place);
                ChargeRecu();
                Annuler();
            }
            else
                Alerte("Code place existant !");
        }


    }
    public boolean VerifPlace(){
        boolean ok =false;
        List<Place> places =iPlace.listePlace();
        for (Place p : places){
            if (p.getCode().equals(codetxt.getText()))
                ok=true;
            break;
        }
        return ok;
    }
    public void Annuler(){

        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        validebt.setVisible(true);
        codetxt.setText("");

    }
    public void Modifier() throws SQLException {

        if (codetxt.getText().equals(""))
            Alerte("Veuillez saisire le code de la place ");
        else {

            boolean ok = VerifPlace();
            Place place = tableplace.getSelectionModel().getSelectedItem();
            if (place.getEtat() != 1 && ok == false) {
                Alerte(ok+" "+place.getEtat());
                String sql="UPDATE place SET code= '"+codetxt.getText()+"' WHERE id = '"+place.getId()+"'";
                db.preparedStatement(sql);
                db.executUpdate(sql);
                ChargeRecu();
                Annuler();
            }
            else
                Alerte("Cette place est occupée\n OU \n Saisire un nouveau code inexistant !");
        }
    }
    public void Supprimer() {
        boolean confirmation = AlertConfirmation("Vous etes sure de vouloire supprimer");
        if (confirmation == true){
            Place place = tableplace.getSelectionModel().getSelectedItem();
            if (place.getEtat()!=1){
                iPlace.suppPlace(id);
                ChargeRecu();
                Annuler();
            }
            else
                Alerte("Place occupée !");
        }
    }
    public void clockLingne() {
       Place place = tableplace.getSelectionModel().getSelectedItem();
        if (place != null){
            modifierbt.setVisible(true);
            imprimerbt.setVisible(true);
            if (adminparams!=null)
                supprimerbt.setVisible(true);
            validebt.setVisible(false);
            id=place.getId();
            codetxt.setText(place.getCode());
        }
    }
    public void Alerte(String message) {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("alerte");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public boolean AlertConfirmation(String message){
        boolean confirmation = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attention");
        alert.setContentText(message);
        Optional<ButtonType> resultat = alert.showAndWait();
        if (resultat.get() == ButtonType.OK)
            confirmation = true;

        return confirmation;

    }
    public void ChargeRecu(){
        ObservableList<Place> placeObservableList = FXCollections.observableArrayList();
        List<Place>placeList = iPlace.listePlace();
        for (Place p : placeList){
            placeObservableList.add(p);
        }
        tableplace.setItems( placeObservableList);
    }




}

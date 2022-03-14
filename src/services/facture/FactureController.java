package services.facture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.classeImple.FactureImpl;
import sourse.classeImple.PlaceImpl;
import sourse.classes.Facture;
import sourse.classes.Place;
import sourse.interphaces.IFacture;
import sourse.interphaces.IPlace;
import sourse.outils.DB;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.MainController.adminparams;

public class FactureController implements Initializable {

    @FXML
    private Button imprimerbt;
    @FXML
    private int id;

    @FXML
    private TableView<Facture> tableplfacture;
    @FXML
    private TableColumn<Facture, Integer> colid;
    @FXML
    private TableColumn<Facture, String>coldate;
    @FXML
    private TableColumn<Facture, String>colmontant;
    @FXML
    private TableColumn<Facture, String>colpenalite;
    @FXML
    private TableColumn<Facture, String>colretoure;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colmontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        colpenalite.setCellValueFactory(new PropertyValueFactory<>("penalite"));
        colretoure.setCellValueFactory(new PropertyValueFactory<>("retoure"));

        imprimerbt.setVisible(false);
        ChargeFacture();
    }

   IFacture iFacture = new FactureImpl();

    public void Annuler(){
        imprimerbt.setVisible(false);
    }
    public void clockLingne() {
       Facture facture =  tableplfacture.getSelectionModel().getSelectedItem();
        if (facture != null){
            imprimerbt.setVisible(true);
            id=facture.getId();
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
    public void ChargeFacture(){
        ObservableList<Facture> facturesObservableList = FXCollections.observableArrayList();
        List<Facture>factureList = iFacture.listeFacture().stream().collect(Collectors.toList());
        for (Facture f: factureList){
            facturesObservableList.add(f);
        }
       tableplfacture.setItems( facturesObservableList);
    }
}

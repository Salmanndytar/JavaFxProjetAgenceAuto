package services.retour;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sourse.classeImple.*;
import sourse.classes.*;
import sourse.interphaces.*;
import sourse.outils.Outil;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.MainController.*;

public class ListRetourController implements Initializable {

    @FXML
    private Button supprimerbt;
    @FXML
    private Button imprimerbt;
    @FXML
    private ComboBox<Location> locationComboBox;
    @FXML
    private int id;

    @FXML
    private TableView<Retoure> tableretoure;
    @FXML
    private TableColumn<Retoure, Integer> colid;
    @FXML
    private TableColumn<Retoure, String>colDateR;
    @FXML
    private TableColumn<Retoure, String>collocation;
    @FXML
    private TableColumn<Retoure, String>colagent;
    @FXML
    private TableColumn<Retoure, String> colcaissier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateR.setCellValueFactory(new PropertyValueFactory<>("Date"));
        collocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colagent.setCellValueFactory(new PropertyValueFactory<>("agent"));
        colcaissier.setCellValueFactory(new PropertyValueFactory<>("caissier"));
        supprimerbt.setVisible(false);
        if (caissierparams!=null){
            supprimerbt.setVisible(false);
        }

        ChargeRetoure();
    }

    Facture facture = new Facture();
    IFacture iFacture = new FactureImpl();
    Retoure retoure = new Retoure();
    IRetour iRetour = new RetourImpl();
    Recu recu = new Recu();
    IRecu iRecu = new RecuImpl();

    public void Annuler(){

      supprimerbt.setVisible(false);
      imprimerbt.setVisible(false);

    }
    public void Supprimer(ActionEvent event){
        Retoure table=tableretoure.getSelectionModel().getSelectedItem();
        int nbj = Outil.CalculJours(table.getDate(),LocalDate.now());

        boolean confirmation = AlertConfirmation(" Vous etes sure de vouloire supprimer ?\n " +
                "cette suppression signifie que\n le retoure n'a jamais eu lieu\n pourtant il est enregisré et validé !\n Elle sera classée parmis les locations validée\n" +
                "--------------------------------\n Il est validé le "+table.getDate()+" il y a "+nbj+" jour(s)");
        if (confirmation == true) {
            Facture facture = null;
            List<Facture> factureList = iFacture.listeFacture();

            for (Facture f : factureList)
                if (f.getRetoure().getId()==tableretoure.getSelectionModel().getSelectedItem().getId())
                    facture = f;
             if (facture!=null){
                 iFacture.suppFacture(facture.getId());
                 Modifrecu();
                 iRetour.suppRetoure(tableretoure.getSelectionModel().getSelectedItem().getLocation().getId());
                 ChargeRetoure();
                 Actualiser(event);
             }

            }
        }
    public void Modifrecu() {
       Location location = tableretoure.getSelectionModel().getSelectedItem().getLocation();
        LocalDate dateF = location.getDateF();
        LocalDate dateD = location.getDateD();
        int nbjour = Outil.CalculJours(dateD,dateF);

        float prix = (location.getVoiture().getPrix());
        prix = prix*(nbjour);
        float demiprix = prix/2;

        recu.setDuree(nbjour);
        recu.setMontantRestant(demiprix);
        recu.setMontantVerse(demiprix);
        recu.setLocation(location);
        iRecu.updatRecu(recu);

    }
    public void clockLingne(){
        Retoure retoure1= tableretoure.getSelectionModel().getSelectedItem();
        if (retoure1!=null){
            imprimerbt.setVisible(true);
            if (adminparams!=null)
                supprimerbt.setVisible(true);
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
    public void ChargeRetoure(){
        ObservableList<Retoure> retoureObservableList = FXCollections.observableArrayList();
        List<Retoure>retoureList =iRetour.listeRetoure();
        for (Retoure r : retoureList){
            retoureObservableList.add(r);
        }
        tableretoure.setItems(retoureObservableList);
    }
    public void Actualiser(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Outil.load(event, "Gestion vehicule", "/services/retour/ListRetour.fxml");
        }catch (Exception ex){ex.printStackTrace();}

    }





}

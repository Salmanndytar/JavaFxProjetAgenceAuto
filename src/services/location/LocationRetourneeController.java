package services.location;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.classeImple.*;
import sourse.classes.*;
import sourse.interphaces.*;
import sourse.outils.Outil;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static main.MainController.adminparams;

public class LocationRetourneeController implements Initializable {

    @FXML
    private Button supprimerbt;
    @FXML
    private Button infobt;
    @FXML
    private int id;
    @FXML
    private TableView<Location> tablelocationR;
    @FXML
    private TableColumn<Location, Integer> colid;
    @FXML
    private TableColumn<Location, String>colDateD;
    @FXML
    private TableColumn<Location, String>colDateF;
    @FXML
    private TableColumn<Location, String>colvoiture;
    @FXML
    private TableColumn<Location, String>collocateur;
    @FXML
    private TableColumn<Location, String>colcaissier;
    @FXML
    private TableColumn<Location, String>colagent;
    @FXML
    private TableColumn<Location, String>coldatV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDateD.setCellValueFactory(new PropertyValueFactory<>("DateD"));
        colDateF.setCellValueFactory(new PropertyValueFactory<>("DateF"));
        colvoiture.setCellValueFactory(new PropertyValueFactory<>("voiture"));
        collocateur.setCellValueFactory(new PropertyValueFactory<>("locateur"));
        colcaissier.setCellValueFactory(new PropertyValueFactory<>("caissier"));
        colagent.setCellValueFactory(new PropertyValueFactory<>("agents"));
        coldatV.setCellValueFactory(new PropertyValueFactory<>("DateV"));

        infobt.setDisable(true);
        supprimerbt.setDisable(true);

        ChargeLocationRetournee();
    }
    Voiture voiture= new Voiture();
    IVoiture iVoiture = new VoitureImpl();
    ILocation iLocation =new LocationImpl();
    Recu recu = new Recu();
    IRecu iRecu = new RecuImpl();
    IRetour iRetour = new RetourImpl();
    IFacture iFacture = new FactureImpl();
    public void SupprimerC(ActionEvent event) {
        boolean confirmation = AlertConfirmation("Vous etes sure de vouloire supprimer");//assurer la suppression
        if (confirmation == true){

            Retoure retoure = null;
            Facture facture = null;

            List<Retoure>retoureList = iRetour.listeRetoure().stream().collect(Collectors.toList());
            List<Facture> factureList = iFacture.listeFacture();

            //les deux boucles for c pour avoir le id de la facture
            for (Retoure r : retoureList){
                if (r.getLocation().getId()==tablelocationR.getSelectionModel().getSelectedItem().getId()){
                    retoure = r;
                }
            }
            for (Facture f : factureList){
                if (f.getRetoure().getId()==retoure.getId()){
                    facture = f;
                }
            }

            int oka = iFacture.suppFacture(facture.getId());
            int oki=iRecu.suppRecu(id);//le recu est supprimer par claus where id location
            int ok = iRetour.suppRetoure(id);//le retoure est supprimer par claus where id location

            if (oki != 0 && ok !=0 && oka != 0) {
                iLocation.suppLocation(id);
                ChargeLocationRetournee();
            }
        }
    }
    public void clickLingne() {
        Location location1= tablelocationR.getSelectionModel().getSelectedItem();
        if (location1!=null){
            if (adminparams!=null){
                supprimerbt.setDisable(false);
                infobt.setDisable(false);
                id=location1.getId();
            }
        }
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
    public void ChargeLocationRetournee(){
        ObservableList<Location> locationObservableList = FXCollections.observableArrayList();
        List<Location>locationList =iLocation.listeLocationRetour().stream().collect(Collectors.toList());
        for (Location l : locationList){
            locationObservableList.add(l);
        }
        tablelocationR.setItems(locationObservableList);
    }
    public void infoLocationRetour(){
        Retoure retoure = null;
        Facture facture = null;

        List<Retoure>retoureList = iRetour.listeRetoure().stream().collect(Collectors.toList());
        List<Facture> factureList = iFacture.listeFacture();

        for (Retoure r : retoureList){
            if (r.getLocation().getId()==tablelocationR.getSelectionModel().getSelectedItem().getId()){
                retoure = r;
            }
        }
        for (Facture f : factureList){
            if (f.getRetoure().getId()==retoure.getId()){
                facture = f;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("InfoPlus");
        alert.setContentText("Cette location etait retoutnée le "+retoure.getDate() + ".\nLes informations de ce retoure etaient saisie par l'agent: "+
                retoure.getAgent().toString().toUpperCase()+" et validé par caissier(e):  " + retoure.getCaissier().toString().toUpperCase()+".\n" +
                "La penelité : "+facture.getPenalite()+" FCFA,  le montant total payé est de : "+facture.getMontant()+" FCFA");
        alert.showAndWait();

    }

}

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
import static main.MainController.*;


public class ListeLocationController implements Initializable {

    @FXML
    private Button supprimerbt;
    @FXML
    private int id;
    @FXML
    private TableView<Location> tablelocation;
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


        supprimerbt.setDisable(true);
        ChargeLocation();
    }
    Voiture voiture= new Voiture();
    IVoiture iVoiture = new VoitureImpl();
    ILocation iLocation =new LocationImpl();
    IRecu iRecu = new RecuImpl();
    public void Supprimer(ActionEvent event) {
        boolean confirmation = AlertConfirmation("Vous etes sure de vouloire supprimer");//assurer la suppression
        if (confirmation == true){
            int oki=iRecu.suppRecu(id);
            if (oki != 0) {
                ModifVoitureEtat0();
                iLocation.suppLocation(id);
                ChargeLocation();
            }
        }
    }
    public void clockLingne() {
        Location location1= tablelocation.getSelectionModel().getSelectedItem();
        if (location1!=null){
            if (adminparams!=null){
                supprimerbt.setDisable(false);
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
    public void ChargeLocation(){
        ObservableList<Location> locationObservableList = FXCollections.observableArrayList();
        List<Location>locationList =iLocation.listeLocation().stream().collect(Collectors.toList());
        for (Location l : locationList){
            locationObservableList.add(l);
        }
        tablelocation.setItems(locationObservableList);
    }
    public void ModifVoitureEtat0(){
        //rendre l'etat=0 pour la voiture liberee par modification
        voiture.setId( tablelocation.getSelectionModel().getSelectedItem().getVoiture().getId());
        voiture.setMatrcule(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getMatrcule());
        voiture.setPoids(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPoids());
        voiture.setVitesse(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getVitesse());
        voiture.setPlace(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPlace());
        voiture.setCarburent(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getCarburent());
        voiture.setCouleur(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getCouleur());
        voiture.setDateMise(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getDateMise());
        voiture.setPrix(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getPrix());
        voiture.setMarque(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getMarque());
        voiture.setModel(tablelocation.getSelectionModel().getSelectedItem().getVoiture().getModel());
        voiture.setEtat(0);
        iVoiture.updatVoiture(voiture);
    }


}

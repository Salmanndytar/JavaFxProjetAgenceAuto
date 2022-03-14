package locateur;

import sourse.classeImple.LocationImpl;
import sourse.classeImple.LocatuerImpl;
import sourse.classes.Locateur;
import sourse.classes.Location;
import sourse.interphaces.ILocateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.interphaces.ILocation;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LocateurController implements Initializable {
    @FXML
    private int verificationtel;
    String telSelect="";
    @FXML
    private int idtxt;
    @FXML
    private TextField nomtxt;
    @FXML
    private TextField carttxt;
    @FXML
    private TextField teltxt;
    @FXML
    private Button validerbt;
    @FXML
    private Button supprimerbt;
    @FXML
    private Button annulerbt;
    @FXML
    private Button modifierbt;

    @FXML
    private TableView<Locateur> tablelocateur;
    @FXML
    private TableColumn<Locateur, Integer> colonneid;
    @FXML
    private TableColumn<Locateur, String>colonnecarte;
    @FXML
    private TableColumn<Locateur, String>colonnenom;
    @FXML
    private TableColumn<Locateur, String>colonnetel;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

            colonneid.setCellValueFactory(new PropertyValueFactory<Locateur, Integer>("id"));
            colonnecarte.setCellValueFactory(new PropertyValueFactory<Locateur, String>("numCarte"));
            colonnenom.setCellValueFactory(new  PropertyValueFactory<Locateur, String>("nom"));
            colonnetel.setCellValueFactory(new PropertyValueFactory<Locateur, String>("tel"));
            load();
            modifierbt.setVisible(false);
            supprimerbt.setVisible(false);
        }
        Locateur locateur= new Locateur();
        ILocateur ilocateur = new LocatuerImpl();
        ILocation iLocation = new  LocationImpl();
        public void Insert() {
            locateur.setNom(nomtxt.getText());
            locateur.setNumCarte(carttxt.getText());
            locateur.setTel(teltxt.getText());


            if(nomtxt.getText().equals("")|| carttxt.getText().equals("")||teltxt.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerte");
                alert.setContentText("Veuillez remplire tous les chemps");
                alert.showAndWait();
            }else
            {
                Verification(teltxt.getText());
                if (verificationtel==0 )
                {
                    ilocateur.addLocateur(locateur);
                    load();
                    Annuler();
                }else Alerte("numero téléphone invalide");
            }
        }
        public void Annuler(){
            nomtxt.setText("");
            carttxt.setText("");
            teltxt.setText("");
            modifierbt.setVisible(false);
            supprimerbt.setVisible(false);
            validerbt.setVisible(true);

        }
        public void Modifier(ActionEvent event){
            locateur.setId(idtxt);
            locateur.setNom(nomtxt.getText());
            locateur.setNumCarte(carttxt.getText());
            locateur.setTel(teltxt.getText());
            if(nomtxt.getText().equals("")|| carttxt.getText().equals("")||teltxt.getText().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerte");
                alert.setContentText("Veuillez remplire tous les chemps");
                alert.showAndWait();
            }else
            {
                Verification(teltxt.getText());
                if (verificationtel==0 )
                {
                    ilocateur.updatLocateur(locateur);
                    load();
                    Annuler();
                }else Alerte("numero téléphone invalide");
            }
        }
        public void Supprimer(){
            boolean ok = AlerteConfi("Vous voulez vraiment supprimer ?");
            if (ok==true){
            boolean locations =Etatlocateur();
            if (locations == true)
                Alerte("Desole ! \n Ce locateur a déjà loué une voiture\n Il n'est pas possible de supprimer.");
            else {
            ilocateur.suppLocateur(idtxt);
            load();
            Annuler();
            }
           }
        }
        public void load(){
            List<Locateur> locateurList = ilocateur.listeLocateur();
            ObservableList<Locateur> observableList= FXCollections.observableArrayList();
            for (Locateur l : locateurList){
                observableList.add(l);
            }
            tablelocateur.setItems(observableList);
        }
        public void clockLingne() {
           Locateur locateur1=tablelocateur.getSelectionModel().getSelectedItem();
           if (locateur1!=null){
           modifierbt.setVisible(true);
            supprimerbt.setVisible(true);
            validerbt.setVisible(false);
            idtxt=locateur1.getId();
            nomtxt.setText(locateur1.getNom());
            carttxt.setText(locateur1.getNumCarte());
            teltxt.setText(locateur1.getTel());
            telSelect=locateur1.getTel();
           }
        }
        public void Verification(String tel){
            verificationtel=0;
            List<Locateur> locateurList=ilocateur.listeLocateur();
            if (!telSelect.equals(tel)){
                for (Locateur l : locateurList) { if (l.getTel().contains(tel)) verificationtel += 1; }
               }
        }
        public void Alerte(String message) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("alerte");
            alert.setContentText(message);
            alert.showAndWait();
        }
        public boolean AlerteConfi(String message){
            boolean confirmation = false;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText(message);
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.get() == ButtonType.OK)
                confirmation = true;

            return confirmation;
        }
        public boolean Etatlocateur() {
        boolean locations = false;
        Locateur locateur = tablelocateur.getSelectionModel().getSelectedItem();
        for (Location lo :iLocation.listeLocationRetour() ){
            if (lo.getLocateur().getId()==locateur.getId())
                locations=true;
        break;
        }
        for (Location loc :iLocation.listeLocationEtat() ){
            if (loc.getLocateur().getId()==locateur.getId())
                locations=true;
        break;
        }
        for (Location loca :iLocation.listeLocation() ){
            if (loca.getLocateur().getId()==locateur.getId())
                locations=true;
        break;
        }
        return locations;
    }


}

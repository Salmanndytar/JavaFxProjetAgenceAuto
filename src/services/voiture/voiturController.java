package services.voiture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.location.LocationController;
import sourse.classeImple.LocationImpl;
import sourse.classeImple.PlaceImpl;
import sourse.classeImple.VoitureImpl;
import sourse.classes.*;
import sourse.interphaces.ILocation;
import sourse.interphaces.IPlace;
import sourse.interphaces.IVoiture;
import sourse.outils.Outil;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class voiturController implements Initializable {
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
    private ComboBox<String>couleurcb;
    @FXML
    private ComboBox<String> energiecb;
    @FXML
    private ComboBox<String> poidscb;
    @FXML
    private ComboBox<Place> placecb;
    @FXML
    private ComboBox<String> vitessecb;
    @FXML
    private TableView<Voiture> tablevoiture;
    @FXML
    private DatePicker datetxt;

    @FXML
    private TextField matricultxt;
    @FXML
    private TextField marquetxt;
    @FXML
    private TextField modeletxt;
    @FXML
    private TextField prixtxt;




    @FXML
    private TableColumn<Voiture, String> colmatricule;
    @FXML
    private TableColumn<Voiture, String>colmarque;
    @FXML
    private TableColumn<Voiture, String>colmodele;
    @FXML
    private TableColumn<Voiture, String>colcouleur;
    @FXML
    private TableColumn<Voiture, String>colpoids;
    @FXML
    private TableColumn<Voiture, String>colenergie;
    @FXML
    private TableColumn<Voiture, String>colplace;
    @FXML
    private TableColumn<Voiture, Float>colprix;
    @FXML
    private TableColumn<Voiture, String>coldate;
    @FXML
    private TableColumn<Voiture, String>coldisfonction;
    @FXML
    private TableColumn<Voiture, String>coletat;

       int    verificationtel;
       String matriculeSelect="";
       int    Idvoiture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colmatricule.setCellValueFactory(new PropertyValueFactory<>("matrcule"));
        colmarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        colmodele.setCellValueFactory(new PropertyValueFactory<>("model"));
        colcouleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
        colpoids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        colenergie.setCellValueFactory(new PropertyValueFactory<>("carburent"));
        colplace.setCellValueFactory(new PropertyValueFactory<>("place"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("dateMise"));
        coldisfonction.setCellValueFactory(new PropertyValueFactory<>("disfonction"));
        coletat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        modifierbt.setVisible(false);
        supprimerbt.setVisible(false);
        imprimerbt.setVisible(false);
        validebt.setVisible(true);
        Couleurs();
        Poids();
        Energie();
        Place();
        Voiture();
        Vitesse();
    }
    Voiture voiture= new Voiture();
    IVoiture iVoiture = new VoitureImpl();
    IPlace iplace =new PlaceImpl();
    Place place = new Place();
    ILocation iLocation =new  LocationImpl();
    public void insertion(ActionEvent event) {
        if(marquetxt.getText().equals("")
                ||marquetxt.getText().equals("")
                ||modeletxt.getText().equals("")
                ||prixtxt.getText().equals("")
                || vitessecb.getValue() == null
                || couleurcb.getValue() == null
                ||energiecb.getValue()== null
                ||placecb.getValue()== null
                ||poidscb.getValue()== null)
        { Alerte("Veuillez ramplire tous les champs");

        }else
        {
            voiture.setMatrcule(matricultxt.getText());
            voiture.setMarque(marquetxt.getText());
            voiture.setModel(modeletxt.getText());
            voiture.setPrix(Float.parseFloat(prixtxt.getText()));
            voiture.setDateMise(datetxt.getValue());
            voiture.setDisfonction(1);
            voiture.setCouleur(couleurcb.getValue());
            voiture.setCarburent(energiecb.getValue());
            voiture.setPlace(placecb.getValue());
            voiture.setPoids(poidscb.getValue());
            voiture.setVitesse(vitessecb.getValue());
            voiture.setEtat(0);
            place.setEtat(1);
            place.setCode(placecb.getValue().toString());
            Verification(matricultxt.getText());
            if (verificationtel==0 )
            {
                int v =iVoiture.addVoiture(voiture);
               if (v != 0){
                   iplace.updatPlace(place);
                    Actualiser(event);
                    Voiture();
                    Annuler();}
            }else Alerte("numero matricule invalide");
        }
    }
    public void Annuler(){
       matricultxt.setText("");
       modeletxt.setText("");
       marquetxt.setText("");
       prixtxt.setText("");

       datetxt.setValue(null);
       couleurcb.setValue(null);
       placecb.setValue(null);
       poidscb.setValue(null);
       energiecb.setValue(null);
       modifierbt.setVisible(false);
       vitessecb.setValue(null);
       supprimerbt.setVisible(false);
       imprimerbt.setVisible(false);
       validebt.setVisible(true);

    }
    public void Modifier(ActionEvent event){
        if(marquetxt.getText().equals("")
                ||marquetxt.getText().equals("")
                ||modeletxt.getText().equals("")
                ||prixtxt.getText().equals("")
                ||vitessecb.getValue() == null
                || couleurcb.getValue() == null
                ||energiecb.getValue()== null
                ||placecb.getValue()== null
                ||poidscb.getValue()== null)
        { Alerte("Veuillez ramplire tous les champs");

        }else
        {
            voiture.setId(Idvoiture);
            voiture.setMatrcule(matricultxt.getText());
            voiture.setMarque(marquetxt.getText());
            voiture.setModel(modeletxt.getText());
            voiture.setPrix(Float.parseFloat(prixtxt.getText()));
            voiture.setDateMise(datetxt.getValue());
            voiture.setVitesse(vitessecb.getValue());
            voiture.setCouleur(couleurcb.getValue());
            voiture.setCarburent(energiecb.getValue());
            voiture.setPlace(placecb.getValue());
            voiture.setPoids(poidscb.getValue());
            voiture.setEtat(tablevoiture.getSelectionModel().getSelectedItem().getEtat());
            place.setEtat(1);
            place.setCode(placecb.getValue().toString());
            Verification(matricultxt.getText());
            if (verificationtel==0 )
            {
                iVoiture.updatVoiture(voiture);
                iplace.updatPlace( place);
                boolean modification = EditPlacVoiture();
                if (modification == true){
                    place.setCode(tablevoiture.getSelectionModel().getSelectedItem().getPlace().toString());
                    place.setEtat(0);
                    iplace.updatPlace(place);
                }
                Actualiser(event);
                Voiture();
                Annuler();
            }else Alerte("numero matriculeinvalide");
        }
    }
    public void Supprimer(ActionEvent event) {
        boolean confirmation = AlertConfirmation("voulez vous vraiment supprimer");
        if (confirmation == true) {
            String etat = EtatVoit();
            if (etat == "en circulation")
                Alerte("Cette voiture est en circulation");
            else if (etat == "louee"){
                Alerte("Cette voiture a effectué des location alors elle sera disfonctionnelle");
                DisfonctionVoit();
                Actualiser(event);
            }
            else {
                iVoiture.suppVoiture(Idvoiture);
                place.setEtat(0);
                place.setCode(placecb.getValue().toString());
                iplace.updatPlace(place);
                Actualiser(event);
                Voiture();
                Annuler();
            }
        }
    }
    public void clockLingne() {
        Voiture voitures=tablevoiture.getSelectionModel().getSelectedItem();
        if (voitures != null) {

            if (voitures.getDisfonction()==1){
                modifierbt.setVisible(true);
                supprimerbt.setVisible(true);
                imprimerbt.setVisible(true);
            }else
                Alerte("Cette voiture ne fonctionne plus");
            validebt.setVisible(false);
            Idvoiture = voitures.getId();
            matriculeSelect = voitures.getMatrcule();
            matricultxt.setText(voitures.getMatrcule());
            marquetxt.setText(voitures.getMarque());
            modeletxt.setText(voitures.getModel());
            prixtxt.setText(String.valueOf(voitures.getPrix()));
            vitessecb.setValue(voitures.getVitesse());
            couleurcb.setValue(voitures.getCouleur());
            energiecb.setValue(voitures.getCarburent());
            poidscb.setValue(voitures.getPoids());
            placecb.setValue(voitures.getPlace());
            datetxt.setValue(voitures.getDateMise());

        }
    }
    public void Verification(String matricule){
        verificationtel=0;
        List<Voiture> voitureList=iVoiture.listeVoiture();
        if (!matriculeSelect.equals(matricule)){
            for (Voiture v : voitureList) {
                if (v.getMatrcule().contains(matricule)) verificationtel += 1; }}
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
    public boolean EditPlacVoiture(){
        boolean modfication = false;
        if (tablevoiture.getSelectionModel().getSelectedItem().getPlace()!=placecb.getValue())
            modfication = true;
        return modfication;
    }
    public void Couleurs(){
        ObservableList<String> couleurlistObservale= FXCollections.observableArrayList();
        List<String> couleurListe= new ArrayList<String>();
        couleurListe.add("NOIRE");
        couleurListe.add("MARRON");
        couleurListe.add("ROUGE");
        couleurListe.add("ORANGE");
        couleurListe.add("JAUNNE");
        couleurListe.add("VIOLETTE");
        couleurListe.add("BLEE");
        couleurListe.add("VERTE");
        couleurListe.add("GRISE");
        couleurListe.add("BLANCHE");
        couleurListe.add("ROSE");
        for (String c : couleurListe){
            couleurlistObservale.add(c);
        }
        couleurcb.setItems(couleurlistObservale);
    }
    public void Energie(){
        ObservableList<String> carburentlistObservale= FXCollections.observableArrayList();
        List<String> carburentListe= new ArrayList<String>();
        carburentListe.add("ESSENCE");
        carburentListe.add("GAZOIL");
        carburentListe.add("ELECTRIQUE");
        carburentListe.add("AUTRE");
        for (String c : carburentListe){
            carburentlistObservale.add(c);
        }
        energiecb.setItems(carburentlistObservale);
    }
    public void Vitesse(){
        ObservableList<String> vitesselistObservale= FXCollections.observableArrayList();
        List<String> vitesseListe= new ArrayList<String>();
        vitesseListe.add("AUTOMATIQUE");
        vitesseListe.add("MANUELLE");
        for (String v : vitesseListe){
            vitesselistObservale.add(v);
        }
        vitessecb.setItems(vitesselistObservale);
    }
    public void Poids(){
        ObservableList<String> poidslistObservale= FXCollections.observableArrayList();
        List<String> poidsListe= new ArrayList<String>();
        poidsListe.add("LEGE");
        poidsListe.add("LOURD");
        for (String p : poidsListe){
            poidslistObservale.add(p);
        }
        poidscb.setItems(poidslistObservale);
    }
    public void Place(){
        IPlace place =new PlaceImpl();
        ObservableList<Place> placelistObservale= FXCollections.observableArrayList();
        List<Place> placeList= place.listePlaceEtat();
        for (Place p : placeList){
            placelistObservale.add(p);
        }
        placecb.setItems(placelistObservale);
    }
    public void Voiture(){
        ObservableList<Voiture> voiturelistObservale= FXCollections.observableArrayList();
        List<Voiture> voitureList= iVoiture.listeVoiture();
        for (Voiture v : voitureList){
            voiturelistObservale.add(v);
        }
        tablevoiture.setItems(voiturelistObservale);
    }
    public void Actualiser(ActionEvent event){
        ((Node)event.getSource()).getScene().getWindow().hide();
        try {
            Outil.load(event, "Gestion vehicule", "/services/voiture/Voiture.fxml");
        }catch (Exception ex){ex.printStackTrace();}

    }
    public String EtatVoit() {
        String etat = "non louee";
        Voiture v = tablevoiture.getSelectionModel().getSelectedItem();
        if (v.getEtat()==1)
            etat = "en circulation";
        for (Location l :iLocation.listeLocationRetour() )
            if (l.getVoiture().getId()==v.getId())
                etat = "louee";
        return etat;
    }
    public void  DisfonctionVoit(){
        //rendre l'etat=0 pour la voiture liberee par modification
        Voiture v = tablevoiture.getSelectionModel().getSelectedItem();
        voiture.setId( v.getId());
       voiture.setDisfonction(0);
        voiture.setEtat(1);
        place.setEtat(0);
        place.setCode(placecb.getValue().toString());
        iplace.updatPlace(place);
        iVoiture.disfonctVoiture(voiture);
    }
}

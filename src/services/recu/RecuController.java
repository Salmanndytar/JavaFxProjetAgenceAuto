package services.recu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sourse.classeImple.FactureImpl;
import sourse.classeImple.RecuImpl;
import sourse.classes.Facture;
import sourse.classes.Recu;
import sourse.interphaces.IFacture;
import sourse.interphaces.IRecu;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecuController implements Initializable {

    @FXML
    private Button imprimerbt;
    @FXML
    private int id;

    @FXML
    private TableView<Recu> tableplRecu;
    @FXML
    private TableColumn<Recu, Integer> colid;
    @FXML
    private TableColumn<Recu, String>colmontantv;
    @FXML
    private TableColumn<Recu, String>colmontantr;
    @FXML
    private TableColumn<Recu, String>colduree;
    @FXML
    private TableColumn<Recu, String>collocation;
    @FXML
    private TableColumn<Recu, String>colretoure;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colmontantv.setCellValueFactory(new PropertyValueFactory<>("montantVerse"));
        colmontantr.setCellValueFactory(new PropertyValueFactory<>("montantRestant"));
        colduree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        collocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colretoure.setCellValueFactory(new PropertyValueFactory<>("retoure"));

        imprimerbt.setVisible(false);
        ChargeRecu();
    }

   IRecu iRecu = new RecuImpl();

    public void Annuler(){
        imprimerbt.setVisible(false);
    }
    public void clockLingne() {
       Recu recu=  tableplRecu.getSelectionModel().getSelectedItem();
        if (recu != null){
            imprimerbt.setVisible(true);
            id=recu.getId();
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
        ObservableList<Recu> recusObservableList = FXCollections.observableArrayList();
        List<Recu> recuListt = iRecu.listeRecu().stream().collect(Collectors.toList());
        for (Recu r: recuListt){
            if (r.getLocation().getValide()==1)
                recusObservableList.add(r);
        }
        tableplRecu.setItems(recusObservableList);
    }
}

package sourse.outils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


public class Outil {
    public static String login;
   public static String pass;
  private void loadPage(ActionEvent event, String title, String url) throws IOException{
      ((Node) event.getSource()).getScene().getWindow().hide();
      Parent root = FXMLLoader.load(getClass().getResource(url));
      Scene scene = new Scene(root);
      Stage stage =new Stage();
      stage.setScene(scene);
      stage.setTitle(title);
      stage.show();
  }
    private void loadSubPage(ActionEvent event, String title, String url) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        Stage stage =new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.showAndWait();
    }
  public  static void  load(ActionEvent event, String title, String url) throws IOException{
      new Outil().loadPage(event, title, url);
  }
    public  static void  loadSub(ActionEvent event, String title, String url) throws IOException{
        new Outil().loadSubPage(event, title, url);
    }

    public static int LesMois(int datem, int datea){
        int j=0;
        if ( datem <= 7 && datem%2 ==1 || datem > 7 && datem%2 ==0){ j =31;
        }
        else if (datea%4==0 && datem==2){ j=29;
        }else if (datea%4 !=0 && datem==2){j =28;
        }else j=30;

        return  j;
    }
    public static int CalculJours(LocalDate dateD, LocalDate dateF){

        int moiD = dateD.getMonthValue();
        int anneEncour= dateF.getYear();
        int nbanne=  dateUtils.getYearsBetweenDat(dateD,dateF);
        nbanne= nbanne*365;
        int intervaljour= dateUtils.getDaysBetweenDate(dateD,dateF);
        int intervalMoi= dateUtils.getMonthsBetweenDate(dateD,dateF);
        int NbjourDemoi=0;
        if (intervalMoi!=0) {
            while (intervalMoi > 0) {
                NbjourDemoi = NbjourDemoi + LesMois(moiD,anneEncour);
                intervalMoi = intervalMoi - 1;
                moiD = moiD +1;
            }
        }
        return intervaljour+NbjourDemoi+nbanne;
    }
    public static int chrono(int second){
        int s=0;
        while(s!=second)
        { s=s+1;
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        return s;
    }



}

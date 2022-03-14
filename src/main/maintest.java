package main;

import sourse.classeImple.LocationImpl;
import sourse.classeImple.LocatuerImpl;
import sourse.classeImple.VoitureImpl;
import sourse.classes.Locateur;
import sourse.classes.Location;
import sourse.classes.Voiture;
import sourse.interphaces.ILocateur;
import sourse.interphaces.ILocation;
import sourse.interphaces.IVoiture;
import sourse.outils.Outil;
import sourse.outils.dateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class maintest {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System .in);
        System.out.println("entre le nombre de jour");
        int nbr= sc.nextInt();
       /* int  datej= LocalDate.now().getDayOfMonth();
        int  datem= LocalDate.now().getMonthValue();
        int  datea= LocalDate.now().getYear();
        datej +=nbr;
        int j=moi(datem,datea);
        while (datej>j){
            datej=datej-j;
            datem +=1;
            while (datem>12){
                datem=datem-12;
                datea +=1;
            }
        }*/
         int s= chrono(nbr);
         if (s==nbr) System.out.println("la duree est fini !!!");









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

   public static int LesMois(int datem, int datea){
        int j=0;
       if ( datem <= 7 && datem%2 ==1 || datem > 7 && datem%2 ==0){ j =31;
       }
       else if (datea%4==0 && datem==2){ j=29;
       }else if (datea%4 !=0 && datem==2){j =28;
       }else j=30;

       return  j;
   }
   public static int CalculJours(int nbjour){
       LocalDate dateD = LocalDate.now();
       LocalDate dateF = LocalDate.now().plusDays(nbjour);
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



}

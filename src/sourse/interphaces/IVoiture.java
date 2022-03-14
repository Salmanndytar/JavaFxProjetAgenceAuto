package sourse.interphaces;

import sourse.classes.Voiture;

import java.util.List;

public interface IVoiture {
    public Voiture retourneVoiture(int id);
    public int suppVoiture(int id);
    public int addVoiture(Voiture voiture);
    public  int updatVoiture(Voiture voiture);
    public List<Voiture> listeVoiture();
    public List<Voiture> listeVoitureEtat();
    public int disfonctVoiture(Voiture voiture);
}

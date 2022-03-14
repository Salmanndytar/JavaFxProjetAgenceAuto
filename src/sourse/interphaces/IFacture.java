package sourse.interphaces;



import sourse.classes.Facture;

import java.util.List;

public interface IFacture {
    public Facture getFacturer(int id);
    public int suppFacture(int id);
    public int addFacture(Facture facture);
    public  int updatFacture(Facture facturer);
    public List<Facture> listeFacture();
}

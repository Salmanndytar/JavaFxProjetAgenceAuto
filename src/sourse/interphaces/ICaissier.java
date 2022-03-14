package sourse.interphaces;

import sourse.classes.Caissier;

import java.util.List;

public interface ICaissier {
    public Caissier getCaissier(int id);
    public int suppCaissier(int id);
    public int addCaissier(Caissier caissier);
    public  int updatCaissier(Caissier caissier);
    public List<Caissier> listeCaissier();
    public List<Caissier> listeCaissierEtat();
}

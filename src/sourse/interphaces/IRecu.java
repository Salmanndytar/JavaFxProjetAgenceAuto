package sourse.interphaces;



import sourse.classes.Recu;

import java.util.List;

public interface IRecu {
    public Recu getRecu (int id);
    public int suppRecu (int id);
    public int addRecu (Recu  recu);
    public  int updatRecu (Recu  recu);
    public  int ValideRecu(Recu  recu);
    public List<Recu > listeRecu ();
}

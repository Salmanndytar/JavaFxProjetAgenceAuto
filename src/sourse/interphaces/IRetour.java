package sourse.interphaces;


import sourse.classes.Retoure;

import java.util.List;

public interface IRetour {
    public Retoure getRetoure (int id);
    public int suppRetoure (int id);
    public int addRetoure (Retoure retoure);
    public  int updatRetoure (Retoure retoure);
    public int ValideRetoure(Retoure retoure);
    public List<Retoure> listeRetoure ();
    public List<Retoure> listeRetoureEtat();
}

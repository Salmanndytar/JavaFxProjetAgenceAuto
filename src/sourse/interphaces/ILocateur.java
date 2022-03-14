package sourse.interphaces;



import sourse.classes.Locateur;

import java.util.List;

public interface ILocateur {
    public Locateur getLocateur(int id);
    public int suppLocateur(int id);
    public int addLocateur(Locateur locateur);
    public  int updatLocateur(Locateur locateur);
    public List<Locateur> listeLocateur();

}

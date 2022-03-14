package sourse.interphaces;

import sourse.classes.User;

import java.util.List;

public interface IUser {
    public User getConnection(String email, String password);
    public User getUser(int id);
    public int suppUser(int id);
    public int adduserAg(User user);
    public int adduserAd(User user);
    public int adduserCa(User user);
    public  int updatUser(User user);

    public List<User>listeUserAg();
    public List<User>listeUserC();
    public List<User>listeUserA();
    public List<User>listeUserComplet();




}

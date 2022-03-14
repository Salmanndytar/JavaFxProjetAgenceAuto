package sourse.interphaces;



import sourse.classes.Admin;

import java.util.List;

public interface IAdmin {
    public Admin getAdmin(int id);
    public int suppAdmin(int id);
    public int addAdmin(Admin admin);
    public  int updatAdmin(Admin admin);
    public List<Admin> listeAdmin();
    public List<Admin> listeAdminEtat();
}

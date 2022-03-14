package sourse.classeImple;

import admin.ControlAdmin;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sourse.classes.Admin;
import sourse.interphaces.IAdmin;
import sourse.outils.DB;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminImpl implements IAdmin {
    DB db =new DB();


    @Override
    public Admin getAdmin(int id) {
        Admin admin=null;

        try {
            String sql = "SELECT * FROM admin WHERE id= ?";
            db.preparedStatement(sql);
            ////DriverManager.getConnection("jdbc:mysql://localhost:3306/eden","root","").preparedStatement("SELECT * FROM admin WHERE id= ?")
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                admin = new Admin();
                admin.setId(rs.getInt(1));
                admin.setNom(rs.getString(2));
                admin.setPrenom(rs.getString(3));
                admin.setTel(rs.getString(4));
                admin.setEtat(rs.getInt(6));
              /*  InputStream is = rs.getBinaryStream(6);
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[] contents = new byte[1024];
                int size = 0;
                while ((size = is.read(contents)) != -1){
                    os.write(contents,0,size);
                }
                admin.setImage(new Image("file:photo.jpg"));*/


            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return admin;
    }
    @Override
    public int suppAdmin(int id) {
        int ok=0;
        String sql = "DELETE FROM admin WHERE id= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int addAdmin(Admin admin) {
        int insert=0;
        try
        {
            String sql="INSERT INTO admin VALUES(NULL, ?, ?, ?, ?,NULL)";

            db.preparedStatement(sql);
            db.getPstmt().setString(1,admin.getNom());
            db.getPstmt().setString(2,admin.getPrenom());
            db.getPstmt().setString(3,admin.getTel());
            db.getPstmt().setInt(4,admin.getEtat());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatAdmin(Admin admin) {
        int edite=0;
        try
        {
            String sql="UPDATE admin SET nom = ?,prenom = ?,tel= ?,etat = ? WHERE id  = ?";

            db.preparedStatement(sql);

            db.getPstmt().setString(1,admin .getNom());
            db.getPstmt().setString(2,admin .getPrenom());
            db.getPstmt().setString(3,admin .getTel());
            db.getPstmt().setInt(4, admin .getEtat());
            db.getPstmt().setInt(5,admin .getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Admin> listeAdmin() {
        String sql="SELECT * FROM admin ";
        List<Admin>adminList= new ArrayList<Admin>();

        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {

                Admin admin = new Admin();
                admin.setId(rs.getInt(1));
                admin.setNom(rs.getString(2));
                admin.setPrenom(rs.getString(3));
                admin.setTel(rs.getString(4));
                admin.setEtat(rs.getInt(6));
                adminList.add(admin);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return adminList;
    }
    @Override
    public List<Admin> listeAdminEtat() {
        String sql="SELECT a.id,a.nom,a.prenom,a.tel,a.etat FROM admin a WHERE etat=0";
        List<Admin>adminList= new ArrayList<Admin>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Admin admin = new Admin();
                admin.setId(rs.getInt("a.id"));
                admin.setNom(rs.getString("a.nom"));
                admin.setPrenom(rs.getString("a.prenom"));
                admin.setTel(rs.getString("a.tel"));
                admin.setEtat(rs.getInt("a.etat"));
                adminList.add(admin);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return adminList;
    }
}

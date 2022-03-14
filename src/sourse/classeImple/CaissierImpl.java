package sourse.classeImple;

import sourse.classes.Caissier;
import sourse.interphaces.ICaissier;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CaissierImpl implements ICaissier {
    DB db =new DB();
    @Override
    public Caissier getCaissier(int id) {
        Caissier caissier = null;

        try {
            String sql = "SELECT * FROM caissier WHERE id_caissier= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                caissier = new Caissier();
                caissier.setId(rs.getInt(1));
                caissier.setNom(rs.getString(2));
                caissier.setPrenom(rs.getString(3));
                caissier.setTel(rs.getString(4));
                caissier.setEtat(rs.getInt(5));

            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return caissier;
    }
    @Override
    public int suppCaissier(int id) {
        int ok=0;
        String sql = "DELETE FROM caissier WHERE id_caissier= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int addCaissier(Caissier caissier) {
        int insert=0;
        try
        {
            String sql="INSERT INTO caissier VALUES(NULL, ?, ?, ?, ?,NULL)";

            db.preparedStatement(sql);
            db.getPstmt().setString(1,caissier.getNom());
            db.getPstmt().setString(2,caissier.getPrenom());
            db.getPstmt().setString(3,caissier.getTel());
            db.getPstmt().setInt(4,caissier.getEtat());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatCaissier(Caissier caissier) {
        int edite=0;
        try
        {
            String sql="UPDATE caissier SET nom_caissier  = ?,prenom_caissier  = ?,tel= ?,etat = ? WHERE id_caissier  = ?";

            db.preparedStatement(sql);

            db.getPstmt().setString(1,caissier .getNom());
            db.getPstmt().setString(2,caissier .getPrenom());
            db.getPstmt().setString(3,caissier .getTel());
            db.getPstmt().setInt(4, caissier .getEtat());
            db.getPstmt().setInt(5,caissier .getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Caissier> listeCaissier() {
        String sql="SELECT * FROM caissier ";
        List<Caissier>caissierList= new ArrayList<Caissier>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Caissier caissier = new Caissier();
                caissier.setId(rs.getInt(1));
                caissier.setNom(rs.getString(2));
                caissier.setPrenom(rs.getString(3));
                caissier.setTel(rs.getString(4));
                caissierList.add(caissier);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return caissierList;
    }
    @Override
    public List<Caissier> listeCaissierEtat() {
        String sql="SELECT * FROM caissier WHERE (etat=0 )";
        List<Caissier>caissierList= new ArrayList<Caissier>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Caissier caissier = new Caissier();
                caissier.setId(rs.getInt(1));
                caissier.setNom(rs.getString(2));
                caissier.setPrenom(rs.getString(3));
                caissier.setTel(rs.getString(4));
                caissierList.add(caissier);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return caissierList;
    }
}


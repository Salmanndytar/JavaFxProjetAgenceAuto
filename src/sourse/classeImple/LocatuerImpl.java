package sourse.classeImple;

import sourse.classes.Locateur;
import sourse.interphaces.ILocateur;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocatuerImpl implements ILocateur {
    DB db = new DB();
    Locateur locateur = new Locateur();
    @Override
    public Locateur getLocateur(int id) {
        Locateur locateur = null ;
        String sql = "SELECT * FROM locateur WHERE id_locateur= ?";
        db.preparedStatement(sql);

        try {
            db.getPstmt().setInt(1,id);
            ResultSet rs = db.executeQuery();
            if (rs.next()){
                locateur = new Locateur();
                locateur.setId(rs.getInt(1));
                locateur.setNumCarte(rs.getString(2));
                locateur.setNom(rs.getString(3));
                locateur.setTel(rs.getString(4));

            }

        }catch (Exception ex){ex.printStackTrace();}
        return locateur;
    }

    @Override
    public int suppLocateur(int id) {
        int ok=0;
        String sql = "DELETE FROM locateur WHERE id_locateur= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }

    @Override
    public int addLocateur(Locateur locateur) {
        int ok=0;
        String sql=" INSERT INTO locateur VALUES(NULL, ?, ?, ? )";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setString(1,locateur.getNumCarte());
            db.getPstmt().setString(2, locateur.getNom());
            db.getPstmt().setString(3, locateur.getTel());
            ok=db.executUpdate(sql);
        }catch (Exception e){e.printStackTrace();}
        return ok;
    }

    @Override
    public int updatLocateur(Locateur locateur) {
        int ok=0;
        String sql="UPDATE locateur SET num_carte = ?,nom = ?,tel= ? WHERE id_locateur = ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setString(1,locateur.getNumCarte());
            db.getPstmt().setString(2, locateur.getNom());
            db.getPstmt().setString(3, locateur.getTel());
            db.getPstmt().setInt(4,locateur.getId());
            ok=db.executUpdate(sql);
        }catch (Exception e){e.printStackTrace();}
        return ok;
    }

    @Override
    public List<Locateur> listeLocateur() {
        String sql="SELECT * FROM locateur";
        List<Locateur>locateurList= new ArrayList<Locateur>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next()) {
               Locateur locateur =new Locateur();
                locateur.setId(rs.getInt(1));
                locateur.setNumCarte(rs.getString(2));
                locateur.setNom(rs.getString(3));
                locateur.setTel(rs.getString(4));
                locateurList.add(locateur);
            }
        }catch (Exception ex){ex.printStackTrace();}
        return locateurList;
    }
}

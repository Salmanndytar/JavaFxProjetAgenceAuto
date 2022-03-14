package sourse.classeImple;

import sourse.classes.Voiture;
import sourse.interphaces.IVoiture;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VoitureImpl implements IVoiture {
    DB db =new DB();
    @Override
    public Voiture retourneVoiture(int id) {
       Voiture voiture = null;

        try {
            String sql = "SELECT * FROM voiture WHERE id_voiture= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                voiture = new Voiture();
                voiture.setId(rs.getInt(1));
                voiture.setMatrcule(rs.getString(2));
                voiture.setMarque(rs.getString(3));
                voiture.setModel(rs.getString(4));
                voiture.setCouleur(rs.getString(5));
                voiture.setCarburent(rs.getString(6));
                voiture.setPoids(rs.getString(7));
                //voiture.setVitesse(rs.getString(8));
                voiture.setDateMise(LocalDate.parse(rs.getString(8)));
                voiture.setPlace(new PlaceImpl().getLPlace(rs.getInt(9)));
                voiture.setPrix(rs.getFloat(10));
                voiture.setDisfonction(rs.getInt(11));
                voiture.setEtat(rs.getInt(12));


            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return voiture;
    }
    @Override
    public int suppVoiture(int id) {
        int ok=0;
        String sql = "DELETE FROM voiture WHERE id_voiture= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int addVoiture(Voiture voiture) {
        int insert=0;
        try
        {
            String sql="INSERT INTO voiture VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,NULL )";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,voiture.getMatrcule());
            db.getPstmt().setString(2,voiture.getMarque());
            db.getPstmt().setString(3,voiture.getModel());
            db.getPstmt().setString(4,voiture.getCouleur());
            db.getPstmt().setString(5,voiture.getCarburent());
            db.getPstmt().setString(6,voiture.getPoids());
            db.getPstmt().setString(7,voiture.getVitesse());
            db.getPstmt().setString(8,voiture.getDateMise().toString());
            db.getPstmt().setInt(9,voiture.getPlace().getId());
            db.getPstmt().setFloat(10,voiture.getPrix());
            db.getPstmt().setInt(11,voiture.getEtat());
            db.getPstmt().setInt(12,voiture.getDisfonction());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatVoiture(Voiture voiture) {
        int edite=0;
        try
        {
            String sql="UPDATE voiture SET matricule =?,marque = ?,model= ?,couleur = ?,carburent= ?,poids= ? ,vitesse= ?,dateSircul= ?,idPlace= ?,prixLocation= ?,etat= ? WHERE id_voiture  = ?";

            db.preparedStatement(sql);

            db.getPstmt().setString(1,voiture.getMatrcule());
            db.getPstmt().setString(2,voiture.getMarque());
            db.getPstmt().setString(3,voiture.getModel());
            db.getPstmt().setString(4,voiture.getCouleur());
            db.getPstmt().setString(5,voiture.getCarburent());
            db.getPstmt().setString(6,voiture.getPoids());
           // db.getPstmt().setString(7,voiture.getVitesse());
            db.getPstmt().setString(7,voiture.getDateMise().toString());
            db.getPstmt().setInt(8,voiture.getPlace().getId());
            db.getPstmt().setFloat(9,voiture.getPrix());
            db.getPstmt().setInt(10,voiture.getEtat());
            db.getPstmt().setInt(11,voiture.getId());

            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public int disfonctVoiture(Voiture voiture) {
        int edite=0;
        try
        {
            String sql="UPDATE voiture SET disfonction = ?,etat= ? WHERE id_voiture  = ?";

            db.preparedStatement(sql);

            db.getPstmt().setFloat(1,voiture.getDisfonction());
            db.getPstmt().setInt(2,voiture.getEtat());
            db.getPstmt().setInt(3,voiture.getId());

            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Voiture> listeVoiture() {
        String sql="SELECT * FROM voiture ";
        List<Voiture>voitureList= new ArrayList<Voiture>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Voiture voiture = new Voiture();
                voiture.setId(rs.getInt(1));
                voiture.setMatrcule(rs.getString(2));
                voiture.setMarque(rs.getString(3));
                voiture.setModel(rs.getString(4));
                voiture.setCouleur(rs.getString(5));
                voiture.setCarburent(rs.getString(6));
                voiture.setPoids(rs.getString(7));
               // voiture.setVitesse(rs.getString(8));//jai oublier ds la base
                voiture.setDateMise(LocalDate.parse(rs.getString(8)));
                voiture.setPlace(new PlaceImpl().getLPlace(rs.getInt(9)));
                voiture.setPrix(rs.getFloat(10));
                voiture.setEtat(rs.getInt(11));
                voiture.setDisfonction(rs.getInt(12));
                voitureList.add(voiture);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return voitureList;
    }
    @Override
    public List<Voiture> listeVoitureEtat() {
        String sql="SELECT * FROM voiture  WHERE etat=0 ";
        List<Voiture>voitureList= new ArrayList<Voiture>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Voiture voiture = new Voiture();
                voiture.setId(rs.getInt(1));
                voiture.setMatrcule(rs.getString(2));
                voiture.setMarque(rs.getString(3));
                voiture.setModel(rs.getString(4));
                voiture.setCouleur(rs.getString(5));
                voiture.setCarburent(rs.getString(6));
                voiture.setPoids(rs.getString(7));
                //voiture.setVitesse(rs.getString(8)); j ai oublier dans la base
                voiture.setDateMise(LocalDate.parse(rs.getString(8)));
                voiture.setPlace(new PlaceImpl().getLPlace(rs.getInt(9)));
                voiture.setPrix(rs.getFloat(10));
                voiture.setEtat(rs.getInt(11));
                voiture.setDisfonction(rs.getInt(12));
                voitureList.add(voiture);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return voitureList;
    }
}

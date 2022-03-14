package sourse.classeImple;

import sourse.classes.Recu;
import sourse.interphaces.IRecu;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecuImpl implements IRecu {
    DB db =new DB();
    @Override
    public Recu getRecu(int id) {
        Recu recu = null;
        try {
            String sql = "SELECT * FROM recu WHERE id_recu= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                recu = new Recu();
                recu.setId(rs.getInt(1));
                recu.setMontantVerse(rs.getFloat(2));
                recu.setMontantRestant(rs.getFloat(3));
                recu.setDuree(rs.getInt(4));
                recu.setLocation(new LocationImpl().getLocation(rs.getInt(5)));
                recu.setRetoure(new RetourImpl().getRetoure(rs.getInt(6)));

            }
        }
        catch (Exception ex){ex.printStackTrace();}
        return recu;
    }
    @Override
    public int suppRecu(int id) {
        int ok=0;
        String sql = "DELETE FROM recu WHERE location= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}
        return ok;
    }
    @Override
    public int addRecu(Recu recu) {
        int insert=0;
        try
        {
            String sql="INSERT INTO recu VALUES(NULL, ?, ?, ?, ?, NULL)";
            db.preparedStatement(sql);
            db.getPstmt().setFloat(1,recu.getMontantVerse());
            db.getPstmt().setFloat(2,recu.getMontantRestant());
            db.getPstmt().setInt(3,recu.getDuree());
            db.getPstmt().setInt(4,recu.getLocation().getId());

            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int ValideRecu(Recu recu) {
        int edite=0;
        try
        {
            String sql="UPDATE recu SET retour = ? WHERE location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,recu.getRetoure().getId());
            db.getPstmt().setInt(2,recu.getLocation().getId());//modifier % id location
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public int updatRecu(Recu recu) {
        int edite=0;
        try
        {
            String sql="UPDATE recu SET retour=NULL, montant_verse= ?,montant_restant= ?,duree= ?,location = ? WHERE location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setFloat(1,recu.getMontantVerse());
            db.getPstmt().setFloat(2,recu.getMontantRestant());
            db.getPstmt().setInt(3,recu.getDuree());
            db.getPstmt().setInt(4,recu.getLocation().getId());
            db.getPstmt().setInt(5,recu.getLocation().getId());//modifier % id location
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Recu> listeRecu() {
        String sql="SELECT * FROM recu ";
        List<Recu>recuList= new ArrayList<Recu>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Recu recu = new Recu();
                recu.setId(rs.getInt(1));
                recu.setMontantVerse(rs.getFloat(2));
                recu.setMontantRestant(rs.getFloat(3));
                recu.setDuree(rs.getInt(4));
                recu.setLocation(new LocationImpl().getLocation(rs.getInt(5)));
                recu.setRetoure(new RetourImpl().getRetoure(rs.getInt(6)));
                recuList.add(recu);
            }
        }catch (Exception ex){ex.printStackTrace();}
        return recuList;
    }
}

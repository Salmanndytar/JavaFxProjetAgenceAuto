package sourse.classeImple;

import sourse.classes.Retoure;
import sourse.interphaces.IRetour;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RetourImpl implements IRetour {
    DB db =new DB();
    @Override
    public Retoure getRetoure(int id) {
        Retoure retoure = null;
        try {
            String sql = "SELECT * FROM retoure WHERE id_retoure= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                retoure = new Retoure();
                retoure.setId(rs.getInt(1));
                retoure.setDate(LocalDate.parse(rs.getString(2)));
                retoure.setLocation(new LocationImpl().getLocation(rs.getInt(3)));
                retoure.setValide(rs.getInt(4));
                retoure.setCaissier(new CaissierImpl().getCaissier(rs.getInt(5)));
                retoure.setAgent(new AgentImpl().getAgent(rs.getInt(6)));
            }
        }
        catch (Exception ex){ex.printStackTrace();}
        return retoure;
    }
    @Override
    public int suppRetoure(int id) {
        int ok=0;
        String sql = "DELETE FROM retoure WHERE location= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}
        return ok;
    }
    @Override
    public int addRetoure(Retoure retoure) {
        int insert=0;
        try
        {
            String sql="INSERT INTO retoure VALUES(NULL, ?, ?, ?, ?, ?)";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,retoure.getDate().toString());
            db.getPstmt().setInt(2,retoure.getLocation().getId());
            db.getPstmt().setInt(3,retoure.getValide());
            db.getPstmt().setInt(4,retoure.getCaissier().getId());
            db.getPstmt().setInt(5,retoure.getAgent().getId());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int ValideRetoure(Retoure retoure) {
        int edite=0;
        try
        {
            String sql="UPDATE retoure SET valide= ?,caissier= ? WHERE location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,retoure.getValide());
            db.getPstmt().setInt(2,retoure.getCaissier().getId());
            db.getPstmt().setInt(3,retoure.getLocation().getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public int updatRetoure(Retoure retoure) {
        int edite=0;
        try
        {
            String sql="UPDATE retoure SET date_retoure= ?,location= ? WHERE id_retoure= ?";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,retoure.getDate().toString());
            db.getPstmt().setInt(2,retoure.getLocation().getId());
            db.getPstmt().setInt(3,retoure.getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Retoure> listeRetoure() {
        String sql="SELECT * FROM retoure WHERE valide = 1";
        List<Retoure>retoureList= new ArrayList<Retoure>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Retoure retoure = new Retoure();
                retoure.setId(rs.getInt(1));
                retoure.setDate(LocalDate.parse(rs.getString(2)));
                retoure.setLocation(new LocationImpl().getLocation(rs.getInt(3)));
                retoure.setValide(rs.getInt(4));
                retoure.setCaissier(new CaissierImpl().getCaissier(rs.getInt(5)));
                retoure.setAgent(new AgentImpl().getAgent(rs.getInt(6)));
                retoureList.add(retoure);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return retoureList;
    }
    @Override
    public List<Retoure> listeRetoureEtat() {
        String sql="SELECT * FROM retoure WHERE valide=0 ";
        List<Retoure>retoureList= new ArrayList<Retoure>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Retoure retoure = new Retoure();
                retoure.setId(rs.getInt(1));
                retoure.setDate(LocalDate.parse(rs.getString(2)));
                retoure.setLocation(new LocationImpl().getLocation(rs.getInt(3)));
                retoure.setValide(rs.getInt(4));
                retoure.setCaissier(new CaissierImpl().getCaissier(rs.getInt(5)));
                retoure.setAgent(new AgentImpl().getAgent(rs.getInt(6)));
                retoureList.add(retoure);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return retoureList;
    }
}

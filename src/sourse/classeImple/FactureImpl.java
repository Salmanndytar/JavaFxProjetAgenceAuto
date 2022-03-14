package sourse.classeImple;

import sourse.classes.Facture;
import sourse.interphaces.IFacture;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FactureImpl implements IFacture {
    DB db = new DB();
    @Override
    public Facture getFacturer(int id) {
        Facture facture = null;
        try {
            String sql = "SELECT * FROM facture WHERE id_facture= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                facture = new Facture();
                facture.setId(rs.getInt(1));
                facture.setDate(LocalDate.parse(rs.getString(2)));
                facture.setPenalite(rs.getInt(3));
                facture.setMontant(rs.getFloat(4));
                facture.setRetoure(new RetourImpl().getRetoure(rs.getInt(5)));

            }
        }
        catch (Exception ex){ex.printStackTrace();}
        return facture;
    }

    @Override
    public int suppFacture(int id) {
        int ok=0;
        String sql = "DELETE FROM facture WHERE id_facture= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}
        return ok;
    }

    @Override
    public int addFacture(Facture facture) {
        int insert=0;
        try
        {
            String sql="INSERT INTO facture VALUES(NULL, ?, ?, ?, ?)";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,facture.getDate().toString());
            db.getPstmt().setFloat(2,facture.getPenalite());
            db.getPstmt().setFloat(3,facture.getMontant());
            db.getPstmt().setInt(4,facture.getRetoure().getId());

            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }

    @Override
    public int updatFacture(Facture facture){
        int edite=0;
        try
        {
            String sql="UPDATE facture SET date_facture= ?,penalite= ?,montant_total= ?,id_retoure= ? WHERE id_facture= ?";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,facture.getDate().toString());
            db.getPstmt().setFloat(2,facture.getPenalite());
            db.getPstmt().setFloat(3,facture.getMontant());
            db.getPstmt().setInt(4,facture.getRetoure().getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }

    @Override
    public List<Facture> listeFacture() {
        String sql="SELECT * FROM facture ";
        List<Facture>factureList= new ArrayList<Facture>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Facture facture = new Facture();
                facture.setId(rs.getInt(1));
                facture.setDate(LocalDate.parse(rs.getString(2)));
                facture.setPenalite(rs.getInt(3));
                facture.setMontant(rs.getFloat(4));
                facture.setRetoure(new RetourImpl().getRetoure(rs.getInt(5)));
                factureList.add(facture);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return factureList;
    }
}

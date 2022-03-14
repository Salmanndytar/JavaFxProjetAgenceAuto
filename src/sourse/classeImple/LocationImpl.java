package sourse.classeImple;

import sourse.classes.Location;
import sourse.interphaces.ILocation;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocationImpl implements ILocation {
    DB db = new DB();
    @Override
    public Location getLocation(int id) {
        Location location = null;

        try {
            String sql = "SELECT * FROM location WHERE id_location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                location = new Location();
                location.setId(rs.getInt(1));
                location.setDateD(LocalDate.parse(rs.getString(2)));
                location.setDateF(LocalDate.parse(rs.getString(3)));
                location.setVoiture(new VoitureImpl().retourneVoiture(rs.getInt(4)));
                location.setLocateur(new LocatuerImpl().getLocateur(rs.getInt(5)));
                location.setCaissier(new CaissierImpl().getCaissier(rs.getInt(6)));
                location.setAgents(new AgentImpl().getAgent(rs.getInt(7)));
                location.setValide(rs.getInt(8));
                location.setDateV(LocalDate.parse(rs.getString(9)));

            }
        }
        catch (Exception ex){ex.printStackTrace();}
 
        return location;
    }
    @Override
    public int suppLocation(int id) {
        int ok=0;
        String sql = "DELETE FROM location WHERE id_location= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int addLocation(Location location) {
        int insert=0;
        try
        {
            String sql="INSERT INTO location VALUES(NULL, ?, ?, ?, ?, NULL, ?, ?, NULL)";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,location.getDateD().toString());
            db.getPstmt().setString(2,location.getDateF().toString());
            db.getPstmt().setInt(3,location.getVoiture().getId());
            db.getPstmt().setInt(4,location.getLocateur().getId());
            db.getPstmt().setInt(5,location.getAgents().getId());
            db.getPstmt().setInt(6, location.getValide());


            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatLocation(Location location) {
        int insert=0;
        try
        {
            String sql="UPDATE location SET date_debut= ?, date_fin= ?, voiture= ?,locateur= ?,agent= ? WHERE id_location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,location.getDateD().toString());
            db.getPstmt().setString(2,location.getDateF().toString());
            db.getPstmt().setInt(3,location.getVoiture().getId());
            db.getPstmt().setInt(4,location.getLocateur().getId());

            db.getPstmt().setInt(5,location.getAgents().getId());
            db.getPstmt().setInt(6, location.getId());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }

    @Override
    public int ValiderLocation(Location location) {
        int insert=0;
        try
        {
            String sql="UPDATE location SET caissier= ?, date_valide= ?,valide= ? WHERE id_location= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,location.getCaissier().getId());
            db.getPstmt().setString(2,location.getDateV().toString());
            db.getPstmt().setInt(3,location.getValide());;
            db.getPstmt().setInt(4, location.getId());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }

    @Override
    public List<Location> listeLocation() {
        String sql="SELECT location.* FROM location WHERE id_location not in(SELECT location FROM retoure) AND date_valide is not null";
        List<Location>locationList= new ArrayList<Location>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Location location= new Location();
                location.setId(rs.getInt(1));
                location.setDateD(LocalDate.parse(rs.getString(2)));
                location.setDateF(LocalDate.parse(rs.getString(3)));
                location.setVoiture(new VoitureImpl().retourneVoiture(rs.getInt(4)));
                location.setLocateur(new LocatuerImpl().getLocateur(rs.getInt(5)));
                location.setCaissier(new CaissierImpl().getCaissier(rs.getInt(6)));
                location.setAgents(new AgentImpl().getAgent(rs.getInt(7)));
                location.setValide((rs.getInt(8)));
                location.setDateV(LocalDate.parse(rs.getString(9)));
                locationList.add(location);

            }

        }catch (Exception ex){ex.printStackTrace();}
        return locationList;
    }
    @Override
    public List<Location> listeLocationRetour() {
        String sql="SELECT location.* FROM location WHERE id_location in(SELECT location FROM retoure WHERE valide = 1) AND  date_valide is not null ";
        List<Location>locationList= new ArrayList<Location>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Location location= new Location();
                location.setId(rs.getInt(1));
                location.setDateD(LocalDate.parse(rs.getString(2)));
                location.setDateF(LocalDate.parse(rs.getString(3)));
                location.setVoiture(new VoitureImpl().retourneVoiture(rs.getInt(4)));
                location.setLocateur(new LocatuerImpl().getLocateur(rs.getInt(5)));
                location.setCaissier(new CaissierImpl().getCaissier(rs.getInt(6)));
                location.setAgents(new AgentImpl().getAgent(rs.getInt(7)));
                location.setValide((rs.getInt(8)));
                location.setDateV(LocalDate.parse(rs.getString(9)));
                locationList.add(location);

            }

        }catch (Exception ex){ex.printStackTrace();}
        return locationList;
    }
    @Override
    public List<Location> listeLocationEtat() {
        Location location= new Location();

        String sql="SELECT * FROM location WHERE valide=0 ";
        List<Location>locationList= new ArrayList<Location>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                location= new Location();
                location.setId(rs.getInt(1));
                location.setDateD(LocalDate.parse(rs.getString(2)));
                location.setDateF(LocalDate.parse(rs.getString(3)));
                location.setVoiture(new VoitureImpl().retourneVoiture(rs.getInt(4)));
                location.setLocateur(new LocatuerImpl().getLocateur(rs.getInt(5)));
                location.setAgents(new AgentImpl().getAgent(rs.getInt(7)));
                location.setValide((rs.getInt(8)));

                locationList.add(location);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return locationList;
    }
}

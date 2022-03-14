package sourse.classeImple;

import sourse.classes.Place;
import sourse.classes.Voiture;
import sourse.interphaces.IPlace;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceImpl implements IPlace {
    DB db =new DB();
    @Override
    public Place getLPlace(int id) {
        Place place = null;

        try {
                String sql = "SELECT * FROM place WHERE id= ?";
                db.preparedStatement(sql);
                db.getPstmt().setInt(1, id);
                ResultSet rs = db.executeQuery();
                if (rs.next())
                {
                    place = new Place();
                    place.setId(rs.getInt(1));
                    place.setCode(rs.getString(2));
                    place.setEtat(rs.getInt(3));
                }
            }
            catch (Exception ex){ex.printStackTrace();}

        return place;
    }
    @Override
    public int suppPlace(int id) {
        int ok=0;
        String sql = "DELETE FROM place WHERE id= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}
        return ok;
    }
    @Override
    public int addPlace(Place place) {
        int insert=0;
        try
        {
            String sql="INSERT INTO place VALUES(NULL, ?, ?)";
            db.preparedStatement(sql);
            db.getPstmt().setString(1,place.getCode());
            db.getPstmt().setInt(2,place.getEtat());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatPlace(Place place) {
        int edite=0;
        try
        {
            String sql="UPDATE place SET code= ?,etat= ? WHERE code = ?";

            db.preparedStatement(sql);

            db.getPstmt().setString(1,place.getCode());
            db.getPstmt().setInt(2,place.getEtat());
            db.getPstmt().setString(3,place.getCode());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Place> listePlace() {
        String sql="SELECT * FROM place ";
        List<Place>placeList= new ArrayList<Place>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
               Place place = new Place();
                place.setId(rs.getInt(1));
                place.setCode(rs.getString(2));
                place.setEtat(rs.getInt(3));
                placeList.add(place);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return placeList;
    }
    @Override
    public List<Place> listePlaceEtat() {
        String sql="SELECT * FROM place WHERE etat = 0";
        List<Place>placeList= new ArrayList<Place>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Place place = new Place();
                place.setId(rs.getInt(1));
                place.setCode(rs.getString(2));
                place.setEtat(rs.getInt(3));
                placeList.add(place);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return placeList;
    }
}

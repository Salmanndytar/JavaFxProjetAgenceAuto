package sourse.interphaces;



import sourse.classes.Place;

import java.util.List;

public interface IPlace {
    public Place getLPlace(int id);
    public int suppPlace(int id);
    public int addPlace(Place place);
    public  int updatPlace(Place place);
    public List<Place> listePlace();
    public List<Place> listePlaceEtat();
}

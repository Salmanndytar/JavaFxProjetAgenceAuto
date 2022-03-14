package sourse.interphaces;


import sourse.classes.Location;

import java.util.List;

public interface ILocation {
    public Location getLocation(int id);
    public int suppLocation(int id);
    public int addLocation(Location location);
    public  int updatLocation(Location location);
    public int ValiderLocation(Location location);
    public List<Location> listeLocationRetour();
    public List<Location> listeLocation();
    public List<Location> listeLocationEtat();
}

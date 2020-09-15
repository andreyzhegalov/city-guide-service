package model;

public class ShowPlace {
    private Adress adress;
    private GeoPosition geoPosition;
    private Description description;

    public ShowPlace() {
        this.adress = null;
        this.geoPosition = null;
        this.description = null;
    }

    public ShowPlace(Adress adress, GeoPosition geoPosition, Description description) {
        this.adress = adress;
        this.geoPosition = geoPosition;
        this.description = description;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress){
        this.adress = adress;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition){
        this.geoPosition = geoPosition;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description){
        this.description = description;
    }
}

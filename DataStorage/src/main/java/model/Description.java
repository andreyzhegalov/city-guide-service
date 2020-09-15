package model;

public class Description {
    private final String title;
    private String yearOfConstructon;
    private String architect;
    private final String info;

    public Description(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getYearOfConstructon() {
        return yearOfConstructon;
    }

    public void setYearOfConstructon(String yearOfConstructon) {
        this.yearOfConstructon = yearOfConstructon;
    }

    public String getArchitect() {
        return architect;
    }

    public void setArchitect(String architect) {
        this.architect = architect;
    }

    public String getInfo() {
        return info;
    }

}

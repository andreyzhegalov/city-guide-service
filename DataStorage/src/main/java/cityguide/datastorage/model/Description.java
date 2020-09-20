package cityguide.datastorage.model;

public class Description {
    private String title;
    private String yearOfConstructon;
    private String architect;
    private String info;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Description setYearOfConstructon(String yearOfConstructon) {
        this.yearOfConstructon = yearOfConstructon;
        return this;
    }

    public String getYearOfConstructon() {
        return yearOfConstructon;
    }

    public Description setArchitect(String architect) {
        this.architect = architect;
        return this;
    }

    public String getArchitect() {
        return architect;
    }

    public Description setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "Description{" +
            "title = " + getTitle() +
            ", yearOfConstructon = " + getYearOfConstructon() +
            ", architect = " + getArchitect() +
            ", info = " + getInfo() +
            "}";
    }

}

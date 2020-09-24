package cityguide.datastorage.model;

public class Description {
    private String title;
    private String yearOfConstruction;
    private String architect;
    private String info;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Description setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
        return this;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
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
            ", yearOfConstructon = " + getYearOfConstruction() +
            ", architect = " + getArchitect() +
            ", info = " + getInfo() +
            "}";
    }

}

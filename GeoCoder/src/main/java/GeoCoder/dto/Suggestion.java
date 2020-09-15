package GeoCoder.dto;

public class Suggestion {
    private String value;
    private Data data;

    public Suggestion() {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Suggestion{" + "value = " + getValue() + ", data = " + getData() + "}";
    }
}


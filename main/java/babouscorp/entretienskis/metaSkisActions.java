package babouscorp.entretienskis;

public class metaSkisActions {
    private String id;
    private String idSki;
    private long date;
    private String type;

    public String getId() {
        return id;
    }

    public String getIdSki() {
        return idSki;
    }

    public long getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdSki(String idSki) {
        this.idSki = idSki;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toCSV() {
        return date + ";" + type + ";";
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

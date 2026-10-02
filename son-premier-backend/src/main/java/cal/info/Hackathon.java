package cal.info;

public class Hackathon {

    private int id;
    private String nomHackathon;
    private String dateHackathon;
    private String lieuHackathon;
    private String urlHackathon;

    public Hackathon() {
    }

    public Hackathon(String nomHackathon, String dateHackathon, String lieuHackathon, String urlHackathon) {
        this.nomHackathon = nomHackathon;
        this.dateHackathon = dateHackathon;
        this.lieuHackathon = lieuHackathon;
        this.urlHackathon = urlHackathon;
    }

    public Hackathon(int id, String nomHackathon, String dateHackathon, String lieuHackathon, String urlHackathon) {
        this.id = id;
        this.nomHackathon = nomHackathon;
        this.dateHackathon = dateHackathon;
        this.lieuHackathon = lieuHackathon;
        this.urlHackathon = urlHackathon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomHackathon() {
        return nomHackathon;
    }

    public void setNomHackathon(String nomHackathon) {
        this.nomHackathon = nomHackathon;
    }

    public String getDateHackathon() {
        return dateHackathon;
    }

    public void setDateHackathon(String dateHackathon) {
        this.dateHackathon = dateHackathon;
    }

    public String getLieuHackathon() {
        return lieuHackathon;
    }

    public void setLieuHackathon(String lieuHackathon) {
        this.lieuHackathon = lieuHackathon;
    }

    public String getUrlHackathon() {
        return urlHackathon;
    }

    public void setUrlHackathon(String urlHackathon) {
        this.urlHackathon = urlHackathon;
    }

    @Override
    public String toString() {
        return "Woa le beau hackathon : {" +
                "\"id\": \"" + id + "\"" +
                "\"nomHackathon\": \"" + nomHackathon + "\"" +
                ", \"dateHackathon\": \"" + dateHackathon + "\"" +
                ", \"lieuHackathon\": \"" + lieuHackathon + "\"" +
                ", \"urlHackathon\": \"" + urlHackathon + "\"" +
                '}';
    }
}

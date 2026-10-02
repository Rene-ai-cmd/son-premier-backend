package cal.info;

import java.util.ArrayList;
import java.util.List;

public class Etudiant {

    private int id;
    private String nomEtudiant;
    private int ageEtudiant;
    private String programme;
    private Integer matricule;
    private List<Hackathon> preferencesHackathons;

    public Etudiant() {
    }

    public Etudiant(String nomEtudiant, int ageEtudiant, String programme, int matricule) {
        this.nomEtudiant = nomEtudiant;
        this.ageEtudiant = ageEtudiant;
        this.programme = programme;
        this.matricule = matricule;
        this.preferencesHackathons = new ArrayList<>();
    }

    public Etudiant(int id, String nomEtudiant, int ageEtudiant, String programme, int matricule) {
        this.id = id;
        this(nomEtudiant, ageEtudiant, programme, matricule);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomEtudiant() {
        return this.nomEtudiant;
    }

    public int getAgeEtudiant() {
        return this.ageEtudiant;
    }

    public String getProgramme() {
        return programme;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public void setAgeEtudiant(int ageEtudiant) {
        this.ageEtudiant = ageEtudiant;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public void ajouterUnePreference(Hackathon hackathon){
        this.preferencesHackathons.add(hackathon);
    }

    public List<Hackathon> getPreferencesHackathons() {
        return this.preferencesHackathons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return etudiant.matricule.equals(this.matricule);
    }

    @Override
    public int hashCode() {
        return 17 * matricule.hashCode();
    }

    @Override
    public String toString() {
        return "On dit bonjour à : {" +
                "\"nomEtudiant\": \"" + nomEtudiant + "\"" +
                ", \"ageEtudiant\": " + ageEtudiant +
                ", \"programme\": \"" + programme + "\"" +
                ", \"preferences\": \"" + preferencesHackathons + "\"" +
                "}";
    }
}

package Technopli.classe;

public class Transporteur {
    private String nom_transporteur;
    private int id_transporteur;

    public Transporteur( int id_transporteur,String nom_transporteur) {
        this.nom_transporteur = nom_transporteur;
        this.id_transporteur = id_transporteur;
    }

    public String getNom_transporteur() {
        return nom_transporteur;
    }

    public void setNom_transporteur(String nom_transporteur) {
        this.nom_transporteur = nom_transporteur;
    }

    public int getId_transporteur() {
        return id_transporteur;
    }

    public void setId_transporteur(int id_transporteur) {
        this.id_transporteur = id_transporteur;
    }
}

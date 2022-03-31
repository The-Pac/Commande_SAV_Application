package Technopli.classe;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Commande {
    private String client;
    private int id_commandeSAV;
    private String numero_commande;
    private String date_commande;
    private String date_saisi;
    private String reference_SAP;
    private String numero_article;
    private int quantite_commande;
    private int quantite_livrer;
    private boolean prise_en_compte_MAG;
    private int pret;
    private String date_pret;
    private boolean livre;
    private boolean bl;
    private String date_livraison;
    private String observation;
    private String retard;
    private String temps_gestion_commande;
    private BooleanProperty prise_en_compte_MAG_Property, livre_Property, annule_Property;
    private Statistique statistique = new Statistique();
    private boolean annule;


    //constructeur vide
    public Commande() {
    }

    //constructeur secretaire prise en compte
    public Commande(boolean bl_Constructeur,int id_commandeSAV) {
        this.bl = bl_Constructeur;
        this.id_commandeSAV = id_commandeSAV;
    }

    //constructeur secretaire mise a jour
    public Commande(String client, int id_commandeSAV, String numero_commande, String date_commande, String date_saisi, String reference_SAP, String numero_article, int quantite_commande, int quantite_livrer, boolean bl_Constructeur, boolean livre, String date_livraison, String temps_Gestion_Commande, String observation, String retard, boolean annule) {
        this.client = client;
        this.id_commandeSAV = id_commandeSAV;
        this.numero_commande = numero_commande;
        this.date_commande = date_commande;
        this.reference_SAP = reference_SAP;
        this.numero_article = numero_article;
        this.quantite_commande = quantite_commande;
        this.quantite_livrer = quantite_livrer;
        this.bl = bl_Constructeur;
        this.livre = livre;
        this.date_livraison = date_livraison;
        this.observation = observation;
        this.retard = retard;
        this.annule = annule;
        this.date_saisi = date_saisi;
        this.temps_gestion_commande = temps_Gestion_Commande;
    }

    //constructeur secretaire nouvelle commande
    public Commande(String client, int id_commandeSAV, String numero_commande, String date_commande, String date_saisi, String reference_SAP, String numero_article, int quantite_commande, int quantite_livrer, String observation) {
        this.client = client;
        this.id_commandeSAV = id_commandeSAV;
        this.numero_commande = numero_commande;
        this.date_commande = date_commande;
        this.reference_SAP = reference_SAP;
        this.numero_article = numero_article;
        this.quantite_commande = quantite_commande;
        this.quantite_livrer = quantite_livrer;
        this.observation = observation;
        this.date_saisi = date_saisi;
    }

    //constructeur d'une commande
    public Commande(String client_Constructeur, int id_commandeSAV_Constructeur, String numero_commande_Constructeur, String date_commande_Constructeur, String date_saisi_Constructeur, String reference_SAP_Constructeur, String numero_article_Constructeur, int quantite_commande_Constructeur, int quantite_livrer_Constructeur, boolean prise_en_compte_MAG_Constructeur, int pret_Constructeur, String date_pret_Constructeur, boolean bl_Constructeur, boolean livre_Constructeur, String date_livraison_Constructeur, String observation_Constructeur, String retard_Constructeur, String temps_gestion_commande_Constructeur, boolean annule_Constructeur) {
        this.id_commandeSAV = id_commandeSAV_Constructeur;
        this.quantite_commande = quantite_commande_Constructeur;
        this.quantite_livrer = quantite_livrer_Constructeur;
        this.pret = pret_Constructeur;

        if (client_Constructeur == null || client_Constructeur.isEmpty()) {
            this.client = "Inconnu";
        } else {
            this.client = client_Constructeur;
        }

        if (numero_commande_Constructeur == null || numero_commande_Constructeur.isEmpty()) {
            this.numero_commande = "Non Precisé";
        } else {
            this.numero_commande = numero_commande_Constructeur;
        }

        if (date_commande_Constructeur == null || date_commande_Constructeur.isEmpty()) {
            this.date_commande = "Non Precisé";
        } else {
            this.date_commande = date_commande_Constructeur;
        }

        if (date_saisi_Constructeur == null) {
            date_saisi = "";
        } else {
            date_saisi = date_saisi_Constructeur;
        }

        if (reference_SAP_Constructeur == null || reference_SAP_Constructeur.isEmpty()) {
            this.reference_SAP = "Non Precisé";
        } else {
            this.reference_SAP = reference_SAP_Constructeur;
        }

        if (numero_article_Constructeur == null || numero_article_Constructeur.isEmpty()) {
            this.numero_article = "Non precisé";
        } else {
            this.numero_article = numero_article_Constructeur;
        }
        this.prise_en_compte_MAG = prise_en_compte_MAG_Constructeur;

        this.date_pret = date_pret_Constructeur;

        this.livre = livre_Constructeur;
        this.bl = bl_Constructeur;
        this.date_livraison = date_livraison_Constructeur;

        if (observation_Constructeur == null || observation_Constructeur.isEmpty()) {
            this.observation = "Aucune observation";
        } else {
            this.observation = observation_Constructeur;
        }
        if (retard_Constructeur == null || retard_Constructeur.isEmpty()) {
            this.retard = "Aucun retard";
        } else {
            this.retard = retard_Constructeur;
        }
        this.temps_gestion_commande = temps_gestion_commande_Constructeur;

        this.annule = annule_Constructeur;


        this.livre_Property = new SimpleBooleanProperty(this.livre);
        this.prise_en_compte_MAG_Property = new SimpleBooleanProperty(this.prise_en_compte_MAG);
        this.annule_Property = new SimpleBooleanProperty(this.annule);
    }

    //getter setter

    public BooleanProperty annule_PropertyProperty() {
        return annule_Property;
    }

    public BooleanProperty livre_Property() {
        return livre_Property;
    }

    public BooleanProperty prise_en_compte_MAG_Property() {
        return prise_en_compte_MAG_Property;
    }

    public boolean isAnnule_Property() {
        return annule_Property.get();
    }

    public void setAnnule_Property(boolean annule_Property) {
        this.annule_Property.set(annule_Property);
    }

    public boolean isAnnule() {
        return annule;
    }

    public void setAnnule(boolean annule) {
        this.annule = annule;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getId_commandeSAV() {
        return id_commandeSAV;
    }

    public void setId_commandeSAV(int id_commandeSAV) {
        this.id_commandeSAV = id_commandeSAV;
    }

    public String getNumero_commande() {
        return numero_commande;
    }

    public void setNumero_commande(String numero_commande) {
        this.numero_commande = numero_commande;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public String getReference_SAP() {
        return reference_SAP;
    }

    public void setReference_SAP(String reference_SAP) {
        this.reference_SAP = reference_SAP;
    }

    public String getNumero_article() {
        return numero_article;
    }

    public void setNumero_article(String numero_article) {
        this.numero_article = numero_article;
    }

    public int getQuantite_commande() {
        return quantite_commande;
    }

    public void setQuantite_commande(int quantite_commande) {
        this.quantite_commande = quantite_commande;
    }

    public int getQuantite_livrer() {
        return quantite_livrer;
    }

    public void setQuantite_livrer(int quantite_livrer) {
        this.quantite_livrer = quantite_livrer;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getDate_pret() {
        return date_pret;
    }

    public void setDate_pret(String date_pret) {
        this.date_pret = date_pret;
    }

    public String getDate_livraison() {
        return date_livraison;
    }

    public void setDate_livraison(String date_livraison) {
        this.date_livraison = date_livraison;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRetard() {
        return retard;
    }

    public void setRetard(String retard) {
        this.retard = retard;
    }

    public String getTemps_gestion_commande() {
        return temps_gestion_commande;
    }

    public void setTemps_gestion_commande(String temps_gestion_commande) {
        this.temps_gestion_commande = temps_gestion_commande;
    }

    public String getDate_saisi() {
        return date_saisi;
    }

    public void setDate_saisi(String date_saisi) {
        this.date_saisi = date_saisi;
    }

    public boolean isPrise_en_compte_MAG() {
        return prise_en_compte_MAG;
    }

    public void setPrise_en_compte_MAG(boolean prise_en_compte_MAG) {
        this.prise_en_compte_MAG = prise_en_compte_MAG;
    }

    public boolean isLivre() {
        return livre;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public boolean isBl() {
        return bl;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }
}

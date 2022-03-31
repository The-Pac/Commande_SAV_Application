package Technopli.classe;

public class Client {
    private int id_client;
    private String nom_client;
    private String service_facturation_client;
    private String adresse_facturation_client;
    private String complement_adresse_facturation_client;
    private String pays_facturation_client;
    private String tel_facturation_client;
    private String fax_facturation_client;
    private String email_facturation_client;
    private String adresse_livraison_client;
    private String ville_livraison_client;
    private String pays_livraison_client;
    private String complement_adresse_livraison_client;
    private String nom_condition_paiement_client;
    private String email_condition_paiement_client;
    private String tel_condition_paiement_client;
    private String fax_condition_paiement_client;
    private String observation_transport_client;
    private String transporteur_client;

    public Client(int id_client, String nom_client_Constructeur, String service_facturation_Constructeur, String adresse_facturation_Constructeur, String complement_adresse_facturation_Constructeur, String pays_facturation_Constructeur, String tel_facturation_Constructeur, String fax_facturation_Constructeur, String email_facturation_Constructeur, String adresse_livraison_Constructeur, String ville_livraison_Constructeur, String pays_livraison_Constructeur, String complement_adresse_livraison_Constructeur, String nom_condition_paiement_Constructeur, String email_condition_paiement_Constructeur, String tel_condition_paiement_Constructeur, String fax_condition_paiement_Constructeur, String observation_transport_Constructeur, String transporteur_Constructeur) {
        this.id_client = id_client;
        this.nom_client = nom_client_Constructeur;
        this.service_facturation_client = service_facturation_Constructeur;
        this.adresse_facturation_client = adresse_facturation_Constructeur;
        this.complement_adresse_facturation_client = complement_adresse_facturation_Constructeur;
        this.pays_facturation_client = pays_facturation_Constructeur;
        this.tel_facturation_client = tel_facturation_Constructeur;
        this.fax_facturation_client = fax_facturation_Constructeur;
        this.email_facturation_client = email_facturation_Constructeur;
        this.adresse_livraison_client = adresse_livraison_Constructeur;
        this.ville_livraison_client = ville_livraison_Constructeur;
        this.pays_livraison_client = pays_livraison_Constructeur;
        this.complement_adresse_livraison_client = complement_adresse_livraison_Constructeur;
        this.nom_condition_paiement_client = nom_condition_paiement_Constructeur;
        this.email_condition_paiement_client = email_condition_paiement_Constructeur;
        this.tel_condition_paiement_client = tel_condition_paiement_Constructeur;
        this.fax_condition_paiement_client = fax_condition_paiement_Constructeur;
        this.observation_transport_client = observation_transport_Constructeur;
        this.transporteur_client = transporteur_Constructeur;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getService_facturation_client() {
        return service_facturation_client;
    }

    public void setService_facturation_client(String service_facturation_client) {
        this.service_facturation_client = service_facturation_client;
    }

    public String getAdresse_facturation_client() {
        return adresse_facturation_client;
    }

    public void setAdresse_facturation_client(String adresse_facturation_client) {
        this.adresse_facturation_client = adresse_facturation_client;
    }

    public String getComplement_adresse_facturation_client() {
        return complement_adresse_facturation_client;
    }

    public void setComplement_adresse_facturation_client(String complement_adresse_facturation_client) {
        this.complement_adresse_facturation_client = complement_adresse_facturation_client;
    }

    public String getPays_facturation_client() {
        return pays_facturation_client;
    }

    public void setPays_facturation_client(String pays_facturation_client) {
        this.pays_facturation_client = pays_facturation_client;
    }

    public String getTel_facturation_client() {
        return tel_facturation_client;
    }

    public void setTel_facturation_client(String tel_facturation_client) {
        this.tel_facturation_client = tel_facturation_client;
    }

    public String getFax_facturation_client() {
        return fax_facturation_client;
    }

    public void setFax_facturation_client(String fax_facturation_client) {
        this.fax_facturation_client = fax_facturation_client;
    }

    public String getEmail_facturation_client() {
        return email_facturation_client;
    }

    public void setEmail_facturation_client(String email_facturation_client) {
        this.email_facturation_client = email_facturation_client;
    }

    public String getAdresse_livraison_client() {
        return adresse_livraison_client;
    }

    public void setAdresse_livraison_client(String adresse_livraison_client) {
        this.adresse_livraison_client = adresse_livraison_client;
    }

    public String getVille_livraison_client() {
        return ville_livraison_client;
    }

    public void setVille_livraison_client(String ville_livraison_client) {
        this.ville_livraison_client = ville_livraison_client;
    }

    public String getPays_livraison_client() {
        return pays_livraison_client;
    }

    public void setPays_livraison_client(String pays_livraison_client) {
        this.pays_livraison_client = pays_livraison_client;
    }

    public String getComplement_adresse_livraison_client() {
        return complement_adresse_livraison_client;
    }

    public void setComplement_adresse_livraison_client(String complement_adresse_livraison_client) {
        this.complement_adresse_livraison_client = complement_adresse_livraison_client;
    }

    public String getNom_condition_paiement_client() {
        return nom_condition_paiement_client;
    }

    public void setNom_condition_paiement_client(String nom_condition_paiement_client) {
        this.nom_condition_paiement_client = nom_condition_paiement_client;
    }

    public String getEmail_condition_paiement_client() {
        return email_condition_paiement_client;
    }

    public void setEmail_condition_paiement_client(String email_condition_paiement_client) {
        this.email_condition_paiement_client = email_condition_paiement_client;
    }

    public String getTel_condition_paiement_client() {
        return tel_condition_paiement_client;
    }

    public void setTel_condition_paiement_client(String tel_condition_paiement_client) {
        this.tel_condition_paiement_client = tel_condition_paiement_client;
    }

    public String getFax_condition_paiement_client() {
        return fax_condition_paiement_client;
    }

    public void setFax_condition_paiement_client(String fax_condition_paiement_client) {
        this.fax_condition_paiement_client = fax_condition_paiement_client;
    }

    public String getObservation_transport_client() {
        return observation_transport_client;
    }

    public void setObservation_transport_client(String observation_transport_client) {
        this.observation_transport_client = observation_transport_client;
    }

    public String getTransporteur_client() {
        return transporteur_client;
    }

    public void setTransporteur_client(String transporteur_client) {
        this.transporteur_client = transporteur_client;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
}

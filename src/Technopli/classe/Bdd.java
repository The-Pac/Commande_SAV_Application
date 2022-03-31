package Technopli.classe;

import Technopli.Controller_Client;
import Technopli.Controller_Commande_Secretaire;
import Technopli.Controller_Filtre;
import Technopli.Controller_Main;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Bdd {
    private static ObservableList<Commande> data_commande;
    public ArrayList<String> arrayList_data_String = new ArrayList<>();
    public ArrayList<Integer> arrayList_data_Int = new ArrayList<>();
    public ArrayList<Boolean> arrayList_data_Bool = new ArrayList<>();

    public ObservableList<String> data_choix_client;
    public ObservableList<Client> data_client;
    public ObservableList<String> data_choix_transporteur;

    String userName = "user";
    String password = "root";
    String url = "jdbc:MySQL://localhost:3306/commande_SAV";

    Connection conn = null;
    String requete = null;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    private Controller_Main controller_main;
    private Controller_Client controller_client;
    private boolean etat_Bdd = false, etat_Driver = false;


    /////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    public static ObservableList<Commande> getData_commande() {
        return data_commande;
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    //preparation des drivers
    public void test_Drivers() {
        try {
            System.out.println("\n***** Test Drivers *****");
            Class.forName("com.mysql.jdbc.Driver");
            etat_Driver = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    //connexion et verification de l'etat des drivers
    public void connexion() {
        System.out.println("\n***** Test Connexion Bdd *****");
        test_Drivers();
        if (etat_Driver) {
            System.out.println("Driver on");
            try {
                conn = DriverManager.getConnection(url, userName, password);
                System.out.println("\nConnecté a la bdd");
                this.etat_Bdd = true;
            } catch (SQLException e) {
                this.etat_Bdd = false;
                e.printStackTrace();
                System.out.println("impossible de se connecté a la bdd");
            }
        } else {
            System.out.println("Driver off");
        }
        System.out.println("-------------------------------------------------------");

    }

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    //deconnexion
    public void deconnexion() {
        if (this.conn != null) {
            System.out.println("-------------------------------------------------------");
            try {
                this.conn.close();
                System.out.println("\n***** Déconnecté de la bdd *****");
            } catch (SQLException e) {
                System.out.println("Déconnexion impossible de la bdd");
                e.printStackTrace();
            }
        }
        System.out.println("-------------------------------------------------------");
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    //recuperation des donnee dans la bdd
    public void recuperation_Donnee_tableau(Controller_Main controller_main) {
        this.controller_main = controller_main;
        arrayList_data_Bool.clear();
        arrayList_data_String.clear();
        arrayList_data_Int.clear();
        requete = "SELECT * FROM `commandesav` ";
        System.out.println("-------------------------------------------------------");
        try {
            data_commande = FXCollections.observableArrayList();
            preparedStatement = conn.prepareStatement(requete);
            rs = preparedStatement.executeQuery(requete);
            while (rs.next()) {
                arrayList_data_Bool.clear();
                arrayList_data_String.clear();
                arrayList_data_Int.clear();

                arrayList_data_String.add(rs.getString("client"));
                arrayList_data_Int.add(rs.getInt("id_commandeSAV"));
                arrayList_data_String.add(rs.getString("numero_commande"));
                arrayList_data_String.add(rs.getString("date_commande"));
                arrayList_data_String.add(rs.getString("date_saisi"));
                arrayList_data_String.add(rs.getString("reference_SAP"));
                arrayList_data_String.add(rs.getString("numero_article"));
                arrayList_data_Int.add(rs.getInt("quantite_commande"));
                arrayList_data_Int.add(rs.getInt("quantite_a_livrer"));
                arrayList_data_Bool.add(rs.getBoolean("prise_en_compte_MAG"));
                arrayList_data_Int.add(rs.getInt("pret"));
                arrayList_data_String.add(rs.getString("date_pret"));
                arrayList_data_Bool.add(rs.getBoolean("bl"));
                arrayList_data_Bool.add(rs.getBoolean("livre"));
                arrayList_data_String.add(rs.getString("date_livraison"));
                arrayList_data_String.add(rs.getString("observation"));
                arrayList_data_String.add(rs.getString("retard"));
                arrayList_data_String.add(rs.getString("temps_gestion_commande"));
                arrayList_data_Bool.add(rs.getBoolean("annule"));

                //debug
                System.out.println("\nclient: " + arrayList_data_String.get(0)
                        + "   id_commande: " + arrayList_data_Int.get(0) + "   numero_commande: " + arrayList_data_String.get(1)
                        + "   date_commande: " + arrayList_data_String.get(2) + "   date_saisi: " + arrayList_data_String.get(3)
                        + "   reference_SAP: " + arrayList_data_String.get(4) + "   numero_article: " + arrayList_data_String.get(5)
                        + "   quantite_commande: " + arrayList_data_Int.get(1) + "   quantite_a_livrer: " + arrayList_data_Int.get(2)
                        + "   prise_en_compte_MAG: " + arrayList_data_Bool.get(0) + "   pret: " + arrayList_data_Int.get(3)
                        + "   date_pret: " + arrayList_data_String.get(6) + "bl: " + arrayList_data_Bool.get(1) +
                        "   livre: " + arrayList_data_Bool.get(2) + "   date_livraison: " + arrayList_data_String.get(7)
                        + "   observation: " + arrayList_data_String.get(8) + "   retard: " + arrayList_data_String.get(9)
                        + "   temps_gestion_commande: " + arrayList_data_String.get(10) + "   annule: " + arrayList_data_Bool.get(3));

                data_commande.add(new Commande(arrayList_data_String.get(0)
                        , arrayList_data_Int.get(0), arrayList_data_String.get(1)
                        , arrayList_data_String.get(2), arrayList_data_String.get(3)
                        , arrayList_data_String.get(4), arrayList_data_String.get(5)
                        , arrayList_data_Int.get(1), arrayList_data_Int.get(2)
                        , arrayList_data_Bool.get(0), arrayList_data_Int.get(3)
                        , arrayList_data_String.get(6), arrayList_data_Bool.get(1)
                        , arrayList_data_Bool.get(2), arrayList_data_String.get(7)
                        , arrayList_data_String.get(8), arrayList_data_String.get(9)
                        , arrayList_data_String.get(10), arrayList_data_Bool.get(3)));

            }
            mise_en_FXML_tableau(data_commande);
            System.out.println("\nFin de recuperation de donnee via bdd\n");
        } catch (SQLException ex) {
            System.out.println("impossible de recuperer la table commandeSAV");
            ex.printStackTrace();
        }
        System.out.println("-------------------------------------------------------");
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
    }

    /////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    //recupere les identifiant et le mot de passe et verifier via la bdd
    public String authentification(String identifiant, String mot_de_passe) {
        boolean reussi = false;
        try {
            arrayList_data_String.clear();
            requete = "SELECT * FROM `identification` WHERE `identifiant` = ? AND `mot_de_passe` = ?";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setString(1, identifiant);
            preparedStatement.setString(2, mot_de_passe);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                this.arrayList_data_String.add(this.rs.getString("prenom"));
                this.arrayList_data_String.add(this.rs.getString("nom"));
                this.arrayList_data_String.add(this.rs.getString("metier"));
                reussi = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        if (reussi) {
            String prenom_Client = arrayList_data_String.get(0);
            String nom_Client = arrayList_data_String.get(1);
            String metier_Client = arrayList_data_String.get(2);

            return "true;" + nom_Client + ";" + prenom_Client + ";" + metier_Client;
        } else {
            return "false";
        }

    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    //affichage dans le tableau via un thread
    protected void mise_en_FXML_tableau(ObservableList<Commande> data_commande) {
        Runnable command = () -> {
            //ajout au controller
            controller_main.tableau_Commande.setItems(data_commande);

        };
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    public void suppression_bdd_commande(int id_commande_ligne) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "DELETE FROM `commande_sav`.`commandesav` WHERE (`id_commandeSAV` =" + id_commande_ligne + ")";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("suppression reussi de la ligne: " + id_commande_ligne);
        } catch (SQLException e) {
            System.out.println("impossible de supprimer dans la bdd (la ligne n'existe peut etre deja plus?)");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    public void ajout_bdd_commande(String client, int id_commandeSAV, String numero_commande, String date_commande, String date_saisi, String reference_SAP, String numero_article, int quantite_commande, int quantite_livrer, String observation) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "INSERT INTO `commande_sav`.`commandesav` (`client`,`numero_commande`,`date_commande`,`date_saisi`,`reference_SAP`,`numero_article`,`quantite_commande`,`quantite_a_livrer`,`observation`) VALUE ('" + client + "','" + numero_commande + "','" + date_commande + "','" + date_saisi + "','" + reference_SAP + "','" + numero_article + "','" + quantite_commande + "','" + quantite_livrer + "','" + observation + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("ajout reussi de la ligne: " + id_commandeSAV);
        } catch (SQLException e) {
            System.out.println("impossible d'ajouter la ligne dans la bdd");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    //recuperation d'information via bdd

    public void mise_a_jour_bdd_commande_secretaire(String client, int id_commandeSAV, String numero_commande, String date_commande, String date_saisi, String reference_SAP, String numero_article, int quantite_commande, int quantite_livrer, boolean bl, boolean livre, String date_livraison, String temps_Gestion_Commande, String observation, String retard, boolean annule) {
        try {
            System.out.println("-------------------------------------------------------");
            requete = "UPDATE `commande_sav`.`commandesav` set `client`= '" + client + "',`id_commandeSAV`= '" + id_commandeSAV + "',`numero_commande`='"
                    + numero_commande + "',`date_commande`='" + date_commande + "',`date_saisi`='" + date_saisi + "',`reference_SAP`='" + reference_SAP
                    + "',`numero_article`='" + numero_article + "',`quantite_commande`='" + quantite_commande + "',`quantite_a_livrer`='"
                    + quantite_livrer + "',`bl`='" + bl + "',`livre`='" + livre + "',`date_livraison`='" + date_livraison + "',`temps_gestion_commande`='" + temps_Gestion_Commande + "',`observation`='" + observation
                    + "',`retard`='" + retard + "',`annule`= '" + annule + "'WHERE (`id_commandeSAV` = '" + id_commandeSAV + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("mise a jour reussi de la ligne: " + id_commandeSAV);
        } catch (SQLException e) {
            System.out.println("impossible de mettre a jour dans la bdd ");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    public void prise_en_compte_Secretaire(boolean bl, int id_commandeSAV) {
        try {
            System.out.println("-------------------------------------------------------");
            requete = "UPDATE `commande_sav`.`commandesav` set `bl`='" + bl + "'WHERE (`id_commandeSAV` = '" + id_commandeSAV + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("mise a jour reussi de la ligne: " + id_commandeSAV);
        } catch (SQLException e) {
            System.out.println("impossible de mettre a jour dans la bdd ");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    //recuperation d'information via bdd

    public void mise_a_jour_bdd_commande_technicien(int id_commandeSAV, boolean prise_en_compte_MAG, String pret, String date_pret) {
        try {
            System.out.println("-------------------------------------------------------");
            requete = "UPDATE `commande_sav`.`commandesav` set `prise_en_compte_MAG`= '" + prise_en_compte_MAG + "',`pret`= '" + pret + "',`date_pret`='" + date_pret + "'WHERE (`id_commandeSAV` = '" + id_commandeSAV + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("mise a jour reussi de la ligne: " + id_commandeSAV);
        } catch (SQLException e) {
            System.out.println("impossible de mettre a jour dans la bdd ");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }
    /////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////

    public void recuperation_Client() {
        System.out.println("-------------------------------------------------------");
        arrayList_data_String.clear();
        arrayList_data_Int.clear();
        requete = "SELECT * FROM `client`";
        try {
            data_client = FXCollections.observableArrayList();
            data_choix_client = FXCollections.observableArrayList();
            data_choix_transporteur = FXCollections.observableArrayList();
            preparedStatement = conn.prepareStatement(requete);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                arrayList_data_String.clear();
                arrayList_data_Int.clear();

                arrayList_data_Int.add(rs.getInt("id_client"));
                arrayList_data_String.add(rs.getString("nom"));
                arrayList_data_String.add(rs.getString("service"));
                arrayList_data_String.add(rs.getString("adresse_facturation"));
                arrayList_data_String.add(rs.getString("pays_facturation"));
                arrayList_data_String.add(rs.getString("complement_adresse_facturation"));
                arrayList_data_String.add(rs.getString("tel_facturation"));
                arrayList_data_String.add(rs.getString("fax_facturation"));
                arrayList_data_String.add(rs.getString("email_facturation"));
                arrayList_data_String.add(rs.getString("adresse_livraison"));
                arrayList_data_String.add(rs.getString("ville_livraison"));
                arrayList_data_String.add(rs.getString("pays_livraison"));
                arrayList_data_String.add(rs.getString("complement_adresse_livraison"));
                arrayList_data_String.add(rs.getString("nom_condition_paiement"));
                arrayList_data_String.add(rs.getString("email_condition_paiement"));
                arrayList_data_String.add(rs.getString("tel_condition_paiement"));
                arrayList_data_String.add(rs.getString("fax_condition_paiement"));
                arrayList_data_String.add(rs.getString("observation_transport"));
                arrayList_data_String.add(rs.getString("transporteur"));
                //Debug
                System.out.println("" + arrayList_data_Int + arrayList_data_String);
                data_choix_client.add(arrayList_data_String.get(0));
                data_client.add(new Client(arrayList_data_Int.get(0), arrayList_data_String.get(0), arrayList_data_String.get(1)
                        , arrayList_data_String.get(2), arrayList_data_String.get(3)
                        , arrayList_data_String.get(4), arrayList_data_String.get(5)
                        , arrayList_data_String.get(6), arrayList_data_String.get(7)
                        , arrayList_data_String.get(8), arrayList_data_String.get(9)
                        , arrayList_data_String.get(10), arrayList_data_String.get(11)
                        , arrayList_data_String.get(12), arrayList_data_String.get(13)
                        , arrayList_data_String.get(14), arrayList_data_String.get(15)
                        , arrayList_data_String.get(16), arrayList_data_String.get(17)));
            }
            System.out.println("-------------------------------------------------------");
            //fin recuperation client / debut recuperation transporteur
            arrayList_data_String.clear();
            arrayList_data_Int.clear();
            requete = "SELECT * FROM `transporteur`";
            ObservableList<Transporteur> data_transporteur = FXCollections.observableArrayList();
            preparedStatement = conn.prepareStatement(requete);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                arrayList_data_String.clear();
                arrayList_data_Int.clear();

                arrayList_data_Int.add(rs.getInt("id_transporteur"));
                arrayList_data_String.add(rs.getString("nom_transporteur"));
                //Debug
                System.out.println("" + arrayList_data_Int + arrayList_data_String);
                data_choix_transporteur.add(arrayList_data_String.get(0));
                data_transporteur.add(new Transporteur(arrayList_data_Int.get(0), arrayList_data_String.get(0)));
            }
            mise_en_FXML_client(data_choix_client, data_choix_transporteur, data_client, data_transporteur);
            System.out.println("-------------------------------------------------------");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }

    }

    //affichage dans le tableau via un thread
    protected void mise_en_FXML_client(ObservableList<String> data_choix_client, ObservableList<String> data_choix_transporteur, ObservableList<Client> data_client, ObservableList<Transporteur> data_transporteur) {
        Runnable command = new Runnable() {
            @Override
            public void run() {
                //ajout au controller
                Controller_Commande_Secretaire.setData_choix_client(data_choix_client);
                Controller_Client.setData_client(data_client);
                Controller_Filtre.setData_choix_client(data_choix_client);
                Controller_Client.setData_choix_client(data_choix_client);
                Controller_Client.setData_transporteur(data_transporteur);
                Controller_Client.setData_choix_transporteur(data_choix_transporteur);
            }
        };
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    public void ajout_bdd_client(int id_client_Constructeur, String nom_client_Constructeur, String service_facturation_Constructeur, String adresse_facturation_Constructeur, String complement_adresse_facturation_Constructeur, String pays_facturation_Constructeur, String tel_facturation_Constructeur, String fax_facturation_Constructeur, String email_facturation_Constructeur, String adresse_livraison_Constructeur, String ville_livraison_Constructeur, String pays_livraison_Constructeur, String complement_adresse_livraison_Constructeur, String nom_condition_paiement_Constructeur, String email_condition_paiement_Constructeur, String tel_condition_paiement_Constructeur, String fax_condition_paiement_Constructeur, String observation_transport_Constructeur, String transporteur_Constructeur) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "INSERT INTO `commande_sav`.`client` (`nom`,`service`,`adresse_facturation`,`pays_facturation`," +
                    "`complement_adresse_facturation`,`tel_facturation`,`fax_facturation`,`email_facturation`,`adresse_livraison`,`ville_livraison`," +
                    "`pays_livraison`,`complement_adresse_livraison`,`nom_condition_paiement`,`email_condition_paiement`,`tel_condition_paiement`," +
                    "`fax_condition_paiement`,`observation_transport`,`transporteur`) VALUE ('" + nom_client_Constructeur +
                    "','" + service_facturation_Constructeur + "','" + adresse_facturation_Constructeur + "','" + complement_adresse_facturation_Constructeur +
                    "','" + pays_facturation_Constructeur + "','" + tel_facturation_Constructeur + "','" + fax_facturation_Constructeur + "','" +
                    email_facturation_Constructeur + "','" + adresse_livraison_Constructeur + "','" + ville_livraison_Constructeur + "','" + pays_livraison_Constructeur +
                    "','" + complement_adresse_livraison_Constructeur + "','" + nom_condition_paiement_Constructeur + "','" + email_condition_paiement_Constructeur +
                    "','" + tel_condition_paiement_Constructeur + "','" + fax_condition_paiement_Constructeur + "','" + observation_transport_Constructeur + "','" +
                    transporteur_Constructeur + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("ajout reussi de la ligne: " + id_client_Constructeur);
        } catch (SQLException e) {
            System.out.println("impossible d'ajouter la ligne dans la bdd");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    //recuperation d'information via bdd

    public void mise_a_jour_bdd_client(int id_client_Constructeur, String nom_client_Constructeur, String service_facturation_Constructeur, String adresse_facturation_Constructeur, String complement_adresse_facturation_Constructeur, String pays_facturation_Constructeur, String tel_facturation_Constructeur, String fax_facturation_Constructeur, String email_facturation_Constructeur, String adresse_livraison_Constructeur, String ville_livraison_Constructeur, String pays_livraison_Constructeur, String complement_adresse_livraison_Constructeur, String nom_condition_paiement_Constructeur, String email_condition_paiement_Constructeur, String tel_condition_paiement_Constructeur, String fax_condition_paiement_Constructeur, String observation_transport_Constructeur, String transporteur_Constructeur) {
        try {
            System.out.println("-------------------------------------------------------");
            requete = "UPDATE `commande_sav`.`client` set nom = '" + nom_client_Constructeur +
                    "',service='" + service_facturation_Constructeur + "',adresse_facturation='" + adresse_facturation_Constructeur +
                    "',pays_facturation='" + pays_facturation_Constructeur + "',complement_adresse_facturation='" + complement_adresse_facturation_Constructeur +
                    "',tel_facturation='" + tel_facturation_Constructeur + "',fax_facturation='" + fax_facturation_Constructeur +
                    "',email_facturation='" + email_facturation_Constructeur + "',adresse_livraison='" + adresse_facturation_Constructeur +
                    "',pays_livraison='" + pays_livraison_Constructeur + "',complement_adresse_livraison='" + complement_adresse_livraison_Constructeur +
                    "',nom_condition_paiement='" + nom_condition_paiement_Constructeur + "',email_condition_paiement= '" + email_condition_paiement_Constructeur +
                    "',tel_condition_paiement= '" + tel_condition_paiement_Constructeur + "',fax_condition_paiement= '" + fax_condition_paiement_Constructeur +
                    "',observation_transport= '" + observation_transport_Constructeur + "',transporteur= '" + transporteur_Constructeur +
                    "' WHERE id_client = " + id_client_Constructeur;
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("mise a jour reussi de la ligne: " + id_client_Constructeur);
        } catch (SQLException e) {
            System.out.println("impossible de mettre a jour dans la bdd ");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    /////////////////////////////////////////////////////////
    //getter et setter
    public void ajout_bdd_transporteur(String nom_transporteur, int id_transporteur) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "INSERT INTO `commande_sav`.`transporteur` (`nom_transporteur`) VALUE ('" + nom_transporteur + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("ajout reussi de la ligne: " + id_transporteur);
        } catch (SQLException e) {
            System.out.println("impossible d'ajouter la ligne dans la bdd");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    public void suppression_bdd_transporteur(String nom_transporteur) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "DELETE FROM `commande_sav`.`transporteur` WHERE (`nom_transporteur` ='" + nom_transporteur + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();

            requete = "UPDATE `commande_sav`.`client` set `transporteur` = 'Aucun' WHERE (`transporteur` = '" + nom_transporteur + "'and `id_client` <> 0)";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("suppression reussi du transporteur : " + nom_transporteur);
        } catch (SQLException e) {
            System.out.println("impossible de supprimer dans la bdd (la ligne n'existe peut etre deja plus?)");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }
    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////

    public void suppression_bdd_client(String nom_client) {
        System.out.println("-------------------------------------------------------");
        try {
            requete = "DELETE FROM `commande_sav`.`client` WHERE (`nom` ='" + nom_client + "')";
            preparedStatement = conn.prepareStatement(requete);
            preparedStatement.executeUpdate();
            System.out.println("suppression reussi du client: " + nom_client);
        } catch (SQLException e) {
            System.out.println("impossible de supprimer dans la bdd (la ligne n'existe peut etre deja plus?)");
            e.printStackTrace();
        }
        if (this.rs != null) {
            try {
                this.rs.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.rs = null;
        }
        if (this.preparedStatement != null) {
            try {
                this.preparedStatement.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            this.preparedStatement = null;
        }
        System.out.println("-------------------------------------------------------");
    }

    public void raffraichissement_tableau() {
        System.out.println("-------------------------------------------------------");
        arrayList_data_Bool.clear();
        arrayList_data_String.clear();
        arrayList_data_Int.clear();
        System.out.println("Raffraichissement du tableau commencé ");
        try {
            data_commande = FXCollections.observableArrayList();
            requete = "SELECT * FROM `commandesav` ";
            preparedStatement = conn.prepareStatement(requete);
            rs = preparedStatement.executeQuery(requete);

            if (!rs.next()) {
                System.out.println("pas de nouvelle commande");
            } else if (rs.next()) {
                int i = 0;
                while (rs.next()) {
                    arrayList_data_Bool.clear();
                    arrayList_data_String.clear();
                    arrayList_data_Int.clear();

                    arrayList_data_String.add(rs.getString("client"));
                    arrayList_data_Int.add(rs.getInt("id_commandeSAV"));
                    arrayList_data_String.add(rs.getString("numero_commande"));
                    arrayList_data_String.add(rs.getString("date_commande"));
                    arrayList_data_String.add(rs.getString("date_saisi"));
                    arrayList_data_String.add(rs.getString("reference_SAP"));
                    arrayList_data_String.add(rs.getString("numero_article"));
                    arrayList_data_Int.add(rs.getInt("quantite_commande"));
                    arrayList_data_Int.add(rs.getInt("quantite_a_livrer"));
                    arrayList_data_Bool.add(rs.getBoolean("prise_en_compte_MAG"));
                    arrayList_data_Int.add(rs.getInt("pret"));
                    arrayList_data_String.add(rs.getString("date_pret"));
                    arrayList_data_Bool.add(rs.getBoolean("bl"));
                    arrayList_data_Bool.add(rs.getBoolean("livre"));
                    arrayList_data_String.add(rs.getString("date_livraison"));
                    arrayList_data_String.add(rs.getString("observation"));
                    arrayList_data_String.add(rs.getString("retard"));
                    arrayList_data_String.add(rs.getString("temps_gestion_commande"));
                    arrayList_data_Bool.add(rs.getBoolean("annule"));
                    i++;
                    //debug
                    data_commande.add(new Commande(arrayList_data_String.get(0)
                            , arrayList_data_Int.get(0), arrayList_data_String.get(1)
                            , arrayList_data_String.get(2), arrayList_data_String.get(3)
                            , arrayList_data_String.get(4), arrayList_data_String.get(5)
                            , arrayList_data_Int.get(1), arrayList_data_Int.get(2)
                            , arrayList_data_Bool.get(0), arrayList_data_Int.get(3)
                            , arrayList_data_String.get(6), arrayList_data_Bool.get(1)
                            , arrayList_data_Bool.get(2), arrayList_data_String.get(7)
                            , arrayList_data_String.get(8), arrayList_data_String.get(9)
                            , arrayList_data_String.get(10), arrayList_data_Bool.get(3)));
                }
                mise_en_FXML_tableau(data_commande);
                System.out.println("nombre commande:" + i);
            }
        } catch (SQLException ex) {
            System.out.println("impossible de recuperer la table commandeSAV");
            ex.printStackTrace();
        }
        System.out.println("-------------------------------------------------------");
    }

    //verification de l'etat de la bdd
    public boolean isEtat_bdd() {
        return etat_Bdd;
    }
}

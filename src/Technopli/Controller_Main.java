package Technopli;

import Technopli.classe.Bdd;
import Technopli.classe.Commande;
import Technopli.classe.Statistique;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Main implements Initializable {
    //private:
    private static int nombre_commande = 0;
    private static String nom_utilisateur, prenom_utilisateur, metier_utilisateur;
    private static Image image_Client;
    //public:
    //FXML
    public BarChart statistique_BarChart;
    public NumberAxis statistique_NumberAxis;
    public CategoryAxis statistique_CategoryAxis;
    public Optional<ButtonType> optional;
    public ImageView image_Client_ImageView;
    public Label metier_Label;
    public VBox trie_Rapide_Vbox, tableau_Vbox;
    public HBox information_Hbox, statistique_Hbox;
    public Button export_Excel_Bouton, reset_Filtre_Bouton, rafraichissement_Tableau_Bouton, supression_Ligne_Bouton, modifier_Ligne_Bouton;
    public TableView<Commande> tableau_Commande;
    public TableColumn<Commande, String> client_TableColonne, id_Commande_TableColonne, numero_Commande_TableColonne, date_Saisi_Colonne,
            date_Commande_TableColonne, reference_SAP_TableColonne, numero_Article_TableColonne, quantite_Commande_TableColonne,
            quantite_Livrer_TableColonne, date_Pret_TableColonne, date_Livraison_TableColonne, retard_TableColonne, observation_TableColonne,
            temps_Gestion_Commande_TableColonne;
    public TableColumn<Commande, CheckBox> bL_TableColonne, prise_En_Compte_MAG_TableColonne, livre_TableColonne, annule_TableColonne;
    public TableColumn<Commande, TextField> pret_TableColonne;
    public MenuBar menuBar;
    public FilteredList<Commande> recherche_Data_FilteredList;
    public TextField moyenne_Total_TextField, recherche_TextField, moyenne_Mois_TextField, moyenne_Annee_TextField;
    public MenuItem nouvelle_commande_MenuItem, nouveau_client_MenuItem, filtre_MenuItem, help_MenuItem, deconnexion_MenuItem;
    public Menu nouveau_Menu, principal_Menu;
    public CheckBox bl_Colonne_Checkbox, livre_Colonne_CheckBox, prise_En_Compte_MAG_Colonne_CheckBox, annule_Colonne_CheckBox, livre_Tri_Rapide_CheckBox, prise_En_Compte_MAG_Tri_Rapide_CheckBox, annule_Tri_Rapide_CheckBox;
    public ToggleButton detail_Tableau_ToggleBouton;
    public ComboBox<String> choix_Mois_ComboBox, choix_Annee_ComboBox;
    public ImageView secretaire_ImageView;
    //variable
    public Image refresh_Image;
    public Stage stage;
    public Node source;
    public Alert alert;
    public Bdd bdd;
    public boolean fentre_Ouverte = false;
    public DateTimeFormatter dateTimeFormatter;
    public Statistique statistique;


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    //getters
    public static String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public static void setNom_utilisateur(String nom_utilisateur) {
        Controller_Main.nom_utilisateur = nom_utilisateur;
    }

    public static String getPrenom_utilisateur() {
        return prenom_utilisateur;
    }

    public static void setPrenom_utilisateur(String prenom_utilisateur) {
        Controller_Main.prenom_utilisateur = prenom_utilisateur;
    }

    public static String getMetier_utilisateur() {
        return metier_utilisateur;
    }

    public static void setMetier_utilisateur(String metier_utilisateur) {
        Controller_Main.metier_utilisateur = metier_utilisateur;
    }

    public static Image getImage_Client() {
        return image_Client;
    }

    public static void setImage_Client(Image image_Client) {
        Controller_Main.image_Client = image_Client;
    }


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //initialisation
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation Classe
        bdd = new Bdd();
        statistique = new Statistique();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //element invisible / visible
        client_TableColonne.setVisible(false);
        id_Commande_TableColonne.setVisible(false);
        temps_Gestion_Commande_TableColonne.setVisible(false);
        retard_TableColonne.setVisible(false);
        observation_TableColonne.setVisible(false);
        annule_Tri_Rapide_CheckBox.setDisable(true);
        annule_TableColonne.setVisible(false);

        //configuration image
        //secretaire affichage
        refresh_Image = new Image("Technopli/img/refresh.png");
        secretaire_ImageView = new ImageView(refresh_Image);
        secretaire_ImageView.setFitHeight(30);
        secretaire_ImageView.setFitWidth(30);
        secretaire_ImageView.setPreserveRatio(true);
        rafraichissement_Tableau_Bouton.setGraphic(secretaire_ImageView);

        //configuration des colonnes du tableau des commandes
        client_TableColonne.setCellValueFactory(new PropertyValueFactory<>("client"));
        id_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("id_commandeSAV"));
        numero_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("numero_commande"));
        date_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        date_Saisi_Colonne.setCellValueFactory(new PropertyValueFactory<>("date_saisi"));
        reference_SAP_TableColonne.setCellValueFactory(new PropertyValueFactory<>("reference_SAP"));
        numero_Article_TableColonne.setCellValueFactory(new PropertyValueFactory<>("numero_article"));
        quantite_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("quantite_commande"));
        quantite_Livrer_TableColonne.setCellValueFactory(new PropertyValueFactory<>("quantite_livrer"));

        prise_En_Compte_MAG_TableColonne.setCellValueFactory(arg0 -> {
            Commande commande = arg0.getValue();
            prise_En_Compte_MAG_Colonne_CheckBox = new CheckBox();
            prise_En_Compte_MAG_Colonne_CheckBox.setDisable(true);
            prise_En_Compte_MAG_Colonne_CheckBox.selectedProperty().setValue(commande.isPrise_en_compte_MAG());
            return new SimpleObjectProperty<>(prise_En_Compte_MAG_Colonne_CheckBox);
        });

        pret_TableColonne.setCellValueFactory(new PropertyValueFactory<>("pret"));
        date_Pret_TableColonne.setCellValueFactory(new PropertyValueFactory<>("date_pret"));

        bL_TableColonne.setCellValueFactory(arg0 -> {
            Commande commande = arg0.getValue();
            bl_Colonne_Checkbox = new CheckBox();
            bl_Colonne_Checkbox.setDisable(true);
            bl_Colonne_Checkbox.selectedProperty().setValue(commande.isBl());
            return new SimpleObjectProperty<>(bl_Colonne_Checkbox);
        });

        livre_TableColonne.setCellValueFactory(arg0 -> {
            Commande commande = arg0.getValue();
            livre_Colonne_CheckBox = new CheckBox();
            livre_Colonne_CheckBox.setDisable(true);
            livre_Colonne_CheckBox.selectedProperty().setValue(commande.isLivre());
            return new SimpleObjectProperty<>(livre_Colonne_CheckBox);
        });

        date_Livraison_TableColonne.setCellValueFactory(new PropertyValueFactory<>("date_livraison"));
        retard_TableColonne.setCellValueFactory(new PropertyValueFactory<>("retard"));
        observation_TableColonne.setCellValueFactory(new PropertyValueFactory<>("observation"));
        temps_Gestion_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("temps_gestion_commande"));
        annule_TableColonne.setCellValueFactory(arg0 -> {
            Commande commande = arg0.getValue();
            annule_Colonne_CheckBox = new CheckBox();
            annule_Colonne_CheckBox.setDisable(true);
            annule_Colonne_CheckBox.selectedProperty().setValue(commande.isAnnule());
            return new SimpleObjectProperty<>(annule_Colonne_CheckBox);
        });

        //recupere le model du tableau
        TableView.TableViewSelectionModel<Commande> model_tableau = tableau_Commande.getSelectionModel();
        model_tableau.setSelectionMode(SelectionMode.SINGLE);

        //information utilisateur
        metier_Label.setText(getMetier_utilisateur());
        image_Client_ImageView.setImage(getImage_Client());

        //element visible et invisible / disable en fonction des metiers
        switch (getMetier_utilisateur()) {
            case "secretaire":
                //notification_Label.setText("Nouvelle prise en compte MAG");
                nouveau_Menu.setVisible(true);
                modifier_Ligne_Bouton.setVisible(true);
                id_Commande_TableColonne.setEditable(true);
                client_TableColonne.setEditable(true);
                numero_Commande_TableColonne.setEditable(true);
                date_Commande_TableColonne.setEditable(true);
                reference_SAP_TableColonne.setEditable(true);
                numero_Article_TableColonne.setEditable(true);
                quantite_Livrer_TableColonne.setEditable(true);
                quantite_Commande_TableColonne.setEditable(true);
                livre_TableColonne.setEditable(true);
                statistique_Hbox.setVisible(false);
                date_Livraison_TableColonne.setEditable(true);
                retard_TableColonne.setEditable(true);
                break;
            case "technicien":
                //notification_Label.setText("Nouvelle commande");
                nouveau_Menu.setVisible(false);
                prise_En_Compte_MAG_TableColonne.setEditable(true);
                pret_TableColonne.setEditable(true);
                date_Pret_TableColonne.setEditable(true);
                modifier_Ligne_Bouton.setVisible(true);
                statistique_Hbox.setVisible(false);
                supression_Ligne_Bouton.setVisible(false);
                break;
            case "superviseur":
                nouveau_Menu.setVisible(false);
                modifier_Ligne_Bouton.setVisible(false);
                id_Commande_TableColonne.setEditable(true);
                client_TableColonne.setEditable(true);
                numero_Commande_TableColonne.setEditable(true);
                date_Commande_TableColonne.setEditable(true);
                reference_SAP_TableColonne.setEditable(true);
                numero_Article_TableColonne.setEditable(true);
                quantite_Livrer_TableColonne.setEditable(true);
                quantite_Commande_TableColonne.setEditable(true);
                livre_TableColonne.setEditable(true);
                date_Livraison_TableColonne.setEditable(true);
                retard_TableColonne.setEditable(true);
                prise_En_Compte_MAG_TableColonne.setEditable(true);
                pret_TableColonne.setEditable(true);
                date_Pret_TableColonne.setEditable(true);
                break;
        }
        information_Hbox.setVisible(true);
        deconnexion_MenuItem.setVisible(true);
        filtre_MenuItem.setVisible(true);
        tableau_Vbox.setVisible(true);
        trie_Rapide_Vbox.setDisable(false);

        //recuperation données tableau / statistique
        bdd.connexion();
        Controller_Main controller_main = this;
        bdd.recuperation_Donnee_tableau(controller_main);

        //statistique
        statistique.affichage_statistique();
        statistique.moyenne_Total(Bdd.getData_commande(), this);

        //ordre de trie du tableau par defaut
        id_Commande_TableColonne.setSortType(TableColumn.SortType.DESCENDING);
        tableau_Commande.getSortOrder().add(id_Commande_TableColonne);
        tableau_Commande.sort();
        tableau_Commande.autosize();
        //listener sur le toggle bouton detail/simple du tableau
        detail_Tableau_ToggleBouton.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (detail_Tableau_ToggleBouton.isSelected()) {
                detail_Tableau_ToggleBouton.setText("Simple");
                client_TableColonne.setVisible(true);
                id_Commande_TableColonne.setVisible(true);
                temps_Gestion_Commande_TableColonne.setVisible(true);
                retard_TableColonne.setVisible(true);
                observation_TableColonne.setVisible(true);
                annule_TableColonne.setVisible(true);
                annule_Tri_Rapide_CheckBox.setDisable(false);
            }
            if (!detail_Tableau_ToggleBouton.isSelected()) {
                annule_Tri_Rapide_CheckBox.setSelected(false);
                detail_Tableau_ToggleBouton.setText("Détails");
                client_TableColonne.setVisible(false);
                annule_TableColonne.setVisible(false);
                id_Commande_TableColonne.setVisible(false);
                temps_Gestion_Commande_TableColonne.setVisible(false);
                retard_TableColonne.setVisible(false);
                observation_TableColonne.setVisible(false);
                annule_Tri_Rapide_CheckBox.setDisable(true);
            }
        });


        //filtre et trie des données
        recherche_Data_FilteredList = new FilteredList<>(Bdd.getData_commande(), p -> true);
        //filtre apres recuperation bdd
        recherche_TextField.textProperty().addListener((observable, ancienne_Valeur, nouvelle_Valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {
            if (nouvelle_Valeur == null || nouvelle_Valeur.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = nouvelle_Valeur.toLowerCase();

            if (commande.getClient().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getQuantite_commande()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getQuantite_livrer()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getPret()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getNumero_commande().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getNumero_article().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getReference_SAP().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getRetard().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }
        }));

        //trie rapide sur des checkbox
        annule_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (annule_Tri_Rapide_CheckBox.isSelected() == commande.isAnnule()) {
                return true;
            } else if (!annule_Tri_Rapide_CheckBox.isSelected() == commande.isAnnule()) {
                return false;
            }
            return false;
        })));
        livre_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                return true;
            } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                return false;
            }
            return false;
        })));
        prise_En_Compte_MAG_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                return true;
            } else if (!prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG()) {
                return false;
            }
            return false;
        })));

        //initialisation du trie du tableau par rapport au metier de l'utilisateur
        if (getMetier_utilisateur().equals("secretaire")) {
            //met en avant les commande non livrer
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                    return false;
                }
                return false;
            });
        } else if (getMetier_utilisateur().equals("technicien")) {
            //met en avant les commande non prise en compte MAG
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG()) {
                    return false;
                }
                return false;
            });
        } else {
            //met en avant les commande non livrer
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                    return false;
                }
                return false;
            });
        }
        SortedList<Commande> data_trie = new SortedList<>(recherche_Data_FilteredList);
        data_trie.comparatorProperty().bind(tableau_Commande.comparatorProperty());
        tableau_Commande.setItems(data_trie);

        //suppression ligne si une ligne est selectionner
        //modification ligne si une ligne est selectionner
        tableau_Commande.getSelectionModel().selectedIndexProperty().addListener((obs, ancienne_selection, nouvelle_selection) -> {
            if (nouvelle_selection != null) {
                supression_Ligne_Bouton.setDisable(false);
                modifier_Ligne_Bouton.setDisable(false);
            }
        });

        choix_Mois_ComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {

        });

        choix_Annee_ComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {

        });

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //affichage page nouvelle commande
    public void nouvelle_commande_menuItem_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        try {
            Controller_Commande_Secretaire.setModifier(false);
            Controller_Commande_Secretaire.setController_main(this);
            Controller_Commande_Secretaire.setId_max(Bdd.getData_commande().get(Bdd.getData_commande().size() - 1).getId_commandeSAV());
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Commande_Secretaire.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //affichage page nouveau client
    public void nouveau_client_menuItem_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Client.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //filtre pour rechercher parmis les commandes
    public void filtre_menuItem_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("fxml/filtre.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setFullScreen(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //deconnexion de l'utilisateur et redirection vers la page de connexion
    public void deconnexion_menuItem_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        bdd.deconnexion();
        stage = (Stage) menuBar.getScene().getWindow();
        stage.close();

        stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/identification.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //Permettre a l'utilisateur de quitter ou non l'aplication
    public void quitter_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("Quitter");
        alert.setHeaderText("Êtes-vous sur de vouloir quitter?");
        optional = alert.showAndWait();

        if (optional.get() == ButtonType.OK) {
            if (bdd.isEtat_bdd()) {
                bdd.deconnexion();
            }
            stage.close();
        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void supression_ligne_bouton_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("Supression de ligne");
        alert.setHeaderText("Êtes-vous sur de vouloir supprimer la ligne?");
        optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            bdd.suppression_bdd_commande(tableau_Commande.getSelectionModel().getSelectedItem().getId_commandeSAV());
            Bdd.getData_commande().remove(tableau_Commande.getSelectionModel().getSelectedItem());
            nombre_commande--;
        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void modifier_ligne_bouton_Action(ActionEvent actionEvent) {
        fentre_Ouverte = true;
        //ouvrir fenetre modifier en fonction du metier de l'utilisateur
        if (getMetier_utilisateur().equals("secretaire")) {
            try {
                Controller_Commande_Secretaire.setCommande(tableau_Commande.getSelectionModel().getSelectedItem());
                Controller_Commande_Secretaire.setController_main(this);
                Controller_Commande_Secretaire.setModifier(true);
                Controller_Commande_Secretaire.setPrise_en_compte_MAG(tableau_Commande.getSelectionModel().getSelectedItem().isPrise_en_compte_MAG());
                Controller_Commande_Secretaire.setId_max(Bdd.getData_commande().get(Bdd.getData_commande().size() - 1).getId_commandeSAV());

                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("fxml/Commande_Secretaire.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setFullScreen(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (getMetier_utilisateur().equals("technicien")) {
            try {
                Controller_Commande_Technicien.setCommande(tableau_Commande.getSelectionModel().getSelectedItem());
                Controller_Commande_Technicien.setController_main(this);
                Controller_Commande_Technicien.setId_max(Bdd.getData_commande().get(Bdd.getData_commande().size() - 1).getId_commandeSAV());

                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("fxml/Commande_Technicien.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.setFullScreen(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void rafraichissement_Tableau_Bouton_Action(ActionEvent actionEvent) {
        rafraichissement_Tableau_Bouton.setDisable(true);

        bdd.raffraichissement_tableau();
        tableau_Commande.refresh();
        recherche_Data_FilteredList = new FilteredList<>(Bdd.getData_commande(), p -> true);
        recherche_TextField.textProperty().addListener((observable, ancienne_Valeur, nouvelle_Valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {
            if (nouvelle_Valeur == null || nouvelle_Valeur.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = nouvelle_Valeur.toLowerCase();

            if (commande.getClient().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getQuantite_commande()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getQuantite_livrer()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (String.valueOf(commande.getPret()).toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getNumero_commande().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getNumero_article().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getReference_SAP().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getRetard().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (commande.getDate_livraison().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }
        }));

        //trie rapide sur des checkbox
        annule_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (annule_Tri_Rapide_CheckBox.isSelected() == commande.isAnnule()) {
                return true;
            } else if (!annule_Tri_Rapide_CheckBox.isSelected() == commande.isAnnule()) {
                return false;
            }
            return false;
        })));
        livre_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                return true;
            } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                return false;
            }
            return false;
        })));
        prise_En_Compte_MAG_Tri_Rapide_CheckBox.selectedProperty().addListener(((observableValue, ancienne_valeur, nouvelle_valeur) -> recherche_Data_FilteredList.setPredicate(commande -> {

            if (prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                return true;
            } else if (!prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG()) {
                return false;
            }
            return false;
        })));

        //initialisation du trie du tableau par rapport au metier de l'utilisateur
        if (getMetier_utilisateur().equals("secretaire")) {
            //met en avant les commande non livrer
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                    return false;
                }
                return false;
            });
        } else if (getMetier_utilisateur().equals("technicien")) {
            //met en avant les commande non prise en compte MAG
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!prise_En_Compte_MAG_Tri_Rapide_CheckBox.isSelected() == commande.isPrise_en_compte_MAG()) {
                    return false;
                }
                return false;
            });
        } else {
            //met en avant les commande non livrer
            recherche_Data_FilteredList.setPredicate(commande -> {
                if (livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre() && !annule_Tri_Rapide_CheckBox.isSelected() == !commande.isAnnule()) {
                    return true;
                } else if (!livre_Tri_Rapide_CheckBox.isSelected() == commande.isLivre()) {
                    return false;
                }
                return false;
            });
        }
        SortedList<Commande> data_trie = new SortedList<>(recherche_Data_FilteredList);
        data_trie.comparatorProperty().bind(tableau_Commande.comparatorProperty());
        tableau_Commande.setItems(data_trie);

        rafraichissement_Tableau_Bouton.setDisable(false);
    }

    public void reset_Filtre_Bouton_Action(ActionEvent actionEvent) {
        recherche_Data_FilteredList.setPredicate(commande -> true);
    }

    public void export_Excel_Bouton_Action(ActionEvent actionEvent) {
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("Mise en excel tableau");
        alert.setHeaderText("Êtes-vous sur de vouloir mettre en excel le tableau?");
        optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);
            //recuperation des colonnes du tableau
            for (int j = 0; j < tableau_Commande.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tableau_Commande.getColumns().get(j).getText());
            }
            //recuperation des données
            for (int i = 0; i < tableau_Commande.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tableau_Commande.getColumns().size(); j++) {
                    if (tableau_Commande.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tableau_Commande.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            //sauvegarde du fichier
            try {
                FileOutputStream fileOut = new FileOutputStream("filtre_Commande_SAV.xls");
                workbook.write(fileOut);
                System.out.println("creation du fichier filtre_Commande_SAV.xls");
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
}
package Technopli;

import Technopli.classe.Bdd;
import Technopli.classe.Commande;
import Technopli.classe.Statistique;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Commande_Secretaire implements Initializable {
    //private
    private static Commande commande;
    private static boolean modifier = false, prise_en_compte_MAG = false;
    private static int id_max;
    private static Controller_Main controller_main;
    private static ObservableList<String> data_choix_client;
    //initialisation
    //fmxl
    public Node source;
    public Optional<ButtonType> optional;

    public Button modifier_Article_Bouton, supression_Article_Bouton, retour_Bouton, finalisation_Avant_Apres_Prise_En_Compte_MAG_Bouton, ajout_Article_Bouton;
    public Label commande_Numero_Label;
    public TextField retard_TextField, observation_TextField, quantite_commande_TextField, numero_article_TextField, reference_SAP_TextField, numero_commande_TextField;
    public DatePicker date_Commande_DatePicker;
    public ComboBox<String> choix_Client_Commande_ComboBox;
    public DateTimeFormatter dateTimeFormatter;
    public VBox avant_MAG_Vbox, apres_MAG_Vbox, ligne_Vbox, apres_Prise_En_Compte_MAG_Vbox;
    public RadioButton avant_Prise_En_Compte_MAG_RadioBouton, apres_Prise_En_Compte_MAG_RadioBouton;
    public CheckBox bl_Checkbox, livre_CheckBox, annule_CheckBox;
    public ToggleGroup avant_apres_ToggleGroup;
    public Label erreur_Label;
    public TableView<Commande> tableau_Commande;
    public TableColumn<Commande, String> client_TableColonne, numero_Commande_TableColonne, date_Commande_TableColonne, reference_SAP_TableColonne, numero_Article_TableColonne, quantite_Commande_TableColonne, observation_TableColonne;
    public HBox tableau_Hbox;

    //variable
    public boolean prise_en_compte_Secretaire = false;
    public Bdd bdd;
    public Stage stage;
    public Alert alert;
    public ObservableList<Commande> data_Tableau_Secretaire_ObservableList;

    public static void setController_main(Controller_Main controller_main) {
        Controller_Commande_Secretaire.controller_main = controller_main;
    }

    public static int getId_max() {
        return id_max;
    }

    public static void setId_max(int id_max) {
        Controller_Commande_Secretaire.id_max = id_max;
    }

    public static void setCommande(Commande commande) {
        Controller_Commande_Secretaire.commande = commande;
    }

    public static void setModifier(boolean modifier) {
        Controller_Commande_Secretaire.modifier = modifier;
    }

    public static void setData_choix_client(ObservableList<String> data_choix_client) {
        Controller_Commande_Secretaire.data_choix_client = data_choix_client;
    }

    public static boolean isPrise_en_compte_MAG() {
        return prise_en_compte_MAG;
    }

    public static void setPrise_en_compte_MAG(boolean prise_en_compte_MAG) {
        Controller_Commande_Secretaire.prise_en_compte_MAG = prise_en_compte_MAG;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation
        data_Tableau_Secretaire_ObservableList = FXCollections.observableArrayList();
        erreur_Label = new Label();
        apres_MAG_Vbox.getChildren().add(erreur_Label);
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        bdd = new Bdd();
        setId_max(getId_max() + 1);
        //tableview configuration
        client_TableColonne.setCellValueFactory(new PropertyValueFactory<>("client"));
        numero_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("numero_commande"));
        date_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        reference_SAP_TableColonne.setCellValueFactory(new PropertyValueFactory<>("reference_SAP"));
        numero_Article_TableColonne.setCellValueFactory(new PropertyValueFactory<>("numero_article"));
        quantite_Commande_TableColonne.setCellValueFactory(new PropertyValueFactory<>("quantite_commande"));
        observation_TableColonne.setCellValueFactory(new PropertyValueFactory<>("observation"));
        //recuperation client
        bdd.connexion();
        bdd.recuperation_Client();
        choix_Client_Commande_ComboBox.setItems(data_choix_client);
        //recuperation information si modifier une commande
        if (modifier) {
            ligne_Vbox.setVisible(false);
            ajout_Article_Bouton.setVisible(false);
            tableau_Hbox.setVisible(false);
            finalisation_Avant_Apres_Prise_En_Compte_MAG_Bouton.setText("Valider");
            if (!prise_en_compte_MAG) {
                avant_Prise_En_Compte_MAG_RadioBouton.setSelected(true);
                apres_Prise_En_Compte_MAG_RadioBouton.setDisable(true);
                apres_MAG_Vbox.setDisable(true);
            } else {
                apres_Prise_En_Compte_MAG_RadioBouton.setSelected(true);
                avant_MAG_Vbox.setDisable(true);
            }
            commande_Numero_Label.setText(String.valueOf(commande.getId_commandeSAV()));
            choix_Client_Commande_ComboBox.setValue(commande.getClient());
            String[] date_commande = commande.getDate_commande().split("/");
            date_Commande_DatePicker.setValue(LocalDate.of(Integer.parseInt(date_commande[2]), Integer.parseInt(date_commande[1]), Integer.parseInt(date_commande[0])));
            numero_commande_TextField.setText(commande.getNumero_commande());
            reference_SAP_TextField.setText(commande.getReference_SAP());
            numero_article_TextField.setText(commande.getNumero_article());
            quantite_commande_TextField.setText(String.valueOf(commande.getQuantite_commande()));
            bl_Checkbox.setSelected(commande.isBl());
            observation_TextField.setText(commande.getObservation());
            livre_CheckBox.setSelected(commande.isLivre());
            if (commande.getRetard() == null) {
                retard_TextField.setText("");
            } else {
                retard_TextField.setText(commande.getRetard());
            }
            annule_CheckBox.setSelected(commande.isAnnule());
            if (livre_CheckBox.isSelected()) {
                annule_CheckBox.setDisable(true);
            } else {
                annule_CheckBox.setDisable(false);
            }
            if (annule_CheckBox.isSelected()) {
                livre_CheckBox.setDisable(true);
                retard_TextField.setDisable(true);
            } else {
                livre_CheckBox.setDisable(false);
                retard_TextField.setDisable(false);
            }

        } else {
            apres_Prise_En_Compte_MAG_Vbox.setVisible(false);
            apres_MAG_Vbox.setVisible(false);
            finalisation_Avant_Apres_Prise_En_Compte_MAG_Bouton.setText("Finaliser");
            commande_Numero_Label.setText(String.valueOf(getId_max()));
        }
        avant_apres_ToggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (apres_Prise_En_Compte_MAG_RadioBouton.isSelected()) {
                avant_MAG_Vbox.setDisable(true);
            } else {
                avant_MAG_Vbox.setDisable(false);
            }
            if (avant_Prise_En_Compte_MAG_RadioBouton.isSelected()) {
                apres_MAG_Vbox.setDisable(true);
            } else {
                apres_MAG_Vbox.setDisable(false);
            }
        });

        livre_CheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {

            if (livre_CheckBox.isSelected()) {
                annule_CheckBox.setDisable(true);
            } else {
                annule_CheckBox.setDisable(false);
            }


        });
        annule_CheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (annule_CheckBox.isSelected()) {
                livre_CheckBox.setDisable(true);
                retard_TextField.setDisable(true);
                retard_TextField.setText("Annulé");
            } else {
                livre_CheckBox.setDisable(false);
                retard_TextField.setDisable(false);
                retard_TextField.setText("");
            }
        });

        //suppression ligne si une ligne est selectionner
        tableau_Commande.getSelectionModel().selectedIndexProperty().addListener((obs, ancienne_selection, nouvelle_selection) -> {
            if (nouvelle_selection != null && ancienne_selection != null) {
                supression_Article_Bouton.setDisable(false);
                modifier_Article_Bouton.setDisable(false);
                commande_Numero_Label.setText(String.valueOf(tableau_Commande.getSelectionModel().getSelectedItem().getId_commandeSAV()));
                choix_Client_Commande_ComboBox.setValue(tableau_Commande.getSelectionModel().getSelectedItem().getClient());
                numero_commande_TextField.setText(tableau_Commande.getSelectionModel().getSelectedItem().getNumero_commande());
                String[] date_commande_tableau = tableau_Commande.getSelectionModel().getSelectedItem().getDate_commande().split("/");
                LocalDate date_commande = LocalDate.of(Integer.parseInt(date_commande_tableau[2]), Integer.parseInt(date_commande_tableau[1]), Integer.parseInt(date_commande_tableau[0]));
                date_Commande_DatePicker.setValue(date_commande);
                reference_SAP_TextField.setText(tableau_Commande.getSelectionModel().getSelectedItem().getReference_SAP());
                numero_article_TextField.setText(tableau_Commande.getSelectionModel().getSelectedItem().getNumero_article());
                quantite_commande_TextField.setText(String.valueOf(tableau_Commande.getSelectionModel().getSelectedItem().getQuantite_commande()));
                observation_TextField.setText(tableau_Commande.getSelectionModel().getSelectedItem().getObservation());
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //Permettre a l'utilisateur de retourner dans le main de l'aplication
    public void retour_Bouton_Action(ActionEvent actionEvent) {
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //finalisation de la commande
    public void finalisation_Avant_Apres_Prise_En_Compte_MAG_Bouton_Action(ActionEvent actionEvent) {
        data_choix_client = FXCollections.observableArrayList();
        //non modifier
        if (!modifier) {
            for (int i = 0; i < tableau_Commande.getItems().size(); i++) {
                Commande commande = tableau_Commande.getItems().get(i);

                Commande nouvelle_commande = new Commande(commande.getClient()
                        , commande.getId_commandeSAV(), commande.getNumero_commande()
                        , commande.getDate_commande(), commande.getDate_saisi(), commande.getReference_SAP()
                        , commande.getNumero_article(), commande.getQuantite_commande()
                        , commande.getQuantite_livrer(), commande.getObservation());

                System.out.println("Nouvelle commande :\n" +
                        "\tClient:" + commande.getClient() +
                        "\n\tNumero commande: " + commande.getNumero_article() +
                        "\n\tDate Commande: " + commande.getDate_commande() +
                        "\n\tDate Saisi: " + commande.getDate_saisi() +
                        "\n\tReference SAP: " + commande.getReference_SAP() +
                        "\n\tNumero article: " + commande.getNumero_article() +
                        "\n\tQuantite commande: " + commande.getQuantite_commande() +
                        "\n\tQuantite livre: " + commande.getQuantite_livrer() +
                        "\n\tObservation: " + commande.getObservation());
                mise_En_FXML_Tableau_Main(nouvelle_commande);
            }
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();

            //modifier
        } else {
            String valeur_date_livraison;
            boolean client_check, valeur_date_commande_check, numero_commande_check, date_commande_check, reference_SAP_check, quantite_commande_check;
            data_choix_client = FXCollections.observableArrayList();
            String valeur_date_commande = null;
            //check datepicker
            if (date_Commande_DatePicker.getValue() != null) {
                valeur_date_commande = dateTimeFormatter.format(date_Commande_DatePicker.getValue());
                date_Commande_DatePicker.getStyleClass().remove("erreur");
                valeur_date_commande_check = true;
            } else {
                date_Commande_DatePicker.getStyleClass().add("erreur");
                valeur_date_commande_check = false;
            }
            if (choix_Client_Commande_ComboBox.getValue() == null) {
                choix_Client_Commande_ComboBox.setValue(null);
                choix_Client_Commande_ComboBox.setPromptText("Champs Requis");
                choix_Client_Commande_ComboBox.getStyleClass().add("erreur");
                client_check = false;
            } else {
                choix_Client_Commande_ComboBox.getStyleClass().remove("erreur");
                client_check = true;
            }

            //check numero commande
            if (numero_commande_TextField.getText() == null || numero_commande_TextField.getText().trim().isEmpty()) {
                numero_commande_TextField.setPromptText("Champs Requis");
                numero_commande_TextField.getStyleClass().add("erreur");
                numero_commande_check = false;
            } else {
                numero_commande_TextField.getStyleClass().remove("erreur");
                numero_commande_check = true;
            }
            //check date commande
            if (date_Commande_DatePicker.getValue() == null) {
                date_Commande_DatePicker.setPromptText("Champs Requis");
                date_Commande_DatePicker.getStyleClass().add("erreur");
                date_commande_check = false;
            } else {
                date_Commande_DatePicker.getStyleClass().remove("erreur");
                date_commande_check = true;
            }
            //check reference sap
            if (reference_SAP_TextField.getText() == null || reference_SAP_TextField.getText().trim().isEmpty()) {
                reference_SAP_TextField.setPromptText("Champs Requis");
                reference_SAP_TextField.getStyleClass().add("erreur");
                reference_SAP_check = false;
            } else {
                reference_SAP_TextField.getStyleClass().remove("erreur");
                reference_SAP_check = true;
            }
            //check numero article
            if (numero_article_TextField.getText() == null || numero_article_TextField.getText().trim().isEmpty()) {
                numero_article_TextField.setText("Non precisé");
            }

            //check quantite commande
            if (quantite_commande_TextField.getText() == null || quantite_commande_TextField.getText().trim().isEmpty()) {
                quantite_commande_TextField.setPromptText("Champs Requis");
                quantite_commande_TextField.getStyleClass().add("erreur");
                quantite_commande_check = false;
            } else {
                try {
                    Integer.parseInt(quantite_commande_TextField.getText());
                    quantite_commande_TextField.getStyleClass().remove("erreur");
                    quantite_commande_check = true;
                } catch (NumberFormatException e) {
                    quantite_commande_TextField.getStyleClass().add("erreur");
                    quantite_commande_TextField.clear();
                    quantite_commande_TextField.setPromptText("Veuillez saisir que des chiffres");
                    quantite_commande_check = false;
                }
            }
            //check observation

            if (observation_TextField.getText() == null) {
                observation_TextField.setText("Aucune observation");
            }
            //check combobox client

            if (choix_Client_Commande_ComboBox.getValue() == null) {
                choix_Client_Commande_ComboBox.setPromptText("Champs Requis");
                choix_Client_Commande_ComboBox.getStyleClass().add("erreur");
            } else {
                choix_Client_Commande_ComboBox.getStyleClass().remove("erreur");
                client_check = true;
            }

            //si pris en compte avant ou apres
            if (valeur_date_commande_check && client_check && date_commande_check && numero_commande_check && quantite_commande_check && reference_SAP_check) {
                if (apres_Prise_En_Compte_MAG_RadioBouton.isSelected()) {
                    boolean livre_check = false, annule_check = false, bl_check = false;
                    if (bl_Checkbox.isSelected()) {
                        bl_check = true;
                        //si livrer
                        if (livre_CheckBox.isSelected()) {
                            //remplir retard si vide
                            if (retard_TextField.getText() == null || retard_TextField.getText().isEmpty()) {
                                retard_TextField.setText("Aucun retard");
                            }
                            livre_check = true;
                        } else if (annule_CheckBox.isSelected()) {
                            annule_check = true;
                        } else {
                            erreur_Label.setText("champs requis incorrect");
                            livre_CheckBox.getStyleClass().add("erreur");
                            annule_CheckBox.getStyleClass().add("erreur");
                        }
                    } else {
                        if (annule_CheckBox.isSelected() || livre_CheckBox.isSelected()) {
                            bl_Checkbox.getStyleClass().add("erreur");
                        }
                    }

                    //si livrer ou annule
                    if (bl_check) {
                        if (livre_check || annule_check) {
                            if (livre_check) {
                                valeur_date_livraison = dateTimeFormatter.format(LocalDate.now());
                            } else {
                                valeur_date_livraison = "Annulé";
                            }
                            String temps_Gestion_Commande = "";
                            if (commande.getDate_saisi() != null && !annule_CheckBox.isSelected() && !valeur_date_livraison.equals("Annulé")) {
                                if (!commande.getDate_saisi().isEmpty()) {
                                    Statistique statistique = new Statistique(commande.getDate_saisi(), valeur_date_livraison);
                                    temps_Gestion_Commande = statistique.temps_gestion_commande() + "/dJours";
                                    commande.setTemps_gestion_commande(temps_Gestion_Commande);
                                }
                            }

                            Commande mise_a_jour_commande = new Commande(choix_Client_Commande_ComboBox.getValue()
                                    , Integer.parseInt(commande_Numero_Label.getText()), numero_commande_TextField.getText()
                                    , commande.getDate_commande(), commande.getDate_saisi(), reference_SAP_TextField.getText()
                                    , numero_article_TextField.getText(), Integer.parseInt(quantite_commande_TextField.getText())
                                    , Integer.parseInt(quantite_commande_TextField.getText()), bl_Checkbox.isSelected(), livre_CheckBox.isSelected()
                                    , valeur_date_livraison, temps_Gestion_Commande, observation_TextField.getText(), retard_TextField.getText()
                                    , annule_CheckBox.isSelected());

                            System.out.println("Mise a jour commande :\n" +
                                    "\tClient:" + choix_Client_Commande_ComboBox.getValue() +
                                    "\n\tNumero commande: " + numero_commande_TextField.getText() +
                                    "\n\tDate Commande: " + date_Commande_DatePicker.getValue() +
                                    "\n\tDate Saisi: " + commande.getDate_saisi() +
                                    "\n\tReference SAP: " + reference_SAP_TextField.getText() +
                                    "\n\tNumero article: " + numero_article_TextField.getText() +
                                    "\n\tQuantite commande: " + quantite_commande_TextField.getText() +
                                    "\n\tQuantite livre: " + quantite_commande_TextField.getText() +
                                    "\n\tBl: " + bl_Checkbox.isSelected() +
                                    "\tLivre:" + livre_CheckBox.isSelected() +
                                    "\n\tDate livraison: " + valeur_date_livraison +
                                    "\n\tTemps de gestion: " + temps_Gestion_Commande +
                                    "\n\tRetard : " + retard_TextField.getText() +
                                    "\n\tObservation: " + observation_TextField.getText() +
                                    "\n\tAnnule: " + annule_CheckBox.isSelected());

                            mise_En_FXML_Tableau_Main(mise_a_jour_commande);
                            commande.setClient(choix_Client_Commande_ComboBox.getValue());
                            commande.setNumero_commande(numero_commande_TextField.getText());
                            commande.setDate_commande(valeur_date_commande);
                            commande.setReference_SAP(reference_SAP_TextField.getText());
                            commande.setNumero_article(numero_article_TextField.getText());
                            commande.setQuantite_commande(Integer.parseInt(quantite_commande_TextField.getText()));
                            commande.setQuantite_livrer(Integer.parseInt(quantite_commande_TextField.getText()));
                            commande.setBl(bl_Checkbox.isSelected());
                            commande.setLivre(livre_CheckBox.isSelected());
                            commande.setDate_livraison(valeur_date_livraison);
                            commande.setRetard(retard_TextField.getText());
                            commande.setObservation(observation_TextField.getText());
                            commande.setAnnule(annule_CheckBox.isSelected());
                            controller_main.tableau_Commande.refresh();
                            source = (Node) actionEvent.getSource();
                            stage = (Stage) source.getScene().getWindow();
                            stage.close();

                        } else {

                        }
                        prise_en_compte_Secretaire = true;
                        //prise en compte secretaire
                        Commande prise_en_compte = new Commande(bl_Checkbox.isSelected(), Integer.parseInt(commande_Numero_Label.getText()));
                        System.out.println("Mise a jour commande :\n" +
                                "\n\tBl: " + bl_Checkbox.isSelected());
                        mise_En_FXML_Tableau_Main(prise_en_compte);

                        commande.setBl(bl_Checkbox.isSelected());

                        controller_main.tableau_Commande.refresh();
                        source = (Node) actionEvent.getSource();
                        stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }


                } else if (avant_Prise_En_Compte_MAG_RadioBouton.isSelected()) {

                    if (commande.isPrise_en_compte_MAG()) {
                        commande.setPrise_en_compte_MAG(false);
                        commande.setPret(0);
                        commande.setLivre(false);
                        commande.setDate_livraison(null);
                        commande.setDate_pret(null);
                        commande.setTemps_gestion_commande(null);
                    }

                    if (commande.getDate_livraison() == null) {
                        commande.setDate_livraison("");
                    }

                    Commande mise_a_jour_commande = new Commande(choix_Client_Commande_ComboBox.getValue()
                            , Integer.parseInt(commande_Numero_Label.getText()), numero_commande_TextField.getText()
                            , commande.getDate_commande(), commande.getDate_saisi(), reference_SAP_TextField.getText()
                            , numero_article_TextField.getText(), Integer.parseInt(quantite_commande_TextField.getText())
                            , Integer.parseInt(quantite_commande_TextField.getText()), bl_Checkbox.isSelected(), commande.isLivre()
                            , commande.getDate_livraison(), commande.getTemps_gestion_commande(), observation_TextField.getText(), retard_TextField.getText()
                            , annule_CheckBox.isSelected());

                    System.out.println("Mise a jour commande :\n" +
                            "\tClient:" + choix_Client_Commande_ComboBox.getValue() +
                            "\n\tNumero commande: " + numero_commande_TextField.getText() +
                            "\n\tDate Commande: " + date_Commande_DatePicker.getValue() +
                            "\n\tDate Saisi: " + commande.getDate_saisi() +
                            "\n\tReference SAP: " + reference_SAP_TextField.getText() +
                            "\n\tNumero article: " + numero_article_TextField.getText() +
                            "\n\tQuantite commande: " + quantite_commande_TextField.getText() +
                            "\n\tQuantite livre: " + quantite_commande_TextField.getText() +
                            "\n\tBl: " + bl_Checkbox.isSelected() +
                            "\tLivre:" + commande.getDate_pret() +
                            "\n\tDate livraison: " + commande.getDate_livraison() +
                            "\n\tTemps de gestion: " + commande.getTemps_gestion_commande() +
                            "\n\tRetard : " + commande.getRetard() +
                            "\n\tObservation: " + commande.getObservation() +
                            "\n\tAnnule: " + commande.isAnnule());

                    mise_En_FXML_Tableau_Main(mise_a_jour_commande);
                    //mise a jour  des valeurs du tableau
                    commande.setClient(choix_Client_Commande_ComboBox.getValue());
                    commande.setNumero_commande(numero_commande_TextField.getText());
                    commande.setDate_commande(valeur_date_commande);
                    commande.setReference_SAP(reference_SAP_TextField.getText());
                    commande.setNumero_article(numero_article_TextField.getText());
                    commande.setQuantite_commande(Integer.parseInt(quantite_commande_TextField.getText()));
                    commande.setQuantite_livrer(Integer.parseInt(quantite_commande_TextField.getText()));
                    commande.setLivre(livre_CheckBox.isSelected());
                    commande.setDate_livraison(commande.getDate_livraison());
                    commande.setRetard(commande.getRetard());
                    commande.setObservation(commande.getObservation());
                    commande.setAnnule(commande.isAnnule());

                    //refresh tableau plus fermeture de la fenetre
                    controller_main.tableau_Commande.refresh();
                    source = (Node) actionEvent.getSource();
                    stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //affichage dans le tableau Main via un thread
    protected void mise_En_FXML_Tableau_Main(Commande commande) {
        Runnable command = () -> {
            //ajout au controller
            controller_main.tableau_Commande.setEditable(true);
            if (modifier) {
                if (!prise_en_compte_Secretaire) {
                    bdd.mise_a_jour_bdd_commande_secretaire(commande.getClient(), commande.getId_commandeSAV(), commande.getNumero_commande(), commande.getDate_commande(), commande.getDate_saisi(), commande.getReference_SAP(), commande.getNumero_article(), commande.getQuantite_commande(), commande.getQuantite_livrer(), commande.isBl(), commande.isLivre(), commande.getDate_livraison(), commande.getTemps_gestion_commande(), commande.getObservation(), commande.getRetard(), commande.isAnnule());
                } else {
                    bdd.prise_en_compte_Secretaire(commande.isBl(), commande.getId_commandeSAV());
                }
                System.out.println("Commande numero: " + commande_Numero_Label.getText() + " modifier!");
            } else {
                bdd.ajout_bdd_commande(commande.getClient(), commande.getId_commandeSAV(), commande.getNumero_commande(), commande.getDate_commande(), commande.getDate_saisi(), commande.getReference_SAP(), commande.getNumero_article(), commande.getQuantite_commande(), commande.getQuantite_livrer(), commande.getObservation());
                Bdd.getData_commande().add(commande);
                System.out.println("Commande numero: " + commande_Numero_Label.getText() + " ajouter!");
            }
            controller_main.tableau_Commande.setEditable(false);
            System.out.println("-------------------------------------------------------");
        };
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //affichage dans le tableau secretaire via un thread
    protected void mise_En_FXML_Tableau_Secretaire(Commande commande) {
        Runnable command = () -> {
            //ajout au controller
            tableau_Commande.getItems().add(commande);
            tableau_Commande.refresh();
            choix_Client_Commande_ComboBox.setValue(commande.getClient());
            numero_commande_TextField.setText(commande.getNumero_commande());
            date_Commande_DatePicker.setDisable(false);
            choix_Client_Commande_ComboBox.setEditable(false);
            numero_commande_TextField.setEditable(false);
            System.out.println("article numero: " + commande_Numero_Label.getText() + " ajouter!");
            System.out.println("-------------------------------------------------------");
        };
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


    //ajout d'un article
    public void ajout_Article_Bouton_Action(ActionEvent actionEvent) {
        boolean client_check, valeur_date_commande_check, numero_commande_check, date_commande_check, reference_SAP_check, quantite_commande_check;
        data_choix_client = FXCollections.observableArrayList();
        String valeur_date_commande = null;
        //check datepicker
        if (date_Commande_DatePicker.getValue() != null) {
            valeur_date_commande = dateTimeFormatter.format(date_Commande_DatePicker.getValue());
            date_Commande_DatePicker.getStyleClass().remove("erreur");
            valeur_date_commande_check = true;
        } else {
            date_Commande_DatePicker.getStyleClass().add("erreur");
            valeur_date_commande_check = false;
        }
        if (choix_Client_Commande_ComboBox.getValue() == null) {
            choix_Client_Commande_ComboBox.setValue(null);
            choix_Client_Commande_ComboBox.setPromptText("Champs Requis");
            choix_Client_Commande_ComboBox.getStyleClass().add("erreur");
            client_check = false;
        } else {
            choix_Client_Commande_ComboBox.getStyleClass().remove("erreur");
            client_check = true;
        }

        //check numero commande
        if (numero_commande_TextField.getText() == null || numero_commande_TextField.getText().trim().isEmpty()) {
            numero_commande_TextField.setPromptText("Champs Requis");
            numero_commande_TextField.getStyleClass().add("erreur");
            numero_commande_check = false;
        } else {
            numero_commande_TextField.getStyleClass().remove("erreur");
            numero_commande_check = true;
        }
        //check date commande
        if (date_Commande_DatePicker.getValue() == null) {
            date_Commande_DatePicker.setPromptText("Champs Requis");
            date_Commande_DatePicker.getStyleClass().add("erreur");
            date_commande_check = false;
        } else {
            date_Commande_DatePicker.getStyleClass().remove("erreur");
            date_commande_check = true;
        }
        //check reference sap
        if (reference_SAP_TextField.getText() == null || reference_SAP_TextField.getText().trim().isEmpty()) {
            reference_SAP_TextField.setPromptText("Champs Requis");
            reference_SAP_TextField.getStyleClass().add("erreur");
            reference_SAP_check = false;
        } else {
            reference_SAP_TextField.getStyleClass().remove("erreur");
            reference_SAP_check = true;
        }
        //check numero article
        if (numero_article_TextField.getText() == null || numero_article_TextField.getText().trim().isEmpty()) {
            numero_article_TextField.setText("Non precisé");
        }

        //check quantite commande
        if (quantite_commande_TextField.getText() == null || quantite_commande_TextField.getText().trim().isEmpty()) {
            quantite_commande_TextField.setPromptText("Champs Requis");
            quantite_commande_TextField.getStyleClass().add("erreur");
            quantite_commande_check = false;
        } else {
            try {
                Integer.parseInt(quantite_commande_TextField.getText());
                quantite_commande_TextField.getStyleClass().remove("erreur");
                quantite_commande_check = true;
            } catch (NumberFormatException e) {
                quantite_commande_TextField.getStyleClass().add("erreur");
                quantite_commande_TextField.clear();
                quantite_commande_TextField.setPromptText("Veuillez saisir que des chiffres");
                quantite_commande_check = false;
            }
        }
        //check observation

        if (observation_TextField.getText() == null) {
            observation_TextField.setText("Aucune observation");
        }
        if (!modifier) {
            //check combobox client

            if (choix_Client_Commande_ComboBox.getValue() == null) {
                choix_Client_Commande_ComboBox.setPromptText("Champs Requis");
                choix_Client_Commande_ComboBox.getStyleClass().add("erreur");
            } else {
                choix_Client_Commande_ComboBox.getStyleClass().remove("erreur");
                client_check = true;
            }
            if (valeur_date_commande_check && client_check && date_commande_check && numero_commande_check && quantite_commande_check && reference_SAP_check) {
                String date_saisi = dateTimeFormatter.format(LocalDateTime.now());

                Commande nouvelle_commande = new Commande(choix_Client_Commande_ComboBox.getValue()
                        , getId_max(), numero_commande_TextField.getText()
                        , valeur_date_commande, date_saisi, reference_SAP_TextField.getText()
                        , numero_article_TextField.getText(), Integer.parseInt(quantite_commande_TextField.getText())
                        , Integer.parseInt(quantite_commande_TextField.getText()), observation_TextField.getText());

                System.out.println("Nouvel article :\n" +
                        "\tClient:" + choix_Client_Commande_ComboBox.getValue() +
                        "\n\tNumero commande: " + getId_max() +
                        "\n\tDate Commande: " + date_Commande_DatePicker.getValue() +
                        "\n\tDate Saisi: " + date_saisi +
                        "\n\tReference SAP: " + reference_SAP_TextField.getText() +
                        "\n\tNumero article: " + numero_article_TextField.getText() +
                        "\n\tQuantite commande: " + quantite_commande_TextField.getText() +
                        "\n\tQuantite livre: " + quantite_commande_TextField.getText() +
                        "\n\tObservation: " + observation_TextField.getText());
                setId_max(getId_max() + 1);
                commande_Numero_Label.setText(String.valueOf(getId_max()));
                data_Tableau_Secretaire_ObservableList.add(nouvelle_commande);
                mise_En_FXML_Tableau_Secretaire(nouvelle_commande);
                reference_SAP_TextField.setText(null);
                numero_article_TextField.setText(null);
                quantite_commande_TextField.setText(null);
                observation_TextField.setText(null);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


    //suppression d'un article
    public void suppression_Article_Bouton_Action(ActionEvent actionEvent) {
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("Supression de ligne");
        alert.setHeaderText("Êtes-vous sur de vouloir supprimer la ligne?");
        optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            tableau_Commande.getItems().remove(tableau_Commande.getSelectionModel().getSelectedItem());
            setId_max(getId_max() - 1);
            supression_Article_Bouton.setDisable(true);
        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //modification d'un article
    public void modifier_Article_Bouton_Action(ActionEvent actionEvent) {
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("modification de ligne");
        alert.setHeaderText("Êtes-vous sur de vouloir modifier la ligne?");
        optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            //modification
            Commande commande = tableau_Commande.getSelectionModel().getSelectedItem();
            commande.setReference_SAP(reference_SAP_TextField.getText());
            commande.setNumero_article(numero_article_TextField.getText());
            commande.setQuantite_commande(Integer.parseInt(quantite_commande_TextField.getText()));
            commande.setObservation(observation_TextField.getText());
            tableau_Commande.refresh();
        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
}
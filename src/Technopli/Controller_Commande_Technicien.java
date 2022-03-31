package Technopli;

import Technopli.classe.Bdd;
import Technopli.classe.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Commande_Technicien implements Initializable {
    //private
    private static Commande commande;
    private static boolean modifier = false;
    private static int id_max;
    private static Controller_Main controller_main;
    //initialisation
    //fxml

    public Optional<ButtonType> optional;

    public Button retour_Bouton;
    public DateTimeFormatter dateTimeFormatter;
    public Button appliquer_Bouton;
    public TextField pret_TextField;
    public CheckBox prise_En_Compte_MAG_Checkbox;
    public Label commande_Numero, information_Label;

    //variable
    public Bdd bdd;
    public Node source;
    public Stage stage;

    public static void setController_main(Controller_Main controller_main) {
        Controller_Commande_Technicien.controller_main = controller_main;
    }

    public static int getId_max() {
        return id_max;
    }

    public static void setId_max(int id_max) {
        Controller_Commande_Technicien.id_max = id_max;
    }

    public static void setCommande(Commande commande) {
        Controller_Commande_Technicien.commande = commande;
    }

    public static void setModifier(boolean modifier) {
        Controller_Commande_Technicien.modifier = modifier;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        bdd = new Bdd();
        bdd.connexion();
        //recuperation information si modifier une commande
        commande_Numero.setText(String.valueOf(commande.getId_commandeSAV()));
        prise_En_Compte_MAG_Checkbox.setSelected(commande.isPrise_en_compte_MAG());
        pret_TextField.setText(String.valueOf(commande.getPret()));
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

    //application des modification du technicien
    public void appliquer_Bouton_Action(ActionEvent actionEvent) {
        String date_pret = "";
        boolean prise_en_compte_Mag_Check, pret_Check = false, pret_inferieur_Check = false;
        //si prise en compte mag est select
        if (prise_En_Compte_MAG_Checkbox.isSelected()) {
            prise_En_Compte_MAG_Checkbox.getStyleClass().removeAll("erreur");
            prise_en_compte_Mag_Check = true;
            //si pret n'est pas vide
            if (pret_TextField.getText() != null && !pret_TextField.getText().equals("0")) {
                pret_TextField.getStyleClass().remove("erreur");
                //si pret est in int
                try {
                    Integer.parseInt(pret_TextField.getText());
                    pret_TextField.getStyleClass().remove("erreur");
                    //si pret est superrieur a 0 et inferieur ou  egal a la commande
                    if (Integer.parseInt(pret_TextField.getText()) > 0 && Integer.parseInt(pret_TextField.getText()) <= commande.getQuantite_livrer()) {
                        pret_TextField.getStyleClass().remove("erreur");
                        pret_Check = true;
                        //si pret inferieur a la commande creation nouvelle commande
                        if (Integer.parseInt(pret_TextField.getText()) > 0 && Integer.parseInt(pret_TextField.getText()) < commande.getQuantite_livrer()) {
                            if (commande.getQuantite_livrer() - Integer.parseInt(pret_TextField.getText()) != 0) {
                                pret_inferieur_Check = true;
                            }
                        }
                    } else {
                        pret_TextField.getStyleClass().add("erreur");
                        pret_TextField.setText(null);
                        pret_TextField.setPromptText("valeur incorrect");
                    }
                } catch (NumberFormatException e) {
                    pret_TextField.getStyleClass().add("erreur");
                    pret_TextField.clear();
                    pret_TextField.setPromptText("Veuillez saisir que des chiffres");
                }
            } else {
                pret_TextField.getStyleClass().add("erreur");
            }
        } else {
            prise_en_compte_Mag_Check = false;
        }

        //si check
        if (prise_en_compte_Mag_Check) {
            if (pret_Check) {
                date_pret = dateTimeFormatter.format(LocalDateTime.now());
                if (pret_inferieur_Check) {
                    Commande nouvelle_commande = new Commande(commande.getClient()
                            , Bdd.getData_commande().get(Bdd.getData_commande().size() - 1).getId_commandeSAV() + 1, commande.getNumero_commande()
                            , commande.getDate_commande(), commande.getDate_saisi(), commande.getReference_SAP()
                            , commande.getNumero_article(), commande.getQuantite_commande()
                            , commande.getQuantite_livrer() - Integer.parseInt(pret_TextField.getText()), commande.getObservation());

                    System.out.println("Nouvelle commande :\n" + (Bdd.getData_commande().get(Bdd.getData_commande().size() - 1).getId_commandeSAV() + 1) +
                            "\tClient:" + commande.getClient() +
                            "\n\tNumero commande: " + commande.getNumero_commande() +
                            "\n\tDate Commande: " + commande.getDate_commande() +
                            "\n\tDate Saisi: " + commande.getDate_saisi() +
                            "\n\tReference SAP: " + commande.getReference_SAP() +
                            "\n\tNumero article: " + commande.getNumero_article() +
                            "\n\tQuantite commande: " + commande.getQuantite_commande() +
                            "\n\tQuantite livre: " + (commande.getQuantite_livrer() - Integer.parseInt(pret_TextField.getText())) +
                            "\n\tObservation: " + commande.getObservation());

                    bdd.ajout_bdd_commande(nouvelle_commande.getClient(), nouvelle_commande.getId_commandeSAV(), nouvelle_commande.getNumero_commande(), nouvelle_commande.getDate_commande(), nouvelle_commande.getDate_saisi(), nouvelle_commande.getReference_SAP(), nouvelle_commande.getNumero_article(), nouvelle_commande.getQuantite_commande(), nouvelle_commande.getQuantite_livrer(), nouvelle_commande.getObservation());
                    Bdd.getData_commande().add(nouvelle_commande);
                    System.out.println("Commande numero: " + nouvelle_commande.getId_commandeSAV() + " ajouter!");
                }
            }

            bdd.mise_a_jour_bdd_commande_technicien(commande.getId_commandeSAV(), prise_En_Compte_MAG_Checkbox.isSelected(), pret_TextField.getText(), date_pret);
            commande.setPrise_en_compte_MAG(prise_En_Compte_MAG_Checkbox.isSelected());
            commande.setPret(Integer.parseInt(pret_TextField.getText()));
            commande.setDate_pret(date_pret);
            controller_main.tableau_Commande.refresh();
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            //si prise en compte mag n'es pas coche mais qu'il y a des données dans le reste
            if (pret_TextField.getText() != null && !pret_TextField.getText().equals("0")) {
                prise_En_Compte_MAG_Checkbox.getStyleClass().add("erreur");
            } else {
                prise_En_Compte_MAG_Checkbox.getStyleClass().remove("erreur");
                bdd.mise_a_jour_bdd_commande_technicien(commande.getId_commandeSAV(), prise_En_Compte_MAG_Checkbox.isSelected(), pret_TextField.getText(), date_pret);
                commande.setPrise_en_compte_MAG(prise_En_Compte_MAG_Checkbox.isSelected());
                commande.setPret(Integer.parseInt(pret_TextField.getText()));
                commande.setDate_pret(date_pret);
                controller_main.tableau_Commande.refresh();
                source = (Node) actionEvent.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        }

    }
}

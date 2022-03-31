package Technopli;

import Technopli.classe.Bdd;
import Technopli.classe.Client;
import Technopli.classe.Transporteur;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Client implements Initializable {
    //private
    private static ObservableList<Client> data_client;
    private static ObservableList<String> data_choix_client;
    private static ObservableList<Transporteur> data_transporteur;
    private static ObservableList<String> data_choix_transporteur;
    //initialisation
    //fxml

    public TextField nom_Client_TextField, service_Facturation_Client_TextField, adresse_Facturation_Client_TextField, complement_Adresse_Facturation_Client_TextField,
            pays_Facturation_Client_TextField, tel_Facturation_Client_TextField, fax_Facturation_Client_TextField, email_Facturation_Client_TextField,
            adresse_Livraison_Client_TextField, ville_Livraison_Client_TextField, pays_Livraison_Client_TextField, complement_Adresse_Livraison_Client_TextField,
            nom_Condition_Paiement_Client_TextField, email_Condition_Paiement_Client_TextField, tel_Condition_Paiement_Client_TextField, fax_Condition_Paiement_Client_TextField,
            observation_Transport_Client_TextField, nouveau_Transporteur_Client_TextField;
    public ComboBox<String> transporteur_Client_ComboBox,choix_Modifier_Client_ComboBox;
    public Button ajout_Transporteur_Client_Bouton, ajout_Modifier_Client_Bouton, retour_Bouton, suppression_Client_Bouton, suppression_Transporteur_Bouton;
    public CheckBox nouveau_Client_Checkbox;
    public Optional<ButtonType> optional;
    public Alert alert;
    public Label information_Label;

    //variable
    public Node source;
    public Stage stage;
    public Bdd bdd;
    public String transporteur;
    public int id_client,nombre_tranporteur, nombre_client;


    public static void setData_client(ObservableList<Client> data_client) {
        Controller_Client.data_client = data_client;
    }

    public static void setData_choix_client(ObservableList<String> data_choix_client) {
        Controller_Client.data_choix_client = data_choix_client;
    }

    public static void setData_transporteur(ObservableList<Transporteur> data_transporteur) {
        Controller_Client.data_transporteur = data_transporteur;
    }

    public static void setData_choix_transporteur(ObservableList<String> data_choix_transporteur) {
        Controller_Client.data_choix_transporteur = data_choix_transporteur;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialisation
        bdd = new Bdd();
        bdd.connexion();

        //recupertaion des donnée des clients et transporteur
        bdd.recuperation_Client();
        choix_Modifier_Client_ComboBox.setItems(data_choix_client);
        transporteur_Client_ComboBox.setItems(data_choix_transporteur);
        //element disable /visible
        suppression_Client_Bouton.setDisable(true);
        ajout_Modifier_Client_Bouton.setDisable(true);
        if (data_choix_client.size() == 0) {
            choix_Modifier_Client_ComboBox.setDisable(true);
            choix_Modifier_Client_ComboBox.setPromptText("Aucun client");
            nouveau_Client_Checkbox.setSelected(true);
            nouveau_Client_Checkbox.setDisable(true);
        } else {
            //nombre total de client et de transporteur
            nombre_client = data_client.get(data_client.size() - 1).getId_client() + 1;
            if (data_choix_transporteur.size() == 0) {
                transporteur_Client_ComboBox.setDisable(true);
                transporteur_Client_ComboBox.setPromptText("Aucun transporteur");
            } else {
                nombre_tranporteur = data_choix_transporteur.size() + 1;
            }

        }


        //choix client combobox
        choix_Modifier_Client_ComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                suppression_Client_Bouton.setDisable(false);
                ajout_Modifier_Client_Bouton.setDisable(false);
            }
            for (Client client : data_client) {
                if (choix_Modifier_Client_ComboBox.getValue().equals(client.getNom_client())) {
                    id_client = client.getId_client();
                    nom_Client_TextField.setText(client.getNom_client());
                    service_Facturation_Client_TextField.setText(client.getService_facturation_client());
                    adresse_Facturation_Client_TextField.setText(client.getAdresse_facturation_client());
                    complement_Adresse_Facturation_Client_TextField.setText(client.getComplement_adresse_facturation_client());
                    pays_Facturation_Client_TextField.setText(client.getPays_facturation_client());
                    tel_Facturation_Client_TextField.setText(client.getTel_facturation_client());
                    fax_Facturation_Client_TextField.setText(client.getFax_facturation_client());
                    email_Facturation_Client_TextField.setText(client.getEmail_facturation_client());
                    adresse_Livraison_Client_TextField.setText(client.getComplement_adresse_livraison_client());
                    ville_Livraison_Client_TextField.setText(client.getVille_livraison_client());
                    pays_Livraison_Client_TextField.setText(client.getPays_livraison_client());
                    complement_Adresse_Livraison_Client_TextField.setText(client.getComplement_adresse_livraison_client());
                    nom_Condition_Paiement_Client_TextField.setText(client.getNom_condition_paiement_client());
                    email_Condition_Paiement_Client_TextField.setText(client.getEmail_condition_paiement_client());
                    tel_Condition_Paiement_Client_TextField.setText(client.getTel_condition_paiement_client());
                    fax_Condition_Paiement_Client_TextField.setText(client.getFax_condition_paiement_client());
                    observation_Transport_Client_TextField.setText(client.getObservation_transport_client());
                    for (Transporteur value : data_transporteur) {
                        if (client.getTransporteur_client().equals(value.getNom_transporteur())) {
                            transporteur_Client_ComboBox.setValue(value.getNom_transporteur());
                        }
                    }
                }
            }
        });

        //si le nom de client est deja connu
        nom_Client_TextField.textProperty().addListener((ov, old_val, new_val) -> {
            if (nouveau_Client_Checkbox.isSelected()) {
                information_Label.setText(null);
                if (new_val != null) {
                    for (Client client : data_client) {
                        if (new_val.equals(client.getNom_client())) {
                            choix_Modifier_Client_ComboBox.setDisable(false);
                            choix_Modifier_Client_ComboBox.setValue(client.getNom_client());
                            ajout_Modifier_Client_Bouton.setText("Modifier le client");
                            suppression_Client_Bouton.setDisable(false);
                            nouveau_Client_Checkbox.setSelected(false);
                            information_Label.setText("Le client: " + client.getNom_client() + " existe déjà");
                            nom_Client_TextField.setText(client.getNom_client());
                            service_Facturation_Client_TextField.setText(client.getService_facturation_client());
                            adresse_Facturation_Client_TextField.setText(client.getAdresse_facturation_client());
                            complement_Adresse_Facturation_Client_TextField.setText(client.getComplement_adresse_facturation_client());
                            pays_Facturation_Client_TextField.setText(client.getPays_facturation_client());
                            tel_Facturation_Client_TextField.setText(client.getTel_facturation_client());
                            fax_Facturation_Client_TextField.setText(client.getFax_facturation_client());
                            email_Facturation_Client_TextField.setText(client.getEmail_facturation_client());
                            adresse_Livraison_Client_TextField.setText(client.getComplement_adresse_livraison_client());
                            ville_Livraison_Client_TextField.setText(client.getVille_livraison_client());
                            pays_Livraison_Client_TextField.setText(client.getPays_livraison_client());
                            complement_Adresse_Livraison_Client_TextField.setText(client.getComplement_adresse_livraison_client());
                            nom_Condition_Paiement_Client_TextField.setText(client.getNom_condition_paiement_client());
                            email_Condition_Paiement_Client_TextField.setText(client.getEmail_condition_paiement_client());
                            tel_Condition_Paiement_Client_TextField.setText(client.getTel_condition_paiement_client());
                            fax_Condition_Paiement_Client_TextField.setText(client.getFax_condition_paiement_client());
                            observation_Transport_Client_TextField.setText(client.getObservation_transport_client());
                            for (Transporteur value : data_transporteur) {
                                if (client.getTransporteur_client().equals(value.getNom_transporteur())) {
                                    transporteur_Client_ComboBox.setValue(value.getNom_transporteur());
                                }
                            }
                        }
                    }
                }
            }
        });

        //Si nouveau client
        nouveau_Client_Checkbox.selectedProperty().addListener((ov, old_val, new_val) -> {
            if (new_val != null) {
                suppression_Client_Bouton.setDisable(false);
                ajout_Modifier_Client_Bouton.setDisable(false);
            }
            if (data_choix_client.size() == 0) {
                choix_Modifier_Client_ComboBox.setDisable(true);
                ajout_Modifier_Client_Bouton.setText("Ajouter nouveau client");
                suppression_Client_Bouton.setDisable(true);
            } else {
                if (nouveau_Client_Checkbox.isSelected()) {
                    nom_Client_TextField.setText(null);
                    service_Facturation_Client_TextField.setText(null);
                    adresse_Facturation_Client_TextField.setText(null);
                    complement_Adresse_Facturation_Client_TextField.setText(null);
                    pays_Facturation_Client_TextField.setText(null);
                    tel_Facturation_Client_TextField.setText(null);
                    fax_Facturation_Client_TextField.setText(null);
                    email_Facturation_Client_TextField.setText(null);
                    adresse_Livraison_Client_TextField.setText(null);
                    ville_Livraison_Client_TextField.setText(null);
                    pays_Livraison_Client_TextField.setText(null);
                    complement_Adresse_Livraison_Client_TextField.setText(null);
                    nom_Condition_Paiement_Client_TextField.setText(null);
                    email_Condition_Paiement_Client_TextField.setText(null);
                    tel_Condition_Paiement_Client_TextField.setText(null);
                    fax_Condition_Paiement_Client_TextField.setText(null);
                    observation_Transport_Client_TextField.setText(null);
                    transporteur_Client_ComboBox.setValue(null);
                    transporteur_Client_ComboBox.setPromptText("Choix transporteur");
                    choix_Modifier_Client_ComboBox.setDisable(true);
                    ajout_Modifier_Client_Bouton.setText("Ajouter nouveau client");
                    suppression_Client_Bouton.setDisable(true);
                } else {
                    transporteur_Client_ComboBox.setValue(transporteur);
                    nom_Client_TextField.setText(choix_Modifier_Client_ComboBox.getValue());
                    choix_Modifier_Client_ComboBox.setDisable(false);
                    ajout_Modifier_Client_Bouton.setText("Modifier le client");
                    suppression_Client_Bouton.setDisable(false);
                }
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

    //permettre a l'utilisateur d'ajouter un client ou de modifier un deja existant
    public void ajout_Modifier_Client_Bouton_Action(ActionEvent actionEvent) {
        boolean transporteur_check = false, nom_client_check = false;
        //si nouveau client
        if (nom_Client_TextField.getText() == null || nom_Client_TextField.getText().isEmpty()) {
            nom_Client_TextField.setText(null);
            nom_Client_TextField.setPromptText("Champ requis");
            nom_Client_TextField.getStyleClass().add("erreur");
        } else {
            nom_Client_TextField.getStyleClass().remove("erreur");
            nom_client_check = true;
        }
        if (transporteur_Client_ComboBox.getValue() == null || transporteur_Client_ComboBox.getValue().isEmpty()) {
            transporteur_Client_ComboBox.setValue(null);
            transporteur_Client_ComboBox.setValue("Champ requis");
            transporteur_Client_ComboBox.getStyleClass().add("erreur");
        } else {
            transporteur_Client_ComboBox.getStyleClass().remove("erreur");
            transporteur_check = true;
        }
        if (nom_client_check && transporteur_check) {
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            if (nouveau_Client_Checkbox.isSelected()) {
                alert.setTitle("Ajout client");
                alert.setHeaderText("Êtes-vous sur de vouloir ajouter le client?");
            } else {
                alert.setTitle("modification client");
                alert.setHeaderText("Êtes-vous sur de vouloir modifier le client?");
            }
            optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                //remplissage si vide
                if (service_Facturation_Client_TextField.getText() == null || service_Facturation_Client_TextField.getText().isEmpty()) {
                    service_Facturation_Client_TextField.setText("");
                }
                if (adresse_Facturation_Client_TextField.getText() == null || adresse_Facturation_Client_TextField.getText().isEmpty()) {
                    adresse_Facturation_Client_TextField.setText("");
                }
                if (complement_Adresse_Facturation_Client_TextField.getText() == null || complement_Adresse_Facturation_Client_TextField.getText().isEmpty()) {
                    complement_Adresse_Facturation_Client_TextField.setText("");
                }
                if (pays_Facturation_Client_TextField.getText() == null || pays_Facturation_Client_TextField.getText().isEmpty()) {
                    pays_Facturation_Client_TextField.setText("");
                }
                if (tel_Facturation_Client_TextField.getText() == null || tel_Facturation_Client_TextField.getText().isEmpty()) {
                    tel_Facturation_Client_TextField.setText("");
                }
                if (fax_Facturation_Client_TextField.getText() == null || fax_Facturation_Client_TextField.getText().isEmpty()) {
                    fax_Facturation_Client_TextField.setText("");
                }
                if (email_Facturation_Client_TextField.getText() == null || email_Facturation_Client_TextField.getText().isEmpty()) {
                    email_Facturation_Client_TextField.setText("");
                }
                if (adresse_Livraison_Client_TextField.getText() == null || adresse_Livraison_Client_TextField.getText().isEmpty()) {
                    adresse_Livraison_Client_TextField.setText("");
                }
                if (ville_Livraison_Client_TextField.getText() == null || ville_Livraison_Client_TextField.getText().isEmpty()) {
                    ville_Livraison_Client_TextField.setText("");
                }
                if (pays_Livraison_Client_TextField.getText() == null || pays_Livraison_Client_TextField.getText().isEmpty()) {
                    pays_Livraison_Client_TextField.setText("");
                }
                if (complement_Adresse_Livraison_Client_TextField.getText() == null || complement_Adresse_Livraison_Client_TextField.getText().isEmpty()) {
                    complement_Adresse_Livraison_Client_TextField.setText("");
                }
                if (nom_Condition_Paiement_Client_TextField.getText() == null || nom_Condition_Paiement_Client_TextField.getText().isEmpty()) {
                    nom_Condition_Paiement_Client_TextField.setText("");
                }
                if (email_Condition_Paiement_Client_TextField.getText() == null || email_Condition_Paiement_Client_TextField.getText().isEmpty()) {
                    email_Condition_Paiement_Client_TextField.setText("");
                }
                if (tel_Condition_Paiement_Client_TextField.getText() == null || tel_Condition_Paiement_Client_TextField.getText().isEmpty()) {
                    tel_Condition_Paiement_Client_TextField.setText("");
                }
                if (fax_Condition_Paiement_Client_TextField.getText() == null || fax_Condition_Paiement_Client_TextField.getText().isEmpty()) {
                    fax_Condition_Paiement_Client_TextField.setText("");
                }
                if (observation_Transport_Client_TextField.getText() == null || observation_Transport_Client_TextField.getText().isEmpty()) {
                    observation_Transport_Client_TextField.setText("");
                }

                //creation du client
                if (nouveau_Client_Checkbox.isSelected()) {
                    id_client = nombre_client;
                }
                System.out.println(id_client);
                Client client = new Client(id_client, nom_Client_TextField.getText(), service_Facturation_Client_TextField.getText(), adresse_Facturation_Client_TextField.getText(),
                        complement_Adresse_Facturation_Client_TextField.getText(), pays_Facturation_Client_TextField.getText(), tel_Facturation_Client_TextField.getText(),
                        fax_Facturation_Client_TextField.getText(), email_Facturation_Client_TextField.getText(), adresse_Livraison_Client_TextField.getText(),
                        ville_Livraison_Client_TextField.getText(), pays_Livraison_Client_TextField.getText(), complement_Adresse_Livraison_Client_TextField.getText(),
                        nom_Condition_Paiement_Client_TextField.getText(), email_Condition_Paiement_Client_TextField.getText(), tel_Condition_Paiement_Client_TextField.getText(),
                        fax_Condition_Paiement_Client_TextField.getText(), observation_Transport_Client_TextField.getText(), transporteur_Client_ComboBox.getValue());
                //ajout ou modification
                if (nouveau_Client_Checkbox.isSelected()) {
                    //ajout client
                    bdd.ajout_bdd_client(client.getId_client(), client.getNom_client(), client.getService_facturation_client(), client.getAdresse_facturation_client(), client.getComplement_adresse_livraison_client(), client.getTel_facturation_client(), client.getFax_facturation_client(), client.getEmail_facturation_client(), client.getAdresse_facturation_client(), client.getAdresse_livraison_client(), client.getVille_livraison_client(), client.getPays_livraison_client(), client.getComplement_adresse_livraison_client(), client.getNom_condition_paiement_client(), client.getEmail_condition_paiement_client(), client.getTel_condition_paiement_client(), client.getFax_condition_paiement_client(), client.getObservation_transport_client(), client.getTransporteur_client());
                } else {
                    //mise a jour client
                    bdd.mise_a_jour_bdd_client(client.getId_client(), client.getNom_client(), client.getService_facturation_client(), client.getAdresse_facturation_client(), client.getComplement_adresse_livraison_client(), client.getTel_facturation_client(), client.getFax_facturation_client(), client.getEmail_facturation_client(), client.getAdresse_facturation_client(), client.getAdresse_livraison_client(), client.getVille_livraison_client(), client.getPays_livraison_client(), client.getComplement_adresse_livraison_client(), client.getNom_condition_paiement_client(), client.getEmail_condition_paiement_client(), client.getTel_condition_paiement_client(), client.getFax_condition_paiement_client(), client.getObservation_transport_client(), client.getTransporteur_client());
                }
                source = (Node) actionEvent.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else if (optional.get() == ButtonType.CANCEL) {
                alert.close();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    //permettre a l'utilisateur d'ajouter un transporteur ou de modifier un deja existant

    public void ajout_Transporteur_Bouton_Action(ActionEvent actionEvent) {
        boolean transporteur_check = false;
        if (nouveau_Transporteur_Client_TextField.getText() == null || nouveau_Transporteur_Client_TextField.getText().isEmpty()) {
            nouveau_Transporteur_Client_TextField.getStyleClass().add("erreur");
            nouveau_Transporteur_Client_TextField.setPromptText("Champ requis");
        } else {
            nouveau_Transporteur_Client_TextField.getStyleClass().remove("erreur");
            transporteur_check = true;
        }
        if (transporteur_check) {
            bdd.ajout_bdd_transporteur(nouveau_Transporteur_Client_TextField.getText(), nombre_tranporteur++);
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //supression d'un client
    public void suppression_Client_Bouton_Action(ActionEvent actionEvent) {
        source = (Node) actionEvent.getSource();
        stage = (Stage) source.getScene().getWindow();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        alert.setTitle("Suppression Client");
        alert.setHeaderText("Êtes-vous sur de vouloir supprimer le client?");
        optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            for (int i = 0; i < data_choix_client.size(); i++) {
                if (choix_Modifier_Client_ComboBox.getValue().equals(data_client.get(i).getNom_client())) {
                    bdd.suppression_bdd_client(data_client.get(i).getNom_client());
                    source = (Node) actionEvent.getSource();
                    stage = (Stage) source.getScene().getWindow();
                    stage.close();
                }
            }
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else if (optional.get() == ButtonType.CANCEL) {
            alert.close();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //supression d'un transporteur
    public void suppression_Transporteur_Bouton_Action(ActionEvent actionEvent) {
        if (!transporteur_Client_ComboBox.getValue().isEmpty()) {
            transporteur_Client_ComboBox.getStyleClass().remove("erreur");
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            alert.setTitle("Suppression Transporteur");
            alert.setHeaderText("Êtes-vous sur de vouloir supprimer le transporteur?");
            optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK) {
                for (int i = 0; i < data_transporteur.size(); i++) {
                    if (transporteur_Client_ComboBox.getValue().equals(data_transporteur.get(i).getNom_transporteur())) {
                        bdd.suppression_bdd_transporteur(transporteur_Client_ComboBox.getValue());
                        source = (Node) actionEvent.getSource();
                        stage = (Stage) source.getScene().getWindow();
                        stage.close();
                    }
                }
                source = (Node) actionEvent.getSource();
                stage = (Stage) source.getScene().getWindow();
                stage.close();
            } else if (optional.get() == ButtonType.CANCEL) {
                alert.close();
            }
        } else {
            transporteur_Client_ComboBox.getStyleClass().add("erreur");
            transporteur_Client_ComboBox.setValue(null);
            transporteur_Client_ComboBox.setPromptText("Aucun transporteur choisi");
        }
    }
}
package Technopli;

import Technopli.classe.Bdd;
import Technopli.classe.Commande;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

public class Controller_Filtre implements Initializable {
    //private
    private static ObservableList<String> data_choix_client;
    //initialisation
    public Node source;
    public Optional<ButtonType> optional;
    public Stage stage;
    public Alert alert;
    public ComboBox<String> choix_Client_Filtre_Combobox;
    public Button reset_Filtre_Bouton, retour_Bouton, export_Excel_Bouton;
    public TableView<Commande> tableau_Commande;
    public TableColumn<Commande, String> client_TableColonne, id_Commande_TableColonne, numero_Commande_TableColonne, date_Saisi_Colonne,
            date_Commande_TableColonne, reference_SAP_TableColonne, numero_Article_TableColonne, quantite_Commande_TableColonne,
            quantite_Livrer_TableColonne, date_Pret_TableColonne, date_Livraison_TableColonne, retard_TableColonne, observation_TableColonne,
            temps_Gestion_Commande_TableColonne;
    public TableColumn<Commande, CheckBox> bL_TableColonne, prise_En_Compte_MAG_TableColonne, livre_TableColonne, annule_TableColonne;
    public TableColumn<Commande, TextField> pret_TableColonne;
    public CheckBox bl_Colonne_Checkbox, livre_Colonne_CheckBox, prise_En_Compte_MAG_Colonne_CheckBox, annule_Colonne_CheckBox;
    public TextField reference_SAP_TextField;
    public RadioButton entre_le_RadioBouton, le_RadioBouton;
    public ToggleGroup switch_Datepicker_ToggleGroup;
    public DatePicker debut_Date_DatePicker, fin_Date_DatePicker, date_DatePicker;

    //variables
    private DateTimeFormatter dateTimeFormatter;
    private Bdd bdd;
    private ObservableList<Commande> commande_ObservableList;

    public static void setData_choix_client(ObservableList<String> data_choix_client) {
        Controller_Filtre.data_choix_client = data_choix_client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //initialisation des variables
        bdd = new Bdd();
        bdd.connexion();
        bdd.recuperation_Client();
        choix_Client_Filtre_Combobox.setItems(data_choix_client);
        commande_ObservableList = FXCollections.observableArrayList();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //configuration des tableaux
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


        switch_Datepicker_ToggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (entre_le_RadioBouton.isSelected()) {
                date_DatePicker.setDisable(true);
                date_DatePicker.setValue(null);
                debut_Date_DatePicker.setDisable(false);
                fin_Date_DatePicker.setDisable(false);
            } else if (le_RadioBouton.isSelected()) {
                date_DatePicker.setDisable(false);
                debut_Date_DatePicker.setDisable(true);
                fin_Date_DatePicker.setDisable(true);
                debut_Date_DatePicker.setValue(null);
                fin_Date_DatePicker.setValue(null);
            }
        });

        //toutes les commandes par reference sap
        reference_SAP_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            commande_ObservableList.clear();
            for (int i = 0; i < Bdd.getData_commande().size(); i++)
                if (Bdd.getData_commande().get(i).getReference_SAP().equals(newValue)) {
                    commande_ObservableList.add(Bdd.getData_commande().get(i));
                }
            tableau_Commande.setItems(commande_ObservableList);
        });

        le_RadioBouton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            //toutes les commande de cette date
            date_DatePicker.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                if (newValue1 != null) {
                    commande_ObservableList.clear();
                    String date_pick = dateTimeFormatter.format(newValue1);
                    for (int i = 0; i < Bdd.getData_commande().size(); i++)
                        if (Bdd.getData_commande().get(i).getDate_commande().equals(date_pick)) {
                            commande_ObservableList.add(Bdd.getData_commande().get(i));
                        }
                    tableau_Commande.setItems(commande_ObservableList);
                }
            });
        });
        //filtre par client
        choix_Client_Filtre_Combobox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            commande_ObservableList.clear();
            for (int i = 0; i < Bdd.getData_commande().size(); i++)
                if (Bdd.getData_commande().get(i).getClient().equals(t1)) {
                    commande_ObservableList.add(Bdd.getData_commande().get(i));
                }
            tableau_Commande.setItems(commande_ObservableList);
        });

        entre_le_RadioBouton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (debut_Date_DatePicker.getValue() != null && fin_Date_DatePicker.getValue() != null) {
                debut_Date_DatePicker.getStyleClass().remove("erreur");
                fin_Date_DatePicker.getStyleClass().remove("erreur");

                debut_Date_DatePicker.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                    commande_ObservableList.clear();
                    fin_Date_DatePicker.valueProperty().addListener((observable2, oldValue2, newValue2) -> {

                        /*for (int i = 0; i < Bdd.getData_commande().size(); i++)
                            if (Bdd.getData_commande().get(i).getDate_commande()) {
                                commande_ObservableList.add(Bdd.getData_commande().get(i));
                            }*/
                        tableau_Commande.getItems().addAll(commande_ObservableList);
                    });
                });

            } else if (debut_Date_DatePicker.getValue() != null && fin_Date_DatePicker.getValue() == null) {
                fin_Date_DatePicker.getStyleClass().add("erreur");
            } else if (fin_Date_DatePicker.getValue() != null && debut_Date_DatePicker.getValue() != null) {
                debut_Date_DatePicker.getStyleClass().add("erreur");
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

    public void reset_Filtre_Bouton_Action(ActionEvent actionEvent) {
        reference_SAP_TextField.setText(null);
        choix_Client_Filtre_Combobox.setValue(null);
        date_DatePicker.setValue(null);
        debut_Date_DatePicker.setValue(null);
        fin_Date_DatePicker.setValue(null);
        commande_ObservableList.clear();
    }


    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


}
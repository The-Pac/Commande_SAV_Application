package Technopli;

import Technopli.classe.Bdd;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller_Login implements Initializable {
    //initialisation
    //fxml
    public ImageView secretaire_ImageView, technicien_ImageView, superviseur_ImageView;
    public Node source;
    public Image secretaire_Image, technicien_Image, superviseur_Image;
    public Optional<ButtonType> optional;
    public Stage stage;
    public Alert alert;
    public Label connexion_Label;
    public Button quitter_Bouton, secretaire_Bouton, technicien_Bouton, superviseur_Bouton;
    public HBox connexion_Hbox;

    //variable
    public String identifiant, mot_De_Passe;
    public Bdd bdd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bdd = new Bdd();
        //secretaire affichage
        secretaire_Image = new Image("Technopli/img/Secretaire.png");
        secretaire_ImageView = new ImageView(secretaire_Image);
        secretaire_ImageView.setFitHeight(80);
        secretaire_ImageView.setFitWidth(80);
        secretaire_ImageView.setPreserveRatio(true);
        secretaire_Bouton.setGraphic(secretaire_ImageView);

        //technicien affichage
        technicien_Image = new Image("Technopli/img/Technicien.png");
        technicien_ImageView = new ImageView(technicien_Image);
        technicien_ImageView.setFitHeight(80);
        technicien_ImageView.setFitWidth(80);
        technicien_ImageView.setPreserveRatio(true);
        technicien_Bouton.setGraphic(technicien_ImageView);
        //superviseur affichage
        superviseur_Image = new Image("Technopli/img/Superviseur.png");
        superviseur_ImageView = new ImageView(superviseur_Image);
        superviseur_ImageView.setFitHeight(80);
        superviseur_ImageView.setFitWidth(80);
        superviseur_ImageView.setPreserveRatio(true);
        superviseur_Bouton.setGraphic(superviseur_ImageView);

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    //Permettre a l'utilisateur de quitter ou non l'aplication
    public void quitter_Bouton_Action(ActionEvent actionEvent) {
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

    //connexion de l'utilisateur via bdd et mise en page du tableau et des elements
    public void connexion(String identifiant, String mot_De_Passe, Image image, ActionEvent actionEvent) {
        //check bdd
        bdd.connexion();
        System.out.println("\n***** Login *****");

        String[] connecte = bdd.authentification(identifiant, mot_De_Passe).split(";");
        if (connecte[0].equals("true")) {
            //debug
            System.out.println("Connecté au compte:\nUsername = " + identifiant + "\nPassword = " + mot_De_Passe);
            System.out.println("\n***** Fin Login *****");
            Controller_Main.setMetier_utilisateur(connecte[3]);
            Controller_Main.setImage_Client(image);
            bdd.deconnexion();
            source = (Node) actionEvent.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
            try {
                stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
                Scene scene = new Scene(root);
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Commande SAV");
                stage.setResizable(false);
                stage.setX(primaryScreenBounds.getMinX() - 5);
                stage.setY(primaryScreenBounds.getMinY() - 5);
                stage.setWidth(primaryScreenBounds.getWidth() + 10);
                stage.setHeight(primaryScreenBounds.getHeight() + 10);
                stage.setFullScreen(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("non connecté(utilisateur inconnu)");
            Label authentification_Info = new Label();
            connexion_Hbox.getChildren().add(authentification_Info);
            authentification_Info.setText("aucun compte avec ces identifiants");
        }

    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void technicien_Bouton_Action(ActionEvent actionEvent) {
        identifiant = "technicien";
        mot_De_Passe = "technicien";
        connexion(identifiant, mot_De_Passe, technicien_Image, actionEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void secretaire_Bouton_Action(ActionEvent actionEvent) {

        identifiant = "secretaire";
        mot_De_Passe = "secretaire";
        connexion(identifiant, mot_De_Passe, secretaire_Image, actionEvent);
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    public void superviseur_Bouton_action(ActionEvent actionEvent) {
        identifiant = "superviseur";
        mot_De_Passe = "superviseur";
        connexion(identifiant, mot_De_Passe, superviseur_Image, actionEvent);
    }
}

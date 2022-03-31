package Technopli.classe;

import Technopli.Controller_Main;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Statistique {
    private Controller_Main controller_main = new Controller_Main();
    private ArrayList<Integer> tableau_mois = new ArrayList<>(), tableau_annee = new ArrayList<>();
    private ArrayList<Double> tableau_jour = new ArrayList<>();
    private int somme = 0, i = 0;
    private String date_saisi_Constructeur, date_livraison_Constructeur;
    private Bdd bdd;

    public Statistique(String date_saisi_Constructeur, String date_livraison_Constructeur) {
        this.date_saisi_Constructeur = date_saisi_Constructeur;
        this.date_livraison_Constructeur = date_livraison_Constructeur;
    }

    public Statistique() {
    }

    public void moyenne_Total(ObservableList<Commande> data_commande ,Controller_Main controller_main) {
        double moyenne = 0,i = 0;
        for (Commande commande: data_commande){
            if (commande.getTemps_gestion_commande() != null && !commande.getTemps_gestion_commande().isEmpty() && !commande.getTemps_gestion_commande().equals("null")){
                String[] jour_tableau = commande.getTemps_gestion_commande().split("/");
                moyenne += Double.parseDouble(jour_tableau[0]);
                i++;
            }
        }
        moyenne = moyenne/i;
        controller_main.moyenne_Total_TextField.setText(String.valueOf(moyenne));
    }

    public void affichage_statistique() {
        XYChart.Series<String, Number> data = new XYChart.Series<String, Number>();
    }


    public double temps_gestion_commande() {
        double jour = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date_saisi = simpleDateFormat.parse(this.date_saisi_Constructeur), date_livraison = simpleDateFormat.parse(this.date_livraison_Constructeur);
            long difference = date_livraison.getTime() - date_saisi.getTime();
            jour = (double) (difference / (1000 * 60 * 60 * 24));
            return jour;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Impossible de lire la date: " + this.date_saisi_Constructeur + " " + this.date_livraison_Constructeur);
        }
        return jour;
    }
}

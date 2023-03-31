import java.io.File;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        /*
        Nom des fichiers à utiliser
        fichier_bin est le fichier binaire à décompresser
        fichier_freq est le fichier contenant les fréquences
        fichier_fin est le fichier dans lequel on écrit le texte décompressé
         */
        String fichier_bin ="Ressources/textesimple_comp.bin";
        String fichier_freq = "Ressources/textesimple_freq.txt";
        String fichier_fin = "Ressources/textesimple_decomp.txt";


        //Création de la liste des fréquences de chaque lettre
        ParcourtFreq monParcours = new ParcourtFreq(fichier_freq);
        monParcours.parcourtFreq();
        ArrayList<ArrayList> sorted_frequence = monParcours.getSorted_frequence();

        //Création de l'arbre des nœuds
        ListeNode maListe = new ListeNode(sorted_frequence);
        maListe.listeNode();
        ArrayList<Node> liste_node = maListe.getListeNode();

        //Parcours l'arbre pour créer précédemment afin de créer un dictionnaire avec chaque caractère et son code en binaire
        HashMap<String, String > parcours = (HashMap<String , String>) liste_node.get(0).parcoursNode(new ArrayList<>(), null,null,null).get(1);

        //Trouver le nombre de caractères que doit faire le nombre en binaire
        Somme maSomme = new Somme(parcours, sorted_frequence);
        maSomme.somme();
        Integer somme = maSomme.getSomme();

        //Parcours du fichier binaire qui retourne le code binaire de celui-ci
        Binary monBinary = new Binary(fichier_bin);
        monBinary.binary();
        String chiffre = monBinary.getBinaryString();


        //Rajoute des zéros au début de code binaire pour qu'il ait le bon nombre de caractères
        while ((Integer.parseInt(String.valueOf(chiffre.length()))) != somme){
            chiffre = chiffre.substring(1);
        }

        //Écrire le fichier texte final
        liste_node.get(0).parcoursVersTexteNode(chiffre, null, fichier_fin);

        //Affichage du taux de compression d
        File file_comp = new File(fichier_bin);
        float file_comp_length = (float) file_comp.length();
        File file_decomp = new File(fichier_fin);
        float file_decomp_length = (float) file_decomp.length();
        float calcul = 1 - file_comp_length / file_decomp_length;

        System.out.format("The size of the file '%s' is %d octets.%n", fichier_bin, file_comp.length());
        System.out.format("The size of the file '%s' is %d octets.%n", fichier_fin, file_decomp.length());
        System.out.println("Taux de compression = " + calcul);

        //Affichage du nombre moyen d'octets sur lesquels sont écrits les caractères
        double moyenne = 0;
        for (Object caractere : parcours.keySet()) {
            moyenne += parcours.get(caractere).length();
        }
        moyenne = moyenne / parcours.size();
        System.out.println("Nombre moyen de bits par caractère = " + moyenne);
    }
}

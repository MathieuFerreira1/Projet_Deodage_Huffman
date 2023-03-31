import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParcourtFreq {
    private String fichier_freq;
    private ArrayList<ArrayList> sorted_frequence;

    public String getFichier_freq() {
        return fichier_freq;
    }

    public void setFichier_freq(String fichier_freq) {
        this.fichier_freq = fichier_freq;
    }

    public ArrayList<ArrayList> getSorted_frequence() {
        return sorted_frequence;
    }

    public void setSorted_frequence(ArrayList<ArrayList> sorted_frequence) {
        this.sorted_frequence = sorted_frequence;
    }

    public ParcourtFreq(String fichier_freq) {
        this.fichier_freq = fichier_freq;
    }

    public void parcourtFreq() {
        //Création d'une liste vide de liste vide qui contiendra les caractères avec leurs fréquences
        ArrayList<ArrayList> sorted_frequence = new ArrayList<>();
        try {
            FileReader f = new FileReader(fichier_freq);
            BufferedReader br = new BufferedReader(f);
            String line;
            String prec = null;
            while ((line = br.readLine()) != null) {
                //Parcours des différents cas possible pour les lignes du fichier
                //Pour ne pas prendre en compte la première ligne
                //Pour s'adapter au saut de ligne du fichier qui correspond au saut de ligne dans le texte final
                //Pour s'adapter à l'espace dans le fichier qui est aussi l'espace dans le fichier final

                //Cas général
                if (line.split(" ").length == 2) {
                    ArrayList<String> min_liste = new ArrayList<>();
                    min_liste.add(line.split(" ")[0]);
                    min_liste.add(line.split(" ")[1]);
                    sorted_frequence.add(min_liste);
                }
                //Cas du saut de ligne
                else if ((line.split("").length == 1)) {
                    ArrayList<String> min_liste = new ArrayList<>();
                    min_liste.add("\n");
                    min_liste.add(br.readLine().split(" ")[1]);
                    sorted_frequence.add(min_liste);
                }
                //Cas de l'espace
                else if (prec != null) {
                    ArrayList<String> min_liste = new ArrayList<>();
                    min_liste.add(" ");
                    min_liste.add(line.split(" ")[2]);
                    sorted_frequence.add(min_liste);
                }
                prec = line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'existe pas'");
        } catch (IOException e) {
            System.out.println("Il y a eu une erreur lors de la lecture du fichier");
        }
        //Retourne la liste des caractères avec leur fréquence
        this.sorted_frequence = sorted_frequence;
    }
}

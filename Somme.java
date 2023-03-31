import java.util.ArrayList;
import java.util.HashMap;

public class Somme {
    /*
    parcours = dictionnaire avec chaque caractère et son code en binaire
    sorted_frequence = liste des fréquences de chaque lettre
     */
    private HashMap<String, String > parcours;
    private ArrayList<ArrayList> sorted_frequence;
    private Integer somme;

    public Integer getSomme() {
        return somme;
    }

    public void setSomme(Integer somme) {
        this.somme = somme;
    }

    public HashMap<String, String> getParcours() {
        return parcours;
    }

    public void setParcours(HashMap<String, String> parcours) {
        this.parcours = parcours;
    }

    public ArrayList<ArrayList> getSorted_frequence() {
        return sorted_frequence;
    }

    public void setSorted_frequence(ArrayList<ArrayList> sorted_frequence) {
        this.sorted_frequence = sorted_frequence;
    }

    public Somme(HashMap<String, String> parcours, ArrayList<ArrayList> sorted_frequence) {
        this.parcours = parcours;
        this.sorted_frequence = sorted_frequence;
    }

    public void somme(){
        Integer somme = 0;
        //Parcours du dictionnaire
        for (Object o : parcours.keySet()){
            //Parcours de la liste des fréquences
            for (ArrayList liste : sorted_frequence){
                //Vérifie si l'objet du dictionnaire (le caractère) est le meme que dans la liste et si c'est le cas
                //On ajoute la longueur de code de ce caractère * le nombre d'occurrences de ce caractère (dans le texte final)
                if (o == liste.get(0)){
                    Integer num_liste = Integer.parseInt((String) liste.get(1));
                    Integer longueur = parcours.get(o).length();
                    somme = somme + (num_liste * longueur);
                }
            }
        }
        //On retourne le nombre de chiffres que doit avoir la chaine de 1 et de 0 au final
        this.somme = somme;
    }
}

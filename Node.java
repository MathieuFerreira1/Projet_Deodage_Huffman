import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

public class Node {
    private String label;
    private int freq;
    private Node FG;
    private Node FD;


    public Node(String label, int freq) {
        this.label = label;
        this.freq = freq;
        this.FG = null;
        this.FD = null;
    }

    @Override
    public String toString() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getFreq() {
        return freq;
    }

    public void setFG(Node FG) {
        this.FG = FG;
    }

    public Node getFG() {
        return FG;
    }

    public void setFD(Node FD) {
        this.FD = FD;
    }

    public Node getFD() {
        return FD;
    }

    public boolean is_leaf(){
        return FG == null && FD == null;
    }


    /*
    Liste = liste des nœuds
    dico = dictionnaire intermédiaire contenant les nœuds avec leur code
    parent = nœud parent du nœud en cours
    final_dico = dictionnaire final des caractères avec leur code en binaire
     */
    public ArrayList<Object> parcoursNode(ArrayList<Node> liste, Dictionary<Node, String> dico, Node parent, HashMap<String, String> final_dico) {
        //Initialisation des dictionnaires et de la valeur du parent au premier appelle de cette fonction
        if (parent == null) {
            dico = new Hashtable<>();
            final_dico = new HashMap<>();
            parent = this;
            dico.put(parent, "");
        }

        //Ajout des nœuds dans la liste de nœuds s'ils n'y sont pas déjà
        if (!liste.contains(this)) {
            liste.add(this);
        }

        //Les étapes à effectuer si jamais le nœud en cours est une feuille
        if (!this.is_leaf()) {
            parent = this;
            dico.put(this.FG, dico.get(parent) + "0");

            //Ajout du fils gauche dans la liste final et on recommence le parcours avec ce fils gauche
            if (this.FG.is_leaf()) {
                final_dico.put(this.FG.getLabel(), dico.get(parent) + "0");
            }
            this.FG.parcoursNode(liste, dico, parent, final_dico);

            //Ajout du fils droit dans la liste final et on recommence le parcours avec ce fils droit
            dico.put(this.FD, dico.get(parent) + "1");
            if (this.FD.is_leaf()) {
                final_dico.put(this.FD.getLabel(), dico.get(parent) + "1");
            }
            this.FD.parcoursNode(liste, dico, parent, final_dico);
        }

        //On crée une liste vide dans laquelle on ajoute la liste des nœuds et le dictionnaire final
        ArrayList<Object> liste_final = new ArrayList<>();
        liste_final.add(liste);
        liste_final.add(final_dico);
        return liste_final;

    }

    /*
    binaire = la suite de 1 et de 0 venant du décodage du fichier binaire
    depart = racine de l'arbre
    fichier_arrivee = fichier dans lequel on écrit le texte décompressé
     */
    public void parcoursVersTexteNode(String binaire, Node depart, String fichier_arrivee) {
        //Crée le fichier de texte final en fichier vide afin d'être sûr que celui-ci n'avait pas déjà du contenue
        try {
            FileWriter writer = new FileWriter(fichier_arrivee);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initialisation de la racine
        if (depart == null) {
            depart = this;
        }

        //On fait une pile pour stocker les nœuds dans laquelle on ajoute le nœud courant
        Stack<Node> stack = new Stack<>();
        stack.push(this);

        //On continue le programme tant que la pile n'est pas vide, à ce moment-là on aura parcouru le code binaire en entier
        while (!stack.empty()) {
            //On enlève le nœud en cours de la pile
            Node node = stack.pop();

            //On regarde le label du nœud pour n'écrire que les vrais caractères du texte dans le fichier final
            if (node.label != null) {
                try {
                    FileWriter writer = new FileWriter(fichier_arrivee, true);
                    writer.write(node.label);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //On regarde si le nombre binaire est nul
            if (binaire != "") {
                //Si c'est une feuille, on ajoute la racine en haut de la pile afin de repartir en haut de l'arbre pour le caractère suivant
                if (node.is_leaf()) {
                    stack.push(depart);
                }

                //Si ce n'est pas une feuille
                else {
                    //Si le prochain caractère est un 0 alors il va falloir se déplacer à gauche dans l'arbre
                    //Ainsi à ce moment, on enlève le premier caractère de nombre binaire et on ajoute le fils gauche
                        //en haut de la pile afin de pouvoir continuer le parcours de l'arbre
                    if (binaire.charAt(0) == '0') {
                        binaire = binaire.substring(1);
                        stack.push(node.getFG());
                    }

                    //Si le prochain caractère est un 1 alors il va falloir se déplacer à droite dans l'arbre
                    //Ainsi à ce moment, on enlève le premier caractère de nombre binaire et on ajoute le fils droit
                    //en haut de la pile afin de pouvoir continuer le parcours de l'arbre
                    else {
                        binaire = binaire.substring(1);
                        stack.push(node.getFD());
                    }
                }
            }
        }
    }

}

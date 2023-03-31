import java.util.ArrayList;

public class ListeNode {

    /*
    sorted_frequence = liste des fréquences de chaque lettre
    */
    private ArrayList<Node> listeNode;
    private ArrayList<ArrayList> sorted_frequence;

    public ArrayList<ArrayList> getSorted_frequence() {
        return sorted_frequence;
    }

    public void setSorted_frequence(ArrayList<ArrayList> sorted_frequence) {
        this.sorted_frequence = sorted_frequence;
    }

    public ListeNode(ArrayList<ArrayList> sorted_frequence) {
        this.sorted_frequence = sorted_frequence;
    }

    public ArrayList<Node> getListeNode() {
        return listeNode;
    }

    public void setListeNode(ArrayList<Node> listeNode) {
        this.listeNode = listeNode;
    }

    public void listeNode(){
        //On commence par faire une liste vide de Nœuds
        ArrayList<Node> liste_node = new ArrayList<>();
        //On parcourt la liste des fréquences afin de créer des arbres avec ses éléments et ajouter ces arbres dans une liste
        for (ArrayList<String> tuple : sorted_frequence) {
            Node node = new Node(tuple.get(0), Integer.parseInt(tuple.get(1)));
            liste_node.add(node);
        }

        //Parcours de tous les nœuds pour créer un grand arbre pour tous les relier et créer l'arbre de Huffman
        while(liste_node.size() != 1) {
            Node node1 = null;
            Node node2 = null;
            int freq1 = liste_node.get(0).getFreq();
            for(Node node : liste_node) {
                if(node.getFreq() <= freq1) {
                    node1 = node;
                    freq1 = node.getFreq();
                }
            }
            liste_node.remove(node1);
            int freq2 = liste_node.get(0).getFreq();
            for(Node node : liste_node) {
                if(node.getFreq() <= freq2) {
                    node2 = node;
                    freq2 = node.getFreq();
                }
            }
            liste_node.remove(node2);
            Node node = new Node(null, freq1 + freq2);
            node.setFG(node1);
            node.setFD(node2);

            liste_node.add(node);
        }
        //Retourne une liste ne contenant qu'un seul élément qui est la racine de l'arbre
        this.listeNode = liste_node;
    }
}

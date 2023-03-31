import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Binary {
    private String binaryString;
    private String fichier;

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getBinaryString() {
        return binaryString;
    }

    public void setBinaryString(String binaryString) {
        this.binaryString = binaryString;
    }

    public Binary(String fichier) {
        this.fichier = fichier;
    }

    public void binary() {
        File file = new File(this.fichier);
        StringBuilder binaryString = new StringBuilder();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            int data;
            //On parcourt les caractères du fichier binaire
            while ((data = inputStream.read()) != -1) {
                String binary = Integer.toBinaryString(data);
                //Si la longueur du caractère en binaire n'a pas la bonne longueur (ne fais pas 8 caractères)
                //On lui rajoute un nombre de 0 permettant qu'il ait la taille d'un octet
                while (binary.length() < 8) {
                    binary = "0" + binary;
                }
                //On l'ajoute dans le nombre binaire final
                binaryString.append(binary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //On retourne le nombre binaire final sous forme d'un string
        this.binaryString = binaryString.toString();
    }
}

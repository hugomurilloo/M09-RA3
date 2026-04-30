import java.io.*;

public class FilServidorXat extends Thread {

    private ObjectInputStream ois;
    private String nom;

    public FilServidorXat(ObjectInputStream ois, String nom) {
        this.ois = ois;
        this.nom = nom;
    }

    @Override
    public void run() {
        try {
            String missatge;
            while (true) {
                missatge = (String) ois.readObject();
                System.out.println("\nRebut: " + missatge);
                System.out.print("Missatge ('sortir' per tancar): ");
                if (missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("\nEl client ha tancat la connexió.");
        }
        System.out.println("Fil de xat finalitzat.");
    }
}
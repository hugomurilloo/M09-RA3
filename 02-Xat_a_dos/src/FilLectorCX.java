import java.io.*;

public class FilLectorCX extends Thread {

    private ObjectInputStream ois;

    public FilLectorCX(ObjectInputStream ois) {
        this.ois = ois;
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
            System.out.println("\nEl servidor ha tancat la connexió.");
        }
    }
}
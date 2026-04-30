import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorXat {

    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";

    private ServerSocket serverSocket;

    public void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
    }

    public void pararServidor() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        System.out.println("Servidor aturat.");
    }

    public String getNom(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return (String) ois.readObject();
    }

    public static void main(String[] args) {
        ServidorXat servidor = new ServidorXat();

        try {
            servidor.iniciarServidor();

            Socket clientSocket = servidor.serverSocket.accept();
            System.out.println("Client connectat: " + clientSocket.getInetAddress());

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            String nom = servidor.getNom(ois);
            System.out.println("Nom rebut: " + nom);

            FilServidorXat fil = new FilServidorXat(ois, nom);
            System.out.println("Fil de xat creat.");
            fil.start();
            System.out.println("Fil de " + nom + " iniciat");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Missatge ('sortir' per tancar): ");
            String missatge;
            while (scanner.hasNextLine()) {
                missatge = scanner.nextLine();
                oos.writeObject(missatge);
                oos.flush();
                if (missatge.equalsIgnoreCase(MSG_SORTIR)) {
                    break;
                }
                System.out.print("Missatge ('sortir' per tancar): ");
            }
            scanner.close();

            fil.join();

            clientSocket.close();
            servidor.pararServidor();

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.err.println("Error al servidor: " + e.getMessage());
        }
    }
}
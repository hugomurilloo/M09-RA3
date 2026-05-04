import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientXat {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public void connecta() throws IOException {
        socket = new Socket(ServidorXat.HOST, ServidorXat.PORT);
        System.out.println("Client connectat a " + ServidorXat.HOST + ":" + ServidorXat.PORT);

        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Flux d'entrada i sortida creat.");
    }

    public void enviarMissatge(String missatge) throws IOException {
        System.out.println("Enviant missatge: " + missatge);
        oos.writeObject(missatge);
        oos.flush();
    }

    public void tancarClient() throws IOException {
        System.out.println("Tancant client...");
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
        System.out.println("Client tancat.");
    }

    public static void main(String[] args) {
        ClientXat client = new ClientXat();

        try {
            client.connecta();

            FilLectorCX filLector = new FilLectorCX(client.ois);
            filLector.start();
            System.out.println("Fil de lectura iniciat");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Missatge ('sortir' per tancar): ");
            String missatge;
            while (scanner.hasNextLine()) {
                missatge = scanner.nextLine();
                client.enviarMissatge(missatge);
                if (missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                    break;
                }
                System.out.print("Missatge ('sortir' per tancar): ");
            }
            scanner.close();

            filLector.join();
            client.tancarClient();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al client: " + e.getMessage());
        }
    }
}
import java.io.*;
import java.net.*;

public class Client {
    private static final int PORT = 7777;
    private static final String HOST = "localhost";
    private Socket socket;
    private PrintWriter out;

    public void connecta() {
        try {
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
        } catch (IOException e) {
            System.err.println("Error en connecta(): " + e.getMessage());
        }
    }

    public void envia(String missatge) {
        if (out != null) {
            out.println(missatge);
            System.out.println("Enviat al servidor: " + missatge);
        }
    }

    public void tanca() {
        try {
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("Client tancat");
        } catch (IOException e) {
            System.err.println("Error en tanca(): " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connecta();
        
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adeu!");
        
        System.out.println("Prem Enter per tancar el client...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        client.tanca();
    }
}
package app.sockets.clientSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import app.vista.vistaUsuario;

import java.net.InetAddress;

/**
 * Cliente
 */
public class Client {
    private Socket client;
    private vistaUsuario vista;

    public Client(vistaUsuario vista){
        this.vista = vista;
    }
    /**
     * Crea el client en socket, se debe tener en cuenta el nombre del computador de monitores en el dominio
     * @throws Exception
     */
    public void sendRequest() throws Exception {
        client = new Socket("monitores", 1717);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader inC = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter outC = new PrintWriter(client.getOutputStream(), true);
        String request = mapeoDeRequest();
        System.out.println(request);
        outC.println(request);
        System.out.println(inC.readLine());
        inC.close();
        outC.close();
        client.close();
    }

    private String mapeoDeRequest() {
        StringBuilder requestTemp = new StringBuilder();
        requestTemp.append(getUserName()+";");
        
        // Implementar logica seria sobre la descripción, es decir recoger el dato de la
        // vista.
        requestTemp.append(vista.getDescription()+";");
        requestTemp.append(getPCName()+";");
        requestTemp.append(getCurrentDate());
        return requestTemp.toString();
    }

    private String getUserName() {
        return System.getProperty("user.name");
    }

    private String getPCName() {
        String computerName = "Desconocido";
        try {
            computerName = InetAddress.getLocalHost().getHostName();
        }
        catch (Exception ex) {
            return computerName;
        }
        return computerName;
    }

    private String getCurrentDate() {
        StringBuilder currentDate = new StringBuilder();
        currentDate.append(System.currentTimeMillis());
        return currentDate.toString();
    }
}
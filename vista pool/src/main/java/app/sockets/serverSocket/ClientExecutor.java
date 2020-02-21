package app.sockets.serverSocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import app.model.Request;
import app.request.PoolView;

import javax.swing.*;

/**
 * ClientExecutor
 */
// Encargado de recibir la información del cliente.
public class ClientExecutor extends Thread {
    private Socket client;
    private PoolView poolView;

    public ClientExecutor(Socket client, PoolView poolView) {
        this.client = client;
        this.poolView = poolView;
    }

    @Override
    public void run() {
        try {
            procesar();
            JOptionPane.showMessageDialog(null, "Solicitud recibida");
        } catch (Exception e) {
            System.out.println("Fallo en la ejecución por cliente.");
        }
    }

    private void procesar() throws Exception {
        PrintWriter outS = new PrintWriter(client.getOutputStream(), true);
        BufferedReader inS = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String request = inS.readLine();
        StringTokenizer stk = new StringTokenizer(request, ";");
        Request uRequest = mapeoDeRequest(stk);
        poolView.saveRequestInDB(uRequest);
        poolView.addRequest(uRequest);
        outS.println("Solicitud recibida");
        outS.close();
        inS.close();
        client.close();
    }

    private Request mapeoDeRequest(StringTokenizer stk) throws Exception {
        Request request = null;
        try {
            String usuario = stk.nextToken();
            String descripcion = stk.nextToken();
            String equipo = stk.nextToken();
            String estado = "En espera";
            Date inicio = new Date(Long.parseLong(stk.nextToken()));
            request = new Request(usuario, descripcion, equipo, estado, inicio);

        } catch (Exception e) {
            System.out.println("Solicitud invalida");
        }
        if (request == null) {
            throw new Exception("Error - Solicitud invalida");
        } else {
            return request;
        }
    }

}
package app.sockets.serverSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import app.request.PoolView;

/**
 * ServerSocket
 */
public class Server implements Runnable{
    private ServerSocket serverSocket;
    private ExecutorService pool;
    private PoolView poolView;


    public Server(int poolSize, PoolView poolView){
        try {
            serverSocket = new ServerSocket(1717);
            pool = Executors.newFixedThreadPool(poolSize);
            this.poolView = poolView;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
        try{
            while(true){
                Socket cliente = serverSocket.accept();
                ClientExecutor clienteT = new ClientExecutor(cliente, poolView);
                pool.execute(clienteT);
            }
        }catch(Exception e){
            pool.shutdown();
        }
    }

}
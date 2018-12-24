package Client;

import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * this class is a Thread for the client side. talks to ServerThread class.
 */
public class ClientThread implements Runnable {
    private Socket clientSocket;
    private Client client;
    private boolean running;
    
    /**
     * this method is a constructor to this Thread.
     * @param client is the client GUI.
     * @param clientSocket is the socket of the client.
    */
    public ClientThread(Client client, Socket clientSocket) {
        this.client = client; 
        this.clientSocket = clientSocket;
        this.running = true;
    }
    
    /**
     * this method makes actions which comes from the ServerThread.
    */
    @Override
    public void run()
    {
        BufferedReader input = null;
        String messageLine;
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while(running)
        {
            try{
                if((messageLine = input.readLine()) == null) break;
                if(messageLine.startsWith(MyProtocol.NOT_CONNECTED))
                {
                    String nameTo = messageLine.substring(MyProtocol.NOT_CONNECTED.length());
                    JOptionPane.showMessageDialog(null, nameTo + " is not connected.", "Error", HEIGHT);
                }
                else if(messageLine.startsWith(MyProtocol.COLOR_DISCONNECT))
                {
                    client.setOnlineOrOffline(false);
                }
                else if(messageLine.startsWith(MyProtocol.COLOR_CONNECT))
                {
                    client.setOnlineOrOffline(true);
                }
                else if(messageLine.startsWith(MyProtocol.SERVER_STOPPED))
                {
                    client.actions(messageLine);
                    stopper();
                    break;
                }
                else client.actions(messageLine);
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * this method stops this thread.
    */
    public void stopper()
    {
        this.running = false;
        try{
            clientSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

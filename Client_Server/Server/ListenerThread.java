package Server;


import static java.awt.image.ImageObserver.HEIGHT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
/**
 * this class is a Thread which need to listen to new clients. it has vector of server threads which each one is for a client. 
 */
public class ListenerThread implements Runnable{
    private ServerSocket serverSocket;
    private boolean isStopped = false;
    private Vector<ServerThread> clientsOnline;
    private Server myServerGUI;
    /**
     * this method is a constructor to this Thread.
     * @param s is the server GUI.
    */
    public ListenerThread(Server s)
    {
        myServerGUI = s;
        this.clientsOnline = new Vector<>();
    }
    /**
     * 
     * @return the vector of the servers which handles each client.
     */
    public Vector<ServerThread> getClientsVector()
    {
        return clientsOnline;
    }
    /**
     * this method listens to connections to the server.
    */
    @Override
    public void run()
    {
        try{
            serverSocket = new ServerSocket(MyProtocol.PORT);
        }catch(IOException e){
                System.out.println(e.getMessage());
        }
        while(!isStopped)
        {
            Socket clientSocket = null;
            String name = "";
            try{
                clientSocket = this.serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputMessage = input.readLine();
                if(inputMessage != null && inputMessage.startsWith(MyProtocol.CONNECT_CMD))
                {
                    name = inputMessage.substring(MyProtocol.CONNECT_CMD.length(), inputMessage.length());
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            if(!name.equals(""))
            {
                myServerGUI.setTextArea(name + " Connected");
                for(int i = 0; i < clientsOnline.size(); i++)
                {
                    if(!name.equals(clientsOnline.elementAt(i).getName()))
                    {
                        PrintWriter outputToClients;
                        try {
                            outputToClients = new PrintWriter(clientsOnline.elementAt(i).getClientSocket().getOutputStream(), true);
                            outputToClients.println(name + " Connected.");
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                ServerThread serverThread = new ServerThread(this, clientSocket, name, myServerGUI);
                clientsOnline.add(serverThread);
                new Thread(serverThread).start();
            }
        }
    }
    /**
     * this method stops the thread and stoppes all server threads.
    */
    public void stoper()
    {
        this.isStopped = true;
        for(int i = 0; i < clientsOnline.size(); i++)
        {
            clientsOnline.elementAt(i).stopAll();
        }
        try{
            this.serverSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

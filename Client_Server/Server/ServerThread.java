package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
/**
 * this class is a Thread for the server side. talks to ClientServer and ListenerThread classes.
 * 
 */
public class ServerThread implements Runnable{
    private Socket clientSocket;
    private ListenerThread server;
    private String name;
    private boolean running;
    private Server serverGUI;
    /**
     * this method is a constructor to this Thread.
     * @param server is the Listener Thread.
     * @param  clientSocket is the socket of the client.
     * @param name is the name of the client.
     * @param serverGUI is the GUI of the server.
    */
    public ServerThread(ListenerThread server, Socket clientSocket, String name, Server serverGUI)
    {
        this.clientSocket = clientSocket;
        this.server = server;
        this.name = name;
        this.running = true;
        this.serverGUI = serverGUI;
    }
 
    /**
     * @return the name of the client.
     */
    public String getName()
    {
        return name;
    }
    /**
     * 
     * @return the socket of the client.
     */
    public Socket getClientSocket()
    {
        return this.clientSocket;
    }
    /**
     * this method handles the client. getting commands of connection, disconnection, requests for online list, and messages.
    */
    @Override
    public void run()
    {
        String messageLine = "";
        BufferedReader input = null;            
        while(running)
        {
            try{
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
            try{
                Vector<ServerThread> temp = server.getClientsVector();
                if((messageLine = input.readLine()) == null) break;
                if(messageLine.startsWith(MyProtocol.TOALL))
                {
                    String message = messageLine.substring(MyProtocol.TOALL.length(), messageLine.length());
                    for(int i = 0; i < temp.size(); i++)
                    {
                        PrintWriter outputToClients = new PrintWriter(temp.elementAt(i).getClientSocket().getOutputStream(), true);
                        outputToClients.println(name + ": " + message);
                    }
                }
                else if(messageLine.startsWith(MyProtocol.TOONE))
                {
                    boolean found = false;
                    String nameTo = messageLine.substring(MyProtocol.TOONE.length(), messageLine.length());
                    String messageTo = input.readLine();
                    for(int i = 0; i < temp.size(); i++)
                    {
                        String hisName = temp.elementAt(i).name;
                        if(hisName.equals(nameTo))
                        {
                            found = true;
                            //messageLine = input.readLine();
                            PrintWriter outputToClients = new PrintWriter(temp.elementAt(i).getClientSocket().getOutputStream(), true);
                            outputToClients.println(MyProtocol.PRIVATE + name + ": " + messageTo);
                        }
                    }
                    if(!found){
                        PrintWriter outputToClients = new PrintWriter(clientSocket.getOutputStream(), true);
                        outputToClients.println(MyProtocol.NOT_CONNECTED + nameTo);
                    }
                }
                else if(messageLine.startsWith(MyProtocol.DISCONNECT_CMD))
                {
                    String myName = messageLine.substring(MyProtocol.DISCONNECT_CMD.length(), messageLine.length());
                    for(int i = 0; i < temp.size(); i++)
                    {
                        if(myName.equals(temp.elementAt(i).name))
                        {
                            temp.remove(i);
                            i--;
                            break;
                        }
                    }
                    for(int i = 0; i < temp.size(); i++)
                    {
                        PrintWriter outputToAll = new PrintWriter(temp.elementAt(i).getClientSocket().getOutputStream(), true);
                        outputToAll.println(myName + " Disconnected.");
                    }
                    serverGUI.setTextArea(myName + " Disconnected.");
                }
                else if(messageLine.startsWith(MyProtocol.SHOW_ONLINE))
                {
                    String myName = messageLine.substring(MyProtocol.SHOW_ONLINE.length());
                    String names = "";
                    int j = 0;
                    for(int i = 0; i < temp.size(); i++)
                    {
                        String hisName = temp.elementAt(i).name;
                        if(hisName.equals(myName))
                        {
                            j = i;
                        }
                        names += hisName + "-";
                    }
                    
                    PrintWriter outputToMe = new PrintWriter(temp.elementAt(j).getClientSocket().getOutputStream(), true);
                    outputToMe.println(MyProtocol.SHOW_ONLINE + names);
                }
                else if(messageLine.startsWith(MyProtocol.IS_ONLINE))
                {
                    String name = messageLine.substring(MyProtocol.IS_ONLINE.length());
                    boolean isOnline = false;
                    for(int i = 0; i < temp.size(); i++)
                    {
                        if(name.equals(temp.elementAt(i).getName()))
                        {
                            isOnline = true;
                            break;
                        }
                    }
                    if(isOnline)
                    {
                        PrintWriter outputToMe = new PrintWriter(clientSocket.getOutputStream(), true);
                        outputToMe.println(MyProtocol.COLOR_CONNECT);
                    }
                    else
                    {
                        PrintWriter outputToMe = new PrintWriter(clientSocket.getOutputStream(), true);
                        outputToMe.println(MyProtocol.COLOR_DISCONNECT);
                    }
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
                stop();
            }
        }
    }
    /**
     * this method stops the thread.
    */
    public void stop()
    {
        this.running = false;
        try{
            this.clientSocket.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * this method stops the thread and the client thread.
    */
    public void stopAll()
    {
        PrintWriter output = null;
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(MyProtocol.SERVER_STOPPED);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        stop();
    }
}

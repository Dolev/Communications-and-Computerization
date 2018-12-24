package Server;

/**
 * this class means the protocol of my project.
 * 
 * 
 */
public class MyProtocol {
    public static final int PORT = 25000; // port to this project.
    public static final String TOALL = "toAll"; // broadcast message
    public static final String TOONE = "toOne"; // singular message
    public static final String CONNECT_CMD = "Connect"; // connect request from a user to the server
    public static final String DISCONNECT_CMD = "Disconnect"; // disconnect request from a user to the server
    public static final String SHOW_ONLINE = "SHOWONLINE"; // request to show online clients
    public static final String PRIVATE = "Private from "; // private start to a private message
    public static final String NOT_CONNECTED = "notConnected"; // client not connected response after sending private message which faild
    public static final String COLOR_CONNECT = "green" ;// green color to private chatting
    public static final String COLOR_DISCONNECT = "red"; // red color to private chatting
    public static final String IS_ONLINE = "isOnline"; // requesting knowledge if a client is online
    public static final String SERVER_STOPPED = "serverStopped"; // notifying to all clients that the server is stopped
}

package Client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 * this class handles client GUI
 */
public class Client extends javax.swing.JFrame {
    private ClientThread clientThread;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private boolean isOnlineTo;
	private JLabel jLabel3;
    
    public Client() {
        initComponents();
        isOnlineTo = false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConnect = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        btnShowOnline = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        txtSend = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMessages = new javax.swing.JTextArea();
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnConnect.setText("Connect");
        btnConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConnectMouseClicked(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Address:");

        txtAddress.setText("localhost");
        
        jLabel3.setText("Private:");

        btnShowOnline.setText("Show Online");
        btnShowOnline.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowOnlineMouseClicked(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendMouseClicked(evt);
            }
        });

        txtTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtToKeyReleased(evt);
            }
        });

        txtMessages.setEditable(false);
        txtMessages.setColumns(20);
        txtMessages.setRows(5);
        jScrollPane1.setViewportView(txtMessages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowOnline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnect)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShowOnline)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend)
                    .addComponent(txtSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * this method gets data from the client thread.
     * @param s is the stream which come from the client thread. 
    */
    public void actions(String s)
    {
        if(s.startsWith(MyProtocol.SHOW_ONLINE))
        {
            String list = showOnline(s);
            txtMessages.append("Online players:\n");
            setTextArea(list);
            txtMessages.append("---------------------------\n");
        }
        else if(s.startsWith(MyProtocol.SERVER_STOPPED))
        {
            input = null;
            socket = null;
            output = null;
            setTextArea("Server disconnected.");
            txtSend.setEditable(false);
            txtTo.setEditable(false);
            txtName.setEditable(true);
            txtAddress.setEditable(true);
            btnConnect.setText("Connect");
        }
        else
        {
            txtMessages.append(s + "\n");
        }
    }
    /**
     * this method prints on the messages window
     * @param s is the string which printed. 
    */
    public void setTextArea(String s)
    {
        txtMessages.append(s + "\n");
    }
    /**
     * this method makes online clients list.
     * @param s is the string which contains list of online clients. 
     * @return the list of clients.
    */
    public String showOnline(String s)
    {
        s = s.substring(MyProtocol.SHOW_ONLINE.length());
        String ret = "";
        String name = "";
        for(int i = 0; i < s.length(); i++)
        {
            if(s.charAt(i) != '-')
                name += s.charAt(i);
            else
            {
                ret += name + "\n";
                name = "";                    
            }
        }
        ret = ret.substring(0, ret.length() - "\n".length());
        return ret;
    }
    /**
     * this method gets deals with the client when connected and disconnected.
     * @param evt is the event. 
    */
    private void btnConnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConnectMouseClicked
        // TODO create clientThread and send the "connect" message to the server
        String address = txtAddress.getText();
        String name = txtName.getText();
        if(btnConnect.getText().equals("Connect"))
        {
            if((address.equals(""))) JOptionPane.showMessageDialog(null, "Must enter address", "Error", HEIGHT);
            else if(name.equals("")) JOptionPane.showMessageDialog(null, "Must enter name", "Error", HEIGHT);
            else
            {
                connect(name, address);
            }
        }
        else if(btnConnect.getText().equals("Disconnect"))
        {
            disconnect();
        }
    }//GEN-LAST:event_btnConnectMouseClicked
    /**
     * this method being called from btnConnectMouseClicked method.
     * deals with the connection of the client.
     * @param name is the name of the client.
     * @param address is the ip address of the server.
    */
    public void connect(String name, String address)
    {
        try{
            socket = new Socket(address, MyProtocol.PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println(MyProtocol.CONNECT_CMD + name);
            clientThread = new ClientThread(this, socket);   
            new Thread(clientThread).start();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Can not connect the server.", "Error", HEIGHT);
            socket = null;
            output = null;
            clientThread.stopper();
            clientThread = null;
            return;
        }
        txtName.setEditable(false);
        txtAddress.setEditable(false);
        btnConnect.setText("Disconnect");
        txtSend.setEditable(true);
        txtTo.setEditable(true);
        setTextArea("You are Connected.");
    }
    /**
     * this method being called from btnConnectMouseClicked method.
     * deals with disconnection of the client.
    */
    public void disconnect()
    {
        output.println(MyProtocol.DISCONNECT_CMD + txtName.getText());
        clientThread.stopper();
        input = null;
        socket = null;
        output = null;
        setTextArea("You are Disconnected.");
        txtSend.setEditable(false);
        txtTo.setEditable(false);
        txtName.setEditable(true);
        txtAddress.setEditable(true);
        btnConnect.setText("Connect");
    }
    /**
     * this method sends in the socket message to other clients..
     * @param evt is the event. 
    */
    private void btnSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseClicked
        if(btnConnect.getText().equals("Disconnect"))
        {
            if(txtSend.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Must enter message.", "Errot", HEIGHT);
            }
            else
            {
                if(txtTo.getText().equals(""))
                {
                    try {
                        output = new PrintWriter(socket.getOutputStream(), true);
                        output.println(MyProtocol.TOALL + txtSend.getText());
                        
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    txtSend.setText("");
                }
                else
                {
                    String name = txtTo.getText();
                    try {
                        output = new PrintWriter(socket.getOutputStream(), true);
                        output.println(MyProtocol.TOONE + name);
                        output.println(txtSend.getText());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    if(isOnlineTo) setTextArea("Private to " + name + ": " + txtSend.getText());
                    txtSend.setText("");
                }
            }
        }
        else if(btnConnect.getText().equals("Connect"))
        {
            JOptionPane.showMessageDialog(null, "Must connect first.", "Error", HEIGHT);
        }
        
    }//GEN-LAST:event_btnSendMouseClicked
    /**
     * this method clears the mwssages window.
     * @param evt is the event.
    */
    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        txtMessages.setText("");
    }//GEN-LAST:event_btnClearMouseClicked
    /**
     * this method requests online list from the server..
     * @param evt is the event.
    */
    private void btnShowOnlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowOnlineMouseClicked
        if(btnConnect.getText().equals("Connect"))
        {
            JOptionPane.showMessageDialog(null, "Must connect first.", "Error", HEIGHT);
        }
        else
        {
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(MyProtocol.SHOW_ONLINE + txtName.getText());
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btnShowOnlineMouseClicked
    /**
     * this method requests from the server if the client who meant to be sent a message is online.
     * @param evt is the event. 
    */
    private void txtToKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtToKeyReleased
        if(!txtTo.getText().equals(""))
        {
            String name = txtTo.getText();
            try {
                output = new PrintWriter(socket.getOutputStream(), true);
                output.println(MyProtocol.IS_ONLINE + name);
                output.println(txtName.getText());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            txtTo.setBackground(Color.white);
        }
    }//GEN-LAST:event_txtToKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(btnConnect.getText().equals("Disconnect")){
            disconnect();
        }
    }//GEN-LAST:event_formWindowClosing
    /**
     * this method paints the private message text..
     * @param online means if the client which wroten is online. 
    */
    public void setOnlineOrOffline(boolean online)
    {
        if(txtTo.getText().equals(""))
        {
            txtTo.setBackground(Color.white);
            isOnlineTo = false;
        }
        else if(online)
        {
            txtTo.setBackground(Color.green);
            isOnlineTo = true;
        }
        else
        {
            txtTo.setBackground(Color.red);
            isOnlineTo = false;
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnShowOnline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextArea txtMessages;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSend;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import config.Configuration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import management.UserManagement;

/**
 *
 * @author nishant
 */
public class Connector {

    UserManagement userManagement = null;
    public void startServer() {
        userManagement = new UserManagement();
        try {
            ServerSocket serverSocket = new ServerSocket(Configuration.PORT);
            serverSocket.setSoTimeout(0);
            acceptClients(serverSocket);
            
        } catch (IOException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void acceptClients(final ServerSocket serverSocket) {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Socket socket = serverSocket.accept();
                        Configuration.CLIENT_COUNT++;
                        System.err.println("Client-" + Configuration.CLIENT_COUNT + " connected");
                        System.err.println("Server connected to " + socket.getRemoteSocketAddress().toString());
                       
                        Configuration.clientSocket[Configuration.CLIENT_COUNT] = socket;
                        userManagement.createNewThread(socket);
                    } catch (IOException ex) {
                        Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import config.Configuration;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import javax.swing.JOptionPane;
import service.ClientService;

/**
 *
 * @author nishant
 */
public class UserManagement implements Serializable {

    public void createNewThread(final Socket socket) {
        new Thread() {
            @Override
            public void run() {
                try {
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Configuration.objInpMap.put(socket, ois);
                    Configuration.objOutMap.put(socket, oos);
                    
                    Message message = new Message();
                    Message reply = new Message();
                    ClientService clientService = new ClientService();
                    while(true) {
                        //Takes a request from client
                        message = (Message) ois.readObject();
                        System.err.println("server received request");
                        
                        //Serves the client and generates a reply
                        reply = clientService.serveClient(message);
                        System.err.println("Server processed the request");
                        
                        //sends back the reply to client
                        oos.writeObject(reply);
                        oos.flush();
                        System.err.println("Server sent the reply");
                    }
                
                } catch(Exception ex) {
                    //JOptionPane.showMessageDialog(null, ex.getMessage() + ex.getClass() + "");
                }
            }

        }.start();
    }

    public void makeAnnouncement() throws IOException {
        int i;
        Message announcement = new Message();
        announcement.code = 11;
        announcement.displayMessage = "Contest is running";
        
        for(i = 1; i <= Configuration.CLIENT_COUNT; i++) {
            //ObjectInputStream ois = Configuration.objInpMap.get(Configuration.clientSocket[i]);
            ObjectOutputStream oos = Configuration.objOutMap.get(Configuration.clientSocket[i]);
            oos.writeObject(announcement);
            oos.flush();
        }
    }
    
}

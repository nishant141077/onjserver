/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.SecretKey;

/**
 *
 * @author nishant
 */
public class Configuration {
    public static int PORT = 9900;
    public static int CLIENT_COUNT = 0;
    public static Socket clientSocket[] = new Socket[5000];
    
    public static byte[] encryptionKey = {'O', 'n', 'l', 'j', '2', '0', '1', '6'};
    public static SecretKey desKey;
    
    /* Admin Cache */
    public static String HANDLE = "";
}

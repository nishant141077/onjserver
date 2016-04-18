/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import com.sun.mail.util.BASE64DecoderStream;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 *
 * @author nishant
 */
public class Security {
    public static SecretKey generateEncryptionKey() throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance("DES").generateKey();
    }
    
    public static String getDecryptedPassword(String encryptedPassword, SecretKey desKey) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            
            //decode encryptedPassword to base64
            byte[] decryptedPassword = BASE64DecoderStream.decode(encryptedPassword.getBytes());
            byte[] utf8 = cipher.doFinal(decryptedPassword);
            return new String(utf8, "UTF8");
        } catch(Exception exception) {
            //JOptionPane.showMessageDialog(null, "Server 1: " + exception.getClass());
        }
        return "";
    }
}

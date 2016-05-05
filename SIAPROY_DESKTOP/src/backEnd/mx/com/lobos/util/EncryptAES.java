/*
 *  _______________________________________________________________________
 * |                    COPYRIGHT (C) BY                                   |
 * |               DERECHOS RESERVADOS (C) POR                             |
 * |                LOBO SOFTWARE S.A. DE  C.V.                            |
 * |                                                                       |
 * |Ninguna parte de esta obra, parcial o total puede                      | 
 * |ser reproducida o transmitida, mediante ningun sistema                 |
 * |o metodo electronico o mecanico (incluyendo el                         |   
 * |fotocopiado, la grabacion, o cualquier sistema de                      |  
 * |recuperacion y almacenamiento de informacion),                         |  
 * |SIN LA AUTORIZACION POR ESCRITO DEL AUTOR.                             |  
 * |                                                                       |  
 * |Derechos reservados                                                    |  
 * |(C) 2012, LOBO SOFTWARE, S.A. DE C.V. (*)                              |  
 * |                                                                       |  
 * |Esta obra forma parte del SIAL-CH (C) "CAPITAL HUMANO"                 |  
 * |                                                                       |  
 * |(*) Marca registrada por                                               |   
 * |LOBO SOFTWARE, S.A. DE C.V.                                            |  
 * |_______________________________________________________________________|  
 *  Document     : EncryptAES.java
 * Created on    : 02 may 2016 05:58:54 PM
 * Author        : CCL
 * Modifications : 
 */


package backEnd.mx.com.lobos.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


public class EncryptAES{
    
    private static String       secret = "tvnw63ufg9gh5392";
    private static SecretKey    key;
    private static Cipher       cipher;
    
    public EncryptAES(){
        try{
            key     = new SecretKeySpec(secret.getBytes(), "AES");
            cipher  = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        }catch(Throwable t){
        }
    }
    
    public String encrypt(String plainText){
        byte[]  cipherText;
        String  result = null;
        try{
            
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText  = cipher.doFinal(plainText.getBytes());
            result      = new String(Base64.encodeBase64(cipherText));
            
        }catch(Exception ex){
            ex.getMessage();
        }
        return  result;
    }
    
    public String decrypt(String codedText){
        byte[]  encypted    = Base64.decodeBase64(codedText.getBytes());
        byte[]  decrypted   = null;
        try{
            
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(encypted);
            
        }catch(Exception ex){
            ex.getMessage();
        }
        return new String(decrypted);
    }
    
}
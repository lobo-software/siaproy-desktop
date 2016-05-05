/*
 *  _______________________________________________________________________
 * |                                         COPYRIGHT (C) BY                                                          |
 * |                             DERECHOS RESERVADOS (C) POR                                             |
 * |                                 LOBO SOFTWARE S.A. DE  C.V.                                               |
 * |                                                                                                                                  |
 * |Ninguna parte de esta obra, parcial o total puede                                                          | 
 * |ser reproducida o transmitida, mediante ningun sistema                                                 |
 * |o metodo electronico o mecanico (incluyendo el                                                             |   
 * |fotocopiado, la grabacion, o cualquier sistema de                                                          |  
 * |recuperacion y almacenamiento de informacion),                                                           |  
 * |SIN LA AUTORIZACION POR ESCRITO DEL AUTOR.                                                 |  
 * |                                                                                                                                  |  
 * |Derechos reservados                                                                                                   |  
 * |(C) 2012, LOBO SOFTWARE, S.A. DE C.V. (*)                                                            |  
 * |                                                                                                                                  |  
 * |Esta obra forma parte del SIAL-CH (C) "CAPITAL HUMANO"                                         |  
 * |                                                                                                                                  |  
 * |(*) Marca registrada por                                                                                               |   
 * |LOBO SOFTWARE, S.A. DE C.V.                                                                                |  
 * |_______________________________________________________________________|  
 *  Document     : Conexion.java
 * Created on    : 25 Apr 2016 5:00:10 PM
 * Author           : SVA
 * Modifications : 
 */


package backEnd.mx.com.lobos.util;


import frontEnd.util.GeneraCuadroMensaje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;


/**
 *
 * @author Lobo Software
 */
public class Conexion {
    
    private static          HashMap<String, HashMap>    configuracionesConexion = null;
    private static final    EncryptAES                  ENCRYPT_AES             = new EncryptAES();
    
    public static synchronized void setParametrosConexion(String usr, HashMap<String, String> parametrosHsm){
        if(configuracionesConexion == null){
            configuracionesConexion = new HashMap();
        }else if(configuracionesConexion.containsKey(usr)){
            configuracionesConexion.remove(usr);
        }
        
        configuracionesConexion.put(usr, parametrosHsm);
    }
    
    public static synchronized String getParametrosConexion(String usr, String key){
        HashMap<String, String> temp;
        String                  valor = "";
        
        if(configuracionesConexion != null && configuracionesConexion.containsKey(usr)){
            temp = configuracionesConexion.get(usr);
            if(temp.containsKey(key)){
                valor = temp.get(key);
            }
        }
        return valor;
    }
    
    public static Connection creaConexionLocalBD() throws Exception{
        String dbms = "jdbc:derby:C:/Users/ceciliac/Desktop/siaproy-desktop/SIAPROY_DESKTOP/database/SIAPROY_DESKTOP";
        String uName = "LOBORH";
        String uPass = "k_23h45t";
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con = DriverManager.getConnection(dbms, uName, uPass);
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: Conexion. \nMÉTODO: creaConexionLocalBD");
        }
        return con;
    }
    
    public static synchronized Connection getConnectionSial() throws Exception{
        String  cveUsuario,
                ipServidorBaseDatos,
                puertoServidorBaseDatos,
                baseDatos,
                esquema,
                passwordEBD,
                driver,
                url;
        Connection conexion = null;
        try{
            
            cveUsuario              = "SPRYDSK";
            ipServidorBaseDatos     = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "servidorBaseDatos"));
            puertoServidorBaseDatos = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "puertoBD"));
            baseDatos               = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "baseDatos"));
            esquema                 = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "esquema"));
            passwordEBD             = ENCRYPT_AES.decrypt(ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "passwordEsquema")));
            driver                  = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "driver"));
            url                     = "jdbc:oracle:thin:@".concat(ipServidorBaseDatos).concat(":").concat(puertoServidorBaseDatos).concat(":").concat(baseDatos);
            
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, esquema, passwordEBD);
            
        }catch(Exception ex){
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: Conexion. \nMÉTODO: getConnectionSial");
        }
        return conexion;
    }
    
}

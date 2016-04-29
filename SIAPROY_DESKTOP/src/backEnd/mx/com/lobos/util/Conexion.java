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

import frontEnd.controller.SPPRYF12Controller;
import frontEnd.util.GeneraCuadroMensaje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lobo Software
 */
public class Conexion {
    private static Connection con = null;

    public static Connection creaConexionLocalBD() throws Exception{
        String dbms = "jdbc:derby:C:/SIAPROY_DESKTOP/siaproy-desktop/SIAPROY_DESKTOP/database/SIAPROY_DESKTOP";
        String uName = "LOBORH";
        String uPass = "k_23h45t";
        con = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con = DriverManager.getConnection(dbms, uName, uPass);
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: Conexion. \nMÉTODO: creaConexionLocalBD");
        }
        return con;
    }
    
}

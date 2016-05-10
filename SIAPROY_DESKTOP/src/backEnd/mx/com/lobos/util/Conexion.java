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
 * Modifications : 10/May/2016 13:07 SVA (LOBO_000076): Se mejora el método "creaConexionLocalBD" y se da formato al archivo.
 */
package backEnd.mx.com.lobos.util;

import frontEnd.util.GeneraCuadroMensaje;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author Lobo Software
 */
public class Conexion {

    private static HashMap<String, HashMap> configuracionesConexion = null;
    private static final EncryptAES ENCRYPT_AES = new EncryptAES();

    public static synchronized void setParametrosConexion(String usr, HashMap<String, String> parametrosHsm) {
        if (configuracionesConexion == null) {
            configuracionesConexion = new HashMap();
        } else if (configuracionesConexion.containsKey(usr)) {
            configuracionesConexion.remove(usr);
        }

        configuracionesConexion.put(usr, parametrosHsm);
    }

    public static synchronized String getParametrosConexion(String usr, String key) {
        HashMap<String, String> temp;
        String valor = "";

        if (configuracionesConexion != null && configuracionesConexion.containsKey(usr)) {
            temp = configuracionesConexion.get(usr);
            if (temp.containsKey(key)) {
                valor = temp.get(key);
            }
        }
        return valor;
    }

    public static Connection creaConexionLocalBD() throws Exception {
        String dbms = "jdbc:derby:SIAPROY_DESKTOP_DATABASE;create=true";
        String uName = "LOBORH";
        String uPass = "k_23h45t";
        Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con = DriverManager.getConnection(dbms, uName, uPass);
            Statement st = con.createStatement();
            ResultSet rs = null;
            DatabaseMetaData meta = con.getMetaData();
            rs = meta.getTables(null, null, "SPD_REPORTES_ACTIVIDADES", new String[]{"TABLE"});
            if (!rs.next()) {
                st.execute("CREATE SCHEMA LOBORH");
                st.execute("CREATE TABLE \"SPD_REPORTES_ACTIVIDADES\"\n"
                        + "  (\n"
                        + "    \"ID_REPORTE_ACTIVIDAD\"   NUMERIC NOT NULL,\n"
                        + "    \"ID_PROY_COL_PLAN_ACT\"   NUMERIC DEFAULT NULL,\n"
                        + "    \"ID_REPORTE_COLABORADOR\" NUMERIC DEFAULT NULL,\n"
                        + "    \"PROYECTO\" CLOB DEFAULT NULL,\n"
                        + "    \"ACTIVIDAD\" CLOB DEFAULT NULL,\n"
                        + "    \"FECHA\"                  DATE NOT NULL,\n"
                        + "    \"DESCRIPCION\" CLOB NOT NULL,\n"
                        + "    \"DURACION\"            NUMERIC(18, 4) NOT NULL,\n"
                        + "    \"HORA_INICIO\"         NUMERIC(18, 4) DEFAULT NULL,\n"
                        + "    \"HORA_FIN\"            NUMERIC(18, 4) DEFAULT NULL,\n"
                        + "    \"AVANCE\"              NUMERIC(18, 4) NOT NULL,\n"
                        + "    \"SINCRONIZADO_SIAPROY\"              VARCHAR(1) NOT NULL,\n"
                        + "    \"USUARIO\"             VARCHAR(10) NOT NULL,\n"
                        + "    \"FECHA_ACTUALIZACION\" TIMESTAMP NOT NULL\n"
                        + "  )");
            }
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: Conexion. \nMÉTODO: creaConexionLocalBD");
        }
        return con;
    }

    public static synchronized Connection getConnectionSial() throws Exception {
        String cveUsuario,
                ipServidorBaseDatos,
                puertoServidorBaseDatos,
                baseDatos,
                esquema,
                passwordEBD,
                driver,
                url;
        Connection conexion = null;
        try {

            cveUsuario = "SPRYDSK";
            ipServidorBaseDatos = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "servidorBaseDatos"));
            puertoServidorBaseDatos = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "puertoBD"));
            baseDatos = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "baseDatos"));
            esquema = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "esquema"));
            passwordEBD = ENCRYPT_AES.decrypt(ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "passwordEsquema")));
            driver = ENCRYPT_AES.decrypt(Conexion.getParametrosConexion(cveUsuario, "driver"));
            url = "jdbc:oracle:thin:@".concat(ipServidorBaseDatos).concat(":").concat(puertoServidorBaseDatos).concat(":").concat(baseDatos);

            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, esquema, passwordEBD);

        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: Conexion. \nMÉTODO: getConnectionSial");
        }
        return conexion;
    }

}

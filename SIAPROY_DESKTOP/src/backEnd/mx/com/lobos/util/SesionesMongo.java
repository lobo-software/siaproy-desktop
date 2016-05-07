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
 *  Document     : SesionesMongo.java
 * Created on    : 02 may 2016 05:34:40 PM
 * Author        : CCL
 * Modifications : 
 */


package backEnd.mx.com.lobos.util;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import frontEnd.util.GeneraCuadroMensaje;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SesionesMongo {
    
    public static synchronized DBCollection iniciaConexion() throws Exception{
        MongoClient     mongoClient;
        DBCollection    sesiones = null;
        DB              mDB;
        try{
            
            mongoClient     = new MongoClient(new MongoClientURI("mongodb://192.0.0.170:27018,192.0.0.170:27019,192.0.0.170:27020"));
            mDB             = mongoClient.getDB("lobodb");
            sesiones        = mDB.getCollection("sesiones");
            
        }catch(Exception ex){
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SesionesMongo. \nMÉTODO: iniciaConexion");  
        }
        return sesiones;
    }
    
    public static void getDataConection() throws Exception{
        DBObject                    consulta,
                                    dbObjSesion,
                                    datosObj;
        HashMap<String, String>     conexionHsm = new HashMap<String, String>();
        String                      cveUsuario;
        Map.Entry<String, String>   entry;
        DBCollection                sesiones;
        try{
            
            cveUsuario = "SPRYDSK";
            consulta = new BasicDBObject();
            consulta.put("cveUsuario", cveUsuario);
            
            sesiones    = iniciaConexion();
            dbObjSesion = sesiones.findOne(consulta);
            
            if(dbObjSesion != null){
                
                datosObj = (DBObject) dbObjSesion.get("datosConexion");
                for(Iterator it = datosObj.toMap().entrySet().iterator(); it.hasNext();){
                    entry = (Map.Entry<String, String>) it.next();
                    conexionHsm.put(entry.getKey(), entry.getValue());
                }
                
                if(!conexionHsm.isEmpty()){
                    Conexion.setParametrosConexion(cveUsuario, conexionHsm);
                }
                
            }else{
                throw new Exception("No hay disponibles usuarios con la clave " + cveUsuario);
            }
            
        }catch(Exception ex){
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SesionesMongo. \nMÉTODO: getDataConection");
        }
    }
    
}

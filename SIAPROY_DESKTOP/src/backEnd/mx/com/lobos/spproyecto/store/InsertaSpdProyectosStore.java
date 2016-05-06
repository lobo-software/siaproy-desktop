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
 *  Document     : InsertaSpdProyectosStore.java
 * Created on    : 05 may 2016 04:28:32 PM
 * Author        : CCL
 * Modifications : 
 */
package backEnd.mx.com.lobos.spproyecto.store;

import backEnd.mx.com.lobos.spproyecto.service.SpProyectoService;
import frontEnd.util.GeneraCuadroMensaje;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Lobo Software
 */
public class InsertaSpdProyectosStore {
    public ObservableList<String> inserta(HashMap<String, Object> parametrosHsm) throws Exception {
        ObservableList<String> registros = null;
        try {
            SpProyectoService sras = new SpProyectoService();
            registros = sras.insertaActividadesSiaproyWeb(parametrosHsm);
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: ConsultaSpdProyectosStore. \nMÉTODO: consultaProyectos");
        }
        return registros;
    }
}

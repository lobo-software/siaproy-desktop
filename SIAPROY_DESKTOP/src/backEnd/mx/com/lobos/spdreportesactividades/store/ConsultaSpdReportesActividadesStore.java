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
 *  Document     : ConsultaSpdReportesActividadesStore.java
 * Created on    : 26 Apr 2016 11:33:38 AM
 * Author           : SVA
 * Modifications : 
 */
package backEnd.mx.com.lobos.spdreportesactividades.store;

import backEnd.mx.com.lobos.spdreportesactividades.service.SpdReportesActividadesService;
import frontEnd.model.SpdReportesActividadesModel;
import frontEnd.util.GeneraCuadroMensaje;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author Lobo Software
 */
public class ConsultaSpdReportesActividadesStore {

    public ObservableList<SpdReportesActividadesModel> consultaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        ObservableList<SpdReportesActividadesModel> registros = null;
        try {
            SpdReportesActividadesService sras = new SpdReportesActividadesService();
            registros = sras.consulta(parametrosHsm);
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: ConsultaSpdReportesActividadesStore. \nMÉTODO: consultaActividades");
        }
        return registros;
    }

}

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
 *  Document     : TimerTiempoFin.java
 * Created on    : 25 Apr 2016 11:49:43 AM
 * Author           : SVA
 * Modifications : 
 */
package frontEnd.model.stopWatch;

import java.time.LocalDateTime;
import javafx.concurrent.Task;

/**
 *
 * @author Lobo Software
 */
public class TimerTiempoFin extends Task<Void> {
    private long finalHours, finalMinutes, finalSeconds;
    
     public void setHoraFin(long finalHours, long finalMinutes, long finalSeconds){
       this.finalHours = finalHours;
       this.finalMinutes = finalMinutes;
       this.finalSeconds = finalSeconds;
   }

    @Override
    protected Void call() throws Exception {
         updateMessage(String.format("%02d", finalHours) + ":" + String.format("%02d", finalMinutes) + ":" + String.format("%02d", finalSeconds));
         return null;
    }
    
}

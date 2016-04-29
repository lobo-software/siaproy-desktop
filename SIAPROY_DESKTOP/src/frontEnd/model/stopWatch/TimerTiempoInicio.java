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
 *  Document     : TimerTiempoInicio.java
 * Created on    : 25 Apr 2016 11:49:34 AM
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
public class TimerTiempoInicio extends Task<Void> {

    private LocalDateTime startTime;

   public void setHoraInicio(LocalDateTime startTime){
       this.startTime = startTime;
   }

    @Override
    protected Void call() throws Exception {
       updateMessage(String.format("%02d", startTime.getHour()) + ":" + String.format("%02d", startTime.getMinute()) + ":" + String.format("%02d", startTime.getSecond()));
       return null;
    }

}

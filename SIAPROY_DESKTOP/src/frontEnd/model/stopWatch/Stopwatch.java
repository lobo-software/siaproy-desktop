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
 *  Document     : Stopwatch.java
 * Created on    : 06 abr 2016 12:32:40 PM
 * Author        : SVA
   Modifications : 14/Apr/2016 13:44 CCL (LOBO_000076): Se eliminan librerías inicesarias, se comentaron unos segmentos de código}
                  para saber  exactamente  donde se realizaronmodificaciones al momento de la integración del StopWatch, se eliminó espacio }
                  inecesario así como librerías no usadas dentro  de la clase.                                    
 */
package frontEnd.model.stopWatch;

/**
 *
 * @author Lobo Software
 */
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.time.Duration;
import java.time.LocalDateTime;
import javafx.scene.control.TextField;

public class Stopwatch extends Region {

    private enum StopWatchStatus {

        STOPPED, RUNNING, PAUSE
    };

    private Duration currentTime;
    private long contador = 0;
    private LocalDateTime startTime = LocalDateTime.now();

    StopwatchWorker stopWatchWorker;
    StopWatchStatus currentStatus = StopWatchStatus.STOPPED;
    //Decalarción de la varible startStop como local para usarla dentro de la 
    public Button startStop;

    public Stopwatch(TextField total, TextField tiempoInicio, TextField tiempoFinal) {
        startStop = AwesomeDude.createIconButton(AwesomeIcon.PLAY);
        //Se de un de HBox se define el tamamo delIconodel Stopwatch boton
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(startStop);
        HBox.setHgrow(startStop, Priority.ALWAYS);
        startStop.setMinWidth(61);
        VBox vBox = new VBox();
        vBox.setSpacing(5d);
        vBox.getChildren().addAll(hBox);
        hBox.prefWidthProperty().bind(vBox.widthProperty());
        this.getChildren().add(vBox);
        startStop.setOnAction(arg0 -> {
            if (currentStatus == StopWatchStatus.STOPPED) {
                AwesomeDude.setIcon(startStop, AwesomeIcon.STOP);
                currentStatus = StopWatchStatus.RUNNING;
                ///Se añaden parametros dentro constructor stpoWatchWorker como los textfield los cuales muestran dentro de la vista la información del tiempo....
                stopWatchWorker = new StopwatchWorker(contador, currentTime, startTime, total, tiempoInicio, tiempoFinal);
                Thread t = new Thread(stopWatchWorker);
                total.textProperty().bind(stopWatchWorker.messageProperty());

                t.setDaemon(true);
                t.start();
                return;
            }
            if (currentStatus == StopWatchStatus.RUNNING) {
                AwesomeDude.setIcon(startStop, AwesomeIcon.PLAY);
                currentTime = stopWatchWorker.stop();
                startTime = stopWatchWorker.getStartTime();
                System.out.println("Stop : " + currentTime);
                contador++;
                stopWatchWorker = null;
                currentStatus = StopWatchStatus.STOPPED;
            }
        });
    }
}

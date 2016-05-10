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
 *  Document     : StopwatchWorker.java
 * Created on    : 06 abr 2016 12:32:40 PM
 * Author        : SVA
   Modifications : 13/Apr/2016 10:44 CCL (LOBO_000076): Se se añaden la funcionalidad a los 
                   a las  variables que haran la impresion de información dentro del los TextFields en pantalla.
                   14/Apr/2016 10:44 CCL(LOBO_000076):Se elimina codigo inecesario y espacios en blacon,por último se da formatoalcódigo.
 15/Apr/2016 13:24 CCL(LOBO_000076):Se eliminaron objetos innecesarios en la clase.
 15/Apr/2016 13:44 CCL (LOBO_000076): No se modificó nada en este modelo.
 29/Abr/2016 17:07 SVA (LOBO_000076): Se mejora funcionalidad para mostrar las horas.
10/May/2016 13:07 SVA (LOBO_000076): Se añade validación en el método call para reanudar el tiempo del día actual en caso de existir el registro en la BDD.
 */
package frontEnd.model.stopWatch;

/**
 *
 * @author Lobo Software
 */
import java.time.Duration;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

public class StopwatchWorker extends Task<Void> {

    public StopwatchWorker(long contador, Duration currentTime, LocalDateTime startTime, String duracion) {
        this.contador = contador;
        this.currentTime = currentTime;
        this.startTime = startTime;
        this.duracion = duracion;
        this.tfTiempoInicio = new TextField();
        this.tfTiempoFin = new TextField();
        tti = new TimerTiempoInicio();
        ttf = new TimerTiempoFin();
    }
    //VARIABLES A NIVEL DE CLASE LAS  QUE  SE UTILIZARAN EN TODOS LOS MÉTODOS.
    private final BooleanProperty stop = new SimpleBooleanProperty(false);
    private LocalDateTime startDateTime;
    private LocalDateTime stopDateTime;
    private LocalDateTime startTime;
    private final long contador;
    private Duration duration;
    private Duration currentTime;
    private final TextField tfTiempoInicio;
    private final TextField tfTiempoFin;
    private String duracion;
    TimerTiempoInicio tti;
    TimerTiempoFin ttf;

    @Override
    protected Void call() throws Exception {
        long currentHours, finalHours, currentMinutes, finalMinutes, currentSeconds, finalSeconds, tempMinutes;
        startDateTime = LocalDateTime.now();
        if (contador == 0) {
            startTime = startDateTime;
        }
        tti.setHoraInicio(startTime);
        while (!stop.getValue()) {
            stopDateTime = LocalDateTime.now();
            duration = Duration.between(startDateTime, stopDateTime);
            if (contador > 0 || !duracion.equals("00:00:00")) {
                if (currentTime != null) {
                    duration = duration.plus(currentTime);
                } else {
                    int hours = Integer.parseInt(duracion.split(":")[0]);
                    int minutes = Integer.parseInt(duracion.split(":")[1]);
                    int seconds = Integer.parseInt(duracion.split(":")[2]);
                    duration = duration.plusSeconds(3600 * hours + 60 * minutes +  seconds);
                }

            }
            currentHours = Math.max(0, duration.toHours());
            currentMinutes = Math.max(0, duration.toMinutes() - 60 * duration.toHours());
            currentSeconds = Math.max(0, duration.getSeconds() - 60 * duration.toMinutes());
            if (contador > 0 || !duracion.equals("00:00:00")) {
                LocalDateTime newTime = LocalDateTime.now();
                finalHours = newTime.getHour();
                finalMinutes = newTime.getMinute();
                finalSeconds = newTime.getSecond();
            } else {
                finalSeconds = (startDateTime.getMinute() + currentMinutes) * 60 + (startDateTime.getSecond() + currentSeconds);
                finalMinutes = finalSeconds / 60;
                finalHours = (startDateTime.getHour() + currentHours) + finalMinutes / 60;
                finalSeconds = Math.max(0, finalSeconds - 60 * finalMinutes);
                if (finalMinutes >= 60) {
                    tempMinutes = finalMinutes * (finalHours - startDateTime.getHour());
                    finalMinutes = Math.max(0, tempMinutes - 60 * (finalHours - startDateTime.getHour()));
                }
            }
            ttf.setHoraFin(finalHours, finalMinutes, finalSeconds);
            //Se agraga  la función  las variables  las cuales  hacen  la imprecion del tiempo Inicio final y total paramostrarse en los textfields de la API.
            System.out.print(String.format("%02d", currentHours) + ":" + String.format("%02d", currentMinutes) + ":" + String.format("%02d", currentSeconds));
            System.out.print("Hora inicio: " + String.format("%02d", startTime.getHour()) + ":" + String.format("%02d", startTime.getMinute()) + ":" + String.format("%02d", startTime.getSecond()));
            System.out.print("Hora final: " + String.format("%02d", finalHours) + ":" + String.format("%02d", finalMinutes) + ":" + String.format("%02d", finalSeconds));
            System.out.println();
            updateMessage(String.format("%02d", currentHours) + ":" + String.format("%02d", currentMinutes) + ":" + String.format("%02d", currentSeconds));
            tti.call();
            ttf.call();
            Thread.sleep(1000);
        }
        return null;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Boolean getStop() {
        return stop.getValue();
    }

    public LocalDateTime getStopDateTime() {
        return stopDateTime;
    }

    public void setStop(Boolean stop) {
        this.stop.setValue(stop);
    }

    public Duration stop() {
        setStop(true);
        return duration;
    }

    public BooleanProperty stopProperty() {
        return this.stop;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StringProperty getTiempoInicioProperty() {
        return tfTiempoInicio.textProperty();
    }

    public StringProperty getTiempoFinalProperty() {
        return tfTiempoFin.textProperty();
    }
}

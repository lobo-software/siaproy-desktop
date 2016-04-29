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
 *  Document     : Actividades.java
 * Created on    : 08 abr 2016 10:23:09 AM
 * Author        : CCL
 * Modifications : 08/Apr/2016 18:44 CCL (LOBO_000076): Se añaden cabeceras de licencia a los archivos. 
                   14/Apr/2016 09:53 CCL (LOBO_000076): Se elimina espaciocios innecesarios y codigoinnecesario.
14/Apr/2016 17:00 SVA (LOBO_000076): Se cambia el tipo de dato en las propiedades del modelo / se da formato y restructuración de métodos en el archivo.
15/Apr/2016 13:25 CCL(LOBO_000076):Se cambió el tipo de dato así como los setters y getters de la variable tiempo / se añade la propiedad Stopwatch al modelo.
16/Apr/2016 11:40 SVA (LOBO_000076):  Se da formato al archivo y se elimina el parámetro de tipo Stopwatch en el constructor.


 */
package frontEnd.model;

import frontEnd.model.stopWatch.Stopwatch;
import frontEnd.util.SialCheckBox;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Lobo Software
 */
public class Actividades implements SialCheckBox{

    private final SimpleObjectProperty<Stopwatch> stopWatch;
    private String proyecto;
    private String actividad;
    private final SimpleStringProperty tiempoTotal;
    private final SimpleStringProperty tiempoInicio;
    private final SimpleStringProperty tiempoFin;
    private SimpleStringProperty descripcion;
    private String avance;
    private SimpleObjectProperty<Date> fecha;
    private SimpleBooleanProperty activo;

    //Se inicia con el modelado de datos
    public Actividades() {
        this.stopWatch = new SimpleObjectProperty();
        this.proyecto = "";
        this.actividad = "";
        this.tiempoTotal = new SimpleStringProperty("");
        this.tiempoInicio = new SimpleStringProperty("");
        this.tiempoFin = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.avance = "";
        this.fecha = new SimpleObjectProperty<>();
        this.activo = new SimpleBooleanProperty();

    }

    public Actividades(Stopwatch stopWatch, String proyecto, String actividad, String tiempoTotal, String tiempoInicio, String tiempoFin, String descripcion, String avance, Date fecha, String activo) {
        this.stopWatch = new SimpleObjectProperty(stopWatch);
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.tiempoTotal = new SimpleStringProperty(tiempoTotal);
        this.tiempoInicio = new SimpleStringProperty(tiempoInicio);
        this.tiempoFin = new SimpleStringProperty(tiempoFin);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.avance = avance;
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.activo = new SimpleBooleanProperty(activo.equals("S") || activo.equals("A"));

    }

    public void setStopWatch(Stopwatch stopWatch) {
        this.stopWatch.set(stopWatch);
    }

    public Stopwatch getStopWatch() {
        return stopWatch.get();
    }

    public ObjectProperty<Stopwatch> stopWatchProperty() {
        return stopWatch;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal.set(tiempoTotal);
    }

    public String getTiempoTotal() {
        return tiempoTotal.get();
    }

    public StringProperty tiempoTotalProperty() {
        return tiempoTotal;
    }

    public void setTiempoInicio(String tiempoInicio) {
        this.tiempoInicio.set(tiempoInicio);
    }

    public String getTiempoInicio() {
        return tiempoInicio.get();
    }

    public StringProperty tiempoInicioProperty() {
        return tiempoInicio;
    }

    public void setTiempoFin(String tiempoFinal) {
        this.tiempoFin.set(tiempoFinal);
    }

    public String getTiempoFin() {
        return tiempoFin.get();
    }

    public StringProperty tiempoFinProperty() {
        return tiempoFin;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getAvance() {
        return avance;
    }
    
        public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }

    public Date getFecha() {
        return fecha.get();
    }

    public ObjectProperty<Date> fechaProperty() {
        return fecha;
    }

    public void setActivo(boolean activo) {
        this.activo.set(activo);
    }

    public boolean getActivo() {
        return activo.get();
    }

    public BooleanProperty activoProperty() {
        return activo;
    }
    
    @Override
    public SimpleBooleanProperty getCheckedProperty(){
        return activo;
    }

    @Override
    public void setOneSelection() {
        this.setActivo(false);
    }

    @Override
    public void setAllSelection(boolean valueHeader) {
        this.setActivo(valueHeader);
    }

}

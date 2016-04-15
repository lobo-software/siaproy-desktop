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


 */
package frontEnd.model;

import frontEnd.model.stopWatch.Stopwatch;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Lobo Software
 */
public class Actividades {

    private String proyecto;
    private String actividad;
    private SimpleStringProperty tiempo;
    private String descripcion;
    private String avance;
    private String Fecha;
    private SimpleObjectProperty<Stopwatch> stopWatch;

    //Se inicia con el modelado de datos
    public Actividades() {
        this.proyecto = "";
        this.actividad = "";
        this.tiempo = new SimpleStringProperty("");
        this.descripcion = "";
        this.avance = "";

    }

    public Actividades(Stopwatch stopWatch, String proyecto, String actividad, String tiempo, String descripcion, String avance) {
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.tiempo = new SimpleStringProperty(tiempo);
        this.descripcion = descripcion;
        this.avance = avance;
          this.stopWatch = new SimpleObjectProperty(stopWatch);
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

    public void setTiempo(String tiempo) {
        this.tiempo.set(avance);
    }

    public String getTiempo() {
        return tiempo.get();
    }
    
    public StringProperty tiempoProperty(){
        return tiempo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getAvance() {
        return avance;
    }
    
    public void setStopWatch(Stopwatch stopWatch){
        this.stopWatch.set(stopWatch);
    }
    public Stopwatch getStopWatch(){
        return stopWatch.get();
    }
    
    public ObjectProperty<Stopwatch> stopWatchProperty(){
        return stopWatch;
    }
}

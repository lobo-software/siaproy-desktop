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
 */
package frontEnd.model;

import frontEnd.model.stopWatch.Stopwatch;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Lobo Software
 */
public class Actividades {
    private  SimpleObjectProperty<Typ> proyecto;
    private  SimpleObjectProperty<Typ> actividad;
    private Stopwatch stopWatch;
    private String tiempo;
    private String descripcion;
    private String avance;
    private String Fecha;

    public Actividades(){
    }
    
    //Se inicia con el modelado de datos
    public Actividades(Stopwatch stopWatch, Typ proyecto, Typ actividad, String tiempo, String descripcion, String avance) {
        this.stopWatch = stopWatch;
        this.proyecto = new SimpleObjectProperty(proyecto);
        this.actividad = new SimpleObjectProperty(actividad);
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.avance = avance;
    }

    public Typ getTypProyecto() {
        return proyecto.get();
    }

    public void setTypProyecto(Typ proyecto) {
        this.proyecto.set(proyecto);
    }

    public ObjectProperty<Typ> getProyectoProperty() {
        return proyecto;
    }

    public Typ getTypActividad() {
        return actividad.get();
    }

    public void setTypActividad(Typ actividad) {
        this.actividad.set(actividad);
    }

    public ObjectProperty<Typ> getActividadProperty() {
        return actividad;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPorcentajeAvance(String avance) {
        this.avance = avance;
    }

    public String getAvance() {
        return avance;

    }
    
    public Stopwatch getStopWatch(){
        return stopWatch;
    }
}

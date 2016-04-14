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
 */
package frontEnd.model;

import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Lobo Software
 */
public class Actividades {

    private String proyecto;
   
    private String actividad;
    private String tiempo;
    private  String descripcion;
    private String avance;
    private SimpleObjectProperty<Date> fecha;
    private BooleanProperty activo;
//    private TplList tplList;
    
//     public Actividades(){
//        this.proyecto = "";
//        this.actividad = "";
//        this.tiempo = "";
//        this.descripcion = new SimpleStringProperty();
//        this.avance="";
//      
//        
//    }




    public Actividades(String proyecto, String actividad, String tiempo, String descripcion, String avance, Date fecha, String activo) {
        this.proyecto = proyecto;
//        tplList = new TplList(proyecto);
        this.actividad = actividad;
        this.tiempo = tiempo;
        this.descripcion = descripcion;
        this.avance = avance;
        this.fecha = new SimpleObjectProperty(fecha);
        this.activo = new SimpleBooleanProperty(activo.equals("S") || activo.equals("A"));
    }
    
      public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }
      
     public String getProyecto() {
        return proyecto;
    }

//     public StringProperty getProyectoProperty(){
//         return proyecto;
//     }
    
     public void setActividad(String actividad) {
        this.actividad = actividad;
    }
    
     public String getActividad () {
        return actividad;
    }
     
     public String getTiempo() {
        return tiempo;
    }
    
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    
    public void setDescripcion(String descripcion){
    this.descripcion = descripcion;
}
    
     public String getDescripcion() {
        return descripcion;
    }
     
     public void setFecha(Date fecha){
         this.fecha.set(fecha);
     }
     
     public Date getFecha(){
         return fecha.get();
     }
     
     public ObjectProperty getFechaProperty(){
         return fecha;
     }
     
     public void setActivo(boolean activo){
         this.activo.set(activo);
     }
     
     public boolean getActivo(){
         return activo.get();
     }
     
     public BooleanProperty getActivoProperty(){
         return activo;
     }
    
//    
//  public StringProperty descripcionProperty() {
//            return this.descripcion;
//        }
// public StringProperty avance
    public void setAvance(String avance) {
        this.avance = avance;
    }
    
    public String getAvance(){
        return avance;
    }

    public void CronometroProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
   

}


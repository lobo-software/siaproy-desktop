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
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Lobo Software
 */
public class Actividades {

    private String proyecto;
   
    private String actividad;
//    private String botonInicio;
    private String tiempo;
    private final SimpleStringProperty descripcion;
//    private String avance;
    private final SimpleStringProperty avance;
    
//     public Actividades(){
//        this.proyecto = "";
//        this.actividad = "";
//        this.tiempo = "";
//        this.descripcion = new SimpleStringProperty();
//        this.avance="";
//      
//        
//    }




    public Actividades(String proyecto, String actividad, String tiempo, String descripcion, String avance) {
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.tiempo = tiempo;
        this.descripcion = new SimpleStringProperty(descripcion);
        this.avance = new SimpleStringProperty(avance);
    }
    
     public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }
    
     public String getActividad () {
        return actividad;
    }
     public void setActividad(String actividad) {
        this.actividad = actividad;
    }
     
     
     public String getTiempo() {
        return tiempo;
    }
    
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    
    public void setDescripcion(String descripcion){
    this.descripcion.set(descripcion);
}
    
     public String getDescripcion() {
        return descripcion.get();
    }
    
//    
  public StringProperty descripcionProperty() {
            return this.descripcion;
        }
  public StringProperty porcentajeAvanceProperty() {
            return this.descripcion;
        }
// public StringProperty avance
    public void setporcentajeAvance(String avance) {
        this.avance.set(avance);
    }

    public void CronometroProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
   

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ceciliac
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


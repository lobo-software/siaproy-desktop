/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ceciliac
 */
public class Actividades {

    public static void setMostrarDescripcion(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private String proyecto;
    @SuppressWarnings("FieldMayBeFinal")
    private String actividad;
    private String botonInicio;
    private String tiempo;
    @SuppressWarnings("FieldMayBeFinal")
    private SimpleStringProperty descripcion;
    private String avance;
    
     public Actividades(){
        this.proyecto = "";
        this.actividad = "";
        this.tiempo = "";
        this.descripcion = new SimpleStringProperty();
        this.avance="";
      
        
    }
public void setDescripcion(String descripcion){
    this.descripcion.set(descripcion);
}



    public Actividades(String proyecto, String actividad, String tiempo, String descripcion, String avance) {
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.tiempo = tiempo;
        this.descripcion.set(descripcion);
        this.avance = avance;
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
    
     public String getDescripcion() {
        return descripcion.get();
    }
    
//     public String getDescripcion() {
//        return descripcion;
//    }

 
    public void setAvance(String avance) {
        this.avance = avance;
    }

    public void CronometroProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
   

}


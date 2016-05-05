package frontEnd.model;

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
 *  Document     : SpProyectoModel.java
 * Created on    : 03 may 2016 11:15:24 AM
 * Author        : CCL
 * Modifications : 
 */

/**
 *
 * @author Lobo Software
 */
    public class SpProyectoModel {
    private String Id_proyecto;
    private String Descripcion;
     private String Clave_Referencia;
//     private String FechaInicio;

     public SpProyectoModel() {
          this.Id_proyecto = "";
        this.Descripcion = "";
        this.Clave_Referencia = "";
    }
     
    public SpProyectoModel(String Id_proyecto, String Descripcion, String Clave_Referencia) {
        this.Id_proyecto = Id_proyecto;
        this.Descripcion = Descripcion;
        this.Clave_Referencia = "";
        
    }

     
   
    public String getId_proyecto() {
        return Id_proyecto;
    }

    public void setId_proyecto(String Id_proyecto) {
        this.Id_proyecto = Id_proyecto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getClave_Referencia() {
        return Clave_Referencia;
    }

    public void setClave_Referencia(String Clave_Referencia) {
        this.Clave_Referencia = Clave_Referencia;
    }
//    public String getFechaInicio(){
//        return FechaInicio;
//    }
//    public void setClave_Referencia(String FechaInicio ) {
//    this.FechaInicio=FechaInicio;
//    }
}

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
 * Created on    : 03 may 2016 01:18:06 PM
 * Author        : CCL
 * Modifications : 
 */
package frontEnd.model;

/**
 *
 * @author Lobo Software
 */
public class SpdActividadesModel {
    private String Id_Actividad;
    private String Descripcion;
     private String Clave_Actividad;

    public SpdActividadesModel() {
         this.Id_Actividad = "";
        this.Descripcion = "";
        this.Clave_Actividad = "";
    }

    public SpdActividadesModel(String Id_Actividad, String Descripcion, String Clave_Actividad) {
        this.Id_Actividad = Id_Actividad;
        this.Descripcion = Descripcion;
        this.Clave_Actividad = Clave_Actividad;
    }

    public String getId_Actividad() {
        return Id_Actividad;
    }

    public void setId_Actividad(String Id_Actividad) {
        this.Id_Actividad = Id_Actividad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String Clave_Actividad() {
        return Clave_Actividad;
    }

    public void setClave_Actividad(String Clave_Actividad) {
        this.Clave_Actividad = Clave_Actividad;
    }
     

}

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
 *  Document     : SpProyectoService.java
 * Created on    : 03 may 2016 12:04:15 PM
 * Author        : CCL
 * Modifications : 05/May/2016 09:57 CCL (LOBO_000076): Se crea la cascada para mandar cargar los proyectos y atividades.

 */
package backEnd.mx.com.lobos.spproyecto.service;

import backEnd.mx.com.lobos.spproyecto.dao.SpProyectoDao;
import backEnd.mx.com.lobos.util.Conexion;
import frontEnd.util.GeneraCuadroMensaje;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//05/May/2016 10:35 CCl(LOBO_0000076):Se les da funcionalidad a los metodos para efectuar la cascada en Consulta Proyecto y Actividades de los combox.
//05/May/2016 19:09 CCl(LOBO_0000076):Se les da funcionalidad a los metodos para efectuar la cascada para efectuar la funcion mesaje exitosoo fallido dependiedo la insersión de datos.


/**
 *
 * @author Lobo Software
 */
public class SpProyectoService {
    public ObservableList<String> consulta(HashMap<String, Object> parametrosHsm) throws Exception{
        String cascada;
        ObservableList<String> lista = null;
        try{
            cascada = parametrosHsm.containsKey("cascada") ? (String)parametrosHsm.get("cascada") : "";
            if(cascada.equals("consultaProyectos")){
                lista = this.consultaProyecto(parametrosHsm);
            }else if(cascada.equals("consultaActividades")){
                lista = this.consultaActividades(parametrosHsm);
            }else if(cascada.equals("consultaActividadesAll")){
                lista = this.consultaActividades(parametrosHsm);
            }
        }catch(Exception e){
            GeneraCuadroMensaje.error(e.toString());
        }
        return lista;
    }
    
     public ObservableList<String> consultaProyecto(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<String> lista = null;
//        SpProyectoModel proyecto;
//        java.sql.Date fecha = java.sql.Date.valueOf((LocalDate) parametrosHsm.get("fecha"));
//        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.getConnectionSial();
            st = con.prepareCall(SpProyectoDao.consultaProyecto());
            st.setString(1,parametrosHsm.get("cveColaborador").toString());
       
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
//                proyecto = new SpProyectoModel();
//                proyecto.setId_proyecto(rs.getString("ID_PROYECTO"));
//                proyecto.setClave_Referencia(rs.getString("CLAVE_REFERENCIA"));
//                proyecto.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(rs.getString("ID_PROYECTO").concat(" - ").concat(rs.getString("CLAVE_REFERENCIA")).concat(" - ").concat(rs.getString("DESCRIPCION")));
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpProyectoService. \nMÉTODO: consultaProyecto");
//            if(mascara.isShowing()){
//                mascara.close();
//            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaProyecto");
            }
        }
        return lista;
    }
     
     
     public ObservableList<String> consultaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<String> lista = null;
//        SpProyectoModel proyecto;
//        java.sql.Date fecha = java.sql.Date.valueOf((LocalDate) parametrosHsm.get("fecha"));
//        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.getConnectionSial();
            st = con.prepareCall(SpProyectoDao.consultaActividades(parametrosHsm));
            st.setString(1,parametrosHsm.get("cveColaborador").toString());
            if (parametrosHsm.containsKey("allActividades") && ! (Boolean)parametrosHsm.get("allActividades")) {
                 st.setBigDecimal(2,new BigDecimal(Long.parseLong(parametrosHsm.get("idProyecto").toString())));
            }
       
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
//                proyecto = new SpProyectoModel();
//                proyecto.setId_proyecto(rs.getString("ID_PROYECTO"));
//                proyecto.setClave_Referencia(rs.getString("CLAVE_REFERENCIA"));
//                proyecto.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add(rs.getString("ID_PROY_COL_PLAN_ACT").concat(" - ").concat(rs.getString("CVE_ACTIVIDAD")).concat(" - ").concat(rs.getString("DESCRIPCION")));
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpProyectoService. \nMÉTODO: consultaActividades");
//            if(mascara.isShowing()){
//                mascara.close();
//            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividades");
            }
        }
        return lista;
    }
     
     public ObservableList<String> insertaActividadesSiaproyWeb(HashMap<String, Object> parametrosHsm){
       Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<String> lista = null;
//        SpProyectoModel proyecto;
//        java.sql.Date fecha = java.sql.Date.valueOf((LocalDate) parametrosHsm.get("fecha"));
//        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.getConnectionSial();
            st = con.prepareCall(SpProyectoDao.insertaActividadesFromSiaproyDesktop(parametrosHsm));
       
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
//                proyecto = new SpProyectoModel();
//                proyecto.setId_proyecto(rs.getString("ID_PROYECTO"));
//                proyecto.setClave_Referencia(rs.getString("CLAVE_REFERENCIA"));
//                proyecto.setDescripcion(rs.getString("DESCRIPCION"));
                lista.add("mensaje: insercion exitosa");
        } catch (Exception e) {
            lista.add("mensaje: insercion fallida");
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpProyectoService. \nMÉTODO: insertaActividadesSiaproyWeb");
//            if(mascara.isShowing()){
//                mascara.close();
//            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: insertaActividadesSiaproyWeb");
            }
        }
        return lista;
     }
}

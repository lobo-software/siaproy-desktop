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
 *  Document     : SpActividadesService.java
 * Created on    : 03 may 2016 05:05:53 PM
 * Author        : CCL
 * Modifications : 
 */
package backEnd.mx.com.lobos.spactividades.service;

import backEnd.mx.com.lobos.spactividades.Dao.SpActividadesDao;
import backEnd.mx.com.lobos.spproyecto.dao.SpProyectoDao;
import backEnd.mx.com.lobos.util.Conexion;
import frontEnd.model.Actividades;
import frontEnd.model.SpProyectoModel;
import frontEnd.model.SpdActividadesModel;
import frontEnd.util.GeneraCuadroMensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Lobo Software
 */
public class SpActividadesService {
         public ObservableList<String> consultaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<String> lista = null;
//        SpdActividadesModel actividades;
//        java.sql.Date fecha = java.sql.Date.valueOf((LocalDate) parametrosHsm.get("fecha"));
//        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.getConnectionSial();
            st = con.prepareCall(SpActividadesDao.consultaActividades());
            st.setString(1,parametrosHsm.get("cveColavorador").toString());
       
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
//                actividades = new SpdActividadesModel();
//                actividades.setId_Actividad(rs.getString("ID_PLAN_ACTIVIDAD"));
//                actividades.setClave_Actividad("CVE_ACTIVIDAD");
//                actividades.setDescripcion("DESCRIPCION");
                lista.add(rs.getString("CVE_ACTIVIDAD").concat(" - ").concat(rs.getString("DESCRIPCION")));
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpActividadesService. \nMÉTODO: consultaActividades");
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

}

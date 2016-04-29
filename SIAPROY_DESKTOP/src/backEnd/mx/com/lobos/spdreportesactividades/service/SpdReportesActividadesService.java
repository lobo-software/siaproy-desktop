/*
 *  _______________________________________________________________________
 * |                                         COPYRIGHT (C) BY                                                          |
 * |                             DERECHOS RESERVADOS (C) POR                                             |
 * |                                 LOBO SOFTWARE S.A. DE  C.V.                                               |
 * |                                                                                                                                  |
 * |Ninguna parte de esta obra, parcial o total puede                                                          | 
 * |ser reproducida o transmitida, mediante ningun sistema                                                 |
 * |o metodo electronico o mecanico (incluyendo el                                                             |   
 * |fotocopiado, la grabacion, o cualquier sistema de                                                          |  
 * |recuperacion y almacenamiento de informacion),                                                           |  
 * |SIN LA AUTORIZACION POR ESCRITO DEL AUTOR.                                                 |  
 * |                                                                                                                                  |  
 * |Derechos reservados                                                                                                   |  
 * |(C) 2012, LOBO SOFTWARE, S.A. DE C.V. (*)                                                            |  
 * |                                                                                                                                  |  
 * |Esta obra forma parte del SIAL-CH (C) "CAPITAL HUMANO"                                         |  
 * |                                                                                                                                  |  
 * |(*) Marca registrada por                                                                                               |   
 * |LOBO SOFTWARE, S.A. DE C.V.                                                                                |  
 * |_______________________________________________________________________|  
 *  Document     : SpdReportesActividadesService.java
 * Created on    : 26 Apr 2016 11:34:33 AM
 * Author           : SVA
 * Modifications : 
 */
package backEnd.mx.com.lobos.spdreportesactividades.service;

import backEnd.mx.com.lobos.spdreportesactividades.dao.SpdReportesActividadesDao;
import backEnd.mx.com.lobos.util.Conexion;
import frontEnd.model.SpdReportesActividadesModel;
import frontEnd.model.stopWatch.Stopwatch;
import frontEnd.util.GeneraCuadroMensaje;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Lobo Software
 */
public class SpdReportesActividadesService {

    public ObservableList<SpdReportesActividadesModel> consulta(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<SpdReportesActividadesModel> lista = null;
        SpdReportesActividadesModel actividades;
        java.sql.Date fecha = java.sql.Date.valueOf((LocalDate) parametrosHsm.get("fecha"));
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.creaConexionLocalBD();
            st = con.prepareCall(SpdReportesActividadesDao.consultaActividades());
            st.setDate(1, fecha);
            st.setString(2, "N");
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
                actividades = new SpdReportesActividadesModel();
                actividades.setStopWatch(new Stopwatch((TextField) parametrosHsm.get("txtTiempoTotal"), (TextField) parametrosHsm.get("txtTiempoInicio"), (TextField) parametrosHsm.get("txtTiempoFinal")));
                actividades.setIdReporteActividad(rs.getString("ID_REPORTE_ACTIVIDAD"));
                actividades.setIdProyColPlanAct(rs.getString("ID_PROY_COL_PLAN_ACT"));
                actividades.setIdReporteColaborador(rs.getString("ID_REPORTE_COLABORADOR"));
                actividades.setFecha(rs.getString("FECHA"));
                actividades.setDescripcion(rs.getString("DESCRIPCION"));
                actividades.setDuracion(rs.getString("DURACION"));
                actividades.setHoraInicio(rs.getString("HORA_INICIO"));
                actividades.setHoraFin(rs.getString("HORA_FIN"));
                actividades.setAvance(rs.getString("AVANCE"));
                actividades.setUsuario(rs.getString("USUARIO"));
                actividades.setFechaActualizacion(rs.getTimestamp("FECHA_ACTUALIZACION"));
                lista.add(actividades);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: consulta");
            if(mascara.isShowing()){
                mascara.close();
            }
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
                GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: consulta");
            }
        }
        return lista;
    }

    public void insertaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        ObservableList<SpdReportesActividadesModel> lista = null;
        SpdReportesActividadesModel actividades;
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            lista = (ObservableList<SpdReportesActividadesModel>) parametrosHsm.get("listaCreate");
            for (int a = 0; a < lista.size(); a++) {
                actividades = new SpdReportesActividadesModel();
                actividades.setIdProyColPlanAct(lista.get(a).getidProyColPlanAct());
                actividades.setIdReporteColaborador(lista.get(a).getIdReporteColaborador());
                actividades.setFecha(lista.get(a).getFecha());
                actividades.setDescripcion(lista.get(a).getDescripcion());
                actividades.setDuracion(lista.get(a).getDuracion());
                actividades.setHoraInicio(lista.get(a).getHoraInicio());
                actividades.setHoraFin(lista.get(a).getHoraFin());
                actividades.setAvance(lista.get(a).getAvance());
                this.inserta(actividades, mascara);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: insertaActividades");
            if(mascara.isShowing()){
                mascara.close();
            }
        }
    }

    public void inserta(SpdReportesActividadesModel actividades, Stage mascara) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.creaConexionLocalBD();
            ps = con.prepareStatement(SpdReportesActividadesDao.insertaActividades());
            ps.setBigDecimal(1, actividades.getidProyColPlanAct() != null ? BigDecimal.valueOf(Long.parseLong(actividades.getidProyColPlanAct())) : null);
            ps.setBigDecimal(2, actividades.getIdReporteColaborador() != null ? BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteColaborador())) : null);
            ps.setDate(3, java.sql.Date.valueOf((LocalDate.parse(actividades.getFecha()))));
            ps.setString(4, actividades.getDescripcion());
            ps.setDouble(5, Double.parseDouble(convierteHoraADecimal(actividades.getDuracion())));
            ps.setDouble(6, Double.parseDouble(convierteHoraADecimal(actividades.getHoraInicio())));
            ps.setDouble(7, Double.parseDouble(convierteHoraADecimal(actividades.getHoraFin())));
            ps.setDouble(8, Double.parseDouble(actividades.getAvance()));
            ps.setString(9, "N");
            ps.setString(10, "SIRH");
            ps.executeUpdate();
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: inserta");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: inserta");
                if(mascara.isShowing()){
                    mascara.close();
                }
            }
        }
    }

    public void actualizaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        ObservableList<SpdReportesActividadesModel> lista = null;
        SpdReportesActividadesModel actividades;
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            lista = (ObservableList<SpdReportesActividadesModel>) parametrosHsm.get("listaUpdate");
            for (int a = 0; a < lista.size(); a++) {
                actividades = new SpdReportesActividadesModel();
                actividades.setIdReporteActividad(lista.get(a).getIdReporteActividad());
                actividades.setIdProyColPlanAct(lista.get(a).getidProyColPlanAct());
                actividades.setIdReporteColaborador(lista.get(a).getIdReporteColaborador());
                actividades.setFecha(lista.get(a).getFecha());
                actividades.setDescripcion(lista.get(a).getDescripcion());
                actividades.setDuracion(lista.get(a).getDuracion());
                actividades.setHoraInicio(lista.get(a).getHoraInicio());
                actividades.setHoraFin(lista.get(a).getHoraFin());
                actividades.setAvance(lista.get(a).getAvance());
                this.actualiza(actividades, mascara);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaActividades");
            if(mascara.isShowing()){
                mascara.close();
            }
        }
    }

    public void actualiza(SpdReportesActividadesModel actividades, Stage mascara) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.creaConexionLocalBD();
            ps = con.prepareStatement(SpdReportesActividadesDao.actualizaActividades());
            ps.setBigDecimal(1, actividades.getidProyColPlanAct() != null ? BigDecimal.valueOf(Long.parseLong(actividades.getidProyColPlanAct())) : null);
            ps.setBigDecimal(2, actividades.getIdReporteColaborador() != null ? BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteColaborador())) : null);
            ps.setDate(3, java.sql.Date.valueOf((LocalDate.parse(actividades.getFecha()))));
            ps.setString(4, actividades.getDescripcion());
            ps.setDouble(5, Double.parseDouble(convierteHoraADecimal(actividades.getDuracion())));
            ps.setDouble(6, Double.parseDouble(convierteHoraADecimal(actividades.getHoraInicio())));
            ps.setDouble(7, Double.parseDouble(convierteHoraADecimal(actividades.getHoraFin())));
            ps.setDouble(8, Double.parseDouble(actividades.getAvance()));
            ps.setString(9, "N");
            ps.setString(10, "SIRH");
            ps.setBigDecimal(11, BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteActividad())));
            ps.executeUpdate();
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualiza");
            if(mascara.isShowing()){
                mascara.close();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualiza");
            }
        }
    }

    public void eliminaActividades(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ObservableList<SpdReportesActividadesModel> lista = null;
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            lista = (ObservableList<SpdReportesActividadesModel>) parametrosHsm.get("listaDelete");
            con = Conexion.creaConexionLocalBD();
            ps = con.prepareCall(SpdReportesActividadesDao.eliminaActividades());
            for (int a = 0; a < lista.size(); a++) {
                ps.setString(1, lista.get(a).getIdReporteActividad());
                ps.executeUpdate();
            }
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: eliminaActividades");
            if(mascara.isShowing()){
                mascara.close();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                GeneraCuadroMensaje.error(e.toString() + "\nCLASE: SpdReportesActividadesService. \nMÉTODO: eliminaActividades");
                if(mascara.isShowing()){
                    mascara.close();
                }
            }
        }
    }

    public String convierteHoraADecimal(String hora) {
        String[] tiempo = hora.split(":");
        boolean esEntero = true;
        if (tiempo[1].startsWith("0")) {
            tiempo[1] = tiempo[1].replaceFirst(tiempo[1], "0." + tiempo[1]);
        }
        double min = Double.parseDouble(tiempo[1]) * 100 / 60;
        if (String.valueOf(min).split("[.]")[0].startsWith("0")) {
            esEntero = false;
        }
        if (esEntero) {
            long m = Math.round(min);
            hora = tiempo[0] + "." + String.valueOf(m);
        } else{
            min = Math.ceil(min * 1000 ) / 1000;
            hora = tiempo[0] + "." + String.valueOf(min).split("[.]")[1];
        }

        return hora;
    }
}

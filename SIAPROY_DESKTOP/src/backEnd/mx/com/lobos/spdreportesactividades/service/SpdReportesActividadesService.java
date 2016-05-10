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
 * Modifications : 06/May/2016 09:35 SVA (LOBO_000076): Se modifican los métodos "consultaActividadesDiarias" y  "consultaActividadesSinSincronizar" por cambios en la BDD local.
10/May/2016 13:07 SVA (LOBO_000076): Se crea el método "consultaActividadesSinProyecto" y se modifican los mensajes de error en las exepciones.
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
        String cascada;
        ObservableList<SpdReportesActividadesModel> registros = null;
        try {
            cascada = parametrosHsm.containsKey("cascada") ? (String) parametrosHsm.get("cascada") : "";
            if (cascada.equals("")) {
                registros = this.consultaActividadesDiarias(parametrosHsm);
            } else if (cascada.equals("consultaActividadesSinSincronizar")) {
                registros = this.consultaActividadesSinSincronizar(parametrosHsm);
            } else if (cascada.equals("consultaActividadesSinProyecto")) {
                registros = this.consultaActividadesSinProyecto(parametrosHsm);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consulta");
        }
        return registros;
    }

    public void actualiza(HashMap<String, Object> parametrosHsm) throws Exception {
        String cascada;
        try {
            cascada = parametrosHsm.containsKey("cascada") ? (String) parametrosHsm.get("cascada") : "";
            if (cascada.equals("")) {
                this.actualizaActividadesBDLocal(parametrosHsm);
            } else if (cascada.equals("actualizaSincronizadoSiaproy")) {
                this.actualizaSincronizadoSiaproy(parametrosHsm);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualiza");
        }
    }

    public ObservableList<SpdReportesActividadesModel> consultaActividadesDiarias(HashMap<String, Object> parametrosHsm) throws Exception {
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
                actividades.setProyecto(rs.getString("PROYECTO"));
                actividades.setActividad(rs.getString("ACTIVIDAD"));
                actividades.setFecha(rs.getString("FECHA"));
                actividades.setDescripcion(rs.getString("DESCRIPCION"));
                actividades.setDuracion(rs.getString("DURACION"));
                actividades.setHoraInicio(rs.getString("HORA_INICIO"));
                actividades.setHoraFin(rs.getString("HORA_FIN"));
                actividades.setAvance(rs.getString("AVANCE").split("[.]")[0] + "." + rs.getString("AVANCE").split("[.]")[1].substring(0, 1));
                actividades.setUsuario(rs.getString("USUARIO"));
                actividades.setFechaActualizacion(rs.getTimestamp("FECHA_ACTUALIZACION"));
                lista.add(actividades);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesDiarias");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesDiarias");
            }
        }
        return lista;
    }

    public ObservableList<SpdReportesActividadesModel> consultaActividadesSinSincronizar(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<SpdReportesActividadesModel> lista = null;
        SpdReportesActividadesModel actividades;
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.creaConexionLocalBD();
            st = con.prepareCall(SpdReportesActividadesDao.consultaActividadesSinSincronizar());
            st.setString(1, "N");
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
                actividades = new SpdReportesActividadesModel();
                actividades.setStopWatch(new Stopwatch((TextField) parametrosHsm.get("txtTiempoTotal"), (TextField) parametrosHsm.get("txtTiempoInicio"), (TextField) parametrosHsm.get("txtTiempoFinal")));
                actividades.setIdReporteActividad(rs.getString("ID_REPORTE_ACTIVIDAD"));
                actividades.setIdProyColPlanAct(rs.getString("ID_PROY_COL_PLAN_ACT"));
                actividades.setIdReporteColaborador(rs.getString("ID_REPORTE_COLABORADOR"));
                actividades.setProyecto(rs.getString("PROYECTO"));
                actividades.setActividad(rs.getString("ACTIVIDAD"));
                actividades.setFecha(rs.getString("FECHA"));
                actividades.setDescripcion(rs.getString("DESCRIPCION"));
                actividades.setDuracion(rs.getString("DURACION"));
                actividades.setHoraInicio(rs.getString("HORA_INICIO"));
                actividades.setHoraFin(rs.getString("HORA_FIN"));
                actividades.setAvance(rs.getString("AVANCE").split("[.]")[0] + "." + rs.getString("AVANCE").split("[.]")[1].substring(0, 1));
                actividades.setUsuario(rs.getString("USUARIO"));
                actividades.setFechaActualizacion(rs.getTimestamp("FECHA_ACTUALIZACION"));
                lista.add(actividades);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesSinSincronizar");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesSinSincronizar");
            }
        }
        return lista;
    }

    public ObservableList<SpdReportesActividadesModel> consultaActividadesSinProyecto(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ObservableList<SpdReportesActividadesModel> lista = null;
        SpdReportesActividadesModel actividades;
        Stage mascara = (Stage) parametrosHsm.get("mascara");
        try {
            con = Conexion.creaConexionLocalBD();
            st = con.prepareCall(SpdReportesActividadesDao.consultaActividadesSinProyecto());
            st.setString(1, "N");
            rs = st.executeQuery();
            lista = FXCollections.observableArrayList();
            while (rs.next()) {
                actividades = new SpdReportesActividadesModel();
                actividades.setFecha(rs.getString("FECHA"));
                lista.add(actividades);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesSinProyecto");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: consultaActividadesSinProyecto");
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
                actividades.setProyecto(lista.get(a).getProyecto());
                actividades.setActividad(lista.get(a).getActividad());
                actividades.setFecha(lista.get(a).getFecha());
                actividades.setDescripcion(lista.get(a).getDescripcion());
                actividades.setDuracion(lista.get(a).getDuracion());
                actividades.setHoraInicio(lista.get(a).getHoraInicio());
                actividades.setHoraFin(lista.get(a).getHoraFin());
                actividades.setAvance(lista.get(a).getAvance());
                this.inserta(actividades, mascara);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: insertaActividades");
            if (mascara.isShowing()) {
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
            ps.setBigDecimal(1, actividades.getidProyColPlanAct() != null && !actividades.getidProyColPlanAct().equals("") ? BigDecimal.valueOf(Long.parseLong(actividades.getidProyColPlanAct().trim())) : null);
            ps.setBigDecimal(2, actividades.getIdReporteColaborador() != null && !actividades.getIdReporteColaborador().equals("") ? BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteColaborador().trim())) : null);
            ps.setString(3, actividades.getProyecto() != null && !actividades.getProyecto().equals("") ? actividades.getProyecto().trim() : null);
            ps.setString(4, actividades.getActividad() != null && !actividades.getActividad().equals("") ? actividades.getActividad().trim() : null);
            ps.setDate(5, java.sql.Date.valueOf((LocalDate.parse(actividades.getFecha().trim()))));
            ps.setString(6, actividades.getDescripcion().trim());
            ps.setDouble(7, Double.parseDouble(convierteHoraADecimal(actividades.getDuracion().trim())));
            ps.setDouble(8, actividades.getHoraInicio() != null && !actividades.getHoraInicio().equals("") ? Double.parseDouble(convierteHoraADecimal(actividades.getHoraInicio().trim())) : Double.parseDouble(convierteHoraADecimal("00:00:00")));
            ps.setDouble(9, actividades.getHoraFin() != null && !actividades.getHoraFin().equals("") ? Double.parseDouble(convierteHoraADecimal(actividades.getHoraFin().trim())) : Double.parseDouble(convierteHoraADecimal("00:00:00")));
            ps.setDouble(10, Double.parseDouble(actividades.getAvance().trim()));
            ps.setString(11, "N");
            ps.setString(12, "SIRH");
            ps.executeUpdate();
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: inserta");
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: inserta");
                if (mascara.isShowing()) {
                    mascara.close();
                }
            }
        }
    }

    public void actualizaActividadesBDLocal(HashMap<String, Object> parametrosHsm) throws Exception {
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
                actividades.setProyecto(lista.get(a).getProyecto());
                actividades.setActividad(lista.get(a).getActividad());
                actividades.setFecha(lista.get(a).getFecha());
                actividades.setDescripcion(lista.get(a).getDescripcion());
                actividades.setDuracion(lista.get(a).getDuracion());
                actividades.setHoraInicio(lista.get(a).getHoraInicio());
                actividades.setHoraFin(lista.get(a).getHoraFin());
                actividades.setAvance(lista.get(a).getAvance());
                actividades.setProyecto(lista.get(a).getProyecto());
                this.actualizaActividades(actividades, mascara);
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaActividadesBDLocal");
            if (mascara.isShowing()) {
                mascara.close();
            }
        }
    }

    public void actualizaActividades(SpdReportesActividadesModel actividades, Stage mascara) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.creaConexionLocalBD();
            ps = con.prepareStatement(SpdReportesActividadesDao.actualizaActividades());
            ps.setBigDecimal(1, actividades.getidProyColPlanAct() != null && !actividades.getidProyColPlanAct().equals("") ? BigDecimal.valueOf(Long.parseLong(actividades.getidProyColPlanAct().trim())) : null);
            ps.setBigDecimal(2, actividades.getIdReporteColaborador() != null && !actividades.getIdReporteColaborador().equals("") ? BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteColaborador().trim())) : null);
            ps.setString(3, actividades.getProyecto() != null && !actividades.getProyecto().equals("") ? actividades.getProyecto().trim() : null);
            ps.setString(4, actividades.getActividad() != null && !actividades.getActividad().equals("") ? actividades.getActividad().trim() : null);
            ps.setDate(5, java.sql.Date.valueOf((LocalDate.parse(actividades.getFecha().trim()))));
            ps.setString(6, actividades.getDescripcion().trim());
            ps.setDouble(7, Double.parseDouble(convierteHoraADecimal(actividades.getDuracion().trim())));
            ps.setDouble(8, actividades.getHoraInicio() != null && !actividades.getHoraInicio().equals("") ? Double.parseDouble(convierteHoraADecimal(actividades.getHoraInicio().trim())) : Double.parseDouble(convierteHoraADecimal("00:00:00")));
            ps.setDouble(9, actividades.getHoraFin() != null && !actividades.getHoraFin().equals("") ? Double.parseDouble(convierteHoraADecimal(actividades.getHoraFin().trim())) : Double.parseDouble(convierteHoraADecimal("00:00:00")));
            ps.setDouble(10, Double.parseDouble(actividades.getAvance().trim()));
            ps.setString(11, "N");
            ps.setString(12, "SIRH");
            ps.setBigDecimal(13, BigDecimal.valueOf(Long.parseLong(actividades.getIdReporteActividad().trim())));
            ps.executeUpdate();
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaActividades");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaActividades");
            }
        }
    }

    public void actualizaSincronizadoSiaproy(HashMap<String, Object> parametrosHsm) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        Stage mascara = null;
        try {
            mascara = (Stage) parametrosHsm.get("mascara");
            con = Conexion.creaConexionLocalBD();
            ps = con.prepareStatement(SpdReportesActividadesDao.actualizaSincronizadoSiaproy());
            ps.executeUpdate();
        } catch (Exception e) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaSincronizadoSiaproy");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: actualizaSincronizadoSiaproy");
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
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: eliminaActividades");
            if (mascara.isShowing()) {
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
                GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + e.toString() + "\n\nCLASE: SpdReportesActividadesService. \nMÉTODO: eliminaActividades");
                if (mascara.isShowing()) {
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
        } else {
            min = Math.ceil(min * 1000) / 1000;
            hora = tiempo[0] + "." + String.valueOf(min).split("[.]")[1];
        }
        return hora;
    }
}
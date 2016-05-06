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
 *  Document     : SpdReportesActividadesDao.java
 * Created on    : 26 Apr 2016 11:34:47 AM
 * Author           : SVA
 * Modifications : 05/May/2016 06:53 CCL (LOBO_000076): Se añade la funcionalidad de Insertar, Consultar y Sincronizar a Siaproy web, y función de Sincronizado a BDD Siaproy.

 */
package backEnd.mx.com.lobos.spdreportesactividades.dao;

/**
 *
 * @author Lobo Softwar
 */
public class SpdReportesActividadesDao {

    public static String consultaActividades() {
        StringBuilder consultaActividades = new StringBuilder();
        consultaActividades.append("SELECT ID_REPORTE_ACTIVIDAD, ");
        consultaActividades.append("  ID_PROY_COL_PLAN_ACT, ");
        consultaActividades.append("  ID_REPORTE_COLABORADOR, ");
        consultaActividades.append("  PROYECTO, ");
        consultaActividades.append("  ACTIVIDAD, ");
        consultaActividades.append("  FECHA, ");
        consultaActividades.append("  DESCRIPCION, ");
        consultaActividades.append("  DURACION, ");
        consultaActividades.append("  HORA_INICIO, ");
        consultaActividades.append("  HORA_FIN, ");
        consultaActividades.append("  AVANCE, ");
        consultaActividades.append("  PROYECTO, ");
        consultaActividades.append("  USUARIO, ");
        consultaActividades.append("  FECHA_ACTUALIZACION ");
        consultaActividades.append("FROM SPD_REPORTES_ACTIVIDADES ");
        consultaActividades.append("WHERE FECHA = ? ");
        consultaActividades.append("AND SINCRONIZADO_SIAPROY = ? ");
        consultaActividades.append("ORDER BY ID_REPORTE_ACTIVIDAD ASC");
        return consultaActividades.toString();
    }
    
    public static String consultaActividadesSinSincronizar() {
        StringBuilder consultaActividadesSinSincronizar = new StringBuilder();
        consultaActividadesSinSincronizar.append("SELECT ID_REPORTE_ACTIVIDAD, ");
        consultaActividadesSinSincronizar.append("  ID_PROY_COL_PLAN_ACT, ");
        consultaActividadesSinSincronizar.append("  ID_REPORTE_COLABORADOR, ");
        consultaActividadesSinSincronizar.append("  PROYECTO, ");
        consultaActividadesSinSincronizar.append("  ACTIVIDAD, ");
        consultaActividadesSinSincronizar.append("  FECHA, ");
        consultaActividadesSinSincronizar.append("  DESCRIPCION, ");
        consultaActividadesSinSincronizar.append("  DURACION, ");
        consultaActividadesSinSincronizar.append("  HORA_INICIO, ");
        consultaActividadesSinSincronizar.append("  HORA_FIN, ");
        consultaActividadesSinSincronizar.append("  AVANCE, ");
        consultaActividadesSinSincronizar.append("  PROYECTO, ");
        consultaActividadesSinSincronizar.append("  USUARIO, ");
        consultaActividadesSinSincronizar.append("  FECHA_ACTUALIZACION ");
        consultaActividadesSinSincronizar.append("FROM SPD_REPORTES_ACTIVIDADES ");
        consultaActividadesSinSincronizar.append("WHERE SINCRONIZADO_SIAPROY = ? ");
        return consultaActividadesSinSincronizar.toString();
    }

    public static String insertaActividades() {
        StringBuilder insertaActividades = new StringBuilder();
        insertaActividades.append("INSERT ");
        insertaActividades.append("INTO SPD_REPORTES_ACTIVIDADES VALUES ");
        insertaActividades.append("  ( ");
        insertaActividades.append("    (SELECT COALESCE(MAX (ID_REPORTE_ACTIVIDAD)+1, 1) FROM SPD_REPORTES_ACTIVIDADES ");
        insertaActividades.append("    ) ");
        insertaActividades.append("    , ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    ?, ");
        insertaActividades.append("    CURRENT_TIMESTAMP ");
        insertaActividades.append("  )");
        return insertaActividades.toString();
    }

    public static String actualizaActividades() {
        StringBuilder actualizaActividades = new StringBuilder();
        actualizaActividades.append("UPDATE SPD_REPORTES_ACTIVIDADES ");
        actualizaActividades.append("SET ID_PROY_COL_PLAN_ACT   = ?, ");
        actualizaActividades.append("  ID_REPORTE_COLABORADOR   = ?, ");
        actualizaActividades.append("  PROYECTO                 = ?, ");
        actualizaActividades.append("  ACTIVIDAD                = ?, ");
        actualizaActividades.append("  FECHA                    = ?, ");
        actualizaActividades.append("  DESCRIPCION              = ?, ");
        actualizaActividades.append("  DURACION                 = ?, ");
        actualizaActividades.append("  HORA_INICIO              = ?, ");
        actualizaActividades.append("  HORA_FIN                 = ?, ");
        actualizaActividades.append("  AVANCE                   = ?, ");
        actualizaActividades.append("  SINCRONIZADO_SIAPROY     = ?, ");
        actualizaActividades.append("  USUARIO                  = ?, ");
        actualizaActividades.append("  FECHA_ACTUALIZACION      = CURRENT_TIMESTAMP ");
        actualizaActividades.append("WHERE ID_REPORTE_ACTIVIDAD = ?");
        return actualizaActividades.toString();
    }
    
    public static String actualizaSincronizadoSiaproy() {
        StringBuilder actualizaSincronizadoSiaproy = new StringBuilder();
        actualizaSincronizadoSiaproy.append("UPDATE SPD_REPORTES_ACTIVIDADES ");
        actualizaSincronizadoSiaproy.append("SET SINCRONIZADO_SIAPROY   = 'S' ");
        actualizaSincronizadoSiaproy.append("WHERE SINCRONIZADO_SIAPROY = 'N'");
        return actualizaSincronizadoSiaproy.toString();
    }

    public static String eliminaActividades() {
        StringBuilder eliminaActividades = new StringBuilder();
        eliminaActividades.append("DELETE FROM SPD_REPORTES_ACTIVIDADES WHERE ID_REPORTE_ACTIVIDAD = ?");
        return eliminaActividades.toString();
    }

}

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
 * Modifications : 
 */
package backEnd.mx.com.lobos.spdreportesactividades.dao;

/**
 *
 * @author Lobo Software
 */
public class SpdReportesActividadesDao {

    public static String consultaActividades() {
        StringBuilder consultaActividades = new StringBuilder();
        consultaActividades.append("SELECT ID_REPORTE_ACTIVIDAD, ");
        consultaActividades.append("  ID_PROY_COL_PLAN_ACT, ");
        consultaActividades.append("  ID_REPORTE_COLABORADOR, ");
        consultaActividades.append("  FECHA, ");
        consultaActividades.append("  DESCRIPCION, ");
        consultaActividades.append("  DURACION, ");
        consultaActividades.append("  HORA_INICIO, ");
        consultaActividades.append("  HORA_FIN, ");
        consultaActividades.append("  AVANCE, ");
        consultaActividades.append("  USUARIO, ");
        consultaActividades.append("  FECHA_ACTUALIZACION ");
        consultaActividades.append("FROM SPD_REPORTES_ACTIVIDADES ");
        consultaActividades.append("WHERE FECHA = ? ");
        consultaActividades.append("AND SINCRONIZADO_SIAPROY = ? ");
        consultaActividades.append("ORDER BY ID_REPORTE_ACTIVIDAD ASC");
        return consultaActividades.toString();
    }

    public static String insertaActividades() {
        StringBuilder insertaActividades = new StringBuilder();
        insertaActividades.append("INSERT ");
        insertaActividades.append("INTO SPD_REPORTES_ACTIVIDADES VALUES ");
        insertaActividades.append("  ( ");
        insertaActividades.append("    (SELECT NULLIF(MAX (ID_REPORTE_ACTIVIDAD)+1, 1) FROM SPD_REPORTES_ACTIVIDADES ");
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
        insertaActividades.append("    CURRENT_TIMESTAMP ");
        insertaActividades.append("  )");
        return insertaActividades.toString();
    }

    public static String actualizaActividades() {
        StringBuilder actualizaActividades = new StringBuilder();
        actualizaActividades.append("UPDATE SPD_REPORTES_ACTIVIDADES ");
        actualizaActividades.append("SET ID_PROY_COL_PLAN_ACT   = ?, ");
        actualizaActividades.append("  ID_REPORTE_COLABORADOR   = ?, ");
        actualizaActividades.append("  FECHA                    = ?, ");
        actualizaActividades.append("  DESCRIPCION              = ?, ");
        actualizaActividades.append("  DURACION                 = ?, ");
        actualizaActividades.append("  HORA_INICIO              = ?, ");
        actualizaActividades.append("  HORA_FIN                 = ?, ");
        actualizaActividades.append("  AVANCE                   = ?, ");
        actualizaActividades.append("  SINCRONIZADO_SIAPROY = ?, ");
        actualizaActividades.append("  USUARIO                  = ?, ");
        actualizaActividades.append("  FECHA_ACTUALIZACION      = CURRENT_TIMESTAMP ");
        actualizaActividades.append("WHERE ID_REPORTE_ACTIVIDAD = ?");
        return actualizaActividades.toString();
    }

    public static String eliminaActividades() {
        StringBuilder eliminaActividades = new StringBuilder();
        eliminaActividades.append("DELETE FROM SPD_REPORTES_ACTIVIDADES WHERE ID_REPORTE_ACTIVIDAD = ?");
        return eliminaActividades.toString();
    }

}

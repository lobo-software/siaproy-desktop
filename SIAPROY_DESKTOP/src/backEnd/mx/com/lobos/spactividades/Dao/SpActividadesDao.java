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
 *  Document     : SpProyectoDao.java
 * Created on    : 03 may 2016 01:30:30 PM
 * Author        : CCL
 * Modifications : 
 */
package backEnd.mx.com.lobos.spactividades.Dao;

/**
 *
 * @author Lobo Software
 */
public class SpActividadesDao {
     public static String consultaActividades() {
        StringBuilder consultaActividades = new StringBuilder();
        
       consultaActividades.append("SELECT SPA.CVE_ACTIVIDAD, ");
consultaActividades.append("  SPA.DESCRIPCION ");
consultaActividades.append("FROM SP_ACTIVIDADES SPA, ");
consultaActividades.append("  SP_PLANES_ACTIVIDADES SPPA, ");
consultaActividades.append("  SP_PROYECTOS_COL_PLANES_ACT SPPCPA, ");
consultaActividades.append("  SP_PROYECTOS_COLABORADORES SPPC, ");
consultaActividades.append("  SP_COLABORADORES SPC, ");
consultaActividades.append("  SP_PROYECTOS SPP, ");
consultaActividades.append("  SP_PLANES_ACTIVIDADES_PROY SPPAP ");
consultaActividades.append("WHERE SPA.ID_ACTIVIDAD             = SPPA.ID_ACTIVIDAD ");
consultaActividades.append("AND SPPA.ID_PLAN_ACTIVIDAD         = SPPCPA.ID_PLAN_ACTIVIDAD ");
consultaActividades.append("AND SPPCPA.ID_PROYECTO_COLABORADOR = SPPC.ID_PROYECTO_COLABORADOR ");
consultaActividades.append("AND SPPC.ID_COLABORADOR            = SPC.ID_COLABORADOR ");
consultaActividades.append("AND SPPC.ID_PROYECTO               = SPP.ID_PROYECTO ");
consultaActividades.append("AND SPP.ID_PROYECTO                = SPPAP.ID_PROYECTO ");
consultaActividades.append("AND SPPAP.ID_PLAN_ACTIVIDAD_PROY   = SPPA.ID_PLAN_ACTIVIDAD_PROY ");
consultaActividades.append("AND SPPAP.VERSION                  = ");
consultaActividades.append("  (SELECT MAX(SPPAP2.VERSION) ");
consultaActividades.append("  FROM SP_PLANES_ACTIVIDADES_PROY SPPAP2 ");
consultaActividades.append("  WHERE SPPAP.ID_PLAN_ACTIVIDAD_PROY = SPPAP2.ID_PLAN_ACTIVIDAD_PROY ");
consultaActividades.append("  ) ");
consultaActividades.append("AND SPC.CLAVE = ?");


        return consultaActividades.toString();
      
    }
}

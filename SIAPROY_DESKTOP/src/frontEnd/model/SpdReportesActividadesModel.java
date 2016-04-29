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
 *  Document     : SpdReportesActividadesModel.java
 * Created on    : 26 Apr 2016 12:25:01 PM
 * Author           : SVA
 * Modifications : 
 */
package frontEnd.model;

import frontEnd.model.stopWatch.Stopwatch;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Lobo Software
 */
public class SpdReportesActividadesModel {

    private final SimpleObjectProperty<Stopwatch> stopWatch;
    private SimpleStringProperty idReporteActividad;
    private SimpleStringProperty idProyColPlanAct;
    private SimpleStringProperty idReporteColaborador;
    private SimpleStringProperty fecha;
    private SimpleStringProperty descripcion;
    private final SimpleStringProperty duracion;
    private final SimpleStringProperty horaInicio;
    private final SimpleStringProperty horaFin;
    private SimpleStringProperty avance;
    private String usuario;
    private Timestamp fechaActualizacion;

    //Se inicia con el modelado de datos
    public SpdReportesActividadesModel() {
        this.stopWatch = new SimpleObjectProperty();
        this.idReporteActividad = new SimpleStringProperty();
        this.idProyColPlanAct = new SimpleStringProperty();
        this.idReporteColaborador = new SimpleStringProperty();
        this.fecha = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.duracion = new SimpleStringProperty("");
        this.horaInicio = new SimpleStringProperty("");
        this.horaFin = new SimpleStringProperty("");
        this.avance = new SimpleStringProperty("");
        this.usuario = "";
        this.fechaActualizacion = Timestamp.valueOf(LocalDateTime.now());

    }

    public SpdReportesActividadesModel(Stopwatch stopWatch, String idReporteActividad, String idProyColPlanAct, String idReporteColaborador, String fecha, String descripcion, String duracion, String horaInicio, String horaFin, String avance, String usuario, Timestamp fechaActualizacion) {
        this.stopWatch = new SimpleObjectProperty(stopWatch);
        this.idReporteActividad = new SimpleStringProperty(idReporteActividad);
        this.idProyColPlanAct = new SimpleStringProperty(idProyColPlanAct);
        this.idReporteColaborador = new SimpleStringProperty(idReporteColaborador);
        this.fecha = new SimpleStringProperty(fecha);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.duracion = new SimpleStringProperty(duracion);
        this.horaInicio = new SimpleStringProperty(horaInicio);
        this.horaFin = new SimpleStringProperty(horaFin);
        this.avance = new SimpleStringProperty(avance);
        this.usuario = usuario;
        this.fechaActualizacion = fechaActualizacion;

    }

    public void setStopWatch(Stopwatch stopWatch) {
        this.stopWatch.set(stopWatch);
    }

    public Stopwatch getStopWatch() {
        return stopWatch.get();
    }

    public ObjectProperty<Stopwatch> stopWatchProperty() {
        return stopWatch;
    }

    public void setIdReporteActividad(String idReporteActividad) {
        this.idReporteActividad.set(idReporteActividad);
    }

    public String getIdReporteActividad() {
        return idReporteActividad.get();
    }

    public StringProperty idReporteActividadProperty() {
        return idReporteActividad;
    }

    public void setIdProyColPlanAct(String idProyColPlanAct) {
        this.idProyColPlanAct.set(idProyColPlanAct);
    }

    public String getidProyColPlanAct() {
        return idProyColPlanAct.get();
    }

    public StringProperty idProyColPlanActProperty() {
        return idProyColPlanAct;
    }

    public void setIdReporteColaborador(String idReporteColaborador) {
        this.idReporteColaborador.set(idReporteColaborador);
    }

    public String getIdReporteColaborador() {
        return idReporteColaborador.get();
    }

    public StringProperty idReporteColaboradorProperty() {
        return idReporteColaborador;
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDuracion(String duracion) {
        this.duracion.set(duracion);
    }

    public String getDuracion() {
        return duracion.get();
    }

    public StringProperty duracionProperty() {
        return duracion;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio.set(horaInicio);
    }

    public String getHoraInicio() {
        return horaInicio.get();
    }

    public StringProperty horaInicioProperty() {
        return horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin.set(horaFin);
    }

    public String getHoraFin() {
        return horaFin.get();
    }

    public StringProperty horaFinProperty() {
        return horaFin;
    }

    public void setAvance(String avance) {
        this.avance.set(avance);
    }

    public String getAvance() {
        return avance.get();
    }

    public StringProperty avanceProperty() {
        return avance;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }
}

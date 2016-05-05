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
 *  Document     : SPPRYF12Controller.java
 * Created on    : 08 abr 2016 10:24:19 AM
 * Author        : CCL
 * Modifications : 08/Apr/2016 18:44 CCL (LOBO_000076): Se añaden cabeceras de licencia a los archivos. 
   11/Apr/2016 10:03 CCL (LOBO_000076): Se eliminó código inecesario dentro del  controller. 
   12/Apr/2016 10:03 CCL (LOBO_000076): Se eliminó código inecesario dentro del  controller y se implemnto codigo el codigopara la columna tiempo.
   14/Apr/2016 09:55 CCl(LOBO_000076: Se agraga funcionalidad al boton que contine el Stopwatch  dentro de este Controller se manda llamar la misma Clase Sw.
   14/Apr/2016 09:55 CCL(LOBO_000076):Se da formato a este dcomunto.
   14/Apr/2016 17:11 SVA (LOBO_000076): Se añade funcionalidad con clases genéricas para la edición del TableView / Se restructura el archivo
   15/Apr/2016 01:03 CCL (LOBO_000076): Se añade funcionalidad para cerrar la ventana principal con creando un alert al mismo.
   22/Apr/2016 01:17 CCL (LOBO_000076): Se siguen añadiendo funcionalidades a los componentes de la vista y se eliminó cógio inesesario.
   29/Abr/2016 17:07 SVA (LOBO_000076): Se restructura clase y se mejora funcionalidad.
   29/Abr/2016 01:17 CCL(LOBO_000076):Se añade un metodo para llevar acabo el conteo de tiempo total de actividades.
   30/Abr/2016 12:50 SVA (LOBO_000076): Se mejora método "setTiempoTotal".
   05/May/2016 09:57 CCL (LOBO_000076): Se crean  métodos para Insertar, Agrear Eliminar y Actilizar enla BDD Local,se añaden 3 clases en el paquete ultil de backEnd (Mongo,Conexion,Encripta)y se crea el webService.

 */
package frontEnd.controller;

import backEnd.mx.com.lobos.spdreportesactividades.store.ActualizaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.ConsultaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.EliminaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.InsertaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spactividades.store.ConsultaSpdActividadesStore;
import backEnd.mx.com.lobos.spproyecto.store.ConsultaSpdProyectosStore;
import backEnd.mx.com.lobos.util.SesionesMongo;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import frontEnd.model.SpProyectoModel;
import frontEnd.model.SpdActividadesModel;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import frontEnd.model.SpdReportesActividadesModel;
import frontEnd.model.stopWatch.Stopwatch;
import frontEnd.util.GeneraCuadroMensaje;
import frontEnd.util.SialComboCellFactory;
import frontEnd.util.SialDateCellFactory;
import frontEnd.util.SialTextFieldCellFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//import mx.com.lobos.RegistroActividadesWs.LoboException_Exception;

/**
 *
 * @author Lobo Software
 */
public class SPPRYF12Controller implements Initializable {

    @FXML
    private Label lbFecha;
    @FXML
    private DatePicker dtfFechaActual;
    @FXML
    private HBox layoutDatePicker;
    @FXML
    private TableView<SpdReportesActividadesModel> grdActividades;
    @FXML
    private TableColumn<SpdReportesActividadesModel, Stopwatch> colTimer;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colProyecto;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colActividades;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colTiempo;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colDescripcion;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colInicio;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colFin;
    @FXML
    private TextField tfTiempoTotal;
    @FXML
    private TextField tfTiempoInicio;
    @FXML
    private TextField tfTiempoFin;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colAvance;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private Button btnStarStop;
    @FXML
    private Button btnAgregarActividad;
    @FXML
    private Label lbTotalRegistros;
    @FXML
    private Label lbTiempoTotal;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colFecha;
    @FXML
    private TableColumn<SpdReportesActividadesModel, Boolean> colActivo;
    @FXML
//    private Button btnGuardar;
//      @FXML
//    private TextField tfUser;
//    @FXML
//    private PasswordField tfPass;
    // VARIABLES 
    private ObservableList<String> datosComboProyecto;
    private ObservableList<String> datosComboActividades;
    private final Font fuenteReloj = Font.loadFont(Stopwatch.class.getResource("digital-7_mono.ttf").toExternalForm(), 24);
    private static boolean running, insertaDiaActual;
    private static TableView<SpdReportesActividadesModel> grid;
    private static int posicionTimer;
    private static Stage primaryStage;
    public static DatePicker datePicker;
    public static int posicionTimerRunning;
    private static ObservableList<SpdReportesActividadesModel> registrosNuevos, registrosActualizados;
    private static TextField duracion, horaInicio, horaFin;
    private static LocalDate fechaSeleccionada;
    private int contadorCambioDia;
    private SpdReportesActividadesModel actividadEnEjecucion;
    private static Stage loading = GeneraCuadroMensaje.loading();
//    private String claveUsuario;
//    private String password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        loading.show();
        tfTiempoTotal.setText("00:00:00");
        tfTiempoTotal.setFont(fuenteReloj);
        tfTiempoInicio.setText("00:00:00");
        tfTiempoInicio.setFont(fuenteReloj);
        tfTiempoFin.setText("00:00:00");
        tfTiempoFin.setFont(fuenteReloj);
        AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
//        Stopwatch stopWatch = new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin);
        btnStarStop.setMinWidth(61);
        btnStarStop.setMinHeight(65);
//        hboxBotonPlayAgregar.getChildren().removeAll(btnStarStop, btnAgragarAtividad);
//        hboxBotonPlayAgregar.getChildren().addAll(stopWatch, btnAgragarAtividad);
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutDatePicker.getChildren().remove(dtfFechaActual);
        layoutDatePicker.getChildren().add(popupContent);
        datePicker = dtfFechaActual;
        dtfFechaActual.valueProperty().addListener((ov, oldValue, newValue) -> {
            fechaSeleccionada = newValue;
            consultaActividades();
//            if (contadorCambioDia == 0) {
//                SPPRYF12Controller.oldValue = oldValue;
//                SPPRYF12Controller.newValue = newValue;
//                this.cambioDia();
//            } else {
//                contadorCambioDia = 0;
//            }
        });
        muestraFecha();
        //Store actividades.

        SialTextFieldCellFactory<SpdReportesActividadesModel, String> textFieldCell = new SialTextFieldCellFactory<>();
        SialComboCellFactory<SpdReportesActividadesModel, String> comboBoxCell = new SialComboCellFactory<>();
        SialComboCellFactory<SpdReportesActividadesModel, String> comboBoxCellCascade = new SialComboCellFactory<>();
        SialDateCellFactory<SpdReportesActividadesModel, Date> datePickerCell = new SialDateCellFactory<>();
//        SialCheckBoxCellFactory<SpdReportesActividadesModel, Boolean> checkBoxCell = new SialCheckBoxCellFactory<>();

        colTimer.setCellValueFactory(new PropertyValueFactory<>("stopWatch"));
//        colTimer.setCellFactory(timerCell.creaTimer(tfTotal, tftiempoInicio, tfTiempoFin));
        try {
            SesionesMongo.getDataConection();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller. \nMÉTODO: initialize");
        }
//        
        this.SpProyecto();
        this.SpActividades("all");
        colProyecto.setCellValueFactory(new PropertyValueFactory<>("proyecto"));
        colProyecto.setCellFactory(comboBoxCell.creaComboBox(datosComboProyecto, true, 1));
        colProyecto.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setProyecto(t.getNewValue());
                    ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setActividad("");
                    this.SpActividades(t.getNewValue());
                });
        colActividades.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colActividades.setCellFactory(comboBoxCellCascade.creaComboBoxWithCascade(datosComboProyecto, datosComboActividades, true, 2));
        colActividades.setOnEditStart((TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
            String proyecto = ((SpdReportesActividadesModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .getProyecto().split("-")[0].trim();

            this.SpActividades(proyecto);
            comboBoxCellCascade.actualizaListaComboCascada(datosComboActividades);
        });
        colActividades.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIdProyColPlanAct(t.getNewValue().split("-")[0]);
                    ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setActividad(t.getNewValue());
                });
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(textFieldCell.creaTextField());
        colDescripcion.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    ((SpdReportesActividadesModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setDescripcion(t.getNewValue());

                });

        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colAvance.setCellFactory(textFieldCell.creaTextField());
        colAvance.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    ((SpdReportesActividadesModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setAvance(t.getNewValue());
                });
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
//        colFecha.setCellFactory(datePickerCell.creaDatePicker());
//        colActivo.setGraphic(checkBoxCell.createCheckHeader(colActivo, grdActividades));
//        colActivo.setCellValueFactory(cellData -> cellData.getValue().activoProperty());
//        colActivo.setCellFactory(checkBoxCell.creaCheckBoxMultipleSelection());

        //Carga de registros de actividades
        grid = grdActividades;
        duracion = tfTiempoTotal;
        horaInicio = tfTiempoInicio;
        horaFin = tfTiempoFin;
        fechaSeleccionada = dtfFechaActual.getValue();
        grdActividades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue muestra, Object valorViejo, Object valorNuevo) {
                muestraActividadesFormulario();
            }
        });
        tfTiempoTotal.setOnAction(e -> {
            if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                grdActividades.getSelectionModel().getSelectedItem().setDuracion(tfTiempoTotal.getText());
            }
        });
        tfTiempoTotal.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                        grdActividades.getSelectionModel().getSelectedItem().setDuracion(tfTiempoTotal.getText());
                    }
                }
            }
        });
        tfTiempoInicio.setOnAction(e -> {
            if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                grdActividades.getSelectionModel().getSelectedItem().setHoraInicio(tfTiempoInicio.getText());
            }
        });
        tfTiempoInicio.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                        grdActividades.getSelectionModel().getSelectedItem().setHoraInicio(tfTiempoInicio.getText());
                    }
                }
            }
        });
        tfTiempoFin.setOnAction(e -> {
            if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                grdActividades.getSelectionModel().getSelectedItem().setHoraFin(tfTiempoFin.getText());
            }
        });
        tfTiempoFin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                        grdActividades.getSelectionModel().getSelectedItem().setHoraFin(tfTiempoFin.getText());
                    }
                }
            }
        });
        try {
            consultaActividades();
            setTotalRegistros();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller. \nMÉTODO: initialize");
        }
//        loading.close();
    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada.toUpperCase());
    }

    public void consultaActividades() {
        ObservableList<SpdReportesActividadesModel> registros = FXCollections.observableArrayList();
        boolean isPhantom = false;
        int posicion = 0;
        if (!fechaSeleccionada.equals(LocalDate.now())) {
            btnStarStop.setDisable(true);
            btnAgregarActividad.setDisable(false);
            if (!running) {
                setPosicionTimer(0);
            }
        } else {
            btnStarStop.setDisable(false);
            btnAgregarActividad.setDisable(true);
        }
        HashMap<String, Object> parametrosHsm = new HashMap<>();
        parametrosHsm.put("fecha", fechaSeleccionada);
        parametrosHsm.put("txtTiempoTotal", tfTiempoTotal);
        parametrosHsm.put("txtTiempoInicio", tfTiempoInicio);
        parametrosHsm.put("txtTiempoFinal", tfTiempoFin);
        try {
            ConsultaSpdReportesActividadesStore store = new ConsultaSpdReportesActividadesStore();
            registros = store.consultaActividades(parametrosHsm);
            if (contadorCambioDia == 0) {
                if (grdActividades.getItems().size() > 0) {
                    grdActividades.getItems().get(posicionTimer).duracionProperty().unbind();
                    grdActividades.getItems().get(posicionTimer).horaInicioProperty().unbind();
                    grdActividades.getItems().get(posicionTimer).horaFinProperty().unbind();
                }
            }
//            tfDescripcion.textProperty().unbind();
            tfDescripcion.setText("");
            tfTiempoTotal.textProperty().unbind();
            tfTiempoTotal.setText("00:00:00");
            tfTiempoInicio.textProperty().unbind();
            tfTiempoInicio.setText("00:00:00");
            tfTiempoFin.textProperty().unbind();
            tfTiempoFin.setText("00:00:00");
            if (!fechaSeleccionada.equals(LocalDate.now()) && running && contadorCambioDia == 0) {
                actividadEnEjecucion = grdActividades.getItems().get(posicionTimer);
                contadorCambioDia++;
            } else if (fechaSeleccionada.equals(LocalDate.now()) && actividadEnEjecucion != null && running && contadorCambioDia > 0) {
                contadorCambioDia = 0;
                if (actividadEnEjecucion.getIdReporteActividad() == null) {
                    registros.add(0, actividadEnEjecucion);
                    isPhantom = true;
                } else {
                    for (int x = 0; x < registros.size(); x++) {
                        if (registros.get(x).getIdReporteActividad().equals(actividadEnEjecucion.getIdReporteActividad())) {
                            registros.remove(x);
                            registros.add(x, actividadEnEjecucion);
                            posicion = x;
                        }
                    }

                    isPhantom = false;
                }
            }
            grdActividades.setItems(registros);
            if (isPhantom && running && grdActividades.getItems().size() > 0) {
                grdActividades.getItems().get(0).getStopWatch().startStop.fire();
                grdActividades.getItems().get(0).getStopWatch().startStop.fire();
                contadorCambioDia = 0;
            } else if (!isPhantom && running && grdActividades.getItems().size() > 0) {
                grdActividades.getItems().get(posicion).getStopWatch().startStop.fire();
                grdActividades.getItems().get(posicion).getStopWatch().startStop.fire();
            }
            setTotalRegistros();
            setTiempoTotal();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller. \nMÉTODO: consultaActividades");
        }
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SPPRYF12Controller.primaryStage = primaryStage;
    }

    public static void setBanderaEjecucion(boolean bandera) {
        running = bandera;
    }

    public static boolean getBanderaEjecucion() {
        return running;
    }

    public static void alertActividades(Stage primaryStage, String title, String headerText, int operacion) {
        if (running) {
            Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
            ventana.setTitle(title);
            ventana.setHeaderText(headerText);
            ventana.setContentText("Existe una actividad en ejecución. Si continúa perderá los cambios realizados. ¿Desea continuar?");
//            if (oldValue != null) {
//                contadorCambioDia++;
//                datePicker.setValue(oldValue);
//            }
            Optional<ButtonType> seleccion = ventana.showAndWait();
            if (seleccion.get() == ButtonType.OK) {
                if (operacion == 0) {//cerrar ventana
                    primaryStage.close();
                } else if (operacion == 1) {//eliminar registro
                    eliminaActividadesAction();
                } else if (operacion == 2) {
                    guardaActividadesAction();
                }
//                else if (operacion == 2) { //cambiar dia
//                    cambioDiaAction(grid.getItems(), grid.getSelectionModel().getSelectedItems());
//                    System.out.println("Descarta cambios de: " + oldValue + " Consulta actividades dia: " + newValue);
//                }
            }
            ventana.close();
        }
    }

    public static TableView<SpdReportesActividadesModel> getTableView() {
        return grid;
    }

    public static void setPosicionTimer(int posicion) {
        posicionTimer = posicion;
    }

    public static int getPosicionTimer() {
        return posicionTimer;
    }

    public void agregaActividades() {
        SpdReportesActividadesModel actividades = new SpdReportesActividadesModel();
        actividades.setStopWatch(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin));
        actividades.setDescripcion(tfDescripcion.getText());
        if (dtfFechaActual.getValue().equals(LocalDate.now())) {
            actividades.setDuracion("00:00:00");
            actividades.setHoraInicio("00:00:00");
            actividades.setHoraFin("00:00:00");
            if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
                if (running) {
                    grdActividades.getSelectionModel().getSelectedItem().getStopWatch().startStop.fire();
                }
            }
        } else {
            actividades.setDuracion(tfTiempoTotal.getText());
            actividades.setHoraInicio(tfTiempoInicio.getText());
            actividades.setHoraFin(tfTiempoFin.getText());
        }
        actividades.setFecha(dtfFechaActual.getValue().toString());
        actividades.setAvance("0");
        grdActividades.getItems().add(0, actividades);
        actividades.getStopWatch().startStop.fire();
        setTotalRegistros();

    }

    public void muestraActividadesFormulario() {
        ObservableList<SpdReportesActividadesModel> muestraInfo = grid.getSelectionModel().getSelectedItems();
        if (grid.getSelectionModel().getSelectedItems().size() > 0 && fechaSeleccionada.equals(LocalDate.now())) {
//            tfDescripcion.textProperty().bind(muestraInfo.get(0).descripcionProperty());
            tfDescripcion.setText(muestraInfo.get(0).getDescripcion());
            tfTiempoTotal.textProperty().bind(muestraInfo.get(0).duracionProperty());
            tfTiempoInicio.textProperty().bind(muestraInfo.get(0).horaInicioProperty());
            tfTiempoFin.textProperty().bind(muestraInfo.get(0).horaFinProperty());
        } else if (grid.getSelectionModel().getSelectedItems().size() > 0 && !fechaSeleccionada.equals(LocalDate.now())) {
//            tfDescripcion.textProperty().unbind();
            tfDescripcion.setText(muestraInfo.get(0).getDescripcion());
            tfTiempoTotal.textProperty().unbind();
            tfTiempoTotal.setText(muestraInfo.get(0).getDuracion());
            tfTiempoInicio.textProperty().unbind();
            tfTiempoInicio.setText(muestraInfo.get(0).getHoraInicio());
            tfTiempoFin.textProperty().unbind();
            tfTiempoFin.setText(muestraInfo.get(0).getHoraFin());
//            muestraInfo.get(0).duracionProperty().bindBidirectional(tfTiempoTotal.textProperty());
//            muestraInfo.get(0).horaInicioProperty().bindBidirectional(tfTiempoInicio.textProperty());
//            muestraInfo.get(0).horaFinProperty().bindBidirectional(tfTiempoFin.textProperty());
        }
//        setPosicionTimer(grdActividades.getSelectionModel().getSelectedIndex());
    }

    public void eliminaActividades() {
        if (!grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().getCurrentStatus()) {//SI LA ACTIVIDAD A ELIMINAR NO ESTÁ CORRIENDO
            eliminaActividadesAction();
        } else {
            alertActividades(primaryStage, "SPPRYF12", "ELIMINAR ACTIVIDAD", 1);
        }
        setTotalRegistros();

    }
//    public void cambioDia() {
//        ObservableList<SpdReportesActividadesModel> allRecords = grdActividades.getItems();
//        ObservableList<SpdReportesActividadesModel> registro = grdActividades.getSelectionModel().getSelectedItems();
//        List registroSeleccionado = new ArrayList(registro);
//        if (registro.size() > 0) {
//            if (registro.get(0).getStopWatch().getCurrentStatus()) {
//                alertSpdReportesActividadesModel(primaryStage, "SPPRYF12", "CAMBIAR DE DIA", 2);
//            }
//        } else {
//            System.out.println("Consulta actividades dia" + newValue);
//        }
//    }

    private static void eliminaActividadesAction() {
        if (grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().getCurrentStatus()) {
            grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().startStop.fire();
        }
        grid.getSelectionModel().getSelectedItems().get(0).setDescripcion("");
//        descripcionAct.textProperty().unbind();
        grid.getSelectionModel().getSelectedItems().get(0).duracionProperty().unbind();
        duracion.textProperty().unbind();
        duracion.setText("00:00:00");
        grid.getSelectionModel().getSelectedItems().get(0).horaInicioProperty().unbind();
        horaInicio.textProperty().unbind();
        horaInicio.setText("00:00:00");
        grid.getSelectionModel().getSelectedItems().get(0).horaFinProperty().unbind();
        horaFin.textProperty().unbind();
        horaFin.setText("00:00:00");
        HashMap<String, Object> parametrosHsm = new HashMap<>();
        parametrosHsm.put("listaDelete", grid.getSelectionModel().getSelectedItems());
        parametrosHsm.put("mascara", loading);
        boolean isPhantom = grid.getSelectionModel().getSelectedItems().get(0).getIdReporteActividad() == null;
        if (!isPhantom) {
            EliminaSpdReportesActividadesStore store = new EliminaSpdReportesActividadesStore();
            try {
                store.eliminaActividades(parametrosHsm);
                loading.close();
            } catch (Exception ex) {
                GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller. \nMÉTODO: eliminaActividadesAction");
            }
        }
        grid.getItems().removeAll(grid.getSelectionModel().getSelectedItems());
    }

    public void setTotalRegistros() {
        lbTotalRegistros.setText(String.valueOf(grid.getItems().size()));
    }

//    public static void cambioDiaAction(ObservableList<SpdReportesActividadesModel> allRecords, ObservableList<SpdReportesActividadesModel> registroSeleccionado) {
//        contadorCambioDia++;
//        datePicker.setValue(newValue);
//        registroSeleccionado.get(0).getStopWatch().startStop.fire();
//    }
    public void setTiempoTotal() {
//               int TiempoTotal = 0;
//        String tiempoInicio = "", tiempoFin = "", tiempoTotal = "", minutos = "", sumaTiempoTotal = "";
//        Double total, totalFinal = Double.MIN_VALUE;
//        int sumar = 0, i = 0;
//        String[] hora, horaTiempoIni, horaTiempoFin;
//        long hr;
//        double min = 0, sumaTotal = 0;
//probar

        String duracion = "";
        String[] tiempo;
        double min = 0.0, hora = 0.0, horasTotales = 0.0;
        for (int i = 0; i < grid.getItems().size(); i++) {
            if (grid.getItems().get(i).getDuracion() != null) {
                if (!grid.getItems().get(i).getDuracion().equals("")) {
                    tiempo = grid.getItems().get(i).getDuracion().split("[.]");
                    hora = Double.parseDouble(grid.getItems().get(i).getDuracion());
                    hora = Math.ceil(hora * 1000) / 1000;
                    horasTotales += hora;
                    hora -= Integer.parseInt(tiempo[0]);
                    hora = Math.ceil(hora * 1000) / 1000;
                    min = hora * 60 / 100;
                    min = Math.floor(min * 100) / 100;
                    duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00");
                    grid.getItems().get(i).setDuracion(duracion);
                } else if (!dtfFechaActual.getValue().equals(LocalDate.now())) {
                    grid.getItems().get(i).setDuracion("00:00:00");
                }
            }
            if (grid.getItems().get(i).getHoraInicio() != null) {
                if (!grid.getItems().get(i).getHoraInicio().equals("")) {
                    tiempo = grid.getItems().get(i).getHoraInicio().split("[.]");
                    hora = Double.parseDouble(grid.getItems().get(i).getHoraInicio());
                    hora = Math.ceil(hora * 1000) / 1000;
                    hora -= Integer.parseInt(tiempo[0]);
                    hora = Math.ceil(hora * 1000) / 1000;
                    min = hora * 60 / 100;
                    min = Math.floor(min * 100) / 100;
                    duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00");
                    grid.getItems().get(i).setHoraInicio(duracion);
                } else if (!dtfFechaActual.getValue().equals(LocalDate.now())) {
                    grid.getItems().get(i).setHoraInicio("00:00:00");
                }
            }
            if (grid.getItems().get(i).getHoraFin() != null) {
                if (!grid.getItems().get(i).getHoraFin().equals("")) {
                    tiempo = grid.getItems().get(i).getHoraFin().split("[.]");
                    hora = Double.parseDouble(grid.getItems().get(i).getHoraFin());
                    hora = Math.ceil(hora * 1000) / 1000;
                    hora -= Integer.parseInt(tiempo[0]);
                    hora = Math.ceil(hora * 1000) / 1000;
                    min = hora * 60 / 100;
                    min = Math.floor(min * 100) / 100;
                    duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00");
                    grid.getItems().get(i).setHoraFin(duracion);
                } else if (!dtfFechaActual.getValue().equals(LocalDate.now())) {
                    grid.getItems().get(i).setHoraFin("00:00:00");
                }
            }

            tiempo = String.valueOf(horasTotales).split("[.]");
            hora = horasTotales;
            hora = Math.ceil(hora * 1000) / 1000;
            hora -= Integer.parseInt(tiempo[0]);
            hora = Math.ceil(hora * 1000) / 1000;
            min = hora * 60 / 100;
            min = Math.floor(min * 100) / 100;
            duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(min).split("[.]")[1].length() == 1 ? "0" + String.valueOf(min).split("[.]")[1] : String.valueOf(min).split("[.]")[1]) + ":00");
            lbTiempoTotal.setText(String.valueOf(duracion));
        }
    }

    public void guardaActividades() {
        registrosNuevos = FXCollections.observableArrayList();
        registrosActualizados = FXCollections.observableArrayList();
        for (int x = 0; x < grdActividades.getItems().size(); x++) {
            if (grdActividades.getItems().get(x).getIdReporteActividad() == null) { //REGISTROS NUEVOS
                registrosNuevos.add(grdActividades.getItems().get(x));
            } else {
                registrosActualizados.add(grdActividades.getItems().get(x));
            }
            if (dtfFechaActual.getValue().equals(LocalDate.now())) {
                if (grdActividades.getItems().get(x).getStopWatch().getCurrentStatus()) {//SI EL REGISTRO ACTUAL ESTA CORRIENDO
                    posicionTimerRunning = x;
                    insertaDiaActual = true;
                }
            } else {
                insertaDiaActual = false;
            }
        }
        if (running && dtfFechaActual.getValue().equals(LocalDate.now())) {
            alertActividades(primaryStage, "SPPRYF12", "GUARDAR ACTIVIDADES", 2);
        } else {
            guardaActividadesAction();
        }
    }

    public static void guardaActividadesAction() {
//        loading.show();
        InsertaSpdReportesActividadesStore storeInsert;
        ActualizaSpdReportesActividadesStore storeUpdate;
        HashMap<String, Object> parametrosHsm;
        if (registrosNuevos.size() > 0) {
            if (running) {
                if (insertaDiaActual) {
                    registrosNuevos.get(posicionTimerRunning).getStopWatch().startStop.fire();
                }
                if (grid.getItems().get(posicionTimer).getStopWatch().getCurrentStatus() && datePicker.getValue().equals(LocalDate.now())) {
                    grid.getItems().get(posicionTimer).getStopWatch().startStop.fire();
                }
            }
        }
        if (registrosActualizados.size() > 0) {
            if (running) {
                if (insertaDiaActual) {
                    registrosActualizados.get(posicionTimerRunning).getStopWatch().startStop.fire();
                }
                if (registrosActualizados.get(posicionTimerRunning).getStopWatch().getCurrentStatus() && datePicker.getValue().equals(LocalDate.now())) {
                    grid.getItems().get(posicionTimer).getStopWatch().startStop.fire();
                }
            }
        }
        storeInsert = new InsertaSpdReportesActividadesStore();
        storeUpdate = new ActualizaSpdReportesActividadesStore();
        parametrosHsm = new HashMap<>();
        parametrosHsm.put("listaCreate", registrosNuevos);
        parametrosHsm.put("listaUpdate", registrosActualizados);
        parametrosHsm.put("mascara", loading);
        try {
            storeInsert.insertaActividades(parametrosHsm);
            storeUpdate.actualizaActividades(parametrosHsm);
            loading.close();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller. \nMÉTODO: guardaActividadesAction");
        }
    }

    public void consultaProyectos() {
        muestraVentanaLoginWebService();
    }
//

    public void SincronizaActividades() throws Exception {
//        claveUsuario = "ceci";
//        password = "";
//     
//        try {
//            muestraVentanaLoginWebService();
////                
//        } catch (Exception ex) {
//            Logger.getLogger(SPPRYF12Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (tfUser.getText().equals(claveUsuario) && tfPass.getText().equals(password)) {
//            System.out.print("User loggin ...");
//
//        }else{
//            System.out.println("Loggin failed");
//    }
    }

    public void muestraVentanaLoginWebService() {
        FXMLLoader ventanaInicioSesion = new FXMLLoader(getClass().getResource("/frontEnd/view/SPPRYF12AView.fxml"));
        AnchorPane root;
        Stage ventanaInicio;
        Scene scene;
        try {
            root = ventanaInicioSesion.load();
            ventanaInicio = new Stage();
            scene = new Scene(root);
            ventanaInicio.setScene(scene);
            ventanaInicio.setTitle("SPPRYF12AView. Login a SIAPROY WEB");
            ventanaInicio.show();
        } catch (IOException ex) {
            GeneraCuadroMensaje.error(ex.toString() + "\nCLASE: SPPRYF12Controller\nMÉTODO: muestraVentanaLoginWebService");
        }
////     
//        
//        
    }

    public void SpProyecto() {
        HashMap<String, Object> parametrosHsm = new HashMap<>();
        parametrosHsm.put("cascada", "consultaProyectos");
        parametrosHsm.put("cveColaborador", "CCORTEZ");
        ObservableList<String> registros = FXCollections.observableArrayList();
        ConsultaSpdProyectosStore store = new ConsultaSpdProyectosStore();
        try {
            registros = store.consulta(parametrosHsm);
            datosComboProyecto = registros;
        } catch (Exception ex) {
            Logger.getLogger(SPPRYF12Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SpActividades(String proyecto) {
        HashMap<String, Object> parametrosHsm = new HashMap<>();
        parametrosHsm.put("cveColaborador", "CCORTEZ");
        parametrosHsm.put("cascada", "consultaActividades");
        if (!proyecto.equals("all")) {
            parametrosHsm.put("idProyecto", proyecto.split("-")[0].trim());
        }
        parametrosHsm.put("allActividades", proyecto.equals("all"));
        

        ObservableList<String> registros = FXCollections.observableArrayList();
        ConsultaSpdProyectosStore store = new ConsultaSpdProyectosStore();
        try {
            registros = store.consulta(parametrosHsm);
            datosComboActividades = registros;
        } catch (Exception ex) {
            Logger.getLogger(SPPRYF12Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    private static String cargaRegistro(java.lang.String parametros, java.lang.String cascada) throws Exception {
//        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service service = new mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service();
//        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs port = service.getRegistroActividadesWsPort();
//        return port.cargaRegistro(parametros, cascada);
////    
//}
}

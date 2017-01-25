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
   05/May/2016 18:55 CCL (LOBO_000076): Se añade la funcionalidad de Insert Siaproy web, y función de Sincronizado a BDD Siaproy.
   06/May/2016 09:35 SVA (LOBO_000076): Se añade parámetro en el método "creaTextField".
   06/May/2016 07:27 CCL (LOBO_000076): Se añade la funcionalidad de los Jason para la interpretación de los registros en la versión web y se eliminan linas innecesarías. .
   07/May/2016 11:02 CCL (LOBO_000076): Se eliminan comoponentes inecesarior y se establece funcionalidad a los componenets pera establecer clave colavorador y potros para genrear consultas si la clave colaborador es correcta.
   07/May/2016 11:31 SVA (LOBO_000076): Se mejora el método "setTiempoTotal".
   07/May/2016 11:44 SVA (LOBO_000076): Se mejora el cálculo de horas totales en el método "setTiempoTotal".
   10/May/2016 13:07 SVA (LOBO_000076): Se mejoran funciones en general.
   //10/May/2016 15:30 CCL (LOBO_000076): Se elimina código y se ajustan componentes y se restructura la aparicencia visual.
   24/Ene/2017 11:13 BEFL: Se corrige error al consultar actividades  y se validan las horas.


 */
package frontEnd.controller;

import backEnd.mx.com.lobos.spdreportesactividades.store.ActualizaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.ConsultaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.EliminaSpdReportesActividadesStore;
import backEnd.mx.com.lobos.spdreportesactividades.store.InsertaSpdReportesActividadesStore;
import com.google.gson.Gson;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
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
import frontEnd.util.SialTextFieldCellFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    private Button btnConsultaProyectos;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lbTotalRegistros;
    @FXML
    private Label lbTiempoTotal;
    @FXML
    private TableColumn<SpdReportesActividadesModel, String> colFecha;
    @FXML
    private TableColumn<SpdReportesActividadesModel, Boolean> colActivo;
    @FXML
    private TableColumn<SpdReportesActividadesModel, Date> colFechaEditable;
    @FXML
    private ImageView imgLogo1;
    private ObservableList<String> datosComboProyecto;
    private ObservableList<String> datosComboActividades;
    private final Font fuenteReloj = Font.loadFont(Stopwatch.class.getResource("digital-7_mono.ttf").toExternalForm(), 24);
    private static boolean running, insertaDiaActual;
    private static TableView<SpdReportesActividadesModel> grid;
    private static DatePicker dfFecha;
    private static int posicionTimer;
    private static Stage primaryStage;
    public static DatePicker datePicker;
    public static int posicionTimerRunning;
    private static ObservableList<SpdReportesActividadesModel> registrosNuevos, registrosActualizados;
    private static TextField descripcion, duracion, horaInicio, horaFin;
    private static Button btStarStop, btAgregarActividad;
    private static LocalDate fechaSeleccionada;
    private static Label lblTotal, lblTiempoTotal;
    private static int contadorCambioDia;
    private static SpdReportesActividadesModel actividadEnEjecucion;
    private static Stage loading;
    private String valorAnterior;
    private String claveUsuario;
    private String contraseña;
    private Object tfClaveUsuario;
    private Object tfContrasena;
    private String claveUsuarioSiaproy;
    private String contrasenaSiaproy;
    private boolean loginSiaproWeb, loginFromProyecto;
    private Gson gson = new Gson();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/frontEnd/images/SIAPROY1.png");
        imgLogo1.setImage(image);
        loading = GeneraCuadroMensaje.loading();
        tfTiempoTotal.setText("00:00:00");
        tfTiempoTotal.setFont(fuenteReloj);
        tfTiempoInicio.setText("00:00:00");
        tfTiempoInicio.setFont(fuenteReloj);
        tfTiempoFin.setText("00:00:00");
        tfTiempoFin.setFont(fuenteReloj);
        AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
        btnStarStop.setMinWidth(61);
        btnStarStop.setMinHeight(65);
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutDatePicker.getChildren().remove(dtfFechaActual);
        layoutDatePicker.getChildren().add(popupContent);
        datePicker = dtfFechaActual;
        dtfFechaActual.valueProperty().addListener((ov, oldValue, newValue) -> {
            fechaSeleccionada = newValue;
            consultaActividades();
        });
        muestraFecha();
        SialTextFieldCellFactory<SpdReportesActividadesModel, String> textFieldCell = new SialTextFieldCellFactory<>();
        SialComboCellFactory<SpdReportesActividadesModel, String> comboBoxCell = new SialComboCellFactory<>();
        SialComboCellFactory<SpdReportesActividadesModel, String> comboBoxCellCascade = new SialComboCellFactory<>();

        colTimer.setCellValueFactory(new PropertyValueFactory<>("stopWatch"));
        colProyecto.setCellValueFactory(new PropertyValueFactory<>("proyecto"));
        colProyecto.setCellFactory(comboBoxCell.creaComboBox(datosComboProyecto, true, 1));
        colProyecto.setOnEditStart((TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
            comboBoxCellCascade.actualizaListaCombo(datosComboProyecto);
        });
        colProyecto.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    if (t.getNewValue() != null) {
                        if (!t.getNewValue().equals("")) {
                            if (!t.getNewValue().equals(t.getOldValue())) {
                                ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setActividad("");
                                ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIdProyColPlanAct("");
                                this.consultaActividadesSiaproyWeb(t.getNewValue());
                            }
                        }
                    }
                    ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setProyecto(t.getNewValue());
                });
        colProyecto.setEditable(false);
        colActividades.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colActividades.setCellFactory(comboBoxCellCascade.creaComboBoxWithCascade(datosComboProyecto, datosComboActividades, true, 2));
        colActividades.setOnEditStart((TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
            String proyecto = ((SpdReportesActividadesModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .getProyecto();
            if (proyecto != null) {
                proyecto = proyecto.split("-")[0].trim();
            }

            this.consultaActividadesSiaproyWeb(proyecto);
            comboBoxCellCascade.actualizaListaComboCascada(datosComboActividades);
        });
        colActividades.setOnEditCommit(
                (TableColumn.CellEditEvent<SpdReportesActividadesModel, String> t) -> {
                    if (t.getNewValue() != null) {
                        if (!t.getNewValue().equals("")) {
                            ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIdProyColPlanAct(t.getNewValue().split("-")[0]);
                            ((SpdReportesActividadesModel) t.getTableView().getItems().get(t.getTablePosition().getRow())).setActividad(t.getNewValue());
                            datosComboActividades = FXCollections.observableArrayList();
                        }
                    }
                });
        colActividades.setEditable(false);
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(textFieldCell.creaTextField(0));
        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colAvance.setCellFactory(textFieldCell.creaTextField(4));
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        descripcion = tfDescripcion;
        duracion = tfTiempoTotal;
        horaInicio = tfTiempoInicio;
        horaFin = tfTiempoFin;
        fechaSeleccionada = dtfFechaActual.getValue();
        lblTotal = lbTotalRegistros;
        lblTiempoTotal = lbTiempoTotal;
        btStarStop = btnStarStop;
        btAgregarActividad = btnAgregarActividad;
        btnGuardar.setOnAction(e -> {
            guardaActividades();
        });

        grid = grdActividades;
        grid.setPlaceholder(new Label("No hay datos que mostrar"));
        grid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue muestra, Object valorViejo, Object valorNuevo) {
                muestraActividadesFormulario();
            }
        });
        duracion.setOnKeyPressed((KeyEvent t) -> {
            if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                if (grid.getSelectionModel().getSelectedItems().size() > 0) {
                    valorAnterior = grid.getSelectionModel().getSelectedItem().getDuracion();
                    if (!duracion.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                        duracion.setText(valorAnterior);
                    }
                    grid.getSelectionModel().getSelectedItem().setDuracion(duracion.getText());
                }
            }
        });
        duracion.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue && grid.getSelectionModel().getSelectedItems().size() > 0) {
                valorAnterior = grid.getSelectionModel().getSelectedItem().getDuracion();
                if (!duracion.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    duracion.setText(valorAnterior);
                }
                grid.getSelectionModel().getSelectedItem().setDuracion(duracion.getText());
            }
        });
        duracion.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() == 8) {
                if (!duracion.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    duracion.setText(oldValue);
                }
            }
            if (duracion.getText().length() > 8) {
                duracion.setText(oldValue);
            }
        });
        tfTiempoInicio.setOnKeyPressed((KeyEvent t) -> {
            if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                if (grid.getSelectionModel().getSelectedItems().size() > 0) {
                    valorAnterior = grid.getSelectionModel().getSelectedItem().getHoraInicio();
                    if (!tfTiempoInicio.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                        tfTiempoInicio.setText(valorAnterior);
                    }
                    grid.getSelectionModel().getSelectedItem().setHoraInicio(tfTiempoInicio.getText());
                }
            }
        });
        tfTiempoInicio.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue && grid.getSelectionModel().getSelectedItems().size() > 0) {
                valorAnterior = grid.getSelectionModel().getSelectedItem().getHoraInicio();
                if (!tfTiempoInicio.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    tfTiempoInicio.setText(valorAnterior);
                }
                grid.getSelectionModel().getSelectedItem().setHoraInicio(tfTiempoInicio.getText());
            }
        });
        tfTiempoInicio.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() == 8) {
                if (!tfTiempoInicio.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    tfTiempoInicio.setText(oldValue);
                }
            }
            if (tfTiempoInicio.getText().length() > 8) {
                tfTiempoInicio.setText(oldValue);
            }
        });
        tfTiempoFin.setOnKeyPressed((KeyEvent t) -> {
            if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                if (grid.getSelectionModel().getSelectedItems().size() > 0) {
                    valorAnterior = grid.getSelectionModel().getSelectedItem().getHoraFin();
                    if (!tfTiempoFin.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                        tfTiempoFin.setText(valorAnterior);
                    }
                    grid.getSelectionModel().getSelectedItem().setHoraFin(tfTiempoFin.getText());
                }
            }
        });
        tfTiempoFin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                    if (grid.getSelectionModel().getSelectedItems().size() > 0) {
                        grid.getSelectionModel().getSelectedItem().setHoraFin(tfTiempoFin.getText());
                    }
                }
            }
        });
        tfTiempoFin.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if (oldPropertyValue && grid.getSelectionModel().getSelectedItems().size() > 0) {
                valorAnterior = grid.getSelectionModel().getSelectedItem().getHoraFin();
                if (!tfTiempoFin.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    tfTiempoFin.setText(valorAnterior);
                }
                grid.getSelectionModel().getSelectedItem().setHoraFin(tfTiempoFin.getText());
            }
        });
        tfTiempoFin.textProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue.length() == 8) {
                if (!tfTiempoFin.getText().matches("[0-2][0-9]:[0-5][0-9]:[0-5][0-9]")) {
                    tfTiempoFin.setText(oldValue);
                }
            }
            if (tfTiempoFin.getText().length() > 8) {
                tfTiempoFin.setText(oldValue);
            }
        });
        tfDescripcion.textProperty().addListener((ov, oldValue, newValue) -> {
            StringProperty elemento = (StringProperty) ov;
            ((TextField) elemento.getBean()).setText(newValue.toUpperCase());
        });
        try {
            consultaActividades();
            setTotalRegistros();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error("Ocurrió un problema al iniciar la aplicación.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: initialize");
        }
    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada.toUpperCase());
    }

    public static void consultaActividades() {
        ObservableList<SpdReportesActividadesModel> registros = FXCollections.observableArrayList();
        boolean isPhantom = false;
        int posicion = 0;
        if (!fechaSeleccionada.equals(LocalDate.now())) {
            btStarStop.setDisable(true);
            btAgregarActividad.setDisable(false);
            if (!running) {
                setPosicionTimer(0);
            }
        } else {
            btStarStop.setDisable(false);
            btAgregarActividad.setDisable(true);
        }
        HashMap<String, Object> parametrosHsm = new HashMap<>();
        parametrosHsm.put("fecha", fechaSeleccionada);
        parametrosHsm.put("txtTiempoTotal", duracion);
        parametrosHsm.put("txtTiempoInicio", horaInicio);
        parametrosHsm.put("txtTiempoFinal", horaFin);
        try {
            ConsultaSpdReportesActividadesStore store = new ConsultaSpdReportesActividadesStore();
            registros = store.consultaActividades(parametrosHsm);
            if (contadorCambioDia == 0) {
                if (grid.getItems().size() > 0) {
                    grid.getItems().get(posicionTimer).duracionProperty().unbind();
                    grid.getItems().get(posicionTimer).horaInicioProperty().unbind();
                    grid.getItems().get(posicionTimer).horaFinProperty().unbind();
                }
            }
            descripcion.setText("");
            duracion.textProperty().unbind();
            duracion.setText("00:00:00");
            horaInicio.textProperty().unbind();
            horaInicio.setText("00:00:00");
            horaFin.textProperty().unbind();
            horaFin.setText("00:00:00");
            if (!fechaSeleccionada.equals(LocalDate.now()) && running && contadorCambioDia == 0) {
                actividadEnEjecucion = grid.getItems().get(posicionTimer);
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
            grid.setItems(registros);
            if (isPhantom && running && grid.getItems().size() > 0) {
                grid.getItems().get(0).getStopWatch().startStop.fire();
                grid.getItems().get(0).getStopWatch().startStop.fire();
                contadorCambioDia = 0;
            } else if (!isPhantom && running && grid.getItems().size() > 0) {
                grid.getItems().get(posicion).getStopWatch().startStop.fire();
                grid.getItems().get(posicion).getStopWatch().startStop.fire();
            }
            setTotalRegistros();
            setTiempoTotal();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error("Ocurrió un problema al consultar las actividades.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: consultaActividades");
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
            Optional<ButtonType> seleccion = ventana.showAndWait();
            if (seleccion.get() == ButtonType.OK) {
                if (operacion == 0) {
                    primaryStage.close();
                    System.exit(-1);
                } else if (operacion == 1) {
                    eliminaActividadesAction();
                } else if (operacion == 2) {
                    guardaActividadesAction();
                }
            }
            ventana.close();
        }
    }

    public static TableView<SpdReportesActividadesModel> getTableView() {
        return grid;
    }

    public static TextField getTfDuracion() {
        return duracion;
    }

    public static TextField getTfHoraInicio() {
        return horaInicio;
    }

    public static TextField getTfHoraFin() {
        return horaFin;
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
        actividades.setDescripcion(!tfDescripcion.getText().equals("") ? tfDescripcion.getText() : "NUEVA ACTIVIDAD");
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
            actividades.setDuracion(duracion.getText());
            actividades.setHoraInicio(tfTiempoInicio.getText());
            actividades.setHoraFin(tfTiempoFin.getText());
        }
        actividades.setFecha(dtfFechaActual.getValue().toString());
        actividades.setAvance("0.0");
        grdActividades.getItems().add(0, actividades);
        actividades.getStopWatch().startStop.fire();
        setTotalRegistros();

    }

    public void muestraActividadesFormulario() {
        ObservableList<SpdReportesActividadesModel> seleccion = grid.getSelectionModel().getSelectedItems();
        if (seleccion.size() < 1) {
            return;
        }
//        if (fechaSeleccionada.equals(LocalDate.now())) {
//            tfTiempoTotal.textProperty().bind(seleccion.get(0).duracionProperty());
//            tfTiempoInicio.textProperty().bind(seleccion.get(0).horaInicioProperty());
//            tfTiempoFin.textProperty().bind(seleccion.get(0).horaFinProperty());

//        } else {
        tfDescripcion.setText(seleccion.get(0).getDescripcion());
        duracion.textProperty().unbind();
        duracion.setText(seleccion.get(0).getDuracion());
        tfTiempoInicio.textProperty().unbind();
        tfTiempoInicio.setText(seleccion.get(0).getHoraInicio());
        tfTiempoFin.textProperty().unbind();
        tfTiempoFin.setText(seleccion.get(0).getHoraFin());
//        }
    }

    public void eliminaActividades() {
        if (!grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().getCurrentStatus()) {//SI LA ACTIVIDAD A ELIMINAR NO ESTÁ CORRIENDO
            eliminaActividadesAction();
        } else {
            alertActividades(primaryStage, "SPPRYF12", "ELIMINAR ACTIVIDAD", 1);
        }
        setTotalRegistros();

    }

    private static void eliminaActividadesAction() {
        if (grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().getCurrentStatus()) {
            grid.getSelectionModel().getSelectedItems().get(0).getStopWatch().startStop.fire();
        }
        descripcion.setText("");
        grid.getSelectionModel().getSelectedItems().get(0).setDescripcion("");
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
                GeneraCuadroMensaje.error("Ocurrió un problema al eliminar el registro.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: eliminaActividadesAction");
            }
        }
        grid.getItems().removeAll(grid.getSelectionModel().getSelectedItems());
    }

    public static void setTotalRegistros() {
        lblTiempoTotal.setText(String.valueOf(grid.getItems().size()));
    }

    public static void setTiempoTotal() {
        String duracion = "", m = "";
        String[] tiempo = null;
        double min = 0.0, hora = 0.0, horasTotales = 0.0;
        if (grid.getItems().size() > 0) {
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
                        min = Math.floor(min * 1000) / 1000;
                        m = String.valueOf(min);
                        if (String.valueOf(min).split("[.]")[1].length() > 2 && Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(2)) > 5) {
                            m = "0." + String.valueOf(Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(0, 2)) + 1);
                        } else if (m.split("[.]")[1].length() > 1) {
                            m = m.substring(0, 4);
                        } else if (m.split("[.]")[1].equals("3")) {
                            m = "0.30";
                        }
                        duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00");
                        grid.getItems().get(i).setDuracion(duracion);
                    } else if (!fechaSeleccionada.equals(LocalDate.now())) {
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
                        min = Math.floor(min * 1000) / 1000;
                        m = String.valueOf(min);
                        if (String.valueOf(min).split("[.]")[1].length() > 2 && Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(2)) > 5) {
                            m = "0." + String.valueOf(Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(0, 2)) + 1);
                        } else if (m.split("[.]")[1].length() > 1) {
                            m = m.substring(0, 4);
                        } else if (m.split("[.]")[1].equals("3")) {
                            m = "0.30";
                        }
                        duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00");
                        grid.getItems().get(i).setHoraInicio(duracion);
                    } else if (!fechaSeleccionada.equals(LocalDate.now())) {
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
                        min = Math.floor(min * 1000) / 1000;
                        m = String.valueOf(min);
                        if (String.valueOf(min).split("[.]")[1].length() > 2 && Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(2)) > 5) {
                            m = "0." + String.valueOf(Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(0, 2)) + 1);
                        } else if (m.split("[.]")[1].length() > 1) {
                            m = m.substring(0, 4);
                        } else if (m.split("[.]")[1].equals("3")) {
                            m = "0.30";
                        }
                        duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00");
                        grid.getItems().get(i).setHoraFin(duracion);
                    } else if (!fechaSeleccionada.equals(LocalDate.now())) {
                        grid.getItems().get(i).setHoraFin("00:00:00");
                    }
                }

                tiempo = String.valueOf(horasTotales).split("[.]");
                hora = horasTotales;
                hora = Math.ceil(hora * 1000) / 1000;
                hora -= Integer.parseInt(tiempo[0]);
                hora = Math.ceil(hora * 1000) / 1000;
                min = hora * 60 / 100;
                min = Math.floor(min * 1000) / 1000;
                m = String.valueOf(min);
                if (String.valueOf(min).split("[.]")[1].length() > 2 && Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(2)) > 5) {
                    m = "0." + String.valueOf(Integer.parseInt(String.valueOf(min).split("[.]")[1].substring(0, 2)) + 1);
                } else if (m.split("[.]")[1].length() > 1) {
                    m = m.substring(0, 4);
                } else if (m.split("[.]")[1].equals("3")) {
                    m = "0.30";
                }
                duracion = tiempo[0].length() > 1 ? tiempo[0] + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00") : ("0" + tiempo[0]) + (":" + (String.valueOf(m).split("[.]")[1].length() == 1 ? "0" + String.valueOf(m).split("[.]")[1] : String.valueOf(m).split("[.]")[1].length() > 2 ? String.valueOf(m).split("[.]")[1].substring(0, 2) : String.valueOf(m).split("[.]")[1]) + ":00");
                lblTiempoTotal.setText(String.valueOf(duracion));
            }
        } else {
            lblTiempoTotal.setText(String.valueOf("00:00:00"));
        }
    }

    public static void guardaActividades() {
        registrosNuevos = FXCollections.observableArrayList();
        registrosActualizados = FXCollections.observableArrayList();
        for (int x = 0; x < grid.getItems().size(); x++) {
            if (grid.getItems().get(x).getIdReporteActividad() == null) { //REGISTROS NUEVOS
                registrosNuevos.add(grid.getItems().get(x));
            } else {
                registrosActualizados.add(grid.getItems().get(x));
            }
            if (fechaSeleccionada.equals(LocalDate.now())) {
                if (grid.getItems().get(x).getStopWatch().getCurrentStatus()) {//SI EL REGISTRO ACTUAL ESTA CORRIENDO
                    posicionTimerRunning = x;
                    insertaDiaActual = true;
                }
            } else {
                insertaDiaActual = false;
            }
        }
        if (running && fechaSeleccionada.equals(LocalDate.now())) {
            alertActividades(primaryStage, "SPPRYF12", "GUARDAR ACTIVIDADES", 2);
        } else {
            guardaActividadesAction();
        }
    }

    public static void guardaActividadesAction() {
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
            consultaActividades();
            loading.close();
        } catch (Exception ex) {
            GeneraCuadroMensaje.error("Ocurrió un problema al guardar la información.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: guardaActividadesAction");
        }
    }

    public void consultaProyectos() {
        if (loginSiaproWeb == false) {
            loginFromProyecto = true;
            muestraVentanaLoginWebService();
        } else {
            loginFromProyecto = false;
            this.consultaProyectosSiaproyWeb();
        }
    }

    public void SincronizaActividades() throws Exception {
        HashMap<String, String> parametrosHsmBDDLocal = new HashMap<>();
        HashMap<String, Object> parametrosHsm;
        ObservableList<SpdReportesActividadesModel> registros;
        ObservableList<String> siaproyWeb;
        String jsonParams, result, records = "";
        try {
            if (loginSiaproWeb == false) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("SPPRYF12. Sincronizar actividades");
                alert.setHeaderText("Información incompleta.");
                alert.setContentText("Establezca un colaborador.");
                alert.showAndWait();
            } else {
                ConsultaSpdReportesActividadesStore store = new ConsultaSpdReportesActividadesStore();
                parametrosHsm = new HashMap<>();
                parametrosHsm.put("cascada", "consultaActividadesSinProyecto");
                registros = store.consultaActividades(parametrosHsm);
                if (registros.size() > 0) {
                    String fechas = "";
                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                    LocalDate fecha = LocalDate.now();
                    for (int x = 0; x < registros.size(); x++) {
                        fecha = fecha.parse(registros.get(x).getFecha());
                        fechas += format.format(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant())) + "\n";
                    }
                    Alert alertFechas = new Alert(AlertType.INFORMATION);
                    alertFechas.setTitle("SPPRYF12. Sincronizar actividades");
                    alertFechas.setHeaderText("Información incompleta");
                    alertFechas.setContentText("Existen registros sin un proyecto y actividad en los siguientes días:\n" + fechas + "\nActualice su información e intente nuevamente.");
                    alertFechas.showAndWait();
                } else {
                    parametrosHsm.replace("cascada", "consultaActividadesSinSincronizar");
                    registros = store.consultaActividades(parametrosHsm);
                    if (registros.size() > 0) {
                        String record;
                        records = "[";
                        for (int x = 0; x < registros.size(); x++) {
                            record = "{idProyColPlanAct: '" + registros.get(x).getidProyColPlanAct() + "',"
                                    + "idReporteColaborador: '" + registros.get(x).getIdReporteColaborador() + "',"
                                    + "fecha: '" + registros.get(x).getFecha() + "',"
                                    + "descripcion: '" + registros.get(x).getDescripcion() + "',"
                                    + "duracion: '" + registros.get(x).getDuracion() + "',"
                                    + "horaInicio: '" + registros.get(x).getHoraInicio() + "',"
                                    + "horaFin: '" + registros.get(x).getHoraFin() + "',"
                                    + "avance: '" + registros.get(x).getAvance() + "',"
                                    + "usuario: '" + registros.get(x).getUsuario() + "'},";
                            records = records + record;
                        }
                        records = records.substring(0, records.length() - 1);
                        records = records + "]";
                        parametrosHsmBDDLocal.put("registrosBDLocal", records);
                        jsonParams = gson.toJson(parametrosHsmBDDLocal);

                        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service service = new mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service();
                        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs port = service.getRegistroActividadesWsPort();
                        result = port.cargaRegistro(jsonParams, "insertaRegistrosFromSiaproyDesktop");
                        if (result != null) {
                            if (result.contains("exitosa")) {
                                ActualizaSpdReportesActividadesStore storeUpdate = new ActualizaSpdReportesActividadesStore();
                                parametrosHsm.replace("cascada", "actualizaSincronizadoSiaproy");
                                storeUpdate.actualizaActividades(parametrosHsm);
                                consultaActividades();
                                loading.close();
                            } else {
                                loading.close();
                                GeneraCuadroMensaje.error("Ocurrió un problema en la sincronización." + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: SincronizaActividades");
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            loading.close();
            GeneraCuadroMensaje.error("Ocurrió un problema en la sincronización.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: SincronizaActividades");
        }

    }

    public void muestraVentanaLoginWebService() {
        FXMLLoader ventanaInicioSesion = new FXMLLoader(getClass().getResource("/frontEnd/view/SPPRYF12AView.fxml"));
        AnchorPane root;
        Stage ventanaInicio;
        Scene scene;
        try {
            root = ventanaInicioSesion.load();
            SPPRYF12AController controller = ventanaInicioSesion.getController();
            controller.setControllerPrincipal(this);
            ventanaInicio = new Stage();
            scene = new Scene(root);
            ventanaInicio.setScene(scene);
            ventanaInicio.getIcons().add(new Image("/frontEnd/images/SIAPROY1_icono.jpg"));
            ventanaInicio.setTitle("SPPRYF12A. Consulta usuario");
            controller.setStage(ventanaInicio);
            ventanaInicio.show();
        } catch (IOException ex) {
            GeneraCuadroMensaje.error("Ocurrió un problema desconocido.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: muestraVentanaLoginWebService");
        }
    }

    public void consultaProyectosSiaproyWeb() {
        HashMap<String, String> parametrosHsm = new HashMap<>();
        String result;
        String jsonParams;
        parametrosHsm.put("cveColaborador", claveUsuarioSiaproy);
        parametrosHsm.put("conexionFromSiaproyDesktop", "true");
        jsonParams = gson.toJson(parametrosHsm);
        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service service = new mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service();
        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs port = service.getRegistroActividadesWsPort();
        try {
            result = port.cargaRegistro(jsonParams, "consultaProyectosPorColaborador");
            if (result != null) {
                datosComboProyecto = FXCollections.observableArrayList();
                String[] proyectos = result.substring(1, result.length() - 1).split("\",");
                for (int x = 0; x < proyectos.length; x++) {
                    if (!proyectos[x].equals("")) {
                        if (x != proyectos.length - 1) {
                            datosComboProyecto.add(proyectos[x].substring(1, proyectos[x].length()));
                        } else {
                            datosComboProyecto.add(proyectos[x].substring(1, proyectos[x].length() - 1));
                        }
                    }
                }
                colProyecto.setEditable(true);
                colActividades.setEditable(true);
                loading.close();
            }
        } catch (Exception ex) {
            loading.close();
            GeneraCuadroMensaje.error("Ocurrió un problema al consultar los proyectos.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: consultaProyectosSiaproyWeb");
        }
    }

    public void consultaActividadesSiaproyWeb(String proyecto) {
        HashMap<String, String> parametrosHsm = new HashMap<>();
        String result;
        String jsonParams;
        if (proyecto != null) {
            if (!proyecto.equals("")) {
                parametrosHsm.put("cveColaborador", claveUsuarioSiaproy);
                parametrosHsm.put("conexionFromSiaproyDesktop", "true");
                parametrosHsm.put("idProyecto", proyecto.split("-")[0].trim());
                jsonParams = gson.toJson(parametrosHsm);
                mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service service = new mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service();
                mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs port = service.getRegistroActividadesWsPort();
                try {
                    result = port.cargaRegistro(jsonParams, "consultaActividadesPorColaborador");
                    if (result != null) {
                        datosComboActividades = FXCollections.observableArrayList();
                        String[] actividades = result.substring(1, result.length() - 1).split("\",");
                        for (int x = 0; x < actividades.length; x++) {
                            if (!actividades[x].equals("")) {
                                if (x != actividades.length - 1) {
                                    datosComboActividades.add(actividades[x].substring(1, actividades[x].length()));
                                } else {
                                    datosComboActividades.add(actividades[x].substring(1, actividades[x].length() - 1));
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    GeneraCuadroMensaje.error("Ocurrió un problema al consultar las actividades.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: consultaActividadesSiaproyWeb");
                }
            }
        }
    }

    public void estableceColaboradorSiaproyWeb() {
        this.muestraVentanaLoginWebService();
    }

    public void consultaColaboradorSiaproyWeb(Stage ventana, String claveUsuario) {
        HashMap<String, String> parametrosHsm = new HashMap<>();
        String jsonParams;
        String result;
        claveUsuarioSiaproy = claveUsuario;
        ObservableList<String> colaborador = FXCollections.observableArrayList();
        parametrosHsm.put("cveColaborador", claveUsuarioSiaproy);
        parametrosHsm.put("conexionFromSiaproyDesktop", "true");
        jsonParams = gson.toJson(parametrosHsm);

        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service service = new mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs_Service();
        mx.com.lobos.RegistroActividadesWs.RegistroActividadesWs port = service.getRegistroActividadesWsPort();
        try {
            result = port.cargaRegistro(jsonParams, "consultaColaboradorSiaproyWeb");
            if (!result.contains("Reportar")) {
                String[] col = result.substring(1, result.length() - 1).split(",");
                for (int x = 0; x < col.length; x++) {
                    if (!col[x].equals("")) {
                        colaborador.add(col[x].substring(1, col[x].length() - 1));
                    }
                }
            }
            datosComboProyecto = FXCollections.observableArrayList();
            datosComboActividades = FXCollections.observableArrayList();
            if (colaborador.isEmpty()) {
                loginSiaproWeb = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SPPRYF12A. Login a SIAPROY WEB");
                alert.setHeaderText("INICIO DE SESION FALLIDO");
                alert.setContentText("Los datos de sesión son incorrectos. Verifique su información.");
                alert.showAndWait();
            } else {
                ventana.close();
                loginSiaproWeb = true;
                if (loginFromProyecto) {
                    loginFromProyecto = false;
                    this.consultaProyectosSiaproyWeb();
                }
            }
        } catch (Exception ex) {
            GeneraCuadroMensaje.error("La aplicación SIAPROY WEB no está en línea. Consulte al administrador.\n" + "\nERROR: " + ex.toString() + "\n\nCLASE: SPPRYF12Controller. \nMÉTODO: consultaColaboradorSiaproyWeb");
        }
    }

}

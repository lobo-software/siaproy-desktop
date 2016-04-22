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
   

 */
package frontEnd.controller;

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
import javafx.scene.layout.BorderPane;
import frontEnd.model.Actividades;
import frontEnd.model.stopWatch.Stopwatch;
import frontEnd.util.SialComboCellFactory;
import frontEnd.util.SialStopWatchCellFactory;
import frontEnd.util.SialStringCellFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private BorderPane layoutSecundario;
    @FXML
    private TableView<Actividades> grdActividades;
    @FXML
    private TableColumn<Actividades, Stopwatch> colTimer;
    @FXML
    private TableColumn<Actividades, String> colProyecto;
    @FXML
    private TableColumn<Actividades, String> colActividades;
    @FXML
    private TableColumn<Actividades, String> colTiempo;
    @FXML
    private TableColumn<Actividades, String> colDescripcion;
    @FXML
    private TableColumn<Actividades, String> colInicio;
    @FXML
    private TableColumn<Actividades, String> colFin;
    @FXML
    private TextField tfTiempoTotal;
    @FXML
    private TextField tfTiempoInicio;
    @FXML
    private TextField tfTiempoFin;
    @FXML
    private TableColumn<Actividades, String> colAvance;
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
    // VARIABLES 
    private ObservableList<String> datosComboProyecto;
    private ObservableList<String> datosComboActividades;
    private final Font fuenteReloj = Font.loadFont(Stopwatch.class.getResource("digital-7_mono.ttf").toExternalForm(), 24);
    private static boolean running;
    private static TableView<Actividades> grid;
    private static int posicionTimer;
    private static Stage primaryStage;
    public static DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        layoutSecundario.setRight(popupContent);
        datePicker = dtfFechaActual;
        dtfFechaActual.valueProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println("consulta actividades");
//            if (contadorCambioDia == 0) {
//                SPPRYF12Controller.oldValue = oldValue;
//                SPPRYF12Controller.newValue = newValue;
//                this.cambioDia();
//            } else {
//                contadorCambioDia = 0;
//            }
        });
        muestraFecha();
        //Store proyectos
        datosComboProyecto
                = FXCollections.observableArrayList(
                        "DMS_2014",
                        "DMS_2015",
                        "DMS_2016");
        //Store actividades.
        datosComboActividades
                = FXCollections.observableArrayList(
                        "INVESTIGACION",
                        "REUNIÓN DE ESTÁNDARES",
                        "APOYO AL EQUIPO DE TRABAJO");

        SialStringCellFactory<Actividades, String> textFieldCell = new SialStringCellFactory<>();
        SialComboCellFactory<Actividades, String> comboBoxCell = new SialComboCellFactory<>();
        SialStopWatchCellFactory<Actividades, Boolean> timerCell = new SialStopWatchCellFactory<>();

        colTimer.setCellValueFactory(new PropertyValueFactory<>("stopWatch"));
//        colTimer.setCellFactory(timerCell.creaTimer(tfTotal, tftiempoInicio, tfTiempoFin));
        colProyecto.setCellValueFactory(new PropertyValueFactory<>("proyecto"));
        colProyecto.setCellFactory(comboBoxCell.creaComboBox(datosComboProyecto));
        colProyecto.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    String[] clave = t.getNewValue().split("-");

                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setProyecto(clave[0]);

                    System.out.println("Carga combo secundario (parametro):" + t.getNewValue());
                });
        colActividades.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colActividades.setCellFactory(comboBoxCell.creaComboBoxWithCascade(datosComboProyecto, datosComboActividades));
        colActividades.setOnEditStart((TableColumn.CellEditEvent<Actividades, String> t) -> {
            String proyecto = ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .getProyecto();

            System.out.println("Carga combo secundario (parametro):" + proyecto);
        });
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempoTotal"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("tiempoInicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("tiempoFin"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(textFieldCell.creaTextField());
        colDescripcion.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setDescripcion(t.getNewValue());

                });

        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colAvance.setCellFactory(textFieldCell.creaTextField());
        colAvance.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setAvance(t.getNewValue());
                });
        //Carga de registros de actividades
        grid = grdActividades;
        grdActividades.setItems(cargaRegistrosExistentes());
        grdActividades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue muestra, Object valorViejo, Object valorNuevo) {
                muestraInfActividades();
            }
        });
        setTotalRegistros();
    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada.toUpperCase());
    }

    public ObservableList<Actividades> cargaRegistrosExistentes() {
        ObservableList<Actividades> registros = FXCollections.observableArrayList();
        registros.add(new Actividades(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin), "DMS_2014", "INVESTIGACION", "02:10:00", "09:05:00", "11:15:00", "INVESTIGACION JAVAFX", "100"));
        registros.add(new Actividades(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin), "DMS_2014", "REUNIÓN DE ESTÁNDARES", "01:05:00", "11:16:00", "12:21:00", "REUNION DMS", "100"));
        registros.add(new Actividades(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin), "DMS_2015", "APOYO AL EQUIPO DE TRABAJO", "04:20:00", "12:22:00", "16:42:00", "APOYO A SVA", "100"));
        registros.add(new Actividades(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin), "DMS_2016", "DOCUMENTACIÓN", "00:10:00", "16:43:00", "16:53:00", "REALIZACION DE MANUALES", "100"));
        return registros;
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
                    eliminaActividadesAction(grid.getItems(), grid.getSelectionModel().getSelectedItems());
                }
//                else if (operacion == 2) { //cambiar dia
//                    cambioDiaAction(grid.getItems(), grid.getSelectionModel().getSelectedItems());
//                    System.out.println("Descarta cambios de: " + oldValue + " Consulta actividades dia: " + newValue);
//                }
            }
            ventana.close();
        }
    }

    public static TableView<Actividades> getTableView() {
        return grid;
    }

    public static void setPosicionTimer(int posicion) {
        posicionTimer = posicion;
    }

    public static int getPosicionTimer() {
        return posicionTimer;
    }

    public void agregaActividades() {
        Actividades actividades = new Actividades();
        actividades.setStopWatch(new Stopwatch(tfTiempoTotal, tfTiempoInicio, tfTiempoFin));
        actividades.setDescripcion(tfDescripcion.getText());
        actividades.setTiempoTotal("00:00:00");
        actividades.setTiempoInicio("00:00:00");
        actividades.setTiempoFin("00:00:00");
        if (grdActividades.getSelectionModel().getSelectedItems().size() > 0) {
            grdActividades.getSelectionModel().getSelectedItem().getStopWatch().startStop.fire();
        }
        grdActividades.getItems().add(0, actividades);
        actividades.getStopWatch().startStop.fire();
        setTotalRegistros();

    }

    public void muestraInfActividades() {
        ObservableList<Actividades> muestraInfo = grid.getSelectionModel().getSelectedItems();
        tfDescripcion.textProperty().bind(muestraInfo.get(0).descripcionProperty());
        tfTiempoTotal.textProperty().bind(muestraInfo.get(0).tiempoTotalProperty());
        tfTiempoInicio.textProperty().bind(muestraInfo.get(0).tiempoInicioProperty());
        tfTiempoFin.textProperty().bind(muestraInfo.get(0).tiempoFinProperty());
    }

    public void eliminaActividades() {
        ObservableList<Actividades> allRecords = grdActividades.getItems();
        ObservableList<Actividades> registro = grdActividades.getSelectionModel().getSelectedItems();
        List registroSeleccionado = new ArrayList(registro);
        if (!registro.get(0).getStopWatch().getCurrentStatus()) {
            eliminaActividadesAction(allRecords, registro);
        } else {
            alertActividades(primaryStage, "SPPRYF12", "ELIMINAR ACTIVIDAD", 1);
        }
        setTotalRegistros();

    }
//    public void cambioDia() {
//        ObservableList<Actividades> allRecords = grdActividades.getItems();
//        ObservableList<Actividades> registro = grdActividades.getSelectionModel().getSelectedItems();
//        List registroSeleccionado = new ArrayList(registro);
//        if (registro.size() > 0) {
//            if (registro.get(0).getStopWatch().getCurrentStatus()) {
//                alertActividades(primaryStage, "SPPRYF12", "CAMBIAR DE DIA", 2);
//            }
//        } else {
//            System.out.println("Consulta actividades dia" + newValue);
//        }
//    }

    private static void eliminaActividadesAction(ObservableList<Actividades> allRecords, ObservableList<Actividades> registroSeleccionado) {
        List selectedRecord = new ArrayList(registroSeleccionado);
        if (registroSeleccionado.get(0).getStopWatch().getCurrentStatus()) {
            registroSeleccionado.get(0).getStopWatch().startStop.fire();
        }
        allRecords.removeAll(selectedRecord);
    }

    public void setTotalRegistros() {
        lbTotalRegistros.setText(String.valueOf(grid.getItems().size()));
    }

//    public static void cambioDiaAction(ObservableList<Actividades> allRecords, ObservableList<Actividades> registroSeleccionado) {
//        contadorCambioDia++;
//        datePicker.setValue(newValue);
//        registroSeleccionado.get(0).getStopWatch().startStop.fire();
//    }
    public void setTiempoTotal() {
        lbTiempoTotal.setText(String.valueOf(grid.getItems().size()));
    }
}

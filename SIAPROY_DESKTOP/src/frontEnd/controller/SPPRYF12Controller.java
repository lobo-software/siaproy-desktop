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
import frontEnd.util.SialStringCellFactory;
import java.util.Optional;
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
    private TextField tfTotal;
    @FXML
    private TextField tftiempoInicio;
    @FXML
    private TextField tfFinActividad;
    @FXML
    private TableColumn<Actividades, String> colAvance;
    @FXML
    private TextField tfMostrarDescripcion;
    @FXML
    private Button btnStarStop;
    @FXML
    private Button btnAgragarAtividad;
    @FXML
    private HBox hboxBotonPlayAgregar;
    // VARIABLES 
    private ObservableList<String> datosComboProyecto;
    private ObservableList<String> datosComboActividades;
    private final Font fuenteReloj = Font.loadFont(Stopwatch.class.getResource("digital-7_mono.ttf").toExternalForm(), 24);
    private static boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTotal.setText("00:00:00");
        tfTotal.setFont(fuenteReloj);
        tftiempoInicio.setText("00:00:00");
        tftiempoInicio.setFont(fuenteReloj);
        tfFinActividad.setText("00:00:00");
        tfFinActividad.setFont(fuenteReloj);
        AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
        Stopwatch stopWatch = new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad);
        stopWatch.startStop.setMinWidth(61);
        stopWatch.startStop.setMinHeight(65);
        hboxBotonPlayAgregar.getChildren().removeAll(btnStarStop, btnAgragarAtividad);
        hboxBotonPlayAgregar.getChildren().addAll(stopWatch, btnAgragarAtividad);
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutSecundario.setRight(popupContent);
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
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
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
        colTimer.setCellValueFactory(new PropertyValueFactory<>("stopWatch"));
        //Carga de registros de actividades
        grdActividades.setItems(cargaRegistrosExistentes());
    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada);
    }

    public void muestraActividadesRegistradas() {
        ObservableList<Actividades> muestraInfoActividad = grdActividades.getSelectionModel().getSelectedItems();
    }

    public ObservableList<Actividades> cargaRegistrosExistentes() {
        ObservableList<Actividades> registros = FXCollections.observableArrayList();
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), "DMS_2014", "INVESTIGACION", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), "DMS_2014", "REUNIÓN DE ESTÁNDARES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), "DMS_2015", "APOYO AL EQUIPO DE TRABAJO", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), "DMS_2016", "DOCUMENTACIÓN", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));

        return registros;
    }
    
    public static void setBanderaEjecucion(boolean bandera) {
        running = bandera;
    }
    public static boolean getBanderaEjecucion() {
        return running;
    }

    public static void cierraAplicacion(Stage primaryStage) {
        if (running) {
            Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
            ventana.setTitle("SPPRYF12");
            ventana.setHeaderText("CERRAR APLICACIÓN");
            ventana.setContentText("Existe una actividad en ejecución. Si continúa perderá los cambios realizados. ¿Desea continuar?");
            Optional<ButtonType> seleccion = ventana.showAndWait();
            if (seleccion.get() == ButtonType.OK) {
                primaryStage.close();
            }
            ventana.close();
        }
    }
}

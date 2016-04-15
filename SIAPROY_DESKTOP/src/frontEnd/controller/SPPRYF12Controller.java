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
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
<<<<<<< HEAD
    public boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Layout Principal Border Pane .
        //Border Pane 2
        // Se  cargan los componentes en el BodrerPane 2
        //Se empieza con la codificacion en  boton del StopWatch.
        //Configuración del TextField Total
=======

    @Override
    public void initialize(URL url, ResourceBundle rb) {
>>>>>>> refs/remotes/origin/master
        tfTotal.setText("00:00:00");
        tfTotal.setFont(fuenteReloj);
        tftiempoInicio.setText("00:00:00");
        tftiempoInicio.setFont(fuenteReloj);
<<<<<<< HEAD
        //Configuración del Final
        tfFinActividad.setText("00:00:00");
        tfFinActividad.setFont(fuenteReloj);
        ///Se a agrega esta linea de código para poner el icono de playStop en el boton.     
        AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
        //Seañade la fucnionalidad para mandar llamar la Clase Stopwatch, y  se añaden los parametros en el metdo contructor.
=======
        tfFinActividad.setText("00:00:00");
        tfFinActividad.setFont(fuenteReloj);
        AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
>>>>>>> refs/remotes/origin/master
        Stopwatch stopWatch = new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad);
        stopWatch.startStop.setMinWidth(61);
        stopWatch.startStop.setMinHeight(65);
        hboxBotonPlayAgregar.getChildren().removeAll(btnStarStop, btnAgragarAtividad);
        hboxBotonPlayAgregar.getChildren().addAll(stopWatch, btnAgragarAtividad);
<<<<<<< HEAD
        //Se carga inicializa el componente del DatePicker
=======
>>>>>>> refs/remotes/origin/master
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutSecundario.setRight(popupContent);
        muestraFecha();
<<<<<<< HEAD
        //Se crea el comboBox para la columna de Proyecto en  el TableView.     
        typProyectos
                = FXCollections.observableArrayList(
                        new Typ("DMS_2014"),
                        new Typ("DMS_2015"),
                        new Typ("DMS_2016"));
        //Se crea el comboBox para la columna de Acttividades en  el TableView.
        typActividades
                = FXCollections.observableArrayList(
                        new Typ("INVESTIGACION"),
                        new Typ("REUNIÓN DE ESTÁNDARES"),
                        new Typ("APOYO AL EQUIPO DE TRABAJO"));

        //Confguración  de la Tabla Actividades
        grdActividades.setEditable(true);
        //Instanciación de las  colunas donde vinenen los dos Combo Boexes
        Callback<TableColumn<Actividades, String>, TableCell<Actividades, String>> cellFactory
                = (TableColumn<Actividades, String> param) -> new EditingCell();//Cronstrucctor
        Callback<TableColumn<Actividades, Typ>, TableCell<Actividades, Typ>> comboBoxCellFactory
                = (TableColumn<Actividades, Typ> param) -> new ComboBoxEditingCell(typProyectos);
        ////Iniciar  con la columnaProyecto.
        colProyecto.setCellValueFactory(cellData -> cellData.getValue().getProyectoProperty()); // Cambiar biar los  valores 
        colProyecto.setCellFactory(comboBoxCellFactory);
        //Iniciar  la coluna Actividades.
        comboBoxCellFactory = (TableColumn<Actividades, Typ> param) -> new ComboBoxEditingCell(typActividades);
        colActividades.setCellValueFactory(cellData -> cellData.getValue().getActividadProperty());
        colActividades.setCellFactory(comboBoxCellFactory);

        //Se inicia con la codificación de la columna de Tiempo.
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo")); // Cambiar biar los  valores 
        colTiempo.setCellFactory(cellFactory);
        colTiempo.setOnEditCommit(
=======
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
>>>>>>> refs/remotes/origin/master
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

<<<<<<< HEAD
        ////Iniciar  con la columna  Avance
=======
>>>>>>> refs/remotes/origin/master
        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colAvance.setCellFactory(textFieldCell.creaTextField());
        colAvance.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setAvance(t.getNewValue());
                });
<<<<<<< HEAD
        colTimer.setCellValueFactory(new PropertyValueFactory<>("stopWatch"));
        //Carga de registros de actividades
        grdActividades.setItems(cargaRegistrosExistentes());
    }
//Creación  de los diferentes  métodos. //Metodo donde para mostrar el datePicker de manera Statica al momento de ejecutar la API.
=======
        //Carga de registros de actividades
        grdActividades.setItems(cargaRegistrosExistentes());
    }
>>>>>>> refs/remotes/origin/master

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
<<<<<<< HEAD
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), typProyectos.get(0), typActividades.get(0), "12:10:34", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), typProyectos.get(1), typActividades.get(1), "13:10:45", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), typProyectos.get(2), typActividades.get(2), "09:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad), typProyectos.get(1), typActividades.get(2), "09:50:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        return registros;
    }

    public void agregaActividades() {
        Actividades actividades = new Actividades();
        
        actividades.setDescripcion(tfMostrarDescripcion.getText());
        grdActividades.getItems().add(actividades);

        Stopwatch stopWatch = new Stopwatch(tfTotal, tftiempoInicio, tfFinActividad);
//          stopWatch.startStop.setMinWidth(61);
//          stopWatch.startStop.setMinHeight(65);
        stopWatch.startStop.fire();

    }
    
    public void setBanderaEjecucion(boolean bandera){
        running = bandera;
    }
    
    public void pruebaSalida(){
        if(running){
            System.out.println("No cerrar. Alerta");
        }else{
            System.out.println("cerrar pantalla");
        }
    }
=======
        registros.add(new Actividades("DMS_2014", "INVESTIGACION", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2014", "REUNIÓN DE ESTÁNDARES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2015", "APOYO AL EQUIPO DE TRABAJO", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "DOCUMENTACIÓN", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));

        return registros;
    }
>>>>>>> refs/remotes/origin/master
}

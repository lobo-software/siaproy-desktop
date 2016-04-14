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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import frontEnd.model.Actividades;
import frontEnd.model.ComboBoxEditingCell;
import frontEnd.model.EditingCell;
import frontEnd.model.Typ;
import frontEnd.model.stopWatch.Stopwatch;
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
    private TableColumn<Actividades, Typ> colProyecto;
    @FXML
    private TableColumn<Actividades, Typ> colActividades;
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
    private ObservableList<Typ> typProyectos;
    private ObservableList<Typ> typActividades;
    private final Font fuenteReloj = Font.loadFont(Stopwatch.class.getResource("digital-7_mono.ttf").toExternalForm(), 24);
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //Layout Principal Border Pane .
         //Border Pane 2
        // Se  cargan los componentes en el BodrerPane 2
        //Se empieza con la codificacion en  boton del StopWatch.
         //Configuración del TextField Total
        tfTotal.setText("00:00:00");
        tfTotal.setFont(fuenteReloj);
        //Configuración del Incio
        tftiempoInicio.setText("00:00:00");
        tftiempoInicio.setFont(fuenteReloj);
       //Configuración del Final
        tfFinActividad.setText("00:00:00");
        tfFinActividad.setFont(fuenteReloj);
       ///Se a agrega esta linea de código para poner el icono de playStop en el boton.     
         AwesomeDude.setIcon(btnStarStop, AwesomeIcon.PLAY);
         //Seañade la fucnionalidad para mandar llamar la Clase Stopwatch, y  se añaden los parametros en el metdo contructor.
          Stopwatch stopWatch = new Stopwatch(tfTotal,tftiempoInicio,tfFinActividad);   
          stopWatch.startStop.setMinWidth(61);
          stopWatch.startStop.setMinHeight(65);
        hboxBotonPlayAgregar.getChildren().removeAll(btnStarStop, btnAgragarAtividad);
        hboxBotonPlayAgregar.getChildren().addAll(stopWatch, btnAgragarAtividad);        
          //Se carga inicializa el componente del DatePicker
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutSecundario.setRight(popupContent);
        muestraFecha();
        //SE Agregan locomponentes del Layout Principal el cuál contiene  la Tabla Vista.
        //Se agrega el comboBox para la columna de Proyecto en  el TableView.       
           // Se  cargan los componentes en el BodrerPane.
                  //CREACIÓN DE  LAS COLUMNAS DEL TABLEVIEW

         //Implementación  del cronometro en la primera colmnna de TV.
         //Se  crean dos listas observables las cuales  contendran información 
         //que se mostrarade manera estática en los combox.
        typProyectos
                = FXCollections.observableArrayList(
                        new Typ("DMS_2014"),
                        new Typ("DMS_2015"),
                        new Typ("DMS_2016"));
        //Se agrega el comboBox para la columna de Acttividades en  el TableView.
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
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setTiempo(t.getNewValue());
                });

        ////Iniciar  la descripción columna Descripción.
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion")); // Cambiar biar los  valores 
        colDescripcion.setCellFactory(cellFactory);
        colDescripcion.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setDescripcion(t.getNewValue());
                });
      
         ////Iniciar  con la columna  Avance
        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colAvance.setCellFactory(cellFactory);
        colAvance.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setPorcentajeAvance(t.getNewValue());
                });
     //Carga de registros de actividades
        grdActividades.setItems(cargaRegistrosExistentes());
    }
//Creación  de los diferentes  métodos. //Metodo donde para mostrar el datePicker de manera Statica al momento de ejecutar la API.
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
        registros.add(new Actividades(typProyectos.get(0), typActividades.get(0), "12:10:34", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(typProyectos.get(1), typActividades.get(1), "13:10:45", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(typProyectos.get(2), typActividades.get(2), "09:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades(typProyectos.get(1), typActividades.get(2), "09:50:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        return registros;
    }
}
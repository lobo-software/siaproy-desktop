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
 */
package controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.Actividades;
import model.EditingCell;

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
    private TableColumn<Actividades, String> colCronometro;
    @FXML
    private TableColumn<Actividades, String> colProyecto;
    @FXML
    private TableColumn<Actividades, String> colActividades;
    @FXML
    private TableColumn<Actividades, String> colTiempo;
    @FXML
    private TableColumn<Actividades, String> colDescripcion;

    @FXML
    private TableColumn<Actividades, String> colAvance;

    @FXML
    private TextField tfMostrarDescripcion;
    
      private String MostrarDescripcion;
    private Object Atividades;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        lbFecha.setText("Hello World!");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutSecundario.setRight(popupContent);
        muestraFecha();
        
//           col<Person, String> firstNameCol = new TableColumn("Vorname");
//        firstNameCol.setMinWidth(100);
//        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
//        firstNameCol.setCellFactory(cellFactory);
//        firstNameCol.setOnEditCommit(
//                (TableColumn.CellEditEvent<Person, String> t) -> {
//                    ((Person) t.getTableView().getItems()
//                    .get(t.getTablePosition().getRow()))
//                    .setFirstName(t.getNewValue());
//
//                });
     
       grdActividades.setEditable(true);
        Callback<TableColumn<Actividades, String>, TableCell<Actividades, String>> cellFactory
                = (TableColumn<Actividades, String> param) -> new EditingCell();
       
      
        colProyecto.setCellValueFactory(new PropertyValueFactory<>("proyecto"));
        
//        colActividades.setCellValueFactory(new PropertyValueFactory<>("actividad"));
//        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));

       ////Iniciar  con la columna  Avance
//        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
       colAvance.setCellValueFactory(cellData-> cellData.getValue().porcentajeAvanceProperty());
       colAvance.setCellFactory(cellFactory);
       colAvance.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setporcentajeAvance(t.getNewValue());

                });
        
        ////Iniciar  la descripción columna
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        colDescripcion.setCellFactory(cellFactory);
        colDescripcion.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setDescripcion(t.getNewValue());

                });
        grdActividades.setItems(cargaRegistrosExistentes());

    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada);
    }

    public void agregarActividad() {
    }
    

    public void muestraActividadesRegistradas() {
        ObservableList<Actividades> muestraInfoActividad = grdActividades.getSelectionModel().getSelectedItems();
         
    }
 

    public ObservableList<Actividades> cargaRegistrosExistentes() {
        ObservableList<Actividades> registros = FXCollections.observableArrayList();
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        return registros;
    }
    
    
}

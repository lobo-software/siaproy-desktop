package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Usagi
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
        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
//        colAvance.setCellValueFactory(cellData -> cellData.getValue().setAvance());
//        colAvance.cellFactoryProperty()
//        colAvance.setOnEditCommit(TableColumn.CellEditEvent<Actividades,String> t) ->{
//        (Actividades) t.getTableView().getItems()
//                .get(t.getTablePosition().getRow())
//                .setDescripcion(t));
//    };
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

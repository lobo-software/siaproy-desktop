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

/**
 *
 * @author Usagi
 */
public class FXMLDocumentController implements Initializable {

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
        
//        Callback<TableColumn<Person, Date>, TableCell<Person, Date>> dateCellFactory
//                = (TableColumn<Person, Date> param) -> new DateEditingCell();
//        Callback<TableColumn<Person, Typ>, TableCell<Person, Typ>> comboBoxCellFactory
//                = (TableColumn<Person, Typ> param) -> new ComboBoxEditingCell();
//       colCronometro.setCellValueFactory(new PropertyValueFactory<>("cronometro"));
//       colCronometro.setMinWidth(100);
//      colCronometro.setCellValueFactory(cellData -> cellData.getValue().CronometroProperty());
       
      
        colProyecto.setCellValueFactory(new PropertyValueFactory<>("proyecto"));
//        colProyecto.
        
        colActividades.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        colAvance.setCellValueFactory(new PropertyValueFactory<>("avance"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDescripcion.setCellFactory(cellFactory);
        colDescripcion.setOnEditCommit(
                (TableColumn.CellEditEvent<Actividades, String> t) -> {
                    ((Actividades) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setDescripcion(t.getNewValue());

                });
        grdActividades.setItems(cargaRegistrosExistentes());

////        //Se iniializa  aquí  el grid para  poder  mostrar  info y  hacr mas acciones  simpre  se  inicializan  en este metodo....
////        grdActividades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
////            public void changed(ObservableValue muetra, Object valorViejo, Object valorNuevo) {
////                muestraInfEmpleados();
//            }
//        });
//
//    
    }

    public void muestraFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String fechaFormateada;
        LocalDate fechaActual = dtfFechaActual.getValue();
        fechaFormateada = sdf.format(Date.from(fechaActual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        lbFecha.setText(fechaFormateada);
    }

    public void agregarActividad() {

//        //Metodo 1
//        Actividades actividades= new Actividades();
//    Actividades.setMostrarDescripcion(tfMostrarDescripcion.getText());
    }
//
//    public void eliminaActividad() {
//        ObservableList<Actividades> allRecords = grdActividades.getItems();
//        List registroSeleccionado = new ArrayList(grdActividades.getSelectionModel().getSelectedItems());
//        allRecords.removeAll(registroSeleccionado);
//
//    }
    

    public void muestraActividadesRegistradas() {
        ObservableList<Actividades> muestraInfoActividad = grdActividades.getSelectionModel().getSelectedItems();
//        MostrarDescripcion.setText(muestraInfo.get(0).getMostrarDescripcion());
         
    }
//    
 

    public ObservableList<Actividades> cargaRegistrosExistentes() {
        ObservableList<Actividades> registros = FXCollections.observableArrayList();
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        registros.add(new Actividades("DMS_2016", "REGISTRO ACTIVIDADES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100"));
        return registros;
    }
    
    
    
    class EditingCell extends TableCell<Actividades, String> {

        private TextField textField;

        private EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(item);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
//                        setGraphic(null);
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnAction((e) -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    System.out.println("Commiting " + textField.getText());
                    commitEdit(textField.getText());
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }

    
}

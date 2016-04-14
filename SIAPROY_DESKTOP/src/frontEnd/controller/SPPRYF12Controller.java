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
package frontEnd.controller;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
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
import frontEnd.util.SialCheckBoxCellFactory;
import frontEnd.util.SialComboCellFactory;
import frontEnd.util.SialDateCellFactory;
import frontEnd.util.SialStringCellFactory;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.cell.CheckBoxTableCell;

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
    @FXML
    private TableColumn<Actividades, Date> colFecha;
    @FXML
    private TableColumn<Actividades, Boolean> colActivo;

    private ObservableList<String> datosComboProyecto;
    private ObservableList<String> datosComboActividades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Mostrar el DatePicker sin textfield y dar formato a la fecha mostrada en la etiqueta.
        dtfFechaActual.setValue(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin((dtfFechaActual));
        Node popupContent = datePickerSkin.getPopupContent();
        layoutSecundario.setRight(popupContent);
        muestraFecha();
        //Store proyectos
        datosComboProyecto = FXCollections.observableArrayList(
                "DMS_2014 - PROYECTO DE DMS PARA AÑO 2014",
                "DMS_2015",
                "DMS_2016"
        );
        datosComboActividades = FXCollections.observableArrayList();
        //Creación de objetos genéricos para el TableView editable
        SialStringCellFactory<Actividades, String> textFieldCell = new SialStringCellFactory<>();
        SialComboCellFactory<Actividades, String> comboBoxCell = new SialComboCellFactory<>();
        SialDateCellFactory<Actividades, Date> datePickerCell = new SialDateCellFactory<>();
        SialCheckBoxCellFactory<Actividades> checkBoxCell = new SialCheckBoxCellFactory<>();

//        colProyecto.setCellValueFactory(cellData -> cellData.getValue().getProyectoProperty());
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
        colFecha.setCellValueFactory(cellData -> cellData.getValue().getFechaProperty());
        colFecha.setCellFactory(datePickerCell.creaDatePicker());
        colActivo.setCellValueFactory(cellData -> cellData.getValue().getActivoProperty());
//        colActivo.setCellFactory(CheckBoxTableCell.forTableColumn(colActivo));
        colActivo.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxCell.creaCheckBoxOneSelection(true, grdActividades, grdActividades.getItems().get(0).getActivoProperty())));
        
        
//        colActivo.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
//
//            @Override
//            public ObservableValue<Boolean> call(Integer param) {
//                System.out.println("Cours " + grdActividades.getItems().get(param).getActividad() + " changed value to " + grdActividades.getItems().get(param).getActivo());
//                return grdActividades.getItems().get(param).getActivoProperty();
//            }
//        }));
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
        registros.add(new Actividades("DMS_2014", "INVESTIGACION", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "", new Date(), "S"));
        registros.add(new Actividades("DMS_2014", "REUNIÓN DE ESTÁNDARES", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100", new Date(), "N"));
        registros.add(new Actividades("DMS_2015", "APOYO AL EQUIPO DE TRABAJO", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100", new Date(), "A"));
        registros.add(new Actividades("DMS_2016", "DOCUMENTACIÓN", "00:10:00", "CAPTURA ACTIVIDADES 06/ABR/2016", "100", new Date(), "N"));
        return registros;
    }

}

/*
 *  _______________________________________________________________________
 * |                                         COPYRIGHT (C) BY                                                          |
 * |                             DERECHOS RESERVADOS (C) POR                                             |
 * |                                 LOBO SOFTWARE S.A. DE  C.V.                                               |
 * |                                                                                                                                  |
 * |Ninguna parte de esta obra, parcial o total puede                                                          | 
 * |ser reproducida o transmitida, mediante ningun sistema                                                 |
 * |o metodo electronico o mecanico (incluyendo el                                                             |   
 * |fotocopiado, la grabacion, o cualquier sistema de                                                          |  
 * |recuperacion y almacenamiento de informacion),                                                           |  
 * |SIN LA AUTORIZACION POR ESCRITO DEL AUTOR.                                                 |  
 * |                                                                                                                                  |  
 * |Derechos reservados                                                                                                   |  
 * |(C) 2012, LOBO SOFTWARE, S.A. DE C.V. (*)                                                            |  
 * |                                                                                                                                  |  
 * |Esta obra forma parte del SIAL-CH (C) "CAPITAL HUMANO"                                         |  
 * |                                                                                                                                  |  
 * |(*) Marca registrada por                                                                                               |   
 * |LOBO SOFTWARE, S.A. DE C.V.                                                                                |  
 * |_______________________________________________________________________|  
 *  Document     : SialComboCellFactory.java
 * Created on    : 11 Apr 2016 4:37:13 PM
 * Author           : SVA
 * Modifications : 
 */
package frontEnd.util;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 */
public class SialComboCellFactory<E, T> extends TableCell<E, String>{
    private ComboBox<String> comboBox;
    private ObservableList<String> listaCombo;
    private ObservableList<String> listaComboCascada;
    private boolean cascade;
    
   public SialComboCellFactory(){
   } 
    
    public SialComboCellFactory(ObservableList<String> listaCombo, boolean cascade){
        this.listaCombo = listaCombo;
        this.cascade = cascade;
    }
    public SialComboCellFactory(ObservableList<String>listaCombo, ObservableList<String> listaComboCascada, boolean cascade){
        this.listaCombo = listaCombo;
        this.listaComboCascada = listaComboCascada;
        this.cascade = cascade;
    }
    
    public Callback<TableColumn<E, String>, TableCell<E, String>> creaComboBox(ObservableList<String> listaCombo){
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialComboCellFactory(listaCombo, false);
        return callBack;
    }
    public Callback<TableColumn<E, String>, TableCell<E, String>> creaComboBoxWithCascade(ObservableList<String> listaCombo, ObservableList<String> listaComboCascada){
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialComboCellFactory(listaCombo, listaComboCascada, true);
        return callBack;
    }
    
    @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                if(!cascade){
                    createComboBox();
                }else{
                   createComboBoxWithCascade();
                }
                
                setText(null);
                setGraphic(comboBox);
            }
        }

          @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getTplList());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (comboBox != null) {
                        comboBox.setValue(getTplList());
                    }
                    setText(getTplList());
                    setGraphic(comboBox);
                } else {
                    setText(getTplList());
                    setGraphic(null);
                }
            }
        }

        private void createComboBox() {
            comboBox = new ComboBox<>(listaCombo);
            comboBoxConverter(comboBox);
            comboBox.valueProperty().set(getTplList());
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction((e) -> {
                System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });
            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
                }
            });
        }
        private void createComboBoxWithCascade() {
            comboBox = new ComboBox<>(listaComboCascada);
            comboBoxConverter(comboBox);
            comboBox.valueProperty().set(getTplList());
            comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            comboBox.setOnAction((e) -> {
                System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            });
            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (!newValue) {
                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
                }
            });
        }
            
             private void comboBoxConverter(ComboBox<String> comboBox) {
            // Define rendering of the list of values in ComboBox drop down. 
            comboBox.setCellFactory((c) -> {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(getItem());
                        }
                    }
                };
            });
        }
             
             private String getTplList() {
            return getItem() == null ? "" : (getItem().split("-"))[0];
        } 
    
}

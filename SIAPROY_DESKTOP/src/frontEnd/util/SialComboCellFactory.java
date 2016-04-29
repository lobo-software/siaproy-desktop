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
 * Modifications : 29/Abr/2016 17:07 SVA (LOBO_000076): Se restructura clase y mejora funcionalidad.
 */
package frontEnd.util;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 * @param <E>
 * @param <T>
 */
public class SialComboCellFactory<E, T> extends TableCell<E, String> {

    private ComboBox<String> comboBox;
    private ComboBox<String> comboBoxWithCascade;
    private ObservableList<String> listaCombo;
    private ObservableList<String> listaComboCascada;
    private boolean cascade, editable, editableWithCascade;
    private int contador = 0;
    private int row = 0;

    public SialComboCellFactory() {
    }

    public SialComboCellFactory(ObservableList<String> listaCombo, boolean cascade, boolean editable) {
        this.listaCombo = listaCombo;
        this.cascade = cascade;
        this.editable = editable;
    }

    public SialComboCellFactory(ObservableList<String> listaCombo, ObservableList<String> listaComboCascada, boolean cascade, boolean editable) {
        this.listaCombo = listaCombo;
        this.listaComboCascada = listaComboCascada;
        this.cascade = cascade;
        this.editableWithCascade = editable;
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaComboBox(ObservableList<String> listaCombo, boolean editable) {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialComboCellFactory(listaCombo, false, editable);
        return callBack;
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaComboBoxWithCascade(ObservableList<String> listaCombo, ObservableList<String> listaComboCascada, boolean editable) {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialComboCellFactory(listaCombo, listaComboCascada, true, editable);
        return callBack;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            if (!cascade) {
                createComboBox(editable);
            } else {
                createComboBoxWithCascade(editableWithCascade);
            }
            setText(null);
            setGraphic(!cascade ? comboBox : comboBoxWithCascade);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (!cascade) {
                        comboBox.requestFocus();
                    } else {
                        comboBoxWithCascade.requestFocus();
                    }

                }
            });
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(!cascade ? getTplList() : getTplListCascade());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (!cascade) {
                if (comboBox != null) {
                    comboBox.setValue(getTplList());
                }
            }else{
                 if (comboBoxWithCascade != null) {
                    comboBoxWithCascade.setValue(getTplListCascade());
                }
            }
            setText(!cascade ? getTplList() : getTplListCascade());
            setGraphic(!cascade ? comboBox : comboBoxWithCascade);
        } else {
            setText(!cascade ? getTplList() : getTplListCascade());
            setGraphic(null);
        }
    }

    private void createComboBox(boolean editable) {
        comboBox = new ComboBox<>(listaCombo);
        comboBoxConverter(comboBox);
        comboBox.setEditable(editable);
        comboBox.valueProperty().set(getTplList());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
        comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
            }
        });
        comboBox.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
            if (t.getCode() == KeyCode.TAB) {
                commitEdit(comboBox.getValue());
                TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                if (nextColumn != null) {
                    getTableView().edit(row, nextColumn);
                    getTableView().getSelectionModel().select(row);
                }
            } else if (t.getCode() == KeyCode.ENTER) {
                setText(comboBox.getValue());
                setGraphic(null);
            }
        });
        comboBox.getEditor().textProperty().addListener((ov, oldValue, newValue) -> {
            StringProperty registro = (StringProperty) ov;
            ((TextField) registro.getBean()).setText(newValue.toUpperCase());
        });
        AutoCompleteComboBoxListener cbl = new AutoCompleteComboBoxListener(comboBox);
    }

    private void createComboBoxWithCascade(boolean editable) {
        comboBoxWithCascade = new ComboBox<>(listaComboCascada);
        comboBoxConverter(comboBoxWithCascade);
        comboBoxWithCascade.setEditable(editable);
        comboBoxWithCascade.valueProperty().set(getTplListCascade());
        comboBoxWithCascade.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBoxWithCascade.setOnAction((e) -> {
            commitEdit(comboBoxWithCascade.getSelectionModel().getSelectedItem());
        });
        comboBoxWithCascade.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                commitEdit(comboBoxWithCascade.getSelectionModel().getSelectedItem());
            }
        });
        comboBoxWithCascade.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    commitEdit(comboBoxWithCascade.getValue());
                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                    if (nextColumn != null) {
                        getTableView().edit(row, nextColumn);
                        getTableView().getSelectionModel().select(row);
                    }
                } else if (t.getCode() == KeyCode.ENTER) {
                    setText(comboBoxWithCascade.getValue());
                    setGraphic(null);
                }
            }
        });
        comboBoxWithCascade.getEditor().textProperty().addListener((ov, oldValue, newValue) -> {
            StringProperty registro = (StringProperty) ov;
            ((TextField) registro.getBean()).setText(newValue.toUpperCase());
        });
//        AutoCompleteComboBoxListener cbl = new AutoCompleteComboBoxListener(comboBoxWithCascade);

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
    private String getTplListCascade() {
        return getItem() == null ? "" : (getItem().split("-"))[0];
    }

    private TableColumn<E, ?> getNextColumn(boolean forward) {
        List<TableColumn<E, ?>> columns = new ArrayList<>();
        for (TableColumn<E, ?> column : getTableView().getColumns()) {
            columns.addAll(getLeaves(column));
        }
        // There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int currentIndex = columns.indexOf(getTableColumn());
        int nextIndex = currentIndex;
        row = getTableView().getSelectionModel().getSelectedIndex();
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
                if (contador == 0) {
                    contador++;
                    if (row < getTableView().getItems().size() - 1) {
                        row += 1;
                    } else {
                        row = 0;
                    }
                } else {
                    contador = 0;
                }
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
                if (contador == 0) {
                    contador++;
                    if (row > 0) {
                        row -= 1;
                    } else {
                        row = getTableView().getItems().size() - 1;
                    }
                } else {
                    contador = 0;
                }
            }
        }
        return columns.get(nextIndex);
    }

    private List<TableColumn<E, ?>> getLeaves(TableColumn<E, ?> root) {
        List<TableColumn<E, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            // We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (TableColumn<E, ?> column : root.getColumns()) {
                columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }
}

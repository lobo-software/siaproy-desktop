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
 *  Document     : SialTextFieldCellFactory.java
 * Created on    : 19 Apr 2016 5:45:34 PM
 * Author           : SVA
 * Modifications : 29/Abr/2016 17:07 SVA (LOBO_000076): Se restructura la clase y se mejora la funcionalidad.
06/May/2016 09:35 SVA (LOBO_000076): Se añaden regex para el textfield.
 */
package frontEnd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

/**
 *
 * @author Lobo Software
 * @param <E>
 * @param <T>
 */
public class SialTextFieldCellFactory<E, T> extends TableCell<E, String> {

    private TextField textField;
    private int row = 0;
    private boolean regex;
    private int operacion;

    public SialTextFieldCellFactory() {
    }

    public SialTextFieldCellFactory(int operacion) {
        this.operacion = operacion;
        if (operacion > 0) {
            regex = true;
        }
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaTextField(int operacion) {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialTextFieldCellFactory(operacion);
        return callBack;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                    textField.selectAll();
                }
            });
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(item);
            setGraphic(null);
        } else if (isEditing()) {
            if (textField != null) {
                textField.setText(getString());
            }
            setText(null);
            setGraphic(textField);
        } else {
            setText(getString());
            setGraphic(null);
        }
    }

    private void createTextField() {
        Pattern validDoubleText;
        TextFormatter<Double> textFormatter;
        textField = new TextField(getString());
        textField.selectAll();
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> commitEdit(textField.getText()));
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    commitEdit(textField.getText());
                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                    if (nextColumn != null) {
                        getTableView().edit(row, nextColumn);
                        getTableView().getSelectionModel().select(row);
                    }
                }
            }
        });
        if (regex) {
            switch (operacion) {
                case 1://valores numéricos (double)
                    validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
                    textFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0,
                            change -> {
                                String newText = change.getControlNewText();
                                if (validDoubleText.matcher(newText).matches()) {
                                    return change;
                                } else {
                                    return null;
                                }
                            });
                    textField.setTextFormatter(textFormatter);
                    break;
                default:
                    break;
            }
        }
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            StringProperty elemento = (StringProperty) ov;
            ((TextField) elemento.getBean()).setText(newValue.toUpperCase());
        });
        textField.focusedProperty().addListener((ov, olvPropertyValue, newPropertyValue) -> {
            if (olvPropertyValue) { // pierde el foco
                commitEdit(getItem());
            }
        });
//        textField.textProperty().bindBidirectional(textProperty());
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
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
//                if (contador == 0) {
//                    contador++;
                if (row < getTableView().getItems().size() - 1) {
                    row += 1;
                } else {
                    row = 0;
                }
//                } else {
//                    contador = 0;
//                }
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
//                if (contador == 0) {
//                    contador++;
                if (row > 0) {
                    row -= 1;
                } else {
                    row = getTableView().getItems().size() - 1;
                }
//                } else {
//                    contador = 0;
//                }
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

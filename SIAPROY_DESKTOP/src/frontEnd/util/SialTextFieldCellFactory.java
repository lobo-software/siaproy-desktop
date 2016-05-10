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
10/May/2016 13:07 SVA (LOBO_000076): Se añade funcionalidad en commitEdit para pasar a la siguiente columna editable / Se añaden expresiones regulares en el método "creaTextField".
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
    private int row = 0, operacion, longitud;
    private boolean regex, tieneLongitudMaxima;

    public SialTextFieldCellFactory() {//Texto sin regex ni longitud máxima
    }

    public SialTextFieldCellFactory(int operacion) {
        this.operacion = operacion;
        if (operacion > 0) {
            regex = true;
        }
        tieneLongitudMaxima = false;
    }

    public SialTextFieldCellFactory(int operacion, int longitud) {
        this.operacion = operacion;
        if (operacion > 0) {
            regex = true;
        }
        this.longitud = longitud;
        tieneLongitudMaxima = true;
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaTextField() {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialTextFieldCellFactory();
        return callBack;
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaTextField(int operacion) {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialTextFieldCellFactory(operacion);
        return callBack;
    }

    public Callback<TableColumn<E, String>, TableCell<E, String>> creaTextField(int operacion, int longitud) {
        Callback<TableColumn<E, String>, TableCell<E, String>> callBack;
        callBack = (TableColumn<E, String> tableColumn) -> new SialTextFieldCellFactory(operacion, longitud);
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
        final int maxChars;
        final String restictTo;
        if (tieneLongitudMaxima) {
            maxChars = longitud;
        } else {
            maxChars = 0;
        }
        if (regex) {
            switch (operacion) {
                case 1://Claves de usuario
                    restictTo = "[a-zA-Z0-9]";
                    textField = new TextField(getString()) {
                        @Override
                        public void replaceText(int start, int end, String text) {
                            if (matchTest(text)) {
                                super.replaceText(start, end, text);
                            }
                        }

                        @Override
                        public void replaceSelection(String text) {
                            if (matchTest(text)) {
                                super.replaceSelection(text);
                            }
                        }

                        private boolean matchTest(String text) {
                            if (tieneLongitudMaxima) {
                                return text.isEmpty() || (text.matches(restictTo) && getText().length() < maxChars);
                            } else {
                                return text.isEmpty() || (text.matches(restictTo));
                            }
                        }
                    };
                    break;
                case 2: //Descripción sin caracteres especiales
                    restictTo = "[^\"|']";
                    textField = new TextField(getString()) {
                        @Override
                        public void replaceText(int start, int end, String text) {
                            if (matchTest(text)) {
                                super.replaceText(start, end, text);
                            }
                        }

                        @Override
                        public void replaceSelection(String text) {
                            if (matchTest(text)) {
                                super.replaceSelection(text);
                            }
                        }

                        private boolean matchTest(String text) {
                            if (tieneLongitudMaxima) {
                                return text.isEmpty() || (text.matches(restictTo) && getText().length() < maxChars);
                            } else {
                                return text.isEmpty() || (text.matches(restictTo));
                            }
                        }
                    };
                    break;
                case 3: //Descripción con valores númericos (enteros)
                    restictTo = "[0-9]";
                    textField = new TextField(getString()) {
                        @Override
                        public void replaceText(int start, int end, String text) {
                            if (matchTest(text)) {
                                super.replaceText(start, end, text);
                            }
                        }

                        @Override
                        public void replaceSelection(String text) {
                            if (matchTest(text)) {
                                super.replaceSelection(text);
                            }
                        }

                        private boolean matchTest(String text) {
                            if (tieneLongitudMaxima) {
                                return text.isEmpty() || (text.matches(restictTo) && getText().length() < maxChars);
                            } else {
                                return text.isEmpty() || (text.matches(restictTo));
                            }
                        }
                    };
                    break;
                case 4: //Descripción con valores númericos (dobles)
                    textField = new TextField(getString());
                    Pattern validDoubleText = Pattern.compile("^[-+]?\\d{1,10}+(\\.{0,1}(\\d{0,3}))?$");
                    TextFormatter<Double> textFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0,
                            change -> {
                                String newText = getString();
                                if (validDoubleText.matcher(newText).matches()) {
                                    return change;
                                } else {
                                    return null;
                                }
                            });
                    textField.setTextFormatter(textFormatter);
                    break;
                case 5: // Descripcion sin restricción en los caracteres
                    restictTo = "";
                    textField = new TextField(getString()) {
                        @Override
                        public void replaceText(int start, int end, String text) {
                            if (matchTest(text)) {
                                super.replaceText(start, end, text);
                            }
                        }

                        @Override
                        public void replaceSelection(String text) {
                            if (matchTest(text)) {
                                super.replaceSelection(text);
                            }
                        }

                        private boolean matchTest(String text) {
                            if (tieneLongitudMaxima) {
                                return text.isEmpty() || getText().length() < maxChars;
                            } else {
                                return true;
                            }
                        }
                    };
                    break;
                default:
                    break;
            }
        } else {
            textField = new TextField(getString());
        }
        textField.selectAll();
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnAction((e) -> {
            commitEdit(textField.getText());
            TableColumn nextColumn = getNextColumn(true);
            if (nextColumn != null) {
                getTableView().edit(row, nextColumn);
                getTableView().getSelectionModel().select(row);
            }
        });
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

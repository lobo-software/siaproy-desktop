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
 *  Document     : SialCheckBoxCellFactory.java
 * Created on    : 12 Apr 2016 5:34:46 PM
 * Author           : SVA
 * Modifications : 16/Abr/2016 12:45 Se restructura la clase y se añade funcionalidad a través del método setOneSelection para marcar solo una casilla.
18/Abr/2016 10:49 Se creó el método "createCheckHeader" y se eliminó la variable type.
 */
package frontEnd.util;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 * @param <E>
 * @param <T>
 */
public class SialCheckBoxCellFactory<E extends SialCheckBox, T> extends TableCell<E, Boolean> {

    private boolean multipleSelection;
    private CheckBox checkBox;
    private int row = 0;

    public SialCheckBoxCellFactory() {
    }

    public SialCheckBoxCellFactory(boolean multipleSelection) {
        this.multipleSelection = multipleSelection;
    }

    public Callback<TableColumn<E, Boolean>, TableCell<E, Boolean>> creaCheckBoxOneSelection() {
        Callback<TableColumn<E, Boolean>, TableCell<E, Boolean>> callBack;
        callBack = (TableColumn<E, Boolean> tableColumn) -> new SialCheckBoxCellFactory(false);
        return callBack;
    }

    public Callback<TableColumn<E, Boolean>, TableCell<E, Boolean>> creaCheckBoxMultipleSelection() {
        Callback<TableColumn<E, Boolean>, TableCell<E, Boolean>> callBack;
        callBack = (TableColumn<E, Boolean> tableColumn) -> new SialCheckBoxCellFactory(true);
        return callBack;
    }

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            createCheckBox(item);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    checkBox.requestFocus();
                }
            });
        }
    }

    private void createCheckBox(boolean item) {
        checkBox = new CheckBox();
        TableView<E> grid = getTableView();
//        checkBox.setPadding(new Insets(0, this.getWidth() / 2, 0, this.getWidth() / 2));
        checkBox.selectedProperty().bindBidirectional(grid.getItems().get(getIndex()).getCheckedProperty());
        checkBox.setOnMouseClicked(e -> {
            TableRow row = getTableRow();
            if (row != null) {
                int rowIndex = row.getIndex();
                TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();
                if (!multipleSelection) {
                    if (!item) {
                        for (int x = 0; x < getTableView().getItems().size(); x++) {
                            if (x != rowIndex) {
                                grid.getItems().get(x).setOneSelection();
                            }
                        }
                    }
                }
                sm.select(rowIndex);
            }
        });
        checkBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.TAB) {
                    commitEdit(checkBox.isSelected());
                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                    if (nextColumn != null) {
                        getTableView().edit(row, nextColumn);
                        getTableView().getSelectionModel().select(row);
                    }
                }
            }
        });
        setGraphic(checkBox);
    }

    public CheckBox createCheckHeader(TableColumn<E, Boolean> column, TableView<E> grid) {
        CheckBox checkHeader = new CheckBox();
        column.setText("");
        checkHeader.setOnAction(e -> {
            for (int x = 0; x < grid.getItems().size(); x++) {
                grid.getItems().get(x).setAllSelection(checkHeader.isSelected());
            }
        });
        return checkHeader;
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
                if (row < getTableView().getItems().size() - 1) {
                    row += 1;
                } else {
                    row = 0;
                }
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
                if (row > 0) {
                    row -= 1;
                } else {
                    row = getTableView().getItems().size() - 1;
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

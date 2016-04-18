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
 */
package frontEnd.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 */
public class SialCheckBoxCellFactory<E extends SialCheckBox, T> extends CheckBoxTableCell<E, Boolean> {

    private boolean multipleSelection;
    private Class<E> type;

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
            TableRow row = getTableRow();
            if (row != null) {
                int rowIndex = row.getIndex();
                TableView.TableViewSelectionModel sm = getTableView().getSelectionModel();
                TableView<E> grid = getTableView();
                if (!multipleSelection) {
                    if (item) {
                        for (int x = 0; x < getTableView().getItems().size(); x++) {
                            if (x != rowIndex) {
                                grid.getItems().get(x).setOneSelection();
                            }
                        }
                        sm.select(rowIndex);
                    }
                }
            }
        }
    }
}
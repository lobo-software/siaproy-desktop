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
 *  Document     : SialDateCellFactory.java
 * Created on    : 11 Apr 2016 4:37:26 PM
 * Author           : SVA
 * Modifications : 19/Abr/2016 Se añade funcionalidad para darle el formato 'dd MMM yyyy' a la fecha.
10/May/2016 13:07 SVA (LOBO_000076): Se añade funcionalidad en commitEdit para pasar a la siguiente columna editable.
 */
package frontEnd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 * @param <E>
 * @param <T>
 */
public class SialDateCellFactory<E, T> extends TableCell<E, Date> {

    private DatePicker datePicker;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    private Calendar cal;
    private int contador = 0;
    private int row = 0;

    public SialDateCellFactory() {
    }

    public Callback<TableColumn<E, Date>, TableCell<E, Date>> creaDatePicker() {
        Callback<TableColumn<E, Date>, TableCell<E, Date>> callBack;
        callBack = (TableColumn<E, Date> tableColumn) -> new SialDateCellFactory();
        return callBack;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createDatePicker();
            setText(null);
            setGraphic(datePicker);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    datePicker.requestFocus();
                }
            });
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getDateString());
        setGraphic(null);
    }

    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (datePicker != null) {
                datePicker.setValue(getDate());
            }
            setText(null);
            setGraphic(datePicker);
        } else {
            setText(sdf.format(item));
            setGraphic(null);
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker();
        datePicker.setValue(getDate());
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
             TableColumn nextColumn = getNextColumn(true);
                if (nextColumn != null) {
                    getTableView().edit(row, nextColumn);
                    getTableView().getSelectionModel().select(row);
                }
        });
        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.TAB) {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                TableColumn nextColumn = getNextColumn(!event.isShiftDown());
                if (nextColumn != null) {
                    getTableView().edit(row, nextColumn);
                    getTableView().getSelectionModel().select(row);
                }
             }
        });

    }

    private LocalDate getDate() {
        int dia, mes, anio;
        String dateWithFormat;
        dia = mes = anio = 0;
        dateWithFormat = sdf.format(getItem() == null ? Date.from(getItem().toInstant()) : getItem());
        cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(dateWithFormat));
            dia = Integer.parseInt(dateWithFormat.substring(0, 2));
            mes = cal.get(Calendar.MONTH) + 1;
            anio = Integer.parseInt(dateWithFormat.substring(7, dateWithFormat.length()));
        } catch (ParseException ex) {
            Logger.getLogger(SialDateCellFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return LocalDate.of(anio, mes, dia);
    }

    private String getDateString() {
        return sdf.format(getItem() == null ? new Date() : Date.from(getItem().toInstant()));
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

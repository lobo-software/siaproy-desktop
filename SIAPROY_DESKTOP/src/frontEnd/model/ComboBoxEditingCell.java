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
 *  Document     : ComboBoxEditingCell.java
 * Created on    : 11 abr 2016 10:34:57 AM
 * Author        : CCL
 * Modifications : 
 */
package frontEnd.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;

/**
 *
 * @author Lobo Software
 */
public class ComboBoxEditingCell extends TableCell<Actividades, Typ> {
    private ComboBox<Typ> comboBox;
    private ObservableList<Typ> lista;

    public ComboBoxEditingCell(ObservableList<Typ> typData) {
        lista = typData;
    }
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox(lista);
            setText(null);
            setGraphic(comboBox);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getTyp().getTyp());
        setGraphic(null);
    }

    @Override
    public void updateItem(Typ item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(item);

            }
            setText(getTyp().getTyp());
            setGraphic(comboBox);
        } else {
            setText(getTyp().getTyp());
            setGraphic(null);
        }
    }

    ;
        private void createComboBox(ObservableList<Typ> lista) {
        comboBox = new ComboBox<>(lista);
        comboBoxConverter(comboBox);
        comboBox.valueProperty().set(getTyp());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {
            System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
    }

    private void comboBoxConverter(ComboBox<Typ> comboBox) {
        // Define rendering of the list of values in ComboBox drop down. 
        comboBox.setCellFactory((c) -> {
            return new ListCell<Typ>() {
                @Override
                protected void updateItem(Typ item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getTyp());
                    }
                }
            };
        });
    }

    private Typ getTyp() {
        return getItem() == null ? new Typ("") : getItem();
    }
}

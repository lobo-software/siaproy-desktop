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
 *  Document     : AutoCompleteComboBoxListener.java
 * Created on    : 23 Apr 2016 12:48:32 PM
 * Author           : SVA
 * Modifications : 29/Abr/2016 17:07 SVA (LOBO_000076): Se añade clase y método en la llamada a GeneraCuadroMensaje.
 */
package frontEnd.util;

/**
 *
 * @author Lobo Software
 */
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private StringBuilder sb;
    private int lastLength;

    public AutoCompleteComboBoxListener(ComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);

        // add a focus listener such that if not in focus, reset the filtered typed keys
        this.comboBox.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    // in focus
                } else {
                    try {
                        lastLength = 0;
                        sb.delete(0, sb.length());
                        selectClosestResultBasedOnTextFieldValue(false, false);
                    } catch (Exception ex) {
                    }
                }
            }
        });

        this.comboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    selectClosestResultBasedOnTextFieldValue(true, true);
                } catch (Exception ex) {
                }
            }
        });
    }

    @Override
    public void handle(KeyEvent event) {
        // this variable is used to bypass the auto complete process if the length is the same.
        // this occurs if user types fast, the length of textfield will record after the user
        // has typed after a certain delay.
        try {
            if (lastLength != (comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length())) {
                lastLength = comboBox.getEditor().getLength() - comboBox.getEditor().getSelectedText().length();
            }

            if (event.isControlDown() || event.getCode() == KeyCode.BACK_SPACE
                    || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                return;
            }

            IndexRange ir = comboBox.getEditor().getSelection();
            sb.delete(0, sb.length());
            sb.append(comboBox.getEditor().getText());
            // remove selected string index until end so only unselected text will be recorded
            try {
                sb.delete(ir.getStart(), sb.length());
            } catch (Exception e) {
            }

            ObservableList items = comboBox.getItems();
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).toString().toUpperCase().startsWith(comboBox.getEditor().getText().toUpperCase())) {
                        try {
                            comboBox.getEditor().setText(sb.toString() + items.get(i).toString().substring(sb.toString().length()));
                        } catch (Exception e) {
                            comboBox.getEditor().setText(sb.toString());
                        }
                        comboBox.getEditor().positionCaret(sb.toString().length());
                        comboBox.getEditor().selectEnd();
                        break;
                    } else {
                        comboBox.getSelectionModel().clearSelection();
                    }
                }
            }
        } catch (Exception e) {
            GeneraCuadroMensaje.error(e.toString() + "\nCLASE: AutoCompleteComboBoxListener. \nMÉTODO: handle");
        }
    }

    /*
     * selectClosestResultBasedOnTextFieldValue() - selects the item and scrolls to it when
     * the popup is shown.
     * 
     * parameters:
     *  affect - true if combobox is clicked to show popup so text and caret position will be readjusted.
     *  inFocus - true if combobox has focus. If not, programmatically press enter key to add new entry to list.
     * 
     */
    private void selectClosestResultBasedOnTextFieldValue(boolean affect, boolean inFocus) throws Exception {
        ObservableList items = AutoCompleteComboBoxListener.this.comboBox.getItems();
        boolean found = false;
        try {
            for (int i = 0; i < items.size(); i++) {
                if (AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toUpperCase().equals(items.get(i).toString().toUpperCase())) {
                    try {
                        ListView lv = ((ComboBoxListViewSkin) AutoCompleteComboBoxListener.this.comboBox.getSkin()).getListView();
                        lv.getSelectionModel().clearAndSelect(i);
                        lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
                        found = true;
                        break;
                    } catch (Exception e) {
                    }
                }
            }

            String s = comboBox.getEditor().getText();
            if (!found && affect) {
                comboBox.getSelectionModel().clearSelection();
                comboBox.getEditor().setText(s);
                comboBox.getEditor().end();
            }
        } catch (Exception e) {
        }
    }

}

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
 * Modifications : 
 */
package frontEnd.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 */
public class SialCheckBoxCellFactory<E> {

private boolean multipleSelection;
    
    public Callback<Integer, ObservableValue<Boolean>> creaCheckBoxOneSelection(boolean multipleSelection, TableView<E> grid, BooleanProperty property) {
       this.multipleSelection = multipleSelection;
        Callback<Integer, ObservableValue<Boolean>> callBack;
        callBack = new Callback<Integer, ObservableValue<Boolean>>() {
//
     @Override
            public ObservableValue<Boolean> call(Integer param) {
//                System.out.println("Cours " + grid.getItems().get(param).getActividad() + " changed value to " + grdActividades.getItems().get(param).getActivo());
                if (!multipleSelection) {
                    //selecciona un checkbox
                } else {
                    return property;
                    }
//                return grid.getItems().get(param);
                return property;
                }
        };
        return callBack;
    }

}

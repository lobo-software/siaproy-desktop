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
 *  Document     : SialStopWatchCellFactory.java
 * Created on    : 16 Apr 2016 11:40:36 AM
 * Author           : SVA
 * Modifications : 
 */
package frontEnd.util;

import frontEnd.model.Actividades;
import frontEnd.model.stopWatch.Stopwatch;
import javafx.event.ActionEvent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 *
 * @author Lobo Software
 * @param <E>
 * @param <T>
 */
public class SialStopWatchCellFactory<E, T> extends TableCell<Actividades, Stopwatch> {

    private Stopwatch stopWatch;
    private TextField tiempoTotal;
    private TextField tiempoInicial;
    private TextField tiempoFinal;
    private TableCell<Actividades, Stopwatch> tableCell;

    public SialStopWatchCellFactory() {
    }

    public SialStopWatchCellFactory(TextField tiempoTotal, TextField tiempoInicial, TextField tiempoFinal) {
        this.tiempoTotal = tiempoTotal;
        this.tiempoInicial = tiempoInicial;
        this.tiempoFinal = tiempoFinal;
    }

    public Callback<TableColumn<E, Stopwatch>, TableCell<E, Stopwatch>> creaTimer(TextField tiempoTotal, TextField tiempoInicial, TextField tiempoFinal) {
        Callback<TableColumn<E, Stopwatch>, TableCell<E, Stopwatch>> callBack;
        callBack = (TableColumn<E, Stopwatch> tableColumn) -> {
            tableCell = new SialStopWatchCellFactory();
            return new TableCell();
        };
        return callBack;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            creaStopWatch();
            setText(null);
            setGraphic(stopWatch);
        }
    }

    @Override
    public void updateItem(Stopwatch item, boolean empty) {
        super.updateItem(item, empty);
        TableCell<E, Stopwatch> celdaTimer = new TableCell<E, Stopwatch>() {
        };
        celdaTimer.setOnMouseClicked(e -> {
            if (!celdaTimer.isEmpty()) {
                System.out.println("Deten others timers");
            }
        });
        if (empty) {
            setGraphic(null);
        } else if (isEditing()) {
            if (stopWatch != null) {
            }
            setText(null);
            setGraphic(stopWatch);

        } else {
            setGraphic(creaStopWatch());
        }
    }

    private Stopwatch creaStopWatch() {
        stopWatch = new Stopwatch(tiempoTotal, tiempoInicial, tiempoFinal);
        stopWatch.startStop.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
//            if (SPPRYF12Controller.getContador() == 0 && !SPPRYF12Controller.getBanderaEjecucion()) {
                System.out.println("set row");
//                SPPRYF12Controller.incrementaContadorSelectedTimer();
//                SPPRYF12Controller.setBanderaSelectedTimer(true);
//            } else if (SPPRYF12Controller.getContadorSelectedTimer() == 1 && SPPRYF12Controller.getBanderaEjecucion() && !SPPRYF12Controller.getBanderaSelectedTimer()) {
//                SPPRYF12Controller.decrementaContadorSelectedTimer();
//            } else if (SPPRYF12Controller.getContador() != 0 && SPPRYF12Controller.getContadorSelectedTimer() == 0 && !SPPRYF12Controller.getBanderaEjecucion()) {
                System.out.println("get old row");
                System.out.println("set new row");
                TableView<Actividades> grid = getTableView();
                grid.getItems().get(0).getStopWatch().startStop.fire();
                stopWatch.startStop.fire();
//            }
        });
        return stopWatch;
    }
}

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
 *  Document     : StopwatchTest.java
 * Created on    : 06 abr 2016 12:32:40 PM
 * Author        : SVA
 */
package frontEnd.model.stopWatch;

/**
 *
 * @author Lobo Software
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StopwatchTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }   
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Stopwatch");
        TextField total, tiempoInicio;
        total = new TextField();
        tiempoInicio = new TextField();
//        Stopwatch stopwatch = new Stopwatch(total, tiempoInicio);
        StackPane root = new StackPane();
//        root.getChildren().add(stopwatch);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
}
}
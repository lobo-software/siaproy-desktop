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
 *  Document     : SPPRYF12Main.java
 * Created on    : 08 abr 2016 10:22:36 AM
 * Author        : CCL
 * Modifications : 08/Apr/2016 18:44 CCL (LOBO_000076): Se añaden cabeceras de licencia a los archivos. 
 14/Apr/2016 17:11 SVA (LOBO_000076): Se elimina código comentado / se da formato al archivo.
 14/Apr/2016 15:16 CCL (LOBO_000076): Se añade una validación para cerrar ventana principal con el atert mandanla llamar desde el   SPPRYF12Controller....
 22/Apr/2016 15:36 CCL (LOBO_000076): Se cambia el nombre del método cierraAplicación por alertActividades.
 10/May/2016 15:30 CCL (LOBO_000076): Se Implententan estilos css y se le ponen imagenen principal de la aplicación, se agregaron iconos a la aplicación.


 */
package frontEnd.controller;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Lobo Software
 */
public class SPPRYF12Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/frontEnd/view/SPPRYF12View.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            if (SPPRYF12Controller.getBanderaEjecucion()) {
                e.consume();
            }
            SPPRYF12Controller.alertActividades(stage, "SPPRYF12", "CERRAR APLICACIÓN", 0);
        });
        SPPRYF12Controller.setPrimaryStage(stage);
        stage.getIcons().add(new Image("/frontEnd/images/SIAPROY_icono.jpg"));
        stage.setTitle("SPPRYF12. Reporte de actividades");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

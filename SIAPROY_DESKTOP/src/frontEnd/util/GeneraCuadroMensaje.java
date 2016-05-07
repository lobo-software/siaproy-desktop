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
 *  Document     : GeneraCuadroMensaje.java
 * Created on    : 25 Apr 2016 5:03:22 PM
 * Author           : SVA

 */
package frontEnd.util;

import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Lobo Software
 */
    public class GeneraCuadroMensaje {

    static boolean respuesta;

    public static void alert(String mensaje) {
//        Image venfi = new Image("venfi-pvb.png");
        Alert alert = new Alert(AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(venfi);
        alert.setTitle("VENFI");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add("SarreStyleSheet.css");
        alert.showAndWait();
    }

    public static boolean confirm(String mensaje) {
//        Image venfi = new Image("venfi-pvb.png");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("VENFI");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(venfi);
        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add("SarreStyleSheet.css");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void error(String mensaje) {
//        Image venfi = new Image("venfi-pvb.png");
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("VENFI");
        alert.setHeaderText(null);
        alert.setContentText("Ha ocurrido un error.");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(venfi);

        Hyperlink link = new Hyperlink("http://soporte.lobos.com.mx/Accesos.asp");
        Label label = new Label("Detalles:");

        TextArea textArea = new TextArea(mensaje);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        expContent.add(link, 0, 2);
        
        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add("SarreStyleSheet.css");
        if(loading().isShowing()){
            loading().close();
        }
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();

    }

    public static Stage loading() {
        Stage stage = new Stage();
        ProgressIndicator p1 = new ProgressIndicator();
        BorderPane mainPane = new BorderPane();
        HBox hb = new HBox();
        hb.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        mainPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        p1.setMaxSize(165, 165);
        p1.setMinSize(165, 165);
        p1.setStyle("-fx-accent: green");
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(p1);
        mainPane.setCenter(hb);
        Scene scene = new Scene(mainPane, 900, 650);
        scene.setFill(null);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setAlwaysOnTop(true);
        return stage;
    }
}


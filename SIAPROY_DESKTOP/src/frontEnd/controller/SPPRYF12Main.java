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
 24/Ene/2017 11:21 BEFL: Se agrega funcionalidad para que solo abra una instancia la aplicacion.

 */
package frontEnd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
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

    private static final int SINGLE_INSTANCE_LISTENER_PORT = 9999;
    private static final String SINGLE_INSTANCE_FOCUS_MESSAGE = "focus";
    private static final String instanceId = UUID.randomUUID().toString();
    private static final int FOCUS_REQUEST_PAUSE_MILLIS = 500;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        System.out.println("Starting instance " + instanceId);

        Platform.setImplicitExit(false);

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

    public void init() {
        CountDownLatch instanceCheckLatch = new CountDownLatch(1);

        Thread instanceListener = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SINGLE_INSTANCE_LISTENER_PORT, 10)) {
                instanceCheckLatch.countDown();

                while (true) {
                    try (
                            Socket clientSocket = serverSocket.accept();
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()))) {
                        String input = in.readLine();
                        System.out.println("Received single instance listener message: " + input);
                        if (stage != null) {
                            Thread.sleep(FOCUS_REQUEST_PAUSE_MILLIS);
                            Platform.runLater(() -> {
                                System.out.println("To front " + instanceId);
                                stage.setIconified(false);
                                stage.show();
                                stage.toFront();
                            });
                        }
                    } catch (IOException e) {
                        System.out.println("Single instance listener unable to process focus message from client");
                        e.printStackTrace();
                    }
                }
            } catch (java.net.BindException b) {
                System.out.println("SingleInstanceApp already running");

                try (
                        Socket clientSocket = new Socket(InetAddress.getLocalHost(), SINGLE_INSTANCE_LISTENER_PORT);
                        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                    System.out.println("Requesting existing app to focus");
                    out.println(SINGLE_INSTANCE_FOCUS_MESSAGE + " requested by " + instanceId);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Aborting execution for instance " + instanceId);
                Platform.exit();
            } catch (Exception e) {
                System.out.println(e.toString());
            } finally {
                instanceCheckLatch.countDown();
            }
        }, "instance-listener");
        instanceListener.setDaemon(true);
        instanceListener.start();

        try {
            instanceCheckLatch.await();
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

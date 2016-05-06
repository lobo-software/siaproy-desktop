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
 *  Document     : SPPRYF12AController.java
 * Created on    : 03 may 2016 12:55:44 PM
 * Author        : CCL
 * Modifications : 
 */
package frontEnd.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Lobo Software
 */
public class SPPRYF12AController implements Initializable {

    @FXML
    private Label lbSesionSiaproy;
    @FXML
    private TextField tfClaveUsuario;
    @FXML
    private TextField tfContrasena;
    private String claveUsuario;
    private String contrasena;
    private Stage ventana;
    private SPPRYF12Controller controllerPrincipal;

    public void initialize(URL url, ResourceBundle rb) {
        tfClaveUsuario.textProperty().addListener((ov, oldValue, newValue) -> {
            StringProperty elemento = (StringProperty) ov;
            ((TextField) elemento.getBean()).setText(newValue.toUpperCase());
        });
    }

    public void iniciaSesionSiaproy() {
      if(tfClaveUsuario.getText().equals(claveUsuario) && tfContrasena.getText().equals(contrasena)){
          controllerPrincipal.cargaProyectosActividadesSiaproy(ventana);
      }else{
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("SPPRYF12AView. Login a SIAPROY WEB");
          alert.setHeaderText("INICIO DE SESION FALLIDO");
          alert.setContentText("Los datos de sesión son incorrectos. Verifique su información.");
          alert.showAndWait();
      }

    }
    
    public void setCredencialesLogin(String usuario, String contraseña){
        claveUsuario= usuario;
        contrasena = contraseña;
    }
    
    public void setStage(Stage ventana){
        this.ventana = ventana;
    }
    
    public void setControllerPrincipal(SPPRYF12Controller controllerPrincipal){
        this.controllerPrincipal = controllerPrincipal;
    }
}

package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.net.URL;
import java.util.ResourceBundle;
//import java.awt.*;

public class Controller implements Initializable {
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label etiquetaUsPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void iniciarSesion (KeyEvent ke){
        if(ke.getCode() == KeyCode.ENTER){
            System.out.println("Usuario "+userField.getText());
            System.out.println("Contraseña "+passField.getText());
            etiquetaUsPass.setTextFill(Color.web("#04e01a"));
            etiquetaUsPass.setText("Usuario y contraseña correctos");
        }else{
            System.out.println("NO");
            etiquetaUsPass.setTextFill(Color.web("#ff0000"));
            etiquetaUsPass.setText("Usuario y contraseña incorrectos");
        }
    }

}

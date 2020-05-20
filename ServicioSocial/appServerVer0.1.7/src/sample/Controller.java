package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    //First window
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label etiquetaUsPass;

    //Color
    private Color okColor = Color.GREEN;
    private Color errorColor = Color.RED;
    private static DBConnection myConn;

    public Controller(){

    }

    public void setDBConn(DBConnection dbConnection){
        myConn = dbConnection;
        /*if(myConn.getCorrectCon() == false){
            etiquetaUsPass.setTextFill(errorColor);
            etiquetaUsPass.setText("Error de conexion, servidor caído");
        }else{
            etiquetaUsPass.setTextFill(okColor);
            etiquetaUsPass.setText("Conexion establecida");
        }*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void iniciarSesion (KeyEvent ke) throws Exception {
        if(ke.getCode() == KeyCode.ENTER){
            System.out.println("Usuario "+userField.getText());
            System.out.println("Contraseña "+passField.getText());
            myConn.iniciarSesion(userField.getText(), passField.getText());
            if(myConn.getCorrectCon() == true){
                etiquetaUsPass.setTextFill(okColor);
                etiquetaUsPass.setText("Usuario y contraseña correctos");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("secondView.fxml"));
                Parent root = loader.load();
                Controller2 controller = loader.<Controller2>getController();
                controller.setDBConn(myConn);
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("App Server Version 0.1.7");
                secondaryStage.setScene(new Scene(root, 720, 480));
                secondaryStage.show();
                ((Node)ke.getSource()).getScene().getWindow().hide();
            }else{
                etiquetaUsPass.setTextFill(errorColor);
                etiquetaUsPass.setText("Usuario y contraseña incorrectos");
            }
        }else{
            System.out.println("NO");
        }
    }

    public void iniciarSesion2 (MouseEvent me) throws Exception {
        System.out.println("Usuario "+userField.getText());
        System.out.println("Contraseña "+passField.getText());
        myConn.iniciarSesion(userField.getText(), passField.getText());
        if(myConn.getCorrectCon() == true){
            etiquetaUsPass.setTextFill(okColor);
            etiquetaUsPass.setText("Usuario y contraseña correctos");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondView.fxml"));
            Parent root = loader.load();
            Controller2 controller = loader.<Controller2>getController();
            controller.setDBConn(myConn);
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("App Server Version 0.1.2");
            secondaryStage.setScene(new Scene(root, 720, 480));
            secondaryStage.show();
            ((Node)me.getSource()).getScene().getWindow().hide();
        }else{
            etiquetaUsPass.setTextFill(errorColor);
            etiquetaUsPass.setText("Usuario y contraseña incorrectos");
        }
    }
}

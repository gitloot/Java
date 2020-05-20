package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Controlleralpha extends DBConnection {
    private final Stage thisStage;
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Label etiquetaUsPass;
    @FXML private TextArea messageArea;
    @FXML private TextField commandField;

    private DBConnection myConn;
    private Controller controller;

    public Controlleralpha(DBConnection dbconn) {
        //Create the new stage
        myConn = dbconn;
        thisStage = new Stage();
        //Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba1.fxml"));
            //Set this class as the controller
            //loader.setController(this);
            //Load the scene
            Parent root = loader.load();
            controller = loader.<Controller>getController();
            controller.setDBConn(dbconn);
            //primaryStage.setTitle("App Server Version 0.1.1");
            thisStage.setScene(new Scene(root,400,480));
            //primaryStage.showAndWait();
            //thisStage.setScene(new Scene(loader.load()));
            //Setup the window/stage
            thisStage.setTitle("App Server Version 0.1.1");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
        //thisStage.show();
    }

    public void iniciarSesion (KeyEvent ke) throws Exception {
        if(ke.getCode() == KeyCode.ENTER){
            System.out.println("Usuario "+userField.getText());
            System.out.println("Contraseña "+passField.getText());
            super.conectar();
            super.iniciarSesion(userField.getText(), passField.getText());
            if(super.getConfirm() == 1){
                //thisScene = ke.getSource();
                //((Node)thisScene).getScene().getWindow().hide();
                //thisWindow.hide();
                //((Node)ke.getSource()).getScene().getWindow().hide();
                etiquetaUsPass.setTextFill(Color.web("#04e01a"));
                etiquetaUsPass.setText("Usuario y contraseña correctos");
                Parent root2 = FXMLLoader.load(getClass().getResource("secondView.fxml"));
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("App Server Version 0.1.1");
                secondaryStage.setScene(new Scene(root2, 400, 480));
                secondaryStage.show();
                //listarOpciones();
            }else{
                etiquetaUsPass.setTextFill(Color.web("#ff0000"));
                etiquetaUsPass.setText("Usuario y contraseña incorrectos");
            }
        }else{
            System.out.println("NO");
        }
    }

    public void listarOpciones(){
        messageArea.setText("Bienvenido a la base de datos\n" +
                "A continuacion se enlistan las operaciones realizables:\n" +
                "Consultar - ingrese la letra c \n" +
                "Borrar - ingrese la letra b \n" +
                "Insertar - ingrese la letra i \n" +
                "Modificar - ingrese la letra m \n" +
                "Salir - ingrese la letra s");
    }

    public void Consultar(KeyEvent ke) {
        if(ke.getCode() == KeyCode.ENTER){
            try{
                messageArea.setText("Ingrese el nombre de la tabla a la cual va a consultar datos");
                String entrada = commandField.getText();
                switch(entrada){
                    case "alumno":
                        messageArea.setText(entrada+"\nConsultando datos...");
                        pstmt = conn.prepareStatement("SELECT * FROM ?");
                        pstmt.setString(1, entrada);
                        resultset = pstmt.executeQuery();//Se muestran las tablas de la base de datos
                        while (resultset.next()) {
                            messageArea.setText(messageArea.getText()+"Numero de cuenta: "+resultset.getInt("num_cta")+" Nombre: "+resultset.getString("nombre")+"\n");
                        }
                        break;
                    default:
                        messageArea.setText("La tabla no existe");
                        break;
                }
            }catch(SQLException e){
                messageArea.setText("Error durante la consulta de datos");
                e.printStackTrace();
            }
        }
    }
}

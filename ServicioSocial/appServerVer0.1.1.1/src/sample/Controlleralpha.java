package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Controlleralpha{
    private final Stage thisStage;
    private DBConnection myConn;
    private Controller controller;

    public Controlleralpha(DBConnection dbconn) {
        //Create the new stage
        myConn = dbconn;
        thisStage = new Stage();
        //Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba1.fxml"));
            Parent root = loader.load();
            controller = loader.<Controller>getController();
            controller.setDBConn(dbconn);
            thisStage.setScene(new Scene(root,400,480));
            thisStage.setTitle("App Server Version 0.1.1.1");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }
}

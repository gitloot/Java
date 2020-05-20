package sample;
import com.mysql.cj.jdbc.JdbcConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    DBConnection dbconn = null;

    public static void main(String[] args) {
        launch(args);
    }


    /*@Override
    public void start(Stage primaryStage) throws Exception{
        dbconn = new DBConnection();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prueba1.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("prueba1.fxml"));
        //Parent root = loader.load();
        Controller controller = loader.<Controller>getController();
        controller.setDBConn(dbconn);
        //primaryStage.setTitle("App Server Version 0.1.1");
        //primaryStage.setScene(new Scene(root,480,400));
        primaryStage.setScene(new Scene(loader.load()));
        //primaryStage.showAndWait();
    }*/


    public void start(Stage primaryStage){
        dbconn = new DBConnection();
        Controlleralpha controller = new Controlleralpha(this.dbconn);
        controller.showStage();
    }
}


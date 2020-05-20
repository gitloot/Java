package sample;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.application.Application;
import javafx.stage.Stage;
import sample.DBConnection;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        DBConnection dbConnection = DBConnection.getDbConnection();
        Controlleralpha controller = new Controlleralpha(dbConnection);
        controller.showStage();
    }
}


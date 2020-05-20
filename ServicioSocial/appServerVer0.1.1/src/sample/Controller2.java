package sample;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller2 {
    static final String inventarioTable = "tabla_inventario";

    @FXML private TextField commandField;
    @FXML private FXMLLoader thisScene;
    @FXML private TableView<ArticuloInventario> tableView;
    @FXML private Pane panel1;
    @FXML private Pane panel2;

    private DBConnection myConn;
    private PreparedStatement pstmt = null;
    private ResultSet resultset;
    private ObservableList<ArticuloInventario> datos;
    private Statement stmt = null;

    ///////////////////////////////////Columnas de la tabla///////////////////////////////////////////////
    @FXML private TableColumn<ArticuloInventario, String> equipoColumn = new TableColumn<ArticuloInventario,String>("equipo");
    @FXML private TableColumn<ArticuloInventario, Integer> num_inventarioColumn;

    ///////////////////////////////////Etiquetas de la tabla//////////////////////////////////////////////
    @FXML private Label equipoLabel;
    @FXML private Label num_inventarioLabel;

    public void allDatos(){
        panel1.setVisible(false);
        panel2.setVisible(true);
        try{
            System.out.println("\nConsultando datos espere por favor...");
            //pstmt = myConn.conn.prepareStatement("SELECT * FROM ?");
            //pstmt.setString(1, inventarioTable);
            //resultset = pstmt.executeQuery();//Se muestran las tablas de la base de datos
            stmt = myConn.conn.createStatement();
            resultset = stmt.executeQuery("SELECT equipo, num_inventario FROM tabla_inventario");
            System.out.println(" yes ");
            datos = FXCollections.observableArrayList();
            while (resultset.next()) {
                datos.add(new ArticuloInventario(resultset.getString("equipo"),
                        resultset.getInt("num_inventario")));
            }
            tableView.setItems(datos);
        }catch(SQLException e){
            System.out.println("La tabla no existe");
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    public void NextPanel1(){
        panel1.setVisible(true);
    }

    public void NextPanel2(){
        panel1.setVisible(true);
        panel2.setVisible(false);
    }

    public void setDBConn(DBConnection dbConnection){
        myConn = dbConnection;
    }

    public void initialize(){
        //equipoColumn.setCellValueFactory(cellData -> cellData.getValue().equipoProperty());
        //num_inventarioColumn.setCellValueFactory(cellData -> cellData.getValue().num_inventarioProperty());
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
    }
}

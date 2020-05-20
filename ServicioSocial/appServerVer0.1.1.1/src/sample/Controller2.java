package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller2 {

    ////////////////TextField/////////////
    @FXML private TextField commandField;
    @FXML private TextField equipoField;
    @FXML private TextField numInvField;
    @FXML private TextField processField;
    ////////////////Labels///////////////
    @FXML private Label equipoLabel;
    @FXML private Label numInvLabel;
    ///////////////Tablas///////////////
    @FXML private TableView<ArticuloInventario> tableView;
    @FXML private TableView<Procesador> tableView1;
    /////////////Paneles///////////////
    @FXML private Pane panel0;
    @FXML private Pane panel1;
    @FXML private Pane panel2;
    @FXML private Pane panel3;
    @FXML private Pane panel4;
    ////////////Images////////////////
   @FXML private ImageView editImage;

    private DBConnection myConn;
    private PreparedStatement pstmt = null;
    private ResultSet resultset;
    private ObservableList<ArticuloInventario> datos;
    private ObservableList<Procesador> datos1;
    private Statement stmt = null;
    private ArticuloInventario copia;

    ///////////////////////////////////Columnas de la tabla principal///////////////////////////////////////////////
    @FXML private TableColumn<ArticuloInventario, String> equipoColumn = new TableColumn<ArticuloInventario,String>("equipo");
    @FXML private TableColumn<ArticuloInventario, Integer> num_inventarioColumn = new TableColumn<ArticuloInventario,Integer>("num_inventario");
    @FXML private TableColumn<ArticuloInventario, String> marcaColumn = new TableColumn<ArticuloInventario,String>("marca");
    @FXML private TableColumn<ArticuloInventario, String> modeloColumn = new TableColumn<ArticuloInventario,String>("modelo");
    @FXML private TableColumn<ArticuloInventario, String> num_serieColumn = new TableColumn<ArticuloInventario,String>("num_serie");
    @FXML private TableColumn<ArticuloInventario, String> soColumn = new TableColumn<ArticuloInventario,String>("so");
    @FXML private TableColumn<ArticuloInventario, Integer> num_procesadoresColumn = new TableColumn<ArticuloInventario,Integer>("num_procesadores");
    @FXML private TableColumn<ArticuloInventario, Integer> num_unidad_almacColumn = new TableColumn<ArticuloInventario,Integer>("num_unidad_almac");
    @FXML private TableColumn<ArticuloInventario, String> config_disp_almacColumn = new TableColumn<ArticuloInventario,String>("config_disp_almac");
    @FXML private TableColumn<ArticuloInventario, Integer> num_tarj_videoColumn = new TableColumn<ArticuloInventario,Integer>("num_tarj_video");
    @FXML private TableColumn<ArticuloInventario, Integer> num_interface_redColumn = new TableColumn<ArticuloInventario,Integer>("num_interface_red");
    @FXML private TableColumn<ArticuloInventario, Integer> num_unidad_lectColumn = new TableColumn<ArticuloInventario,Integer>("num_unidad_lect");
    @FXML private TableColumn<ArticuloInventario, String> final_userColumn = new TableColumn<ArticuloInventario,String>("final_user");
    @FXML private TableColumn<ArticuloInventario, String> carac_addColumn = new TableColumn<ArticuloInventario,String>("carac_add");
    @FXML private TableColumn<ArticuloInventario, String> estadoColumn = new TableColumn<ArticuloInventario,String>("estado");
    //////////////////////////////////////Columnas de la tabla procesador////////////////////////////////////
    @FXML private TableColumn<Procesador, String> procesadorColumn = new TableColumn<Procesador, String>("procesador");
    ////////////////////////////////////////////Metodos/////////////////////////////////////////////////////////
    //Para almacenar los datos
    private void storeData(){
        try{
            datos = FXCollections.observableArrayList();
            while (resultset.next()) {
                datos.add(new ArticuloInventario(
                        resultset.getString("equipo"),
                        resultset.getInt("num_inventario"),
                        resultset.getString("marca"),
                        resultset.getString("modelo"),
                        resultset.getString("num_serie"),
                        resultset.getString("so"),
                        resultset.getInt("num_procesadores"),
                        resultset.getInt("num_unidad_almac"),
                        resultset.getString("config_disp_almac"),
                        resultset.getInt("num_tarj_video"),
                        resultset.getInt("num_interface_red"),
                        resultset.getInt("num_unidad_lect"),
                        resultset.getString("final_user"),
                        resultset.getString("carac_add"),
                        resultset.getString("estado")));
            }
            tableView.setItems(datos);
        }catch(SQLException e){
            System.out.println("La tabla no existe");
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }
    //Para mostrar todos los datos permitidos de la tabla principal
    public void consultaAllData(){
        try{
            System.out.println("\nConsultando datos espere por favor...");
            stmt = myConn.conn.createStatement();
            resultset = stmt.executeQuery("SELECT equipo, num_inventario, marca, modelo, " +
                    "num_serie, so, num_procesadores, num_unidad_almac, config_disp_almac, " +
                    "num_tarj_video, num_interface_red, num_unidad_lect, " +
                    "final_user, carac_add, estado  FROM tabla_inventario");
            System.out.println(" yes ");
            storeData();
            //editImage.setVisible(false);
            closePanel(0);
            closePanel(1);
            openPanel(2);
        }catch(SQLException e){
            System.out.println("La tabla no existe");
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }
    //Para la busqueda por numero de inventario
    public void consultaPorNumInventario(){
        //editImage.setVisible(false);
        closePanel(0);
        closePanel(1);
        openPanel(2);
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("SELECT equipo, num_inventario, marca, modelo, " +
                    "num_serie, so, num_procesadores, num_unidad_almac, config_disp_almac, " +
                    "num_tarj_video, num_interface_red, num_unidad_lect, " +
                    "final_user, carac_add, estado  FROM tabla_inventario WHERE num_inventario=?");
            pstmt.setString(1, commandField.getText());
            resultset = pstmt.executeQuery();
            System.out.println(" yes ");
            storeData();
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Para la busqueda por marca
    public void consultaPorMarca(){
        //editImage.setVisible(false);
        closePanel(0);
        closePanel(1);
        openPanel(2);
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("SELECT equipo, num_inventario, marca, modelo, " +
                    "num_serie, so, num_procesadores, num_unidad_almac, config_disp_almac, " +
                    "num_tarj_video, num_interface_red, num_unidad_lect, " +
                    "final_user, carac_add, estado  FROM tabla_inventario WHERE marca=?");
            pstmt.setString(1, commandField.getText());
            resultset = pstmt.executeQuery();
            System.out.println(" yes ");
            storeData();
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////Modificadores de datos//////////////////////////////////////////////
    public void updateDatos(){
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("UPDATE tabla_inventario SET equipo=?, num_inventario=? WHERE equipo=? and " +
                    "num_inventario=?");
            pstmt.setString(1, equipoLabel.getText());
            pstmt.setInt(2, Integer.parseInt(numInvLabel.getText()));
            pstmt.setString(3,copia.getEquipo());
            pstmt.setInt(4,copia.getNumInventario());
            pstmt.executeUpdate();
            System.out.println(" yes ");
            closePanel(3);
            closePanel(2);
            openPanel(0);
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    public void editDato(){
        openPanel(3);
        System.out.println("Se quiere editar datos");
        datos1 = FXCollections.observableArrayList();
        datos1.add(new Procesador("Exito0"));
        datos1.add(new Procesador("Exito1"));
        datos1.add(new Procesador("Exito2"));
        tableView1.setItems(datos1);
        tableView1.setEditable(true);
    }

    public void editButtonAction(){
        System.out.println("Your clicking me, i am button "+ tableView.getSelectionModel().getSelectedItem().getEquipo());
        copia = tableView.getSelectionModel().getSelectedItem();
        equipoField.setText(copia.getEquipo());
        equipoLabel.setText(copia.getEquipo());
        numInvField.setText(Integer.toString(copia.getNumInventario()));
        numInvLabel.setText(Integer.toString(copia.getNumInventario()));
        closePanel(2);
        openPanel(3);
    }

    public void editTextField(KeyEvent ke){
        try{
            if(ke.getCode() == KeyCode.ENTER){
                System.out.println("Id"+((TextField)ke.getSource()).getId());
                if(((TextField)ke.getSource()).getId().equals(equipoField.getId())){
                    equipoLabel.setText(equipoField.getText());
                }else{
                    System.out.println("Nop");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////Activadores de paneles//////////////////////////////////////
    public void NextPanel1(){
        panel1.setVisible(true);
    }

    public void NextPanel2(){
        panel0.setVisible(true);
        panel1.setVisible(true);
        panel2.setVisible(false);
    }

    public void Panel3a2(){
        panel2.setVisible(true);
        panel3.setVisible(false);
    }


    public void openPanel(int p){
        switch (p){
            case 0:
                panel0.setVisible(true);
                break;
            case 1:
                panel1.setVisible(true);
                break;
            case 2:
                panel2.setVisible(true);
                break;
            case 3:
                panel3.setVisible(true);
                break;
            case 4:
                panel4.setVisible(true);
                break;
            default:
                break;
        }
    }

    public void closePanel(int p){
        switch (p){
            case 0:
                panel0.setVisible(false);
                break;
            case 1:
                panel1.setVisible(false);
                break;
            case 2:
                panel2.setVisible(false);
                break;
            case 3:
                panel3.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void setDBConn(DBConnection dbConnection){
        myConn = dbConnection;
    }

    public void initialize(){
        //////////////////////////Inicializando datos de la tabla principal/////////////////////
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        num_inventarioColumn.setCellValueFactory(new PropertyValueFactory<>("num_inventario"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        num_serieColumn.setCellValueFactory(new PropertyValueFactory<>("num_serie"));
        soColumn.setCellValueFactory(new PropertyValueFactory<>("so"));
        num_procesadoresColumn.setCellValueFactory(new PropertyValueFactory<>("num_procesadores"));
        num_unidad_almacColumn.setCellValueFactory(new PropertyValueFactory<>("num_unidad_almac"));
        config_disp_almacColumn.setCellValueFactory(new PropertyValueFactory<>("config_disp_almac"));
        num_tarj_videoColumn.setCellValueFactory(new PropertyValueFactory<>("num_tarj_video"));
        num_interface_redColumn.setCellValueFactory(new PropertyValueFactory<>("num_interface_red"));
        num_unidad_lectColumn.setCellValueFactory(new PropertyValueFactory<>("num_unidad_lect"));
        final_userColumn.setCellValueFactory(new PropertyValueFactory<>("final_user"));
        carac_addColumn.setCellValueFactory(new PropertyValueFactory<>("carac_add"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        /////////////////////////Inicializando datos de la tabla procesador//////////////////////
        procesadorColumn.setCellValueFactory(new PropertyValueFactory<>("Procesador"));
        //tableView1.getColumns().addAll(procesadorColumn);
    }
}

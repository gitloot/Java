package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller2 {
    //Constantes
    private final ObservableList<String> listaGenerales = FXCollections.observableArrayList("Equipo","Nombre","Número de inventario","Marca","Modelo","Número de Serie","Descripción","Estado","Usuario Final");
    private final ObservableList<String> listaUbicacion = FXCollections.observableArrayList("Edificio","Piso","Cubículo");
    private final String encabezadoInv = "SELECT id, equipo, nombre, num_inventario, marca, modelo, num_serie, descripcion, estado, final_user FROM tabla_inventario ";
    private final String encabezadoUbi = "SELECT id, edificio, piso, cubiculo FROM tabla_ubicacion ";
    ///////////////Menu Button////////////
    @FXML private MenuButton menuButton;
    @FXML private MenuButton menuButton2;
    @FXML private MenuButton menuButton3;
    @FXML private MenuButton menuButton4;
    ////////////////TextField/////////////
    //De comandos de busqueda
    @FXML private TextField commandField;
    @FXML private TextField edificioField;
    @FXML private TextField pisoField;
    @FXML private TextField cubiculoField;
   //De comandos de edicion
    @FXML private TextField equipoField;
    @FXML private TextField numInvField;
    @FXML private TextField advancedTF1;
    @FXML private TextField advancedTF2;
    @FXML private TextField advancedTF3;
    ///////////////HBox//////////////////
    @FXML private HBox searchBox;
    ////////////////Labels///////////////
    @FXML private Label equipoLabel;
    @FXML private Label numInvLabel;
    ///////////////Tablas///////////////
    @FXML private TableView<ArticuloInventario> tableView;
    @FXML private TableView<Ubicacion> tableUbicacion;
    /////////////Paneles///////////////
    @FXML private Pane panel0;  //Panel de busqueda
    @FXML private Pane panel1;  //Panel de tabla de busqueda
    @FXML private Pane panel2;  //Panel de edición
    @FXML private Pane panel3;  //Panel de detalles
    @FXML private Pane panel4;  //Panel de busqueda avanzada
    ////////////Tab Pane//////////////
    @FXML private TabPane tabPanel0;
    ///////////List View//////////////
    @FXML private ListView<String> listaEtiqGen;
    @FXML private ListView<String> listaValGen;
    @FXML private ListView<String> listaEtiqUbi;
    @FXML private ListView<String> listaValUbi;
    ////////////Images////////////////

    private DBConnection myConn;
    private PreparedStatement pstmt = null;
    private ResultSet resultset;
    private ObservableList<ArticuloInventario> datos;
    private ObservableList<Ubicacion> datosUbicacion;
    private Statement stmt = null;
    private ArticuloInventario copia;

    //@FXML privste TableColumn<Clase,Clase del valor> nombre = new TableColumn<>("Nombre de la columna en el entorno grafico; Nota solo funciona cuando se crea la columna por código");
    ///////////////////////////////////Columnas de la tabla principal///////////////////////////////////////////////
    @FXML private TableColumn<ArticuloInventario, String> equipoColumn = new TableColumn<>();
    @FXML private TableColumn<ArticuloInventario, String> nombreColumn = new TableColumn<>();
    @FXML private TableColumn<ArticuloInventario, Integer> num_inventarioColumn = new TableColumn<>();
    @FXML private TableColumn<ArticuloInventario, String> marcaColumn = new TableColumn<ArticuloInventario,String>();
    @FXML private TableColumn<ArticuloInventario, String> modeloColumn = new TableColumn<ArticuloInventario,String>();
    @FXML private TableColumn<ArticuloInventario, String> num_serieColumn = new TableColumn<ArticuloInventario,String>();
    @FXML private TableColumn<ArticuloInventario, String> descripcionColumn = new TableColumn<>();
    @FXML private TableColumn<ArticuloInventario, String> estadoColumn = new TableColumn<ArticuloInventario,String>();
    @FXML private TableColumn<ArticuloInventario, String> final_userColumn = new TableColumn<ArticuloInventario,String>();

    //////////////////////////////////////Columnas de la tabla ubicacion////////////////////////////////////
    @FXML private TableColumn<Ubicacion, String> equipoUbiColumn = new TableColumn<>();
    @FXML private TableColumn<Ubicacion, Integer> numInvUbiColumn = new TableColumn<>();
    @FXML private TableColumn<Ubicacion, String> edificioUbiColumn = new TableColumn<>();
    @FXML private TableColumn<Ubicacion, String> pisoUbiColumn = new TableColumn<>();
    @FXML private TableColumn<Ubicacion, String> cubiculoUbiColumn = new TableColumn<>();
    ////////////////////////////////////////////Metodos/////////////////////////////////////////////////////////
    //Para almacenar los datos generales en la variable asociada
    private void storeData(){
        try{
            datos = FXCollections.observableArrayList();
            while (resultset.next()) {
                datos.add(new ArticuloInventario(
                        resultset.getInt("id"),
                        resultset.getString("equipo"),
                        resultset.getString("nombre"),
                        resultset.getInt("num_inventario"),
                        resultset.getString("marca"),
                        resultset.getString("modelo"),
                        resultset.getString("num_serie"),
                        resultset.getString("descripcion"),
                        resultset.getString("estado"),
                        resultset.getString("final_user")));
            }
            tableView.setItems(datos);
        }catch(SQLException e){
            System.out.println("La tabla no existe");
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }
    //Para almacenar los datos de ubicación en la variable asociada
    private void storeDataUbicacion(){
        try{
            datosUbicacion = FXCollections.observableArrayList();
            while (resultset.next()) {
                datosUbicacion.add(new Ubicacion(
                        resultset.getInt("id"),
                        resultset.getString("equipo"),
                        resultset.getInt("num_inventario"),
                        resultset.getString("edificio"),
                        resultset.getString("piso"),
                        resultset.getString("cubiculo")));
            }
            tableUbicacion.setItems(datosUbicacion);
        }catch(SQLException e){
            System.out.println("Error durante el set de datos");
            e.printStackTrace();
        }
    }


    //Para la busqueda por ámbito unitario conociendo el nombre de la tabla de busqueda
    private void consultaUnicaPor(String campo){
        //editImage.setVisible(false);
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("SELECT id, equipo, nombre, num_inventario, marca, modelo, " +
                    "num_serie, descripcion, estado, final_user FROM tabla_inventario WHERE "+campo+"=?");
            pstmt.setString(1, commandField.getText());
            resultset = pstmt.executeQuery();
            //System.out.println(" yes ");
            //storeData();
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Conociendo la columna y la tabla de busqueda
    private void consultaUnicaPor(String campo, String tableName){
        //editImage.setVisible(false);
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("SELECT id, equipo, nombre, num_inventario, marca, modelo, " +
                    "num_serie, descripcion, estado, final_user FROM " + tableName + " WHERE "+campo+"=?");
            pstmt.setString(1, commandField.getText());
            resultset = pstmt.executeQuery();
            //System.out.println(" yes ");
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Conociendo la tabla, columna y valor especificos para realizar la busqueda
    private void consultaUnicaPor(String column, String valor, String tableName){
        System.out.println("\nConsultando datos espere por favor...");
        try{
            switch (tableName){
                case "tabla_inventario":
                    pstmt = myConn.conn.prepareStatement(encabezadoInv+ " WHERE " +column+"=?");
                    pstmt.setString(1, valor);
                    resultset = pstmt.executeQuery();
                    break;
                case "tabla_ubicacion":
                    pstmt = myConn.conn.prepareStatement(encabezadoUbi+ " WHERE " +column+"=?");
                    pstmt.setString(1, valor);
                    resultset = pstmt.executeQuery();
                default:
                    break;
            }
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Para la busqueda por ubicacion de multiples ambitos
    private void consultaMultiplePorUbicacion(Listado[] listados){
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("select ti.id, ti.equipo, ti.num_inventario, tu.edificio, tu.piso, tu.cubiculo " +
                    "from tabla_inventario as ti " +
                    "inner join tabla_ubicacion as tu on ti.id = tu.id " +
                    "where tu.edificio=? or tu.piso=? or tu.cubiculo=?");
            pstmt.setString(1, listados[0].getValor());
            pstmt.setString(2, listados[1].getValor());
            pstmt.setString(3, listados[2].getValor());
            resultset = pstmt.executeQuery();
            //System.out.println(" yes ");
            storeDataUbicacion();
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Para visualizar la tabla del procesador


    /*Función del menu button en el panel de busqueda; despliega el text field de campo multiple
     cuando se selecciona busqueda por ubicación*/
    public void selectButton(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        System.out.println(s);
        if (s.equals("Ubicación")){
            commandField.setVisible(false);
            searchBox.setVisible(true);
        }else{
            commandField.setVisible(true);
            searchBox.setVisible(false);
        }
        menuButton.setText(s);
    }

    /*Función génerica para el menubutton, al momento de escoger una opcion el menubutton se actualiza con el nombre de la opcion escogida
     */
    public void selectButton2(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuButton2.setText(s);
    }

    //////////////////////////////////////////////Botones//////////////////////////////////////////////
    //Botón de guardado, que aparece en el panel de edición
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
            //System.out.println(" yes ");
            closePanel(2);
            closePanel(1);
            openPanel(0);
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    //Para el botón de edición, el icono de lapíz en el panel de tabla
    public void editButtonAction(){
        System.out.println("Your clicking me, i am button "+ tableView.getSelectionModel().getSelectedItem().getEquipo());
        copia = tableView.getSelectionModel().getSelectedItem();
        equipoField.setText(copia.getEquipo());
        equipoLabel.setText(copia.getEquipo());
        numInvField.setText(Integer.toString(copia.getNumInventario()));
        numInvLabel.setText(Integer.toString(copia.getNumInventario()));
        closePanel(1);
        openPanel(2);
    }

    /*Para modificar la etiqueta, que muestra un cambio previo al salvado de información que se encuentran en el panel de edición*/
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

    /*Para iniciar la busqueda una vez se de ENTER en el textfield principal*/
    public void search1(KeyEvent ke){
        try{
            if(ke.getCode() == KeyCode.ENTER){
               switch (menuButton.getText()){
                   case "# Inventario":
                       System.out.println("Busqueda por inventario");
                       consultaUnicaPor("num_inventario");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       closeTabla(1);
                       openTabla(0);
                       break;
                   case "# Serial":
                       System.out.println("Busqueda por serial");
                       consultaUnicaPor("num_serie");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       closeTabla(1);
                       openTabla(0);
                       break;
                   case "Modelo":
                       System.out.println("Busqueda por modelo");
                       consultaUnicaPor("modelo");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       closeTabla(1);
                       openTabla(0);
                       break;
                   case "Marca":
                       consultaUnicaPor("marca");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       closeTabla(1);
                       openTabla(0);
                   case "Ubicación":
                       System.out.println("Busqueda por ubicacion");
                       Listado[] listadoTemp = new Listado[3];
                       listadoTemp[0] = new Listado("edificio",edificioField.getText());
                       listadoTemp[1] = new Listado("piso",pisoField.getText());
                       listadoTemp[2] = new Listado("cubiculo",cubiculoField.getText());
                       consultaMultiplePorUbicacion(listadoTemp);
                       closePanel(0);
                       openPanel(1);
                       closeTabla(0);
                       openTabla(1);
                       break;
                   case "Estado del bien":
                       System.out.println("Busqueda por estado del bien");
                       consultaUnicaPor("estado");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       closeTabla(1);
                       openTabla(0);
                       break;
                   default:
                       System.out.println("No contemplado");
                       break;
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Para iniciar la busqueda una vez se de ENTER en el textfield principal*/
    public void search2(KeyEvent ke){
        String s;
        try{
            if (menuButton2.getText() != "..."){
                s = menuButton2.getText();
            }
            if(ke.getCode() == KeyCode.ENTER){
                switch (menuButton2.getText()){
                    case "# Inventario":
                        System.out.println("Busqueda por inventario");
                        consultaUnicaPor("num_inventario");
                        storeData();
                        closePanel(0);
                        openPanel(1);
                        closeTabla(1);
                        openTabla(0);
                        break;
                    case "# Serial":
                        System.out.println("Busqueda por serial");
                        consultaUnicaPor("num_serie");
                        storeData();
                        closePanel(0);
                        openPanel(1);
                        closeTabla(1);
                        openTabla(0);
                        break;
                    case "Modelo":
                        System.out.println("Busqueda por modelo");
                        consultaUnicaPor("modelo");
                        storeData();
                        closePanel(0);
                        openPanel(1);
                        closeTabla(1);
                        openTabla(0);
                        break;
                    case "Marca":
                        consultaUnicaPor("marca");
                        storeData();
                        closePanel(0);
                        openPanel(1);
                        closeTabla(1);
                        openTabla(0);
                    case "Ubicación":
                        System.out.println("Busqueda por ubicacion");
                        Listado[] listadoTemp = new Listado[3];
                        listadoTemp[0] = new Listado("edificio",edificioField.getText());
                        listadoTemp[1] = new Listado("piso",pisoField.getText());
                        listadoTemp[2] = new Listado("cubiculo",cubiculoField.getText());
                        consultaMultiplePorUbicacion(listadoTemp);
                        closePanel(0);
                        openPanel(1);
                        closeTabla(0);
                        openTabla(1);
                        break;
                    case "Estado del bien":
                        System.out.println("Busqueda por estado del bien");
                        consultaUnicaPor("estado");
                        storeData();
                        closePanel(0);
                        openPanel(1);
                        closeTabla(1);
                        openTabla(0);
                        break;
                    default:
                        System.out.println("No contemplado");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Bóton de más información, icono de i ubicado en el panel de tabla*/
    public void moreInfoButton(){
        closePanel(1);
        openPanel(3);
        try {
            if(tableView.isVisible()){
                //System.out.println("Your clicking me, i am button " + tableView.getSelectionModel().getSelectedItem().getId());
                String auxId = Integer.toString(tableView.getSelectionModel().getSelectedItem().getId());
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx Id "+auxId);
                consultaUnicaPor("id", auxId,"tabla_inventario");
                //System.out.println("pppppppppppppppppppppppppppp "+resultset.getString("equipo"));
                //System.out.println("--------------------------------------------------- " +aux.get(0));
                listaValGen.setItems(asignacionDeResultados(0));
                consultaUnicaPor("id", auxId,"tabla_ubicacion");
                //System.out.println("pppppppppppppppppppppppppppp "+resultset.getString("equipo"));
                listaValUbi.setItems(asignacionDeResultados(1));
            }else if(tableUbicacion.isVisible()){
                System.out.println("Your clicking me, i am button " + tableUbicacion.getSelectionModel().getSelectedItem().getId());
                String auxId = Integer.toString(tableUbicacion.getSelectionModel().getSelectedItem().getId());
                consultaUnicaPor("id", auxId,"tabla_ubicacion");
                listaValUbi.setItems(asignacionDeResultados(1));
                consultaUnicaPor("id", auxId,"tabla_inventario");
                listaValGen.setItems(asignacionDeResultados(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        listaEtiqGen.setItems(listaGenerales);
        listaEtiqUbi.setItems(listaUbicacion);
    }
    //Método de apoyo al metodo anterior, se utilizan para almacenar los datos de busqueda en los listados gráficos
    private ObservableList<String> asignacionDeResultados(int idTabla) {
        ObservableList<String> aux = null;
        try{
            resultset.next();
            switch (idTabla){
                case 0:
                    aux = FXCollections.observableArrayList(
                            resultset.getString("equipo"),
                            resultset.getString("nombre"),
                            Integer.toString(resultset.getInt("num_inventario")),
                            resultset.getString("marca"),
                            resultset.getString("modelo"),
                            resultset.getString("num_serie"),
                            resultset.getString("descripcion"),
                            resultset.getString("estado"),
                            resultset.getString("final_user"));
                    break;
                case 1:
                    aux = FXCollections.observableArrayList(
                            resultset.getString("edificio"),
                            resultset.getString("piso"),
                            resultset.getString("cubiculo"));
                    break;
                default:
                    break;
            }
        }catch(SQLException se){

        }
        return aux;
    }
    ///////////////////////Activadores de tabla
    //Visualiza una tabla
    public void openTabla(int t){
        switch (t){
            case 0:
                tableView.setVisible(true);
                break;
            case 1:
                tableUbicacion.setVisible(true);
                break;
            default:
                break;
        }
    }
    //Oculta una tabla
    public void closeTabla(int t){
        switch (t){
            case 0:
                tableView.setVisible(false);
                break;
            case 1:
                tableUbicacion.setVisible(false);
                break;
            default:
                break;
        }
    }

    ///////////////////////////////////Activadores de paneles//////////////////////////////////////
    public void Panel2a1(){
        panel1.setVisible(true);
        panel2.setVisible(false);
    }

    public void returnToPanel0(){
        panel0.setVisible(true);
        panel1.setVisible(false);
    }

    public void Panel3a1(){
        openPanel(1);
        closePanel(3);
    }

    public void Panel4a0(){
        openPanel(0);
        closePanel(4);
    }

    public void Panel0a4(){
        openPanel(4);
        closePanel(0);
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
            case 4:
                panel4.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void setDBConn(DBConnection dbConnection){
        myConn = dbConnection;
    }

    public void initialize(){
        //nombre de columna.setCellValueFactory(new PropertyValueFactory<>("nombre de la propiedad - METODO"));
        //////////////////////////Inicializando datos de la tabla principal/////////////////////
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        num_inventarioColumn.setCellValueFactory(new PropertyValueFactory<>("num_inventario"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        num_serieColumn.setCellValueFactory(new PropertyValueFactory<>("num_serie"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        final_userColumn.setCellValueFactory(new PropertyValueFactory<>("final_user"));
        /////////////////////////Inicializando datos de la tabla ubicacion//////////////////////
        equipoUbiColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        numInvUbiColumn.setCellValueFactory(new PropertyValueFactory<>("numInventario"));
        edificioUbiColumn.setCellValueFactory(new PropertyValueFactory<>("edificio"));
        pisoUbiColumn.setCellValueFactory(new PropertyValueFactory<>("piso"));
        cubiculoUbiColumn.setCellValueFactory(new PropertyValueFactory<>("cubiculo"));
    }

}

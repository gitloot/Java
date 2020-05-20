package sample;

//Apache poi
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//JavaFX
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

//Javax
import javax.swing.*;

//Java
import java.io.*;
import java.sql.*;
import java.util.Random;

public class Controller2 {
    //Constantes
    private final String SALTO_D_LINEA = "\n";
    private final String[] ENCABEZADOS = {"ID","Equipo","Nombre","Número de inventario","Marca","Modelo","Número de serie","Descripción","Estado","Usuario Final"};
    private final ObservableList<String> listaGenerales = FXCollections.observableArrayList("Equipo", "Nombre", "Número de inventario", "Marca", "Modelo", "Número de Serie", "Descripción", "Estado", "Usuario Final");
    private final ObservableList<String> listaUbicacion = FXCollections.observableArrayList("Edificio", "Piso", "Cubículo");
    private final String encabezadoInv = "SELECT id, equipo, nombre, num_inventario, marca, modelo, num_serie, descripcion, estado, final_user FROM tabla_inventario ";
    private final String encabezadoUbi = "SELECT id, edificio, piso, cubiculo FROM tabla_ubicacion ";
    //Variables
    private String campo;
    private Random random = new Random();
    private int contCampos = 1;
    ////////////Colores////////////////
    private final Color errorColor = Color.RED;
    private final Color warningColor = Color.GOLD;
    private final Color okColor = Color.GREEN;
    ///////////////Labels////////////////
    //General
    @FXML private Label lEq;
    @FXML private Label lNom;
    @FXML private Label lNumInv;
    @FXML private Label lMarc;
    @FXML private Label lMod;
    @FXML private Label lNumSer;
    @FXML private Label lDesc;
    @FXML private Label lEst;

    @FXML private Label lUsFin;
    //Ubicacion
    @FXML private Label lEdi;
    @FXML private Label lPiso;
    @FXML private Label lCub;

    //Warning labels
    @FXML private Label labelInicio;
    @FXML private Label labelInsert;
    ////////////ImageView//////////////
    @FXML private ImageView editEquipo1;
    @FXML private ImageView editNumInv1;
    ///////////////Menu Button////////////
    @FXML private MenuButton menuButton; //General
    @FXML private MenuButton menuAdvance1; //Avanzada 1
    @FXML private MenuButton menuEdificio;
    @FXML private MenuButton menuPiso;
    @FXML private MenuButton menuSalon;
    @FXML private MenuButton menuModels1;
    @FXML private MenuButton menuInsercion;
    //De inserción
    @FXML private MenuButton menuInsertMarca;
    @FXML private MenuButton menuInsertModelo;
    @FXML private MenuButton menuInsertEstado;
    @FXML private MenuButton menuInsertFinUser;
    ////////////////TextField/////////////
    //De comandos de busqueda
    @FXML private TextField commandField;
   //De comandos de edicion
    @FXML private TextField equipoFieldUpdate;
    @FXML private TextField numInvFieldUpdate;
    @FXML private TextField advancedTF1;
    @FXML private TextField advancedTF2;
    @FXML private TextField advancedTF3;
    //De inserción
    @FXML private TextField insertFieldEquipo;
    @FXML private TextField insertFieldNumInv;
    @FXML private TextField insertFieldNom;
    @FXML private TextField insertFieldDesc;
    @FXML private TextField insertFieldNumSer;
    ///////////////HBox//////////////////
    @FXML private HBox searchBox;
    //Busqueda avanzada
    @FXML private HBox campo1;
    @FXML private HBox campo2;
    @FXML private HBox campo3;
    @FXML private HBox campo4;
    @FXML private HBox campo5;
    @FXML private HBox campo6;
    ////////////////Labels///////////////
    @FXML private Label equipoLabel;
    @FXML private Label numInvLabel;
    ///////////////Tablas///////////////
    @FXML private TableView<ArticuloInventario> tableView;
    @FXML private TableView<Ubicacion> tableUbicacion;
    @FXML private TableView<Articulo> tableArticulo;
    ///////////Observable list////////////////////
    private ObservableList<ArticuloInventario> datos;
    private ObservableList<Ubicacion> datosUbicacion;
    private ObservableList<Articulo> datosAdvance;
    /////////////Paneles///////////////
    @FXML private Pane panel0;  //Panel de busqueda
    @FXML private Pane panel1;  //Panel de tabla de busqueda
    @FXML private Pane panel2;  //Panel de edición
    @FXML private Pane panel3;  //Panel de detalles y EDICION
    @FXML private Pane panel4;  //Panel de busqueda avanzada
    @FXML private Pane panel10; //Panel de inserción
    @FXML private Pane panel11; //Panel inserción general
    @FXML private Pane panel12; //Panel inseción ubicación
    ////////////Tab Pane//////////////
    @FXML private TabPane tabPaneTables;
    @FXML private TabPane tabPanel0;
    //////////////Tab////////////////
    @FXML private Tab tabTablesGeneral;
    @FXML private Tab tabTablesUbicacion;
    @FXML private Tab tabTablesAdvance;
    //Listas
    //@FXML private ListView<String> listaEtiGen;
    //@FXML private ListView<String> listaEtiUbi;
    //@FXML private ListView<String> listaValGen;
    @FXML private ListView<String> listaValUbi;
    //Variables adicionales
    private DBConnection myConn; //Para la conexión con la base de datos
    private PreparedStatement pstmt = null; //Para ejecutar staments en la base de datos
    private ResultSet resultset; //Almacena resultados de búsqueda
    private Statement stmt = null;
    private ArticuloInventario copiaArticuloInv;

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

    ///////////////////////////////////Columnas de la tabla de busqueda avanzada///////////////////////////////////////////////
    //General
    @FXML private TableColumn<Articulo, String> equipoColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> nombreColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, Integer> num_inventarioColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> marcaColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> modeloColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> num_serieColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> descripcionColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> estadoColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> final_userColumnA = new TableColumn<>();
    //Ubicacion
    @FXML private TableColumn<Articulo, String> edificioUbiColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> pisoUbiColumnA = new TableColumn<>();
    @FXML private TableColumn<Articulo, String> cubiculoUbiColumnA = new TableColumn<>();

    ////////////////////////////////////////////Metodos de búsquda/////////////////////////////////////////////////////////
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

    //Tabla de datos por busqueda avanzada
    private void storeAdvanceData(){
        try{
            datosAdvance = FXCollections.observableArrayList();
            while (resultset.next()) {
                datosAdvance.add(new Articulo(
                        resultset.getInt("id"),
                        resultset.getString("equipo")/*,
                        resultset.getString("nombre"),
                        resultset.getInt("num_inventario"),
                        resultset.getString("marca"),
                        resultset.getString("modelo"),
                        resultset.getString("num_serie"),
                        resultset.getString("descripcion"),
                        resultset.getString("estado"),
                        resultset.getString("final_user"),
                        resultset.getString("edificio"),
                        resultset.getString("piso"),
                        resultset.getString("cubiculo")*/));
            }
            tableArticulo.setItems(datosAdvance);
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
            if(campo.equals("num_inventario") || campo.equals("num_serie")){
                pstmt.setString(1, commandField.getText());
            }else if(campo.equals("marca") || campo.equals("modelo") || campo.equals("estado")){
                pstmt.setString(1, menuModels1.getText());
            }else if(campo.equals("todo")){
                pstmt = myConn.conn.prepareStatement("SELECT id, equipo, nombre, num_inventario, marca, modelo, " +
                        "num_serie, descripcion, estado, final_user FROM tabla_inventario WHERE id not like ''");
            }

            resultset = pstmt.executeQuery();
            //System.out.println(" yes ");
            //storeData();
            this.campo = campo;
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

    //Para visualizar información en la tabla de búsqueda avanzada
    private void consultaMultiplePor(){
        try{
            String s1, s2, s3, s4;
            ResultSet r1 = null, r2 = null, r3 = null;
            s2 = menuAdvance1.getText();
            //s3 = menuButton3.getText();
            //s4 = menuButton4.getText();
            //Casos
            //1.- Si se realiza busqueda donde solo un campo está lleno
            if (!s2.equals("...")){
                //Si la tabla es la general
                System.out.println("The consulting");
                if(s2.equals("# Inventario") || s2.equals("Equipo") || s2.equals("Nombre")){
                    System.out.println("\nConsulta avanzada...");
                    pstmt = myConn.conn.prepareStatement("select ti.id, ti.equipo, ti.num_inventario " +
                            "from tabla_inventario as ti where " + menuAdvance1.getText() +
                            " =?");
                    pstmt.setString(1, advancedTF1.getText());
                    //r1 = pstmt.executeQuery();
                    resultset = pstmt.executeQuery();
                }
            }else{
                System.out.println("No enter");
            }
        }catch(SQLException e){
            System.out.println("Error durante la consulta de datos");
            e.printStackTrace();
        }
    }

    ////////////////////////////Inserción//////////////////////////////////

    private void insercionPor(int i){
        switch (i){
            case 0: //Datos generales
                try{
                    pstmt = myConn.conn.prepareStatement("INSERT INTO tabla_inventario (id, equipo, nombre, num_inventario, marca, modelo, " +
                            "num_serie, descripcion, estado, final_user) VALUES(?,?,?,?,?,?,?,?,?,?)");
                    int id = random.nextInt();
                    pstmt.setInt(1,id);
                    pstmt.setString(2, insertFieldEquipo.getText());
                    pstmt.setString(3, insertFieldNom.getText());
                    pstmt.setInt(4, Integer.parseInt(insertFieldNumInv.getText()));
                    pstmt.setString(5, menuInsertMarca.getText());
                    pstmt.setString(6, menuInsertModelo.getText());
                    pstmt.setString(7, insertFieldNumSer.getText());
                    pstmt.setString(8, insertFieldDesc.getText());
                    pstmt.setString(9, menuInsertEstado.getText());
                    pstmt.setString(10, menuInsertFinUser.getText());
                    pstmt.executeUpdate();
                    System.out.println("Inserción dato general exitosa");
                    labelInsert.setTextFill(okColor);
                    labelInsert.setText("Insercion exitosa");
                }catch(SQLException e){
                    System.out.println("Error al ingresar datos, revise que los campos sean llenados correctamente");
                }catch(NumberFormatException e){
                    System.out.println("Error tipo de datos no coincide, texto en lugar de números");
                    labelInsert.setTextFill(errorColor);
                    labelInsert.setText("Error de inserción");
                    insertFieldNumInv.setBorder(new Border(new BorderStroke(errorColor, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
                }
                break;
            default:
                break;
        }
    }

    ///////////////////////////Borrado/////////////////////////////////

    public void borrado(){
        try {
            if(tableView.isVisible()) {
                int idAux = tableView.getSelectionModel().getSelectedItem().getId();
                pstmt = myConn.conn.prepareStatement("DELETE FROM tabla_inventario WHERE id = ?");
                pstmt.setInt(1, idAux);
                pstmt.executeUpdate();
                System.out.println("Borrado exitoso");
            }
        }
        catch(SQLException e){
            System.out.println("Error registro no seleccionado");
            e.printStackTrace();
        }
    }

    /////////////////////Menu Button Selectors/////////////////////////
    //Sobre el primer selector, que se encuentra arriba del lado izquierdo, dependiendo de la opcion que se escoja
    // se tendrá que mostrar un text field u otro menu button que se encontrara al lado de este
    public void selectButton(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        System.out.println(s);
        switch(s){
            case "# Inventario":
                commandField.setVisible(true);
                closeMenuButton(0);
                closeHbox();
                break;
            case "# Serial":
                commandField.setVisible(true);
                closeMenuButton(0);
                closeHbox();
                break;
            case "Modelo":
                commandField.setVisible(false);
                closeMenuButton(0);
                openMenuButton(2);
                closeHbox();
                break;
            case "Marca":
                commandField.setVisible(false);
                closeMenuButton(0);
                closeHbox();
                break;
            case "Ubicación":
                commandField.setVisible(false);
                openHbox();
                closeMenuButton(0);
                openMenuButton(1);
                break;
            case "Estado del bien":
                commandField.setVisible(false);
                closeMenuButton(0);
                closeHbox();
                break;
            default:
                break;
        }
        menuButton.setText(s);
    }

    /*
    Función génerica para el menubutton, al momento de escoger una opcion el menubutton
    se actualiza con el nombre de la opcion escogida
    */
    public void selectButton2(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuAdvance1.setText(s);
    }
    public void selectButtonModels1(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuModels1.setText(s);
    }
    public void selectButtonUbi1(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuEdificio.setText(s);
    }
    public void selectButtonUbi2(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuPiso.setText(s);
    }
    public void selectButtonUbi3(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuSalon.setText(s);
    }

    public void selectMBInsertMarca(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuInsertMarca.setText(s);
    }

    public void selectMBInsertModelo(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuInsertModelo.setText(s);
    }

    public void selectMBInsertEdo(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuInsertEstado.setText(s);
    }

    public void selectMBInsertFinUser(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        menuInsertFinUser.setText(s);
    }

    public void insertSelectMB(ActionEvent ae){
        String s = ((MenuItem)ae.getSource()).getText();
        System.out.println(s);
        switch (s){
            case "Datos Generales":
                openPanel(11);
                break;
            case "Ubicación":
                break;
            default:
                System.out.println("No aplica");
                break;
        }
        menuInsercion.setText(s);
    }

    //////////////////////////////////////////////Botones//////////////////////////////////////////////

    ////////////////////Actualización////////////////////
    //Botón de guardado, que aparece en el panel de edición
    public void updateDatos(){
        try{
            System.out.println("\nConsultando datos espere por favor...");
            pstmt = myConn.conn.prepareStatement("UPDATE tabla_inventario SET equipo=?, num_inventario=? WHERE equipo=? and " +
                    "num_inventario=?");
            //System.out.println("Equipo "+equipoLabel.getText()+" numInv "+numInvLabel.getText());
            pstmt.setString(1, lEq.getText());
            pstmt.setInt(2, Integer.parseInt(lNumInv.getText()));
            pstmt.setString(3,copiaArticuloInv.getEquipo());
            pstmt.setInt(4,copiaArticuloInv.getNumInventario());
            pstmt.executeUpdate();
            //System.out.println(" yes ");
            closePanel(3);
            closePanel(1);
            openPanel(0);
            System.out.println("Actualización exitosa");
        }catch(SQLException e){
            System.out.println("Error durante la actualizacion de datos");
            e.printStackTrace();
        }
    }

    //Para el botón de edición, el icono de lapíz en el panel de tabla
    public void editButtonAction(){
        System.out.println("Your clicking me, i am button "+ tableView.getSelectionModel().getSelectedItem().getEquipo());
        copiaArticuloInv = tableView.getSelectionModel().getSelectedItem();
        equipoFieldUpdate.setText(copiaArticuloInv.getEquipo());
        equipoLabel.setText(copiaArticuloInv.getEquipo());
        numInvFieldUpdate.setText(Integer.toString(copiaArticuloInv.getNumInventario()));
        numInvLabel.setText(Integer.toString(copiaArticuloInv.getNumInventario()));
        closePanel(1);
        openPanel(2);
    }

    /*Para modificar la etiqueta, que muestra un cambio previo al salvado de información que se encuentran en el panel de edición*/
    public void editTextField(KeyEvent ke){
        try{
            if(ke.getCode() == KeyCode.ENTER){
                System.out.println("Id"+((TextField)ke.getSource()).getId());
                if(((TextField)ke.getSource()).getId().equals(equipoFieldUpdate.getId())){
                    equipoLabel.setText(equipoFieldUpdate.getText());
                }else{
                    System.out.println("Nop");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editClicking(MouseEvent ae){
        String id = ((ImageView)ae.getSource()).getId();
        switch(id){
            case "editEquipo1":
                equipoFieldUpdate.setVisible(true);
                break;
            case "editNumInv1":
                numInvFieldUpdate.setVisible(true);
                break;
            default:
                break;
        }
    }

    public void editParam(KeyEvent ke){
        if(ke.getCode() == KeyCode.ENTER){
            TextField textAux = (TextField)ke.getSource();
            System.out.println("Print print id "+ textAux.getId());
            switch(textAux.getId()){
                case "equipoFieldUpdate":
                    lEq.setText(textAux.getText());
                    break;
                default:
                    break;
            }
        }
    }
    //////////////////Generador de archivos//////////////////////////

    public void crearCSV(){
        consultaUnicaPor("todo");
        try {
            FileWriter fw = new FileWriter("inventarioCSV.csv");
            while (resultset.next()) {
                fw.append(Integer.toString(resultset.getInt("id"))+","+
                        resultset.getString("equipo")+","+
                        resultset.getString("nombre")+","+
                        resultset.getInt("num_inventario")+","+
                        resultset.getString("marca")+","+



                        resultset.getString("modelo")+","+
                        resultset.getString("num_serie")+","+
                        resultset.getString("descripcion")+","+
                        resultset.getString("estado")+","+
                        resultset.getString("final_user")+SALTO_D_LINEA);
            }
            fw.flush();
            fw.close();
            System.out.println("CSV de Busqueda Generado");
            labelInicio.setTextFill(okColor);
            labelInicio.setText("CSV Generado");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    //Para implementarlo hace falta crear un nuevo stage, problemas que podrían llegar a aparecer:
    //Multiples ventanas al dar click
    public void generarCSV(){
        DirectoryChooser seleccionar = new DirectoryChooser();
        File archivo = null;
        FileInputStream entrada;
        String texto = "";
        consultaUnicaPor("todo");
        try {
            FileWriter fw = new FileWriter("inventarioCSV.csv");
            //seleccionar.setFileSelectionMode(0);
            seleccionar.showDialog(null, "Guardar");
            if(seleccionar.showDialog(null, "Guardar") == DirectoryChooser .APPROVE_OPTION) {
                archivo = seleccionar.getSelectedFile();
                System.out.println(archivo.getName());
                seleccionar.
                seleccionar.setFileFilter(new FileTypeFilter(".csv","CSV File"));
                if (archivo.canRead()) {
                    if (archivo.getName().endsWith("csv")) {
                    }
                }
            }
            while (resultset.next()) {
                fw.append(Integer.toString(resultset.getInt("id"))+","+
                        resultset.getString("equipo")+","+
                        resultset.getString("nombre")+","+
                        resultset.getInt("num_inventario")+","+
                        resultset.getString("marca")+","+



                        resultset.getString("modelo")+","+
                        resultset.getString("num_serie")+","+
                        resultset.getString("descripcion")+","+
                        resultset.getString("estado")+","+
                        resultset.getString("final_user")+SALTO_D_LINEA);
            }
            fw.flush();
            fw.close();
            System.out.println("CSV de Busqueda Generado");
            labelInicio.setTextFill(okColor);
            labelInicio.setText("CSV Generado");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void cargarCSV(){
        JFileChooser seleccionar = new JFileChooser();
        File archivo = null;
        FileInputStream entrada;
        String texto = "";
        try{
            //seleccionar.setFileSelectionMode(0);
            if(seleccionar.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
                archivo = seleccionar.getSelectedFile();
                System.out.println(archivo.getName());
                if (archivo.canRead()) {
                    if (archivo.getName().endsWith("csv")) {
                        System.out.println("En proceso de leer archivo");
                        /*Abre un flujo de lectura a el fichero*/
                        BufferedReader leeArchivo = new BufferedReader(new FileReader(archivo));
                        String Slinea, datos = "";
                        int col = 1;
                        /*Lee el fichero linea a linea hasta llegar a la ultima*/
                        pstmt = myConn.conn.prepareStatement("insert into tabla_inventario" +
                                "(id,equipo,nombre,num_inventario,marca,modelo,num_serie,descripcion,estado,final_user)" +
                                " values(?,?,?,?,?,?,?,?,?,?)");
                        while ((Slinea = leeArchivo.readLine()) != null) {
                            /*Imprime la linea leida*/
                            for (int x=0;x<Slinea.length();x++) {
                                if (Slinea.charAt(x) == ',') {
                                    //System.out.println("Caracter " + x + ": " + Slinea.charAt(x));
                                    switch (col){
                                        case 1: //Id
                                            pstmt.setInt(1,Integer.parseInt(datos));
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 2: //Equipo
                                            pstmt.setString(2,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 3: //Nombre
                                            pstmt.setString(3,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 4: //#Inventario
                                            pstmt.setInt(4,Integer.parseInt(datos));
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 5: //Marca
                                            pstmt.setString(5,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 6: //Modelo
                                            pstmt.setString(6,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 7: //#Serie
                                            pstmt.setString(7,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 8: //Descripcion
                                            pstmt.setString(8,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        case 9: //Estado
                                            pstmt.setString(9,datos);
                                            //System.out.println(datos);
                                            datos="";
                                            col++;
                                            break;
                                        default:
                                            System.out.println("Error rango de valores por renglon excede el límite permitido");
                                            break;
                                    }
                                } else {
                                    datos += Slinea.charAt(x);
                                }
                            }
                            try{
                                if(col==10) {
                                    pstmt.setString(10, datos);
                                    //System.out.println(datos);
                                    datos = "";
                                    pstmt.execute();
                                }
                            }catch (SQLIntegrityConstraintViolationException e){
                                labelInicio.setTextFill(warningColor);
                                labelInicio.setText("Advertencia, se esta ingresando duplicados a la base de datos");
                                System.out.println("Advertencia, se esta ingresando duplicados a la db");
                            }
                            col = 1;
                        }
                        /*Cierra el flujo*/
                        leeArchivo.close();
                        System.out.println("Datos del CSV ingresados");
                        labelInicio.setTextFill(okColor);
                        labelInicio.setText("Datos del CSV ingresados");
                    } else {
                        System.out.println("Formato de archivo no valido");
                        labelInicio.setTextFill(errorColor);
                        labelInicio.setText("Formate o extension de archivo no valido");
                    }
                } else {
                    System.out.println("Archivo no válido");
                }
            }
            //entrada = new FileInputStream(archivo);
        }catch(FileNotFoundException e){
            System.out.println("Archivo no encontrado");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("Error producido durante la lectura de archivo");
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("Error durante la insercion de datos en la db alguno de los parametros\n" +
                    "fue ingresado de forma incorrecta o hace falta, verificar las reglas de la db");
            labelInicio.setTextFill(errorColor);
            labelInicio.setText("Error verificar que el CSV este bien escrito");
            e.printStackTrace();
        }catch (NumberFormatException e){
            System.out.println("Error en el formato en alguno de los parametros,\n" +
                    "revisa que los tipos de datos correspondan");
            labelInicio.setTextFill(errorColor);
            labelInicio.setText("Error, verificar que los datos en el csv sean del tipo correcto numeros y/o caracteres");
            e.printStackTrace();
        }
    }

    public void crearExcel(){
        consultaUnicaPor("todo");
        try {
            XSSFWorkbook book = new XSSFWorkbook(); //Se crea el archivo excel con ruta
            Sheet pagina = book.createSheet("Datos Generales");
            int numFila = 0;
            Row fila = pagina.createRow(numFila);
            Cell celda = null;
            for(int i = 0; i < ENCABEZADOS.length; i++){
                celda = fila.createCell(i);
                celda.setCellValue(ENCABEZADOS[i]);
            }
            File excelFile = new File("inventarioExcel.xlsx");
            while (resultset.next()) {
                numFila++;
                fila = pagina.createRow(numFila);
                celda = fila.createCell(0);
                celda.setCellValue(Integer.toString(resultset.getInt("id")));
                celda = fila.createCell(1);
                celda.setCellValue(resultset.getString("equipo"));
                celda = fila.createCell(2);
                celda.setCellValue(resultset.getString("nombre"));
                celda = fila.createCell(3);
                celda.setCellValue(resultset.getInt("num_inventario"));
                celda = fila.createCell(4);
                celda.setCellValue(resultset.getString("marca"));
                celda = fila.createCell(5);
                celda.setCellValue(resultset.getString("modelo"));
                celda = fila.createCell(6);
                celda.setCellValue(resultset.getString("num_serie"));
                celda = fila.createCell(7);
                celda.setCellValue(resultset.getString("descripcion"));
                celda = fila.createCell(8);
                celda.setCellValue(resultset.getString("estado"));
                celda = fila.createCell(9);
                celda.setCellValue(resultset.getString("final_user"));
            }

            FileOutputStream fileOuS = new FileOutputStream(excelFile);
            if (excelFile.exists()) { // Si el archivo existe lo eliminaremos
                excelFile.delete();
                System.out.println("Registro anterior eliminado!");
            }
            book.write(fileOuS);
            //book.write(fileOuS);
            fileOuS.flush();
            fileOuS.close();
            System.out.println("Excel Generado!");
            labelInicio.setTextFill(okColor);
            labelInicio.setText("Excel Generado");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Archivo no encontrado");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error generado durante la consulta de datos");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear el archivo excel");
        } catch (NoClassDefFoundError e){
            e.printStackTrace();
            System.out.println("Revisar si las librerias han sido agregadas y de forma correcta\n" +
                    "para intellij ir a project structure, seleccionar libraries y agregar la libreria\n" +
                    "correspondiente que aun falte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearCSVBusqueda(){
        try{
            if(tableView.isVisible()){
                FileWriter fw = new FileWriter("busquedaCSV.csv");
                for(ArticuloInventario a:datos){
                    consultaUnicaPor("todo");
                    fw.append(a.getId()+","+
                            a.getEquipo()+","+
                            a.getNombre()+","+
                            a.getNumInventario()+","+
                            a.getMarca()+","+
                            a.getModelo()+","+
                            a.getNum_serie()+","+
                            a.getDescripcion()+","+
                            a.getEstado()+","+
                            a.getFinal_user()+SALTO_D_LINEA);
                        fw.flush();
                        fw.close();
                        System.out.println("CSV de Busqueda Generado");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ////////////Insercion de datos////////////////

    public void insertar(){
        openPanel(10);
        closePanel(0);
    }

    public void saveInsercion(){
        switch(menuInsercion.getText()){
            case "Datos Generales":
                //System.out.println("Se han insertado en la base los datos del registro de forma correcta");
                insercionPor(0);
                break;
            case "Ubicación":
                //System.out.println("Se han insertado en la base los datos de ubicación de forma correcta");
                break;
            default:
                System.out.println("La inserción no se pudo realizar");
                break;
        }
    }

    /////////////////////Busqueda avanzada////////////////////

    public void addCampo(){
        switch (contCampos){
            case 1:
                campo2.setVisible(true);
                contCampos++;
                break;
            case 2:
                contCampos++;
                break;
            default:
                break;
        }
    }

    ////////////////////////////Busquedas Activadores/////////////////////////////////////
    /*Para iniciar la busqueda una vez se de ENTER en el textfield principal*/
    public void searchForTextField1(KeyEvent ke){
        try{
            if(ke.getCode() == KeyCode.ENTER){
               switch (menuButton.getText()){
                   case "# Inventario":
                       System.out.println("Busqueda por inventario");
                       consultaUnicaPor("num_inventario");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       disableAllTabs();
                       openTabPane(0);
                       break;
                   case "# Serial":
                       System.out.println("Busqueda por serial");
                       consultaUnicaPor("num_serie");
                       storeData();
                       closePanel(0);
                       openPanel(1);
                       disableAllTabs();
                       openTabPane(0);
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

    public void searchClickingMouse1(){
        switch (menuButton.getText()){
            case "# Inventario":
                System.out.println("Busqueda por inventario");
                consultaUnicaPor("num_inventario");
                storeData();
                tabPaneTables.getSelectionModel().select(0);
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(0);
                break;
            case "# Serial":
                System.out.println("Busqueda por serial");
                consultaUnicaPor("num_serie");
                storeData();
                tabPaneTables.getSelectionModel().select(0);
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(0);
                break;
            case "Modelo":
                System.out.println("Busqueda por modelo");
                consultaUnicaPor("modelo");
                storeData();
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(0);
                break;
            case "Marca":
                consultaUnicaPor("marca");
                storeData();
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(0);
            case "Ubicación":
                System.out.println("Busqueda por ubicacion");
                Listado[] listadoTemp = new Listado[3];
                listadoTemp[0] = new Listado("edificio",menuEdificio.getText());
                listadoTemp[1] = new Listado("piso",menuPiso.getText());
                listadoTemp[2] = new Listado("cubiculo",menuSalon.getText());
                consultaMultiplePorUbicacion(listadoTemp);
                tabPaneTables.getSelectionModel().select(1);
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(1);
                break;
            case "Estado del bien":
                System.out.println("Busqueda por estado del bien");
                consultaUnicaPor("estado");
                storeData();
                closePanel(0);
                openPanel(1);
                disableAllTabs();
                openTabPane(0);
                break;
            default:
                System.out.println("No contemplado");
                break;
        }
    }

    /*Para iniciar la busqueda una vez se de ENTER en el textfield principal*/
    public void search2(KeyEvent ke){
        String s1, s2, s3;
        try{
            if(ke.getCode() == KeyCode.ENTER){
                //if (menuButton2.getText() != "..."|| menuButton3.getText() != "..." || menuButton4.getText() != "..."){
                if (!(menuAdvance1.getText().equals("..."))){
                    consultaMultiplePor();
                    if(resultset!=null)
                        storeAdvanceData();
                    s1 = menuAdvance1.getText();
                    closePanel(4);
                    openPanel(1);
                    disableAllTabs();
                    openTabPane(2);
                    tabPaneTables.getSelectionModel().select(2);
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
                copiaArticuloInv = tableView.getSelectionModel().getSelectedItem();
                //String auxId = Integer.toString(tableView.getSelectionModel().getSelectedItem().getId());
                String auxId = Integer.toString(copiaArticuloInv.getId());
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx Id "+auxId);
                consultaUnicaPor("id", auxId,"tabla_inventario");
                //System.out.println("pppppppppppppppppppppppppppp "+resultset.getString("equipo"));
                //System.out.println("--------------------------------------------------- " +aux.get(0));
                //listaValGen.setItems(asignacionDeResultados(0));
                asignacionDeResultados(0);
                consultaUnicaPor("id", auxId,"tabla_ubicacion");


                //System.out.println("pppppppppppppppppppppppppppp "+resultset.getString("equipo"));
                listaValUbi.setItems(asignacionDeResultados(1));
            }else if(tableUbicacion.isVisible()){
                System.out.println("Your clicking me, i am button " + tableUbicacion.getSelectionModel().getSelectedItem().getId());
                String auxId = Integer.toString(tableUbicacion.getSelectionModel().getSelectedItem().getId());
                consultaUnicaPor("id", auxId,"tabla_ubicacion");
                asignacionDeResultados(1);
                consultaUnicaPor("id", auxId,"tabla_inventario");
                //listaValGen.setItems(asignacionDeResultados(0));
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("Advertencia : El renglon de informacion no tiene datos adicionales ademas de sus datos generales");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //listaEtiGen.setItems(listaGenerales);
        //listaEtiUbi.setItems(listaUbicacion);
    }

    //Método de apoyo al metodo anterior, se utilizan para almacenar los datos de busqueda en los listados gráficos
    private ObservableList<String> asignacionDeResultados(int idTabla) {
        ObservableList<String> aux = null;
        try{
            resultset.next();
            switch (idTabla){
                case 0:
                    /*
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
                    System.out.println(aux.get(0));*/
                    lEq.setText(resultset.getString("equipo"));
                    lNom.setText(resultset.getString("nombre"));
                    lNumInv.setText(Integer.toString(resultset.getInt("num_inventario")));
                    lMarc.setText(resultset.getString("marca"));
                    lMod.setText(resultset.getString("modelo"));
                    lNumSer.setText(resultset.getString("num_serie"));
                    lDesc.setText(resultset.getString("descripcion"));
                    lEst.setText(resultset.getString("estado"));
                    lUsFin.setText(resultset.getString("final_user"));
                    break;
                case 1:
                    /*
                    aux = FXCollections.observableArrayList(
                            resultset.getString("edificio"),
                            resultset.getString("piso"),
                            resultset.getString("cubiculo"));
                            */
                    lEdi.setText(resultset.getString("edificio"));
                    lPiso.setText(resultset.getString("piso"));
                    lCub.setText(resultset.getString("cubiculo"));
                    break;
                default:
                    break;
            }
        }catch(SQLException se){

        }catch(NullPointerException ne){
            System.out.println("Resultado vacío");
        }
        return aux;
    }
    ///////////////////////Activadores de elementos(menubutton, hbox)///////////////////////////////
    public void openMenuButton(int caso){
        switch(caso){
            case 1: //Ubicacion
                menuEdificio.setVisible(true);
                menuPiso.setVisible(true);
                menuSalon.setVisible(true);
                break;
            case 2:
                menuModels1.setVisible(true);
                break;
            default:
                break;
        }
    }
    public void closeMenuButton(int caso){
        switch(caso){
            case 0: //Ubicacion
                menuEdificio.setVisible(false);
                menuPiso.setVisible(false);
                menuSalon.setVisible(false);
                menuModels1.setVisible(false);
                break;
            case 1:
                break;
            default:
                break;
        }
    }
    public void openHbox(){
        searchBox.setVisible(true);
    }
    public void closeHbox(){ searchBox.setVisible(false);}
    ///////////////////////Activadores de tabla/////////////////////////////////////
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

    public void Panel1a0(){
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

    public void Panel10a0(){
        openPanel(0);
        closePanel(10);
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
            case 10:
                panel10.setVisible(true);
                break;
            case 11:
                panel11.setVisible(true);
                break;
            case 12:
                panel12.setVisible(true);
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
            case 10:
                panel10.setVisible(false);
                break;
            case 11:
                panel11.setVisible(false);
                break;
            case 12:
                panel12.setVisible(false);
                break;
            default:
                break;
        }
    }

    //////////////////////Tab Pane///////////////////////////
    public void openTabPane(int p){
        switch (p){
            case 0:
                tabTablesGeneral.setDisable(false);
                break;
            case 1:
                tabTablesUbicacion.setDisable(false);
                break;
            case 2:
                tabTablesAdvance.setDisable(false);
                break;
            default:
                break;
        }
    }
    public void closeTabPane(int p){
        switch (p){
            case 0:
                tabTablesGeneral.setDisable(true);
                break;
            case 1:
                tabTablesUbicacion.setDisable(true);
                break;
            case 2:
                tabTablesAdvance.setDisable(true);
            default:
                break;
        }
    }
    public void disableAllTabs() {
        tabTablesGeneral.setDisable(true);
        tabTablesUbicacion.setDisable(true);
        tabTablesAdvance.setDisable(true);
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
        //Tabla avanzada
        //General
        equipoColumnA.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        nombreColumnA.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        num_inventarioColumnA.setCellValueFactory(new PropertyValueFactory<>("num_inventario"));
        marcaColumnA.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumnA.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        num_serieColumnA.setCellValueFactory(new PropertyValueFactory<>("num_serie"));
        descripcionColumnA.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        estadoColumnA.setCellValueFactory(new PropertyValueFactory<>("estado"));
        final_userColumnA.setCellValueFactory(new PropertyValueFactory<>("final_user"));
        //Ubicación
        edificioUbiColumnA.setCellValueFactory(new PropertyValueFactory<>("edificio"));
        pisoUbiColumnA.setCellValueFactory(new PropertyValueFactory<>("piso"));
        cubiculoUbiColumnA.setCellValueFactory(new PropertyValueFactory<>("cubiculo"));
    }

}

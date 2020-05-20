package sample;
import versiones_anteriores.Listado;

import java.sql.*;

public class DBConnection {
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";
    static final String DB_NAME_INVENTARIO = "test";
    static final String DB_NAME_USERS = "test";
    static final String TABLE_USERS_NAME = "users";
    private int confirm = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet resultset = null;
    Listado lis = new Listado();
    String in = null;

    public DBConnection(){

    }


    public void conectar() {
        try {
            System.out.println("Conectando con la base de datos...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fallo de conexion con el servidor");
        }
    }

    public void iniciarSesion(String user, String pass){
        try{
            stmt = conn.createStatement();
            stmt.executeQuery("USE "+DB_NAME_USERS);//Se indica qué base de datos de usara
            pstmt = conn.prepareStatement("SELECT id FROM users WHERE usuario=? and password=?");
            pstmt.setString(1,user);
            pstmt.setString(2,pass);
            resultset = pstmt.executeQuery();
            if(resultset.next()){
                confirm = 1;
                System.out.println("Se ha ingresado con exito a la base de datos de usuarios");
            }else{
                confirm = 0;
                System.out.println("Usuario o contrasena invalidos");
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Base datos de usuarios no valida o\nFallo de entrada: usuario y contraseña no validos");
        }
    }

    public void close(){
        try{
            if(stmt != null)
                stmt.close();
            if(conn != null)
                conn.close();
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fallo al cerrar statement y connection");
        }
    }

    public int getConfirm(){
        return confirm;
    }

    public ResultSet consultar(String tableName) {
        try {
            pstmt = conn.prepareStatement("SELECT * FROM ?");
            pstmt.setString(1, tableName);
            resultset = pstmt.executeQuery();//Se muestran las tablas de la base de datos
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error tabla de datos inexistente");
        } catch(NullPointerException ne){
            System.out.println("Error referencia inexistente");
        }
        return resultset;
    }
}

package sample;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.*;

public class DBConnection {

    private static DBConnection dbConnection;

    static {dbConnection = new DBConnection();}

    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";
    static final String DB_NAME_USERS = "test";
    private boolean correctCon = false;
    //private int confirm = 0;
    Connection conn = null;
    //PreparedStatement pstmt = null;
    Statement stmt = null;
    //ResultSet resultset = null;

    /*
    private DBConnection() {
        try {
            System.out.println("Conectando con el servidor...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conexion exitosa");
        } catch (CommunicationsException e){
            System.out.println("Error, servidor caído");
            correctCon = false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fallo durante la consulta con la base de datos");
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public DBConnection(String user, String pass) {
        try {
            System.out.println("Conectando con el servidor...");
            conn = DriverManager.getConnection(DB_URL, user, pass);
            System.out.println("Conexion exitosa");
        } catch (CommunicationsException e){
            System.out.println("Error, servidor caído");
            correctCon = false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fallo durante la consulta con la base de datos");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public DBConnection(){

    }

    public void iniciarSesion(String user, String pass){
        try {
            System.out.println("Conectando con el servidor...");
            conn = DriverManager.getConnection(DB_URL, user, pass);
            System.out.println("Conexion exitosa");
            stmt = conn.createStatement();
            stmt.executeQuery("USE "+DB_NAME_USERS);//Se indica qué base de datos de usara
            correctCon = true;
        } catch (CommunicationsException e){
            System.out.println("Error de conexion");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error usuario o contraseña incorrectos");
        } catch (Exception e){
            e.printStackTrace();
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

    //public int getConfirm(){return confirm;}

    //////////////////////////////////////GET/////////////////////////////

    public Connection getConn(){
        return this.conn;
    }

    public static DBConnection getDbConnection(){return dbConnection;}

    public boolean getCorrectCon(){
        return  correctCon;
    }

}

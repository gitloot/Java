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
    private boolean correctCon = true;
    private int confirm = 0;
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet resultset = null;

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
                System.out.println("Se ha ingresado con exito a la base de datos");
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


    //////////////////////////////////////GET/////////////////////////////

    public Connection getConn(){
        return this.conn;
    }

    public static DBConnection getDbConnection(){
        return dbConnection;
    }

    public boolean getCorrectCon(){
        return  correctCon;
    }

}

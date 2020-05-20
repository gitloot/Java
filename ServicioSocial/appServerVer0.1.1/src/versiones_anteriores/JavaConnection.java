package versiones_anteriores;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author servicio social
 */

//STEP 1. Import required packages

import sample.Consulta;

import java.sql.*;
import java.util.Scanner;

public class JavaConnection {
   // JDBC driver name and database URL
   //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "admin";
   
   public void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   ResultSet resultset = null;
   Listado lis = new Listado();
   Scanner entrada = null;
   String in = null;
   try{
      //STEP 2: Register JDBC driver 
	  //Class.forName("com.mysql.cj.jdbc.Driver");	//NO UTILIZADO EN VERSIONES RECIENTES, DESPUES DE VERSION 5...
	  
      //STEP 3: Open a connection
	  System.out.println("Conectando con la base de datos...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	  System.out.println("Conexion exitosa");
	  Consulta1 consult = new Consulta1(conn);
       try{
           System.out.println("Ingrese el nombre de la base de datos");
           System.out.print("");
           entrada = new Scanner(System.in);
           in = entrada.nextLine();
           stmt = conn.createStatement();
           stmt.executeQuery("USE "+in);//Se indica qué base de datos de usará
           System.out.println("Se ha ingresado con exito a la base de datos");
       }catch(SQLException e){
           System.out.println("Error al seleccionar base de datos.\nBase de datos inexistente\n");
           e.printStackTrace();
       }
       lis.getListado(0);
       entrada = new Scanner(System.in);
       in = entrada.nextLine();
       while(in.equals("s")==false) {
            //Insertar
           switch(in){
               case "i":
                   consult.Insertar();
                    break;
               case "b":
                   consult.Eliminar();
                   break;
               case "c":
                   consult.Consultar();
                   break;
               case "m":
                   consult.Modificar();
                   break;
               default:
                   System.exit(0);
                   break;
            }
            System.out.print("\nPulse cualquier tecla para continuar");
            entrada = new Scanner(System.in);
            lis.getListado(1);
            System.out.println("Operacion terminada, seleccione la siguiente operacion\n");
            System.out.print("");
            entrada = new Scanner(System.in);
            in = entrada.nextLine();
       }

       if(stmt != null)
        stmt.close();
       if(conn != null)
        conn.close();
    System.out.println("Fin de la ejecucion del programa");
   }catch (SQLException e){
       System.out.println("Error al realizar operaciones en la base de datos");
       while (e != null){
           System.out.println("SQLState: " + e.getSQLState());
           System.out.println("Mensaje: " + e.getMessage());
           System.out.println("COdigo: " + e.getErrorCode());
           e.getNextException();
           System.out.println(" -------------------------------------- ");
       }
   }catch (NumberFormatException e){
       System.out.println("Error numErico:\n" + e.getMessage());
       e.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
	  System.err.println("Fallo de conexion");
      e.printStackTrace();
   }
   }
}

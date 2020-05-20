package sockets.cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import sockets.conexion.Conexion;

public class Cliente extends Conexion
{
    public Cliente() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Metodo para iniciar el cliente
    {
        try
        {
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(cs.getOutputStream());

            //Probando una entrada

            //Se enviarán dos mensajes
            for (int i = 0; i < 2; i++)
            {

                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF("Este es el mensaje numero " + (i+1) + "\n");
                salidaServidor.writeUTF(new Scanner(System.in).nextLine()+"\n");
            }

            cs.close();//Fin de la conexión

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}

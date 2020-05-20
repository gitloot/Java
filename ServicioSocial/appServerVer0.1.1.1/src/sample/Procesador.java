package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Procesador {

    private Button procesador;

    public Procesador(){}

    public Procesador(String procesador){
        this.procesador = new Button(procesador);
    }

    //////////////////Set/////////////////////////

    public void setProcesador(String procesador){
        this.procesador.setText(procesador);
    }

    //////////////////Get/////////////////////////

    public String getProcesador(){
        return this.procesador.getText();
    }

    /////////////////Property/////////////////////

    public Button procesadorProperty(){
        return this.procesador;
    }
}

package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Procesador {

    private StringProperty proces_id;

    public Procesador(){}

    public Procesador(String procesador){
        this.proces_id = new SimpleStringProperty(procesador);
    }

    //////////////////Set/////////////////////////

    public void setProcesador(String proces_id){
        this.proces_id.setValue(proces_id);
    }

    //////////////////Get/////////////////////////

    public String getProcesador(){
        return this.proces_id.getValue();
    }

    /////////////////Property/////////////////////

    public StringProperty procesadorProperty(){
        return this.proces_id;
    }
}

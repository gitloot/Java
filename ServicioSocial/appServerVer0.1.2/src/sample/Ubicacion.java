package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ubicacion {

    private int id;
    private StringProperty equipo;
    private IntegerProperty numInventario;
    private StringProperty edificio;
    private StringProperty piso;
    private StringProperty cubiculo;

    public Ubicacion(){}

    public Ubicacion(String equipo, Integer numInventario, String edificio, String piso, String cubiculo){
        this.equipo = new SimpleStringProperty(equipo);
        this.numInventario = new SimpleIntegerProperty(numInventario);
        this.edificio = new SimpleStringProperty(edificio);
        this.piso = new SimpleStringProperty(piso);
        this.cubiculo = new SimpleStringProperty(cubiculo);
    }

    public Ubicacion(int id, String equipo, Integer numInventario, String edificio, String piso, String cubiculo){
        this.id = id;
        this.equipo = new SimpleStringProperty(equipo);
        this.numInventario = new SimpleIntegerProperty(numInventario);
        this.edificio = new SimpleStringProperty(edificio);
        this.piso = new SimpleStringProperty(piso);
        this.cubiculo = new SimpleStringProperty(cubiculo);
    }
    ////////////////////////////////GET////////////////////////////
    public int getId(){return this.id;}

    public String getEquipo(){
        return equipo.get();
    }

    public int getNumInv(){
        return this.numInventario.get();
    }

    public String getEdificio(){
        return edificio.get();
    }

    public String getPiso(){
        return this.piso.get();
    }

    public String getCubiculo(){
        return this.cubiculo.get();
    }
    ///////////////////////////////SET/////////////////////////////
    public void setEquipo(String equipo){
        this.equipo.set(equipo);
    }

    public void setNumInventario(int numInventario){this.numInventario.set(numInventario);}
    //////////////////////////PROPERTYS///////////////////////////
    public StringProperty equipoProperty(){
        return this.equipo;
    }

    public IntegerProperty numInventarioProperty(){
        return this.numInventario;
    }

    public StringProperty edificioProperty(){
        return this.edificio;
    }

    public StringProperty pisoProperty(){
        return this.piso;
    }

    public StringProperty cubiculoProperty(){
        return this.cubiculo;
    }
}

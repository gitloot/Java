package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArticuloInventario {

    private int id;
    private StringProperty equipo;
    private StringProperty nombre;
    private IntegerProperty num_inventario;
    private StringProperty marca;
    private StringProperty modelo;
    private StringProperty num_serie;
    private StringProperty descripcion;
    private StringProperty estado;
    private StringProperty final_user;


    ////////////////////////////////////////////////////////Constructores/////////////////////////////////////////////////////////////////

    public ArticuloInventario(String equipo, String nombre, Integer num_inventario, String marca, String modelo,
                              String num_serie, String descripcion, String estado, String final_user){
        this.equipo = new SimpleStringProperty(equipo);
        this.nombre = new SimpleStringProperty(nombre);
        this.num_inventario = new SimpleIntegerProperty(num_inventario);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.num_serie = new SimpleStringProperty(num_serie);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estado);
        this.final_user = new SimpleStringProperty(final_user);
    }

    public ArticuloInventario(int id, String equipo, String nombre, Integer num_inventario, String marca, String modelo,
                              String num_serie, String descripcion, String estado, String final_user){
        this.id = id;
        this.equipo = new SimpleStringProperty(equipo);
        this.nombre = new SimpleStringProperty(nombre);
        this.num_inventario = new SimpleIntegerProperty(num_inventario);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.num_serie = new SimpleStringProperty(num_serie);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.estado = new SimpleStringProperty(estado);
        this.final_user = new SimpleStringProperty(final_user);
    }

    public ArticuloInventario(){
    }

    //////////////////////////////////////////////////////Metodos de retorno/////////////////////////////////////////////////////////////////////

    public int getId(){return this.id;}

    public String getEquipo(){
        return equipo.get();
    }

    public String getNombre() {return nombre.get();}

    public int getNumInventario(){
        return num_inventario.get();
    }

    public String getMarca(){
        return marca.get();
    }

    public String getModelo(){
        return modelo.get();
    }

    public String getNum_serie(){
        return num_serie.get();
    }

    public String getDescripcion(){ return  descripcion.get(); }

    public String getEstado(){
        return estado.get();
    }

    public String getFinal_user(){
        return final_user.get();
    }

    public ObservableList getList(){
        return FXCollections.observableArrayList(this.equipo, this.nombre);
    }

    ////////////////////////////////////////////////////////////Set de datos//////////////////////////////////////////////////////////////////////////////////////////////

    public void setId(int id){this.id=id;}

    public void setEquipo(String equipo){
        this.equipo.set(equipo);
    }

    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    public void setNumInventario(int num_inventario){
        this.num_inventario.set(num_inventario);
    }

    public void setMarca(String marca){
        this.marca.set(marca);
    }

    public void setModelo(String modelo){  this.modelo.set(modelo);
    }

    public void setNum_serie(String num_serie){
        this.num_serie.set(num_serie);
    }

    public void setDescripcion(String descripcion){
        this.descripcion.set(descripcion);
    }

    public void setEstado(String estado){
        this.estado.set(estado);
    }

    public void setFinal_user(String final_user){
        this.final_user.set(final_user);
    }

    ///////////////////////////////////////////////////////get propertys /////////////////////////////////////////////////////////

    public StringProperty equipoProperty(){return this.equipo;}

    public StringProperty nombreProperty(){return this.nombre;}

    public IntegerProperty num_inventarioProperty(){return this.num_inventario;}

    public StringProperty marcaProperty(){return this.marca;}

    public StringProperty modeloProperty(){return this.modelo;}

    public StringProperty num_serieProperty(){return this.num_serie;}

    public StringProperty descripcionProperty(){return this.descripcion;}

    public StringProperty estadoProperty(){return this.estado;}

    public StringProperty final_userProperty(){return this.final_user;}

}

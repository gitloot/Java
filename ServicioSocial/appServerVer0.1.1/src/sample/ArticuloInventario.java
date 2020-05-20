package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ArticuloInventario {

    private StringProperty equipo;
    private IntegerProperty num_inventario;
    private StringProperty marca;
    private StringProperty modelo;
    private StringProperty num_serie;
    private StringProperty so;
    private IntegerProperty num_procesadores;
    private IntegerProperty num_unidad_almac;
    private StringProperty config_disp_almac;
    private IntegerProperty num_tarj_video;
    private IntegerProperty num_interface_red;
    private IntegerProperty num_unidad_lect;
    private StringProperty final_user;
    private StringProperty carac_add;
    private StringProperty estado;

    ////////////////////////////////////////////////////////Constructores/////////////////////////////////////////////////////////////////

    public ArticuloInventario(String equipo, Integer num_inventario){
        this.equipo = new SimpleStringProperty(equipo);
        this.num_inventario = new SimpleIntegerProperty(num_inventario);
    }

    public ArticuloInventario(){

    }

    //////////////////////////////////////////////////////Metodos de retorno/////////////////////////////////////////////////////////////////////

    public String getEquipo(){
        return equipo.get();
    }

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

    public String getSO(){
        return so.get();
    }

    public int getNum_procesadores(){
        return num_procesadores.get();
    }

    public int getNum_unidad_almac(){
        return num_unidad_almac.get();
    }

    public String getConfig_disp_almac(){
        return config_disp_almac.get();
    }

    public int getNum_tarj_video(){
        return num_tarj_video.get();
    }

    public int getNum_interface_red(){
        return num_interface_red.get();
    }

    public int getNum_unidad_lect(){
        return num_unidad_lect.get();
    }

    public String getFinal_user(){
        return final_user.get();
    }

    public String getCarac_add(){
        return carac_add.get();
    }

    public String getEstado(){
        return estado.get();
    }

    ////////////////////////////////////////////////////////////Set de datos//////////////////////////////////////////////////////////////////////////////////////////////

    public void setEquipo(String equipo){
        this.equipo.set(equipo);
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

    public void setSO(String so){
         this.so.set(so);
    }

    public void setNum_procesadores(int num_procesadores){
        this.num_procesadores.set(num_procesadores);
    }

    public void setNum_unidad_almac(int num_unidad_almac){
        this.num_unidad_almac.set(num_unidad_almac);
    }

    public void setConfig_disp_almac(String config_disp_almac){
        this.config_disp_almac.set(config_disp_almac);
    }

    public void setNum_tarj_video(int num_tarj_video){  this.num_tarj_video.set(num_tarj_video);
    }

    public void setNum_interface_red(int num_interface_red){
        this.num_interface_red.set(num_interface_red);
    }

    public void setNum_unidad_lect(int num_unidad_lect){
        this.num_unidad_lect.set(num_unidad_lect);
    }

    public void setFinal_user(String final_user){
        this.final_user.set(final_user);
    }

    public void setCarac_add(String carac_add){
        this.carac_add.set(carac_add);
    }

    public void setEstado(String estado){
        this.estado.set(estado);
    }

    ////////////////////////////////get propertys //////////////////////////////////

    public StringProperty equipoProperty(){
        return equipo;
    }

    public IntegerProperty num_inventarioProperty(){
        return num_inventario;
    }

}

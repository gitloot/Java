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

    public ArticuloInventario(String equipo, Integer num_inventario, String marca, String modelo, String num_serie, String so,
                              Integer num_procesadores, Integer num_unidad_almac, String config_disp_almac,
                              Integer num_tarj_video, Integer num_interface_red, Integer num_unidad_lect,
                              String final_user, String carac_add, String estado){
        this.equipo = new SimpleStringProperty(equipo);
        this.num_inventario = new SimpleIntegerProperty(num_inventario);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.num_serie = new SimpleStringProperty(num_serie);
        this.so = new SimpleStringProperty(so);
        this.num_procesadores = new SimpleIntegerProperty(num_procesadores);
        this.num_unidad_almac = new SimpleIntegerProperty(num_unidad_almac);
        this.config_disp_almac = new SimpleStringProperty(config_disp_almac);
        this.num_tarj_video = new SimpleIntegerProperty(num_tarj_video);
        this.num_interface_red = new SimpleIntegerProperty(num_interface_red);
        this.num_unidad_lect = new SimpleIntegerProperty(num_unidad_lect);
        this.final_user = new SimpleStringProperty(final_user);
        this.carac_add = new SimpleStringProperty(carac_add);
        this.estado = new SimpleStringProperty(estado);
    }

    public ArticuloInventario(){
        this.equipo = null;
        this.num_inventario = null;
        this.marca = null;
        this.modelo = null;
        this.num_serie = null;
        this.so = null;
        this.num_procesadores = null;
        this.num_unidad_almac = null;
        this.config_disp_almac = null;
        this.num_tarj_video = null;
        this.num_interface_red = null;
        this.num_unidad_lect = null;
        this.final_user = null;
        this.carac_add = null;
        this.estado = null;
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

    ///////////////////////////////////////////////////////get propertys /////////////////////////////////////////////////////////


    public StringProperty equipoProperty(){return this.equipo;}

    public IntegerProperty num_inventarioProperty(){return this.num_inventario;}

    public StringProperty marcaProperty(){return this.marca;}

    public StringProperty modeloProperty(){return this.modelo;}

    public StringProperty num_serieProperty(){return this.num_serie;}

    public StringProperty soProperty(){return this.so;}

    public IntegerProperty num_procesadoresProperty(){return this.num_procesadores;}

    public IntegerProperty num_unidad_almacProperty(){return this.num_unidad_almac;}

    public StringProperty config_disp_almacProperty(){return this.config_disp_almac;}

    public IntegerProperty num_tarj_videoProperty(){return this.num_tarj_video;}

    public IntegerProperty num_interface_redProperty(){return this.num_interface_red;}

    public IntegerProperty num_unidad_lectProperty(){return this.num_unidad_lect;}

    public StringProperty final_userProperty(){return this.final_user;}

    public StringProperty carac_addProperty(){return this.carac_add;}

    public StringProperty estadoProperty(){return this.estado;}

}

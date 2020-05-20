package sample;

public class Listado {
    private String campo;
    private String valor;

    public Listado(){}

    public Listado(String campo, String valor){
        this.campo = campo;
        this.valor = valor;
    }

    public String getCampo() {
        return this.campo;
    }

    public String getValor() {
        return this.valor;
    }
}

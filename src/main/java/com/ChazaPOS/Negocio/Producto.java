package ProyectoPOO.src.main.java.com.ChazaPOS.Negocio;

public abstract class Producto {
    private int codigo,stock,stockMinimo;
    private String nombre,categoria,rutaImagen;
    private boolean activo;

    public Producto(int codigo, int stock, int stockMinimo, String nombre, String categoria, String rutaImagen,
            boolean activo) {
        this.codigo = codigo;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }

    public boolean stock(int cantidad){
        if (cantidad > 0){return true;}
        else{return false;}
    };

    public boolean stockBajo(){
        if (stock < 3){return true;}
        else{return false;}
    };

    public void descontarStock(){
        stock--;
    };

    public void aumentarStock(){
        stock++;
    }


    public int getCodigo() {return codigo;}
    public void setCodigo(int codigo) {this.codigo = codigo;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}
    public int getStockMinimo() {return stockMinimo;}
    public void setStockMinimo(int stockMinimo) {this.stockMinimo = stockMinimo;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}
    public String getRutaImagen() {return rutaImagen;}
    public void setRutaImagen(String rutaImagen) {this.rutaImagen = rutaImagen;}
    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;};

    
    
}

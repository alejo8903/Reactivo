package clase2;

public class Persona {

    private String nombre;
    private Integer edad;
    private String genero;

    public Persona() {
    }

    public Persona(String nombre, Integer edad, String genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad(){
        return this.edad;
    }

    public void setEdad(Integer edad){
        this.edad = edad;
    }

    public String getGenero(){
        return this.genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }
}

package Modelo;

public class Actores {
	private String id;
	private String nombre;
	private String nacionalidad;
	private String edad;
	private String residencia;
	private Peliculas peliculas;

	public Actores() {

	}

	public Actores(String id, String nombre, String nacionalidad, String edad, String residencia, Peliculas peliculas) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.edad = edad;
		this.residencia = residencia;
		this.peliculas = peliculas;

	}

	public Actores(String nombre, String nacionalidad, String edad, String residencia, Peliculas peliculas) {
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.edad = edad;
		this.residencia = residencia;
		this.peliculas = peliculas;

	}

	public Actores(String id, String nombre, String nacionalidad, String edad, String residencia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.edad = edad;
		this.residencia = residencia;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public Peliculas getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Peliculas peliculas) {
		this.peliculas = peliculas;
	}

}
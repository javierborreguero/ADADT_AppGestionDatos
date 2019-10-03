package Controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import Modelo.Actores;
import Modelo.Peliculas;

public class Controlador {
	// Nos treamos las varibales que nos dan la opción de eleccion en los menús
	private Intercambio opcionPrincipal;
	private Intercambio opcionSecundaria;
	private String FicheroConfiguracionDB;

	public Controlador() {
		FicheroConfiguracionDB = "FicherosConfiguracion/ConfiguracionDB.ini";
	}

	public void elegiarOpcion(int acceso) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if (acceso == 1) {
			opcionPrincipal = new FileManager();
		} else if (acceso == 2) {
			opcionPrincipal = new DBManager(FicheroConfiguracionDB);
		}
	}

	public void leerActores() throws IOException {
		HashMap<String, Actores> ver = opcionPrincipal.leerActores();
		int contador = 1;
		if (ver.size() == 0) {
			System.out.println("No existen actores");
		} else {
			for (Entry<String, Actores> entry : ver.entrySet()) {
				System.out.println("---- Actor " + contador + " ----");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Nacionalidad: " + entry.getValue().getNacionalidad());
				System.out.println("Edad: " + entry.getValue().getEdad());
				System.out.println("Residencia: " + entry.getValue().getResidencia());
				if (entry.getValue().getPeliculas().getId().equals("null")) {
					System.out.println("No tiene ninguna pelicula asignada");
				} else {
					System.out.println("Pelicula: " + entry.getValue().getPeliculas().getId());
				}
				contador++;
			}
		}

	}

	public void leerPeliculas() throws IOException {
		HashMap<String, Peliculas> ver = opcionPrincipal.leerPeliculas();
		int contador = 1;
		if (ver.size() == 0) {
			System.out.println("No existen peliculas");
		} else {
			for (Entry<String, Peliculas> entry : ver.entrySet()) {
				System.out.println("---- Pelicula " + contador + " ----");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Descripcion: " + entry.getValue().getDescripcion());
				contador++;
			}
		}

	}

	public Peliculas escogerPelicula(String id_pelicula) throws IOException {
		HashMap<String, Peliculas> ver = opcionPrincipal.leerPeliculas();
		Peliculas elegirPelicula = null;
		String id = null;
		String nombre = null;
		String descripcion = null;
		for (Entry<String, Peliculas> entry : ver.entrySet()) {
			if (entry.getValue().getId().equals(id_pelicula)) {
				id = entry.getValue().getId();
				nombre = entry.getValue().getNombre();
				descripcion = entry.getValue().getDescripcion();
				elegirPelicula = new Peliculas(id, nombre, descripcion);
			}
		}
		return elegirPelicula;
	}

	public void insertarActor() throws IOException {
		Scanner teclado = new Scanner(System.in);
		// teclado.nextLine();
		System.out.println("Introduzca el id del actor");
		String id = teclado.nextLine();
		System.out.println("Introduzca el nombre del actor");
		String nombre = teclado.nextLine();
		System.out.println("Introduzca la nacionalidad del actor");
		String nacionalidad = teclado.nextLine();
		System.out.println("Introduzca la edad del actor");
		String edad = teclado.nextLine();
		System.out.println("introduzca la residencia del actor");
		String residencia = teclado.nextLine();
		System.out.println("Introduzca el id de la pelicula");
		String id_pelicula = teclado.nextLine();
		Peliculas obj_peliculas = escogerPelicula(id_pelicula);
		if (obj_peliculas != null) {
			Actores nuevo = new Actores(id, nombre, nacionalidad, edad, residencia, obj_peliculas);
			if (pedirdatosActores(nuevo)) {
				System.out.println("Actor añadido correctamente");
			} else {
				System.out.println("El actor no se ha podido añadir");
			}
		} else {
			System.out.println("No se ha encontrado ningún actor");
		}

	}

	public boolean pedirdatosActores(Actores nuevo) throws IOException {
		if (opcionPrincipal.insertarActor(nuevo)) {
			return true;
		}
		return false;
	}

	public void insetarPelicula() throws IOException {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca el id de la pelicula");
		String id = teclado.nextLine();
		System.out.println("Introduzca el nombre de la pelicula");
		String nombre = teclado.nextLine();
		System.out.println("Introduzca la descripcion de la pelicula");
		String descripcion = teclado.nextLine();
		Peliculas nuevo = new Peliculas(id, nombre, descripcion);
		if (pedirdatosPeliculas(nuevo)) {
			System.out.println("Pelicula añadida correctamente");
		} else {
			System.out.println("La pelicula no se ha podid añadir");
		}

	}

	public boolean pedirdatosPeliculas(Peliculas nuevo) throws IOException {
		if (opcionPrincipal.isertarPelicula(nuevo)) {
			return true;
		}
		return false;
	}

	public boolean borrarActores() throws IOException {
		opcionPrincipal.borrarActores();
		System.out.println("Datos borrados correctmente");
		return true;
	}

	public void borrarPeliculas() throws IOException {
		opcionPrincipal.borrarPeliculas();
		System.out.println("Datos borrados correctamente");
	}

	public void importar(int importar) throws IOException {
		if (importar == 1) {
			opcionSecundaria = new FileManager();
			HashMap<String, Actores> leer_actores = opcionSecundaria.leerActores();
			HashMap<String, Peliculas> leer_peliculas = opcionSecundaria.leerPeliculas();
			opcionPrincipal.escribirtodasPeliculas(leer_peliculas);
			opcionPrincipal.escribirtodosActores(leer_actores);
		} else if (importar == 2) {
			opcionSecundaria = new DBManager(FicheroConfiguracionDB);
			HashMap<String, Actores> leer_actores = opcionSecundaria.leerActores();
			HashMap<String, Peliculas> leer_peliculas = opcionSecundaria.leerPeliculas();
			opcionPrincipal.escribirtodasPeliculas(leer_peliculas);
			opcionPrincipal.escribirtodosActores(leer_actores);
		}

	}

}

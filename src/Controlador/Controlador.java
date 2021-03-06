package Controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import Modelo.Actores;
import Modelo.Peliculas;

public class Controlador {
	// Nos treamos las varibales que nos dan la opci�n de eleccion en los men�s
	private Intercambio opcionPrincipal;
	private Intercambio opcionSecundaria;
	private String FicheroConfiguracionDB, FicheroConfiguracionMongo, FicheroConfiguracionJSON;

	public Controlador() {
		FicheroConfiguracionDB = "FicherosConfiguracion/ConfiguracionDB.ini";
		FicheroConfiguracionMongo = "FicherosConfiguracion/ConfiguracionMongo.ini";
		FicheroConfiguracionJSON = "FicherosConfiguracion/ConfiguracionJSON.ini";
	}

	public void elegiarOpcion(int acceso) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if (acceso == 1) {
			opcionPrincipal = new FileManager();
		} else if (acceso == 2) {
			opcionPrincipal = new DBManager(FicheroConfiguracionDB);
		} else if (acceso == 3) {
			opcionPrincipal = new HibernateManager();
		} else if (acceso == 4) {
			opcionPrincipal = new MongoManager(FicheroConfiguracionMongo);
		} else if (acceso == 5) {
			opcionPrincipal = new JSONManager(FicheroConfiguracionJSON);
		}
	}

	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> ver = opcionPrincipal.leerActores();
		return ver;

	}

	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> ver = opcionPrincipal.leerPeliculas();
		return ver;
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

	public boolean pedirdatosActores(Actores nuevo) throws IOException {
		if (opcionPrincipal.insertarActor(nuevo)) {
			return true;
		}
		return false;
	}

	public void insetarPelicula() throws IOException {

	}

	public boolean pedirdatosPeliculas(Peliculas nuevo) throws IOException {
		if (opcionPrincipal.isertarPelicula(nuevo)) {
			return true;
		}
		return false;
	}

	public boolean borrarActores() throws IOException {
		opcionPrincipal.borrarActores();

		return true;
	}

	public boolean borrarPeliculas() throws IOException {
		opcionPrincipal.borrarPeliculas();
		return true;
	}

	public boolean importar(int importar) throws IOException {
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
		} else if (importar == 3) {
			opcionSecundaria = new HibernateManager();
			HashMap<String, Actores> leer_actores = opcionSecundaria.leerActores();
			HashMap<String, Peliculas> leer_peliculas = opcionSecundaria.leerPeliculas();
			opcionPrincipal.escribirtodasPeliculas(leer_peliculas);
			opcionPrincipal.escribirtodosActores(leer_actores);
		} else if (importar == 4) {
			opcionSecundaria = new MongoManager(FicheroConfiguracionMongo);
			HashMap<String, Actores> leer_actores = opcionSecundaria.leerActores();
			HashMap<String, Peliculas> leer_peliculas = opcionSecundaria.leerPeliculas();
			opcionPrincipal.escribirtodasPeliculas(leer_peliculas);
			opcionPrincipal.escribirtodosActores(leer_actores);
		} else if (importar == 5) {
			opcionSecundaria = new JSONManager(FicheroConfiguracionJSON);
			HashMap<String, Actores> leer_actores = opcionSecundaria.leerActores();
			HashMap<String, Peliculas> leer_peliculas = opcionSecundaria.leerPeliculas();
			opcionPrincipal.escribirtodasPeliculas(leer_peliculas);
			opcionPrincipal.escribirtodosActores(leer_actores);
		}
		return true;

	}

	public boolean borrarUnActor(String idborrar) throws IOException {
		if (opcionPrincipal.borrarUnActor(idborrar)) {
			return true;
		}
		return false;
	}

	public boolean borrarUnaPelicula(String idborrar) throws IOException {
		if (opcionPrincipal.borrarUnaPelicula(idborrar)) {
			return true;
		}
		return false;
	}

	public HashMap<String, Peliculas> leerIdPeliculas() throws IOException {
		HashMap<String, Peliculas> ver = opcionPrincipal.leerPeliculas();
		return ver;

	}

	public HashMap<String, Actores> leerIdActores() throws IOException {
		HashMap<String, Actores> ver = opcionPrincipal.leerActores();

		return ver;

	}

	public boolean modificarUnActor(String modificar, Actores actmodificar) throws IOException {
		if (opcionPrincipal.modificarUnActor(modificar, actmodificar)) {
			return true;
		}
		return false;

	}

	public boolean modificarUnaPelicula(String modificar, Peliculas reprmodificar) throws IOException {
		if (opcionPrincipal.modificarUnaPelicula(modificar, reprmodificar)) {
			return true;
		}
		return false;
	}

	public HashMap<String, Peliculas> getPeliculas() throws IOException {
		return opcionPrincipal.leerPeliculas();
	}

}

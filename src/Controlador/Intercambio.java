package Controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import Modelo.Actores;
import Modelo.Peliculas;

public interface Intercambio {
	public HashMap<String, Actores> leerActores() throws IOException;

	public HashMap<String, Peliculas> leerPeliculas() throws IOException;

	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException;

	public boolean comprobarIdActor(Actores nuevo) throws IOException;

	public boolean insertarActor(Actores nuevo) throws IOException;

	public boolean isertarPelicula(Peliculas nuevo) throws IOException;
	public boolean borrarActores() throws IOException;
	public boolean borrarPeliculas() throws IOException;
	public void escribirtodosActores(HashMap<String, Actores> lista) throws IOException;
	public void escribirtodasPeliculas(HashMap<String, Peliculas> lista) throws IOException;
}

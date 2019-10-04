package Controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import Modelo.Actores;
import Modelo.Peliculas;

public class FileManager implements Intercambio {

	@Override
	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE LECTURA
	 * 
	 * ----------------------------------
	 */
	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> actores = new HashMap<>();
		HashMap<String, Peliculas> peliculas = leerPeliculas();
		String cadena;
		FileReader fr = new FileReader("Ficheros/actores.txt");
		BufferedReader br = new BufferedReader(fr);
		Actores mActor;
		while ((cadena = br.readLine()) != null) {
			if (!cadena.equals("*")) {
				String[] partes = cadena.split(":");
				mActor = new Actores();
				mActor.setId(partes[0]);
				mActor.setNombre(partes[1]);
				mActor.setNacionalidad(partes[2]);
				mActor.setEdad(partes[3]);
				mActor.setResidencia(partes[4]);
				if (!partes[5].equals("null")) {
					mActor.setPeliculas(peliculas.get(partes[5]));
					actores.put(partes[0], mActor);
				} else {
					mActor.setPeliculas(new Peliculas("null"));
					actores.put(partes[0], mActor);
				}
			}

		}
		br.close();
		return actores;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> peliculas = new HashMap<>();
		FileReader fr = new FileReader("Ficheros/peliculas.txt");
		BufferedReader br = new BufferedReader(fr);
		String cadena;
		Peliculas mPelicula;
		while ((cadena = br.readLine()) != null) {
			if (!cadena.equals("*")) {
				String[] partes = cadena.split(":");
				mPelicula = new Peliculas();
				mPelicula.setId(partes[0]);
				mPelicula.setNombre(partes[1]);
				mPelicula.setDescripcion(partes[2]);
				peliculas.put(partes[0], mPelicula);
			}
		}
		br.close();
		return peliculas;
	}

	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE ESCRITURA
	 * 
	 * ----------------------------------
	 */

	@Override
	public boolean insertarActor(Actores nuevo) throws IOException {
		if (!comprobarIdActor(nuevo)) {
			FileWriter fw = new FileWriter("Ficheros/actores.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(nuevo.getId());
			bw.write(":");
			bw.write(nuevo.getNombre());
			bw.write(":");
			bw.write(nuevo.getNacionalidad());
			bw.write(":");
			bw.write(nuevo.getEdad());
			bw.write(":");
			bw.write(nuevo.getResidencia());
			bw.write(":");
			if (nuevo.getPeliculas() == null) {
				bw.write("null");
			} else {
				bw.write(nuevo.getPeliculas().getId());
			}
			bw.write("\n");
			bw.write("*");
			bw.write("\n");
			bw.close();
			return true;
		}
		return false;
	}

	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		HashMap<String, Actores> ver = leerActores();
		for (Entry<String, Actores> entry : ver.entrySet()) {
			if (entry.getValue().getId().equals(nuevo.getId())) {
				// System.out.println("Id repetido");
				return true;
			}
		}
		return false;
	}

	@Override
	public void escribirtodosActores(HashMap<String, Actores> lista) throws IOException {
		borrarActores();
		for (Entry<String, Actores> entry : lista.entrySet()) {
			insertarActor(lista.get(entry.getKey()));
		}
	}

	@Override
	public boolean isertarPelicula(Peliculas nuevo) throws IOException {
		if (!comprobarIdPeli(nuevo)) {
			FileWriter fw = new FileWriter("Ficheros/peliculas.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(nuevo.getId());
			bw.write(":");
			bw.write(nuevo.getNombre());
			bw.write(":");
			bw.write(nuevo.getDescripcion());
			bw.write("\n");
			bw.write("*");
			bw.write("\n");
			bw.close();
			return true;
		}
		return false;
	}

	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		HashMap<String, Peliculas> ver = leerPeliculas();
		for (Entry<String, Peliculas> entry : ver.entrySet()) {
			if (entry.getValue().getId().equals(nuevo.getId())) {
				// System.out.println("Id repetido");
				return true;
			}

		}

		return false;
	}

	@Override
	public void escribirtodasPeliculas(HashMap<String, Peliculas> lista) throws IOException {
		borrarPeliculas();
		for (Entry<String, Peliculas> entry : lista.entrySet()) {
			isertarPelicula(lista.get(entry.getKey()));
		}

	}
	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE Borrado
	 * 
	 * ----------------------------------
	 */

	@Override
	public boolean borrarActores() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("Ficheros/actores.txt"));
		bw.write("");
		bw.close();
		return true;
	}

	@Override
	public boolean borrarPeliculas() throws IOException {
		borrarActores();
		BufferedWriter bw = new BufferedWriter(new FileWriter("Ficheros/peliculas.txt"));
		bw.write("");
		bw.close();
		return true;
	}

	@Override
	public boolean borrarUnActor(String Id) throws IOException {
		HashMap<String, Actores> ver = leerActores();
		boolean fin = false;
		if (ver.containsKey(Id)) {
			ver.remove(Id);
			escribirtodosActores(ver);
			fin = true;
		} else {
			fin = false;
		}
		return fin;
	}

	@Override
	public boolean borrarUnaPelicula(String Id) throws IOException {
		HashMap<String, Peliculas> ver_peli = leerPeliculas();
		HashMap<String, Actores> ver = leerActores();
		boolean fin = false;
		Actores mActores = new Actores();
		Peliculas mPeli = new Peliculas("NULL");
		for (Entry<String, Actores> entry : ver.entrySet()) {
			if (entry.getValue().getPeliculas().getId().equals(Id)) {
				mActores.setId(entry.getValue().getId());
				mActores.setNombre(entry.getValue().getNombre());
				mActores.setNacionalidad(entry.getValue().getNacionalidad());
				mActores.setEdad(entry.getValue().getEdad());
				mActores.setResidencia(entry.getValue().getResidencia());
				mActores.setPeliculas(mPeli);
				entry.setValue(mActores);
			}
		}
		if (ver_peli.containsKey(Id)) {
			ver_peli.remove(Id);
			escribirtodasPeliculas(ver_peli);
			escribirtodosActores(ver);
			fin = true;
		} else {
			fin = false;
		}
		return fin;
	}

}

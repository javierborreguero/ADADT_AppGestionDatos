package Controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.bson.Document;
import org.json.simple.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


import Modelo.Actores;
import Modelo.Peliculas;


public class MongoManager implements Intercambio {
	private String IP;
	private String PORT;
	private String NAMEDATABASE;
	private MongoClient mongoClient;
	private String ACTORES;
	private String PELICULAS;
	private MongoDatabase database;

	public MongoManager(String archivo) throws FileNotFoundException, IOException {
		Properties mProperties = new Properties();
		mProperties.load(new FileReader(archivo));
		IP = mProperties.getProperty("IP");
		NAMEDATABASE = mProperties.getProperty("NAMEDATABASE");
		PORT = mProperties.getProperty("PORT");
		ACTORES = mProperties.getProperty("ACTORES");
		PELICULAS = mProperties.getProperty("PELICULAS");
		int port = Integer.parseInt(PORT);
		mongoClient = new MongoClient(IP, port);
		database = (MongoDatabase) mongoClient.getDatabase(NAMEDATABASE);
	}

	@Override
	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> actores = new HashMap<String, Actores>();
		MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
		Actores mActores = null;
		Peliculas mPeliculas = null;
		String idActor = null;
		String nombre = null;
		String nacionalidad = null;
		String edad = null;
		String residencia = null;
		String idPelicula = null;
		JSONObject obj;
		for (Document document : collectionActores.find()) {
			idActor = document.get("id").toString();
			nombre = document.get("nombre").toString();
			nacionalidad = document.get("nacionalidad").toString();
			edad = document.get("edad").toString();
			residencia = document.get("residencia").toString();
			mActores = new Actores(idActor, nombre, nacionalidad, edad, residencia);
			actores.put(idActor, mActores);
		}
		return actores;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> aux = new HashMap<String, Peliculas>();
		MongoCollection<Document> collection = database.getCollection(PELICULAS);
		Peliculas mPeliculas = null;
		String id, nombre, descripcion = null;
		for (Document document : collection.find()) {
			id = document.get("id").toString();
			nombre = document.getString("nombre").toString();
			descripcion = document.getString("descripcion").toString();
			mPeliculas = new Peliculas(id, nombre, descripcion);
			aux.put(id, mPeliculas);
		}
		return aux;
	}

	@Override
	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insertarActor(Actores nuevo) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isertarPelicula(Peliculas nuevo) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarActores() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarPeliculas() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarUnActor(String Id) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean borrarUnaPelicula(String Id) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void escribirtodosActores(HashMap<String, Actores> lista) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void escribirtodasPeliculas(HashMap<String, Peliculas> lista) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean modificarUnActor(String idmodificar, Actores modificar) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modificarUnaPelicula(String idmodificar, Peliculas modificar) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}

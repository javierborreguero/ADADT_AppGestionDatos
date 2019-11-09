package Controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import com.sun.javadoc.Doc;

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
		String nombrePelicula = null;
		JSONObject obj;
		JSONArray arr;
		for (Document document : collectionActores.find()) {
			obj = (JSONObject) JSONValue.parse(document.toJson().toString());
			idActor = document.get("id").toString();
			nombre = document.get("nombre").toString();
			nacionalidad = document.get("nacionalidad").toString();
			edad = document.get("edad").toString();
			residencia = document.get("residencia").toString();
			mActores = new Actores(idActor, nombre, nacionalidad, edad, residencia);
			if (!obj.get("pelicula").equals("null")) {
				arr = (JSONArray) obj.get("pelicula");
				for (int i = 0; i < arr.size(); i++) {
					JSONObject columna = (JSONObject) arr.get(i);
					if (!columna.get("id").equals("null")) {

						nombrePelicula = columna.get("nombre").toString();
					} else {
						idPelicula = "null";
						nombrePelicula = "null";
					}
					mPeliculas = new Peliculas(nombrePelicula);
					mActores.setPeliculas(mPeliculas);
					actores.put(idActor, mActores);
				}

			} else {
				mPeliculas = new Peliculas("null");
				mActores.setPeliculas(mPeliculas);
				actores.put(idActor, mActores);
			}

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
	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		HashMap<String, Actores> actores = leerActores();
		if (actores.get(nuevo.getId()) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertarActor(Actores nuevo) throws IOException {
		if (!comprobarIdActor(nuevo)) {
			MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
			Document document = new Document();
			document.put("id", nuevo.getId());
			document.put("nombre", nuevo.getNombre());
			document.put("nacionalidad", nuevo.getNacionalidad());
			document.put("edad", nuevo.getEdad());
			document.put("residencia", nuevo.getResidencia());
			if (nuevo.getPeliculas() != null) {
				if (!nuevo.getPeliculas().getId().equals("null")) {
					JSONObject obj = new JSONObject();
					obj.put("id", nuevo.getPeliculas().getId());
					obj.put("nombre", nuevo.getPeliculas().getNombre());
					obj.put("descripcion", nuevo.getPeliculas().getDescripcion());
					JSONArray arr = new JSONArray();
					arr.add(obj);
					document.put("pelicula", arr);
				} else {
					document.put("pelicula", "null");
				}

			} else {
				document.put("pelicula", "null");
			}
			collectionActores.insertOne(document);
			return true;
		}
		return false;
	}

	@Override
	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		HashMap<String, Peliculas> peliculas = leerPeliculas();
		if (peliculas.get(nuevo.getId()) != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isertarPelicula(Peliculas nuevo) throws IOException {
		if (!comprobarIdPeli(nuevo)) {
			MongoCollection<Document> collectionPelis = database.getCollection(PELICULAS);
			Document document = new Document();
			document.put("id", nuevo.getId());
			document.put("nombre", nuevo.getNombre());
			document.put("descripcion", nuevo.getDescripcion());
			collectionPelis.insertOne(document);
			return true;
		}
		return false;
	}

	@Override
	public void escribirtodosActores(HashMap<String, Actores> lista) throws IOException {
		borrarPeliculas();
		for (Entry<String, Actores> entry : lista.entrySet()) {
			insertarActor(lista.get(entry.getKey()));
		}

	}

	@Override
	public void escribirtodasPeliculas(HashMap<String, Peliculas> lista) throws IOException {
		borrarPeliculas();
		for (Entry<String, Peliculas> entry : lista.entrySet()) {
			isertarPelicula(lista.get(entry.getKey()));
		}
	}

	@Override
	public boolean borrarActores() throws IOException {

		MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
		for (Entry<String, Actores> entry : leerActores().entrySet()) {
			collectionActores.deleteMany(Filters.gte("id", entry.getKey()));
		}
		return true;
	}

	@Override
	public boolean borrarPeliculas() throws IOException {
		borrarActores();
		MongoCollection<Document> collectionPelis = database.getCollection(PELICULAS);
		for (Entry<String, Peliculas> entry : leerPeliculas().entrySet()) {
			collectionPelis.deleteMany(Filters.gte("id", entry.getKey()));
		}
		return true;
	}

	@Override
	public boolean borrarUnActor(String Id) throws IOException {
		if (leerActores().get(Id) != null) {
			MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
			collectionActores.deleteOne(Filters.eq("id", Id));
			return true;
		}
		return false;
	}

	@Override
	public boolean borrarUnaPelicula(String Id) throws IOException {
		if (leerPeliculas().get(Id) != null) {
			for (Entry<String, Actores> entry : leerActores().entrySet()) {
				if (entry.getValue().getPeliculas().getId().equals(Id)) {
					MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
					Document query = new Document();
					query.append("id", entry.getValue().getId());
					Document setData = new Document();
					setData.append("pelicula", "null");
					Document update = new Document();
					update.append("$set", setData);
					collectionActores.updateOne(query, update);
				}
			}
			MongoCollection<Document> collectionPelis = database.getCollection(PELICULAS);
			collectionPelis.deleteOne(Filters.eq("id", Id));
			return true;
		}
		return false;
	}

	@Override
	public boolean modificarUnActor(String idmodificar, Actores modificar) throws IOException {
		if (leerActores().get(idmodificar) != null) {
			MongoCollection<Document> collectionActores = database.getCollection(ACTORES);
			Document query = new Document();
			query.append("id", idmodificar);
			Document setData = new Document();
			setData.append("nombre", modificar.getNombre()).append("nacionalidad", modificar.getNacionalidad())
					.append("edad", modificar.getEdad()).append("residencia", modificar.getResidencia());
			if (modificar.getPeliculas() != null) {
				if (!modificar.getPeliculas().getId().equals("null")) {
					JSONObject obj = new JSONObject();
					Peliculas mPeliculas = modificar.getPeliculas();
					System.out.println("Id de la pelicula " + mPeliculas.getId() + "Nombre de la pelicula "
							+ mPeliculas.getNombre());
					obj.put("id", mPeliculas.getId());
					obj.put("nombre", mPeliculas.getNombre());
					obj.put("descripcion", mPeliculas.getDescripcion());
					JSONArray arr = new JSONArray();
					arr.add(obj);
					setData.append("pelicula", arr);
				} else {
					setData.append("pelicula", "null");
				}
			} else {
				setData.append("pelicula", "null");
			}
			Document update = new Document();
			update.append("$set", setData);
			collectionActores.updateOne(query, update);
			return true;
		}
		return false;
	}

	@Override
	public boolean modificarUnaPelicula(String idmodificar, Peliculas modificar) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
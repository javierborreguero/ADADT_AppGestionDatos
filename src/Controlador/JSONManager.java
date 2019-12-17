package Controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import com.mongodb.util.JSON;
import Modelo.Actores;
import Modelo.Peliculas;

public class JSONManager implements Intercambio {

	private ApiRequest encargadoPeticiones;
	private String SERVER_PATH, GET_ACTORES, GET_PELICULAS;

	public JSONManager(String archivo) throws FileNotFoundException, IOException {

		encargadoPeticiones = new ApiRequest();
		Properties p = new Properties();
		p.load(new FileReader(archivo));
		SERVER_PATH = p.getProperty("SERVER_PATH");
		GET_ACTORES = p.getProperty("GET_ACTORES");
		GET_PELICULAS = p.getProperty("GET_PELICULAS");
	}

	@Override
	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> auxhm = new HashMap<String, Actores>();
		String url = SERVER_PATH + GET_ACTORES;
		String response = encargadoPeticiones.getRequest(url);
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
		if (respuesta == null) {
			System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
			System.exit(-1);
		} else {
			String estado = (String) respuesta.get("estado");
			if (estado.equals("ok")) {
				JSONArray actoresarr = (JSONArray) respuesta.get("actores");
				if (actoresarr.size() > 0) {
					Actores mActores;
					String id;
					String nombre;
					String nacionalidad;
					String edad;
					String residencia;
					String pelicula;
					Peliculas mPeliculas = null;
					for (int i = 0; i < actoresarr.size(); i++) {
						JSONObject row = (JSONObject) actoresarr.get(i);
						id = row.get("id").toString();
						nombre = row.get("nombre").toString();
						nacionalidad = row.get("nacionalidad").toString();
						edad = row.get("edad").toString();
						residencia = row.get("residencia").toString();
						if (row.get("pelicula") != null) {
							pelicula = row.get("pelicula").toString();
//							if (leerPeliculas().get(pelicula) != null) {
//								mPeliculas = new Peliculas(pelicula, leerPeliculas().get(pelicula).getNombre());
//							}
//						} else {
//							pelicula = "null";
							mPeliculas = new Peliculas(pelicula);
						}
						mActores = new Actores(id, nombre, nacionalidad, edad, residencia, (Peliculas) mPeliculas);
						auxhm.put(id, mActores);
					}

				} else {
					System.out.println("Acceso JSON Remoto - No hay datos que tratar");
					System.out.println();
				}

			} else {
				System.out.println("Ha ocurrido un error en la busqueda de datos");
				System.out.println("Error: " + (String) respuesta.get("error"));
				System.out.println("Consulta: " + (String) respuesta.get("query"));
				System.exit(-1);
			}

		}
		return auxhm;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> auxhm = new HashMap<String, Peliculas>();
		String url = SERVER_PATH + GET_PELICULAS;
		String response = encargadoPeticiones.getRequest(url);
		JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());
		if (respuesta == null) {
			System.out.println("El json recibido no es correcto. Finaliza la ejecuci�n");
			System.exit(-1);
		} else {
			String estado = (String) respuesta.get("estado");
			if (estado.equals("ok")) {
				JSONArray peliculaArr = (JSONArray) respuesta.get("pelicula");
				if (peliculaArr.size() > 0) {
					Peliculas mPeliculas;
					String id;
					String nombre;
					String descripcion;
					for (int i = 0; i < peliculaArr.size(); i++) {
						JSONObject row = (JSONObject) peliculaArr.get(i);
						id = row.get("id").toString();
						nombre = row.get("nombre").toString();
						descripcion = row.get("descripcion").toString();
						mPeliculas = new Peliculas(id, nombre, descripcion);
						auxhm.put(id, mPeliculas);
					}
				} else {
					System.out.println("Acceso JSON Remoto - No hay datos que tratar");
					System.out.println();
				}
			} else {
				System.out.println("Ha ocurrido un error en la busqueda de datos");
				System.out.println("Error: " + (String) respuesta.get("error"));
				System.out.println("Consulta: " + (String) respuesta.get("query"));
				System.exit(-1);
			}
		}
		return auxhm;
	}

	@Override
	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		if (leerActores().get(nuevo.getId()) != null) {
			return true;
		}
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
		borrarActores();
		for (Entry<String, Actores> entry : lista.entrySet()) {
			insertarActor(lista.get(entry.getKey()));
		}
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

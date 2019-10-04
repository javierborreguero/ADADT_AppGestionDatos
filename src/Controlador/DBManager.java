package Controlador;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;
import Controlador.*;
import Modelo.Actores;
import Modelo.Peliculas;

public class DBManager implements Intercambio {
	private Connection conexion;
	private String login;
	private String firsturl;
	private String secondurl;
	private String pwd;
	private String driver;
	private String dbname;
	private String urlCompleta;

	public DBManager(String archivo) throws FileNotFoundException, IOException {
		// Creamos la conexion
		Properties p = new Properties();
		p.load(new FileReader(archivo));
		login = p.getProperty("login");
		pwd = p.getProperty("pwd");
		firsturl = p.getProperty("firsturl");
		secondurl = p.getProperty("secondurl");
		driver = p.getProperty("driver");
		dbname = p.getProperty("dbname");
		urlCompleta = firsturl + dbname + secondurl;
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(urlCompleta, login, pwd);
			if (conexion != null) {
				System.out.println("- Conexión establecida correctamente -");
			}
		} catch (Exception e) {
			System.out.println("Error al crear la conexion");
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE LECTURA
	 * 
	 * ----------------------------------
	 */

	@Override
	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> actoresDB = new HashMap<>();
		PreparedStatement pstm;
		Actores mActor;
		try {
			pstm = conexion.prepareStatement("SELECT * FROM actores");
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				mActor = new Actores();
				mActor.setId(rset.getString("Id"));
				mActor.setNombre(rset.getString("Nombre"));
				mActor.setNacionalidad(rset.getString("Nacionalidad"));
				mActor.setEdad(rset.getString("Edad"));
				mActor.setResidencia(rset.getString("Residencia"));
				if (rset.getString("Pelicula") != null) {
					mActor.setPeliculas(crearPelicula(rset.getString("Pelicula")));
				} else {
					mActor.setPeliculas(new Peliculas("nulll"));
				}
				actoresDB.put(rset.getString("Id"), mActor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actoresDB;
	}

	private Peliculas crearPelicula(String id_pelicula) {
		Peliculas peliculasDB = new Peliculas();
		try {
			PreparedStatement pstm = conexion.prepareStatement("SELECT * FROM peliculas WHERE Id = " + id_pelicula);
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				peliculasDB = new Peliculas(rset.getString("Id"), rset.getString("Nombre"),
						rset.getString("Descripcion"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peliculasDB;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> peliculasDB = new HashMap<>();
		PreparedStatement pstm;
		Peliculas mPeliculas;
		try {
			pstm = conexion.prepareStatement("SELECT * FROM peliculas");
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				mPeliculas = new Peliculas();
				mPeliculas.setId(rset.getString("Id"));
				mPeliculas.setNombre(rset.getString("Nombre"));
				mPeliculas.setDescripcion(rset.getString("Descripcion"));
				peliculasDB.put(rset.getString("Id"), mPeliculas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return peliculasDB;
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
		try {
			if (!comprobarIdActor(nuevo)) {
				PreparedStatement pstm = conexion.prepareStatement(
						"INSERT INTO actores (Id,Nombre,Pelicula,Nacionalidad,Edad,Residencia) VALUES(?,?,?,?,?,?)");
				pstm.setString(1, nuevo.getId());
				pstm.setString(2, nuevo.getNombre());
				pstm.setString(3, nuevo.getPeliculas().getId());
				pstm.setString(4, nuevo.getNacionalidad());
				pstm.setString(5, nuevo.getEdad());
				pstm.setString(6, nuevo.getResidencia());
				pstm.executeUpdate();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		PreparedStatement pstm;
		HashMap<String, Actores> ver = leerActores();
		try {
			pstm = conexion.prepareStatement("SELECT * FROM actores WHERE id = " + nuevo.getId());
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				for (Entry<String, Actores> entry : ver.entrySet()) {
					if (entry.getValue().getId().equals(nuevo.getId())) {
						// System.out.println("Id repetido");
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		try {
			if (!comprobarIdPeli(nuevo)) {
				PreparedStatement pstm = conexion
						.prepareStatement("INSERT INTO peliculas (Id,Nombre,Descripcion) VALUES (?,?,?)");
				pstm.setString(1, nuevo.getId());
				pstm.setString(2, nuevo.getNombre());
				pstm.setString(3, nuevo.getDescripcion());
				pstm.executeUpdate();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		PreparedStatement pstm;
		HashMap<String, Peliculas> ver = leerPeliculas();
		try {
			pstm = conexion.prepareStatement("SELECT * FROM peliculas WHERE Id = " + nuevo.getId());
			ResultSet rset = pstm.executeQuery();
			while (rset.next()) {
				for (Entry<String, Peliculas> entry : ver.entrySet()) {
					if (entry.getValue().getId().equals(nuevo.getId())) {
						// System.out.println("Id repetido");
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	@Override
	public boolean borrarActores() throws IOException {
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement("DELETE FROM actores");
			pstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean borrarPeliculas() throws IOException {
		borrarActores();
		PreparedStatement pstm;
		try {
			pstm = conexion.prepareStatement("DELETE  FROM peliculas");
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean borrarUnActor(String Id) throws IOException {
		boolean fin = false;
		PreparedStatement pstm;
		try {
			String query = "DELETE from actores Where Id = " + Id;
			pstm = conexion.prepareStatement(query);
			pstm.executeUpdate();
			fin = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fin;
	}

	@Override
	public boolean borrarUnaPelicula(String Id) throws IOException {
		boolean fin = false;
		PreparedStatement pstm;
		try {
			String query = "UPDATE actores SET " + "Pelicula = NULL " + "WHERE Pelicula = " + Id;
			String query2 = "Delete from peliculas Where Id = " + Id;
			pstm = conexion.prepareStatement(query);
			pstm.executeUpdate();
			pstm = conexion.prepareStatement(query2);
			pstm.executeUpdate();
			fin = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fin;
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

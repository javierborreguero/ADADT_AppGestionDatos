package Controlador;

import java.io.IOException;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import Modelo.Actores;
import Modelo.Peliculas;

public class HibernateManager implements Intercambio {
	private SessionFactory sessionFactory;
	private Session s;
	public HibernateManager() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
			s = sessionFactory.openSession();
			System.out.println("Conexion establecida con Hibernate");

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public HashMap<String, Actores> leerActores() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		// TODO Auto-generated method stub
		return null;
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

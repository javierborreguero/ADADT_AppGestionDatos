package Controlador;

import java.io.IOException;
import java.util.HashMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import Modelo.Actores;
import Modelo.Peliculas;

public class HibernateManager implements Intercambio {
	// Creaci�n de las variables para crear la conexi�n
	private SessionFactory sessionFactory;
	private Session s;

//Constructor en el que se crea y se establece la conexi�n
	public HibernateManager() {
		try {
			// Se establece la conexi�n
			sessionFactory = new Configuration().configure().buildSessionFactory();
			s = sessionFactory.openSession();
			System.out.println("Conexion establecida con Hibernate");

		} catch (Throwable e) {
			System.err.println("Initial SessionFactory creation failed." + e);
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public HashMap<String, Actores> leerActores() throws IOException {
		HashMap<String, Actores> leerActores = new HashMap<String, Actores>();
		Query q = s.createQuery("select e from Actores e");
		List results = q.list();
		Iterator iterator = results.iterator();
		Peliculas mPeliculas;
		while (iterator.hasNext()) {
			Actores mActores = (Actores) iterator.next();
			if (mActores.getPeliculas() == null) {
				mPeliculas = new Peliculas("null");
				mActores.setPeliculas(mPeliculas);
				leerActores.put(mActores.getId(), mActores);
			} else {
				leerActores.put(mActores.getId(), mActores);
			}
		}
		return leerActores;
	}

	@Override
	public HashMap<String, Peliculas> leerPeliculas() throws IOException {
		HashMap<String, Peliculas> leerPeliculas = new HashMap<String, Peliculas>();
		Query q = s.createQuery("select e from Peliculas e");
		List results = q.list();
		Iterator iterator = results.iterator();
		while (iterator.hasNext()) {
			Peliculas mPeliculas = (Peliculas) iterator.next();
			leerPeliculas.put(mPeliculas.getId(), mPeliculas);
		}
		return leerPeliculas;
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

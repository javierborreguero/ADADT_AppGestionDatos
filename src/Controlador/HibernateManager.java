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
	// Creación de las variables para crear la conexión
	private SessionFactory sessionFactory;
	private Session s;

//Constructor en el que se crea y se establece la conexión
	public HibernateManager() {
		try {
			// Se establece la conexión
			sessionFactory = new Configuration().configure().buildSessionFactory();
			s = sessionFactory.openSession();
			System.out.println("Conexion establecida con Hibernate");

		} catch (Throwable e) {
			System.err.println("Initial SessionFactory creation failed." + e);
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
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
		if (comprobarIdActor(nuevo)) {
			s.beginTransaction();
			s.save(nuevo);
			s.getTransaction().commit();
			return true;
		}
		return false;
	}

	@Override
	public boolean comprobarIdActor(Actores nuevo) throws IOException {
		Actores mActores = nuevo;
		try {
			mActores = (Actores) s.get(Actores.class, mActores.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (mActores == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isertarPelicula(Peliculas nuevo) throws IOException {
		if (comprobarIdPeli(nuevo)) {
			s.beginTransaction();
			s.save(nuevo);
			s.getTransaction().commit();
			return true;
		}
		return false;
	}

	@Override

	public boolean comprobarIdPeli(Peliculas nuevo) throws IOException {
		Peliculas mPeliculas = nuevo;
		try {
			mPeliculas = (Peliculas) s.get(Peliculas.class, mPeliculas.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (mPeliculas == null) {
			return true;
		}
		return false;
	}

	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE BORRADO
	 * 
	 * ----------------------------------
	 */

	@Override
	public boolean borrarActores() throws IOException {
		s.beginTransaction();
		String query = "DELETE FROM Actores";
		Query mQuery = s.createQuery(query);
		mQuery.executeUpdate();
		s.getTransaction().commit();
		return true;
	}

	@Override
	public boolean borrarPeliculas() throws IOException {
		borrarActores();
		s.beginTransaction();
		String query = "DELETE FROM Peliculas";
		Query mQuery = s.createQuery(query);
		mQuery.executeUpdate();
		s.getTransaction().commit();
		return true;

	}

	@Override
	public boolean borrarUnActor(String Id) throws IOException {
		boolean fin = false;
		Actores mActor = new Actores();
		HashMap<String, Actores> ver = leerActores();
		for (Entry<String, Actores> entry : ver.entrySet()) {
			if (entry.getValue().getId().equals(Id)) {
				mActor = entry.getValue();
				s.beginTransaction();
				s.delete(mActor);
				s.getTransaction().commit();
				fin = true;
			}
		}
		return fin;
	}

	@Override
	public boolean borrarUnaPelicula(String Id) throws IOException {
		boolean fin = false;
		Actores mActor = new Actores();
		HashMap<String, Actores> ver = leerActores();
		for (Entry<String, Actores> entry : ver.entrySet()) {
			if (entry.getValue().getPeliculas().equals(Id)) {
				mActor = entry.getValue();
				mActor.setPeliculas(null);
				s.beginTransaction();
				s.update(mActor);

			}
		}
		Peliculas mPeliculas = new Peliculas();
		HashMap<String, Peliculas> verPelicula = leerPeliculas();
		for (Entry<String, Peliculas> entry : verPelicula.entrySet()) {
			if (entry.getValue().getId().equals(Id)) {
				mPeliculas = entry.getValue();
				s.delete(mPeliculas);
				s.getTransaction().commit();
				fin = true;
			}
		}
		return fin;
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
		borrarPeliculas();
		for (Entry<String, Peliculas> entry : lista.entrySet()) {
			isertarPelicula(lista.get(entry.getKey()));
		}

	}

	/*
	 * 
	 * -----------------------------------
	 * 
	 * MÉTODOS DE MODIFICAR
	 * 
	 * ----------------------------------
	 */

	@Override
	public boolean modificarUnActor(String idmodificar, Actores modificar) throws IOException {
		boolean fin = false;
		s.beginTransaction();
		Actores mActores = (Actores) s.get(Actores.class, idmodificar);
		mActores.setId(idmodificar);
		mActores.setNombre(modificar.getNombre());
		mActores.setPeliculas(modificar.getPeliculas());
		mActores.setNacionalidad(modificar.getNacionalidad());
		mActores.setEdad(modificar.getEdad());
		mActores.setResidencia(modificar.getResidencia());
		s.update(mActores);
		fin = true;
		s.getTransaction().commit();

		return fin;
	}

	@Override
	public boolean modificarUnaPelicula(String idmodificar, Peliculas modificar) throws IOException {
		boolean fin = false;
		s.beginTransaction();
		Peliculas mPeliculas = (Peliculas) s.get(Peliculas.class, idmodificar);
		mPeliculas.setId(idmodificar);
		mPeliculas.setNombre(modificar.getNombre());
		mPeliculas.setDescripcion(modificar.getDescripcion());
		s.update(mPeliculas);
		fin = true;
		s.getTransaction().commit();

		return fin;
	}

}

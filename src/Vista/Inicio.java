package Vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map.Entry;

import Controlador.Controlador;
import Controlador.FileManager;
import Modelo.Actores;
import Modelo.Peliculas;

public class Inicio {
	// Creamos las variables que vamos a usar durante toda la clase
	private Controlador miControlador;
	private Scanner teclado;
	private String opcionPrincipal;
	private String modificar;
	private String idBorrar;
	private int opcionSecundaria;
	private int acceso;
	private int leer;
	private int aniadir;
	private int importar;
	private int exportar;
	private int borrarTodo;
	private int op_modificar;
	private int borrartUno;
	private boolean opcionCorrecta;
	private boolean salir;

	public Inicio() {
		// Inicializamos las variables que vamos a ituilizar
		miControlador = new Controlador();
		teclado = new Scanner(System.in);
	}

	// El m�todo run, nos permite ejecutar nuestra app
	public void run() {
		try {
			menuOpciones();
			menuPrincipal();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// M�todo para elegir el modo de trabajo
	public void menuOpciones() throws FileNotFoundException, IOException {

		opcionPrincipal = "";
		opcionCorrecta = true;
		while (opcionCorrecta) {
			System.out.println("�Que opci�n quieres utilizar?");
			System.out.println("1. Fichero");
			System.out.println("2. Base de datos");
			acceso = teclado.nextInt();
			switch (acceso) {
			case 1:
				opcionPrincipal = "Fichero";
				miControlador.elegiarOpcion(acceso);
				opcionCorrecta = false;
				break;
			case 2:
				opcionPrincipal = "Base de datos";
				miControlador.elegiarOpcion(acceso);
				opcionCorrecta = false;
				break;
			default:
				System.err.println("Dato mal introducido");
			}
		}
	}

	public void menuPrincipal() throws IOException {
		salir = true;
		while (salir) {

			System.out.println("Has elegido trabajar en " + opcionPrincipal);
			System.out.println("�Que quieres hacer?");
			System.out.println("1. Leer");
			System.out.println("2. A�adir");
			System.out.println("3. Importar");
			System.out.println("4. Borrar");
			System.out.println("5. Salir");
			opcionSecundaria = teclado.nextInt();
			switch (opcionSecundaria) {
			case 1:
				leer();
				break;
			case 2:
				aniadir();
				break;
			case 3:
				importar(acceso);
				break;
			case 4:
				borrarTodo();
				break;

			case 5:
				salir = false;
				break;
			default:
				System.err.println("Dato mal introducido");

			}

		}
	}

	/*
	 * 
	 * M�TODOS PARA SELECCIONAR LEER (ACTORES O PEL�CULAS)
	 * 
	 */

	public void leer() throws IOException {
		System.out.println("Que deseas leer?");
		System.out.println("1. Actores");
		System.out.println("2. Peliculas");
		leer = teclado.nextInt();
		switch (leer) {
		case 1:
			leerActores();
			break;
		case 2:
			leerPeliculas();
			break;
		default:
			System.err.println("Dato mal introducido");
		}

	}

	/*
	 * 
	 * M�TODOS PARA LEER ACTORES
	 * 
	 */

	public void leerActores() throws IOException {
		int contador = 1;
		if (miControlador.leerActores() == null) {
			System.out.println("No existen actores");
		} else {
			for (Entry<String, Actores> entry : miControlador.leerActores().entrySet()) {
				System.out.println("---- Actor " + contador + " ----");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Nacionalidad: " + entry.getValue().getNacionalidad());
				System.out.println("Edad: " + entry.getValue().getEdad());
				System.out.println("Residencia: " + entry.getValue().getResidencia());
				if (entry.getValue().getPeliculas().getId().equals("null")) {
					System.out.println("No tiene ninguna pelicula asignada");
				} else {
					System.out.println("Pelicula: " + entry.getValue().getPeliculas().getId());
				}
				contador++;
			}
		}
	}

	/*
	 * 
	 * M�TODOS PARA LEER PELICULAS
	 * 
	 */

	public void leerPeliculas() throws IOException {
		int contador = 1;
		if (miControlador.leerPeliculas() == null) {
			System.out.println("No existen peliculas");
		} else {
			for (Entry<String, Peliculas> entry : miControlador.leerPeliculas().entrySet()) {
				System.out.println("---- Pelicula " + contador + " ----");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Descripcion: " + entry.getValue().getDescripcion());
				contador++;
			}
		}

	}

	/*
	 * 
	 * M�TODOS PARA SELECCIONAR A�ADIR (ACTORES O PEL�CULAS)
	 * 
	 */

	public void aniadir() throws IOException {
		System.out.println("�Que quieres a�adir?");
		System.out.println("1. Actor");
		System.out.println("2. Pelicula");
		aniadir = teclado.nextInt();
		switch (aniadir) {
		case 1:
			insertarActor();
			break;
		case 2:
			insertarPelicula();
			break;
		default:
			System.out.println("Dato mal introducido");
		}

	}

	/*
	 * 
	 * M�TODOS PARA INSERTAR ACTORES
	 * 
	 */

	public void insertarActor() throws IOException {
		Scanner teclado = new Scanner(System.in);
		// teclado.nextLine();
		System.out.println("Introduzca el id del actor");
		String id = teclado.nextLine();
		System.out.println("Introduzca el nombre del actor");
		String nombre = teclado.nextLine();
		System.out.println("Introduzca la nacionalidad del actor");
		String nacionalidad = teclado.nextLine();
		System.out.println("Introduzca la edad del actor");
		String edad = teclado.nextLine();
		System.out.println("introduzca la residencia del actor");
		String residencia = teclado.nextLine();
		System.out.println("Introduzca el id de la pelicula");
		String id_pelicula = teclado.nextLine();
		Peliculas obj_peliculas = miControlador.escogerPelicula(id_pelicula);
		if (obj_peliculas != null) {
			Actores nuevo = new Actores(id, nombre, nacionalidad, edad, residencia, obj_peliculas);
			if (miControlador.pedirdatosActores(nuevo)) {
				System.out.println("Actor a�adido correctamente");
			} else {
				System.out.println("El actor no se ha podido a�adir");
			}
		} else {
			System.out.println("No se ha encontrado ning�n actor");
		}
	}

	/*
	 * 
	 * M�TODOS PARA INSERTAR PEL�CULAS
	 * 
	 */

	public void insertarPelicula() throws IOException {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduzca el id de la pelicula");
		String id = teclado.nextLine();
		System.out.println("Introduzca el nombre de la pelicula");
		String nombre = teclado.nextLine();
		System.out.println("Introduzca la descripcion de la pelicula");
		String descripcion = teclado.nextLine();
		Peliculas nuevo = new Peliculas(id, nombre, descripcion);
		if (miControlador.pedirdatosPeliculas(nuevo)) {
			System.out.println("Pelicula a�adida correctamente");
		} else {
			System.out.println("La pelicula no se ha podid a�adir");
		}
	}

	public void importar(int aux) throws IOException {

		if (acceso == 1) {
			System.out.println("�De d�nde quieres copiar los datos?");
			System.out.println("2. Base de datos");
			importar = teclado.nextInt();
			switch (importar) {

			case 1:
				// importarFicheros(importar);
				break;
			case 2:
				importarDB(importar);
				break;
			default:
				System.out.println("Dato mal introducido");
			}
		}
		if (acceso == 2) {
			System.out.println("�De d�nde quieres copiar los datos?");
			System.out.println("1. Ficheros");
			importar = teclado.nextInt();
			switch (importar) {
			case 1:
				importarFicheros(importar);
				break;
			case 2:
				// importarDB(importar);
				break;
			default:
				System.out.println("Dato mal introducido");
			}
		}
	}

	public void importarFicheros(int importar) throws IOException {
		if (acceso == 2) {
			miControlador.importar(importar);
			if (miControlador.importar(importar)) {
				System.out.println("Datos copiados correctamente");
			} else {
				System.out.println("No se han podido copiar los datos");
			}
		}

	}

	public void importarDB(int importar) throws IOException {
		if (acceso == 1) {
			miControlador.importar(importar);
			if (miControlador.importar(importar)) {
				System.out.println("Datos copiados correctamente");
			} else {
				System.out.println("No se han podido copiar los datos");
			}

		}

	}

	/*
	 * 
	 * M�TODOS PARA SELECCIONAR BORRAR (ACTORES O PEL�CULAS)
	 * 
	 */

	public void borrarTodo() throws IOException {
		System.out.println("�Donde quieres borrar todos los datos?");
		System.out.println("1. Actores");
		System.out.println("2. Peliculas");
		borrarTodo = teclado.nextInt();
		switch (borrarTodo) {
		case 1:
			borrarTodosActores();
			break;
		case 2:
			borrarTodasPeliculas();
			break;
		default:
			System.out.println("Dato mal introducido");
		}

	}

	/*
	 * 
	 * M�TODOS PARA BORRAR ACTORES
	 * 
	 */

	public void borrarTodosActores() throws IOException {
		if (miControlador.borrarActores()) {
			System.out.println("Datos borrados correctmente");
		} else {
			System.out.println("No se han podido borrar los datos");
		}

	}

	/*
	 * 
	 * M�TODOS PARA BORRAR PELICULAS
	 * 
	 */

	public void borrarTodasPeliculas() throws IOException {
		if (miControlador.borrarPeliculas()) {
			System.out.println("Datos borrados correctmente");
		} else {
			System.out.println("No se han podido borrar los datos");
		}

	}

}
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

	// El método run, nos permite ejecutar nuestra app
	public void run() {
		try {
			menuOpciones();
			menuPrincipal();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	// Método para elegir el modo de trabajo
	public void menuOpciones() throws FileNotFoundException, IOException {

		opcionPrincipal = "";
		opcionCorrecta = true;
		while (opcionCorrecta) {
			System.out.println("¿Que opción quieres utilizar?");
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
			System.out.println("¿Que quieres hacer?");
			System.out.println("1. Leer");
			System.out.println("2. Añadir");
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
	 * MÉTODOS PARA SELECCIONAR LEER (ACTORES O PELÍCULAS)
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
	 * MÉTODOS PARA LEER ACTORES
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
	 * MÉTODOS PARA LEER PELICULAS
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

	public void aniadir() throws IOException {
		System.out.println("¿Que quieres añadir?");
		System.out.println("1. Actor");
		System.out.println("2. Pelicula");
		aniadir = teclado.nextInt();
		switch (aniadir) {
		case 1:
			miControlador.insertarActor();
			break;
		case 2:
			miControlador.insetarPelicula();
			break;
		default:
			System.out.println("Dato mal introducido");
		}

	}

	public void importar(int aux) throws IOException {

		importar = teclado.nextInt();
		if (acceso == 1) {
			System.out.println("¿Donde quieres importar los datos");
			System.out.println("1. Fichero");
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

	private void importarFicheros(int importar) throws IOException {
		if (acceso == 1) {
			System.out.println("No puedes copiar loa datos del fichero en el fichero");
		} else if (acceso == 2) {
			miControlador.importar(importar);
		}

	}

	public void borrarTodo() throws IOException {
		System.out.println("¿Donde quieres borrar todos los datos?");
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

	public void borrarTodosActores() throws IOException {
		miControlador.borrarActores();

	}

	public void borrarTodasPeliculas() throws IOException {
		miControlador.borrarPeliculas();

	}

}

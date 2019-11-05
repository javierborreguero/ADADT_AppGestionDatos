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
	private int borrarUno;
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
			System.out.println("3. Hibernate");
			System.out.println("4. Mongo");
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
			case 3:
				opcionPrincipal = "Hibernate";
				miControlador.elegiarOpcion(acceso);
				opcionCorrecta = false;
				break;
			case 4:
				opcionPrincipal = "Mongo";
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
			System.out.println("4. Borrar todo");
			System.out.println("5. Borrar uno");
			System.out.println("6. Modificar");
			System.out.println("7. Volver al menú principal");
			System.out.println("8. Salir");
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
				borrarUno();
			case 6:
				modificar();
				break;
			case 7:
				menuOpciones();
				break;
			case 8:
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
				System.out.println("----------- Actor " + contador + " -----------");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Nacionalidad: " + entry.getValue().getNacionalidad());
				System.out.println("Edad: " + entry.getValue().getEdad());
				System.out.println("Residencia: " + entry.getValue().getResidencia());
				if (entry.getValue().getPeliculas().getId().equals("null")) {
					System.out.println("No tiene ninguna pelicula asignada");
				} else {
					System.out.println("Pelicula: " + entry.getValue().getPeliculas().getId());
					//System.out.println("Nombre Pelicula" + entry.getValue().getPeliculas().getNombre());

				}
				contador++;
				System.out.println("-------------------------------- \n");
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
				System.out.println("---------- Pelicula " + contador + " -----------");
				System.out.println("Id: " + entry.getValue().getId());
				System.out.println("Nombre: " + entry.getValue().getNombre());
				System.out.println("Descripcion: " + entry.getValue().getDescripcion());
				contador++;
				System.out.println("-------------------------------- \n");
			}
		}

	}

	/*
	 * 
	 * MÉTODOS PARA SELECCIONAR AÑADIR (ACTORES O PELÍCULAS)
	 * 
	 */

	public void aniadir() throws IOException {
		System.out.println("¿Que quieres añadir?");
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
	 * MÉTODOS PARA INSERTAR ACTORES
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
				System.out.println("Actor añadido correctamente");
			} else {
				System.out.println("El actor no se ha podido añadir");
			}
		} else {
			System.out.println("No se ha encontrado ningún actor");
		}
	}

	/*
	 * 
	 * MÉTODOS PARA INSERTAR PELÍCULAS
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
			System.out.println("Pelicula añadida correctamente");
		} else {
			System.out.println("La pelicula no se ha podid añadir");
		}
	}

	public void importar(int aux) throws IOException {

		if (acceso == 1) {
			System.out.println("¿De dónde quieres copiar los datos?");
			System.out.println("2. Base de datos");
			System.out.println("3. Hibernate");
			importar = teclado.nextInt();
			switch (importar) {

			case 1:
				// importarFicheros(importar);
				break;

			case 2:
				importarDB(importar);
				break;
			case 3:
				importarHM(importar);
				break;
			default:
				System.out.println("Dato mal introducido");
			}
		}
		if (acceso == 2) {
			System.out.println("¿De dónde quieres copiar los datos?");
			System.out.println("1. Ficheros");
			importar = teclado.nextInt();
			switch (importar) {
			case 1:
				importarFicheros(importar);
				break;
			case 2:
				importarHM(importar);
				break;
			case 3:
				// importarDB(importar);
				break;
			default:
				System.out.println("Dato mal introducido");
			}
		}
		if (acceso == 3) {
			System.out.println("¿De dónde quieres copiar los datos?");
			System.out.println("1. Ficheros");
			System.out.println("2. Base de datos");
			importar = teclado.nextInt();
			switch (importar) {
			case 1:
				importarFicheros(importar);
				break;
			case 2:
				importarDB(importar);
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
		} else if (acceso == 3) {
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

		} else if (acceso == 3) {
			miControlador.importar(importar);
			if (miControlador.importar(importar)) {
				System.out.println("Datos copiados correctamente");
			} else {
				System.out.println("No se han podido copiar los datos");
			}
		}

	}

	public void importarHM(int importar) throws IOException {
		if (acceso == 1) {
			miControlador.importar(importar);
			if (miControlador.importar(importar)) {
				System.out.println("Datos copiados correctamente");
			} else {
				System.out.println("No se han podido copiar los datos");
			}

		} else if (acceso == 2) {
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
	 * MÉTODOS PARA SELECCIONAR BORRAR (ACTORES O PELÍCULAS)
	 * 
	 */

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

	/*
	 * 
	 * MÉTODOS PARA BORRAR ACTORES
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
	 * MÉTODOS PARA BORRAR PELICULAS
	 * 
	 */

	public void borrarTodasPeliculas() throws IOException {
		if (miControlador.borrarPeliculas()) {
			System.out.println("Datos borrados correctmente");
		} else {
			System.out.println("No se han podido borrar los datos");
		}

	}

	/*
	 * 
	 * MÉTODOS PARA SELECCIONAR BORRAR (UN ACTOR O UNA PELÍCULA)
	 * 
	 */

	public void borrarUno() throws IOException {
		teclado.nextLine();
		System.out.println("¿Donde quieres borrar un dato?");
		System.out.println("1. Actores");
		System.out.println("2. Peliculas");
		borrarUno = teclado.nextInt();
		switch (borrarUno) {
		case 1:
			mostrarIdActor();
			break;
		case 2:
			mostrarIdPelicula();
			break;
		default:
			break;
		}
		teclado.nextLine();
		idBorrar = teclado.nextLine();
		switch (borrarUno) {
		case 1:
			borrarUnActor(idBorrar);
			break;
		case 2:
			borrarUunaPelicula(idBorrar);
			break;
		default:
			System.out.println("Dato mal introducido");
			break;
		}

	}

	public void mostrarIdPelicula() throws IOException {
		System.out.println("Escoga el id de la pelicula");
		for (Entry<String, Peliculas> entry : miControlador.leerIdPeliculas().entrySet()) {
			System.out.println(entry.getValue().getId() + "." + entry.getValue().getNombre() + " ");
		}
	}

	public void mostrarIdActor() throws IOException {
		System.out.println("Escoga el id del actor");
		for (Entry<String, Actores> entry : miControlador.leerIdActores().entrySet()) {
			System.out.println(entry.getValue().getId() + "." + entry.getValue().getNombre() + " ");
		}
	}

	public void borrarUunaPelicula(String idBorrar) throws IOException {
		if (miControlador.borrarUnaPelicula(idBorrar)) {
			System.out.println("Pelicula borrada correctamente");
		} else {
			System.out.println("La peliculas no se ha podido borrar");
		}

	}

	public void borrarUnActor(String idBorrar) throws IOException {
		if (miControlador.borrarUnActor(idBorrar)) {
			System.out.println("Actor borrado correctamente");
		} else {
			System.out.println("El actor no se ha podido borrar");
		}

	}

	/*
	 * 
	 * MÉTODOS PARA SELECCIONAR MODIFICAR (UN ACTOR O UNA PELÍCULA)
	 * 
	 */

	public void modificar() throws IOException {
		teclado.nextLine();
		modificar = null;
		System.out.println("¿De dónde quieres modificar un dato?");
		System.out.println("1. Actores");
		System.out.println("2. Peliculas");
		op_modificar = teclado.nextInt();
		switch (op_modificar) {
		case 1:
			modificar = mostrarIdActorModificar();
			break;
		case 2:
			modificar = mostrarIdPeliModificar();
			break;
		default:
			System.out.println("Dato mal introducido");
			break;
		}
//		teclado.nextLine();
//		modificar = null;
//		op_modificar = teclado.nextInt();
		switch (op_modificar) {
		case 1:
			modificarActor();
			break;
		case 2:
			modificarPelicula();
			break;
		default:
			System.out.println("Dato mal introducido");
			break;
		}

	}

	/*
	 * 
	 * MÉTODOS PARA MODIFICAR UN ACTOR
	 * 
	 */

	public void modificarActor() throws IOException {
		System.out.println("Escriba el nuevo nombre del actor");
		String nNombre = teclado.nextLine();
		System.out.println("Escriba la nacionalidad del actor");
		String nNacionalidad = teclado.nextLine();
		System.out.println("Escriba la nueva edad del actor");
		String nEdad = teclado.nextLine();
		System.out.println("Escriba la nueva residencia del actor");
		String nResidencia = teclado.nextLine();
		System.out.println("El actor está asociado a una pelicula. Lea la información de las películas");
		mostrarIdPelicula();
		System.out.println("Escrib el id de la nueva película");
		String nueva_peli = teclado.nextLine();
		Peliculas peliculas = miControlador.getPeliculas().get(nueva_peli);
		Actores actor_modificado = new Actores(modificar, nNombre, nNacionalidad, nEdad, nResidencia, peliculas);
		if (miControlador.modificarUnActor(modificar, actor_modificado)) {
			System.out.println("El actor ha sido odificado correctamente en " + opcionPrincipal);
		} else {
			System.out.println("El actor no se ha podido modificar en " + opcionPrincipal);
		}
	}

	public String mostrarIdActorModificar() throws IOException {
		System.out.println("En tu fichero tienes guardados los siguientes actores:");
		mostrarIdActor();
		teclado.nextLine();
		System.out.println("Escriba el id del actor que quieres modificar");
		String modificar = null;
		modificar = teclado.nextLine();
		return modificar;
	}

	/*
	 * 
	 * MÉTODOS PARA MODIFICAR UNA PELÍCULA
	 * 
	 */

	public void modificarPelicula() throws IOException {
		System.out.println("Escriba el nuevo nombre de la película");
		String nNombre = teclado.nextLine();
		System.out.println("Escriba la nueva descripción de la película");
		String ndescripcion = teclado.nextLine();
		Peliculas peli_modificada = new Peliculas(modificar, nNombre, ndescripcion);
		if (miControlador.modificarUnaPelicula(modificar, peli_modificada)) {
			System.out.println("La película ha sido modificada correctamente en " + opcionPrincipal);

		} else {
			System.out.println("La película no se ha podido modificar en " + opcionPrincipal);

		}
	}

	public String mostrarIdPeliModificar() throws IOException {
		System.out.println("En tu fichero tienes guardadoas las siguientes películas:");
		mostrarIdPelicula();
		teclado.nextLine();
		System.out.println("Escriba el id de la película que quieres modificar");
		String modificar = null;
		modificar = teclado.nextLine();
		return modificar;
	}

}

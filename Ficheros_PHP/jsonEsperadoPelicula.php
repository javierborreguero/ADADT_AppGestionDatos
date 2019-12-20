<?php

/*
Formato JSON esperado
*/

$arrEsperado = array();
$arrPeliculaEsperado = array();

$arrPeliculaEsperado["id"] = "1 (Un string)";
$arrPeliculaEsperado["nombre"] = "Harry Potter (Un string)";
$arrPeliculaEsperado["descripcion"] = "vfdvdb (Un string)";

$arrEsperado["peticion"] = "add";

$arrEsperado["peliculaAnnadir"] = $arrPeliculaEsperado;

/*
Función que comprueba si el dato recibido es igual al esperado
*/

function JSONCorrectoAnnadirPelicula($recibido) {
  $auxCorrecto = false;

  if(isset($recibido["peticion"]) && $recibido["peticion"] = "add" && isset($recibido["peliculaAnnadir"])) {
    $auxPelicula = $recibido["peliculaAnnadir"];
    if(isset($auxPelicula["id"]) && isset($auxPelicula["nombre"]) && isset($auxPelicula["descripcion"])){
			$auxCorrecto = true;
		}

	}

  return $auxCorrecto;

}

 ?>

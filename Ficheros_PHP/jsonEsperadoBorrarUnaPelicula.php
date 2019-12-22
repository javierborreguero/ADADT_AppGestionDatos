<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrPeliculaEsperado = array();

$arrPeliculaEsperado["Id"] = "1";

$arrEsperado["peticion"] = "delete";


$arrEsperado["peliculaBorrar"] = $arrPeliculaEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoBorrarRepresentante($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="delete" && isset($recibido["peliculaBorrar"])){

		$auxPelicula = $recibido["peliculaBorrar"];
		if(isset($auxPelicula["Id"])){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}

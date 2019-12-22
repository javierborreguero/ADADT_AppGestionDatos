<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrRepresentanteEsperado = array();


$arrPeliculaEsperado["id"] = "1 (Un string)";
$arrPeliculaEsperado["nombre"] = "David (Un string)";
$arrPeliculaEsperado["descripcion"] = "vetgffdsghsgh (Un string)";

$arrEsperado["peticion"] = "update";


$arrEsperado["peliculaModificar"] = $arrPeliculaEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoModificarRepresentante($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="update" && isset($recibido["representanteModificar"])){

		$auxPelicula = $recibido["peliculaModificar"];
		if(isset($auxPelicula["id"]) && isset($auxPelicula["nombre"]) && isset($auxPelicula["descripcion"])){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}

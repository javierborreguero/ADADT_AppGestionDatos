<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrActorEsperado = array();

$arrActorEsperado["Id"] = "1";
$arrActorEsperado["Nombre"] = "nombre";
$arrActorEsperado["Nacionalidad"]= "Nacionalidad";
$arrActorEsperado["Edad"]= "Edad";
$arrActorEsperado["Residencia"]= "Residencia";
$arrActorEsperado["Pelicula"]= " id Pelicula";

$arrEsperado["peticion"] = "update";


$arrEsperado["actorModificar"] = $arrActorEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoModificarActor($recibido){

	$auxCorrecto = false;

	if(isset($recibido["peticion"]) && $recibido["peticion"] ="update" && isset($recibido["actorModificar"])){

		$auxActor = $recibido["actorModificar"];
		if(isset($auxActor["Id"]) && isset($auxActor["Nombre"]) && isset($auxActor["Nacionalidad"])&& isset ($auxActor["Edad"]) && isset ($auxActor["Residencia"]) && isset ($auxActor["Pelicula"])){
			$auxCorrecto = true;
		}

	}


	return $auxCorrecto;

}

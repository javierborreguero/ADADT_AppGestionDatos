<?php

/*
Formato JSON esperado
*/

$arrEsperado = array();
$arrActorEsperado = array();

$arrActorEsperado["id"] = "1 (Un string)";
$arrActorEsperado["nombre"] = "Liam Neeson (Un string)";
$arrActorEsperado["pelicula"] = "2 (Un string)";
$arrActorEsperado["nacionalidad"] = "estadounidense (Un string)";
$arrActorEsperado["edad"] = "49 (Un string)";
$arrActorEsperado["residencia"] = "Nueva York (Un string)";

$arrEsperado["peticion"] = "add";
$arrEsperado["actorAnnadir"] = $arrActorEsperado;

/*
Funcion paa comprobar si el recibido es igual al esperado
*/

function JSONCorrectoAnnadirActor($recibido) {
  $auxCorrecto = false;

  if(isset($recibido["peticion"]) && $recibido["peticion"] = "add" && isset($recibido["actorAnnadir"])) {
    $auxActor = $recibido["actorAnnadir"];

    if (isset($auxActor["id"]) && isset($auxActor["nombre"]) && isset($auxActor["pelicula"]) && isset($auxActor["nacionalidad"]) && isset($auxActor["edad"]) && isset($auxActor["residencia"])) {
      $auxCorrecto = true;
    }
  }
  return $auxCorrecto;
}
 ?>

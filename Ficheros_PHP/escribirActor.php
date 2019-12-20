<?php

/*
Fichero que gestiona la conexion con la base de datos
*/

require 'bbdd.php';
require 'jsonEsperadoActor.php';

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMensaje = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

/*
 * Lo primero es comprobar que nos han enviado la información via JSON
 */

$parameters = file_get_contents("php://input");

if (isset($parameters)) {

  /*
  Parseamos el string json y lo convertimos a objeto JSON
  */

  $mensajeRecibido = json_decode($parameters, true);

  /*
  Comprobamos que están todos los datos en el json que hemos recibido.
  */

  if (JSONCorrectoAnnadirActor($mensajeRecibido)) {
    $actor = $mensajeRecibido["actorAnnadir"];
    $id = $actor["id"];
    $nombre = $actor["nombre"];
    $idpelicula = $actor["pelicula"];
    $nacionalidad = $actor["nacionalidad"];
    $edad = $actor["edad"];
    $residencia = $actor["residencia"];

    if($pelicula == "null"){
			$query  = "INSERT INTO  actores (Id,Nombre,Pelicula,Nacionalidad,Edad,Residencia) ";
			$query .= "VALUES ('$id','$nombre','null','$nacionalidad','$edad','$residencia')";
		}else{
      $query  = "INSERT INTO  actores (Id,Nombre,Pelicula,Nacionalidad,Edad,Residencia) ";
      $query .= "VALUES ('$id','$nombre','$idpelicula','$nacionalidad','$edad','$residencia')";
		}

    $result = $conn->query ( $query );

    /*
    Si pasa por este if, la query está está bien y se ha insertado correctamente
    */

    if (isset ( $result) && $result) {
      $arrMensaje["estado"] = "ok";
      $arrMensaje["mensaje"] = "Actor insertado correctamente";
    } else {
      $arrMensaje["estado"] = "error";
      $arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
      $arrMensaje["error"] = $conn->error;
      $arrMensaje["query"] = $query;
    }

  } else {
    $arrMensaje["estado"] = "error";
    $arrMensaje["mensaje"] = "EL JSON NO CONTIENE LOS CAMPOS ESPERADOS";
    $arrMensaje["recibido"] = $mensajeRecibido;
    $arrMensaje["esperado"] = $arrEsperado;
  }
} else {
  $arrMensaje["estado"] = "error";
  $arrMensaje["mensaje"] = "EL JSON NO SE HA ENVIADO CORRECTAMENTE";
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

die();
?>

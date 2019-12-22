<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD
require 'jsonEsperadoBorrarUnaPelicula.php';

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMensaje = array();  // Este array es el codificaremos como JSON tanto si hay resultado como si hay error

/*
 * Lo primero es comprobar que nos han enviado la información via JSON
 */

 if(isset($parameters)){


 } else {	// No nos han enviado el json correctamente

 	$arrMensaje["estado"] = "error";
 	$arrMensaje["mensaje"] = "EL JSON NO SE HA ENVIADO CORRECTAMENTE";

 }

 $mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

 //echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
 echo $mensajeJSON;
 //echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

 $conn->close ();

 die();

$parameters = file_get_contents("php://input");


 ?>

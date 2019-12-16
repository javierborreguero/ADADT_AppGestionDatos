<?php

/* Fichero que gestiona la conexion con la base de datos */

require 'bbdd.php';

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

 $arrMensaje = array(); // En este array el resltado  se parsea a JSON

 $query = "SELECT * FROM actores";
 $result = $conn->query ( $query);

 if (isset ( $result ) && $result) { // Si pasa por este if, la query está está bien y se obtiene resultado
   if ($result->num_rows > 0) {

     $arrActores = array();

     while ($row = $result->fetch_assoc ()) {

       $arrActor = array();
       $arrActor["id"] = $row["Id"];
       $arrActor["nombre"] = $row["Nombre"];
       $arrActor["pelicula"] = $row["Pelicula"];
       $arrActor["nacionalidad"] = $row["Nacionalidad"];
       $arrActor["edad"] = $row["Edad"];
       $arrActor["residencia"] = $row["Residencia"];

       $arrActores[] = $arrActor;

     }

     $arrMensaje["estado"] = "ok";
     $arrMensaje["actores"] = $arrActores;

   } else {

     $arrMensaje["estado"] = "ok";
 		$arrMensaje["actores"] = [];

   }
 } else {

   $arrMensaje["estado"] = "error";
   $arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
   $arrMensaje["error"] = $conn->error;
   $arrMensaje["query"] = $query;
 }

 //var_dump($arrMensaje);

 $mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

 //echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
 echo $mensajeJSON;
 //echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

 $conn->close ();

 ?>

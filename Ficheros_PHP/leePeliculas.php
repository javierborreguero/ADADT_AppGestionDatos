<?php

/* Fichero que gestiona la conexion con la base de datos */

require 'bbdd.php';

/*
 * Se mostrará siempre la información en formato json para que se pueda leer desde un html (via js)
 * o una aplicación móvil o de escritorio realizada en java o en otro lenguajes
 */

$arrMensaje = array(); // En este array el resltado  se parsea a JSON
$query = 'SELECT * FROM peliculas';
$result = $conn->query ( $query );
if (isset ( $result ) && $result) {
  if ($result->num_rows > 0) {
    $arrPeliculas = array();
    while ( $row = $result->fetch_assoc ()) {
      $arrPelicula = array();
      $arrPelicula["id"] = $row["Id"];
      $arrPelicula["nombre"] = $row["Nombre"];
      $arrPelicula["descripcion"] = $row["Descripcion"];
      $arrPeliculas[] = $arrPelicula;
    }

    $arrMensaje["estado"] = "ok";
    $arrMensaje["pelicula"] = $arrPeliculas;
  } else {
    $arrMensaje["estado"] = "ok";
    $arrMensaje["pelicula"] = [];
  }
} else {
  $arrMensaje["estado"] = "error";
  $arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
  $arrMensaje["error"] = $conn->error;
  $arrMensaje["query"] = $query;
}

$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();




 ?>

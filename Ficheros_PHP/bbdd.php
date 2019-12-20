<?php

/* Conexión con la bbdd */

$servername = "localhost";
$user = "root";
$password = "";
$dbname = "cine";
$conn = new mysqli($servername, $user, $password, $dbname);

/* Check connection */

if ($conn->connect_error) {
  die("Error " . $conn->connect_error);
}

if (!$conn->set_charset("utf8")) {
    printf("Error cargando el conjunto de caracteres utf8: %s\n", $conn->error);
    exit();
}

 ?>

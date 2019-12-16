<?php

/* Conexión con la bbdd */

$servername = "localhost";
$user = "root";
$password = "";
$dbname = "cine(1)";
$conn = new mysqli($servername, $user, $password, $dbname);

/* Check connection */

if ($conn->connect_error) {
  die("Error " . $conn->connect_error);
}
 ?>

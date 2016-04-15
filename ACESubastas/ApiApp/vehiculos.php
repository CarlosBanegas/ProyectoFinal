<?php
header('Content-Type: application/json');
$servername = "mysql9.000webhost.com";
$username = "a8560043_root";
$password = "ArVarela29081991";
$dbname="a8560043_apiApp";

// Create connection
$conn = new mysqli($servername, $username, $password,$dbname);

// Check connection

if ($conn->connect_error){
    die("Conection Failed: ". $conn->connect_error);
} 

$sql='SELECT * FROM VEHICULOS';

$result=$conn->query($sql);
$response=array();
$alumnos=array();

if($result->num_rows > 0){
    while($row=$result->fetch_assoc()){
        $alumnos[]=$row;
    }
}
$response['data']=$alumnos;
$response["size"]=count($alumnos);
echo json_encode($response);

$conn->close();

?>

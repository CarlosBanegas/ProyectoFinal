<?php
    $con = mysqli_connect("mysql9.000webhost.com", "a8560043_root", "ArVarela29081991", "a8560043_apiApp");
    
    $marca = $_POST["marca"];
    $modelo = $_POST["modelo"];
    $color = $_POST["color"];
    $ano = $_POST["ano"];
    $tipo = $_POST["tipo"];
    $foto = $_POST["foto"];
    $descripcion = $_POST["descripcion"];
    $precioInicial = $_POST["precioInicial"];

    $statement = mysqli_prepare($con, "INSERT INTO VEHICULOS (marca, modelo, color, ano, tipo, foto, descripcion, precioInicial) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sssssssi", $marca, $modelo, $color, $ano, $tipo, $foto, $descripcion, $precioInicial);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>
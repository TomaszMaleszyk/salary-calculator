<?php 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 //Getting values
 $name = $_POST['name'];
 $price = $_POST['price'];
 
 //Creating an sql query
 $sql = "INSERT INTO produkty (nazwa,cena) VALUES ('$name','$price')";
 
 //Importing our db connection script
 require_once('dbConnect.php');
 
 //Executing query to database
 if(mysqli_query($con,$sql)){
 echo 'Product Added Successfully';
 }else{
 echo 'Could Not Add Product';
 }
 
 //Closing the database 
 mysqli_close($con);
 }
?>
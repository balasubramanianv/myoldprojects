
/*include 'connection.php';
	$row = $_REQUEST['id'];
	$name = $_REQUEST['f_name'];
	$sname = $_REQUEST['s_name'];
	$town = $_REQUEST['town'];
	$item  = $_REQUEST['item'];
	
	$ins = mysql_query("INSERT INTO items (_id, name, s_name, town, item) VALUES('$row','$name','$sname','$town', '$item')");
	if(!$ins)
	{
		echo (mysql_error());
	}
	else
	{
		echo ("data inserted");
	}
?>
*/
<?php
include 'connection.php';
	$table = $_REQUEST['CustTable'];
	$ItemName = $_REQUEST['ItemName'];
	$Quantity = $_REQUEST['Quantity'];
	$PricePerUnit = $_REQUEST['PricePerUnit'];
	$TotalPrice  = $_REQUEST['TotalPrice'];
	$Status  = $_REQUEST['Status'];
	$Billed  = $_REQUEST['Billed'];
	
	$ins = mysql_query("INSERT INTO cust_table (table, ItemName, Quantity, Quantity, PricePerUnit,TotalPrice,Status,Billed) VALUES('$table','$ItemName','$Quantity','$PricePerUnit', '$TotalPrice', '$Status', '$Billed')");
	if(!$ins)
	{
		echo (mysql_error());
	}
	else
	{
		echo ("data inserted");
	}
?>

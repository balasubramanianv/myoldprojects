<?php

$db_host="localhost";
$db_name="pc";
$username="root";
$password="";
$db_con=mysql_connect($db_host,$username,$password);
$connection_string=mysql_select_db($db_name);
echo "connecting";
mysql_connect($db_host,$username,$password);
mysql_select_db($db_name);
echo "connected";

?>

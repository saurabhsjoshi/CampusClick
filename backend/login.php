<?php
require('db.php');
if(connectToDb()) {
	header('Content-Type: application/json');
	$pics_json = json_encode(getUserByUsername($_GET["username"]));
	echo $pics_json;
}
?>
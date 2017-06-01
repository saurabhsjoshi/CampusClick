<?php
require('db.php');
if(connectToDb()) {
	header('Content-Type: application/json');
	$pics_json = json_encode(getPicsByUser($_GET["id"]));
	echo $pics_json;
}
?>
<?php
require('db.php');
if(connectToDb()) {
	header('Content-Type: application/json');
	$user_json = json_encode(getPictureById($_GET["id"]));
	echo $user_json;
}
?>
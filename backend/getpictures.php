<?php
require('db.php');
if(connectToDb()) {
	header('Content-Type: application/json');
	$pics_json = json_encode(getAllPictures());
	echo $pics_json;
}
?>
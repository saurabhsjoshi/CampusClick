<?php
require('db.php');
if(connectToDb()) {
	header('Content-Type: application/json');
	$user_uid = signUpUsers($_POST["username"]);
	$user_json = json_encode(getUserById($user_uid));
	echo $user_json;
}
?>
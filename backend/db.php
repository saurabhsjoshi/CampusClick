<?php
require_once 'config.php';

$db_connection_handle = NULL;
$DEBUG_MODE = FALSE;
function connectToDb() {
	try{
		global $DBUSER, $DBPASS, $DBNAME, $db_connection_handle;
		$db_connection_handle = 
		new PDO("mysql:host=127.0.0.1;dbname=$DBNAME", $DBUSER, $DBPASS);
		$db_connection_handle->
		setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$db_connection_handle->setAttribute(PDO::ATTR_CASE, PDO::CASE_LOWER);
		$db_connection_handle->
		setAttribute(PDO::ATTR_ORACLE_NULLS, PDO::NULL_NATURAL);
		debug_to_console("Connected!");
		return TRUE;
	} catch (PDOException $e) {
		debug_to_console ('PDO ERROR: '.$e->getMessage()."\n");
		return FALSE;
	}
}

function getAllPictures() {
	$pictureList = array();
	try {
		global $db_connection_handle;
		$sql = 'SELECT pics.id, pics.userID, pics.lat, pics.lon, pics.dateCreated, users.username FROM pics INNER JOIN users ON pics.userID = users.id';
		$st = $db_connection_handle->prepare($sql);
		$st->execute();
		while($result = $st->fetch(PDO::FETCH_ASSOC)) {
			array_push($pictureList, $result);
		}
	} catch(PDOException $e) {
		debug_to_console('PDO ERROR: '.$e->getMessage()."\n");
	}
	return $pictureList;
}

function getPicsByUser($id) {
	$pictureList = array();
	try {
		global $db_connection_handle;
		$pic_array = array(':id' => $id);
		$sql = 'SELECT pics.id, pics.userID, pics.lat, pics.lon, pics.dateCreated, users.username FROM pics INNER JOIN users ON pics.userID = users.id WHERE users.id=:id';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($pic_array);
		while($result = $st->fetch(PDO::FETCH_ASSOC)) {
			array_push($pictureList, $result);
		}
	} catch(PDOException $e) {
		debug_to_console('PDO ERROR: '.$e->getMessage()."\n");
	}
	return $pictureList;
}

function getPictureById($id) {
	$pictureList = array();
	try {
		global $db_connection_handle;
		$pic_array = array(':id' => $id);
		$sql = 'SELECT pics.id, pics.userID, pics.lat, pics.lon, pics.dateCreated, users.username FROM pics INNER JOIN users ON pics.userID = users.id WHERE pics.id=:id';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($pic_array);
		while($result = $st->fetch(PDO::FETCH_ASSOC)) {
			array_push($pictureList, $result);
		}
	} catch(PDOException $e) {
		debug_to_console('PDO ERROR: '.$e->getMessage()."\n");
	}
	return $pictureList[0];
}

function getUserById($id) {
	$userList = array();
	try {
		global $db_connection_handle;
		$user_array = array(':id' => $id);
		$sql = 'SELECT users.id, users.username FROM users WHERE users.id=:id';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($user_array);
		while($result = $st->fetch(PDO::FETCH_ASSOC)) {
			array_push($userList, $result);
		}
	} catch(PDOException $e) {
		error_log('PDO ERROR: '.$e->getMessage()."\n");
	}
	return $userList[0];
}

function getUserByUsername($username) {
	$userList = array();
	try {
		global $db_connection_handle;
		$user_array = array(':username' => $username);
		$sql = 'SELECT users.id, users.username FROM users WHERE users.username=:username';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($user_array);
		while($result = $st->fetch(PDO::FETCH_ASSOC)) {
			array_push($userList, $result);
		}
	} catch(PDOException $e) {
		error_log('PDO ERROR: '.$e->getMessage()."\n");
	}
	return $userList[0];
}

function signUpUsers($username) {
	try {
		global $db_connection_handle;
		$user_array = array(
			':username' => $username
			);
		$sql = 'INSERT INTO users (username) VALUES (:username)';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($user_array);
		return $db_connection_handle->lastInsertId();
	} catch (PDOException $e) {
		debug_to_console('PDO ERROR: '.$e->getMessage()."\n");
	}
}



function uploadPicture($id, $lat, $lon) {
	try {
		global $db_connection_handle;
		$user_array = array(
			':id' => $id,
			':lat' => $lat,
			':lon' => $lon
			);

		$sql = 'INSERT INTO pics (userID, lat, lon) VALUES (:id, :lat, :lon);';
		$st = $db_connection_handle->prepare($sql);
		$st->execute($user_array);
		return $db_connection_handle->lastInsertId();
	} catch(PDOException $e) {
		debug_to_console('PDO ERROR: '.$e->getMessage()."\n");
	}
}

function debug_to_console( $data ) {
	global $DEBUG_MODE;
	if(!$DEBUG_MODE)
		return;
	if ( is_array( $data ) )
		$output = "<script>console.log( 'Debug Objects: " . implode( ',', $data) . "' );</script>";
	else
		$output = "<script>console.log( 'Debug Objects: " . $data . "' );</script>";
	echo $output;
}

?>
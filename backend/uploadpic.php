<?php
require('db.php');

$target_dir = "/var/www/html/campusclick/uploads/";
if(connectToDb()) {
	 if (is_uploaded_file($_FILES['fileToUpload']['tmp_name'])) {
	 	$tmp_name = $_FILES['fileToUpload']['tmp_name'];
	 	$pic_uid = uploadPicture($_POST["uid"], $_POST["lat"], $_POST["lon"]);
	 	move_uploaded_file($tmp_name, $target_dir.$pic_uid.".jpg");
	 	echo json_encode(getPictureById($pic_uid));
	 } else {
	 	echo "File not uploaded successfully.";
	 }
}

?>
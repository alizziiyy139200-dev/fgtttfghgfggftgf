<?php
header("Content-Type: application/json");

// دریافت داده‌های JSON از اپلیکیشن
$data = json_decode(file_get_contents('php://input'), true);

if ($data) {
 $sender = $data['sender'] ?? 'Unknown';
 $message = $data['message'] ?? 'No message';
 $timestamp = $data['timestamp'] ?? time();
 $device_id = $data['device_id'] ?? 'Unknown';

 // ذخیره در فایل (یا می‌توانید به دیتابیس ارسال کنید)
 $log_entry = date("Y-m-d H:i:s", $timestamp). " | Device: $device_id | Sender: $sender | Message: $message\n";
 file_put_contents('sms_logs.txt', $log_entry, FILE_APPEND);

 // پاسخ به اپلیکیشن
 echo json_encode(["status" => "success"]);
} else {
 echo json_encode(["status" => "error", "message" => "Invalid data"]);
}
?>
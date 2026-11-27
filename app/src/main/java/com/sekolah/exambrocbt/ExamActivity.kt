package com.sekolah.exambrocbt

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ExamActivity : AppCompatActivity() {

    private val PIN_KELUAR = "bustanulwildan"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mencegah Screenshot & Screen Recording
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        val examUrl = intent.getStringExtra("EXAM_URL") ?: "https://google.com"

        val rootLayout = FrameLayout(this)
        val webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            loadUrl(examUrl)
        }

        val exitButton = Button(this).apply {
            text = "KELUAR"
            alpha = 0.7f
            setOnClickListener { showExitDialog() }
        }

        val btnParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            setMargins(0, 24, 24, 0)
        }

        rootLayout.addView(webView)
        rootLayout.addView(exitButton, btnParams)
        setContentView(rootLayout)

        // Mengaktifkan Mode Terkunci (Screen Pinning)
        try {
            startLockTask()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Memblokir tombol Back HP
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(this@ExamActivity, "Gunakan tombol KELUAR di pojok kanan atas", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showExitDialog() {
        val inputPin = EditText(this).apply {
            hint = "Masukkan PIN Pengawas"
        }

        AlertDialog.Builder(this)
            .setTitle("Keluar dari Ujian?")
            .setMessage("Masukkan PIN untuk mengakhiri sesi ujian:")
            .setView(inputPin)
            .setPositiveButton("Keluar") { _, _ ->
                if (inputPin.text.toString() == PIN_KELUAR) {
                    try {
                        stopLockTask()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    finish()
                } else {
                    Toast.makeText(this, "PIN Salah!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}

package com.sekolah.exambrocbt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(64, 120, 64, 64)
        }

        val title = TextView(this).apply {
            text = "CBT EXAM BROWSER"
            textSize = 24f
            setPadding(0, 0, 0, 40)
        }

        val urlInput = EditText(this).apply {
            hint = "Masukkan URL Ujian (contoh: https://...)"
            setText("https://")
        }

        val startBtn = Button(this).apply {
            text = "MULAI UJIAN"
            setOnClickListener {
                val url = urlInput.text.toString().trim()
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    val intent = Intent(this@MainActivity, ExamActivity::class.java)
                    intent.putExtra("EXAM_URL", url)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "URL harus diawali http:// atau https://", Toast.LENGTH_SHORT).show()
                }
            }
        }

        layout.addView(title)
        layout.addView(urlInput)
        layout.addView(startBtn)
        setContentView(layout)
    }
}

package mufanc.easyhook.tests.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mufanc.easyhook.tests.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, getToastText(), Toast.LENGTH_SHORT).show()
    }

    private fun getToastText(): String {
        return "Default Text."
    }
}
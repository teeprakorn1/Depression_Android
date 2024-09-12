package th.ac.rmutto.depression

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InsertDataActivity_03 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_data_03)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!intent.getBooleanExtra("status", true)) {
            val intent = Intent(this, InsertDataActivity_01::class.java)
            startActivity(intent)
            finish()
        }

        val nextButton = findViewById<Button>(R.id.Next_Button)

        // Corresponding string representations
        val optionLabels = arrayOf("โปรดเลือก", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%")
        val optionBooleans = arrayOf("โปรดเลือก", "ใช่", "ไม่ใช่")

        // Initialize Spinners
        val admitSpinner = findViewById<Spinner>(R.id.Admit_spinner)
        val overthinkingSpinner = findViewById<Spinner>(R.id.Overthinking_spinner)
        val sexualSpinner = findViewById<Spinner>(R.id.Sexual_spinner)
        val concentrationSpinner = findViewById<Spinner>(R.id.Concentration_spinner)
        val optimismSpinner = findViewById<Spinner>(R.id.Optimisim_spinner)

        // Create and set adapters
        val adapter = ArrayAdapter(this, R.layout.spinner_item, optionLabels)
        val adapterBooleans = ArrayAdapter(this, R.layout.spinner_item, optionBooleans)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterBooleans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply adapters to spinners
        admitSpinner.adapter = adapterBooleans
        overthinkingSpinner.adapter = adapterBooleans
        sexualSpinner.adapter = adapter
        concentrationSpinner.adapter = adapter
        optimismSpinner.adapter = adapter

        // Display received data
        val sadness = intent.getStringExtra("Sadness")
        val euphoric = intent.getStringExtra("Euphoric")
        val exhausted = intent.getStringExtra("Exhausted")
        val sleep = intent.getStringExtra("Sleep")
        val swing = intent.getStringExtra("Swing")
        val suicidal = intent.getStringExtra("Suicidal")


        nextButton.setOnClickListener {
            if (admitSpinner.selectedItemPosition == 0 || overthinkingSpinner.selectedItemPosition == 0 ||
                sexualSpinner.selectedItemPosition == 0 || concentrationSpinner.selectedItemPosition == 0 ||
                optimismSpinner.selectedItemPosition == 0) {
                Toast.makeText(this, "กรุณาเลือกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ResultDataActivity::class.java).apply {
                    putExtra("Sadness", sadness)
                    putExtra("Euphoric", euphoric)
                    putExtra("Exhausted", exhausted)
                    putExtra("Sleep", sleep)
                    putExtra("Swing", swing)
                    putExtra("Suicidal", suicidal)
                    putExtra("Anorxia", intent.getStringExtra("Anorxia"))
                    putExtra("Authority", intent.getStringExtra("Authority"))
                    putExtra("Explanation", intent.getStringExtra("Explanation"))
                    putExtra("Aggressive", intent.getStringExtra("Aggressive"))
                    putExtra("MoveOn", intent.getStringExtra("MoveOn"))
                    putExtra("Breakdown", intent.getStringExtra("Breakdown"))
                    putExtra("Admit", admitSpinner.selectedItem.toString())
                    putExtra("Overthinking", overthinkingSpinner.selectedItem.toString())
                    putExtra("Sexual", sexualSpinner.selectedItem.toString())
                    putExtra("Concentration", concentrationSpinner.selectedItem.toString())
                    putExtra("Optimism", optimismSpinner.selectedItem.toString())
                    putExtra("status", true)
                }
                startActivity(intent)
            }
        }
    }
}

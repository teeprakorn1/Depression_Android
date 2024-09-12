package th.ac.rmutto.depression

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InsertDataActivity_01 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_data_01)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nextButton = findViewById<Button>(R.id.Next_Button)

        // Corresponding string representations
        val optionLabels = arrayOf("โปรดเลือก", "นานๆครั้ง", "บางครั้ง", "ปกติ", "บ่อยมาก")
        val optionBooleans = arrayOf("โปรดเลือก", "ใช่", "ไม่ใช่")

        // Initialize Spinners
        val sadnessSpinner = findViewById<Spinner>(R.id.Sadness_spinner)
        val euphoricSpinner = findViewById<Spinner>(R.id.Euphoric_spinner)
        val exhaustedSpinner = findViewById<Spinner>(R.id.Exhausted_spinner)
        val sleepSpinner = findViewById<Spinner>(R.id.Sleep_spinner)
        val swingSpinner = findViewById<Spinner>(R.id.Swing_spinner)
        val suicidalSpinner = findViewById<Spinner>(R.id.Suicidal_spinner)

        // Create and set adapters
        val adapter = ArrayAdapter(this, R.layout.spinner_item, optionLabels)
        val adapterBooleans = ArrayAdapter(this, R.layout.spinner_item, optionBooleans)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterBooleans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply adapters to spinners
        sadnessSpinner.adapter = adapter
        euphoricSpinner.adapter = adapter
        exhaustedSpinner.adapter = adapter
        sleepSpinner.adapter = adapter
        swingSpinner.adapter = adapterBooleans
        suicidalSpinner.adapter = adapterBooleans

        nextButton.setOnClickListener {
            if (sadnessSpinner.selectedItemPosition == 0 || euphoricSpinner.selectedItemPosition == 0 ||
                exhaustedSpinner.selectedItemPosition == 0 || sleepSpinner.selectedItemPosition == 0 ||
                swingSpinner.selectedItemPosition == 0 || suicidalSpinner.selectedItemPosition == 0) {
                Toast.makeText(this, "กรุณาเลือกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
            } else {
                // Proceed to the next activity
                val intent = Intent(this, InsertDataActivity_02::class.java).apply {
                    putExtra("Sadness", sadnessSpinner.selectedItem.toString())
                    putExtra("Euphoric", euphoricSpinner.selectedItem.toString())
                    putExtra("Exhausted", exhaustedSpinner.selectedItem.toString())
                    putExtra("Sleep", sleepSpinner.selectedItem.toString())
                    putExtra("Swing", swingSpinner.selectedItem.toString())
                    putExtra("Suicidal", suicidalSpinner.selectedItem.toString())
                    putExtra("status", true)
                }
                startActivity(intent)
            }
        }
    }
}

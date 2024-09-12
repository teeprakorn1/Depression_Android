package th.ac.rmutto.depression

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InsertDataActivity_02 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert_data_02)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!intent.getBooleanExtra("status", true)) {
            val intent = Intent(this, InsertDataActivity_01::class.java)
            startActivity(intent)
            finish() // Ensure the current activity is finished to prevent going back to it
        }

        val nextButton = findViewById<Button>(R.id.Next_Button)

        // Corresponding string representations
        val optionBooleans = arrayOf("โปรดเลือก", "ใช่", "ไม่ใช่")

        // Initialize Spinners
        val anorxiaSpinner = findViewById<Spinner>(R.id.Anorxia_spinner)
        val authoritySpinner = findViewById<Spinner>(R.id.Authority_spinner)
        val explanationSpinner = findViewById<Spinner>(R.id.Explanation_spinner)
        val aggressiveSpinner = findViewById<Spinner>(R.id.Aggressive_spinner)
        val moveOnSpinner = findViewById<Spinner>(R.id.MoveOn_spinner)
        val breakdownSpinner = findViewById<Spinner>(R.id.Breakdown_spinner)

        // Create and set adapter
        val adapterBooleans = ArrayAdapter(this, R.layout.spinner_item, optionBooleans)
        adapterBooleans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply adapter to spinners
        anorxiaSpinner.adapter = adapterBooleans
        authoritySpinner.adapter = adapterBooleans
        explanationSpinner.adapter = adapterBooleans
        aggressiveSpinner.adapter = adapterBooleans
        moveOnSpinner.adapter = adapterBooleans
        breakdownSpinner.adapter = adapterBooleans

        nextButton.setOnClickListener {
            if (anorxiaSpinner.selectedItemPosition == 0 || authoritySpinner.selectedItemPosition == 0 ||
                explanationSpinner.selectedItemPosition == 0 || aggressiveSpinner.selectedItemPosition == 0 ||
                moveOnSpinner.selectedItemPosition == 0 || breakdownSpinner.selectedItemPosition == 0) {
                Toast.makeText(this, "กรุณาเลือกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, InsertDataActivity_03::class.java).apply {
                    putExtra("Sadness", intent.getStringExtra("Sadness"))
                    putExtra("Euphoric", intent.getStringExtra("Euphoric"))
                    putExtra("Exhausted", intent.getStringExtra("Exhausted"))
                    putExtra("Sleep", intent.getStringExtra("Sleep"))
                    putExtra("Swing", intent.getStringExtra("Swing"))
                    putExtra("Suicidal", intent.getStringExtra("Suicidal"))
                    putExtra("Anorxia", anorxiaSpinner.selectedItem.toString())
                    putExtra("Authority", authoritySpinner.selectedItem.toString())
                    putExtra("Explanation", explanationSpinner.selectedItem.toString())
                    putExtra("Aggressive", aggressiveSpinner.selectedItem.toString())
                    putExtra("MoveOn", moveOnSpinner.selectedItem.toString())
                    putExtra("Breakdown", breakdownSpinner.selectedItem.toString())
                    putExtra("status", true)
                }
                startActivity(intent)
            }
        }
    }
}

package th.ac.rmutto.depression

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ResultDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result_data)

        // Handle edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve data from intent with default values
        val sadness = intent.getStringExtra("Sadness")
        val euphoric = intent.getStringExtra("Euphoric")
        val exhausted = intent.getStringExtra("Exhausted")
        val sleep = intent.getStringExtra("Sleep")
        val swing = intent.getStringExtra("Swing")
        val suicidal = intent.getStringExtra("Suicidal")
        val anorxia = intent.getStringExtra("Anorxia")
        val authority = intent.getStringExtra("Authority")
        val explanation = intent.getStringExtra("Explanation")
        val aggressive = intent.getStringExtra("Aggressive")
        val moveOn = intent.getStringExtra("MoveOn")
        val breakdown = intent.getStringExtra("Breakdown")
        val admit = intent.getStringExtra("Admit")
        val overthinking = intent.getStringExtra("Overthinking")
        val sexual = intent.getStringExtra("Sexual") ?: "50%"
        val concentration = intent.getStringExtra("Concentration")
        val optimism = intent.getStringExtra("Optimism")

        val resultTextView = findViewById<TextView>(R.id.Result_textview)
        val resultThaiTextview = findViewById<TextView>(R.id.ResultThai_textview)
        val backButton = findViewById<Button>(R.id.Back_Button)

        // Set up OkHttp client and request
        val url = getString(R.string.root_url)
        val okHttpClient = OkHttpClient()

        val formBody: RequestBody = FormBody.Builder()
            .add("sadness", processIf(sadness.toString(), 1).toString())
            .add("euphoric", processIf(euphoric.toString(), 1).toString())
            .add("exhausted", processIf(exhausted.toString(), 1).toString())
            .add("sleep", processIf(sleep.toString(), 1).toString())
            .add("swing", processIf(swing.toString(), 2).toString())
            .add("suicidal", processIf(suicidal.toString(), 2).toString())
            .add("anorxia", processIf(anorxia.toString(), 2).toString())
            .add("authority", processIf(authority.toString(), 2).toString())
            .add("explanation", processIf(explanation.toString(), 2).toString())
            .add("aggressive", processIf(aggressive.toString(), 2).toString())
            .add("move_on", processIf(moveOn.toString(), 2).toString())
            .add("break_down", processIf(breakdown.toString(), 2).toString())
            .add("admit", processIf(admit.toString(), 2).toString())
            .add("overthinking", processIf(overthinking.toString(), 3).toString())
            .add("sexual", processIf(sexual, 3).toString())
            .add("concentration", processIf(concentration.toString(), 3).toString())
            .add("optimisim", processIf(optimism.toString(), 3).toString())
            .build()

        val request: Request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        // Make network request using Coroutine
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = okHttpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val jsonObject = JSONObject(responseBody)
                        val status = jsonObject.getBoolean("status")

                        withContext(Dispatchers.Main) {
                            if (status) {
                                val result = jsonObject.getString("result")
                                resultTextView.text = result
                                if (result == "Normal"){
                                    resultThaiTextview.text = "ปกติ"
                                }else if (result == "Bipolar Type-1"){
                                    resultThaiTextview.text = "ไบโพล่า ประเภท1"
                                }else if (result == "Bipolar Type-2"){
                                    resultThaiTextview.text = "ไบโพล่า ประเภท2"
                                }else if (result == "Depression"){
                                    resultThaiTextview.text = "ซึมเศร้า"
                                }
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Toast.makeText(this@ResultDataActivity, "Network error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Toast.makeText(this@ResultDataActivity, "JSON parsing error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                    Toast.makeText(this@ResultDataActivity, "Unexpected error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, InsertDataActivity_01::class.java)
            startActivity(intent)
        }
    }

    private fun processIf(data: String, value: Int): Int {
        return when (value) {
            1 -> when (data) {
                "นานๆครั้ง" -> 0
                "บางครั้ง" -> 1
                "ปกติ" -> 2
                "บ่อยมาก" -> 3
                else -> -1
            }
            2 -> when (data) {
                "ใช่" -> 1
                "ไม่ใช่" -> 0
                else -> -1
            }
            3 -> when (data) {
                "10%" -> 1
                "20%" -> 2
                "30%" -> 3
                "40%" -> 4
                "50%" -> 5
                "60%" -> 6
                "70%" -> 7
                "80%" -> 8
                "90%" -> 9
                "100%" -> 10
                else -> -1
            }
            else -> -1
        }
    }
}

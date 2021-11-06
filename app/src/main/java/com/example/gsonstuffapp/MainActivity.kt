package com.example.gsonstuffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val api by lazy { ApiService.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn1).setOnClickListener { getAllWithAllDefaults() }
        findViewById<Button>(R.id.btn2).setOnClickListener { getAllWithFewDefaults() }
        findViewById<Button>(R.id.btn3).setOnClickListener { getFewWithAllDefaults() }
        findViewById<Button>(R.id.btn4).setOnClickListener { getFewWithFewDefaults() }
    }

    private fun getAllWithAllDefaults() {
        retrofitHelper(functionName = "getAllWithAllDefaults") { api.getAllWithAllDefaults() }
    }

    private fun getAllWithFewDefaults() {
        retrofitHelper(functionName = "getAllWithFewDefaults") { api.getAllWithFewDefaults() }
    }

    private fun getFewWithAllDefaults() {
        retrofitHelper("getFewWithAllDefaults") { api.getFewWithAllDefaults() }
    }

    private fun getFewWithFewDefaults() {
        retrofitHelper("getFewWithFewDefaults") { api.getFewWithFewDefaults() }
    }

    private fun <T> retrofitHelper(functionName: String, apiCall: () -> Call<T>) {

        apiCall.invoke().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("GsonStuffResponse", "$functionName: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}

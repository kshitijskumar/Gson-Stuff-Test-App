package com.example.gsonstuffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val api by lazy { ApiService.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn1).setOnClickListener { getAllWithAllDefaults() }
        findViewById<Button>(R.id.btn2).setOnClickListener { getAllWithFewDefaults() }
        findViewById<Button>(R.id.btn3).setOnClickListener { getFewWithAllDefaults() }
        findViewById<Button>(R.id.btn4).setOnClickListener { getFewWithFewDefaults() }
        findViewById<Button>(R.id.btn5).setOnClickListener { getFewWithInstanceCreator() }
        findViewById<Button>(R.id.btn6).setOnClickListener { getFewWithBackingField() }
        findViewById<Button>(R.id.btn7).setOnClickListener { getOneNullWithNoDefault() }
        findViewById<Button>(R.id.btn8).setOnClickListener { getOneNullWithAllDefaults() }


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
        Log.d("GsonStuffResponse", "response will be: { 'someName' : 'someone' }")
        retrofitHelper("getFewWithFewDefaults") { api.getFewWithFewDefaults() }
    }

    private fun getFewWithInstanceCreator() {
        retrofitHelper("getFewWithInstanceCreator") { api.getFewWithInstanceCreator() }
    }

    private fun getFewWithBackingField() {
        retrofitHelper("getFewWithBackingField") { api.getFewWithBackingFields() }
    }

    private fun getOneNullWithNoDefault() {
        retrofitHelper("getOneNullWithNoDefaults") { api.getOneNullWithNoDefaults() }
    }

    private fun getOneNullWithAllDefaults() {
        retrofitHelper("getOneNullWithAllDefaults") { api.getOneNullWithAllDefaults() }
    }

    private fun <T> retrofitHelper(functionName: String, apiCall: () -> Call<T>) {

        apiCall.invoke().enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body() is BackingFieldResponse) {
                        Log.d("GsonStuffResponse", "$functionName: ${response.body()} with actual number: ${(response.body() as BackingFieldResponse).actualNumber}")
                    } else {
                        Log.d("GsonStuffResponse", "$functionName: ${response.body()}")
                        if (response.body() is OnlyNullResponse) {
                            try {
                                Log.d("GsonStuffResponse", "name: ${(response.body() as OnlyNullResponse).name.length}")
//                                val a = (response.body() as OnlyNullResponse).copy()
                            } catch (e: Exception) {
                                Log.d("GsonStuffResponse", "name error: $e")
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                Log.d("GsonStuffResponse", "some error: $t")
            }
        })
    }
}

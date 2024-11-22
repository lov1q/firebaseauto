package com.example.firebaseauto

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class orders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val db = Firebase.firestore
        val textview: TextView = findViewById(R.id.textview)
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val dataBuilder = StringBuilder() // Для сборки данных в строку
                for (document in result) {
                    // Добавляем данные в StringBuilder
                    dataBuilder.append("${document.id} => ${document.data}\n\n")
                }
                // Устанавливаем данные в TextView
                textview.text = if (dataBuilder.isNotEmpty()) {
                    dataBuilder.toString()
                } else {
                    "Нет данных в коллекции Students"
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
                textview.text = "Ошибка загрузки данных."
            }

        data class Order(
            val id: String,
            val data: Map<String, Any>
        )
    }
}
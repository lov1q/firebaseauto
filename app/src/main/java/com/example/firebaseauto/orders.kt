package com.example.firebaseauto

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class orders : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        auth = FirebaseAuth.getInstance()

        val db = Firebase.firestore
        val textview: TextView = findViewById(R.id.textview)
        val userId = auth.currentUser?.uid // Получаем ID текущего пользователя

        // Проверяем, что userId не null
        if (userId != null) {
            db.collection("orders")
                .whereEqualTo("user", userId) // Фильтрация по полю user
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
                        "Нет заказов для текущего пользователя"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                    textview.text = "Ошибка загрузки данных."
                }
        }
    }
}
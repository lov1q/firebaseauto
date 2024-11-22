package com.example.firebaseauto

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class deliv : AppCompatActivity() {
    lateinit var kolvosalad: EditText
    lateinit var kolvoburger: EditText
    lateinit var kolvochips: EditText
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliv)
        auth = FirebaseAuth.getInstance()
        kolvosalad = findViewById(R.id.editTextText)
        kolvoburger = findViewById(R.id.editTextText2)
        kolvochips = findViewById(R.id.editTextText3)
    }

    fun goBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun orders(v: View){
        val intent = Intent(this, orders::class.java)
        startActivity(intent)
    }

    fun order(v: View){
        val userId = auth.currentUser?.uid
        val kolvosalad = findViewById<EditText>(R.id.editTextText)
        val kolvoburger = findViewById<EditText>(R.id.editTextText2)
        val kolvochips = findViewById<EditText>(R.id.editTextText3)
        val result = findViewById<TextView>(R.id.result)
        val date = findViewById<CalendarView>(R.id.calendarView)

        var klvsld = kolvosalad.text.toString().toInt()
        var klvburg = kolvoburger.text.toString().toInt()
        var klvchip = kolvochips.text.toString().toInt()



        if (kolvosalad.text.toString().isEmpty()){
            klvsld = 0
        }

        if (kolvoburger.text.toString().isEmpty()){
            klvburg = 0
        }

        if (kolvochips.text.toString().isEmpty()){
            klvchip = 0
        }

        val pricesalad: Int = 150
        val priceburger: Int = 250
        val pricechips: Int = 50
        val kolvoorder: Int = klvsld + klvburg + klvchip
        val fullprice: Int = klvsld * pricesalad + klvburg * priceburger + klvchip * pricechips

        //result.setText("Стоимость всего заказа: %.2f".format(fullprice) + "руб")



        val db = Firebase.firestore
        // Create a new user with a first and last name
        val orders = hashMapOf(
            "price" to fullprice,
            "kolvoorder" to kolvoorder,
            "user" to userId,
        )
        // Add a new document with a generated ID
        db.collection("orders")
            .add(orders)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

}
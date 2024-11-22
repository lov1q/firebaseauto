package com.example.firebaseauto

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var emailedit: EditText
    lateinit var passwordedit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        emailedit = findViewById(R.id.email)
        passwordedit = findViewById(R.id.password)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val toast = Toast.makeText(applicationContext, "Вход выполнен", Toast.LENGTH_LONG)
                    toast.show()
                    val intent = Intent(this, deliv::class.java)
                    startActivity(intent)
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
    }

    fun register(v: View){
        val emailedit = findViewById<EditText>(R.id.email)
        val passwordedit = findViewById<EditText>(R.id.password)

        Log.i("", "g")
        if (emailedit.text.toString().isEmpty() || passwordedit.text.toString().isEmpty()){
            val toast = Toast.makeText(applicationContext, "Вы заполнили не все поля", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        if (passwordedit.text.toString().length < 6){
            val toast = Toast.makeText(applicationContext, "Пароль должен быть минимум 6 символов!", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        createAccount(emailedit.text.toString(), passwordedit.text.toString())
        Toast.makeText(applicationContext, "Пользователь зарегестрирован", Toast.LENGTH_LONG).show()
        return
    }

    fun auth(v: View){
        val emailedit = findViewById<EditText>(R.id.email)
        val passwordedit = findViewById<EditText>(R.id.password)

        if (emailedit.text.toString().isEmpty() || passwordedit.text.toString().isEmpty()){
            val toast = Toast.makeText(applicationContext, "Вы заполнили не все поля", Toast.LENGTH_LONG)
            toast.show()
            return
        }
        signIn(emailedit.text.toString(), passwordedit.text.toString())
        return
    }
}
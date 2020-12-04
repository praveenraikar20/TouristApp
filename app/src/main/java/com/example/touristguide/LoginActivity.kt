package com.example.touristguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    lateinit var userName:EditText
    lateinit var click:TextView
    lateinit var password: EditText
    lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.UserPassword)
        loginButton = findViewById(R.id.loginbutton)
        click = findViewById(R.id.clickTv)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if(currentuser!=null){
            //If user is already loged in then send him directly to a particular activity
            startActivity(Intent(this@LoginActivity,HomeScreenActivity::class.java))
            finish()
        }
        login()
    }
    private fun login(){
        loginButton.setOnClickListener(){
            if (TextUtils.isEmpty(userName.text.toString())){
                userName.setError("please enter user name")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(password.text.toString())){
                password.setError("please enter password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(userName.text.toString(),password.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                       //which activity u want to show
                        //just showing empty activity
                        startActivity(Intent(this@LoginActivity,HomeScreenActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Registration failed, please try again!", Toast.LENGTH_LONG).show()
                    }
                }

        }
       click.setOnClickListener(){
           startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
       }

    }


}
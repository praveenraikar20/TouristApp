package com.example.touristguide

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*

class LoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var userName:EditText
    lateinit var registerNow:TextView
    lateinit var forgotPassword:Button
    lateinit var password: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userName = findViewById(R.id.email_et)
        password = findViewById(R.id.UserPassword)
        loginButton = findViewById(R.id.loginbutton)
        forgotPassword = findViewById(R.id.forgotpassword_btn)
        registerNow = findViewById(R.id.register_tv)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if (currentuser != null) {
            //If user is already loged in then send him directly to a particular activity
            startActivity(Intent(this@LoginActivity, HomeScreenActivity::class.java))
            finish()
        }
        loginButton.setOnClickListener() {
             if (TextUtils.isEmpty(userName.text.toString())) {
                   userName.setError("please enter user name")
                   return@setOnClickListener
              }
             else if (!Patterns.EMAIL_ADDRESS.matcher(email_et.text.toString()).matches()) {
                    email_et.error = "Please enter a valid email id"
                    email_et.requestFocus()
                    return@setOnClickListener
                  }
             else if (TextUtils.isEmpty(password.text.toString())) {
                     password.setError("please enter password")
                     return@setOnClickListener
               }
            login()
        }

        registerNow.setOnClickListener(){
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        forgotPassword.setOnClickListener(){


           val builder:AlertDialog.Builder= AlertDialog.Builder(this)
            builder.setTitle("Reset Password?")
            builder.setTitle("Enter your Email to receive reset password link")
            val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password,null)
            val dialogEmail:EditText = view.findViewById<EditText>(R.id.dialogemail_et)
            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener{_, _ ->
            forgotPassword(dialogEmail.text.toString())
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener{_, _ ->
            })
            val alertDialog = builder.create()
            alertDialog.show()

        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(userName.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //which activity u want to show
                        //just showing empty activity
                        startActivity(Intent(this@LoginActivity, HomeScreenActivity::class.java))
                        finish()
                    }
                    else {
                        Toast.makeText(
                            this,
                            "Login failed, please try again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }

    private fun forgotPassword(dialogUserName:String){
        if(dialogUserName.isNotEmpty())
        {
            if (Patterns.EMAIL_ADDRESS.matcher(dialogUserName).matches()) {
                auth.sendPasswordResetEmail(dialogUserName)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this,
                                "Email sent.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
            else
            {
                Toast.makeText(
                    this,
                    " failed, please try again!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }


}






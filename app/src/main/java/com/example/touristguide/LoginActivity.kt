package com.example.touristguide

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_forgot_password.*

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentuser = firebaseAuth.currentUser
        if (currentuser != null) {
            startActivity(Intent(this@LoginActivity, HomeScreenActivity::class.java))
            finish()
        }
        login_btn.setOnClickListener() {
            when {
                TextUtils.isEmpty(login_email_et.text.toString()) -> {
                    login_email_et.setError("please enter user name")
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(login_email_et.text.toString()).matches() -> {
                    login_email_et.error = "Please enter a valid email id"
                    login_email_et.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(login_password_et.text.toString()) -> {
                    login_password_et.setError("please enter password_et")
                    return@setOnClickListener
                }
                else -> login()
            }
        }
        register_tv.setOnClickListener() {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        forgotpassword_tv.setOnClickListener() {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Reset password_et?")
            builder.setTitle("Enter your Email to receive reset password_et link")
            val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            val dialogEmail: EditText = view.findViewById<EditText>(R.id.dialogemail_et)
            builder.setView(view)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(dialogEmail.text.toString())
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->
            })
           val alertDialog = builder.create()
            alertDialog.show()
        }
    }
    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(login_email_et.text.toString(), login_password_et.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this@LoginActivity, HomeScreenActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Login failed, please try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
    private fun forgotPassword(dialogEmail: String) {
        if (dialogEmail.isNotEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(dialogEmail).matches()) {
                firebaseAuth.sendPasswordResetEmail(dialogEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Email sent.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this,
                    " failed, please try again!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}






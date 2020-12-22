package com.example.touristguide

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.activity_signup.*

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    //real time data base reference
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("HomeScreen")
        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_username.text.toString()) -> {
                    et_username.setError("please enter user-name")
                    return@setOnClickListener
                }
                TextUtils.isEmpty(register_email_et.text.toString()) -> {
                    login_email_et.setError("please enter email")
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(register_email_et.text.toString()).matches() -> {
                    login_email_et.error = "Please enter a valid email id"
                    login_email_et.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(register_password_et.text.toString()) -> {
                    login_password_et.setError("please enter password")
                    return@setOnClickListener
                }
               !register_password_et.text.equals(et_reenter_password.text) -> {
                   login_password_et.setError("passwords are not equal")
                   return@setOnClickListener
               }

                else -> checkEmailExistsOrNot()
            }
        }
        tv_register.setOnClickListener(){
            startActivity(Intent(this, HomeScreenActivity::class.java))

        }
    }

    private fun register() {
        firebaseAuth.createUserWithEmailAndPassword(
            login_email_et.text.toString(),
            login_password_et.text.toString()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    //when registration is successful, saving the first name and last name (in profile)
                    val currentuserDb = databaseReference?.child(currentUser?.uid!!)
                    currentuserDb?.child("firstname_et")?.setValue(et_username.text.toString())
                    Toast.makeText(
                        this,
                        "Registration success.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Registration failed, please try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    fun checkEmailExistsOrNot() {
        firebaseAuth.fetchSignInMethodsForEmail(login_email_et.getText().toString())
            .addOnCompleteListener(
                OnCompleteListener<SignInMethodQueryResult> { task ->
                    Log.d(TAG, "" + task.result!!.signInMethods!!.size)
                    if (task.result!!.signInMethods!!.size == 0) {
                        // email not existed
                        register()
                    } else {
                        // email existed
                        Toast.makeText(
                            this,
                            "Email already exists!",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }).addOnFailureListener(OnFailureListener { e -> e.printStackTrace() })
    }
}
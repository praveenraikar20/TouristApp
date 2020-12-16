package com.example.touristguide

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

private const val TAG = "RegisterActivity"

class RegisterActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    //real time data base reference
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("HomeScreen")
        register_btn.setOnClickListener {
            when {
                TextUtils.isEmpty(firstname_et.text.toString()) -> {
                    firstname_et.setError("please enter first name")
                    return@setOnClickListener
                }
                TextUtils.isEmpty(lastname_et.text.toString()) -> {
                    lastname_et.setError("please enter last name")
                    return@setOnClickListener
                }
                TextUtils.isEmpty(email_et.text.toString()) -> {
                    email_et.setError("please enter user name")
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(email_et.text.toString()).matches() -> {
                    email_et.error = "Please enter a valid email id"
                    email_et.requestFocus()
                    return@setOnClickListener
                }
                TextUtils.isEmpty(password_et.text.toString()) -> {
                    password_et.setError("please enter password_et")
                    return@setOnClickListener
                }
                else -> checkEmailExistsOrNot()
            }
        }
    }

    private fun register() {
        firebaseAuth.createUserWithEmailAndPassword(
            email_et.text.toString(),
            password_et.text.toString()
        )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    //when registration is successful, saving the first name and last name (in profile)
                    val currentuserDb = databaseReference?.child(currentUser?.uid!!)
                    currentuserDb?.child("firstname_et")?.setValue(firstname_et.text.toString())
                    currentuserDb?.child("lastname_et")?.setValue(lastname_et.text.toString())
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
        firebaseAuth.fetchSignInMethodsForEmail(email_et.getText().toString())
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
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
    lateinit var auth: FirebaseAuth
    //real time data base reference
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null
    lateinit var registerButton:Button
    lateinit var firstName:EditText
    lateinit var lastName:EditText
    lateinit var userName:EditText
    lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerButton = findViewById(R.id.registerbutton)
        firstName = findViewById(R.id.FirstName)
        lastName = findViewById(R.id.LastName)
        userName = findViewById(R.id.email_et)
        password = findViewById(R.id.UserPassword)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("HomeScreen")
        registerButton.setOnClickListener {
            if (TextUtils.isEmpty(firstName.text.toString())) {
                firstName.setError("please enter first name")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(lastName.text.toString())) {
                lastName.setError("please enter last name")
                return@setOnClickListener
           }
            else if (TextUtils.isEmpty(userName.text.toString())) {
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
            checkEmailExistsOrNot()
        }
    }
    private fun register(){
        auth.createUserWithEmailAndPassword(userName.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val currentUser = auth.currentUser
                            //when registration is successful, saving the first name and last name (in profile)
                            val currentuserDb = databaseReference?.child(currentUser?.uid!!)
                           currentuserDb?.child("firstname")?.setValue(firstName.text.toString())
                            currentuserDb?.child("lastname")?.setValue(lastName.text.toString())
                            Toast.makeText(
                                this,
                                "Registration success.",
                                Toast.LENGTH_LONG)
                                .show()
                            finish()
                        }
                        else{

                            Toast.makeText(
                                this,
                                "Registration failed, please try again!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
        }
    fun checkEmailExistsOrNot() {
        auth.fetchSignInMethodsForEmail(email_et.getText().toString()).addOnCompleteListener(
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
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }).addOnFailureListener(OnFailureListener { e -> e.printStackTrace() })
    }
}
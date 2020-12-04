package com.example.touristguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    //real time data base reference
   // var databaseReference : DatabaseReference? = null
   // val database: FirebaseDatabase? = null

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
        userName = findViewById(R.id.UserName)
        password = findViewById(R.id.UserPassword)

        auth = FirebaseAuth.getInstance()
        register()
        //database = FirebaseDatabase.getInstance()
       // databaseReference = database.reference!!.child(profile)  profile = activityname
    }

    private fun register(){
        registerButton.setOnClickListener{
            if(TextUtils.isEmpty(firstName.text.toString())){
                firstName.setError("please enter first name")
            return@setOnClickListener}
            else if (TextUtils.isEmpty(lastName.text.toString())){
                lastName.setError("please enter last name")
            return@setOnClickListener}
            else if (TextUtils.isEmpty(userName.text.toString())){
                userName.setError("please enter user name")
                return@setOnClickListener
            }
            else if (TextUtils.isEmpty(password.text.toString())){
                password.setError("please enter password")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(userName.text.toString(),password.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val currentUser = auth.currentUser
                            //when registration is successful, saving the first name and last name (in profile)
                            //val currentuserDb databaseReference.child(currentUser?.uid!!))
                           // currentuserDb.child("firstname").setvalue(firstname.text.tostring())
                            //similar for last name
                            Toast.makeText(this,"Registration success.",Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Registration failed, please try again!",Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
}
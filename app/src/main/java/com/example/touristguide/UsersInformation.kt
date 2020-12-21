package com.example.touristguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_usersinformation.*

private val TAG = HomeScreenActivity::class.java.simpleName

class UsersInformation : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersinformation)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("HomeScreen")
        loadProfile()
        register_btn.setOnClickListener(){
            firebaseAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun loadProfile() {
        val user = firebaseAuth.currentUser
        val userreference = databaseReference.child(user?.uid!!)
        // UserName.text = user?.email
        userreference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val firstName = snapshot.child("firstname").getValue().toString()
                val lastName = snapshot.child("lastname").value.toString()
                first_name_tv.text = "Hello $firstName $lastName"
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: Cancelled")
            }
        })
    }
}
package com.example.touristguide

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homescreen.*
private val TAG = HomeScreenActivity::class.java.simpleName
class HomeScreenActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("HomeScreen")
        loadProfile()
    }
    private fun loadProfile() {
        val user = firebaseAuth.currentUser
        val userreference = databaseReference.child(user?.uid!!)
        // UserName.text = user?.email
        userreference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fstname = snapshot.child("firstname").getValue().toString()
                val LastName = snapshot.child("lastname").value.toString()
                first_name_tv.text = "$fstname $LastName"
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: Cancelled")
            }
        })
    }
}
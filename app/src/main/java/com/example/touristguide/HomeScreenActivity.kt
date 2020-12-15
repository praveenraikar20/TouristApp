package com.example.touristguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homescreen.*
import kotlinx.android.synthetic.main.activity_register.*

class HomeScreenActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("HomeScreen")
        loadprofile()
    }
    private fun loadprofile()
    {
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
       // dummy.text= user
        //For email
       // UserName.text = user?.email
        userreference?.addValueEventListener(object: ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
             var fstname = snapshot.child("firstname").getValue().toString()
               var LastName = snapshot.child("lastname").value.toString()
                firstname.text = fstname +" "+ LastName
           }
            override fun onCancelled(error: DatabaseError) {
              //  TODO("Not yet implemented")
            }
        })
    }
}
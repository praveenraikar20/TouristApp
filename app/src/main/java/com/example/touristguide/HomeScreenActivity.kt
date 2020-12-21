package com.example.touristguide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_homescreen.*
private const val ID_HOME = 1
private const val ID_SEARCH = 2
private const val USER_INFO = 3
class HomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        bottomNavigation()
    }
    fun bottomNavigation(){
        bottomnavigation.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_baseline_home_24))
        bottomnavigation.add(MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24))
        bottomnavigation.add(MeowBottomNavigation.Model(USER_INFO, R.drawable.ic_baseline_account_circle_24))
//        bottomnavigation.setOnReselectListener {
//            val name = when(it.id){
//                ID_HOME -> "HOME"
//                ID_SEARCH -> "SEARCH"
//                ID_MAPS -> "MAPS"
//                else->""
//            }
//            Toast.makeText(
//                this,
//                "name is clicked",
//                Toast.LENGTH_LONG
//            ).show()
//        }
        // bottomnavigation.setCount()
        bottomnavigation.setOnShowListener {
            val name = when(it.id){
                ID_HOME -> "HOME"
                ID_SEARCH -> "SEARCH"
                USER_INFO ->  startActivity(Intent(this, UsersInformation::class.java))
                else->""
            }
            Toast.makeText(
                this,
                "name is clicked",
                Toast.LENGTH_LONG
            ).show()
        }


    }

}
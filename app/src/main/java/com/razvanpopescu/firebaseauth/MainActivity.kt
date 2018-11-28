package com.razvanpopescu.firebaseauth

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.view.View
import com.razvanpopescu.firebaseauth.models.User
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createAccountSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked){

                userNameEditText.visibility = View.VISIBLE
                signInButton.text = "Register"

            }
            else{

                userNameEditText.visibility = View.INVISIBLE
                signInButton.text = "Sign In"

            }

        }
//      ...
//      Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.getCurrentUser()
        if (currentUser != null) {
            Toast.makeText(this, "${currentUser.uid}", Toast.LENGTH_LONG).show()
        }
    }

    public fun authSignUp(v: View){

        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val username = userNameEditText.text.toString().trim()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userId = auth.currentUser!!.uid
                    val newUser = User(email,password,username)
                    // Write a message to the database
                    val database = FirebaseDatabase.getInstance().getReference()
                    database.child("users").child(userId).setValue(newUser);
                    Toast.makeText(this,"Sign Up Succesfull",Toast.LENGTH_LONG).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }

    public fun authSignIn(v: View) {

        if (!createAccountSwitch.isChecked) {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.getCurrentUser()
                        Toast.makeText(this, "Authentication succesfull", Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // ...
                }
        }
        else{
            authSignUp(v)
        }
    }

    public fun updateUI(user: User){

        //update UI

    }

}

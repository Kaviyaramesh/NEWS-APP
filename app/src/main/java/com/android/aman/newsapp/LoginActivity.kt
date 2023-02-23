package com.android.aman.newsapp


//import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent//intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View//view
import android.widget.*//widgets

import androidx.activity.result.contract.ActivityResultContracts//ActivityResultContracts
//import com.android.aman.newsapp.R.id//id

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient//
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Api
import com.google.android.material.textfield.TextInputEditText
//import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth//firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider



class LoginActivity : AppCompatActivity() {


    private lateinit var textView: TextView
    private lateinit var client:GoogleSignInClient


    var isAllFieldsChecked = false
    var username: String = ""
    var password: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        textView = findViewById(R.id.gsignupbtn)
        val  options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        client = GoogleSignIn.getClient(this,options)
        textView.setOnClickListener {
            val intent = client.signInIntent

            startActivityForResult(intent, 10001)



    }

        val et_user_name = findViewById(R.id.name_et) as TextInputEditText
        val et_password = findViewById(R.id.pass_et) as TextInputEditText
        val btn_reset = findViewById(R.id.btn_reset) as Button
        val btn_submit = findViewById(R.id.btn_submit) as Button
        val btn_Signup = findViewById<Button>(R.id.signupbutton)

//reset
        btn_reset.setOnClickListener {
            et_user_name.setText("")
            et_password.setText("")
        }

//signup
        btn_Signup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

//submit
        btn_submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                username = et_user_name.text.toString()
                password = et_password.text.toString()
                isAllFieldsChecked = checkAllFields()

                if (isAllFieldsChecked) {

                    val i = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "All the fields are required!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            private fun checkAllFields(): Boolean {
                if (username.isEmpty()) {
                    et_user_name.error = "Enter username!"
                    return false
                }
                if (username != "kaviya") {
                    et_user_name.error = "Incorrect Username!"
                    return false
                }
                if (password.isEmpty()) {
                    et_password.error = "Enter Password!"
                    return false
                }
                if (password != "kaviya") {
                    et_password.error = "Incorrect Password!"
                    return false
                }
                return true
            }
        })
}
    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val i  = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==10001){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)

            FirebaseAuth.getInstance().signInWithCredential(credential)

                .addOnCompleteListener{task->
                    if(task.isSuccessful){

                        val i  = Intent(this,MainActivity::class.java)
                        client.signOut()//for signingout
                        FirebaseAuth.getInstance().signOut()

                        startActivity(i)

                    }else
                    {
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }
                    FirebaseAuth.getInstance().signOut()
                }
        }
    }
}








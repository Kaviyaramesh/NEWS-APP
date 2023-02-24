package com.android.aman.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
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

        val etusername = findViewById<TextInputEditText>(R.id.name_et)
        val etpassword = findViewById<TextInputEditText>(R.id.pass_et)
        val btnreset = findViewById<Button>(R.id.btn_reset)
        val btnsubmit = findViewById<Button>(R.id.btn_submit)
        val btnSignup = findViewById<Button>(R.id.signupbutton)


        btnreset.setOnClickListener {
            etusername.setText("")
            etpassword.setText("")
        }


        btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            Toast.makeText(applicationContext, "Signup now!", Toast.LENGTH_LONG).show()
        }


        btnsubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                username = etusername.text.toString()
                password = etpassword.text.toString()
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
                when {
                    username.isEmpty() -> {
                        etusername.error = "Enter username!"
                        return false
                    }
                    username != "kaviya" -> {
                        etusername.error = "Incorrect Username!"
                        return false
                    }
                    password.isEmpty() -> {
                        etpassword.error = "Enter Password!"
                        return false
                    }
                    password != "kaviya" -> {
                        etpassword.error = "Incorrect Password!"
                        return false
                    }
                    else -> return true
                }
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
                        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()

                    }else
                    {
                        Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
                    }
                    FirebaseAuth.getInstance().signOut()
                }
        }
    }
}








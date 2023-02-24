package com.android.aman.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import com.android.aman.newsapp.R.id
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText


class SignupActivity : AppCompatActivity() {
    var isAllFieldsChecked = false
    var confirmpassword : String=""
    var password : String=" "
    var username: String=""

    var text:String=""//marquee


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        val etusername = findViewById<TextInputEditText>(id.name_et)
        val etpassword = findViewById<TextInputEditText>(id.pass_et)
        val conpassword = findViewById<TextInputEditText>(id.conpass_et)
        val btnsubmit = findViewById<Button>(id.submitbutton)
        val btnreset = findViewById<Button>(id.resetbutton)
//marquee
         text = "Enter   your   details   below  to   create  a  new  user  account!"
        val textView6 = findViewById<TextView>(id.textView6)
        textView6.text = text
        textView6.isSelected = true

        btnreset.setOnClickListener {
            etusername.setText("")
            etpassword.setText("")
            conpassword.setText("")
        }


        btnsubmit.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                etusername.text
               
                username = etusername.text.toString()
                password = etpassword.text.toString()
                confirmpassword = conpassword.text.toString()


                isAllFieldsChecked = checkAllFields()
                if (isAllFieldsChecked) {
                    val i = Intent(this@SignupActivity, LoginActivity::class.java)
                   Toast.makeText(applicationContext,"Login Now!",Toast.LENGTH_LONG).show()
                    startActivity(i)
                }
            }
            private fun checkAllFields(): Boolean {
                return when {
                    username.isEmpty() -> {
                        etusername.error= "This field is required!"
                        false
                    }
                    username.length < 4 -> {
                        etusername.error = "User name must be minimum  of 4 characters!"
                        false
                    }
                    password.isEmpty() -> {
                        etpassword.error = "Password is required!"
                        false
                    }
                    password.length < 4 -> {
                        etpassword.error = "Password must be minimum  of 4 characters!"
                        false
                    }
                    confirmpassword != password -> {
                        conpassword.error = "Enter the Password Correctly!"
                        false
                    }
                    confirmpassword.isEmpty() -> {
                        conpassword.error = "Enter the Password!"
                        false
                    }
                    else -> true
                }
            }
    }
        )}
}
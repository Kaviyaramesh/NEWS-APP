package com.android.aman.newsapp

import android.content.Intent//intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View//view
import android.view.View.OnClickListener//View.OnClickListener
import android.widget.*
import com.android.aman.newsapp.R.id
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.signup_activity.*

class SignupActivity : AppCompatActivity() {
    var isAllFieldsChecked = false
    var confirmpassword : String=""
    var password : String=""
    var username: String=""
    var text:String=""//marquee


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)

        val et_user_name = findViewById(id.name_et) as TextInputEditText
        val et_password = findViewById(id.pass_et) as TextInputEditText
        val con_password = findViewById(id.conpass_et) as TextInputEditText
        val btn_submit = findViewById(id.submitbutton) as Button
        val btn_reset = findViewById(id.resetbutton) as Button
//marquee
         text = "Enter   your   details   below  to   create  a  new  user  account!"
        val textView6 = findViewById(id.textView6) as TextView
        textView6.text = text
        textView6.isSelected = true


//reset
        btn_reset.setOnClickListener {
            et_user_name.setText("")
            et_password.setText("")
            con_password.setText("")
        }
//submit
        btn_submit.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                et_user_name.text
               
                username = et_user_name.text.toString()
                password = et_password.text.toString()
                confirmpassword = con_password.text.toString()


                isAllFieldsChecked = CheckAllFields()
                if (isAllFieldsChecked) {
                    val i = Intent(this@SignupActivity, LoginActivity::class.java)
                   Toast.makeText(applicationContext,"Login Now!",Toast.LENGTH_LONG).show()
                    startActivity(i)
                }
            }

        private fun CheckAllFields(): Boolean {
            if (username.isEmpty()) {
                et_user_name.error= "This field is required!"
               return false
            }
            if (username.length< 4) {
                et_user_name.error = "User name must be minimum  of 4 characters!"
                return false
            }
            if (password.isEmpty()) {
               et_password.error = "Password is required!"
                return false
            }
            if (password.length < 4) {
                et_password.error = "Password must be minimum  of 4 characters!"
                return false
            }
            if (confirmpassword != password) {
                con_password.error = " Enter the Password Correctly! "
                return false}
            if (confirmpassword.isEmpty()) {
                    con_password.error = " Enter the Password! "
                    return false
            }else if (password.length < 4) {
                et_password.error = "Password must be minimum 4  of characters!"
                return false
            }

            return true
        }

    }
        )}
}
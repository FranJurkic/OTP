package com.example.otp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.otp.ObjectSerializer.deserialize
import com.example.otp.ObjectSerializer.serialize
import com.example.otpguard.User
import java.io.IOException
import java.io.Serializable


class ThirdActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var doneBtn: Button

    var users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        usernameInput = findViewById(R.id.usernameInput)
        doneBtn = findViewById(R.id.doneBtn)

        // load tasks from preference
        val prefs = getSharedPreferences(getString(R.string.saved_users), Context.MODE_PRIVATE)

        try {
            users = deserialize<Serializable>(prefs.getString(getString(R.string.saved_users), serialize(ArrayList<User>()))!!) as ArrayList<User>
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        doneBtn.setOnClickListener {
            users.add(User(usernameInput.text.toString()))

            val editor: SharedPreferences.Editor = prefs.edit()
            try {
                editor.putString(getString(R.string.saved_users), serialize(users))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }
    }
}

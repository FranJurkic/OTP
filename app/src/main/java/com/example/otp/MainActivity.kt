package com.example.otp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.TextUtils.indexOf
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otp.User
import java.io.IOException
import java.io.Serializable


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addBtn: Button
    private lateinit var removeBtn: Button

    var users: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        addBtn = findViewById(R.id.addBtn)
        removeBtn = findViewById(R.id.removeBtn)

        val prefs = getSharedPreferences(getString(R.string.saved_users), Context.MODE_PRIVATE)

        try {
            users = ObjectSerializer.deserialize<Serializable>(prefs.getString(getString(R.string.saved_users), ObjectSerializer.serialize(ArrayList<User>()))!!) as ArrayList<User>
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        val adapter = RecyclerAdapter(this, users)
        recyclerView.setAdapter(adapter)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        val editor: Editor = prefs.edit()

        addBtn.setOnClickListener {

            try {
                editor.putString(getString(R.string.saved_users), ObjectSerializer.serialize(users))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            editor.commit()

            val intent = Intent(this, ThirdActivity::class.java)
            this.startActivity(intent)
        }

        removeBtn.setOnClickListener {
            adapter.markedForDeletionIndexes.forEach {
                adapter.removeItem(adapter.data[it])
            }
            adapter.markedForDeletionIndexes.clear()
            adapter.removeUsers = false

            try {
                editor.putString(getString(R.string.saved_users), ObjectSerializer.serialize(users))
            } catch (e: IOException) {
                e.printStackTrace()
            }
            editor.commit()

            adapter.holders.forEach {
                it.checkBox.visibility = View.INVISIBLE
            }
        }
    }
}

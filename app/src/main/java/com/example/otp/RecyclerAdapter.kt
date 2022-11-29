package com.example.otp

import android.content.Context
import android.content.Intent
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.otp.RecyclerAdapter.MyViewHolder

open class RecyclerAdapter(var context: Context, var data: ArrayList<User>) : RecyclerView.Adapter<MyViewHolder>() {

    val holders: ArrayList<MyViewHolder> = ArrayList()
    val markedForDeletionIndexes: ArrayList<Int> = ArrayList()

    var removeUsers: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_items, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.usernameText.text = data[position].Username
        holder.checkBox.isEnabled = false
        holder.checkBox.visibility = View.INVISIBLE
        holders.add(holder)

        holder.mainLayout.setOnClickListener {
            if (removeUsers) {
                holder.checkBox.isChecked = !holder.checkBox.isChecked

                if (holder.checkBox.isChecked) {
                    markedForDeletionIndexes.add(position)
                }
                else {
                    markedForDeletionIndexes.remove(position) // ovo je sus
                }
            }
            else {
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra("username", data[position].Username)
                context.startActivity(intent)
            }
        }

        holder.mainLayout.setOnLongClickListener {
            removeUsers = true
            holder.checkBox.isChecked = true
            holder.checkBox.visibility = View.VISIBLE
            markedForDeletionIndexes.add(position)
            holders.forEach() {
                it.checkBox.isEnabled = true
                it.checkBox.visibility = View.VISIBLE
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun removeItem(user: User) {
        var position: Int = data.indexOf(user)
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, data.size)
        holders.removeAt(position)
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var usernameText: TextView
        var checkBox: CheckBox
        var mainLayout: ConstraintLayout

        init {
            usernameText = itemView.findViewById(R.id.text)
            checkBox = itemView.findViewById(R.id.checkBox)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }
}

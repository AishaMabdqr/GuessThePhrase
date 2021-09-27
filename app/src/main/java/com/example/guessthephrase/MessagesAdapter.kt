package com.example.guessthephrase

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class MessagesAdapter (val context : Context, val messages : ArrayList<String> ) :RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    class MessageViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        holder.itemView.apply {
            tvMessages.text = message
            if(message.startsWith("Found")){
                tvMessages.setTextColor(Color.GREEN)
            }else if(message.startsWith("No")||message.startsWith("Wrong")){
                tvMessages.setTextColor(Color.RED)
            }else{
                tvMessages.setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount() = messages.size

}
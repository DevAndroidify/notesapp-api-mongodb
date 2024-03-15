package com.example.practiseforhalt

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.practiseforhalt.models.newnoteresponseItem

class noteadapter(private val context: Context, private val noteList: List<newnoteresponseItem>) :
    RecyclerView.Adapter<noteadapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.noteslayout, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val innerList = noteList[position] // Access the note at the given position
        holder.titleTextView.text = innerList.title
        holder.descriptionTextView.text = innerList.description

       holder.itemView.setOnClickListener {
          val a=Intent(context,noteactivity::class.java)
           a.putExtra("title",innerList.title)
           a.putExtra("description",innerList.description)
           a.putExtra("noteid",innerList._id)
           context.startActivity(a)

       }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.notetitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.notedescription)
    }
}

package com.example.notnow

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NoteAdapter(val notes: List<Note>,
                  val itemClickListener: View.OnClickListener,
                  val itemLongClickListener: View.OnLongClickListener)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val dateView = cardView.findViewById<TextView>(R.id.titleDate)
        val titleView = cardView.findViewById<TextView>(R.id.titleView)
        val excerptView = cardView.findViewById<TextView>(R.id.excerpt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.dateView.text = note.date
        holder.titleView.text = note.title
        holder.excerptView.text = note.text

        holder.cardView.setOnLongClickListener(itemLongClickListener)
        holder.cardView.tag = position
        holder.dateView.text = note.date
        holder.titleView.text = note.title
        holder.excerptView.text = note.text
    }

    override fun getItemCount(): Int {
        return notes.size
    }

}
package com.heycode.coolnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.heycode.coolnote.models.NoteData
import com.heycode.coolnote.models.Priority

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    var noteList = emptyList<NoteData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_txt).text = noteList[position].title
        holder.itemView.findViewById<TextView>(R.id.description_txt).text =
            noteList[position].description
        val priority = noteList[position].priority

        when (priority) {
            Priority.HIGH -> {
                holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.red
                        )
                    )
            }
            Priority.MEDIUM -> {
                holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.yellow
                        )
                    )
            }
            Priority.LOW -> {
                holder.itemView.findViewById<CardView>(R.id.priority_indicator)
                    .setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.green
                        )
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(noteList: List<NoteData>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }
}
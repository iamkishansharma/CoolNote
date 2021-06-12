package com.heycode.coolnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.heycode.coolnote.fragments.NoteFragmentDirections
import com.heycode.coolnote.models.NoteData
import com.heycode.coolnote.models.Priority

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    var noteList = emptyList<NoteData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title_txt)
        val priority = itemView.findViewById<CardView>(R.id.priority_indicator)
        val description = itemView.findViewById<TextView>(R.id.description_txt)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.MyViewHolder, position: Int) {
        holder.title.text = noteList[position].title
        holder.description.text = noteList[position].description
        val priority = noteList[position].priority

        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener {
            val action =
                NoteFragmentDirections.actionNoteFragmentToUpdateFragment(noteList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (priority) {
            Priority.HIGH -> {
                holder.priority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
            }
            Priority.MEDIUM -> {
                holder.priority.setCardBackgroundColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.yellow
                    )
                )
            }
            Priority.LOW -> {
                holder.priority.setCardBackgroundColor(
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
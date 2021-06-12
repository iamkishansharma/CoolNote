package com.heycode.coolnote.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.heycode.coolnote.R
import com.heycode.coolnote.database.viewmodel.NoteViewModel
import com.heycode.coolnote.database.viewmodel.SharedViewModel
import com.heycode.coolnote.models.NoteData

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var prioritySpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        title = view.findViewById(R.id.update_title_et)
        description = view.findViewById(R.id.update_description_et)
        prioritySpinner = view.findViewById(R.id.update_priority_spinner)

        title.text = args.currentItem.title
        description.text = args.currentItem.description

        //showing text colour on default selection of spinner item
        prioritySpinner.setSelection(sharedViewModel.parsePriorityToInt(args.currentItem.priority))
        prioritySpinner.onItemSelectedListener = sharedViewModel.listener

        //saving updated information button
        view.findViewById<Button>(R.id.update_button).setOnClickListener { updateItem() }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_note) {
            confirmItemDelete()
        }
        return true
    }


    private fun updateItem() {
        val titl = title.text.toString()
        val desc = description.text.toString()
        val prio = prioritySpinner.selectedItem.toString()

        if (sharedViewModel.verifyDataFromUser(titl, desc)) {
            val updatedNote = NoteData(
                args.currentItem.id,
                titl,
                desc,
                sharedViewModel.parsePriority(prio)
            )
            noteViewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_noteFragment)

        } else {
            title.error = "Required"
            description.error = "Required"
        }
    }

    private fun confirmItemDelete() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                noteViewModel.deleteNote(args.currentItem)
                Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${args.currentItem.title}",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_noteFragment)
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete \"${args.currentItem.title}\"?")
            .setMessage("Are you sure you want ot delete this note?")
            .create().show()
    }
}
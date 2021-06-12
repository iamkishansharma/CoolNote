package com.heycode.coolnote.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.heycode.coolnote.R
import com.heycode.coolnote.database.viewmodel.NoteViewModel
import com.heycode.coolnote.database.viewmodel.SharedViewModel
import com.heycode.coolnote.models.NoteData

class AddFragment : Fragment() {
    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.findViewById<Button>(R.id.add_button).setOnClickListener {
            insertToDb(
                view.findViewById(R.id.title_et),
                view.findViewById(R.id.priority_spinner),
                view.findViewById(R.id.description_et)
            )
        }

        view.findViewById<Spinner>(R.id.priority_spinner).onItemSelectedListener =
            sharedViewModel.listener

        return view
    }

    private fun insertToDb(
        mTitle: EditText,
        mPriority: Spinner,
        mDescription: EditText
    ) {
        val title: String = mTitle.text.toString()
        val priority: String = mPriority.selectedItem.toString()
        val description: String = mDescription.text.toString()

        if (sharedViewModel.verifyDataFromUser(title, description)) {
            val noteData = NoteData(0, title, description, sharedViewModel.parsePriority(priority))
            noteViewModel.insertNote(noteData)
            Toast.makeText(requireContext(), "Note added successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_noteFragment)
        } else {
            mTitle.error = "Required"
            mDescription.error = "Required"
        }
    }

}
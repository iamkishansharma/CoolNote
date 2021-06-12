package com.heycode.coolnote.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.heycode.coolnote.NoteAdapter
import com.heycode.coolnote.R
import com.heycode.coolnote.database.viewmodel.NoteViewModel
import com.heycode.coolnote.database.viewmodel.SharedViewModel

class NoteFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val adapter: NoteAdapter by lazy { NoteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        noteViewModel.getAllNotes.observe(viewLifecycleOwner, { data ->
            sharedViewModel.isDatabaseEmpty(data)
            adapter.setData(data)
        })

        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, {
            showNoDataItems(it)
        })
        setHasOptionsMenu(true)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_addFragment)
        }
        return view
    }

    //If database is empty show this
    private fun showNoDataItems(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.VISIBLE
        } else {
            view?.findViewById<ImageView>(R.id.no_data_imageView)?.visibility = View.INVISIBLE
            view?.findViewById<TextView>(R.id.no_data_textView)?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmDeletion()
        }
        return false
    }

    private fun confirmDeletion() {
        AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                noteViewModel.deleteAllNotes()
                Toast.makeText(
                    requireContext(),
                    "✔ All notes deleted!",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("\uD83D\uDEA9 Delete all the notes?")
            .setMessage("Are you sure you want to delete all the notes?\nNOTE: All your notes will be lost.")
            .create().show()
    }

}
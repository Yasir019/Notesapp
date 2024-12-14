package com.yasir4y.notesapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.Lifecycle
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import com.yasir4y.notesapp.MainActivity
import com.yasir4y.notesapp.R
import com.yasir4y.notesapp.databinding.FragmentEditNoteBinding
import com.yasir4y.notesapp.model.Note
import com.yasir4y.notesapp.viewmodel.NoteViewModel
import android.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.navigation.fragment.findNavController

class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the menu provider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        notesViewModel = (activity as MainActivity).noteViewModel

        // Get the note passed as an argument
        currentNote = args.note!!

        // Populate the fields with the note's current data
        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        // Set up the floating action button click listener
        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()

            if (noteTitle.isNotEmpty()) {
                val note = Note(currentNote.id.toString(), noteTitle, noteDesc)
                notesViewModel.update(note)

                // Navigate back to the home fragment
                view.findNavController().popBackStack(R.id.homeFragment, inclusive = false)
            } else {
                Toast.makeText(requireContext(), "Please enter note title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to delete the note
    private fun deleteNote() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, inclusive = false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    // Cleanup to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        editNoteBinding = null
    }

    // Inflate the menu
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.edit_menu, menu) // Ensure this menu file exists
    }

    // Handle menu item selection
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }
}

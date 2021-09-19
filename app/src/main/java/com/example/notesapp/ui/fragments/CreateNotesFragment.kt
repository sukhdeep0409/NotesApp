package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentCreateNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.viewModel.NotesViewModel
import java.util.*

class CreateNotesFragment : Fragment() {

    private lateinit var binding: FragmentCreateNotesBinding
    private var priority: String = "1"
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)

        //default
        binding.priorityGreen.setImageResource(R.drawable.ic_tick)

        binding.priorityGreen.setOnClickListener {
            priority = "1"
            binding.priorityGreen.setImageResource(R.drawable.ic_tick)
            binding.priorityRed.setImageResource(0)
            binding.priorityYellow.setImageResource(0)
        }
        binding.priorityYellow.setOnClickListener {
            priority = "2"
            binding.priorityYellow.setImageResource(R.drawable.ic_tick)
            binding.priorityRed.setImageResource(0)
            binding.priorityGreen.setImageResource(0)
        }
        binding.priorityRed.setOnClickListener {
            priority = "3"
            binding.priorityRed.setImageResource(R.drawable.ic_tick)
            binding.priorityGreen.setImageResource(0)
            binding.priorityYellow.setImageResource(0)
        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.saveTitle.text.toString()
        val subTitle = binding.saveSubtitle.text.toString()
        val notes = binding.saveTextNotes.text.toString()
        val date: CharSequence = DateFormat.format("MMMM d, yyyy", Date().time).toString()

        if(title.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please fill out the title",
                Toast.LENGTH_LONG
            ).show()
        }
        else {
            val data = Notes(null, title, subTitle, notes, date.toString(), priority)
            viewModel.addNotes(data)
            Toast.makeText(
                requireContext(),
                "Notes created successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
    }
}
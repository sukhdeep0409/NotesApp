package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class EditNotesFragment : Fragment() {
    private lateinit var binding: FragmentEditNotesBinding
    private var priority: String = "1"

    val notes by navArgs<EditNotesFragmentArgs>()
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.editTitle.setText(notes.data.title)
        binding.editSubtitle.setText(notes.data.subTitle)
        binding.editNotes.setText(notes.data.notes)

        when(notes.data.priority) {
            "1" -> {
                priority = "1"
                binding.priorityGreen.setImageResource(R.drawable.ic_tick)
                binding.priorityRed.setImageResource(0)
                binding.priorityYellow.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.priorityYellow.setImageResource(R.drawable.ic_tick)
                binding.priorityRed.setImageResource(0)
                binding.priorityGreen.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.priorityRed.setImageResource(R.drawable.ic_tick)
                binding.priorityGreen.setImageResource(0)
                binding.priorityYellow.setImageResource(0)
            }
        }

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

        binding.btnEditNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root
    }

    private fun updateNotes(it: View?) {
        val title = binding.editTitle.text.toString()
        val subTitle = binding.editSubtitle.text.toString()
        val editNotes = binding.editNotes.text.toString()
        val date: CharSequence = DateFormat.format("MMMM d, yyyy", Date().time).toString()

        if(title.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please fill out the title",
                Toast.LENGTH_LONG
            ).show()
        }
        else {
            val data = Notes(notes.data.id, title, subTitle, editNotes, date.toString(), priority)
            viewModel.updateNotes(data)
            Toast.makeText(
                requireContext(),
                "Notes updated successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        Navigation.findNavController(it!!).navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()

            val yes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val no = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            yes?.setOnClickListener {
                viewModel.deleteNotes(notes.data.id!!)
                bottomSheet.dismiss()
                activity?.supportFragmentManager?.popBackStack()
            }
            no?.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
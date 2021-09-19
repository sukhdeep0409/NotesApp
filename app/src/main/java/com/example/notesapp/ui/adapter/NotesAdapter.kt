package com.example.notesapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.fragments.HomeFragmentDirections

class NotesAdapter
constructor(val requireContext: Context, private val notesList: List<Notes>):
RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val model = notesList[position]
        val view = holder.binding

        view.notesTitle.text = model.title
        view.notesSubtitle.text = model.subTitle
        view.notesDate.text = model.date

        when(model.priority) {
            "1" -> {
                holder.binding.viewPriority
                    .setBackgroundResource(R.drawable.green)
            }
            "2" -> {
                holder.binding.viewPriority
                    .setBackgroundResource(R.drawable.yellow)
            }
            "3" -> {
                holder.binding.viewPriority
                    .setBackgroundResource(R.drawable.red)
            }
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(model)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = notesList.size

    class NotesViewHolder(val binding: ItemNotesBinding): RecyclerView.ViewHolder(binding.root)
}
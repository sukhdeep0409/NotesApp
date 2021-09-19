package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.adapter.NotesAdapter
import com.example.notesapp.viewModel.NotesViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var myNotes = arrayListOf<Notes>()

    private val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
            myNotes = notesList as ArrayList<Notes>
            binding.recyclerViewAllNotes.apply {
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(), notesList)
            }
        })

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, { notesList ->
                myNotes = notesList as ArrayList<Notes>
                binding.recyclerViewAllNotes.apply {
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    adapter = NotesAdapter(requireContext(), notesList)
                }
            })
        }

        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner, { notesList ->
                myNotes = notesList as ArrayList<Notes>
                binding.recyclerViewAllNotes.apply {
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    adapter = NotesAdapter(requireContext(), notesList)
                }
            })

        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner, { notesList ->
                myNotes = notesList as ArrayList<Notes>
                binding.recyclerViewAllNotes.apply {
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    adapter = NotesAdapter(requireContext(), notesList)
                }
            })
        }

        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner, { notesList ->
                myNotes = notesList as ArrayList<Notes>
                binding.recyclerViewAllNotes.apply {
                    layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                    adapter = NotesAdapter(requireContext(), notesList)
                }
            })
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView

        searchView.queryHint="Enter title to search ... "
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                notesFiltering(p0)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun notesFiltering(param: String?) {
        val newFilteredList = arrayListOf<Notes>()
        for (note in myNotes) {
            if (note.title.contains(param!!) || note.subTitle.contains(param)) {
                newFilteredList.add(note)
            }
        }

        binding.recyclerViewAllNotes.apply {
            (adapter as NotesAdapter).filtering(newFilteredList)
        }
    }
}
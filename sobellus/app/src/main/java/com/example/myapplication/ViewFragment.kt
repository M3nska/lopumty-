package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.AppDatabase

class ViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view, container, false)

        // Step 1: Get DAO from database instance
        val dao = AppDatabase.getInstance(requireContext()).studyNoteDao()

        // Step 2: Create ViewModelFactory with DAO
        val factory = StudyViewModelFactory(dao)

        // Step 3: Get ViewModel using factory
        val viewModel = ViewModelProvider(requireActivity(), factory).get(StudyViewModel::class.java)

        // Step 4: Reference to TextView
        val notesTextView = view.findViewById<TextView>(R.id.saved_notes)

        // Step 5: Observe LiveData from Room and update UI
        viewModel.studyNotes.observe(viewLifecycleOwner) { notes ->
            val displayText = notes.joinToString("\n\n") {
                "Aine: ${it.subject}\nTehtävä: ${it.activity}"
            }
            notesTextView.text = displayText
        }

        return view
    }
}

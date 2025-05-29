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

        // creates viewmodel for factory and for dao
        val dao = AppDatabase.getInstance(requireContext()).studyNoteDao()
        val factory = StudyViewModelFactory(dao)


        val viewModel = ViewModelProvider(requireActivity(), factory).get(StudyViewModel::class.java)


        val notesTextView = view.findViewById<TextView>(R.id.saved_notes)

        // counts the data and then converts that data into a displayable text
        viewModel.studyNotes.observe(viewLifecycleOwner) { notes ->
            val grouped = notes.groupingBy { it.subject }.eachCount()
            val displayText = grouped.entries.joinToString("\n") { (subject, count) ->
                "$subject: $count tehtävä" + if (count > 1) "ä" else ""
            }
            //adds text to textview
            notesTextView.text = displayText
        }

        return view
    }
}

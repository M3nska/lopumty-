package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.StudyNote
import com.example.myapplication.StudyViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: StudyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Get the DAO
        val dao = com.example.myapplication.data.AppDatabase.getInstance(requireContext()).studyNoteDao()

        // Create the ViewModelFactory
        val factory = StudyViewModelFactory(dao)

        // Initialize the ViewModel with factory
        viewModel = ViewModelProvider(requireActivity(), factory)[StudyViewModel::class.java]


        val inputActivity = view.findViewById<EditText>(R.id.input_activity)
        val inputSubject = view.findViewById<EditText>(R.id.input_subject)
        val buttonSubmit = view.findViewById<Button>(R.id.button_submit)

        buttonSubmit.setOnClickListener {
            val activityText = inputActivity.text.toString().trim()
            val subjectText = inputSubject.text.toString().trim()

            if (activityText.isEmpty() || subjectText.isEmpty()) {
                Toast.makeText(requireContext(), "Täytä molemmat kentät", Toast.LENGTH_SHORT).show()
            } else {
                val note = StudyNote(subject = subjectText, activity = activityText)
                viewModel.addNote(note)
                inputActivity.text.clear()
                inputSubject.text.clear()
                Toast.makeText(requireContext(), "Tallennettu", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.StudyViewModel
import com.example.myapplication.StudyViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.StudyNote


class EditFragment : Fragment() {

    private lateinit var viewModel: StudyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewNotes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = NotesAdapter { note ->
            confirmAndDelete(note)
        }
        recyclerView.adapter = adapter

        // Initialize ViewModel
        val dao = AppDatabase.getInstance(requireContext()).studyNoteDao()
        val factory = StudyViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudyViewModel::class.java]

        // Observe notes list and submit to adapter
        viewModel.studyNotes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }
        // Code for delebutton so it removes the selected file from database
        val deleteAllButton = view.findViewById<View>(R.id.buttonDeleteAll)
        deleteAllButton.setOnClickListener {
            if (adapter.itemCount == 0) {
                Toast.makeText(requireContext(), "Ei mitään poistettavaa", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Poista kaikki")
                    .setMessage("Haluatko varmasti poistaa kaikki merkinnät?")
                    .setPositiveButton("Kyllä") { _, _ ->
                        viewModel.deleteAllNotes()
                        Toast.makeText(requireContext(), "Kaikki merkinnät poistettu", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Peruuta", null)
                    .show()
            }
        }


        return view
    }
    //Code for deleting everything from app
    private fun confirmAndDelete(note: StudyNote) {
        AlertDialog.Builder(requireContext())
            .setTitle("Opiskelunpoisto")
            .setMessage("Haluatko varmasti poistaa tämän tehtävän tai opiskelun?")
            .setPositiveButton("Poista") { _, _ ->
                viewModel.deleteNote(note)
                Toast.makeText(requireContext(), "Poistettu", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Peruuta", null)
            .show()
    }


}

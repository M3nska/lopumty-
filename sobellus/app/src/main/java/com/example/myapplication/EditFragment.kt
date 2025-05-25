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

        adapter = NotesAdapter { note -> // callback on note click
            confirmAndDelete(note)
        }
        recyclerView.adapter = adapter

        // Initialize ViewModel (you'll need your ViewModelFactory with DAO)
        val dao = AppDatabase.getInstance(requireContext()).studyNoteDao()
        val factory = StudyViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory)[StudyViewModel::class.java]

        // Observe notes list and submit to adapter
        viewModel.studyNotes.observe(viewLifecycleOwner) { notes ->
            adapter.submitList(notes)
        }

        return view
    }

    private fun confirmAndDelete(note: StudyNote) {
        AlertDialog.Builder(requireContext())
            .setTitle("Opiskelunpoisto")
            .setMessage("Haluatko varmasti poistaa tämän tehtävän tai opiskelun?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(note)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

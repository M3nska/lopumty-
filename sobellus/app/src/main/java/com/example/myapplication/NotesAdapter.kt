import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.StudyNote



class NotesAdapter(private val onItemClick: (StudyNote) -> Unit) :
    ListAdapter<StudyNote, NotesAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return NoteViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(itemView: View, val onItemClick: (StudyNote) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val subjectView: TextView = itemView.findViewById(android.R.id.text1)
        private val activityView: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(note: StudyNote) {
            subjectView.text = note.subject
            activityView.text = note.activity

            itemView.setOnClickListener {
                onItemClick(note)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StudyNote>() {
        override fun areItemsTheSame(oldItem: StudyNote, newItem: StudyNote) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StudyNote, newItem: StudyNote) =
            oldItem == newItem
    }
}

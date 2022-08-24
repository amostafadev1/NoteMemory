package com.example.notememory

import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notememory.DataManager.notes
import com.example.notememory.databinding.ItemNoteListBinding

class NoteRecyclerAdapter() :
    RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemBinding: ItemNoteListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val textCourse = itemBinding.courseTitle
        val textNote = itemBinding.noteTitle
        init {
            itemBinding.root.setOnClickListener {
                val intent = Intent(it.context, MainActivity::class.java)
                intent.putExtra(NOTE_POSITION, layoutPosition)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemNoteListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.textCourse.text = note.course?.title ?: "No course"
        holder.textNote.text = note.title
    }

    override fun getItemCount() = notes.size
}
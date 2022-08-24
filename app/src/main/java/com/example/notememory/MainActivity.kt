package com.example.notememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import com.example.notememory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var note: NoteInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val adapterCourses = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.mainContent.spinnerCourses.adapter = adapterCourses

        val notePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition == POSITION_NOT_SET) {
            note = NoteInfo()
        } else {
            note = DataManager.notes[notePosition]
            displayNote()
        }
    }

    private fun displayNote() {
        note?.apply {
            binding.mainContent.textNoteTitle.setText(title)
            binding.mainContent.textNoteText.setText(text)
            val coursePosition = DataManager.courses.values.indexOf(course)
            binding.mainContent.spinnerCourses.setSelection(coursePosition)
        }
    }

    override fun onPause() {
        super.onPause()
        if (!binding.mainContent.textNoteTitle.text.isBlank())
            note?.apply {
                if (title == null)
                    DataManager.notes.add(this)
                saveNote()
            }
    }

    private fun saveNote() {
        note?.apply {
            title = binding.mainContent.textNoteTitle.text.toString()
            text = binding.mainContent.textNoteText.text.toString()
            course = binding.mainContent.spinnerCourses.selectedItem as CourseInfo
        }
    }
}
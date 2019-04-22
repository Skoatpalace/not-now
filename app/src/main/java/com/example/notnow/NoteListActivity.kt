package com.example.notnow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.notnow.utils.deleteNote
import com.example.notnow.utils.loadNotes
import com.example.notnow.utils.persistNote


class NoteListActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter
    lateinit var coordinatorLayout: CoordinatorLayout
    val ACTION_NEW_NOTE = "com.example.notnow.action.ACTION_NEW_NOTE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(this)

        notes = loadNotes(this)
        adapter = NoteAdapter(notes, this, this)

        if (intent.getStringExtra("com.example.notnow.action.ACTION_NEW_NOTE") == ACTION_NEW_NOTE){
            createNewNote()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        coordinatorLayout = findViewById(R.id.coordinator_layout)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        when (data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }

    }

    override fun onLongClick(view: View): Boolean {
        showConfirmDeleteNoteDialog(view.tag as Int)
        return true
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            showNoteDetail(view.tag as Int)
        } else {
            when (view.id) {
                R.id.fab -> createNewNote()
            }
        }
    }

    fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this, note)
        if (noteIndex < 0) {
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun showConfirmDeleteNoteDialog(noteIndex: Int) {
        val note: Note = notes[noteIndex]
        val confirmFragment = ConfirmDeleteNoteDialogFragment(note.date)
        confirmFragment.listener = object : ConfirmDeleteNoteDialogFragment.ConfirmeDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deleteNote(noteIndex)
            }

            override fun onDialogNegativeClick() {}
        }

        confirmFragment.show(supportFragmentManager, "confirmDeleteDialog")
    }

    private fun deleteNote(noteIndex: Int) {
        if (noteIndex < 0) {
            return
        }
        val note = notes.removeAt(noteIndex)
        deleteNote(this, note)
        adapter.notifyDataSetChanged()

        Snackbar.make(
            coordinatorLayout,
            if (note.title.isEmpty()) {
                "la note du ${note.date} a été supprimée"
            } else {
                "${note.title} a été supprimée"
            }, LENGTH_SHORT
        ).show()
    }

    fun createNewNote() {

        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex: Int) {
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }
}

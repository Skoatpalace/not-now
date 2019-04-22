package com.example.notnow

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_note_detail.*
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
        val ACTION_SAVE_NOTE = "com.example.notnow.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.example.notnow.actions.ACTION_DELETE_NOTE"

    }

    lateinit var note: Note
    var noteIndex: Int = -1

    lateinit var dateView: TextView
    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setLogo(R.drawable.logo_complet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)
        textDetail.requestFocus()
        dateView = findViewById(R.id.dateDetail)
        titleView = findViewById(R.id.titleDetail)
        textView = findViewById(R.id.textDetail)

        dateView.text = note.date
        titleView.text = note.title
        textView.text = note.text

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.FRANCE)
            dateView.text = sdf.format(cal.time)

        }

        dateView.setOnClickListener {
            DatePickerDialog(
                this@NoteDetailActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                saveNote()
                return true
            }
            R.id.delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)

            }
        }
    }

    private fun showConfirmDeleteNoteDialog() {
        val confirmFragment = ConfirmDeleteNoteDialogFragment(note.date)
        confirmFragment.listener = object : ConfirmDeleteNoteDialogFragment.ConfirmeDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {}
        }

        confirmFragment.show(supportFragmentManager, "confirmDeleteDialog")
    }

    fun saveNote() {
        note.date = dateView.text.toString()
        note.title = titleView.text.toString()
        note.text = textView.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun deleteNote() {
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

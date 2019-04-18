package com.example.notnow

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
    }

    lateinit var note: Note
    var noteIndex:Int = -1

    lateinit var dateView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        dateView = findViewById(R.id.dateDetail)
        textView = findViewById(R.id.textDetail)

        dateView.text = note.date
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
            DatePickerDialog(this@NoteDetailActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}

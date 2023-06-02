package com.alimarangoz.noteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.alimarangoz.noteapp.database.Database

class NoteDetailActivity : AppCompatActivity() {

    lateinit var deleteBtn : Button
    lateinit var database : Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = Database(this)

        deleteBtn = findViewById(R.id.btn_delete)

        val bundle = intent.extras
        val title = bundle?.getString("title")
        val detail = bundle?.getString("detail")
        val date = bundle?.getString("date")
        val nid = bundle?.getInt("nid")

        val detailTextView: TextView = findViewById(R.id.detailTextView)
        val dateTextView: TextView = findViewById(R.id.dateTextView)


        supportActionBar?.title = title
        detailTextView.text = detail
        dateTextView.text = date


        deleteBtn.setOnClickListener {
            val deleteNote = nid?.let { it1 -> database.deleteNote(it1) }
            Log.d("deleted Note",deleteNote.toString())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}

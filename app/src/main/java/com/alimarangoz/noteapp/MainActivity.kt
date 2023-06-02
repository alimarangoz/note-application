package com.alimarangoz.noteapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.alimarangoz.noteapp.database.Database
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var database: Database
    lateinit var btnDate: AppCompatButton
    lateinit var btnSave: AppCompatButton
    lateinit var title : AutoCompleteTextView
    lateinit var detail : AutoCompleteTextView
    lateinit var listView : ListView
    private var selectDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnSave = findViewById(R.id.btn_save)
        btnDate = findViewById(R.id.btn_date)

        title = findViewById(R.id.title_txt)
        detail = findViewById(R.id.detail_txt)


        listView = findViewById(R.id.list_view)

        database = Database(this)

        val calendar = Calendar.getInstance()

        val noteList = database.allNote()

        val titleList = mutableListOf<String>()
        for (note in noteList) {
            titleList.add(note.title)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList)
        listView.adapter = adapter


        listView.setOnItemClickListener { parent, view, position, id ->
            val note = noteList[position]


            var intent = Intent(this, NoteDetailActivity::class.java)

            intent.putExtra("title", note.title)
            intent.putExtra("detail", note.detail)
            intent.putExtra("date",note.date)
            intent.putExtra("nid",note.nid)

            startActivity(intent)
            onPause()
        }



        btnDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                    var month = "{$i2+1}"
                    if (i2+1 < 10){
                        month = "0${i2+1}"
                    }
                    selectDate = "$i3.$month.$i" },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),

            )
            datePickerDialog.show()
        }

        btnSave.setOnClickListener {

            if ( selectDate != "" ) {

                if (title.text.toString() != ""){

                    database.addNote(title.text.toString(), detail.text.toString(), selectDate)

                    titleList.add(title.text.toString())

                    adapter.notifyDataSetChanged()

                    title.setText("")
                    detail.setText("")

                }else{
                    Toast.makeText(this, "Please Write a Title!", Toast.LENGTH_LONG).show()
                }


            }else {
                Toast.makeText(this, "Please Select Date!", Toast.LENGTH_LONG).show()
            }

        }

    }
}
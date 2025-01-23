package com.example.studentsapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapplication.models.Student

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        val studentNameEditText = findViewById<EditText>(R.id.studentNameEditText)
        val studentIdEditText = findViewById<EditText>(R.id.studentIdEditText)
        val saveStudentButton = findViewById<Button>(R.id.saveStudentButton)

        // Set a static image (already done in XML, no additional logic is required here)

        saveStudentButton.setOnClickListener {
            val name = studentNameEditText.text.toString()
            val id = studentIdEditText.text.toString()

            // Validate inputs
            if (name.isBlank() || id.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new Student object
            val newStudent = Student(id, name, false)

            // Pass the student back to the Student List Activity
            val resultIntent = Intent()
            resultIntent.putExtra("newStudent", newStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close the activity
        }
    }
}
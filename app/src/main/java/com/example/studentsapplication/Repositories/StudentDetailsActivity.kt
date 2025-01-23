package com.example.studentsapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapplication.models.Student

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        // Retrieve the student passed from the StudentListActivity using the new method
        student = intent.getParcelableExtra<Student>("student") ?: return

        // Initialize UI components
        val studentImage: ImageView = findViewById(R.id.studentImageView)
        val studentName: TextView = findViewById(R.id.studentNameTextView)
        val studentId: TextView = findViewById(R.id.studentIdTextView)
        val studentStatus: TextView = findViewById(R.id.studentStatusTextView)
        val editButton: Button = findViewById(R.id.editStudentButton)

        // Set student details in the UI
        studentImage.setImageResource(R.drawable.student) // Replace with your drawable
        studentName.text = student.name
        studentId.text = student.id
        studentStatus.text = if (student.checkStatus) "Checked" else "Unchecked"

        // Handle edit button click
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            startActivity(intent)
        }
    }
}
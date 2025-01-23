
package com.example.studentsapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapplication.models.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Retrieve the student passed from StudentDetailsActivity
        student = intent.getParcelableExtra<Student>("student") ?: return

        // Initialize UI components
        val studentNameInput: EditText = findViewById(R.id.editStudentNameInput)
        val studentIdInput: EditText = findViewById(R.id.editStudentIdInput)
        val studentCheckBox: CheckBox = findViewById(R.id.editStudentCheckBox)
        val saveButton: Button = findViewById(R.id.saveStudentButton)
        val deleteButton: Button = findViewById(R.id.deleteStudentButton)

        // Populate fields with the current student data
        studentNameInput.setText(student.name)
        studentIdInput.setText(student.id)
        studentCheckBox.isChecked = student.checkStatus

        // Save button functionality
        saveButton.setOnClickListener {
            val updatedName = studentNameInput.text.toString().trim()
            val updatedId = studentIdInput.text.toString().trim()
            val updatedCheckStatus = studentCheckBox.isChecked

            if (updatedName.isEmpty() || updatedId.isEmpty()) {
                Toast.makeText(this, "Name and ID cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update student object
            val updatedStudent = student.copy(
                name = updatedName,
                id = updatedId,
                checkStatus = updatedCheckStatus
            )

            // Return the updated student to StudentListActivity or StudentDetailsActivity
            val resultIntent = Intent()
            resultIntent.putExtra("updatedStudent", updatedStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // Delete button functionality
        deleteButton.setOnClickListener {
            // Pass the deletion signal back
            val resultIntent = Intent()
            resultIntent.putExtra("deleteStudentId", student.id)
            setResult(Activity.RESULT_FIRST_USER, resultIntent)
            finish()
        }
    }
}

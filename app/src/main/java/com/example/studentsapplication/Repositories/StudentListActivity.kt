package com.example.studentsapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapplication.adapters.StudentAdapter
import com.example.studentsapplication.models.Student

class StudentListActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    private val editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Handle student update
            val updatedStudent: Student? = result.data?.getParcelableExtra("updatedStudent")
            updatedStudent?.let {
                val position = studentList.indexOfFirst { student -> student.id == it.id }
                if (position != -1) {
                    studentList[position] = it // Update the student at the correct position
                    adapter.notifyItemChanged(position) // Notify adapter that the item has been updated
                    Toast.makeText(this, "Student updated!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (result.resultCode == RESULT_FIRST_USER) {
            // Handle student deletion
            val deletedStudentId = result.data?.getStringExtra("deleteStudentId")
            deletedStudentId?.let {
                val position = studentList.indexOfFirst { it.id == deletedStudentId }
                if (position != -1) {
                    studentList.removeAt(position)
                    adapter.notifyItemRemoved(position) // Notify adapter that the item has been removed
                    Toast.makeText(this, "Student deleted!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    // Register the launcher for the Add Student screen
    @SuppressLint("NotifyDataSetChanged")
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newStudent: Student? = result.data?.getParcelableExtra("newStudent")
            newStudent?.let {
                studentList.add(it)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Student added!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewStudentList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter
        adapter = StudentAdapter(studentList) { student ->
            // Open the EditStudentActivity when a student is clicked
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            editStudentLauncher.launch(intent)  // Use the launcher to open the activity

        }

        recyclerView.adapter = adapter

        // Add sample data (optional)
        addSampleStudents()

        // Add student button
        findViewById<android.widget.Button>(R.id.addStudentButton).setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }
    }

    // Function to add sample students for testing
    @SuppressLint("NotifyDataSetChanged")
    private fun addSampleStudents() {
        studentList.add(Student("123456","Gali Cohen", false))
        studentList.add(Student("987654","Omer Gil", true))
        adapter.notifyDataSetChanged()
    }
}

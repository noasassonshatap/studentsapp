package com.example.studentsapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapplication.R
import com.example.studentsapplication.models.Student

class StudentAdapter(
    private val students: MutableList<Student>,
    private val onStudentClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student, onStudentClick)
    }

    override fun getItemCount(): Int = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val studentImage: ImageView = itemView.findViewById(R.id.studentImage)
        private val studentName: TextView = itemView.findViewById(R.id.studentName)
        private val studentId: TextView = itemView.findViewById(R.id.studentId)
        private val studentCheckBox: CheckBox = itemView.findViewById(R.id.checkBoxStatus)

        fun bind(student: Student, onStudentClick: (Student) -> Unit) {
            studentImage.setImageResource(R.drawable.student) // Static image
            studentName.text = student.name
            studentId.text = "ID: ${student.id}"
            studentCheckBox.isChecked = student.checkStatus

            studentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                student.checkStatus = isChecked
            }

            itemView.setOnClickListener {
                onStudentClick(student)
            }
        }
    }
}
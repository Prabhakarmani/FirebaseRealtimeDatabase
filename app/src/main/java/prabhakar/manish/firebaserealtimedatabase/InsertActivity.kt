package prabhakar.manish.firebaserealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import prabhakar.manish.firebaserealtimedatabase.databinding.ActivityFetchBinding
import prabhakar.manish.firebaserealtimedatabase.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        binding.btnSave.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        val empName = binding.etEmpName.text.toString()
        val empAge = binding.etEmpAge.text.toString()
        val empSalary = binding.etEmpSalary.text.toString()

        if (empName.isEmpty()) {
            binding.etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            binding.etEmpAge.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            binding.etEmpSalary.error = "Please enter salary"
        }

        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                binding.etEmpName.text?.clear()
                binding.etEmpAge.text?.clear()
                binding.etEmpSalary.text?.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
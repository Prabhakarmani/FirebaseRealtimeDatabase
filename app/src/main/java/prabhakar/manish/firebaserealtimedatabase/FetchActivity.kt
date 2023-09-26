package prabhakar.manish.firebaserealtimedatabase

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import prabhakar.manish.firebaserealtimedatabase.databinding.ActivityFetchBinding
import prabhakar.manish.firebaserealtimedatabase.databinding.ActivityMainBinding

class FetchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFetchBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        // Show the loading screen
        progressDialog.show()
        binding.llayout.visibility= View.INVISIBLE

        getEmployeeData(4)
    }

    private fun getEmployeeData(num : Int) {
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.limitToFirst(num).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Data fetched successfully
                progressDialog.dismiss() // Hide the loading screen
                binding.llayout.visibility= View.VISIBLE

                if (snapshot.exists()) {
                    for (empSnap in snapshot.children) {
                        val empData = empSnap.getValue(EmployeeModel::class.java)
                        if (empData != null) {
                            binding.tvEmpName.text = empData.empName
                            binding.tvEmpAge.text = empData.empAge
                            binding.tvEmpSalary.text = empData.empSalary
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors that may occur
                progressDialog.dismiss() // Hide the loading screen
                // Handle the onCancelled event as needed
            }
        })
    }
}
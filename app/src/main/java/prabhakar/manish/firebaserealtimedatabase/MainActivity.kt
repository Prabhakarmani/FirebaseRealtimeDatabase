package prabhakar.manish.firebaserealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import prabhakar.manish.firebaserealtimedatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        binding.btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }

        binding.btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchActivity::class.java)
            startActivity(intent)


        }
        getNumberOfEntries()
    }

    private fun getNumberOfEntries() {
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val numberOfEntries = snapshot.childrenCount.toInt()
                    // `numberOfEntries` now contains the count of entries in the "Employees" node
                    // You can use it as needed
                    binding.tvNumberOfEntries.text = "Number of Entries: $numberOfEntries"
                } else {
                    // Handle the case where there are no entries in the node
                    binding.tvNumberOfEntries.text = "No Entries Found"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the onCancelled event as needed
            }
        })
    }

}
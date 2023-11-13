
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7312_poe_task2.BirdItem
import com.example.opsc7312_poe_task2.R
import com.google.firebase.firestore.FirebaseFirestore

class Birdsfrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BirdListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val birdsfrag = inflater.inflate(R.layout.fragment_birdsfrag, container, false)

        recyclerView = birdsfrag.findViewById(R.id.recyclerView)

        // Initialize your list of bird items
        val birdItems = mutableListOf<BirdItem>()

        // Fetch data from Firebase and update the list
        fetchDataFromFirebase(birdItems)

        // Set up a RecyclerView adapter
        adapter = BirdListAdapter(birdItems, requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return birdsfrag
    }

    private fun fetchDataFromFirebase(birdItems: MutableList<BirdItem>) {
        // TODO: Use your Firebase Firestore instance and query to fetch bird data
        val db = FirebaseFirestore.getInstance()
        db.collection("birds")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // Parse the data from the document and add it to the birdItems list
                    val birdName = document.getString("name") ?: "Bird 1"
                    val sightingDate = document.getString("date") ?: "2023-10-20"
                    val sightingLocation = document.getString("location") ?: "Location 1"

                    birdItems.add(BirdItem(R.mipmap.ic_launcher_round, birdName, sightingDate, sightingLocation))
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle failures
            }
    }
}

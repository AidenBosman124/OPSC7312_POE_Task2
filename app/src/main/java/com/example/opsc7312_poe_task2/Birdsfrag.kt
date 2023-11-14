
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7312_poe_task2.BirdItem
import com.example.opsc7312_poe_task2.BirdListAdapter
import com.example.opsc7312_poe_task2.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

public class Birdsfrag : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BirdListAdapter
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val birdsfrag = inflater.inflate(R.layout.fragment_birdsfrag, container, false)
        recyclerView = birdsfrag.findViewById(R.id.recyclerView)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with FirebaseRecyclerOptions
        val options = FirebaseRecyclerOptions.Builder<BirdItem>()
            .setQuery(database.child("users").child(FirebaseAuth.getInstance().currentUser?.uid ?: "").child("birds"), BirdItem::class.java)
            .build()

        adapter = BirdListAdapter(options, requireContext())
        recyclerView.adapter = adapter

        return birdsfrag
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

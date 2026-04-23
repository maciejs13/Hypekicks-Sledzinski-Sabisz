package com.example.hypekicks

import Sneaker
import SneakerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class StorefrontActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private val db = FirebaseFirestore.getInstance()

    private var sneakerList = mutableListOf<Sneaker>()
    private var fullList = mutableListOf<Sneaker>()
    private lateinit var adapter: SneakerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_front)

        gridView = findViewById(R.id.gridView)

        loadSneakers()
    }

    private fun loadSneakers() {

        db.collection("sneakers")
            .get()
            .addOnSuccessListener { result ->

                sneakerList.clear()
                fullList.clear()


                for (doc in result.documents) {
                    val sneaker = doc.toObject(Sneaker::class.java)
                    if (sneaker != null) {
                        sneakerList.add(sneaker)
                        fullList.add(sneaker)
                    }
                }

                adapter = SneakerAdapter(this, sneakerList)
                gridView.adapter = adapter

                setupClick()
                setupSearch()
            }
    }

    private fun setupClick() {

        gridView.setOnItemClickListener { _, _, position, _ ->

            val sneaker = sneakerList[position]

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("sneaker", sneaker)

            startActivity(intent)
        }
    }

    private fun setupSearch() {
        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText?.trim()?.lowercase() ?: ""

                sneakerList.clear()


                if (text.isEmpty()) {
                    sneakerList.addAll(fullList)
                } else {
                    for (s in fullList) {

                        val model = s.modelName

                        if (model.lowercase().contains(text)) {
                            sneakerList.add(s)
                        }
                    }
                }

                adapter.notifyDataSetChanged()
                return true
            }
        })

    }
}


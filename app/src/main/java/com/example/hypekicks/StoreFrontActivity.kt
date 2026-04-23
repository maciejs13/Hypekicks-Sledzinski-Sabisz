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

                for (doc in result.documents) {
                    val sneaker = doc.toObject(Sneaker::class.java)
                    if (sneaker != null) {
                        sneakerList.add(sneaker)
                    }
                }

                adapter = SneakerAdapter(this, sneakerList)
                gridView.adapter = adapter

                setupClick()
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
}
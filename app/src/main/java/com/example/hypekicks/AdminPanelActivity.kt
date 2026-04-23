package com.example.hypekicks

import Sneaker
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AdminPanelActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private lateinit var listView: ListView
    private var sneakerList = mutableListOf<Sneaker>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        listView = findViewById(R.id.listView)

        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }
        val brand = findViewById<EditText>(R.id.brand)
        val model = findViewById<EditText>(R.id.model)
        val year = findViewById<EditText>(R.id.rok)
        val price = findViewById<EditText>(R.id.price)
        val image = findViewById<EditText>(R.id.image)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        loadData()

        btnAdd.setOnClickListener {

            val sneaker = hashMapOf(
                "brand" to brand.text.toString(),
                "modelName" to model.text.toString(),
                "releaseYear" to year.text.toString().toIntOrNull(),
                "resellPrice" to price.text.toString().toDoubleOrNull(),
                "imageUrl" to image.text.toString()
            )

            db.collection("sneakers").add(sneaker)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->

            val sneaker = sneakerList[position]

            db.collection("sneakers")
                .whereEqualTo("modelName", sneaker.modelName ?: "")
                .get()
                .addOnSuccessListener { docs ->
                    for (doc in docs) {
                        db.collection("sneakers").document(doc.id).delete()
                    }
                }

            true
        }
    }

    private fun loadData() {

        db.collection("sneakers")
            .addSnapshotListener { result, _ ->

                sneakerList.clear()
                val names = mutableListOf<String>()

                if (result != null) {

                    for (doc in result.documents) {

                        val sneaker = doc.toObject(Sneaker::class.java)

                        if (sneaker != null) {
                            sneakerList.add(sneaker)
                            names.add("${sneaker.brand} ${sneaker.modelName}")
                        }
                    }
                }

                adapter.clear()
                adapter.addAll(names)
                adapter.notifyDataSetChanged()
            }
    }

}
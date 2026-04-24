package com.example.hypekicks

import Sneaker
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val image = findViewById<ImageView>(R.id.image)
        val brand = findViewById<TextView>(R.id.brand)
        val model = findViewById<TextView>(R.id.model)
        val year = findViewById<TextView>(R.id.year)
        val price = findViewById<TextView>(R.id.price)
        val backButton = findViewById<Button>(R.id.backButton)

        val sneaker = intent.getSerializableExtra("sneaker") as? Sneaker

        if (sneaker != null) {

            brand.text = sneaker.brand
            model.text = sneaker.modelName
            year.text = "Rok: ${sneaker.releaseYear}"
            price.text = "Cena: ${sneaker.resellPrice} zł"

            Glide.with(this)
                .load(sneaker.imageUrl)
                .into(image)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}


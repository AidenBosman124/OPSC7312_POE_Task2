package com.example.opsc7312_poe_task2

import android.content.Context
import com.google.gson.Gson

class PlacesReader(private val context: Context) {

    // GSON object responsible for converting from JSON to a Place object
    private val gson = Gson()

    // Method to read JSON data from a file and convert it to a list of Place objects
    fun readPlacesFromJsonFile(filename: String): List<Place> {
        // You can use the context and filename to read JSON data from a file in the resources or assets
        // and then parse it into a list of Place objects using the Gson library.
        // This method should return the list of Place objects.

        // Sample code:
        val jsonText = readJsonFileContent(filename) // Implement this method to read JSON from a file
        return gson.fromJson(jsonText, Array<Place>::class.java).toList()
    }

    private fun readJsonFileContent(filename: String): String {
        // Implement this method to read the JSON data from a file (e.g., in the assets or resources) and return it as a string.
        // You can use the context to access the resources or assets.
        // Sample code:
        val inputStream = context.assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }
}

package com.example.practiseforhalt
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiseforhalt.api.noteapi
import com.example.practiseforhalt.api.objectforapi
import com.example.practiseforhalt.databinding.FragmentMainBinding
import com.example.practiseforhalt.models.noterequest
import com.example.practiseforhalt.models.noteresponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var apiService: noteapi
    var notelist:List<noteresponse>?=null
    override fun onResume() {
        super.onResume()
        addrecylerview()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        apiService = objectforapi.apiServices
         addrecylerview()

            binding.addmore.setOnClickListener {
                val token=sharedPreferences.getString("token",null)

                    // Inflate the dialog layout
                    val dialogView = layoutInflater.inflate(R.layout.dialog_layout, null)

                    // Reference to the input fields in the dialog layout
                    val titleEditText = dialogView.findViewById<EditText>(R.id.dailogtitle)
                    val descriptionEditText = dialogView.findViewById<EditText>(R.id.dialogdes)

                    // Create and show the dialog
                    AlertDialog.Builder(requireContext())
                        .setView(dialogView)
                        .setTitle("Enter Title and Description")
                        .setPositiveButton("OK") { dialog, which ->
                            val title = titleEditText.text.toString()
                            val description = descriptionEditText.text.toString()
                            val noterequest=noterequest(description,title)
                            createNoteWithAuthorization(token!!,noterequest)

                            // Process title and description here
                            Toast.makeText(requireContext(), "Title: $title, Description: $description", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                }





//        binding.createNoteButton.setOnClickListener {
//            val token = sharedPreferences.getString("token", null)
//            if (!token.isNullOrBlank()) {
//                val noteRequest = noterequest("this is test data","helloworld")
//                createNoteWithAuthorization(token, noteRequest)
//            } else {
//                Toast.makeText(requireContext(), "Token not found", Toast.LENGTH_SHORT).show()
//            }
//        }

        return binding.root
    }

    fun addrecylerview() {
        GlobalScope.launch {
            try {
                val response = objectforapi.apiServices.getNotes()


                // Check if the response contains data

                // Update UI on the main thread
                withContext(Dispatchers.Main) {
                    // Set adapter with the converted list
                    binding.noterecylerview.adapter = noteadapter(requireContext(),response)
                  //  val adapter = binding.noterecylerview.adapter as? noteadapter
                    //adapter?.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                // Handle error
                Log.e("MainFragment", "Error: ${e.message}", e)

                // Show error message to the user, if necessary
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createNoteWithAuthorization(token: String, noteRequest: noterequest) {
        lifecycleScope.launch {
            try {
                val response = apiService.createNote("Brearer $token", noteRequest)
                withContext(Dispatchers.Main) {
                    // Handle success
                    addrecylerview()
                    Toast.makeText(requireContext(), "Note created successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Handle error
                    Toast.makeText(requireContext(), "Failed to create note: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}


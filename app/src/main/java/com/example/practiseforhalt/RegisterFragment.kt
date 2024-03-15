package com.example.practiseforhalt

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.practiseforhalt.api.objectforapi
import com.example.practiseforhalt.databinding.FragmentRegisterBinding
import com.example.practiseforhalt.models.userrequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private lateinit  var binding:FragmentRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding=FragmentRegisterBinding.inflate(layoutInflater)
        binding.Login.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.registersubmit.setOnClickListener {
            val username=binding.registerusername.text.toString()
            val email=binding.registeremail.text.toString()
            val password=binding.registerpassword.text.toString()

            GlobalScope.launch {
                try {
                    val response=objectforapi.apiService.signin(userrequest(username,email,password))
                    val token=response.token
                    withContext(Dispatchers.Main){
                            sharedPreferences.edit().putString("token",token).apply()
                        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

                    }

                }catch (e:Exception){

                }

            }

        }

        return binding.root


    }


}
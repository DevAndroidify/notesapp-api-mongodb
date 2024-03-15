package com.example.practiseforhalt

import android.content.Context
import android.content.SharedPreferences
import android.net.http.HttpException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.practiseforhalt.api.objectforapi
import com.example.practiseforhalt.databinding.FragmentLoginBinding
import com.example.practiseforhalt.models.userrequest
import com.example.practiseforhalt.models.userresponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment : Fragment() {

         private lateinit var binding:FragmentLoginBinding
         private lateinit var  response:userresponse
         private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        binding=FragmentLoginBinding.inflate(layoutInflater)
         binding.loginregister.setOnClickListener {
             findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
         }
        binding.loginsubmit.setOnClickListener {
            val email=binding.loginemail.text.toString()
            val password=binding.loginpassword.text.toString()
            GlobalScope.launch {
                try {
                    response=objectforapi.apiService.signup(userrequest("notrequired",email,password))
                    var token=response.token
                    withContext(Dispatchers.Main){
                        sharedPreferences.edit().putString("token",token).apply()
                        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

                    }

                }catch (e:Exception){
                     withContext(Dispatchers.Main){
                         Toast.makeText(requireContext(),"something went wrong", Toast.LENGTH_SHORT).show()
                         }


                      }

            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }


}
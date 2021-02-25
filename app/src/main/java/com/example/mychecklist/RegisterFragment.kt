package com.example.mychecklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mychecklist.data.ApiService
import com.example.mychecklist.databinding.FragmentAuthBinding
import com.example.mychecklist.databinding.FragmentRegisterBinding
import com.example.mychecklist.utils.Constant
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.registerBtn?.setOnClickListener { registerAuth() }
    }

    private fun registerAuth() {
        val email = binding?.emailEdt?.text
        val password = binding?.passwordEdt?.text
        val username = binding?.usernameEdt?.text

        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("username", username)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val service = provideApiService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.registerAuth(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    binding?.root?.let {
                        Snackbar.make(it, "Succes Register", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToAuthFragment())
                } else {
                    binding?.root?.let {
                        Snackbar.make(it, "Fail Register", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
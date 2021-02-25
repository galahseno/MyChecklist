package com.example.mychecklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mychecklist.data.ApiService
import com.example.mychecklist.databinding.FragmentLoginBinding
import com.example.mychecklist.databinding.FragmentRegisterBinding
import com.example.mychecklist.utils.Constant
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.loginBtn?.setOnClickListener { loginAuth() }
    }

    private fun loginAuth() {
        val password = binding?.passwordEdt?.text
        val username = binding?.usernameEdt?.text

        val jsonObject = JSONObject()
        jsonObject.put("password", password)
        jsonObject.put("username", username)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val service = provideApiService()

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.loginAuth(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val token = response.body()?.data?.token.toString()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToChecklistFragment(token))
                } else {
                    binding?.root?.let {
                        Snackbar.make(it, "Login failed", Snackbar.LENGTH_SHORT)
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
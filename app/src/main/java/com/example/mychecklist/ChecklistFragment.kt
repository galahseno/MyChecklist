package com.example.mychecklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychecklist.adapter.ItemAdapter
import com.example.mychecklist.data.ApiService
import com.example.mychecklist.databinding.FragmentChecklistBinding
import com.example.mychecklist.utils.Constant.Companion.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChecklistFragment : Fragment() {
    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding
    private lateinit var itemAdapter: ItemAdapter
    private val arg by navArgs<ChecklistFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChecklistBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = ItemAdapter(ItemAdapter.OnClickListener {

        })

        binding?.let {
            with(it.itemRv) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = itemAdapter
            }
        }

        populateView()

        binding?.floatingActionButton?.setOnClickListener { }
    }

    private fun populateView() {
        val service = provideApiService()
        val token = arg.token
        Log.d("Checklist", token)
        CoroutineScope(Dispatchers.IO).launch {
            // val response = service.getChecklist(token)

            withContext(Dispatchers.Main) {
                //    itemAdapter.submitList(response)
            }
        }
    }


    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
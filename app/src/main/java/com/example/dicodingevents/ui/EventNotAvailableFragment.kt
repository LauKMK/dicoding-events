package com.example.dicodingevents.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.databinding.FragmentEventNotAvailableBinding

class EventNotAvailableFragment : Fragment() {

    private var _binding: FragmentEventNotAvailableBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: EventNotAvailableViewModel
    private lateinit var adapter: EventNotAvailableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventNotAvailableBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[EventNotAvailableViewModel::class.java]
        adapter = EventNotAvailableAdapter { eventId ->
            val action = EventNotAvailableFragmentDirections.actionEventNotAvailableFragmentToEventDetailFragment(eventId)
            findNavController().navigate(action)
        }

        with(binding) {
            rvEventNotAvailable.layoutManager = LinearLayoutManager(context)
            rvEventNotAvailable.adapter = adapter

            viewModel.eventResponse.observe(viewLifecycleOwner) { eventResponse ->
                if (eventResponse?.listEvents.isNullOrEmpty()) {

                    rvEventNotAvailable.visibility = View.GONE
                    tvErrorMessage.visibility = View.VISIBLE
                } else {

                    rvEventNotAvailable.visibility = View.VISIBLE
                    if (eventResponse != null) {
                        adapter.submitList(eventResponse.listEvents)
                    }
                    tvErrorMessage.visibility = View.GONE
                    viewModel.clearErrorMessage()

                }
            }

            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {

                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }


            viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    tvErrorMessage.text = errorMessage
                    tvErrorMessage.visibility = View.VISIBLE
                    rvEventNotAvailable.visibility = View.GONE
                } else {
                    tvErrorMessage.visibility = View.GONE
                }
            }
        }

        // Fetch the initial data
        viewModel.fetchNotAvailableEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
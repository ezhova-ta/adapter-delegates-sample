package com.example.adapterdelegatessample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.adapterdelegatessample.databinding.FragmentMainBinding
import com.example.adapterdelegatessample.databinding.FullLocationListItemBinding
import com.example.adapterdelegatessample.databinding.ShortLocationListItemBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        setupLocationListView()
        return binding.root
    }

    private fun setupLocationListView() {
        val locationsAdapter = ListDelegationAdapter(
            getShortLocationAdapterDelegate(),
            getFullLocationAdapterDelegate()
        )

        binding.locationList.apply {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        }

        locationsAdapter.items = getLocationList()
    }

    private fun getShortLocationAdapterDelegate() = adapterDelegateViewBinding<ShortLocation, Location, ShortLocationListItemBinding>(
        { layoutInflater, root -> ShortLocationListItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.cityView.text = item.city
            binding.countryView.text = item.country
        }
    }

    private fun getFullLocationAdapterDelegate() = adapterDelegateViewBinding<FullLocation, Location, FullLocationListItemBinding>(
        { layoutInflater, root -> FullLocationListItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.streetView.text = item.street
            binding.buildingNumberView.text = item.buildingNumber
            binding.apartmentNumberView.text = item.apartmentNumber.toString()
            binding.cityView.text = item.city
            binding.countryView.text = item.country
        }
    }

    private fun getLocationList() = listOf(
        ShortLocation("Russia", "Novosibirsk"),
        FullLocation("Russia", "Novosibirsk", "ul.Lenina", "52", 124),
        ShortLocation("Russia", "Saint Petersburg"),
        FullLocation("Russia", "Saint Petersburg", "pr.Nevsky", "56/8", 41),
        ShortLocation("Russia", "Krasnoyarsk")
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
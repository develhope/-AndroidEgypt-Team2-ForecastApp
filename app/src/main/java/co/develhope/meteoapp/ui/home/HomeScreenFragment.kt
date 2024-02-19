package co.develhope.meteoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.data.domain.toWeekItems
import co.develhope.meteoapp.databinding.FragmentHomeScreenBinding
import co.develhope.meteoapp.ui.home.adapter.WeekAdapter
import co.develhope.meteoapp.ui.home.view_model.HomeViewModel
import co.develhope.meteoapp.ui.search.adapter.DataSearches
import co.develhope.meteoapp.ui.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSearches = Data.getSearchCity(requireContext())

        var longitude = DataSearches.ItemSearch(
            longitude = 0.0,
            latitude = 0.0,
            recentCitySearch = "",
            admin1 = ""
        ).longitude
        if (dataSearches is DataSearches.ItemSearch) {
            longitude = dataSearches.longitude
        }

        var latitude = DataSearches.ItemSearch(
            longitude = 0.0,
            latitude = 0.0,
            recentCitySearch = "",
            admin1 = ""
        ).latitude
        if (dataSearches is DataSearches.ItemSearch) {
            latitude = dataSearches.latitude
        }
        homeViewModel.getWeekly(latitude!!, longitude!!)
        setUpObserver()
        setupAdapter()

    }

    private fun setupAdapter() {

        binding.homeRecyclerView.adapter = WeekAdapter(listOf()) {}

    }

    private fun setUpObserver() {
        homeViewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Failure -> it.throwable.message
                is DataState.Loading -> binding.homeProgress.visibility = View.VISIBLE
                is DataState.Success -> {
                    binding.homeProgress.visibility = View.GONE
                    (binding.homeRecyclerView.adapter as WeekAdapter).setNewList(
                        it.data.toWeekItems(
                            requireContext()
                        )
                    )
                }
            }
        }
        lifecycleScope.launch {
            homeViewModel.navigationCommand.collect() {
                findNavController().navigate(R.id.search_screen)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}

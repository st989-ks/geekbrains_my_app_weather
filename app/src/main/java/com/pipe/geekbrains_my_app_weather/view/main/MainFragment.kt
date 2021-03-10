package com.pipe.geekbrains_my_app_weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pipe.geekbrains_my_app_weather.R
import com.pipe.geekbrains_my_app_weather.databinding.FragmentMainBinding
import com.pipe.geekbrains_my_app_weather.model.Weather
import com.pipe.geekbrains_my_app_weather.view.details.WeatherFragment
import com.pipe.geekbrains_my_app_weather.viewmodel.AppState
import com.pipe.geekbrains_my_app_weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var isDataSetRus: Boolean = true

    private val adapter =
        RecyclerViewAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(weather: Weather) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .add(R.id.container, WeatherFragment.newInstance(Bundle().apply {
                            putParcelable(WeatherFragment.BUNDLE_EXTRA, weather)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

    companion object {
        fun newInstance() =
            MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainFragmentFAB, "Error",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        viewModel.getWeatherFromLocalSourceRus()
                    }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }
}
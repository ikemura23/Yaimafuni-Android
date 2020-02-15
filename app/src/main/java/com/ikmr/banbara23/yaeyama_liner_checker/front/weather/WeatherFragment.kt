package com.ikmr.banbara23.yaeyama_liner_checker.front.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ikmr.banbara23.yaeyama_liner_checker.R
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.WeatherFragmentBinding
import com.ikmr.banbara23.yaeyama_liner_checker.utils.CustomTabUtil

/**
 * A placeholder fragment containing a simple view.
 */
class WeatherFragment : Fragment() {

    private val viewModel: WeatherScreenViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherScreenViewModel::class.java)
    }
    private lateinit var binding: WeatherFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.weather_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        viewModel.load()
    }

    private fun setupViewModel() {
        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is WeatherScreenViewModel.Nav.Error -> {
                    //エラーメッセージ
                }
                is WeatherScreenViewModel.Nav.More -> openBrowser()
            }
        })
    }

    private fun openBrowser() {
        CustomTabUtil.start(activity, getString(R.string.weather_open_url))
    }

    companion object {

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }
}

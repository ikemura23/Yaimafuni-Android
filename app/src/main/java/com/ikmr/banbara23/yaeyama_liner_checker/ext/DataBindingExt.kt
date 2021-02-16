package com.ikmr.banbara23.yaeyama_liner_checker.ext

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ActivityのDataBindingを設定する
 *
 */
fun <T : ViewDataBinding> AppCompatActivity.viewBinding(): ReadOnlyProperty<AppCompatActivity, T> {
    return object : ReadOnlyProperty<AppCompatActivity, T> {
        override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
            val view = thisRef.findViewById<ViewGroup>(android.R.id.content)[0]
            val binding = DataBindingUtil.bind<T>(view)!!
            binding.lifecycleOwner = thisRef
            return binding
        }
    }
}

// Fragment.viewBinding() 用
class FragmentViewNotFoundException : Exception("Fragment#getView() returned null.")

/**
 * FragmentのDataBindingを設定する
 *
 * Example
 *
 * ```
 * private val binding: WeatherFragmentBinding by viewBinding()
 *
 * override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *   super.onViewCreated(view, savedInstanceState)
 *   binding.viewModel ...
 * }
 * ```
 *
 */
fun <T : ViewDataBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> {
    return object : ReadOnlyProperty<Fragment, T> {
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val view = thisRef.view ?: throw FragmentViewNotFoundException()
            val binding = DataBindingUtil.bind<T>(view)!!
            binding.lifecycleOwner = thisRef.viewLifecycleOwner
            return binding
        }
    }
}

package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.bIncrement.setOnClickListener { viewModel.increment() }
        binding.bRandom.setOnClickListener { viewModel.setRandomColor() }
        binding.bSwitch.setOnClickListener { viewModel.switchVisibility() }

        if (viewModel.state.value == null) {
            viewModel.initState(
                State(
                    counterValue = 0,
                    counterTextColor = ContextCompat.getColor(this, R.color.purple_200),
                    counterIsVisible = true
                )
            )
        }

        viewModel.state.observe(this, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: State) = with(binding) {
        textView.setText(state.counterValue.toString())
        textView.setTextColor(state.counterTextColor)
        textView.visibility = if (state.counterIsVisible) View.VISIBLE else View.INVISIBLE
    }
}
package com.example.callmevideoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.callmevideoapp.databinding.ActivityMainBinding
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.callmevideoapp.R

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Create binding
    binding = ActivityMainBinding.inflate(layoutInflater)

    setStatusBarPrimary()

    setContentView(binding.root)
  }

  fun setStatusBarPrimary(){
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, R.color.background)
    window.navigationBarColor = ContextCompat.getColor(this, R.color.background)
  }
}
package com.goldstein.room2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goldstein.room2.databinding.ActivityMainBinding
import com.goldstein.room2.fragment.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserFragment.newInstance())
                .commitNow()
        }
    }
}
package com.ivasco.marvelpedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ivasco.marvelpedia.view.CharacterSearchFragment
import com.ivasco.marvelpedia.view.ISearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = this.getString(R.string.app_name)

        navigation.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CharacterSearchFragment.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_navigation, menu)
        val searchItem = menu.findItem(R.id.navigation_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val currentFragment = getCurrentFragment() as ISearchFragment
                currentFragment.makeSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun getCurrentFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        fragments.forEach { fragment ->
            if (fragment.isVisible) {
                return fragment
            }
        }
        return null
    }

    private val onBottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container,
                            CharacterSearchFragment.newInstance()
                        )
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
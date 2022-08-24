package com.example.notememory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.iterator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notememory.databinding.ActivityItemsBinding

class ItemsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityItemsBinding
    private var counter = 0
    private val recyclerView by lazy {
        binding.appBarItems.contentItems.itemsList
    }

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }
    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter()
    }
    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarItems.toolbar)

        binding.appBarItems.fab.setOnClickListener { _ ->
            startActivity(Intent(this, MainActivity::class.java))
        }

        val counterNow = savedInstanceState?.getInt("counter") ?: 0

        if (counterNow < 1)
            displayNotes()

        binding.navView.menu.findItem(R.id.nav_notes).isChecked = true

        val toggle = ActionBarDrawerToggle(
            this, binding.root, binding.appBarItems.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.root.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun displayNotes() {
        recyclerView.adapter = noteRecyclerAdapter
        recyclerView.layoutManager = noteLayoutManager
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_notes -> displayNotes()
            R.id.nav_courses -> displayCourses()
            R.id.nav_info -> {
                val message = getString(
                    R.string.nav_how_many_message_format,
                    DataManager.courses.size, DataManager.notes.size
                )
                Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show()
            }
        }
        binding.root.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displayCourses() {
        recyclerView.adapter = courseRecyclerAdapter
        recyclerView.layoutManager = courseLayoutManager
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        counter++
        outState.putInt("counter", counter)
    }
}
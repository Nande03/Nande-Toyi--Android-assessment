package com.glucode.about_you.engineers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import com.glucode.about_you.mockdata.MockData.engineers

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding
    private lateinit var adapter: EngineersRecyclerViewAdapter
    private var engineersList: MutableList<Engineer> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpEngineersList(MockData.engineers.toMutableList())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    private fun setUpEngineersList(engineers: MutableList<Engineer>) {
        adapter = EngineersRecyclerViewAdapter(engineers) { engineer ->
            goToAbout(engineer)
        }
        binding.list.layoutManager = LinearLayoutManager(requireContext()) as RecyclerView.LayoutManager
        binding.list.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)

        this.engineersList = engineers
    }

    private fun sortEngineersByBugs() {
        engineersList.sortBy { it.quickStats.bugs }
        adapter.updateList(engineersList)
    }

    private fun sortEngineersByCoffee() {
        engineersList.sortBy { it.quickStats.coffees }
        adapter.updateList(engineersList)
    }

    private fun sortEngineersByYears() {
        engineersList.sortBy { it.quickStats.years }
        adapter.updateList(engineersList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> {
                sortEngineersByYears()
                true // Return true to indicate that the event has been handled
            }
            R.id.action_coffees -> {
                sortEngineersByCoffee()
                true // Return true to indicate that the event has been handled
            }
            R.id.action_bugs -> {
                sortEngineersByBugs()
                true // Return true to indicate that the event has been handled
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val position = data.getIntExtra("position", -1)
            if (position != -1 && selectedImageUri != null) {
                val updatedEngineer = engineers[position].copy(imageUrl = selectedImageUri)
                engineersList[position] = updatedEngineer
                adapter.updateList(engineersList)
            }
        }
    }
    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}
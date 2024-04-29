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
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.mockdata.MockData
import com.glucode.about_you.mockdata.MockData.engineers

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding
    private lateinit var adapter: EngineersRecyclerViewAdapter
    private lateinit var engineersList: MutableList<Engineer>

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
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) { engineer ->
            goToAbout(engineer)
        }
        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(dividerItemDecoration)

        this.engineersList = engineers
        adapter = binding.list.adapter as EngineersRecyclerViewAdapter
    }

    private fun sortEngineersByBugs() {
        engineersList.sortBy { it.quickStats.bugs }
        adapter.notifyDataSetChanged()
    }

    private fun sortEngineersByCoffee() {
        engineersList.sortBy { it.quickStats.coffees }
        adapter.notifyDataSetChanged()
    }

    private fun sortEngineersByYears() {
        engineersList.sortBy { it.quickStats.years }
        adapter.notifyDataSetChanged()
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
                engineers = engineers.toMutableList().apply {
                    set(position, updatedEngineer)
                }
                binding.list.adapter?.notifyItemChanged(position)
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
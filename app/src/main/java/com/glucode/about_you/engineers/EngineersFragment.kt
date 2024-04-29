import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.EngineersViewModel
import com.glucode.about_you.engineers.models.Engineer

class EngineersFragment : Fragment() {

    private lateinit var binding: FragmentEngineersBinding
    private lateinit var adapter: EngineersRecyclerViewAdapter
    private val viewModel: EngineersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        setUpRecyclerView()
        observeViewModel()
        return binding.root
    }

    private fun setUpRecyclerView() {
        adapter = EngineersRecyclerViewAdapter { engineer, position -> goToAbout(engineer, position) }
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@EngineersFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        viewModel.engineers.observe(viewLifecycleOwner) { engineers ->
            adapter.setEngineersList(engineers)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_years -> {
                viewModel.sortingEngineersByYears()
                true
            }
            R.id.action_coffees -> {
                viewModel.sortEngineersByCoffee()
                true
            }
            R.id.action_bugs -> {
                viewModel.sortEngineersByBugs()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToAbout(engineer: Engineer, position: Int) {
        val bundle = Bundle().apply {
            putString("name", engineer.name)
            putInt("position", position)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val position = data.getIntExtra("position", RecyclerView.NO_POSITION)
            if (position != RecyclerView.NO_POSITION && selectedImageUri != null) {
                viewModel.updateEngineerImage(position, selectedImageUri)
            }
        }
    }
}

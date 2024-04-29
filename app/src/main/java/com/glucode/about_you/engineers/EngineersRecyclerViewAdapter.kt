import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ItemEngineerBinding
import com.glucode.about_you.engineers.models.Engineer

const val PICK_IMAGE_REQUEST = 1

class EngineersRecyclerViewAdapter(
    private val onClick: (Engineer, position: Int) -> Unit
) : RecyclerView.Adapter<EngineersRecyclerViewAdapter.EngineerViewHolder>() {

    private var engineers = listOf<Engineer>()

    fun setEngineersList(newEngineers: List<Engineer>) {
        val diffCallback = EngineersDiffCallback(engineers, newEngineers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        engineers = newEngineers
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerViewHolder {
        val binding = ItemEngineerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EngineerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EngineerViewHolder, position: Int) {
        holder.bind(engineers[position], position)
    }

    override fun getItemCount(): Int = engineers.size

    inner class EngineerViewHolder(private val binding: ItemEngineerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(engineer: Engineer, position: Int) {
            with(binding) {
                name.text = engineer.name
                role.text = engineer.role
                yearsValue.text = engineer.quickStats.years.toString()
                coffeesValue.text = engineer.quickStats.coffees.toString()
                bugsValue.text = engineer.quickStats.bugs.toString()

                Glide.with(root.context)
                    .load(engineer.imageUrl)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(profileImage)

                root.setOnClickListener {
                    onClick(engineer, position)
                }

                profileImage.setOnClickListener {
                    selectImageGallery(position)
                }
            }
        }

        private fun selectImageGallery(position: Int) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            (itemView.context as? Activity)?.startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }
}

class EngineersDiffCallback(private val oldList: List<Engineer>, private val newList: List<Engineer>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}

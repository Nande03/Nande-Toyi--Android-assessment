package com.glucode.about_you.engineers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ItemEngineerBinding
import com.glucode.about_you.engineers.models.Engineer

const val PICK_IMAGE_REQUEST = 1

class EngineersRecyclerViewAdapter(
    private var engineers: List<Engineer>,
    private val onClick: (Engineer) -> Unit
) : RecyclerView.Adapter<EngineersRecyclerViewAdapter.EngineerViewHolder>() {

    override fun getItemCount() = engineers.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EngineerViewHolder {
        return EngineerViewHolder(ItemEngineerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EngineerViewHolder, position: Int) {
        holder.bind(engineers[position], onClick)
    }

    inner class EngineerViewHolder(private val binding: ItemEngineerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(engineer: Engineer, onClick: (Engineer) -> Unit) {
            with(binding) {
                name.text = engineer.name
                role.text = engineer.role
                yearsValue.text = engineer.quickStats.years.toString()
                coffeesValue.text = engineer.quickStats.coffees.toString()
                bugsValue.text = engineer.quickStats.bugs.toString()
                root.setOnClickListener {
                    onClick(engineer)
                }

                Glide.with(root.context)
                    .load(engineer.imageUrl)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(profileImage)

                profileImage.setOnClickListener {
                    selectimageGallery(adapterPosition)
                }
            }
        }
        private fun selectimageGallery(adapterPosition: Int) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            (itemView.context as? Activity)?.startActivityForResult(intent, PICK_IMAGE_REQUEST, Bundle().apply {
                putInt("position", adapterPosition)
            })
        }
    }
}
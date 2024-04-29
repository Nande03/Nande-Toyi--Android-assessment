import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewAnswerCardBinding

class AnswerCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding: ViewAnswerCardBinding =
        ViewAnswerCardBinding.inflate(LayoutInflater.from(context), this)
    @ColorInt
    private val selectedCardBackgroundColor: Int
    @ColorInt
    private val selectedTextColor: Int
    @ColorInt
    private val deselectedTextColor: Int
    @ColorInt
    private val selectedBorderColor: Int
    private val selectedBorderWidth: Float

    var title: String? = null
        set(value) {
            field = value
            binding.title.text = value
        }

    init {
        val whiteColour = ContextCompat.getColor(context, R.color.white)
        val blackColour = ContextCompat.getColor(context, R.color.black)
        selectedCardBackgroundColor = blackColour
        selectedTextColor = blackColour
        deselectedTextColor = whiteColour
        selectedBorderColor = whiteColour
        selectedBorderWidth = resources.getDimension((R.dimen.corner_radius_normal))
        radius = resources.getDimension(R.dimen.corner_radius_normal)
        elevation = resources.getDimension(R.dimen.elevation_normal)

        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            binding.title.setTextColor(selectedTextColor)
            setBackgroundWithBorder(selectedBorderColor, selectedBorderWidth) // New: Add border when selected
        } else {
            binding.title.setTextColor(deselectedTextColor)
            setBackgroundWithBorder(selectedCardBackgroundColor, selectedBorderWidth)
        }
    }

    // New: Function to set background with border
    fun setBackgroundWithBorder(@ColorInt borderColor: Int, borderWidth: Float) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = radius
            setStroke(borderWidth.toInt(), borderColor)
            if (isSelected) {
                setColor(Color.WHITE)
            }
        }
        background = drawable
    }
}
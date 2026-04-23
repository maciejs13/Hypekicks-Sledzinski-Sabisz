import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.hypekicks.R

class SneakerAdapter(
    private val context: Context,
    private var list: List<Sneaker>
) : BaseAdapter() {

    override fun getCount() = list.size

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_sneaker, parent, false)

        val sneaker = list[position]

        val image = view.findViewById<ImageView>(R.id.image)
        val brand = view.findViewById<TextView>(R.id.brand)
        val model = view.findViewById<TextView>(R.id.model)

        brand.text = sneaker.brand
        model.text = sneaker.modelName

        Glide.with(context)
            .load(sneaker.imageUrl)
            .into(image)

        return view
    }

    fun updateData(newList: List<Sneaker>) {
        list = newList
        notifyDataSetChanged()
    }
}
package ru.appcommerce.mapkittaxi.presentation.feature.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatTextView
import com.yandex.mapkit.search.SuggestItem
import ru.appcommerce.mapkittaxi.R

class SearchAdapter(context: Context): BaseAdapter(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val suggestItems: MutableList<SuggestItem> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val holder: ServicesViewHolder
        if (convertView == null){
            view = inflater.inflate(R.layout.item_suggestion_search, parent, false)
            holder = ServicesViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = view.tag as ServicesViewHolder
        }
        if(suggestItems.size != position){
            val suggest = suggestItems[position]
            holder.name.text = suggest.displayText.orEmpty()
        }
        return view
    }
    override fun getItem(position: Int): String {
        return suggestItems[position].displayText.orEmpty()
    }

    fun getItemByPosition(position: Int): SuggestItem? {
        if (suggestItems.isEmpty()) return null
        return suggestItems[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int {
        return suggestItems.size
    }

    fun setData(data: List<SuggestItem>){
        suggestItems.clear()
        suggestItems.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (!constraint.isNullOrBlank()){
                    filterResults.values = suggestItems
                    filterResults.count = suggestItems.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0){
                    @Suppress("UNCHECKED_CAST")
                    suggestItems.addAll(results.values as List<SuggestItem>)
                    notifyDataSetChanged()
                }
                else{
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    class ServicesViewHolder(itemView: View?){
        val name: AppCompatTextView = itemView?.findViewById(R.id.suggest)!!
    }
}
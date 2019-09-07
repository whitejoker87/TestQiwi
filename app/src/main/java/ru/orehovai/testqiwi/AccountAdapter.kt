package ru.orehovai.testqiwi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.orehovai.testqiwi.model.Choice
import java.util.ArrayList
import android.widget.Filter

class AccountAdapter(
    internal var context: Context,
    internal var resource: Int,
    internal var textViewResourceId: Int,
    internal var items: List<Choice>
) :
    ArrayAdapter<Choice>(context, resource, textViewResourceId, items) {


//    internal var tempItems = listOf<Choice>()
//    internal var suggestions = mutableListOf<Choice>()

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    internal var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as Choice).title
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            if (constraint != null) {
//                suggestions.clear()
//                for (people in tempItems) {
//                    if (people.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
//                        suggestions.add(people)
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = suggestions
//                filterResults.count = suggestions.size
//                return filterResults
//            } else {
                return FilterResults()
//            }
        }
//
        override fun publishResults(constraint: CharSequence, results: FilterResults?) {
//            val filterList = results!!.values as ArrayList<*>
//            if (results.count > 0) {
//                clear()
//                for (people in filterList) {
//                    add(people as Choice)
//                    notifyDataSetChanged()
//                }
//            }
        }
    }

    init {
//        tempItems = ArrayList(items) // this makes the difference.
//        suggestions = ArrayList()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.adapter_autotext, parent, false)
        }
        val people = items[position]
        val lblName = view!!.findViewById(R.id.autoText) as TextView
        lblName.text = people.title
        return view
    }

    override fun getFilter(): Filter {
        return nameFilter
    }
}
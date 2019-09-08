package ru.orehovai.testqiwi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.orehovai.testqiwi.model.Choice
import android.widget.Filter



class AccountAdapter(
    internal var context: Context,
    internal var resource: Int,
    internal var textViewResourceId: Int,
    internal var items: List<Choice>
) :
    ArrayAdapter<Choice>(context, resource, textViewResourceId, items) {

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    internal var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return (resultValue as Choice).title
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults = FilterResults()
        override fun publishResults(constraint: CharSequence, results: FilterResults?) {}
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.adapter_autotext, parent, false)
        }
        val people = getItem(position)
        if (people != null) {
            val lblName = view!!.findViewById(textViewResourceId) as TextView
            lblName.text = people.title
//            lblName.setOnClickListener {
//                View.OnClickListener {
//
//                }
//            }
        }
        return view!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }
}
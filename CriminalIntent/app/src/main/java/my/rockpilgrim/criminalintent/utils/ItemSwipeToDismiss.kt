package my.rockpilgrim.criminalintent.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import my.rockpilgrim.criminalintent.AdapterDismissElement

class ItemSwipeToDismiss(private val adapter: AdapterDismissElement): ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> adapter.deleteView(viewHolder.adapterPosition)
            ItemTouchHelper.RIGHT -> adapter.deleteView(viewHolder.adapterPosition)
        }
    }
}
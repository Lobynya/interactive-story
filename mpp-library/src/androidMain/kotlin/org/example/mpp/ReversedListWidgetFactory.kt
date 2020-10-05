package org.example.mpp

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.units.adapter.UnitsRecyclerViewAdapter
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.core.View
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.applyBackgroundIfNeeded
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.androidId
import dev.icerock.moko.widgets.utils.bind

actual class ReversedListWidgetFactory actual constructor( private val background: Background<Fill.Solid>?) :
    ViewFactory<ListWidget<out WidgetSize>> {

    override fun <WS : WidgetSize> build(
        widget: ListWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val context = viewFactoryContext.androidContext
        val lifecycleOwner = viewFactoryContext.lifecycleOwner

        val layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            true
        )
        val unitsAdapter = UnitsRecyclerViewAdapter(lifecycleOwner)
        val recyclerView = RecyclerView(context).also {
            it.layoutManager = layoutManager
            it.adapter = unitsAdapter
            it.setHasFixedSize(true)

            it.id = widget.id.androidId
        }

        val resultView: View = recyclerView

        widget.items.bind(lifecycleOwner) { units ->
            val list = units.orEmpty()
            val onReachEnd = widget.onReachEnd
            unitsAdapter.units = when (onReachEnd) {
                null -> list
                else -> list.observedEnd(onReachEnd)
            }
        }

        with(resultView) {
            applyBackgroundIfNeeded(this@ReversedListWidgetFactory.background)
        }

        return ViewBundle(
            view = resultView,
            size = size,
            margins = null
        )
    }

    private fun List<TableUnitItem>.observedEnd(onReachEnd: () -> Unit): List<TableUnitItem> {
        if (this.isEmpty()) return this

        val lastWrapped = TableUnitItemWrapper(
            item = this.last(),
            onBind = onReachEnd
        )
        return this.dropLast(1).plus(lastWrapped)
    }

    private class TableUnitItemWrapper(
        private val item: TableUnitItem,
        private val onBind: () -> Unit
    ) : TableUnitItem by item {
        override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
            item.bindViewHolder(viewHolder)
            onBind()
        }
    }
}

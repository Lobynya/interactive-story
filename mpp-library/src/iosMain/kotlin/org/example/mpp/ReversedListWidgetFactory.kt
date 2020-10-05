package org.example.mpp

import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.units.createUnitTableViewDataSource
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.view.MarginValues
import dev.icerock.moko.widgets.style.view.PaddingValues
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.Edges
import dev.icerock.moko.widgets.utils.applyBackgroundIfNeeded
import dev.icerock.moko.widgets.utils.bind
import dev.icerock.moko.widgets.utils.setEventHandler
import dev.icerock.moko.widgets.utils.toEdgeInsets
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.UIRefreshControl
import platform.UIKit.UITableView
import platform.UIKit.UITableViewAutomaticDimension
import platform.UIKit.UITableViewCell
import platform.UIKit.UITableViewCellSeparatorStyle
import platform.UIKit.UITableViewStyle
import platform.UIKit.layoutMargins
import platform.UIKit.translatesAutoresizingMaskIntoConstraints
import platform.Foundation.NSIndexPath
import platform.UIKit.UITableViewScrollPosition
import platform.UIKit.indexPathForRow

actual class ReversedListWidgetFactory actual constructor(background: dev.icerock.moko.widgets.style.background.Background<dev.icerock.moko.widgets.style.background.Fill.Solid>?) :
    ViewFactory<ListWidget<out WidgetSize>>{
    override fun <WS : WidgetSize> build(
        widget: ListWidget<out WidgetSize>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val tableView = UITableView(
            frame = CGRectZero.readValue(),
            style = UITableViewStyle.UITableViewStylePlain
        )
        val unitDataSource = createUnitTableViewDataSource(tableView)

        with(tableView) {
            translatesAutoresizingMaskIntoConstraints = false
            dataSource = unitDataSource
            rowHeight = UITableViewAutomaticDimension
            estimatedRowHeight = UITableViewAutomaticDimension
            allowsSelection = false
            separatorStyle = UITableViewCellSeparatorStyle.UITableViewCellSeparatorStyleNone

            // reverse options
            transform = CGAffineTransformMakeRotation(angle = -kotlin.math.PI)
            showsVerticalScrollIndicator = false

            applyBackgroundIfNeeded(background)

            widget.onRefresh?.let { onRefresh ->
                refreshControl = UIRefreshControl().apply {
                    setEventHandler(UIControlEventValueChanged) {
                        onRefresh { endRefreshing() }
                    }
                }
            }
        }

        widget.items.bind { items ->
            val lastIndex = items.lastIndex
            val processedItems = items.mapIndexed { index: Int, tableUnitItem: TableUnitItem ->
                TableUnitItemWrapper(
                    item = tableUnitItem,
                    onBind = widget.onReachEnd?.takeIf { index == lastIndex }
                )
            }
            unitDataSource.unitItems = processedItems
        }

        return ViewBundle(
            view = tableView,
            size = size,
            margins = null
        )
    }

    private class TableUnitItemWrapper(
        private val item: TableUnitItem,
        private val onBind: (() -> Unit)? = null
    ) : TableUnitItem by item {
        override fun bind(tableViewCell: UITableViewCell) {
            item.bind(tableViewCell)
            onBind?.invoke()

            // reverse options
            tableViewCell.transform = CGAffineTransformMakeRotation(angle = kotlin.math.PI)
        }
    }
}
package org.example.mpp

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem
import org.example.mpp.entity.Content

class ContentUnitItem(
    val theme: Theme,
    val onClick: (Int) -> Unit,
    itemData: Content
) : WidgetsTableUnitItem<Content>(
    itemData.id.toLong(),
    itemData
) {
    override val reuseId = "DealerUnitItem"

    override fun createWidget(data: LiveData<Content>): UnitItemRoot {
        val clickableChild = theme.constraint(
            size = WidgetSize.WidthAsParentHeightWrapContent
        ) {
            with(theme) {

                val name = +text(
                    size = WidgetSize.WidthAsParentHeightWrapContent,
                    text = data.map { it.title.desc() as StringDesc}
                )

                constraints {
                    name topToTop root offset 16
                    name leftRightToLeftRight root offset 8
                    name bottomToBottom root offset 16
                }
            }
        }

        val clickable = theme.clickable(
            child = clickableChild,
            onClick = { onClick(data.value.id) }
        )

        return UnitItemRoot.from(clickable)
    }
}
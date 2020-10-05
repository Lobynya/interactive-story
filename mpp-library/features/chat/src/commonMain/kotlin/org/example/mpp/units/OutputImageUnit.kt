package org.example.mpp.units

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.widgets.ImageWidget
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.image
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem
import org.example.mpp.entity.Message

class OutputImageUnit(
    val theme: Theme,
    id: Long,
    val onClick: () -> Unit,
    itemData: Message
) : WidgetsTableUnitItem<Message>(
    id,
    itemData
) {
    override val reuseId = "OutputImageUnit"

    override fun createWidget(data: LiveData<Message>): UnitItemRoot {

        val widget = theme.constraint(
            size = WidgetSize.WidthAsParentHeightWrapContent
        ) {
            with(theme) {
                val insideWidget = +constraint(
                    id = OutputMessageUnit.Ids.MessageView,
                    size = WidgetSize.WidthAsParentHeightWrapContent
                ) {
                    val title = +text(
                        id = OutputMessageUnit.Ids.UserName,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.author as StringDesc }
                    )
                    val image = +image(
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(128f)),
                        image = data.map { Image.network(it.image) },
                        scaleType = ImageWidget.ScaleType.FIT
                    )

                    constraints {
                        title leftToLeft root offset 16
                        title topToTop root offset 8

                        image topToBottom title offset 8
                        image leftRightToLeftRight root offset 8
                        image bottomToBottom root offset 30
                    }
                }
                constraints {
                    insideWidget rightToRight root offset 0
                    insideWidget leftToLeft root offset 68
                    insideWidget topToTop root offset 8
                    insideWidget bottomToBottom root offset 8
                }
            }
        }

        return UnitItemRoot.from(theme.clickable(child = widget, onClick = onClick))
    }
}

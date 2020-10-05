package org.example.mpp.units

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.ConstraintWidget
import dev.icerock.moko.widgets.TextWidget
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.constraint
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import dev.icerock.moko.widgets.units.UnitItemRoot
import dev.icerock.moko.widgets.units.WidgetsTableUnitItem
import org.example.mpp.entity.Message

class OutputMessageUnit(
    val theme: Theme,
    id: Long,
    val onClick: () -> Unit,
    itemData: Message
) : WidgetsTableUnitItem<Message>(
    id,
    itemData
) {
    override val reuseId = "OutputMessageUnit"

    override fun createWidget(data: LiveData<Message>): UnitItemRoot {

        val widget = theme.constraint(
            size = WidgetSize.WidthAsParentHeightWrapContent
        ) {
            with(theme) {
                val insideWidget = +constraint(
                    id = Ids.MessageView,
                    size = WidgetSize.WidthAsParentHeightWrapContent
                ) {
                    val title = +text(
                        id = Ids.UserName,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.author.desc() as StringDesc }
                    )

                    val text = +text(
                        id = Ids.UserText,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.message.desc() as StringDesc}
                    )

                    constraints {
                        title leftToLeft root offset 16
                        title topToTop root offset 8

                        text topToBottom title offset 8
                        text bottomToBottom root offset 30
                        text leftRightToLeftRight root offset 16
                    }
                }
                constraints {
                    insideWidget leftToLeft root offset 64
                    insideWidget rightToRight root offset 16
                    insideWidget topToTop root offset 8
                    insideWidget bottomToBottom root offset 8
                }
            }
        }

        return UnitItemRoot.from(theme.clickable(child = widget, onClick = onClick))
    }

    object Ids {
        object MessageView : ConstraintWidget.Id
        object UserName : TextWidget.Id
        object UserText : TextWidget.Id
        object TimeText : TextWidget.Id
    }
}

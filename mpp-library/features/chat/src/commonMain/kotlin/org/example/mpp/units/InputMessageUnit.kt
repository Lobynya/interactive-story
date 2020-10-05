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

class InputMessageUnit(
    val theme: Theme,
    id: Long,
    val onClick: () -> Unit,
    itemData: Message
) : WidgetsTableUnitItem<Message>(
    id,
    itemData
) {
    override val reuseId = "InputMessageUnit"

    override fun createWidget(data: LiveData<Message>): UnitItemRoot {

        val widget = theme.constraint(
            size = WidgetSize.WidthAsParentHeightWrapContent
        ) {
            with(theme) {
                val insideWidget = +constraint(
                    id = Ids.MessageView,
                    size = WidgetSize.WidthAsParentHeightWrapContent
                ) {

                    val text = +text(
                        id = Ids.UserText,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.message.desc() } as LiveData<StringDesc>
                    )

                    val nameText = +text(
                        id = Ids.NameText,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.author.desc() } as LiveData<StringDesc>
                    )

                    constraints {
                        text bottomToBottom root offset 30
                        text leftRightToLeftRight root offset 16

                        nameText leftRightToLeftRight root offset 16
                        nameText topToTop root offset 12
                        nameText bottomToTop text offset 8
                    }
                }
                constraints {
                    insideWidget leftToLeft root offset 0
                    insideWidget rightToRight root offset 68
                    insideWidget topToTop root offset 8
                    insideWidget bottomToBottom root offset 8
                }
            }
        }

        return UnitItemRoot.from(theme.clickable(child = widget, onClick = onClick))
    }

    object Ids {
        object MessageView : ConstraintWidget.Id
        object UserText : TextWidget.Id
        object NameText : TextWidget.Id
        object TimeText : TextWidget.Id
    }
}

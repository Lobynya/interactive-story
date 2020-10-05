package org.example.mpp.units

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
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

class InputImageUnit(
    val theme: Theme,
    id: Long,
    val onClick: () -> Unit,
    itemData: Message
) : WidgetsTableUnitItem<Message>(
    id,
    itemData
) {
    override val reuseId = "InputImageUnit"

    override fun createWidget(data: LiveData<Message>): UnitItemRoot {

        val widget = theme.constraint(
            size = WidgetSize.WidthAsParentHeightWrapContent
        ) {
            with(theme) {
                val insideWidget = +constraint(
                    id = InputMessageUnit.Ids.MessageView,
                    size = WidgetSize.WidthAsParentHeightWrapContent
                ) {

                    val image = +image(
                        size = WidgetSize.Const(SizeSpec.MatchConstraint, SizeSpec.Exact(128f)),
                        image = data.map { Image.network(it.image) },
                        scaleType = ImageWidget.ScaleType.FIT
                    )

                    val nameText = +text(
                        id = InputMessageUnit.Ids.NameText,
                        size = WidgetSize.WidthAsParentHeightWrapContent,
                        text = data.map { it.author.desc() as StringDesc }
                    )

                    constraints {
                        image bottomToBottom root offset 30
                        image leftRightToLeftRight root offset 16

                        nameText leftRightToLeftRight root offset 16
                        nameText topToTop root offset 12
                        nameText bottomToTop image offset 8
                    }
                }
                constraints {
                    insideWidget leftToLeft root offset 68
                    insideWidget rightToRight root offset 16
                    insideWidget topToTop root offset 8
                    insideWidget bottomToBottom root offset 8
                }
            }
        }

        return UnitItemRoot.from(theme.clickable(child = widget, onClick = onClick))
    }
}

package org.example.mpp

import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.core.ViewFactory
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.view.WidgetSize

expect class ReversedListWidgetFactory(
    background: Background<Fill.Solid>?
) : ViewFactory<ListWidget<out WidgetSize>>

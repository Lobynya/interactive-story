package org.example.mpp

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.clickable
import dev.icerock.moko.widgets.container
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.list
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.style.view.WidgetSize
import org.example.mpp.entity.Message
import org.example.mpp.units.InputImageUnit
import org.example.mpp.units.InputMessageUnit
import org.example.mpp.units.OutputImageUnit
import org.example.mpp.units.OutputMessageUnit

class ChatScreen(
    private val theme: Theme,
    private val chatCategory: ListWidget.Category,
    private val createViewModel: (
        EventsDispatcher<ChatViewModel.EventsListener>
    ) -> ChatViewModel
) : WidgetScreen<Args.Parcel<ChatScreen.Args>>(), ChatViewModel.EventsListener, NavigationItem {

    override fun createContentWidget() = with(theme) {
        val viewModel: ChatViewModel = getViewModel {
            createViewModel(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(
            this@ChatScreen,
            this@ChatScreen
        )

        val clickableChild = container(size = WidgetSize.AsParent) {
            top {
                theme.clickable(
                    child = list(
                        category = chatCategory,
                        size = WidgetSize.AsParent,
                        items = viewModel.messagesLiveData.map { messageList ->
                            messageList.mapIndexed { id, message ->
                                createMessageUnit(
                                    message = message,
                                    id = (messageList.size - id).toLong(),
                                    onClick = viewModel::onTap
                                ) as TableUnitItem
                            }
                        },
                        id = Ids.List
                    ),
                    onClick = { viewModel.onTap() }
                )
            }
        }

        return@with theme.clickable(
            child = clickableChild,
            onClick = { viewModel.onTap() }
        )
    }

    private fun createMessageUnit(message: Message, id: Long, onClick: () -> Unit): TableUnitItem {
        return when {
            message.isRight && message.image.isNullOrEmpty() -> {
                OutputMessageUnit(theme = theme, itemData = message, id = id, onClick = onClick)
            }
            !message.isRight && message.image.isNullOrEmpty() -> {
                InputMessageUnit(theme = theme, itemData = message, id = id, onClick = onClick)
            }
            message.isRight && !message.image.isNullOrEmpty() -> {
                OutputImageUnit(theme = theme, itemData = message, id = id, onClick = onClick)
            }
            else -> {
                InputImageUnit(theme = theme, itemData = message, id = id, onClick = onClick)
            }
        }
    }

    override val navigationBar: NavigationBar = NavigationBar.None

    object Ids {
        object List : ListWidget.Id
    }

    @Parcelize
    data class Args(val contentId: Int) : Parcelable
}

package org.example.mpp

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.map
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.TableUnitItem
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.container
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.list
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.text
import org.example.mpp.entity.Content

class ContentsScreen(
    private val theme: Theme,
    private val routeToChatScreen: Route<Int>,
    private val createViewModel: (
        EventsDispatcher<ContentsViewModel.EventsListener>
    ) -> ContentsViewModel
) : WidgetScreen<Args.Empty>(), ContentsViewModel.EventsListener, NavigationItem {
    override fun createContentWidget() = with(theme) {
        val viewModel: ContentsViewModel = getViewModel {
            createViewModel(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(
            this@ContentsScreen,
            this@ContentsScreen
        )

        container(size = WidgetSize.AsParent) {
            top {
                list(
                    size = WidgetSize.AsParent,
                    items = viewModel.contensLiveData.map { contentList ->
                        contentList.map { content ->
                            ContentUnitItem(
                                theme = theme,
                                onClick = viewModel::onContentTap,
                                itemData = content
                            ) as TableUnitItem
                        }
                    },
                    id = Ids.List
                )
            }
        }
    }

    override fun routeToChat(id: Int) {
        routeToChatScreen.route(id)
    }

    override val navigationBar: NavigationBar = NavigationBar.None

    object Ids {
        object List : ListWidget.Id
    }
}

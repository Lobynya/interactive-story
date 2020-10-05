/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.mpp

import dev.icerock.moko.graphics.Color
import dev.icerock.moko.widgets.ClickableWidget
import dev.icerock.moko.widgets.ListWidget
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.factory.ClickableViewFactory
import dev.icerock.moko.widgets.factory.SystemListViewFactory
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.Screen
import dev.icerock.moko.widgets.screen.ScreenDesc
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.NavigationScreen
import dev.icerock.moko.widgets.screen.navigation.createPushRoute
import dev.icerock.moko.widgets.screen.navigation.createReplaceRoute
import dev.icerock.moko.widgets.screen.navigation.createRouter
import dev.icerock.moko.widgets.style.background.Background
import dev.icerock.moko.widgets.style.background.Fill
import dev.icerock.moko.widgets.style.view.PaddingValues

class App : BaseApplication() {
    override fun setup(): ScreenDesc<Args.Empty> {
        val theme = Theme() {
            factory[ListWidget.DefaultCategory] = SystemListViewFactory(
                dividerEnabled = false,
                padding = PaddingValues(top = 8f, bottom = 8f)
            )

            factory[ChatCategory] = ReversedListWidgetFactory(
                background = Background(Fill.Solid(Color(0xAAAAA10)))
            )


            factory[ClickableWidget.DefaultCategory] = ClickableViewFactory()
        }

        return registerScreen(RootNavigationScreen::class) {
            val router = createRouter()

            val chatScreen = registerScreen(ChatScreen::class) {
                ChatScreen(theme,
                    chatCategory = ChatCategory,
                    createViewModel = { ChatViewModel(it) })
            }
            val contentsScreen = registerScreen(ContentsScreen::class) {
                ContentsScreen(theme,
                    routeToChatScreen = router.createPushRoute(chatScreen) { ChatScreen.Args(it) },
                    createViewModel = { ContentsViewModel(it) })
            }
            RootNavigationScreen<ContentsScreen>(contentsScreen, router)
        }
    }

    object ChatCategory : ListWidget.Category
}


class RootNavigationScreen<T>(
    initialScreen: TypedScreenDesc<Args.Empty, T>,
    router: Router
) : NavigationScreen<T>(initialScreen, router) where T : Screen<Args.Empty>, T : NavigationItem

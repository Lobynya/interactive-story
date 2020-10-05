package org.example.mpp

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.example.mpp.entity.Content

class ContentsViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val contensLiveData = MutableLiveData<List<Content>>(
        listOf(
            Content(
                id = 0,
                title = "Глава 1"
            ),
            Content(
                id = 0,
                title = "Глава 2"
            ),
            Content(
                id = 0,
                title = "Глава 3"
            ),
            Content(
                id = 0,
                title = "Глава 4"
            ),
            Content(
                id = 0,
                title = "Глава 5"
            )
        )
    )

    fun onContentTap(id: Int){
        eventsDispatcher.dispatchEvent { routeToChat(id) }
    }

    interface EventsListener {
        fun routeToChat(id: Int)
    }
}
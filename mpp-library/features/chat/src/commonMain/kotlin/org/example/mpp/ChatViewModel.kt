package org.example.mpp

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import org.example.mpp.entity.Content
import org.example.mpp.entity.Message

class ChatViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel() {

    val story = listOf<Message>(
        Message(
            author = "Мусташе",
            message = "test message",
            image = "",
            isRight = false,
            id = 0L
        ),
        Message(
            author = "Хенрик",
            message = "test message 2",
            image = "",
            isRight = true,
            id = 1L
        ),
        Message(
            author = "Мусташе",
            message = "test message 3",
            image = "",
            isRight = false,
            id = 2L
        )
    )

    val messagesLiveData = MutableLiveData<List<Message>>(
        story.subList(0, 1)
    )

    fun onTap() {
        val currentSize = messagesLiveData.value.size
        if (currentSize != story.size) {
            messagesLiveData.value = story.subList(0, currentSize + 1).reversed()
        }
    }

    interface EventsListener {
    }
}
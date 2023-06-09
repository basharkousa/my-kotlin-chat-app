package com.bashar.mychatapp.src.ui.fragments.conversationFragment

import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashar.mychatapp.src.data.Repository
import com.bashar.mychatapp.src.data.models.Chat
import com.bashar.mychatapp.src.data.models.Message
import com.bashar.mychatapp.src.data.models.User
import com.bashar.mychatapp.src.utils.MutableListLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    var chat: Chat? = null

    var messageList: MutableListLiveData<Message?>? = MutableListLiveData(mutableListOf())

    var receiverUser: User? = null

    init {
        chat = savedStateHandle.get<Chat>("Chat")
        receiverUser = chat?.receiver
        println("CONVERSATION_FRAGMENT: ${chat?.receiver?.name}")
        getMessages(chat?.id ?: 1)
    }

    private fun getMessages(chatId: Int) = viewModelScope.launch {

        repository.getMessages(chatId).collect { messages ->
            messageList?.clear()
            messageList?.addAll(messages)
            messages.forEach {
                println(it)
            }
            println("MessagesSize: ${messageList?.size}")

        }
    }

    val newMessageText = MutableLiveData<String?>("")
    fun sendMessagePressed() = viewModelScope.launch {
        if (!newMessageText.value.isNullOrBlank()) {

            println("NEW_MESSAGE: ${newMessageText.value}")

            val newMsg = Message(
                message = newMessageText.value!!,
                senderId = chat?.sender?.id!!,
                receiverId = chat?.receiver?.id!!,
                timestamp = System.currentTimeMillis(),
                type = "msg",
                chatId = chat!!.id,
            )

            println("NEW_MESSAGE: ${newMsg}")
            repository.insertMessage(newMsg)
            newMessageText.value = ""
        }
    }

    val isRecording = MutableLiveData<Boolean>(false)
    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String? = null

    fun startRecording(externalCacheDir: File?) = viewModelScope.launch {
        isRecording.value?.let {
            isRecording.value = !it
        }
        mediaRecorder = MediaRecorder()
        outputFile = "${externalCacheDir?.absolutePath}/recording${System.currentTimeMillis()}.mp3"

        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile)
            prepare()
            start()
        }
//        mediaRecorder.setMaxDuration()
    }

    fun stopRecording() = viewModelScope.launch {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        println("NEW_VOICE_MESSAGE: $outputFile")

        isRecording.value?.let {
            val newVoiceMessage = Message(
                message = outputFile!!,
                senderId = chat?.sender?.id!!,
                receiverId = chat?.receiver?.id!!,
                timestamp = System.currentTimeMillis(),
                type = "rec",
                chatId = chat!!.id,
            )
            repository.insertMessage(newVoiceMessage)

            isRecording.value = !it
        }

    }


    private var mediaPlayer: MediaPlayer? = null

    fun startPlaying() = viewModelScope.launch {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(outputFile)
            prepare()
            start()
        }
    }

    fun stopPlaying() = viewModelScope.launch {

        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null


    }


}
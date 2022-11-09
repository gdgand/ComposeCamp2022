package com.codelab.basics.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.basics.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {
    init { loadData() }

    private val _homeDatas = mutableStateOf<List<HomeData>>(emptyList())
    val homeDatas = _homeDatas

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(100)
            val datas = mutableListOf<HomeData>()
            for(i in 0..10) {
                val data = HomeData(
                    index = i,
                    title = UiText.DynamicString("Hello").value,
                    content = UiText.DynamicString("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").value
                )
                datas.add(data)
            }
            withContext(Dispatchers.Main) {
                _homeDatas.value = datas
            }
        }
    }

    companion object {
        val previewData = HomeData(
            index = 2,
            title = UiText.DynamicString("Hello").value,
            content = UiText.DynamicString("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.").value
        )
    }
}
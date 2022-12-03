package androidx.compose.samples.crane.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

/**
 * Created by leeseulbee on 2022/11/24.
 */

class EditableUserInputState(private val hint: String, initialText: String) {

    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    companion object {
        // Saver는 객체를 Saveable 상태인 것으로 변환하는 방법을 설명합니다.
        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.hint, it.text) },
            restore = {
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1],
                )
            }
        )
    }
}
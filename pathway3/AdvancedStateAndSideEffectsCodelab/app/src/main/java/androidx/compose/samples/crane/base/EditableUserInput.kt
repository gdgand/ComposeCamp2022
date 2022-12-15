/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.samples.crane.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.samples.crane.ui.captionTextStyle
import androidx.compose.ui.graphics.SolidColor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Composable의 내부 상태를 담당하는 상태 홀더를 만들어 모든 상태 변경사항을 한 곳으로 중앙화
// -> 쉽게 동기화, 관련 로직도 모두 단일 클래스로 그룹화 가능
class EditableUserInputState(private val hint: String, initialText: String) {

    // Compose가 값의 변경을 추적하고 변경될 때 다시 작성하도록 mutableStateOf를 사용하는 것이 중요함
    // var -> 클래스 외부에서 변경 가능함 (초기화는 initialText)
    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    companion object {
        // listSaver or mapSaver와 같은 기존의 Compose API 사용 가능
        // Saver 정의는 함께 작동하는 클래스와 가깝게 배치하는 것이 좋음 (정적 엑세스 -> companion object)
        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.hint, it.text) },
            restore = {
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1]
                )
            }
        )
    }
}

@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }

@Composable
fun CraneEditableUserInput(
    state: EditableUserInputState = rememberEditableUserInputState(hint = ""),
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null
) {
    // TODO Codelab: Encapsulate this state in a state holder
    CraneBaseUserInput(
        caption = caption,
        tintIcon = { !state.isHint },
        showCaption = { !state.isHint },
        vectorImageId = vectorImageId
    ) {
        BasicTextField(
            value = state.text,
            onValueChange = { state.text = it },
            textStyle = if (state.isHint) {
                captionTextStyle.copy(color = LocalContentColor.current)
            } else {
                MaterialTheme.typography.body1.copy(color = LocalContentColor.current)
            },
            cursorBrush = SolidColor(LocalContentColor.current)
        )
    }
}

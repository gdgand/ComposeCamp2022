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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.samples.crane.ui.captionTextStyle
import androidx.compose.ui.graphics.SolidColor

//text는 CraneEditableUserInput에서와 마찬가지로 String 유형의 변경 가능한 상태입니다. Compose가 값의 변경을 추적하고 변경될 때 다시 작성하도록 mutableStateOf를 사용하는 것이 중요합니다.
//text는 var이며, 클래스 외부에서 직접 변형할 수 있습니다.
//클래스는 text를 초기화하는 데 사용되는 종속 항목으로 initialText를 사용합니다.
//text가 힌트인지 여부를 확인하는 로직은 주문형 검사를 수행하는 isHint 속성에 있습니다.
class EditableUserInputState(private val hint: String, initialText: String) {

    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    companion object {
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



@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }


@Composable
fun CraneEditableUserInput(
    state: EditableUserInputState = rememberEditableUserInputState(""),
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null
    //onInputChanged: (String) -> Unit
    //상태를 끌어올릴 수 있기 때문에 호출자가 입력이 변경되었는지 알고자 하는 경우 상태를 제어하고 이 함수에 상태를 전달할 수 있습니다.
) {
/*    // TODO Codelab: Encapsulate this state in a state holder
    var textState by remember { mutableStateOf(hint) }
    val isHint = { textState == hint }*/

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


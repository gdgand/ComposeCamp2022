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

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.samples.crane.ui.captionTextStyle
import androidx.compose.ui.graphics.SolidColor

class EditableUserInputState(private val hint: String, initialText: String) {

    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    companion object {
        private const val TAG = "EditableUserInputState"

        val Saver: Saver<EditableUserInputState, *> = listSaver(
            save = {
                Log.d(TAG, "save ${it.hint}, ${it.text}")
                listOf(it.hint, it.text)
            },
            restore = {
                Log.d(TAG, "restore $it")
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1]
                )
            }
        )
    }
}

//TODO: 이건 왜 Composable Function 이여야 하는가?
// remember는 Composition에 저장되어야 한다. (이게 무슨 의미지?)
@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) { // activity 재생성에 살아남을 수는 없다.
        EditableUserInputState(hint, hint)
    }

@Composable
fun CraneEditableUserInput(
    // state의 변경을 알고 싶으면 caller가 hoist 해서 사용하자.
    state: EditableUserInputState = rememberEditableUserInputState(""),
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
) {
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

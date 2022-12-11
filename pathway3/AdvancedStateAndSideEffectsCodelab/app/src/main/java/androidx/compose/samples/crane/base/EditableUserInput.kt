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

@Composable
fun CraneEditableUserInput(
    //hint, caption 모두 아이콘 옆의 선택적 텍스트에 해당
    //hint: String,
    //상태홀더 사용하기
    //대신 호출자가 CraneEditableUserInput의 상태를 제어할 수 있도록 EditableUserInputState를 끌어올리고자 합니다.
    state: EditableUserInputState = rememberEditableUserInputState(""),
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    //onInputChanged: (String) -> Unit,

) {
    // TODO Codelab: Encapsulate this state in a state holder
    //var textState by remember { mutableStateOf(hint) }
    //val isHint = { textState == hint }

    CraneBaseUserInput(
        caption = caption,
        tintIcon = { !state.isHint },
        showCaption = { !state.isHint },
        vectorImageId = vectorImageId
    ) {
        //단점
        //TextField 값은 끌어올려지지 않아 외부에서 제어할 수 없으므로 테스트가 더 어렵습니다.
        //이 컴포저블의 논리가 더 복잡해지고 내부 상태가 더 쉽게 동기화되지 않을 수 있습니다.
        //해결
        //내부 상태를 담당하는 상태 홀더를 만들어 모든 상태 변경사항을 한 곳으로 중앙화
        BasicTextField(
            value = state.text,
            onValueChange = {
                state.text = it
                //if (!isHint()) onInputChanged(textState)
            },
            textStyle = if (state.isHint) {
                captionTextStyle.copy(color = LocalContentColor.current)
            } else {
                MaterialTheme.typography.body1.copy(color = LocalContentColor.current)
            },
            cursorBrush = SolidColor(LocalContentColor.current)
        )
    }
}

//상태홀더
class EditableUserInputState(private val hint: String, initialText: String) {
    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    companion object {
        //EditableUserInputState의 인스턴스를 저장하고 복원하는 구현 세부정보로 listSaver를 사용
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

//상태홀더 항상 기억하기
//rememberSaveable은 Bundle 내에
// 저장할 수 있는 객체에 대한 추가 작업 없이
// 이 작업을 모두 수행합니다.
// 프로젝트에서 만든 EditableUserInputState 클래스는
// 그렇지 않습니다.
// 따라서 Saver를 사용하여
// 이 클래스의 인스턴스를 저장 및 복원하는 방법을
// rememberSaveable에 알려야 합니다.
@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    rememberSaveable(hint, saver = EditableUserInputState.Saver) {
        EditableUserInputState(hint, hint)
    }
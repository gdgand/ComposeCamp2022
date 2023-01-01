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


/**
 * StateFul Composable은 hint 및 caption과 같은 일부 매개변수를 가져온다.
 * 목적지 검색시 caption To 표시.
 * textState를 업데이트 하고 표시된 항목이 힌트에 해당하는지 확인하는 로직은 모두 해당 Composable에 있음.
 * 단점은 다음과 같음.
 * 1. TextField 값은 끌어올려지지 않아 외부에서 제어할 수 없으므로 테스트가 더 어려움.
 * 2. 이 Composable이 더 복잡해지고 내부 상태가 더 쉽게 동기화되지 않을 수 있음.
 * -> 따라서, 상태 홀더를 만들고 상태 변경사항을 한곳으로 모은다. 그러므로 유연성, 제어 가능성이 Up!!
 */
@Composable
fun CraneEditableUserInput(
    // 상태를 제어 하기위해 끌어올리기 사용에 따른 상태 매개변수 추가
    // 내부 상태 대신 끌어올린 상태를 사용하도록 리팩토링
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

// 상태 홀더로 일반 클래스 생성
class EditableUserInputState(private val hint: String, initialText: String) {
    // text: String 유형의 변경 가능한 상태
    /**
     * text: 클래스 외부에서 직접 변형 가능
     * 클래스는 text를 초기화하는데 사용되는 종속 항목으로 initialText 사용
     * isHint: text가 힌트인지 여부를 확인하는 로직 검사
     */
    var text by mutableStateOf(initialText)

    val isHint: Boolean
        get() = text == hint

    /**
     * 맞춤 Saver 제작
     * Saver는 객체를 Saveable 상태인 것으로 변환하는 방법을 설명.
     * Saver를 구현하기 위해서 다음과 같은 함수를 재정의
     * save : 원래 값을 저장 가능한 값으로 변환
     * restore: 복원된 값을 원본 클래스의 인스턴스로 변환.
     */

    companion object {
        // listSaver를 사용해 작성하는 코드의 양을 줄임
        // rememberEditableUserInputState 메서드 내에서 remember 대신 rememberSaveable에서 Saver 사용
        val saver : Saver<EditableUserInputState, *> = listSaver(
            save = { listOf(it.hint,it.text) },
            restore = {
                EditableUserInputState(
                    hint = it[0],
                    initialText = it[1],
                )
            }
        )
    }
}

/**
 * 상태 홀더 기억하기
 * 상태 홀더가 항상 기억되어야 Composition에서 유지되고 매번 새로 만들 필요가 없음.
 * remember 처리를 해줌으로 다시 생성시 유지 x
 * 내부적으로 저장된 인스턴스 상태 메커니즘 사용.
 * rememberSaveable은 Bundle내에 저장할 수 있는 객체에 대한 추가 작업 없이 모두 이 작업을 모두 수행.
 * 따라서 Saver를 사용하여 이 클래스의 인스턴스를 저장 및 복원하는 방법을 rememberSaveable에 알려야 한다.
 */

// remember 대신 rememberSaveable에서 이 Saver를 사용
@Composable
fun rememberEditableUserInputState(hint: String): EditableUserInputState =
    // rememberSaveable 메서드 사용시 EditableUserInput에서 기억된 상태가 프로세스 및 활동 재생성시 유지.
    rememberSaveable(hint, saver = EditableUserInputState.saver){
        EditableUserInputState(hint, hint)
    }

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.lifecycle

import androidx.activity.ComponentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.builtins.serializer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class SerializationTest {

    @get:Rule val activityTestRuleScenario = ActivityScenarioRule(ComponentActivity::class.java)

    @Test
    fun simpleRestore() {
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            var value by viewModel.savedStateHandle.saved { 1 }
            assertThat(value).isEqualTo(1)
            value = 2
            assertThat(value).isEqualTo(2)
        }
        activityTestRuleScenario.scenario.recreate()
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            var value: Int by
                viewModel.savedStateHandle.saved { error("Unexpected initializer call") }
            assertThat(value).isEqualTo(2)
        }
    }

    @Test
    fun explicitKey() {
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            var value by viewModel.savedStateHandle.saved(key = "foo") { 1 }
            assertThat(value).isEqualTo(1)
            value = 2
            assertThat(value).isEqualTo(2)
        }
        activityTestRuleScenario.scenario.recreate()
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            var value: Int by
                viewModel.savedStateHandle.saved(key = "foo") {
                    error("Unexpected initializer call")
                }
            assertThat(value).isEqualTo(2)
        }
    }

    @Test
    fun explicitSerializer() {
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val value by viewModel.savedStateHandle.saved(serializer = Int.serializer()) { 1 }
            assertThat(value).isEqualTo(1)
        }
        activityTestRuleScenario.scenario.recreate()
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val value: Int by
                viewModel.savedStateHandle.saved(serializer = Int.serializer()) {
                    error("Unexpected initializer call")
                }
            assertThat(value).isEqualTo(1)
        }
    }

    @Test
    fun explicitKeyAndSerializer() {
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val value by
                viewModel.savedStateHandle.saved(key = "foo", serializer = Int.serializer()) { 1 }
            assertThat(value).isEqualTo(1)
        }
        activityTestRuleScenario.scenario.recreate()
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val value: Int by
                viewModel.savedStateHandle.saved(key = "foo", serializer = Int.serializer()) {
                    error("Unexpected initializer call")
                }
            assertThat(value).isEqualTo(1)
        }
    }

    @Test
    fun duplicateKeys() {
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val serializable by viewModel.savedStateHandle.saved(key = "foo") { 1 }
            assertThat(serializable).isEqualTo(1)
            val duplicate by viewModel.savedStateHandle.saved(key = "foo") { 2 }
            assertThat(duplicate).isEqualTo(2)
            // The value is from the initializer.
            assertThat(serializable).isEqualTo(1)
        }
        activityTestRuleScenario.scenario.recreate()
        activityTestRuleScenario.scenario.onActivity { activity ->
            val viewModel: MyViewModel = ViewModelProvider(activity)[MyViewModel::class]
            val serializable: Int by
                viewModel.savedStateHandle.saved(key = "foo") {
                    error("Unexpected initializer call")
                }
            assertThat(serializable).isEqualTo(2)
            val duplicate: Int by
                viewModel.savedStateHandle.saved(key = "foo") {
                    error("Unexpected initializer call")
                }
            assertThat(duplicate).isEqualTo(2)
            assertThat(serializable).isEqualTo(2)
        }
    }
}

class MyViewModel(val savedStateHandle: SavedStateHandle) : ViewModel()

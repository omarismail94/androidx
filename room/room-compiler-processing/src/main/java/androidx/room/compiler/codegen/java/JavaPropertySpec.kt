/*
 * Copyright 2022 The Android Open Source Project
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

package androidx.room.compiler.codegen.java

import androidx.room.compiler.codegen.JPropertySpec
import androidx.room.compiler.codegen.JPropertySpecBuilder
import androidx.room.compiler.codegen.XAnnotationSpec
import androidx.room.compiler.codegen.XCodeBlock
import androidx.room.compiler.codegen.XPropertySpec

internal class JavaPropertySpec(internal val actual: JPropertySpec) : JavaLang(), XPropertySpec {

    override val name: String = actual.name

    internal class Builder(internal val actual: JPropertySpecBuilder) :
        JavaLang(), XPropertySpec.Builder {

        override fun addAnnotation(annotation: XAnnotationSpec) = apply {
            require(annotation is JavaAnnotationSpec)
            actual.addAnnotation(annotation.actual)
        }

        override fun initializer(initExpr: XCodeBlock) = apply {
            require(initExpr is JavaCodeBlock)
            actual.initializer(initExpr.actual)
        }

        override fun getter(code: XCodeBlock) = apply {
            require(code is JavaCodeBlock)
            error("Adding a property getter when code language is Java is not supported.")
        }

        override fun build() = JavaPropertySpec(actual.build())
    }
}

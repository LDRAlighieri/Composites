/*
 * Copyright 2023 Vladimir Raupov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ldralighieri.composites.carbon.processor.ext

import ru.ldralighieri.composites.carbon.core.ArgumentData

private fun List<ArgumentData>.getArguments(transform: (ArgumentData) -> CharSequence): String {
    val notNullable: List<ArgumentData> = filterNot { it.isNullable }
    return if (notNullable.isEmpty()) ""
    else notNullable.joinToString(prefix = "/", separator = "/", transform = transform)
}

private fun List<ArgumentData>.getOptionalArguments(
    transform: (ArgumentData) -> CharSequence
): String {
    val nullable: List<ArgumentData> = filter { it.isNullable }
    return if (nullable.isEmpty()) ""
    else nullable.joinToString(prefix = "?", separator = "&", transform = transform)
}

internal fun List<ArgumentData>.getPathArguments(): String =
    getArguments { "{${it.name}}" }

internal fun List<ArgumentData>.getCreateArguments(): String =
    getArguments { "\$${it.name}" }

internal fun List<ArgumentData>.getOptionalPathArguments(): String =
    getOptionalArguments { "${it.name}={${it.name}}" }

internal fun List<ArgumentData>.getOptionalCreateArguments(): String =
    getOptionalArguments { "${it.name}=\$${it.name}" }

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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import ru.ldralighieri.composites.carbon.processor.model.booleanNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.booleanTypeName
import ru.ldralighieri.composites.carbon.processor.model.floatNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.floatTypeName
import ru.ldralighieri.composites.carbon.processor.model.intNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.intTypeName
import ru.ldralighieri.composites.carbon.processor.model.longNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.longTypeName
import ru.ldralighieri.composites.carbon.processor.model.navTypeBooleanClassName
import ru.ldralighieri.composites.carbon.processor.model.navTypeFloatClassName
import ru.ldralighieri.composites.carbon.processor.model.navTypeIntClassName
import ru.ldralighieri.composites.carbon.processor.model.navTypeLongClassName
import ru.ldralighieri.composites.carbon.processor.model.navTypeStringClassName
import ru.ldralighieri.composites.carbon.processor.model.stringNullableTypeName
import ru.ldralighieri.composites.carbon.processor.model.stringTypeName

internal fun TypeName.toNavTypeClassName(): ClassName =
    when(this) {
        intTypeName, intNullableTypeName -> navTypeIntClassName
        longTypeName, longNullableTypeName -> navTypeLongClassName
        floatTypeName, floatNullableTypeName -> navTypeFloatClassName
        booleanTypeName, booleanNullableTypeName -> navTypeBooleanClassName
        stringTypeName, stringNullableTypeName -> navTypeStringClassName
        else -> navTypeStringClassName
    }

internal fun TypeName.cast(value: String): Any =
    String.format(
        format = "%s%s",
        when(this) {
            intTypeName -> value.toInt()
            longTypeName -> value.toLong()
            floatTypeName -> value.toFloat()
            booleanTypeName -> value.toBoolean()
            stringTypeName -> "\"$value\""
            else -> "\"$value\""
        },
        when(this) {
            longTypeName -> "L"
            floatTypeName -> "F"
            else -> ""
        }
    )

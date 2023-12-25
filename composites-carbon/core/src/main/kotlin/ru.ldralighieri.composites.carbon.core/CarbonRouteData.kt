package ru.ldralighieri.composites.carbon.core

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName

data class CarbonRouteData(
    val route: String,
    val deeplinkSchema: String,
    val fileName: String,
    val className: ClassName,
    val packageName: String,
    val arguments: List<ArgumentData>,
)

data class ArgumentData(
    val name: String,
    val typeName: TypeName,
    val isNullable: Boolean,
)

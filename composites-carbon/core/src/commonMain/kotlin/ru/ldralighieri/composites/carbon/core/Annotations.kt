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

package ru.ldralighieri.composites.carbon.core

/**
 * Route class annotation
 *
 * @param route Navigation route
 * @param deeplinkSchema Route deeplink schema
 */
@Target(AnnotationTarget.CLASS)
public annotation class CarbonRoute(val route: String, val deeplinkSchema: String = "")

/**
 * Annotation for assigning default value without reflection
 *
 * @param value Default value
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
public annotation class DefaultValue(val value: String)

/*
 * Copyright 2021 The Android Open Source Project
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
package androidx.compose.material3

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.internal.childSemantics
import androidx.compose.material3.internal.rememberAnimatedShape
import androidx.compose.material3.tokens.FilledIconButtonTokens
import androidx.compose.material3.tokens.FilledTonalIconButtonTokens
import androidx.compose.material3.tokens.LargeIconButtonTokens
import androidx.compose.material3.tokens.MediumIconButtonTokens
import androidx.compose.material3.tokens.MotionSchemeKeyTokens
import androidx.compose.material3.tokens.OutlinedIconButtonTokens
import androidx.compose.material3.tokens.SmallIconButtonTokens
import androidx.compose.material3.tokens.StandardIconButtonTokens
import androidx.compose.material3.tokens.XLargeIconButtonTokens
import androidx.compose.material3.tokens.XSmallIconButtonTokens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.jvm.JvmInline

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design standard icon button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Standard icon button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/standard-icon-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Simple Usage
 *
 * @sample androidx.compose.material3.samples.IconButtonSample
 *
 * IconButton with a color tint
 *
 * @sample androidx.compose.material3.samples.TintedIconButtonSample
 * @param onClick called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.iconButtonVibrantColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Deprecated(
    message = "Use overload with `shape`",
    replaceWith =
        ReplaceWith(
            "IconButton(onClick, modifier, enabled, colors, interactionSource, shape, content)"
        ),
    level = DeprecationLevel.HIDDEN
)
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick,
        modifier,
        enabled,
        colors,
        interactionSource,
        IconButtonDefaults.standardShape,
        content
    )
}

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design standard icon button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Standard icon button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/standard-icon-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Simple Usage
 *
 * @sample androidx.compose.material3.samples.IconButtonSample
 *
 * IconButton with a color tint
 *
 * @sample androidx.compose.material3.samples.TintedIconButtonSample
 *
 * Small-sized narrow round shape IconButton
 *
 * @sample androidx.compose.material3.samples.XSmallNarrowSquareIconButtonsSample
 *
 * Medium / default size round-shaped icon button
 *
 * @sample androidx.compose.material3.samples.MediumRoundWideIconButtonSample
 * @param onClick called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.iconButtonVibrantColors] and
 *   [IconButtonDefaults.iconButtonColors] .
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape the [Shape] of this icon button.
 * @param content the content of this icon button, typically an [Icon]
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = IconButtonDefaults.standardShape,
    content: @Composable () -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Box(
        modifier =
            modifier
                .minimumInteractiveComponentSize()
                .size(IconButtonDefaults.smallContainerSize())
                .clip(shape)
                .background(color = colors.containerColor(enabled), shape = shape)
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = ripple()
                )
                .childSemantics()
                .interactionSourceData(interactionSource),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = colors.contentColor(enabled)
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design standard icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Standard icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/standard-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.IconToggleButtonSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.iconToggleButtonVibrantColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Deprecated(
    message = "Use overload with `shape`",
    replaceWith =
        ReplaceWith(
            "IconToggleButton(checked, onCheckedChange, modifier, enabled, colors," +
                " interactionSource, shape, content)"
        ),
    level = DeprecationLevel.HIDDEN
)
@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    IconToggleButton(
        checked,
        onCheckedChange,
        modifier,
        enabled,
        colors,
        interactionSource,
        IconButtonDefaults.standardShape,
        content
    )
}

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design standard icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Standard icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/standard-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.IconToggleButtonSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.iconToggleButtonVibrantColors] and
 *   [IconButtonDefaults.iconToggleButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param shape the [Shape] of this icon button.
 * @param content the content of this icon button, typically an [Icon]
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = IconButtonDefaults.standardShape,
    content: @Composable () -> Unit
) =
    IconToggleButtonImpl(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
        shape = shape,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design standard icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Standard icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/standard-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.IconToggleButtonWithAnimatedShapeSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param shapes the [IconButtonShapes] that the icon toggle button will morph between depending on
 *   the user's interaction with the icon toggle button.
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.iconToggleButtonVibrantColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@ExperimentalMaterial3ExpressiveApi
@Composable
fun IconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shapes: IconButtonShapes,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonVibrantColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    IconToggleButtonImpl(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        shape = shapeForInteraction(checked, shapes, interactionSource),
        colors = colors,
        interactionSource = interactionSource,
        content = content
    )
}

@ExperimentalMaterial3ExpressiveApi
@Composable
private fun IconToggleButtonImpl(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonVibrantColors(),
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = IconButtonDefaults.standardShape,
    content: @Composable () -> Unit
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    Box(
        modifier =
            modifier
                .minimumInteractiveComponentSize()
                .size(IconButtonDefaults.smallContainerSize())
                .clip(shape)
                .background(color = colors.containerColor(enabled, checked).value)
                .toggleable(
                    value = checked,
                    onValueChange = onCheckedChange,
                    enabled = enabled,
                    role = Role.Checkbox,
                    interactionSource = interactionSource,
                    indication = ripple()
                )
                .interactionSourceData(interactionSource),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = colors.contentColor(enabled, checked).value
        CompositionLocalProvider(LocalContentColor provides contentColor, content = content)
    }
}

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled icon button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled icon button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-icon-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Filled icon button sample:
 *
 * @sample androidx.compose.material3.samples.FilledIconButtonSample
 * @param onClick called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container
 * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun FilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Toggleable filled icon button sample:
 *
 * @sample androidx.compose.material3.samples.FilledIconToggleButtonSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconToggleButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun FilledIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconToggleButtonColors = IconButtonDefaults.filledIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Toggleable filled icon button sample:
 *
 * @sample androidx.compose.material3.samples.FilledIconToggleButtonWithAnimatedShapeSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param shapes the [IconButtonShapes] that the icon toggle button will morph between depending on
 *   the user's interaction with the icon toggle button.
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconToggleButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@ExperimentalMaterial3ExpressiveApi
@Composable
fun FilledIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shapes: IconButtonShapes,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.filledIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shapes = shapes,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled tonal icon button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled tonal icon button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-tonal-icon-button.png)
 *
 * A filled tonal icon button is a medium-emphasis icon button that is an alternative middle ground
 * between the default [FilledIconButton] and [OutlinedIconButton]. They can be used in contexts
 * where the lower-priority icon button requires slightly more emphasis than an outline would give.
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Filled tonal icon button sample:
 *
 * @sample androidx.compose.material3.samples.FilledTonalIconButtonSample
 * @param onClick called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container
 * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun FilledTonalIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled tonal icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled tonal icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-tonal-icon-toggle-button.png)
 *
 * A filled tonal toggle icon button is a medium-emphasis icon button that is an alternative middle
 * ground between the default [FilledIconToggleButton] and [OutlinedIconToggleButton]. They can be
 * used in contexts where the lower-priority icon button requires slightly more emphasis than an
 * outline would give.
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Toggleable filled tonal icon button sample:
 *
 * @sample androidx.compose.material3.samples.FilledTonalIconToggleButtonSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconToggleButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun FilledTonalIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconToggleButtonColors = IconButtonDefaults.filledTonalIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design filled tonal icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Filled tonal icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/filled-tonal-icon-toggle-button.png)
 *
 * A filled tonal toggle icon button is a medium-emphasis icon button that is an alternative middle
 * ground between the default [FilledIconToggleButton] and [OutlinedIconToggleButton]. They can be
 * used in contexts where the lower-priority icon button requires slightly more emphasis than an
 * outline would give.
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * Toggleable filled tonal icon button with animatable shape sample:
 *
 * @sample androidx.compose.material3.samples.FilledTonalIconToggleButtonWithAnimatedShapeSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param shapes the [IconButtonShapes] that the icon toggle button will morph between depending on
 *   the user's interaction with the icon toggle button.
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.filledIconToggleButtonColors].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@ExperimentalMaterial3ExpressiveApi
@Composable
fun FilledTonalIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shapes: IconButtonShapes,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.filledTonalIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shapes = shapes,
        colors = colors,
        border = null,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design outlined icon button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Outlined icon button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/outlined-icon-button.png)
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * Use this "contained" icon button when the component requires more visual separation from the
 * background.
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. The outlined icon
 * button has an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.OutlinedIconButtonSample
 *
 * Large-sized uniform rounded shape
 *
 * @sample androidx.compose.material3.samples.LargeRoundUniformOutlinedIconButtonSample
 * @param onClick called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container and border (when [border] is not
 *   null)
 * @param colors [IconButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.outlinedIconButtonVibrantColors] and
 *   [IconButtonDefaults.outlinedIconButtonColors].
 * @param border the border to draw around the container of this icon button. Pass `null` for no
 *   border. See [IconButtonDefaults.outlinedIconButtonBorder] and
 *   [IconButtonDefaults.outlinedIconButtonBorder].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun OutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.outlinedShape,
    colors: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(),
    border: BorderStroke? = IconButtonDefaults.outlinedIconButtonBorder(enabled),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design outlined icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Outlined icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/outlined-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.OutlinedIconToggleButtonSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param shape defines the shape of this icon button's container and border (when [border] is not
 *   null)
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.outlinedIconToggleButtonVibrantColors] and
 *   [IconButtonDefaults.outlinedIconToggleButtonColors].
 * @param border the border to draw around the container of this icon button. Pass `null` for no
 *   border. See [IconButtonDefaults.outlinedIconToggleButtonVibrantBorder] and
 *   [IconButtonDefaults.outlinedIconToggleButtonBorder].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@Composable
fun OutlinedIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.outlinedShape,
    colors: IconToggleButtonColors = IconButtonDefaults.outlinedIconToggleButtonColors(),
    border: BorderStroke? = IconButtonDefaults.outlinedIconToggleButtonBorder(enabled, checked),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        interactionSource = interactionSource,
        content = content
    )

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external"
 * target="_blank">Material Design outlined icon toggle button</a>.
 *
 * Icon buttons help people take supplementary actions with a single tap. They’re used when a
 * compact button is required, such as in a toolbar or image list.
 *
 * ![Outlined icon toggle button
 * image](https://developer.android.com/images/reference/androidx/compose/material3/outlined-icon-toggle-button.png)
 *
 * [content] should typically be an [Icon] (see [androidx.compose.material.icons.Icons]). If using a
 * custom icon, note that the typical size for the internal icon is 24 x 24 dp. This icon button has
 * an overall minimum touch target size of 48 x 48dp, to meet accessibility guidelines.
 *
 * @sample androidx.compose.material3.samples.OutlinedIconToggleButtonWithAnimatedShapeSample
 * @param checked whether this icon button is toggled on or off
 * @param onCheckedChange called when this icon button is clicked
 * @param shapes the [IconButtonShapes] that the icon toggle button will morph between depending on
 *   the user's interaction with the icon toggle button.
 * @param modifier the [Modifier] to be applied to this icon button
 * @param enabled controls the enabled state of this icon button. When `false`, this component will
 *   not respond to user input, and it will appear visually disabled and disabled to accessibility
 *   services.
 * @param colors [IconToggleButtonColors] that will be used to resolve the colors used for this icon
 *   button in different states. See [IconButtonDefaults.outlinedIconToggleButtonVibrantColors].
 * @param border the border to draw around the container of this icon button. Pass `null` for no
 *   border. See [IconButtonDefaults.outlinedIconToggleButtonVibrantBorder].
 * @param interactionSource an optional hoisted [MutableInteractionSource] for observing and
 *   emitting [Interaction]s for this icon button. You can use this to change the icon button's
 *   appearance or preview the icon button in different states. Note that if `null` is provided,
 *   interactions will still happen internally.
 * @param content the content of this icon button, typically an [Icon]
 */
@ExperimentalMaterial3ExpressiveApi
@Composable
fun OutlinedIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shapes: IconButtonShapes,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.outlinedIconToggleButtonVibrantColors(),
    border: BorderStroke? =
        IconButtonDefaults.outlinedIconToggleButtonVibrantBorder(enabled, checked),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit
) =
    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shapes = shapes,
        colors = colors,
        border = border,
        interactionSource = interactionSource,
        content = content
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SurfaceIconButton(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    colors: IconButtonColors,
    border: BorderStroke?,
    interactionSource: MutableInteractionSource?,
    content: @Composable () -> Unit
) =
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = colors.containerColor(enabled),
        contentColor = colors.contentColor(enabled),
        border = border,
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier.size(IconButtonDefaults.smallContainerSize()),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SurfaceIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    colors: IconToggleButtonColors,
    border: BorderStroke?,
    interactionSource: MutableInteractionSource?,
    content: @Composable () -> Unit
) {
    Surface(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.semantics { role = Role.Checkbox },
        enabled = enabled,
        shape = shape,
        color = colors.containerColor(enabled, checked).value,
        contentColor = colors.contentColor(enabled, checked).value,
        border = border,
        interactionSource = interactionSource
    ) {
        Box(
            modifier =
                Modifier.size(
                        IconButtonDefaults.smallContainerSize(),
                    )
                    .then(
                        when (shape) {
                            is ShapeWithOpticalCentering -> {
                                Modifier.opticalCentering(
                                    shape = shape,
                                    basePadding = PaddingValues()
                                )
                            }
                            is CornerBasedShape -> {
                                Modifier.opticalCentering(
                                    shape = shape,
                                    basePadding = PaddingValues()
                                )
                            }
                            else -> {
                                Modifier
                            }
                        }
                    ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@ExperimentalMaterial3ExpressiveApi
@Composable
private fun SurfaceIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shapes: IconButtonShapes,
    colors: IconToggleButtonColors,
    border: BorderStroke?,
    interactionSource: MutableInteractionSource?,
    content: @Composable () -> Unit
) {

    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    SurfaceIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        shape = shapeForInteraction(checked, shapes, interactionSource),
        colors = colors,
        border = border,
        interactionSource = interactionSource,
        content = content
    )
}

@ExperimentalMaterial3ExpressiveApi
@Composable
private fun shapeForInteraction(
    checked: Boolean,
    shapes: IconButtonShapes,
    interactionSource: MutableInteractionSource,
): Shape {
    // TODO Load the motionScheme tokens from the component tokens file
    // MotionSchemeKeyTokens.DefaultEffects is intentional here to prevent
    // any bounce in this component.
    val defaultAnimationSpec = MotionSchemeKeyTokens.DefaultEffects.value<Float>()
    val pressed by interactionSource.collectIsPressedAsState()

    return shapeByInteraction(shapes, pressed, checked, defaultAnimationSpec)
}

/** Contains the default values for all four icon and icon toggle button types. */
object IconButtonDefaults {
    /**
     * Contains the default values used by [IconButton]. [LocalContentColor] will be applied to the
     * icon and down the UI tree.
     *
     * See [iconButtonVibrantColors] for default values that applies the recommended high contrast
     * colors.
     */
    @Composable
    fun iconButtonColors(): IconButtonColors {
        val contentColor = LocalContentColor.current
        val colors = MaterialTheme.colorScheme.defaultIconButtonColors(contentColor)
        return if (colors.contentColor == contentColor) {
            colors
        } else {
            colors.copy(
                contentColor = contentColor,
                disabledContentColor =
                    contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity)
            )
        }
    }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a [IconButton].
     * [LocalContentColor] will be applied to the icon and down the UI tree unless a custom
     * [contentColor] is provided.
     *
     * See [iconButtonVibrantColors] for default values that applies the recommended high contrast
     * colors.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled. By default, this will
     *   use the current LocalContentColor value.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun iconButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = LocalContentColor.current,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity)
    ): IconButtonColors =
        MaterialTheme.colorScheme
            .defaultIconButtonColors(LocalContentColor.current)
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
            )

    internal fun ColorScheme.defaultIconButtonColors(localContentColor: Color): IconButtonColors {
        return defaultIconButtonColorsCached
            ?: run {
                IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = localContentColor,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            localContentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultIconButtonColorsCached = it }
            }
    }

    /**
     * Creates a [IconButtonColors] that represents the recommended high contrast colors used in an
     * [IconButton].
     *
     * See [iconButtonColors] for default values that applies [LocalContentColor] to the icon and
     * down the UI tree.
     */
    @Composable
    fun iconButtonVibrantColors(): IconButtonColors =
        MaterialTheme.colorScheme.defaultIconButtonVibrantColors()

    /**
     * Creates a [IconButtonColors] that represents the recommended high contrast colors used in an
     * [IconButton].
     *
     * See [iconButtonColors] for default values that applies [LocalContentColor] to the icon and
     * down the UI tree.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun iconButtonVibrantColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity)
    ): IconButtonColors =
        MaterialTheme.colorScheme
            .defaultIconButtonVibrantColors()
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
            )

    internal fun ColorScheme.defaultIconButtonVibrantColors(): IconButtonColors {
        return defaultIconButtonVibrantColorsCached
            ?: run {
                IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = fromToken(StandardIconButtonTokens.Color),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            fromToken(StandardIconButtonTokens.DisabledColor)
                                .copy(alpha = StandardIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultIconButtonVibrantColorsCached = it }
            }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [IconToggleButton]. [LocalContentColor] will be applied to the icon and down the UI tree.
     *
     * See [iconToggleButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     */
    @Composable
    fun iconToggleButtonColors(): IconToggleButtonColors {
        val contentColor = LocalContentColor.current
        val colors = MaterialTheme.colorScheme.defaultIconToggleButtonColors(contentColor)
        if (colors.contentColor == contentColor) {
            return colors
        } else {
            return colors.copy(
                contentColor = contentColor,
                disabledContentColor =
                    contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity)
            )
        }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [IconToggleButton]. [LocalContentColor] will be applied to the icon and down the UI tree
     * unless a custom [contentColor] is provided.
     *
     * See [iconToggleButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun iconToggleButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = LocalContentColor.current,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity),
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = Color.Unspecified
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme
            .defaultIconToggleButtonColors(LocalContentColor.current)
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = checkedContentColor,
            )

    internal fun ColorScheme.defaultIconToggleButtonColors(
        localContentColor: Color
    ): IconToggleButtonColors {
        return defaultIconToggleButtonColorsCached
            ?: run {
                IconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = localContentColor,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            localContentColor.copy(
                                alpha = StandardIconButtonTokens.DisabledOpacity
                            ),
                        checkedContainerColor = Color.Transparent,
                        checkedContentColor = fromToken(StandardIconButtonTokens.SelectedColor)
                    )
                    .also { defaultIconToggleButtonColorsCached = it }
            }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the recommended high contrast colors used
     * in a [IconToggleButton]. See [iconToggleButtonColors] for default values that applies
     * [LocalContentColor] to the icon and down the UI tree.
     */
    @Composable
    fun iconToggleButtonVibrantColors(): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultIconToggleButtonVibrantColors()

    /**
     * Creates a [IconToggleButtonColors] that represents the recommended high contrast colors used
     * in a [IconToggleButton].
     *
     * See [iconToggleButtonColors] for default values that applies [LocalContentColor] to the icon
     * and down the UI tree.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun iconToggleButtonVibrantColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = StandardIconButtonTokens.DisabledOpacity),
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = Color.Unspecified
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme
            .defaultIconToggleButtonVibrantColors()
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = checkedContentColor,
            )

    internal fun ColorScheme.defaultIconToggleButtonVibrantColors(): IconToggleButtonColors {
        return defaultIconToggleButtonVibrantColorsCached
            ?: run {
                IconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = fromToken(StandardIconButtonTokens.UnselectedColor),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            fromToken(StandardIconButtonTokens.DisabledColor)
                                .copy(alpha = StandardIconButtonTokens.DisabledOpacity),
                        checkedContainerColor = Color.Transparent,
                        checkedContentColor = fromToken(StandardIconButtonTokens.SelectedColor)
                    )
                    .also { defaultIconToggleButtonVibrantColorsCached = it }
            }
    }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a [FilledIconButton].
     */
    @Composable
    fun filledIconButtonColors(): IconButtonColors =
        MaterialTheme.colorScheme.defaultFilledIconButtonColors

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a [FilledIconButton].
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun filledIconButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = contentColorFor(containerColor),
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified
    ): IconButtonColors =
        MaterialTheme.colorScheme.defaultFilledIconButtonColors.copy(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        )

    internal val ColorScheme.defaultFilledIconButtonColors: IconButtonColors
        get() {
            return defaultFilledIconButtonColorsCached
                ?: IconButtonColors(
                        containerColor = fromToken(FilledIconButtonTokens.ContainerColor),
                        contentColor = fromToken(FilledIconButtonTokens.Color),
                        disabledContainerColor =
                            fromToken(FilledIconButtonTokens.DisabledContainerColor)
                                .copy(alpha = FilledIconButtonTokens.DisabledContainerOpacity),
                        disabledContentColor =
                            fromToken(FilledIconButtonTokens.DisabledColor)
                                .copy(alpha = FilledIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultFilledIconButtonColorsCached = it }
        }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [FilledIconToggleButton].
     */
    @Composable
    fun filledIconToggleButtonColors(): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultFilledIconToggleButtonColors

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [FilledIconToggleButton].
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun filledIconToggleButtonColors(
        containerColor: Color = Color.Unspecified,
        // TODO(b/228455081): Using contentColorFor here will return OnSurfaceVariant,
        //  while the token value is Primary.
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = contentColorFor(checkedContainerColor)
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultFilledIconToggleButtonColors.copy(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
            checkedContainerColor = checkedContainerColor,
            checkedContentColor = checkedContentColor,
        )

    internal val ColorScheme.defaultFilledIconToggleButtonColors: IconToggleButtonColors
        get() {
            return defaultFilledIconToggleButtonColorsCached
                ?: IconToggleButtonColors(
                        containerColor = fromToken(FilledIconButtonTokens.UnselectedContainerColor),
                        // TODO(b/228455081): Using contentColorFor here will return
                        // OnSurfaceVariant,
                        //  while the token value is Primary.
                        contentColor = fromToken(FilledIconButtonTokens.UnselectedColor),
                        disabledContainerColor =
                            fromToken(FilledIconButtonTokens.DisabledContainerColor)
                                .copy(alpha = FilledIconButtonTokens.DisabledContainerOpacity),
                        disabledContentColor =
                            fromToken(FilledIconButtonTokens.DisabledColor)
                                .copy(alpha = FilledIconButtonTokens.DisabledOpacity),
                        checkedContainerColor =
                            fromToken(FilledIconButtonTokens.SelectedContainerColor),
                        checkedContentColor = fromToken(FilledIconButtonTokens.SelectedColor)
                    )
                    .also { defaultFilledIconToggleButtonColorsCached = it }
        }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [FilledTonalIconButton].
     */
    @Composable
    fun filledTonalIconButtonColors(): IconButtonColors =
        MaterialTheme.colorScheme.defaultFilledTonalIconButtonColors

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [FilledTonalIconButton].
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun filledTonalIconButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = contentColorFor(containerColor),
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified
    ): IconButtonColors =
        MaterialTheme.colorScheme.defaultFilledTonalIconButtonColors.copy(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        )

    internal val ColorScheme.defaultFilledTonalIconButtonColors: IconButtonColors
        get() {
            return defaultFilledTonalIconButtonColorsCached
                ?: IconButtonColors(
                        containerColor = fromToken(FilledTonalIconButtonTokens.ContainerColor),
                        contentColor = fromToken(FilledTonalIconButtonTokens.Color),
                        disabledContainerColor =
                            fromToken(FilledTonalIconButtonTokens.DisabledContainerColor)
                                .copy(alpha = FilledTonalIconButtonTokens.DisabledContainerOpacity),
                        disabledContentColor =
                            fromToken(FilledTonalIconButtonTokens.DisabledColor)
                                .copy(alpha = FilledTonalIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultFilledTonalIconButtonColorsCached = it }
        }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [FilledTonalIconToggleButton].
     */
    @Composable
    fun filledTonalIconToggleButtonColors(): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultFilledTonalIconToggleButtonColors

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [FilledTonalIconToggleButton].
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun filledTonalIconToggleButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = contentColorFor(containerColor),
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = contentColorFor(checkedContainerColor)
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultFilledTonalIconToggleButtonColors.copy(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
            checkedContainerColor = checkedContainerColor,
            checkedContentColor = checkedContentColor,
        )

    internal val ColorScheme.defaultFilledTonalIconToggleButtonColors: IconToggleButtonColors
        get() {
            return defaultFilledTonalIconToggleButtonColorsCached
                ?: IconToggleButtonColors(
                        containerColor =
                            fromToken(FilledTonalIconButtonTokens.UnselectedContainerColor),
                        contentColor = fromToken(FilledTonalIconButtonTokens.UnselectedColor),
                        disabledContainerColor =
                            fromToken(FilledTonalIconButtonTokens.DisabledContainerColor)
                                .copy(alpha = FilledTonalIconButtonTokens.DisabledContainerOpacity),
                        disabledContentColor =
                            fromToken(FilledTonalIconButtonTokens.DisabledColor)
                                .copy(alpha = FilledTonalIconButtonTokens.DisabledOpacity),
                        checkedContainerColor =
                            fromToken(FilledTonalIconButtonTokens.SelectedContainerColor),
                        checkedContentColor = fromToken(FilledTonalIconButtonTokens.SelectedColor)
                    )
                    .also { defaultFilledTonalIconToggleButtonColorsCached = it }
        }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [OutlinedIconButton]. [LocalContentColor] will be applied to the icon and down the UI tree.
     *
     * See [outlinedIconButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     */
    @Composable
    fun outlinedIconButtonColors(): IconButtonColors {
        val contentColor = LocalContentColor.current
        val colors = MaterialTheme.colorScheme.defaultOutlinedIconButtonColors(contentColor)
        if (colors.contentColor == contentColor) {
            return colors
        } else {
            return colors.copy(
                contentColor = contentColor,
                disabledContentColor =
                    contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
            )
        }
    }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [OutlinedIconButton].
     *
     * See [outlinedIconButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun outlinedIconButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = LocalContentColor.current,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
    ): IconButtonColors =
        MaterialTheme.colorScheme
            .defaultOutlinedIconButtonColors(LocalContentColor.current)
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
            )

    internal fun ColorScheme.defaultOutlinedIconButtonColors(
        localContentColor: Color
    ): IconButtonColors {
        return defaultOutlinedIconButtonColorsCached
            ?: run {
                IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = localContentColor,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            localContentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultOutlinedIconButtonColorsCached = it }
            }
    }

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [OutlinedIconButton].
     *
     * See [outlinedIconButtonColors] for default values that applies [LocalContentColor] to the
     * icon and down the UI tree.
     */
    @Composable
    fun outlinedIconButtonVibrantColors(): IconButtonColors =
        MaterialTheme.colorScheme.defaultOutlinedIconButtonVibrantColors()

    /**
     * Creates a [IconButtonColors] that represents the default colors used in a
     * [OutlinedIconButton].
     *
     * See [outlinedIconButtonColors] for default values that applies [LocalContentColor] to the
     * icon and down the UI tree.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun outlinedIconButtonVibrantColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
    ): IconButtonColors =
        MaterialTheme.colorScheme
            .defaultOutlinedIconButtonVibrantColors()
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
            )

    internal fun ColorScheme.defaultOutlinedIconButtonVibrantColors(): IconButtonColors {
        return defaultOutlinedIconButtonVibrantColorsCached
            ?: run {
                IconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = fromToken(OutlinedIconButtonTokens.Color),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            fromToken(OutlinedIconButtonTokens.DisabledColor)
                                .copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
                    )
                    .also { defaultOutlinedIconButtonVibrantColorsCached = it }
            }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [OutlinedIconToggleButton]. [LocalContentColor] will be applied to the icon and down the UI
     * tree.
     *
     * See [outlinedIconButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     */
    @Composable
    fun outlinedIconToggleButtonColors(): IconToggleButtonColors {
        val contentColor = LocalContentColor.current
        val colors = MaterialTheme.colorScheme.defaultOutlinedIconToggleButtonColors(contentColor)
        if (colors.contentColor == contentColor) {
            return colors
        } else {
            return colors.copy(
                contentColor = contentColor,
                disabledContentColor =
                    contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity)
            )
        }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [OutlinedIconToggleButton]. [LocalContentColor] will be applied to the icon and down the UI
     * tree.
     *
     * See [outlinedIconButtonVibrantColors] for default values that applies the recommended high
     * contrast colors.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun outlinedIconToggleButtonColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = LocalContentColor.current,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity),
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = contentColorFor(checkedContainerColor)
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme
            .defaultOutlinedIconToggleButtonColors(LocalContentColor.current)
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = checkedContentColor,
            )

    internal fun ColorScheme.defaultOutlinedIconToggleButtonColors(
        localContentColor: Color
    ): IconToggleButtonColors {
        return defaultIconToggleButtonColorsCached
            ?: run {
                IconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = localContentColor,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            localContentColor.copy(
                                alpha = OutlinedIconButtonTokens.DisabledOpacity
                            ),
                        checkedContainerColor =
                            fromToken(OutlinedIconButtonTokens.SelectedContainerColor),
                        checkedContentColor =
                            contentColorFor(
                                fromToken(OutlinedIconButtonTokens.SelectedContainerColor)
                            )
                    )
                    .also { defaultOutlinedIconToggleButtonColorsCached = it }
            }
    }

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [OutlinedIconToggleButton].
     *
     * See [outlinedIconToggleButtonColors] for default values that applies [LocalContentColor] to
     * the icon and down the UI tree.
     */
    @Composable
    fun outlinedIconToggleButtonVibrantColors(): IconToggleButtonColors =
        MaterialTheme.colorScheme.defaultOutlinedIconToggleButtonVibrantColors()

    /**
     * Creates a [IconToggleButtonColors] that represents the default colors used in a
     * [OutlinedIconToggleButton].
     *
     * See [outlinedIconToggleButtonColors] for default values that applies [LocalContentColor] to
     * the icon and down the UI tree.
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     * @param checkedContainerColor the container color of this icon button when checked.
     * @param checkedContentColor the content color of this icon button when checked.
     */
    @Composable
    fun outlinedIconToggleButtonVibrantColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color =
            contentColor.copy(alpha = OutlinedIconButtonTokens.DisabledOpacity),
        checkedContainerColor: Color = Color.Unspecified,
        checkedContentColor: Color = contentColorFor(checkedContainerColor)
    ): IconToggleButtonColors =
        MaterialTheme.colorScheme
            .defaultOutlinedIconToggleButtonVibrantColors()
            .copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor,
                checkedContainerColor = checkedContainerColor,
                checkedContentColor = checkedContentColor,
            )

    internal fun ColorScheme.defaultOutlinedIconToggleButtonVibrantColors():
        IconToggleButtonColors {
        return defaultOutlinedIconToggleButtonVibrantColorsCached
            ?: run {
                IconToggleButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = fromToken(OutlinedIconButtonTokens.UnselectedColor),
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor =
                            fromToken(OutlinedIconButtonTokens.DisabledColor)
                                .copy(alpha = OutlinedIconButtonTokens.DisabledOpacity),
                        checkedContainerColor =
                            fromToken(OutlinedIconButtonTokens.SelectedContainerColor),
                        checkedContentColor = fromToken(OutlinedIconButtonTokens.SelectedColor)
                    )
                    .also { defaultOutlinedIconToggleButtonColorsCached = it }
            }
    }

    /**
     * Represents the [BorderStroke] for an [OutlinedIconButton], depending on its [enabled] and
     * [checked] state. [LocalContentColor] will be used as the border color.
     *
     * See [outlinedIconToggleButtonVibrantBorder] for a [BorderStroke] that uses the spec
     * recommended color as the border color.
     *
     * @param enabled whether the icon button is enabled
     * @param checked whether the icon button is checked
     */
    @Composable
    fun outlinedIconToggleButtonBorder(enabled: Boolean, checked: Boolean): BorderStroke? {
        if (checked) {
            return null
        }
        return outlinedIconButtonBorder(enabled)
    }

    /**
     * Represents the [BorderStroke] for an [OutlinedIconButton], depending on its [enabled] and
     * [checked] state. The spec recommended color will be used as the border color.
     *
     * @param enabled whether the icon button is enabled
     * @param checked whether the icon button is checked
     */
    @Composable
    fun outlinedIconToggleButtonVibrantBorder(enabled: Boolean, checked: Boolean): BorderStroke? {
        if (checked) {
            return null
        }
        return outlinedIconButtonVibrantBorder(enabled)
    }

    /**
     * Represents the [BorderStroke] for an [OutlinedIconButton], depending on its [enabled] state.
     * [LocalContentColor] will be used as the border color.
     *
     * See [outlinedIconToggleButtonVibrantBorder] for a [BorderStroke] that uses the spec
     * recommended color as the border color.
     *
     * @param enabled whether the icon button is enabled
     */
    @Composable
    fun outlinedIconButtonBorder(enabled: Boolean): BorderStroke {
        val outlineColor = LocalContentColor.current
        val color: Color =
            if (enabled) {
                outlineColor
            } else {
                outlineColor.copy(alpha = OutlinedIconButtonTokens.DisabledContainerOpacity)
            }
        return remember(color) { BorderStroke(SmallIconButtonTokens.OutlinedOutlineWidth, color) }
    }

    /**
     * Represents the [BorderStroke] for an [OutlinedIconButton], depending on its [enabled] state.
     * The spec recommended color will be used as the border color.
     *
     * @param enabled whether the icon button is enabled
     */
    @Composable
    fun outlinedIconButtonVibrantBorder(enabled: Boolean): BorderStroke {
        val outlineColor = OutlinedIconButtonTokens.OutlineColor.value
        val color: Color =
            if (enabled) {
                outlineColor
            } else {
                outlineColor.copy(alpha = OutlinedIconButtonTokens.DisabledContainerOpacity)
            }
        return remember(color) { BorderStroke(SmallIconButtonTokens.OutlinedOutlineWidth, color) }
    }

    /** Default ripple shape for a standard icon button. */
    val standardShape: Shape
        @Composable get() = SmallIconButtonTokens.ContainerShapeRound.value

    /** Default shape for a filled icon button. */
    val filledShape: Shape
        @Composable get() = SmallIconButtonTokens.ContainerShapeRound.value

    /** Default shape for an outlined icon button. */
    val outlinedShape: Shape
        @Composable get() = SmallIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default round shape for any extra small icon button. */
    val xSmallRoundShape: Shape
        @Composable get() = XSmallIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default square shape for any extra small icon button. */
    val xSmallSquareShape: Shape
        @Composable get() = XSmallIconButtonTokens.ContainerShapeSquare.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default pressed shape for any extra small icon button. */
    val xSmallPressedShape: Shape
        @Composable get() = XSmallIconButtonTokens.PressedContainerShape.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any small icon button. */
    val smallRoundShape: Shape
        @Composable get() = SmallIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any small icon button. */
    val smallSquareShape: Shape
        @Composable get() = SmallIconButtonTokens.ContainerShapeSquare.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default pressed shape for any small icon button. */
    val smallPressedShape: Shape
        @Composable get() = SmallIconButtonTokens.PressedContainerShape.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any medium icon button. */
    val mediumRoundShape: Shape
        @Composable get() = MediumIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any medium icon button. */
    val mediumSquareShape: Shape
        @Composable get() = MediumIconButtonTokens.ContainerShapeSquare.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default pressed shape for any medium icon button. */
    val mediumPressedShape: Shape
        @Composable get() = MediumIconButtonTokens.PressedContainerShape.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any large icon button. */
    val largeRoundShape: Shape
        @Composable get() = LargeIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any large icon button. */
    val largeSquareShape: Shape
        @Composable get() = LargeIconButtonTokens.ContainerShapeSquare.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default pressed shape for any large icon button. */
    val largePressedShape: Shape
        @Composable get() = LargeIconButtonTokens.PressedContainerShape.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any xlarge icon button. */
    val xLargeRoundShape: Shape
        @Composable get() = XLargeIconButtonTokens.ContainerShapeRound.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default shape for any xlarge icon button. */
    val xLargeSquareShape: Shape
        @Composable get() = XLargeIconButtonTokens.ContainerShapeSquare.value

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default pressed shape for any extra large icon button. */
    val xLargePressedShape: Shape
        @Composable get() = XLargeIconButtonTokens.PressedContainerShape.value

    /**
     * Creates a [ButtonShapes] that correspond to the shapes in the default, pressed, and checked
     * states. Toggle button will morph between these shapes as long as the shapes are all
     * [CornerBasedShape]s.
     *
     * @param shape the unchecked shape for [ButtonShapes]
     * @param pressedShape the unchecked shape for [ButtonShapes]
     * @param checkedShape the unchecked shape for [ButtonShapes]
     */
    @ExperimentalMaterial3ExpressiveApi
    @Composable
    fun shapes(shape: Shape, pressedShape: Shape, checkedShape: Shape): IconButtonShapes =
        remember(shape, pressedShape, checkedShape) {
            IconButtonShapes(shape, pressedShape, checkedShape)
        }

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default container for any extra small icon button. */
    val xSmallIconSize: Dp = XSmallIconButtonTokens.IconSize

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default size for any small icon button. */
    val smallIconSize: Dp = SmallIconButtonTokens.IconSize

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default container size for any medium icon button. */
    val mediumIconSize: Dp = MediumIconButtonTokens.IconSize

    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    /** Default size for any large icon button. */
    val largeIconSize: Dp = LargeIconButtonTokens.IconSize

    /** Default size for any xlarge icon button. */
    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    @get:ExperimentalMaterial3ExpressiveApi
    @ExperimentalMaterial3ExpressiveApi
    val xLargeIconSize: Dp = XLargeIconButtonTokens.IconSize

    /**
     * Default container size for any extra small icon button.
     *
     * @param widthOption the width of the container
     */
    @ExperimentalMaterial3ExpressiveApi
    fun xSmallContainerSize(
        widthOption: IconButtonWidthOption = IconButtonWidthOption.Uniform
    ): DpSize {
        val horizontalSpace =
            when (widthOption) {
                IconButtonWidthOption.Narrow ->
                    XSmallIconButtonTokens.NarrowLeadingSpace +
                        XSmallIconButtonTokens.NarrowTrailingSpace
                IconButtonWidthOption.Uniform ->
                    XSmallIconButtonTokens.UniformLeadingSpace +
                        XSmallIconButtonTokens.UniformLeadingSpace
                IconButtonWidthOption.Wide ->
                    XSmallIconButtonTokens.WideLeadingSpace +
                        XSmallIconButtonTokens.WideTrailingSpace
                else -> 0.dp
            }
        return DpSize(
            XSmallIconButtonTokens.IconSize + horizontalSpace,
            XSmallIconButtonTokens.ContainerHeight
        )
    }

    /**
     * Default container size for any small icon button.
     *
     * @param widthOption the width of the container
     */
    @ExperimentalMaterial3ExpressiveApi
    fun smallContainerSize(
        widthOption: IconButtonWidthOption = IconButtonWidthOption.Uniform
    ): DpSize {
        val horizontalSpace =
            when (widthOption) {
                IconButtonWidthOption.Narrow ->
                    SmallIconButtonTokens.NarrowLeadingSpace +
                        SmallIconButtonTokens.NarrowTrailingSpace
                IconButtonWidthOption.Uniform ->
                    SmallIconButtonTokens.UniformLeadingSpace +
                        SmallIconButtonTokens.UniformLeadingSpace
                IconButtonWidthOption.Wide ->
                    SmallIconButtonTokens.WideLeadingSpace + SmallIconButtonTokens.WideTrailingSpace
                else -> 0.dp
            }
        return DpSize(
            SmallIconButtonTokens.IconSize + horizontalSpace,
            SmallIconButtonTokens.ContainerHeight
        )
    }

    /**
     * Default container size for any medium icon button.
     *
     * @param widthOption the width of the container
     */
    @ExperimentalMaterial3ExpressiveApi
    fun mediumContainerSize(
        widthOption: IconButtonWidthOption = IconButtonWidthOption.Uniform
    ): DpSize {
        val horizontalSpace =
            when (widthOption) {
                IconButtonWidthOption.Narrow ->
                    MediumIconButtonTokens.NarrowLeadingSpace +
                        MediumIconButtonTokens.NarrowTrailingSpace
                IconButtonWidthOption.Uniform ->
                    MediumIconButtonTokens.UniformLeadingSpace +
                        MediumIconButtonTokens.UniformLeadingSpace
                IconButtonWidthOption.Wide ->
                    MediumIconButtonTokens.WideLeadingSpace +
                        MediumIconButtonTokens.WideTrailingSpace
                else -> 0.dp
            }
        return DpSize(
            MediumIconButtonTokens.IconSize + horizontalSpace,
            MediumIconButtonTokens.ContainerHeight
        )
    }

    /**
     * Default container size for any large icon button.
     *
     * @param widthOption the width of the container
     */
    @ExperimentalMaterial3ExpressiveApi
    fun largeContainerSize(
        widthOption: IconButtonWidthOption = IconButtonWidthOption.Uniform
    ): DpSize {
        val horizontalSpace =
            when (widthOption) {
                IconButtonWidthOption.Narrow ->
                    LargeIconButtonTokens.NarrowLeadingSpace +
                        LargeIconButtonTokens.NarrowTrailingSpace
                IconButtonWidthOption.Uniform ->
                    LargeIconButtonTokens.UniformLeadingSpace +
                        LargeIconButtonTokens.UniformLeadingSpace
                IconButtonWidthOption.Wide ->
                    LargeIconButtonTokens.WideLeadingSpace + LargeIconButtonTokens.WideTrailingSpace
                else -> 0.dp
            }
        return DpSize(
            LargeIconButtonTokens.IconSize + horizontalSpace,
            LargeIconButtonTokens.ContainerHeight
        )
    }

    /**
     * Default container size for any extra large icon button.
     *
     * @param widthOption the width of the container
     */
    @ExperimentalMaterial3ExpressiveApi
    fun xLargeContainerSize(
        widthOption: IconButtonWidthOption = IconButtonWidthOption.Uniform
    ): DpSize {
        val horizontalSpace =
            when (widthOption) {
                IconButtonWidthOption.Narrow ->
                    XLargeIconButtonTokens.NarrowLeadingSpace +
                        XLargeIconButtonTokens.NarrowTrailingSpace
                IconButtonWidthOption.Uniform ->
                    XLargeIconButtonTokens.UniformLeadingSpace +
                        XLargeIconButtonTokens.UniformLeadingSpace
                IconButtonWidthOption.Wide ->
                    XLargeIconButtonTokens.WideLeadingSpace +
                        XLargeIconButtonTokens.WideTrailingSpace
                else -> 0.dp
            }
        return DpSize(
            XLargeIconButtonTokens.IconSize + horizontalSpace,
            XLargeIconButtonTokens.ContainerHeight
        )
    }

    /** Class that describes the different supported widths of the [IconButton]. */
    @JvmInline
    value class IconButtonWidthOption private constructor(private val value: Int) {
        companion object {
            // TODO(b/342666275): update this kdoc with spec guidance
            /*
             * This configuration is recommended for small screens.
             */
            val Narrow = IconButtonWidthOption(0)

            /*
             * This configuration is recommended for medium width screens.
             */
            val Uniform = IconButtonWidthOption(1)

            /*
             * This configuration is recommended for wide screens.
             */
            val Wide = IconButtonWidthOption(2)
        }

        override fun toString() =
            when (this) {
                Narrow -> "Narrow"
                Uniform -> "Uniform"
                Wide -> "Wide"
                else -> "Unknown"
            }
    }
}

/**
 * Represents the container and content colors used in an icon button in different states.
 *
 * @param containerColor the container color of this icon button when enabled.
 * @param contentColor the content color of this icon button when enabled.
 * @param disabledContainerColor the container color of this icon button when not enabled.
 * @param disabledContentColor the content color of this icon button when not enabled.
 * @constructor create an instance with arbitrary colors.
 * - See [IconButtonDefaults.filledIconButtonColors] and
 *   [IconButtonDefaults.filledTonalIconButtonColors] for the default colors used in a
 *   [FilledIconButton].
 * - See [IconButtonDefaults.outlinedIconButtonVibrantColors] for the default colors used in an
 *   [OutlinedIconButton].
 */
@Immutable
class IconButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
) {

    /**
     * Returns a copy of this IconButtonColors, optionally overriding some of the values. This uses
     * the Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        disabledContentColor: Color = this.disabledContentColor,
    ) =
        IconButtonColors(
            containerColor.takeOrElse { this.containerColor },
            contentColor.takeOrElse { this.contentColor },
            disabledContainerColor.takeOrElse { this.disabledContainerColor },
            disabledContentColor.takeOrElse { this.disabledContentColor },
        )

    /**
     * Represents the container color for this icon button, depending on [enabled].
     *
     * @param enabled whether the icon button is enabled
     */
    @Stable
    internal fun containerColor(enabled: Boolean): Color =
        if (enabled) containerColor else disabledContainerColor

    /**
     * Represents the content color for this icon button, depending on [enabled].
     *
     * @param enabled whether the icon button is enabled
     */
    @Stable
    internal fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is IconButtonColors) return false

        if (containerColor != other.containerColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()

        return result
    }
}

/**
 * Represents the container and content colors used in a toggleable icon button in different states.
 *
 * @param containerColor the container color of this icon button when enabled.
 * @param contentColor the content color of this icon button when enabled.
 * @param disabledContainerColor the container color of this icon button when not enabled.
 * @param disabledContentColor the content color of this icon button when not enabled.
 * @param checkedContainerColor the container color of this icon button when checked.
 * @param checkedContentColor the content color of this icon button when checked.
 * @constructor create an instance with arbitrary colors.
 * - See [IconButtonDefaults.filledIconToggleButtonColors] and
 *   [IconButtonDefaults.filledTonalIconToggleButtonColors] for the default colors used in a
 *   [FilledIconButton].
 * - See [IconButtonDefaults.outlinedIconToggleButtonVibrantColors] for the default colors used in a
 *   toggleable [OutlinedIconButton].
 */
@Immutable
class IconToggleButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val checkedContainerColor: Color,
    val checkedContentColor: Color,
) {

    /**
     * Returns a copy of this IconToggleButtonColors, optionally overriding some of the values. This
     * uses the Color.Unspecified to mean “use the value from the source”
     */
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
        disabledContainerColor: Color = this.disabledContainerColor,
        disabledContentColor: Color = this.disabledContentColor,
        checkedContainerColor: Color = this.checkedContainerColor,
        checkedContentColor: Color = this.checkedContentColor
    ) =
        IconToggleButtonColors(
            containerColor.takeOrElse { this.containerColor },
            contentColor.takeOrElse { this.contentColor },
            disabledContainerColor.takeOrElse { this.disabledContainerColor },
            disabledContentColor.takeOrElse { this.disabledContentColor },
            checkedContainerColor.takeOrElse { this.checkedContainerColor },
            checkedContentColor.takeOrElse { this.checkedContentColor }
        )

    /**
     * Represents the container color for this icon button, depending on [enabled] and [checked].
     *
     * @param enabled whether the icon button is enabled
     * @param checked whether the icon button is checked
     */
    @Composable
    internal fun containerColor(enabled: Boolean, checked: Boolean): State<Color> {
        val target =
            when {
                !enabled -> disabledContainerColor
                !checked -> containerColor
                else -> checkedContainerColor
            }
        return rememberUpdatedState(target)
    }

    /**
     * Represents the content color for this icon button, depending on [enabled] and [checked].
     *
     * @param enabled whether the icon button is enabled
     * @param checked whether the icon button is checked
     */
    @Composable
    internal fun contentColor(enabled: Boolean, checked: Boolean): State<Color> {
        val target =
            when {
                !enabled -> disabledContentColor
                !checked -> contentColor
                else -> checkedContentColor
            }
        return rememberUpdatedState(target)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is IconToggleButtonColors) return false

        if (containerColor != other.containerColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (disabledContentColor != other.disabledContentColor) return false
        if (checkedContainerColor != other.checkedContainerColor) return false
        if (checkedContentColor != other.checkedContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        result = 31 * result + checkedContainerColor.hashCode()
        result = 31 * result + checkedContentColor.hashCode()

        return result
    }
}

/**
 * The shapes that will be used in toggle buttons. Toggle button will morph between these three
 * shapes depending on the interaction of the toggle button, assuming all of the shapes are
 * [CornerBasedShape]s.
 *
 * @property shape is the unchecked shape.
 * @property pressedShape is the pressed shape.
 * @property checkedShape is the checked shape.
 */
@ExperimentalMaterial3ExpressiveApi
class IconButtonShapes(val shape: Shape, val pressedShape: Shape, val checkedShape: Shape) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is IconButtonShapes) return false

        if (shape != other.shape) return false
        if (pressedShape != other.pressedShape) return false
        if (checkedShape != other.checkedShape) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shape.hashCode()
        result = 31 * result + pressedShape.hashCode()
        result = 31 * result + checkedShape.hashCode()

        return result
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val IconButtonShapes.isCornerBasedShape: Boolean
    get() =
        shape is RoundedCornerShape &&
            pressedShape is CornerBasedShape &&
            checkedShape is CornerBasedShape

@ExperimentalMaterial3ExpressiveApi
@Composable
private fun shapeByInteraction(
    shapes: IconButtonShapes,
    pressed: Boolean,
    checked: Boolean,
    animationSpec: FiniteAnimationSpec<Float>
): Shape {
    val shape =
        if (pressed) {
            shapes.pressedShape
        } else if (checked) {
            shapes.checkedShape
        } else shapes.shape

    if (shapes.isCornerBasedShape) {
        return key(shapes) { rememberAnimatedShape(shape as RoundedCornerShape, animationSpec) }
    }
    return shape
}

package com.louisfn.somovie.core.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

fun CoroutineScope.cancelChildren() = this.coroutineContext.cancelChildren()

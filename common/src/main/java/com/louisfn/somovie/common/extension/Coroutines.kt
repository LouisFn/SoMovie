package com.louisfn.somovie.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren

fun CoroutineScope.cancelChildren() = this.coroutineContext.cancelChildren()

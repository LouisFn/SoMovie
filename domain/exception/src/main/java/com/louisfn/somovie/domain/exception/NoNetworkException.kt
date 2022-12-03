package com.louisfn.somovie.domain.exception

import java.io.IOException

class NoNetworkException(cause: Exception) : IOException(cause)

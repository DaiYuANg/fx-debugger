package org.scenicview

import io.avaje.inject.BeanScope

object DIContext {
  private val beanScope = BeanScope.builder().build()

  fun <T> get(type: Class<T>): T {
    return beanScope.get(type)
  }
}

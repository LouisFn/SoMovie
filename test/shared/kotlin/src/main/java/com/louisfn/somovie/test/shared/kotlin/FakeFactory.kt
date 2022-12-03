package com.louisfn.somovie.test.shared.kotlin

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.RandomProvider
import io.github.serpro69.kfaker.provider.RandomProviderConfig
import kotlin.random.Random

object FakeFactory {

    val instance = Faker()

    inline fun <reified T : Any> create(
        nbr: Int,
        configurator: RandomProviderConfig.() -> Unit = {}
    ): List<T> =
        (0 until nbr).map {
            instance.randomProvider.randomTestClassInstance(configurator)
        }
}

inline fun <reified T : Any> RandomProvider.randomTestClassInstance(configurator: RandomProviderConfig.() -> Unit = {}): T =
    randomClassInstance {
        typeGenerator { Random.nextLong(Long.MAX_VALUE) }
        typeGenerator { Random.nextInt(Int.MAX_VALUE) }
        typeGenerator { FakeLocalDateFactory.create() }
        typeGenerator { FakeInstantFactory.create() }
        typeGenerator { FakeDurationFactory.create() }
        configurator()
    }

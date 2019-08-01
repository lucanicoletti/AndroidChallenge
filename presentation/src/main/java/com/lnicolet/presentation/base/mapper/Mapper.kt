package com.lnicolet.presentation.base.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view model output type
 * @param <D> the domain model input type
 */
interface Mapper<out V, in D> {

    fun mapToView(domainModel: D): V
}
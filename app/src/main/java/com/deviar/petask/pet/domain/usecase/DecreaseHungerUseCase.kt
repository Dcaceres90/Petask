package com.deviar.petask.pet.domain.usecase

import com.deviar.petask.common.database.data.PetRepository
import com.deviar.petask.common.database.data.model.PetModel
import javax.inject.Inject

class DecreaseHungerUseCase @Inject constructor(
    private val petRepository: PetRepository
){
    suspend operator fun invoke(pet: PetModel): PetModel{
        val currentTime = System.currentTimeMillis()
        val timePassed = currentTime - pet.lastHungerUpdate

        val twelveHours = 12 * 60 * 60 * 1000L
        val fiveMinutes = 5 * 60 * 1000L

        val hungerLost = (timePassed / fiveMinutes).toInt()

        if (hungerLost <= 0) {
            return pet
        }

        val newHunger =
            (pet.hunger - hungerLost).coerceAtLeast(0)

        petRepository.updateHunger(
            hunger = newHunger,
            lastHungerUpdate = currentTime
        )

        return pet.copy(
            hunger = newHunger,
            lastHungerUpdate = currentTime
        )
    }
}
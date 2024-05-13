package com.example.apptfgandroid.repository

import com.example.apptfgandroid.dataSource.RegisterRemoteDataSource
import com.example.apptfgandroid.models.CreateUserDTO
import com.example.apptfgandroid.models.PhoneValidationDTO
import com.example.tfgapp.models.ServerResponseDTO

class RepositoryRegister(
    private val registerRemoteDataSource: RegisterRemoteDataSource
) {
    suspend fun confirmPhone(phoneValidationDTO: PhoneValidationDTO): ServerResponseDTO = registerRemoteDataSource.confirmPhone(phoneValidationDTO)

    suspend fun validatePhone(createUserDTO: CreateUserDTO): ServerResponseDTO = registerRemoteDataSource.validatePhone(createUserDTO)

}
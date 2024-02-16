package com.example.apptfgandroid.ui.screens.MainMenu

import androidx.lifecycle.ViewModel
import com.example.apptfgandroid.models.ContactRequestDTO
import com.example.apptfgandroid.models.UserDTO
import com.example.apptfgandroid.useCase.UseCaseMainMenu
import com.example.apptfgandroid.useCase.UseCaseManageContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuViewModel(
    private val useCaseManageContact: UseCaseManageContact,
    private val useCaseMainMenu: UseCaseMainMenu

): ViewModel() {
    private val viewModelScope =  CoroutineScope(Dispatchers.Main)
    private val _request = MutableStateFlow<Set<UserDTO>>(emptySet())
    val request: StateFlow<Set<UserDTO>> = _request
//    val request: Set<UserDTO> = getUsersExample()

    init {
        viewModelScope.launch {
            getRequest()
        }
    }

    fun deleteToken(){
        viewModelScope.launch(Dispatchers.IO) {
            useCaseMainMenu.deleteToken()
        }
    }

    private suspend fun getRequest(){
        useCaseManageContact.getTokenFlow().collect { tokenValue ->
            tokenValue?.let {
                useCaseManageContact.getRequest(it).collect {contacts ->
                    withContext(Dispatchers.Main) {
                        _request.value = contacts
                    }
                }
            }
        }
    }

    fun acceptContactRequest(userDTOToAccept: UserDTO){
        val contactRequestDTO = ContactRequestDTO(username = userDTOToAccept.username)
        viewModelScope.launch(Dispatchers.Main) {
            useCaseManageContact.getTokenFlow().collect { tokenValue ->
                tokenValue?.let {
                    val responseDTO = useCaseManageContact.acceptContactRequest(contactRequestDTO, it)
                    if (responseDTO != null) {
                        deleteRequest(userDTOToAccept)
                    }
                }
            }
        }
    }

    private fun deleteRequest(userToDelete: UserDTO) {
        val currentContacts = _request.value.toMutableSet()

        if (currentContacts.remove(userToDelete)) {
            // Si el usuario estaba presente y se eliminó, actualiza el MutableStateFlow
            _request.value = currentContacts.toSet()
        }
    }
}
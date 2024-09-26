package com.goldstein.room2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldstein.room2.db.UserRepository
import com.goldstein.room2.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(UserState())
    val screenState = _screenState.asStateFlow()

    private val _event = Channel<UserEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    /*   fun getUser(user: User) {
           _screenState.value = UserState(loading = true)
           viewModelScope.launch(Dispatchers.IO) {
               resultOf { userRepository.getUserById(user) }.onSuccess { response ->
                   _screenState.value = UserState(loading = true, users = _screenState.value.users)
               }
                   .onFailure {
                       _screenState.value = UserState(loading = false)
                   }
           }
       }*/

    fun getAllUsers() {
        userRepository.getAllUsers().flowOn(Dispatchers.IO).onEach {
            _screenState.value = UserState(loading = false, users = it)
        }.catch { }.launchIn(viewModelScope)
    }


    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = (UserState(loading = true))
            val result = resultOf {
                userRepository.updateUser(user)
            }
            result.onSuccess { users ->
                _screenState.value = UserState(loading = false)
//                _event.send(UserEvent.onSuccess(users))
            }.onFailure { exception ->
                _screenState.value = UserState(loading = false)
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = (UserState(loading = true))
            val result = resultOf {   userRepository.deleteUser(user) }
            result.onSuccess { user ->
                _screenState.value = UserState(loading = false)
            }.onFailure { exception ->
                _screenState.value = UserState(loading = false)
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.value = (UserState(loading = true))
            val result = resultOf {
                userRepository.deleteAll()
            }
            result.onSuccess { user ->
                _screenState.value = UserState(loading = false)
            }.onFailure { exception ->
                _screenState.value = UserState(loading = false)
            }
        }
    }
}

sealed class UserEvent {
    data class Error(val message: String) : UserEvent()
    data class onSuccess(val users: List<User>) : UserEvent()
}

data class UserState(
    val loading: Boolean = false,
    val users: List<User> = emptyList(),
    val user: User? = null,
)
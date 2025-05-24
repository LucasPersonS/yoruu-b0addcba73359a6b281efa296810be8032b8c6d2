package com.example.mentalhealth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalhealth.data.api.AdviceResponse
import com.example.mentalhealth.data.api.ActivityResponse
import com.example.mentalhealth.data.api.QuoteResponse
import com.example.mentalhealth.data.api.RetrofitClient
import com.example.mentalhealth.data.model.MoodEntry
import com.example.mentalhealth.data.model.User
import com.example.mentalhealth.data.repository.ApiRepository
import com.example.mentalhealth.data.repository.MentalHealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MentalHealthViewModel(
    private val repository: MentalHealthRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _moodEntries = MutableStateFlow<List<MoodEntry>>(emptyList())
    val moodEntries: StateFlow<List<MoodEntry>> = _moodEntries.asStateFlow()

    private val quotableRepository = ApiRepository(RetrofitClient.quotableService)
    private val adviceRepository = ApiRepository(RetrofitClient.adviceService)
    private val boredRepository = ApiRepository(RetrofitClient.boredService)

    private val _quote = MutableStateFlow<QuoteResponse?>(null)
    val quote: StateFlow<QuoteResponse?> = _quote

    private val _advice = MutableStateFlow<AdviceResponse?>(null)
    val advice: StateFlow<AdviceResponse?> = _advice

    private val _activity = MutableStateFlow<ActivityResponse?>(null)
    val activity: StateFlow<ActivityResponse?> = _activity

    init {
        loadMoodEntries()
    }

    fun setCurrentUser(userId: String) {
        viewModelScope.launch {
            repository.getUserById(userId).collect { user ->
                _currentUser.value = user
                if (user != null) {
                    loadMoodEntries()
                }
            }
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repository.saveUser(user)
            _currentUser.value = user
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            _currentUser.value = user
        }
    }

    private fun loadMoodEntries() {
        viewModelScope.launch {
            repository.getAllMoodEntries().collect { entries ->
                _moodEntries.value = entries
            }
        }
    }

    fun addMoodEntry(mood: Int, note: String, activities: List<String> = emptyList()) {
        viewModelScope.launch {
            val entry = MoodEntry(
                mood = mood,
                note = note,
                timestamp = Date(),
                activities = activities
            )
            repository.addMoodEntry(entry)
        }
    }

    fun updateMoodEntry(entry: MoodEntry) {
        viewModelScope.launch {
            repository.updateMoodEntry(entry)
        }
    }

    fun deleteMoodEntry(entry: MoodEntry) {
        viewModelScope.launch {
            repository.deleteMoodEntry(entry)
        }
    }

    fun fetchRandomQuote() {
        viewModelScope.launch {
            _quote.value = quotableRepository.getRandomQuote()
        }
    }

    fun fetchAdvice() {
        viewModelScope.launch {
            _advice.value = adviceRepository.getAdvice()
        }
    }

    fun fetchActivity(type: String? = null, participants: Int? = null) {
        viewModelScope.launch {
            _activity.value = boredRepository.getActivity(type, participants)
        }
    }
} 
package com.example.mydatasiswa.uicontroller.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydatasiswa.repositori.RepositoryDataSiswa
import com.example.mydatasiswa.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch

sealed interface ItemDetailsUiState {
    data class Success(val detailSiswa: DetailsSiswa = DetailsSiswa()) : ItemDetailsUiState
    object Error : ItemDetailsUiState
    object Loading : ItemDetailsUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    var uiState: ItemDetailsUiState by mutableStateOf(ItemDetailsUiState.Loading)
        private set

    init {
        getItemById()
    }

    fun getItemById() {
        viewModelScope.launch {
            uiState = ItemDetailsUiState.Loading
            uiState = try {
                val siswa = repositoryDataSiswa.getSiswaById(itemId)
                ItemDetailsUiState.Success(siswa.toDetailsSiswa())
            } catch (e: Exception) {
                e.printStackTrace()
                ItemDetailsUiState.Error
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            try {
                repositoryDataSiswa.deleteSiswa(itemId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

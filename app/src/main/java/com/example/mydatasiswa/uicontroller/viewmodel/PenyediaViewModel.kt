package com.example.mydatasiswa.uicontroller.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mydatasiswa.AplikasiSiswa

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoryDataSiswa)
        }
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositoryDataSiswa)
        }
        initializer {
            DetailViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoryDataSiswa
            )
        }
        initializer {
            EditViewModel(
                createSavedStateHandle(),
                aplikasiSiswa().container.repositoryDataSiswa
            )
        }
    }
}

fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa)

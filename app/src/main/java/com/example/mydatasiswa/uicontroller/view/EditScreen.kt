package com.example.mydatasiswa.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mydatasiswa.R
import com.example.mydatasiswa.uicontroller.route.DestinasiEdit
import com.example.mydatasiswa.uicontroller.viewmodel.EditViewModel
import com.example.mydatasiswa.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = viewModel<EditViewModel>(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(DestinasiEdit.titleRes)) }
            )
        }
    ) { innerPadding ->
        EntrySiswaBody(
            uiStateSiswa = viewModel.siswaUiState,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSiswa()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

package com.example.mydatasiswa.uicontroller.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mydatasiswa.R
import com.example.mydatasiswa.uicontroller.route.DestinasiDetail
import com.example.mydatasiswa.uicontroller.viewmodel.DetailViewModel
import com.example.mydatasiswa.uicontroller.viewmodel.DetailsSiswa
import com.example.mydatasiswa.uicontroller.viewmodel.ItemDetailsUiState
import com.example.mydatasiswa.uicontroller.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel<DetailViewModel>(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(DestinasiDetail.titleRes)) }
            )
        },
        floatingActionButton = {
            val uiState = viewModel.uiState
            if (uiState is ItemDetailsUiState.Success) {
                FloatingActionButton(
                    onClick = { navigateToEdit(uiState.detailSiswa.id) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(18.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.update)
                    )
                }
            }
        }
    ) { innerPadding ->
        ItemDetailsBody(
            itemDetailsUiState = viewModel.uiState,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun ItemDetailsBody(
    itemDetailsUiState: ItemDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (itemDetailsUiState) {
        is ItemDetailsUiState.Loading -> Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is ItemDetailsUiState.Success -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ItemDetailsCard(
                    detailsSiswa = itemDetailsUiState.detailSiswa,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedButton(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.delete))
                }

                if (deleteConfirmationRequired) {
                    AlertDialog(
                        onDismissRequest = { deleteConfirmationRequired = false },
                        title = { Text(stringResource(R.string.attention)) },
                        text = { Text(stringResource(R.string.tanya)) },
                        confirmButton = {
                            TextButton(onClick = {
                                deleteConfirmationRequired = false
                                onDelete()
                            }) {
                                Text(stringResource(R.string.yes))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { deleteConfirmationRequired = false }) {
                                Text(stringResource(R.string.no))
                            }
                        }
                    )
                }
            }
        }
        is ItemDetailsUiState.Error -> Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.gagal))
        }
    }
}

@Composable
fun ItemDetailsCard(
    detailsSiswa: DetailsSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.nama1))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = detailsSiswa.nama, style = MaterialTheme.typography.titleMedium)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.alamat1))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = detailsSiswa.alamat, style = MaterialTheme.typography.bodyMedium)
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.telpon1))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = detailsSiswa.telpon, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

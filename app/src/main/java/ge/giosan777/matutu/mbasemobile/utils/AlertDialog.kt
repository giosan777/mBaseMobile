package ge.giosan777.matutu.mbasemobile.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ge.giosan777.matutu.mbasemobile.R

@Composable
fun AlertDialogPermissions(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(R.string.permissions))
                    },
                    text = {
                        Text(stringResource(R.string.all_permissions_are_required_for_the_app_to_work_properly))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(true)
                            }) {
                            Text(stringResource(R.string.giving_permission))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(false)
                            }) {
                            Text(stringResource(R.string.close))
                        }
                    }
                )
            }
        }

    }
}

@Composable
fun AlertDialogInternet(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(R.string.internet_connection))
                    },
                    text = {
                        Text(stringResource(R.string.internet_required_for_first_launch))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(false)
                            }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    dismissButton = {

                    }
                )
            }
        }

    }
}


@Composable
fun AlertDialogInternetFromAddNewOrg() {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    title = {
                        Text(text = stringResource(R.string.internet_connection))
                    },
                    text = {
                        Text(stringResource(R.string.internet_required_for_add_new_org))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false

                            }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    dismissButton = {

                    }
                )
            }
        }

    }
}


@Composable
fun AlertDialogCloseApplication(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    text = {
                        Text(stringResource(R.string.permissions_are_not_granted))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(true)
                            }) {
                            Text(stringResource(R.string.yes))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(false)
                            }) {
                            Text(stringResource(R.string.no))
                        }
                    }
                )
            }
        }

    }
}



@Composable
fun AlertDialogCallLogPermission(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    text = {
                        Text(stringResource(R.string.permissions_are_not_granted1))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(true)
                            }) {
                            Text(stringResource(R.string.ok))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(false)
                            }) {
                            Text(stringResource(R.string.no))
                        }
                    }
                )
            }
        }

    }
}


@Composable
fun AlertDialogCallLogPermissionNeed(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    text = {
                        Text(stringResource(R.string.permissions_are_not_granted1))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(true)
                            }) {
                            Text(stringResource(R.string.ok))
                        }
                    },

                )
            }
        }

    }
}



@Composable
fun AlertDialogBattery(callbacks:(Boolean) -> Unit) {
    MaterialTheme {
        Column {
            val openDialog = remember { mutableStateOf(true) }
            if (openDialog.value) {

                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
//                        openDialog.value = false
                    },
                    text = {
                        Text(stringResource(R.string.battery))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(true)
                            }) {
                            Text(stringResource(R.string.disable))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                openDialog.value = false
                                callbacks(false)
                            }) {
                            Text(stringResource(R.string.close))
                        }
                    }
                )
            }
        }

    }
}
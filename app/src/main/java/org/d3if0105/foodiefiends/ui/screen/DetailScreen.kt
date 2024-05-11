import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if0105.foodiefiends.R
import org.d3if0105.foodiefiends.database.FoodieDb
import org.d3if0105.foodiefiends.ui.screen.DetailViewModel
import org.d3if0105.foodiefiends.ui.screen.DisplayAlertDialog
import org.d3if0105.foodiefiends.ui.theme.FoodieFiendsTheme
import org.d3if0105.foodiefiends.util.ViewModelFactory


const val KEY_ID_FOODIE= "idfoodie"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = FoodieDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)
    var namaMenu by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("")}
    var deskripsi by remember { mutableStateOf(""   )}
    var showDialog by remember { mutableStateOf(false) }

    //if (id != null){
    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getFoodie(id) ?: return@LaunchedEffect
        namaMenu = data.namaMenu
        kategori= data.kategori
        deskripsi= data.deskripsi
    }
    Scaffold (
        topBar ={
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )


                    }

                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_menu))
                    else
                        Text(text = stringResource(id = R.string.edit_menu))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if(namaMenu == ""|| kategori == "" || deskripsi == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(namaMenu, kategori, deskripsi)


                        } else {
                            viewModel.update(id, namaMenu, kategori, deskripsi)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }
                    if (id != null){
                        DeleteAction {showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog ,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()

                        }
                    }
                }
            )
        }


    )

    {padding ->
        FormFoodie(
            title = namaMenu,
            onTitleChange = { namaMenu= it },
            desc = deskripsi,
            onDesChange = { deskripsi = it },
            kategori = kategori,
            onClassChange = { kategori = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete:() -> Unit){
    var expanded by remember { mutableStateOf(false)}
    IconButton(onClick = {expanded=true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert ,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded =expanded ,
            onDismissRequest = { expanded = false}
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )

        }


    }

}

@Composable
fun FormFoodie(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDesChange: (String) -> Unit,
    kategori: String, onClassChange: (String) -> Unit,
    modifier: Modifier
) {


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = title,
                onValueChange = { onTitleChange(it) },
                label = { Text(text = stringResource(R.string.nama_menu)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth(),




                )
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp)
            ) {
                Column {
                    listOf(
                        "Makanan",
                        "Minuman"

                    ).forEach { classOption ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { onClassChange(classOption) }
                        ) {
                            RadioButton(
                                selected = classOption == kategori,
                                onClick = { onClassChange(classOption) }
                            )
                            Text(
                                text = classOption,
                                modifier = Modifier.padding(start = 8.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(12.dp))

                    }
                }
                OutlinedTextField(
                    value = desc,
                    onValueChange = { onDesChange(it) },
                    label = { Text(text = stringResource(R.string.tulis_resep)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 9.dp),

                    )

            }
        }
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    FoodieFiendsTheme {
        DetailScreen(rememberNavController())
    }
}
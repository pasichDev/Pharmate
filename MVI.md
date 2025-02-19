Я створю приклад проекту на основі **MVI**, де будуть використані:
- **DataStore** для збереження налаштувань.
- **RealmDB** для локального збереження даних.
- **Dependency Injection (Hilt)** для управління залежностями.
- **Чотири екрани** для демонстрації структури проєкту.

---

## **Структура файлів**
```
app/
 ├── data/
 │   ├── datastore/
 │   │   ├── SettingsDataStore.kt
 │   ├── database/
 │   │   ├── Note.kt
 │   │   ├── NoteDao.kt
 │   │   ├── NoteDatabase.kt
 │   ├── repository/
 │   │   ├── NoteRepository.kt
 │   │   ├── SettingsRepository.kt
 ├── domain/
 │   ├── model/
 │   │   ├── Note.kt
 │   ├── usecases/
 │   │   ├── AddNoteUseCase.kt
 │   │   ├── GetNotesUseCase.kt
 │   │   ├── DeleteNoteUseCase.kt
 ├── presentation/
 │   ├── screens/
 │   │   ├── HomeScreen.kt
 │   │   ├── AddNoteScreen.kt
 │   │   ├── SettingsScreen.kt
 │   │   ├── DetailsScreen.kt
 │   ├── mvi/
 │   │   ├── NoteEvent.kt
 │   │   ├── NoteState.kt
 │   ├── viewmodel/
 │   │   ├── NoteViewModel.kt
 ├── di/
 │   ├── AppModule.kt
```

---

## **Реалізація коду**
### **1. DataStore (SettingsDataStore.kt)**
```kotlin
class SettingsDataStore @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.dataStore

    suspend fun saveTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }

    val isDarkMode: Flow<Boolean> = dataStore.data
        .map { preferences -> preferences[IS_DARK_MODE] ?: false }

    companion object {
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }
}
```

---

### **2. RealmDB (Note.kt, NoteDao.kt, NoteDatabase.kt)**
#### **Note.kt**
```kotlin
@RealmClass
open class Note : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
    var content: String = ""
}
```

#### **NoteDao.kt**
```kotlin
@Dao
interface NoteDao {
    suspend fun addNote(note: Note)
    suspend fun deleteNote(noteId: ObjectId)
    fun getNotes(): Flow<List<Note>>
}
```

#### **NoteDatabase.kt**
```kotlin
@HiltViewModel
class NoteDatabase @Inject constructor() {
    val realm: Realm = Realm.getDefaultInstance()
}
```

---

### **3. Репозиторій (NoteRepository.kt)**
```kotlin
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getNotes(): Flow<List<Note>> = noteDao.getNotes()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun deleteNote(noteId: ObjectId) {
        noteDao.deleteNote(noteId)
    }
}
```

---

### **4. MVI (NoteEvent.kt, NoteState.kt)**
#### **NoteEvent.kt**
```kotlin
sealed class NoteEvent {
    data class AddNote(val note: Note) : NoteEvent()
    data class DeleteNote(val noteId: ObjectId) : NoteEvent()
    object LoadNotes : NoteEvent()
}
```

#### **NoteState.kt**
```kotlin
data class NoteState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false
)
```

---

### **5. ViewModel (NoteViewModel.kt)**
```kotlin
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    var state by mutableStateOf(NoteState())
        private set

    init {
        onEvent(NoteEvent.LoadNotes)
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.LoadNotes -> {
                viewModelScope.launch {
                    repository.getNotes().collect { notes ->
                        state = state.copy(notes = notes, isLoading = false)
                    }
                }
            }
            is NoteEvent.AddNote -> {
                viewModelScope.launch {
                    repository.addNote(event.note)
                }
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    repository.deleteNote(event.noteId)
                }
            }
        }
    }
}
```

---

### **6. UI (4 екрани)**
#### **HomeScreen.kt**
```kotlin
@Composable
fun HomeScreen(navController: NavController, viewModel: NoteViewModel = hiltViewModel()) {
    val state = viewModel.state

    LazyColumn {
        items(state.notes) { note ->
            Text(note.title)
            Button(onClick = { viewModel.onEvent(NoteEvent.DeleteNote(note.id)) }) {
                Text("Delete")
            }
        }
    }
    FloatingActionButton(onClick = { navController.navigate("add_note") }) {
        Icon(Icons.Default.Add, contentDescription = "Add Note")
    }
}
```

#### **AddNoteScreen.kt**
```kotlin
@Composable
fun AddNoteScreen(navController: NavController, viewModel: NoteViewModel = hiltViewModel()) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        TextField(value = content, onValueChange = { content = it }, label = { Text("Content") })
        Button(onClick = {
            viewModel.onEvent(NoteEvent.AddNote(Note().apply { this.title = title; this.content = content }))
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}
```

#### **SettingsScreen.kt**
```kotlin
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val isDarkMode by viewModel.isDarkMode.collectAsState(initial = false)

    Switch(checked = isDarkMode, onCheckedChange = { viewModel.toggleTheme(it) })
}
```

#### **DetailsScreen.kt**
```kotlin
@Composable
fun DetailsScreen(noteId: ObjectId, viewModel: NoteViewModel = hiltViewModel()) {
    val note = viewModel.state.notes.find { it.id == noteId }
    Text(text = note?.content ?: "Note not found")
}
```

---

### **7. DI (AppModule.kt)**
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }
}
```

---

### **Висновок**
🔹 Проєкт реалізує **MVI**, використовуючи **DataStore**, **RealmDB**, **DI (Hilt)**.  
🔹 Структура правильна та масштабована.  
🔹 Усі дані керуються через **ViewModel** та **State** для стабільного UI.

Хочеш щось покращити або додати? 😊
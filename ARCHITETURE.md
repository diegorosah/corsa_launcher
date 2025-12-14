# 🏗️ Arquitetura - Corsa Launcher

Documentação técnica detalhada da arquitetura do **Corsa Launcher**. 

---

## 📐 Visão Geral

O Corsa Launcher segue os princípios de **Clean Architecture** combinado com o padrão **MVVM (Model-View-ViewModel)**, garantindo: 

- ✅ **Separação de responsabilidades**
- ✅ **Testabilidade**
- ✅ **Manutenibilidade**
- ✅ **Escalabilidade**
- ✅ **Independência de frameworks**

---

## 🎯 Camadas da Arquitetura

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│                  (Jetpack Compose UI)                    │
│  ┌────────────┐  ┌────────────┐  ┌─────────────────┐   │
│  │ HomeScreen │  │ Dashboard  │  │ SettingsScreen  │   │
│  └────────────┘  └────────────┘  └─────────────────┘   │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                   VIEWMODEL LAYER                        │
│  ┌──────────────┐  ┌────────────────┐  ┌─────────────┐ │
│  │ HomeViewModel│  │NavigationViewModel│ │MediaViewModel│ │
│  └──────────────┘  └────────────────┘  └─────────────┘ │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                   DOMAIN LAYER                           │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────────┐ │
│  │  UseCases  │  │    Models    │  │  Repositories   │ │
│  └────────────┘  └──────────────┘  └─────────────────┘ │
└────────────────────┬────────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────────┐
│                     DATA LAYER                           │
│  ┌─────────────────┐  ┌──────────────┐  ┌────────────┐ │
│  │RepositoryImpl   │  │ DataSources  │  │  Receivers │ │
│  └─────────────────┘  └──────────────┘  └────────────┘ │
└─────────────────────────────────────────────────────────┘
```

---

## 📦 Estrutura de Pacotes

```
app/src/main/java/com/diegorosah/corsalauncher/
│
├── 📱 ui/                              # Camada de Apresentação
│   ├── theme/                          # Tema e estilos Compose
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   │
│   ├── home/                           # Tela Principal
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   └── components/
│   │       ├── AppGrid.kt
│   │       ├── SearchBar.kt
│   │       └── AppIcon.kt
│   │
│   ├── navigation/                     # Widget de Navegação
│   │   ├── NavigationWidget.kt
│   │   ├── NavigationViewModel.kt
│   │   └── components/
│   │       ├── SpeedDisplay.kt
│   │       ├── SpeedLimitDisplay.kt
│   │       └── RadarDistance.kt
│   │
│   ├── media/                          # Widget de Música
│   │   ├── MediaWidget.kt
│   │   ├── MediaViewModel.kt
│   │   └── components/
│   │       ├── AlbumArt.kt
│   │       ├── MediaControls.kt
│   │       └── TrackInfo.kt
│   │
│   ├── dashboard/                      # Painel de Instrumentos
│   │   ├── DashboardScreen.kt
│   │   ├── DashboardViewModel.kt
│   │   └── components/
│   │       ├── Speedometer.kt
│   │       ├── TemperatureIndicator.kt
│   │       └── ConnectivityIndicators.kt
│   │
│   └── settings/                       # Configurações
│       ├── SettingsScreen.kt
│       ├── SettingsViewModel. kt
│       └── components/
│
├── 💼 domain/                          # Camada de Domínio
│   ├── model/                          # Modelos de Dados
│   │   ├── AppInfo.kt
│   │   ├── NavigationData.kt
│   │   ├── MediaData.kt
│   │   ├── SpeedData.kt
│   │   └── ConnectivityStatus.kt
│   │
│   ├── repository/                     # Interfaces dos Repositórios
│   │   ├── AppRepository.kt
│   │   ├── NavigationRepository.kt
│   │   ├── MediaRepository.kt
│   │   └── SettingsRepository.kt
│   │
│   └── usecase/                        # Casos de Uso
│       ├── GetInstalledAppsUseCase. kt
│       ├── LaunchAppUseCase.kt
│       ├── GetNavigationDataUseCase.kt
│       └── ControlMediaUseCase.kt
│
├── 🗄️ data/                            # Camada de Dados
│   ├── repository/                     # Implementações
│   │   ├── AppRepositoryImpl.kt
│   │   ├── NavigationRepositoryImpl.kt
│   │   ├── MediaRepositoryImpl.kt
│   │   └── SettingsRepositoryImpl. kt
│   │
│   ├── receiver/                       # BroadcastReceivers
│   │   ├── WazeBroadcastReceiver.kt
│   │   ├── GoogleMapsBroadcastReceiver.kt
│   │   └── NavigationDataReceiver.kt
│   │
│   ├── service/                        # Serviços
│   │   ├── MediaControllerService.kt
│   │   └── ConnectivityService.kt
│   │
│   └── local/                          # Armazenamento Local
│       ├── PreferencesManager.kt
│       └── AppCache.kt
│
├── 🔧 util/                            # Utilitários
│   ├── Extensions.kt
│   ├── Constants.kt
│   └── PermissionHelper.kt
│
└── MainActivity.kt                     # Activity Principal
```

---

## 🧩 Componentes Principais

### 1. **Presentation Layer (UI)**

#### Jetpack Compose
- **100% Compose** - Sem XML layouts
- **Material 3** - Design system moderno
- **State Hoisting** - Estados gerenciados pelos ViewModels

#### ViewModels
```kotlin
class HomeViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val launchAppUseCase: LaunchAppUseCase
) : ViewModel() {
    
    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    val filteredApps: StateFlow<List<AppInfo>> = combine(
        apps,
        searchQuery
    ) { apps, query ->
        if (query.isEmpty()) apps
        else apps.filter { it. name.contains(query, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted. Lazily, emptyList())
    
    fun loadApps() {
        viewModelScope.launch {
            getInstalledAppsUseCase()
                .collect { _apps.value = it }
        }
    }
    
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    fun launchApp(packageName: String) {
        viewModelScope.launch {
            launchAppUseCase(packageName)
        }
    }
}
```

---

### 2. **Domain Layer**

#### Models
```kotlin
// AppInfo.kt
data class AppInfo(
    val name: String,
    val packageName: String,
    val icon:  Drawable
)

// NavigationData.kt
data class NavigationData(
    val currentSpeed: Float,           // km/h
    val speedLimit: Float?,            // km/h (null se não disponível)
    val distanceToRadar: Int?,         // metros (null se não há radar)
    val latitude: Double,
    val longitude:  Double,
    val timestamp: Long,
    val source: NavigationSource       // WAZE, GOOGLE_MAPS
)

enum class NavigationSource {
    WAZE,
    GOOGLE_MAPS,
    UNKNOWN
}

// MediaData.kt
data class MediaData(
    val title: String,
    val artist:  String,
    val album: String,
    val artwork: Bitmap?,
    val isPlaying: Boolean,
    val canSkipToNext: Boolean,
    val canSkipToPrevious: Boolean
)
```

#### Repository Interfaces
```kotlin
interface NavigationRepository {
    fun observeNavigationData(): Flow<NavigationData?>
    suspend fun setPreferredNavigationApp(packageName: String)
    suspend fun getPreferredNavigationApp(): String?
}

interface MediaRepository {
    fun observeMediaData(): Flow<MediaData?>
    suspend fun play()
    suspend fun pause()
    suspend fun skipToNext()
    suspend fun skipToPrevious()
}
```

---

### 3. **Data Layer**

#### NavigationRepositoryImpl
```kotlin
class NavigationRepositoryImpl(
    private val context: Context,
    private val preferencesManager: PreferencesManager
) : NavigationRepository {
    
    private val _navigationData = MutableStateFlow<NavigationData?>(null)
    
    // Timeout:  limpar dados após 30s sem atualização
    private val timeoutJob = viewModelScope.launch {
        _navigationData
            .debounce(30000) // 30 segundos
            .collect {
                if (it != null) {
                    _navigationData.value = null
                }
            }
    }
    
    override fun observeNavigationData(): Flow<NavigationData? > = 
        _navigationData.asStateFlow()
    
    fun updateNavigationData(data: NavigationData) {
        _navigationData.value = data
    }
    
    override suspend fun setPreferredNavigationApp(packageName: String) {
        preferencesManager.setNavigationApp(packageName)
    }
    
    override suspend fun getPreferredNavigationApp(): String? =
        preferencesManager.getNavigationApp()
}
```

#### BroadcastReceivers

**WazeBroadcastReceiver. kt**
```kotlin
class WazeBroadcastReceiver :  BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.waze.speedcam") {
            val speed = intent.getFloatExtra("speed", 0f)
            val speedLimit = intent.getFloatExtra("speedLimit", -1f)
            val distance = intent.getIntExtra("distance", -1)
            val lat = intent.getDoubleExtra("latitude", 0.0)
            val lon = intent.getDoubleExtra("longitude", 0.0)
            
            val navigationData = NavigationData(
                currentSpeed = speed,
                speedLimit = if (speedLimit > 0) speedLimit else null,
                distanceToRadar = if (distance > 0) distance else null,
                latitude = lat,
                longitude = lon,
                timestamp = System.currentTimeMillis(),
                source = NavigationSource.WAZE
            )
            
            // Injetar repository e atualizar
            val repository = // DI fornece instância
            repository.updateNavigationData(navigationData)
        }
    }
}
```

**Registro no AndroidManifest.xml**
```xml
<receiver android:name=".data.receiver.WazeBroadcastReceiver"
          android:exported="true">
    <intent-filter>
        <action android:name="com.waze.speedcam" />
    </intent-filter>
</receiver>
```

---

## 🔄 Fluxos de Dados

### Fluxo de Dados de Navegação

```
┌──────────────────┐
│  Waze/Google Maps│
│  (App Externo)   │
└────────┬─────────┘
         │ Envia Broadcast
         ▼
┌────────────────────────┐
│ WazeBroadcastReceiver  │
│ GoogleMapsBroadcastRec │
└────────┬───────────────┘
         │ Parse Intent
         ▼
┌──────────────────────────────┐
│ NavigationRepositoryImpl     │
│ _navigationData.value = data │
└────────┬─────────────────────┘
         │ StateFlow emite
         ▼
┌───────────────────────┐
│ NavigationViewModel   │
│ observa navigationData│
└────────┬──────────────┘
         │ Expõe State
         ▼
┌──────────────────────┐
│  NavigationWidget    │
│  (Compose UI)        │
│  Renderiza dados     │
└──────────────────────┘
```

### Fluxo de Controle de Mídia

```
┌──────────────────┐
│  User toca Play  │
└────────┬─────────┘
         │
         ▼
┌────────────────────┐
│  MediaWidget       │
│  onClick()         │
└────────┬───────────┘
         │
         ▼
┌────────────────────┐
│  MediaViewModel    │
│  play()            │
└────────┬───────────┘
         │
         ▼
┌────────────────────────┐
│  MediaRepository       │
│  mediaController.play()│
└────────┬───────────────┘
         │
         ▼
┌──────────────────────────┐
│  MediaControllerService  │
│  Envia comando ao SO     │
└────────┬─────────────────┘
         │
         ▼
┌──────────────────┐
│  Spotify/YT Music│
│  Inicia música   │
└────────┬─────────┘
         │ Atualiza MediaSession
         ▼
┌────────────────────────┐
│  MediaRepository       │
│  Listener detecta      │
│  _mediaData.value = x  │
└────────┬───────────────┘
         │
         ▼
┌────────────────────┐
│  MediaWidget       │
│  UI atualizada     │
└────────────────────┘
```

---

## 🔐 Permissões Necessárias

### AndroidManifest.xml

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.diegorosah.corsalauncher">

    <!-- Listar todos os apps instalados -->
    <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
    
    <!-- Localização (backup, caso necessário) -->
    <uses-permission android:name="android. permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    
    <!-- Status de conectividade -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android. permission.BLUETOOTH" />
    <uses-permission android:name="android.permission. BLUETOOTH_CONNECT" />
    
    <!-- Sensor de temperatura (implícito) -->
    
    <!-- Internet (para futuras features) -->
    <uses-permission android:name="android.permission. INTERNET" />
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.CorsaLauncher">
        
        <!-- MainActivity como LAUNCHER -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.CorsaLauncher">
            <intent-filter>
                <action android:name="android.intent.action. MAIN" />
                <category android:name="android.intent.category.HOME" />
                <category android: name="android.intent.category. DEFAULT" />
                <category android:name="android. intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <!-- BroadcastReceivers -->
        <receiver android:name=".data.receiver. WazeBroadcastReceiver"
                  android:exported="true">
            <intent-filter>
                <action android:name="com.waze.speedcam" />
            </intent-filter>
        </receiver>
        
        <receiver android:name=".data.receiver.GoogleMapsBroadcastReceiver"
                  android:exported="true">
            <!-- Intent filters para Google Maps -->
        </receiver>
        
    </application>
</manifest>
```

---

## 🧪 Testabilidade

### Estrutura de Testes

```
app/src/
├── test/                           # Testes Unitários (JUnit)
│   ├── viewmodel/
│   │   ├── HomeViewModelTest.kt
│   │   └── NavigationViewModelTest.kt
│   ├── repository/
│   │   └── NavigationRepositoryTest.kt
│   └── usecase/
│       └── GetNavigationDataUseCaseTest.kt
│
└── androidTest/                    # Testes Instrumentados (Espresso)
    ├── ui/
    │   ├── HomeScreenTest.kt
    │   └── NavigationWidgetTest.kt
    └── integration/
        └── MediaControlIntegrationTest.kt
```

### Exemplo de Teste

```kotlin
class NavigationViewModelTest {
    
    @get:Rule
    val coroutineRule = MainCoroutineRule()
    
    private lateinit var viewModel: NavigationViewModel
    private lateinit var mockRepository: NavigationRepository
    
    @Before
    fun setup() {
        mockRepository = mock()
        viewModel = NavigationViewModel(mockRepository)
    }
    
    @Test
    fun `when navigation data is received, ui state is updated`() = runTest {
        // Arrange
        val testData = NavigationData(
            currentSpeed = 60f,
            speedLimit = 50f,
            distanceToRadar = 500,
            latitude = 0.0,
            longitude = 0.0,
            timestamp = 0L,
            source = NavigationSource.WAZE
        )
        whenever(mockRepository.observeNavigationData())
            .thenReturn(flowOf(testData))
        
        // Act
        viewModel. loadNavigationData()
        advanceUntilIdle()
        
        // Assert
        assertEquals(60f, viewModel.navigationState.value.currentSpeed)
        assertEquals(50f, viewModel.navigationState.value.speedLimit)
        assertTrue(viewModel.navigationState.value.isOverSpeed)
    }
}
```

---

## ⚡ Otimizações de Performance

### 1. Lazy Loading de Apps
```kotlin
LazyVerticalGrid(
    columns = GridCells. Adaptive(minSize = 80.dp)
) {
    items(
        items = apps,
        key = { it.packageName }  // Key para recomposição eficiente
    ) { app ->
        AppIcon(app)
    }
}
```

### 2. Remember e Derivação de Estados
```kotlin
@Composable
fun NavigationWidget(viewModel: NavigationViewModel) {
    val navigationData by viewModel.navigationData.collectAsState()
    
    // Compute apenas quando navigationData muda
    val isOverSpeed = remember(navigationData) {
        navigationData?.let {
            it.speedLimit != null && it.currentSpeed > it.speedLimit
        } ?: false
    }
    
    // UI... 
}
```

### 3. Cache de Ícones
```kotlin
class AppRepositoryImpl {
    private val iconCache = LruCache<String, Drawable>(50)
    
    fun getAppIcon(packageName: String): Drawable {
        return iconCache. get(packageName) ?: run {
            val icon = packageManager.getApplicationIcon(packageName)
            iconCache.put(packageName, icon)
            icon
        }
    }
}
```

### 4. Debounce de Busca
```kotlin
val searchQuery = MutableStateFlow("")

val filteredApps = searchQuery
    .debounce(300) // Aguarda 300ms após última digitação
    .flatMapLatest { query ->
        flow { emit(filterApps(query)) }
    }
    .stateIn(viewModelScope, SharingStarted. Lazily, emptyList())
```

---

## 🔮 Decisões Arquiteturais

### Por que Clean Architecture? 
- **Separação de Responsabilidades:** Cada camada tem um propósito claro
- **Testabilidade:** ViewModels e UseCases são facilmente testáveis
- **Independência:** Mudanças em frameworks não afetam lógica de negócio
- **Escalabilidade:** Fácil adicionar novas features

### Por que Jetpack Compose?
- **Declarativo:** UI é função do estado
- **Menos código:** Reduz boilerplate em ~40%
- **Reatividade:** Integração perfeita com Flow/StateFlow
- **Moderno:** Futuro do desenvolvimento Android

### Por que StateFlow em vez de LiveData?
- **Coroutines-first:** Integração nativa com Kotlin Coroutines
- **Type-safe:** Tipos genéricos sem reflexão
- **Operators:** Combine, map, filter, etc.
- **Sincronização:** Valor atual sempre acessível

### Por que BroadcastReceivers? 
- **Única opção:** Waze e Google Maps não fornecem SDKs públicos
- **Baixo acoplamento:** Não dependemos de bibliotecas externas
- **Compatibilidade:** Funciona em qualquer versão do Android

---

## 📚 Referências

- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)
- [Clean Architecture (Uncle Bob)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture. html)

---

**Arquitetura sólida = Código sustentável 🏗️**
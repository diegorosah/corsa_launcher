# 🤖 GitHub Copilot Instructions - Corsa Launcher

Instruções para o GitHub Copilot entender o contexto e ajudar no desenvolvimento do **Corsa Launcher**.

---

## 📌 Contexto do Projeto

Este é o **Corsa Launcher**, um launcher Android personalizado com foco em uso automotivo. 

### Objetivo
Criar um launcher que centraliza: 
- 🗺️ Informações de navegação (Waze/Google Maps)
- 🎵 Controles de música (MediaSession universal)
- 📊 Painel de instrumentos digital
- 📱 Grade de aplicativos

### Público-Alvo
- Motoristas que usam smartphone/tablet no carro
- Usuários de head units Android automotivos
- Dispositivos **sem GPS integrado** (usa dados do app de navegação)

---

## 🛠️ Diretrizes de Desenvolvimento

### Linguagem e Frameworks

```yaml
Linguagem: Kotlin (100%)
UI: Jetpack Compose (NUNCA XML layouts)
Arquitetura:  MVVM + Clean Architecture
Async:  Kotlin Coroutines + Flow
Persistência: DataStore (não SharedPreferences)
Injeção de Dependência: Manual (preparar para Hilt futuramente)
```

### Padrões de Código

#### Estado Reativo
```kotlin
// ✅ CORRETO: Usar StateFlow
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow(MyState())
    val state: StateFlow<MyState> = _state. asStateFlow()
}

// ❌ ERRADO: Usar LiveData
class MyViewModel : ViewModel() {
    val state = MutableLiveData<MyState>()
}
```

#### ViewModels
```kotlin
// ✅ CORRETO:  Usar viewModelScope
fun loadData() {
    viewModelScope.launch {
        // código assíncrono
    }
}

// ❌ ERRADO: Criar seu próprio scope
fun loadData() {
    CoroutineScope(Dispatchers.IO).launch {
        // código assíncrono
    }
}
```

#### Repositories
```kotlin
// ✅ CORRETO: Interface + Implementação
interface NavigationRepository {
    fun observeData(): Flow<NavigationData?>
}

class NavigationRepositoryImpl :  NavigationRepository {
    override fun observeData(): Flow<NavigationData?> = flow { ...  }
}

// ❌ ERRADO: Apenas classe concreta
class NavigationRepository {
    fun observeData(): Flow<NavigationData?> = flow { ... }
}
```

#### Nomenclatura
```kotlin
// Nomes em INGLÊS
class NavigationViewModel  // ✅
class ViewModelNavegacao   // ❌

// Comentários em PORTUGUÊS quando necessário
// Calcula a distância até o próximo radar
fun calculateRadarDistance() { }
```

---

## 🎨 Tema e UI

### Paleta de Cores

```kotlin
// Tema SEMPRE escuro
val BackgroundBlack = Color(0xFF000000)
val SurfaceDarkGray = Color(0xFF1A1A1A)
val AccentGreen = Color(0xFF00FF41)      // Status ativo, velocidade OK
val AccentRed = Color(0xFFFF0000)        // Alertas, velocidade acima do limite
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFFAAAAAA)
```

### Princípios de UI

1. **Grandes e Legíveis**
   - Ícones mínimos:  48dp (área de toque 56dp)
   - Texto primário: 24sp+
   - Velocidade: 48sp+

2. **Minimalismo**
   - Máximo de ícones visuais
   - Mínimo de texto
   - Sem decorações desnecessárias

3. **Alto Contraste**
   - Branco sobre preto
   - Verde/vermelho para status
   - Sem cinzas muito claros

4. **Responsivo**
   - Testar em smartphones, tablets e head units
   - Layouts adaptativos com `Modifier.fillMaxWidth()`

---

## ⚠️ IMPORTANTE:  Dados de Navegação

### Regra de Ouro
**TODOS os dados de GPS/navegação DEVEM vir dos apps de navegação (Waze/Google Maps).**

### ✅ Faça Isso
```kotlin
// Receber dados via BroadcastReceiver
class WazeBroadcastReceiver :  BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val speed = intent.getFloatExtra("speed", 0f)
        // Processar dados... 
    }
}
```

### ❌ NÃO Faça Isso
```kotlin
// ❌ NUNCA usar LocationManager como fonte principal
class NavigationRepository {
    fun getCurrentSpeed(): Float {
        val location = locationManager.getLastKnownLocation()
        return location. speed // ❌ ERRADO! 
    }
}
```

### Por Quê?
- ✅ Funciona em dispositivos sem GPS integrado
- ✅ Não drena bateria com GPS próprio
- ✅ Dados já processados e filtrados pelo app de navegação
- ✅ Mais preciso (apps têm algoritmos avançados)

### Broadcasts Conhecidos

#### Waze
```kotlin
// Intent Action
"com.waze.speedcam"

// Extras
intent.getFloatExtra("speed", 0f)           // Velocidade atual (km/h)
intent.getFloatExtra("speedLimit", -1f)     // Limite da via (km/h)
intent.getIntExtra("distance", -1)          // Distância até radar (m)
intent.getDoubleExtra("latitude", 0.0)
intent.getDoubleExtra("longitude", 0.0)
```

#### Google Maps
```kotlin
// Pesquisar e documentar broadcasts disponíveis
// Google Maps pode usar APIs diferentes
```

### Fallback
Sempre ter um estado "Navegação Inativa" quando não houver dados: 

```kotlin
@Composable
fun NavigationWidget(data: NavigationData?) {
    if (data == null) {
        // Placeholder:  "Inicie uma navegação"
        InactiveNavigationPlaceholder()
    } else {
        // Exibir dados reais
        ActiveNavigationDisplay(data)
    }
}
```

---

## 🎵 Widget de Música

### MediaSession Integration

```kotlin
// ✅ Usar MediaController/MediaSession
class MediaRepository(context: Context) {
    private val mediaSessionManager = 
        context.getSystemService(Context. MEDIA_SESSION_SERVICE) as MediaSessionManager
    
    fun getActiveController(): MediaController? {
        val controllers = mediaSessionManager.getActiveSessions(null)
        return controllers.firstOrNull()
    }
    
    fun play() {
        getActiveController()?.transportControls?.play()
    }
}
```

### Compatibilidade
- ✅ Spotify
- ✅ YouTube Music
- ✅ Deezer
- ✅ Player padrão do Android
- ✅ Qualquer app que use MediaSession

### Controles Mínimos
- Play/Pause
- Próxima música
- Música anterior

### Metadata
- Título
- Artista
- Álbum
- Artwork (bitmap)

---

## ⚡ Performance

### Otimizações Obrigatórias

1. **Cachear Lista de Apps**
```kotlin
// ❌ Carregar toda vez
fun getApps() {
    packageManager.getInstalledApplications()  // Lento! 
}

// ✅ Cachear e atualizar apenas quando necessário
class AppRepository {
    private var cachedApps: List<AppInfo>? = null
    private var lastUpdate: Long = 0
    
    fun getApps(): List<AppInfo> {
        val now = System.currentTimeMillis()
        if (cachedApps == null || now - lastUpdate > 60000) {
            cachedApps = loadApps()
            lastUpdate = now
        }
        return cachedApps!! 
    }
}
```

2. **Lazy Loading de Ícones**
```kotlin
// Usar LazyVerticalGrid, não Column
LazyVerticalGrid(columns = GridCells.Adaptive(80.dp)) {
    items(apps) { app ->
        AppIcon(app)  // Carrega sob
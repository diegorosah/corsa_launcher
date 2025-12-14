# 🚗 Corsa Launcher

<div align="center">

![Android](https://img.shields.io/badge/Android-8.0%2B-green? logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-100%25-purple?logo=kotlin)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Status-In%20Development-yellow)

**Launcher Android personalizado com foco em uso automotivo**

*Navegação • Música • Dashboard Digital • Tudo em um só lugar*

</div>

---

## 📖 Sobre o Projeto

O **Corsa Launcher** é um launcher Android totalmente personalizado, projetado especificamente para uso em ambientes automotivos. Ele integra informações de navegação, controles de mídia e um painel de instrumentos digital em uma interface limpa e otimizada para uso durante a direção.

### ✨ Características Principais

- 🗺️ **Widget de Navegação Inteligente**
  - Integração com Waze e Google Maps
  - Exibição de velocidade atual e limite da via
  - Distância até o próximo radar
  - Alertas visuais de velocidade

- 🎵 **Player de Música Universal**
  - Compatível com qualquer app que use MediaSession
  - Controles:  Play/Pause, Próximo, Anterior
  - Exibição de artwork, título e artista
  - Suporte para Spotify, YouTube Music e mais

- 📊 **Painel de Instrumentos Digital**
  - Velocímetro em tempo real
  - Indicador de temperatura
  - Status de WiFi, Bluetooth e GPS
  - Design inspirado em painéis automotivos modernos

- 🌑 **Tema Escuro Otimizado**
  - Design minimalista e elegante
  - Otimizado para uso noturno
  - Alto contraste para máxima legibilidade
  - Cores de acento configuráveis

- 📱 **Launcher Completo**
  - Grade de aplicativos instalados
  - Busca rápida de apps
  - Totalmente personalizável

---

## 🎯 Por Que Usar o Corsa Launcher?

### Problema
Usar o smartphone no carro geralmente significa alternar entre múltiplos apps:  navegação, música, e o launcher padrão.  Isso é distrativo e perigoso durante a direção.

### Solução
O Corsa Launcher centraliza tudo em uma única tela otimizada, com informações grandes, claras e fáceis de ver rapidamente. Sem distrações, sem múltiplas apps, apenas o essencial. 

### Diferencial
**Funciona mesmo sem GPS integrado! ** 🎯

Diferente de outros launchers, o Corsa obtém dados de localização diretamente dos apps de navegação (Waze/Google Maps) via broadcasts. Isso significa que funciona perfeitamente em: 
- Tablets WiFi-only sem GPS
- Dispositivos antigos
- Head units Android sem GPS próprio

---

## 📋 Requisitos

### Requisitos Mínimos
- **Android:** 8.0+ (API 26)
- **RAM:** 2GB
- **Armazenamento:** 50MB

### Requisitos Recomendados
- **Android:** 10.0+ (API 29)
- **RAM:** 4GB+
- App de navegação instalado (Waze ou Google Maps)
- App de música compatível com MediaSession

---

## 🚀 Instalação

### Opção 1: Baixar APK (Recomendado para usuários)
1. Vá para a página de [Releases](https://github.com/diegorosah/corsa_launcher/releases)
2. Baixe o arquivo `corsa-launcher-v1.0.0.apk`
3. Instale no dispositivo (habilite "Instalar de fontes desconhecidas")
4. Defina como launcher padrão quando solicitado

### Opção 2: Compilar do Código-Fonte

```bash
# Clone o repositório
git clone https://github.com/diegorosah/corsa_launcher.git
cd corsa_launcher

# Abra no Android Studio
# File > Open > Selecione a pasta do projeto

# Compile e instale
# Run > Run 'app' (Shift+F10)
```

#### Requisitos para Compilação
- Android Studio Hedgehog | 2023.1.1 ou superior
- JDK 17
- Android SDK 34
- Gradle 8.0+

---

## 🎮 Como Usar

### Primeira Configuração

1. **Definir como Launcher Padrão**
   - Após instalar, pressione o botão Home
   - Selecione "Corsa Launcher" e marque "Sempre"

2. **Configurar App de Navegação**
   - Abra as Configurações (ícone de engrenagem)
   - Selecione seu app preferido (Waze ou Google Maps)
   - Garanta que o app tenha permissões de localização

3. **Configurar App de Música**
   - Nas Configurações, escolha seu player preferido
   - Testado com Spotify, YouTube Music e player padrão

4. **Iniciar Navegação**
   - Abra seu app de navegação
   - Inicie uma rota
   - Volte para o Corsa Launcher
   - Os dados aparecerão automaticamente no widget!  🎉

### Dicas de Uso

- **Buscar Apps:** Digite no campo de busca no topo
- **Abrir App:** Toque no ícone do app desejado
- **Controlar Música:** Use os botões do widget de música
- **Ver Configurações:** Toque no ícone de engrenagem

---

## 🔧 Como Funciona a Integração com Apps de Navegação

### Waze
O Waze envia broadcasts com dados de navegação que podemos capturar: 

```kotlin
// Intent Action
com.waze.speedcam

// Extras disponíveis
- speed:  velocidade atual (km/h)
- speedLimit: limite de velocidade da via (km/h)
- distance: distância até próximo radar (metros)
- latitude: latitude atual
- longitude: longitude atual
```

### Google Maps
O Google Maps usa APIs diferentes dependendo da versão.  O Corsa Launcher implementa múltiplos métodos de captura para máxima compatibilidade. 

### Sem GPS Integrado
Todo o sistema é projetado para funcionar **sem acessar o LocationManager** diretamente.  Isso significa:
- ✅ Funciona em tablets WiFi-only
- ✅ Não drena bateria com GPS próprio
- ✅ Usa dados já processados pelo app de navegação
- ✅ Mais preciso (apps de navegação têm melhor filtragem de dados)

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/) 100%
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Arquitetura:** MVVM + Clean Architecture
- **Async:** Kotlin Coroutines + Flow
- **Persistência:** DataStore (preferências)
- **Integração de Mídia:** MediaSession/MediaController
- **Navegação:** BroadcastReceivers para intents de apps externos

### Principais Bibliotecas

```gradle
// Jetpack Compose
implementation "androidx. compose.ui:ui:1.5.4"
implementation "androidx.compose. material3:material3:1.1.2"

// ViewModel e Lifecycle
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2"

// Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"

// DataStore
implementation "androidx.datastore:datastore-preferences:1.0.0"

// Media
implementation "androidx.media:media:1.7.0"
```

---

## 📅 Roadmap

Veja o [ROADMAP.md](ROADMAP.md) completo para detalhes de cada fase.

### ✅ Fase 1 - Fundação (Semanas 1-2)
- Estrutura base do launcher
- Grade de aplicativos
- Busca de apps
- Tema escuro

### 🔄 Fase 2 - Widget de Navegação (Semanas 3-4) - **EM ANDAMENTO**
- Integração com Waze e Google Maps
- Display de velocidade e radares

### 🔜 Fase 3 - Widget de Música (Semanas 5-6)
- Controles universais de mídia
- Integração com MediaSession

### 🔜 Fase 4 - Painel de Instrumentos (Semanas 7-8)
- Dashboard completo
- Indicadores de status

### 🔜 Fase 5 - Polimento (Semanas 9-10)
- Animações e transições
- Otimizações de performance

### 🔜 Fase 6 - Lançamento (Semanas 11-12)
- Testes finais
- Release v1.0.0

---

## 🏗️ Arquitetura

O projeto segue **Clean Architecture** com separação clara de responsabilidades. 

```
app/src/main/java/com/diegorosah/corsalauncher/
├── ui/                 # Camada de Apresentação (Compose)
│   ├── home/          # Tela principal
│   ├── navigation/    # Widget de navegação
│   ├── media/         # Widget de música
│   ├── dashboard/     # Painel de instrumentos
│   └── settings/      # Configurações
├── data/              # Camada de Dados
│   ├── repository/    # Repositórios
│   └── receiver/      # BroadcastReceivers
├── domain/            # Camada de Domínio
│   ├── model/         # Modelos de dados
│   └── usecase/       # Casos de uso
└── util/              # Utilitários
```

Veja [ARCHITECTURE.md](ARCHITECTURE.md) para documentação técnica completa.

---

## 🤝 Contribuindo

Contribuições são muito bem-vindas! 🎉

### Como Contribuir

1. **Fork** o projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. Abra um **Pull Request**

### Diretrizes

- Siga o [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- Use Jetpack Compose para UI (não XML)
- Escreva código em inglês, comentários em português (se necessário)
- Adicione testes quando aplicável
- Atualize a documentação

---

## 📸 Screenshots

> 🚧 **Em desenvolvimento** - Screenshots serão adicionados em breve!

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

```
MIT License

Copyright (c) 2025 Diego Rosa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👤 Autor

**Diego Rosa**
- GitHub: [@diegorosah](https://github.com/diegorosah)

---

## 🙏 Agradecimentos

- Comunidade Android Open Source
- Desenvolvedores do Jetpack Compose
- Equipes do Waze e Google Maps por tornar dados disponíveis

---

## 📞 Suporte

Encontrou um bug ou tem uma sugestão? 
- Abra uma [Issue](https://github.com/diegorosah/corsa_launcher/issues)
- Envie um [Pull Request](https://github.com/diegorosah/corsa_launcher/pulls)

---

<div align="center">

**Feito com ❤️ para tornar a direção mais segura e conectada**

⭐ Se você gostou do projeto, deixe uma estrela! 

</div>
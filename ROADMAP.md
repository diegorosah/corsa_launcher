# 🗺️ Roadmap - Corsa Launcher

Roadmap de desenvolvimento do **Corsa Launcher**, dividido em 6 fases ao longo de 12 semanas.

---

## 📋 Visão Geral

| Fase | Período | Foco | Status |
|------|---------|------|--------|
| Fase 1 | Semanas 1-2 | Fundação do Launcher | ✅ Concluído (Parcial) |
| Fase 2 | Semanas 3-4 | Widget de Navegação | ✅ Concluído (Parcial) |
| Fase 3 | Semanas 5-6 | Widget de Player de Música | ✅ Concluído |
| Fase 4 | Semanas 7-8 | Painel de Instrumentos | ✅ Concluído (Parcial) |
| Fase 5 | Semanas 9-10 | Polimento e UX | 🔜 Planejado |
| Fase 6 | Semanas 11-12 | Testes e Lançamento | 🔜 Planejado |

---

## 🏗️ Fase 1 - Fundação (Semanas 1-2)

**Objetivo:** Criar a estrutura base do launcher Android funcional. 

### Tarefas: 

#### 1. 1 Setup do Projeto
- [x] Criar projeto Android Studio com Kotlin
- [x] Configurar Jetpack Compose como UI toolkit
- [x] Adicionar dependências necessárias (Compose, ViewModel, Coroutines)
- [x] Configurar `build.gradle` com versões adequadas
- [x] Definir `minSdk = 26` (Android 8.0)

#### 1.2 Estrutura Base do Launcher
- [x] Criar `MainActivity` como `LAUNCHER` no `AndroidManifest.xml`
- [x] Implementar tema escuro base com Compose Material3
- [x] Configurar cores do tema (preto #000000, cinza #1A1A1A, verde #00FF41)
- [x] Criar estrutura de navegação básica

#### 1.3 Grade de Aplicativos
- [x] Implementar `AppLauncherRepository` para listar apps instalados
- [x] Usar `PackageManager` para obter lista de apps
- [x] Criar modelo de dados `AppInfo` (nome, ícone, packageName)
- [x] Implementar `HomeScreen` com `LazyVerticalGrid` para exibir apps
- [x] Adicionar funcionalidade de abrir app ao clicar
- [x] Cachear lista de apps para performance

#### 1.4 Busca de Aplicativos
- [x] Adicionar `TextField` de busca no topo da tela
- [x] Implementar filtro em tempo real na lista de apps
- [x] Otimizar busca com debounce (Flow + delay)

#### 1.5 Permissões
- [x] Adicionar `QUERY_ALL_PACKAGES` no manifest
- [x] Solicitar permissão em tempo de execução (se necessário)

**Entregável:** Launcher funcional que substitui o launcher padrão, mostra todos os apps e permite busca.

---

## 🗺️ Fase 2 - Widget de Navegação (Semanas 3-4)

**Objetivo:** Integrar dados de apps de navegação (Waze/Google Maps) para exibir informações no launcher. 

### Tarefas:

#### 2.1 Seletor de App de Navegação
- [x] Criar tela de configurações (`SettingsScreen`)
- [x] Implementar seletor para escolher app de navegação preferido
- [x] Salvar preferência com `DataStore` (Waze, Google Maps, outros)
- [x] Detectar automaticamente apps de navegação instalados

#### 2.2 BroadcastReceiver para Waze
- [x] Criar `WazeBroadcastReceiver` para capturar broadcasts
- [x] Registrar receiver para intent `com.waze.speedcam`
- [x] Extrair dados:  velocidade atual, velocidade da via, distância até radar
- [x] Documentar estrutura dos extras do Waze

#### 2.3 BroadcastReceiver para Google Maps
- [ ] Pesquisar broadcasts disponíveis do Google Maps
- [ ] Criar `GoogleMapsBroadcastReceiver`
- [ ] Implementar parsing de dados de navegação
- [ ] Fallback caso Google Maps use API diferente

#### 2.4 Repository de Dados de Navegação
- [x] Criar `NavigationDataRepository`
- [x] Armazenar dados em `StateFlow` para observação
- [x] Implementar timeout:  limpar dados se não houver atualização por 30s
- [x] Gerenciar estado:  navegação ativa vs inativa

#### 2.5 Widget de Navegação na UI
- [x] Criar `NavigationWidget` composable
- [x] Exibir velocidade atual em destaque
- [x] Exibir velocidade máxima da via
- [x] Exibir distância até próximo radar
- [x] Mostrar ícone do app de navegação ativo
- [x] Animação de cores:  verde (OK), vermelho (acima do limite)
- [x] Placeholder quando navegação não está ativa

#### 2.6 Permissões
- [x] Adicionar permissões para receber broadcasts
- [x] Testar em diferentes versões do Android

**Entregável:** Widget de navegação funcional mostrando dados em tempo real do Waze ou Google Maps.

---

## 🎵 Fase 3 - Widget de Player de Música (Semanas 5-6)

**Objetivo:** Controlar reprodução de música de qualquer app compatível com MediaSession.

### Tarefas:

#### 3.1 Seletor de App de Música
- [x] Adicionar seletor de app de música nas configurações
- [x] Detectar apps instalados com suporte a MediaSession
- [x] Salvar preferência no DataStore

#### 3.2 MediaController Integration
- [x] Criar `MediaControllerService` para gerenciar MediaSession
- [x] Conectar ao `MediaSessionManager` do Android
- [x] Obter `MediaController` do app ativo
- [x] Implementar listeners para mudanças de estado

#### 3.3 Repository de Dados de Mídia
- [x] Criar `MediaRepository`
- [x] Expor estado atual:  tocando, pausado, parado
- [x] Expor metadata: título, artista, álbum, artwork
- [x] Usar StateFlow para observação reativa

#### 3.4 Widget de Player na UI
- [x] Criar `MusicPlayerWidget` composable
- [x] Exibir capa do álbum (artwork)
- [x] Exibir título e artista
- [x] Botão Play/Pause com ícone animado
- [x] Botões Previous/Next
- [x] Placeholder quando nenhuma música está tocando

#### 3.5 Controles de Mídia
- [x] Implementar função de Play/Pause
- [x] Implementar função de próxima música
- [x] Implementar função de música anterior
- [x] Feedback visual ao tocar nos botões

#### 3.6 Testes de Compatibilidade
- [ ] Testar com Spotify
- [ ] Testar com YouTube Music
- [ ] Testar com player padrão do Android
- [ ] Documentar apps testados

**Entregável:** Widget de música totalmente funcional com controles universais.

---

## 📊 Fase 4 - Painel de Instrumentos (Semanas 7-8)

**Objetivo:** Criar painel estilo automotivo com indicadores de status e informações do sistema.

### Tarefas:

#### 4.1 Indicador de Velocidade
- [x] Criar `SpeedIndicator` composable (circular estilo velocímetro)
- [x] Obter velocidade do `NavigationDataRepository`
- [x] Animação suave de mudança de velocidade
- [x] Unidades: km/h (configurável para mph)
- [x] Cores dinâmicas baseadas em limite de velocidade

#### 4.2 Sensor de Temperatura
- [x] Acessar sensor de temperatura do dispositivo (via Bateria)
- [x] Criar `TemperatureIndicator` composable
- [x] Exibir temperatura em °C (configurável para °F)
- [x] Aviso visual se temperatura estiver alta (>40°C)

#### 4.3 Indicadores de Conectividade
- [x] Criar `ConnectivityIndicators` composable
- [x] **WiFi:** Monitorar `ConnectivityManager`, ícone verde/cinza
- [x] **Bluetooth:** Monitorar `BluetoothAdapter`, ícone azul/cinza
- [x] **GPS:** Status baseado em dados de navegação, ícone verde/vermelho
- [x] Layout horizontal compacto no topo da tela

#### 4.4 Layout do Dashboard
- [x] Criar `DashboardScreen` composable
- [x] Organizar widgets em grid responsivo
- [x] Widget de navegação em destaque (topo/centro)
- [x] Player de música na parte inferior
- [x] Indicadores de status no topo
- [x] Velocímetro em posição de destaque

#### 4.5 Integração
- [x] Integrar todos os widgets em `MainActivity`
- [x] Implementar transições suaves entre estados
- [x] Otimizar recomposições do Compose

**Entregável:** Dashboard completo com todas as informações automotivas integradas.

---

## ✨ Fase 5 - Polimento e UX (Semanas 9-10)

**Objetivo:** Melhorar experiência do usuário e otimizar para uso durante direção.

### Tarefas:

#### 5.1 Animações e Transições
- [x] Adicionar animações de entrada/saída para widgets (Cards de alerta)
- [ ] Transição suave ao abrir apps
- [ ] Animação de loading enquanto carrega apps
- [x] Efeito visual ao mudar de velocidade (Velocímetro dinâmico)
- [ ] Animação de pulsação para alertas

#### 5.2 Otimização de Performance
- [ ] Profiling com Android Profiler
- [ ] Reduzir recomposições desnecessárias
- [ ] Lazy loading de ícones de apps
- [ ] Cache de bitmaps
- [ ] Reduzir uso de bateria em segundo plano

#### 5.3 Tela de Configurações Completa
- [ ] Design da tela de configurações
- [ ] Seção:  Navegação (app preferido, unidades)
- [ ] Seção:  Música (app preferido)
- [ ] Seção:  Dashboard (widgets visíveis, layout)
- [ ] Seção: Tema (intensidade do escuro, cores de acento)
- [ ] Seção: Sobre (versão, créditos, licença)

#### 5.4 Modo Noturno Otimizado
- [ ] Ajustar brilho automático para condução noturna
- [ ] Reduzir brilho de elementos brancos
- [ ] Aumentar contraste para legibilidade
- [ ] Modo "Ultra Dark" opcional

#### 5.5 Usabilidade Durante Direção
- [ ] Aumentar área de toque dos botões (mínimo 48dp)
- [ ] Reduzir número de toques necessários
- [ ] Gestos simples:  swipe para abrir apps recentes
- [ ] Confirmação visual clara de ações
- [ ] Testar com luvas

#### 5.6 Acessibilidade
- [ ] Adicionar content descriptions
- [ ] Suporte a TalkBack
- [ ] Contraste de cores adequado (WCAG AA)

**Entregável:** Launcher polido, rápido e otimizado para uso automotivo seguro.

---

## 🧪 Fase 6 - Testes e Lançamento (Semanas 11-12)

**Objetivo:** Garantir qualidade e preparar para lançamento.

### Tarefas:

#### 6.1 Testes em Dispositivos
- [ ] Testar em smartphones (diferentes tamanhos)
- [ ] Testar em tablets 7-10"
- [ ] Testar em head units Android automotivos
- [ ] Testar em diferentes versões:  Android 8, 9, 10, 11, 12, 13, 14
- [ ] Documentar dispositivos testados e issues encontrados

#### 6.2 Testes Sem GPS Integrado
- [ ] Testar em tablet WiFi-only
- [ ] Confirmar que dados vêm 100% do app de navegação
- [ ] Verificar comportamento quando navegação não está ativa
- [ ] Testar fallbacks e placeholders

#### 6.3 Testes de Integração
- [ ] Waze + Spotify
- [ ] Google Maps + YouTube Music
- [ ] Diferentes combinações de apps
- [ ] Trocar app de navegação em tempo real
- [ ] Trocar app de música em tempo real

#### 6.4 Correção de Bugs
- [ ] Priorizar bugs críticos (crashes)
- [ ] Corrigir bugs de UI (layouts quebrados)
- [ ] Corrigir bugs de performance
- [ ] Revisar todos os TODOs no código

#### 6.5 Documentação Final
- [ ] Atualizar README com screenshots reais
- [ ] Criar guia de usuário
- [ ] Documentar broadcasts conhecidos de apps de navegação
- [ ] Criar FAQ para problemas comuns
- [ ] Adicionar CONTRIBUTING.md

#### 6.6 Preparação para Release
- [ ] Definir versão 1.0.0
- [ ] Gerar APK de release assinado
- [ ] Criar release notes
- [ ] Preparar assets para Google Play (se aplicável)
- [ ] Screenshots e descrições
- [ ] Vídeo de demonstração

#### 6.7 Publicação
- [ ] Criar release no GitHub
- [ ] Publicar na Google Play Store (opcional)
- [ ] Compartilhar em comunidades Android
- [ ] Post no Reddit (r/androidapps, r/androiddev)

**Entregável:** Corsa Launcher v1.0.0 publicado e pronto para uso!  🎉

---

## 🔮 Futuras Melhorias (Pós-v1.0)

Ideias para versões futuras: 

- **Integração com OBD-II:** Dados reais do carro (RPM, temperatura do motor)
- **Modo passageiro:** Layout diferente para quem não está dirigindo
- **Widgets customizáveis:** Usuário escolhe quais widgets exibir
- **Temas personalizados:** Múltiplas paletas de cores
- **Suporte a Android Auto:** Integração nativa
- **Voice commands:** Controle por voz
- **Modo econômico:** Reduzir consumo de bateria ao máximo
- **Estatísticas de viagem:** Distância percorrida, tempo de viagem
- **Integração com calendário:** Mostrar próximo compromisso e ETA

---

## 📊 Métricas de Sucesso

- ✅ Launcher inicia em menos de 2 segundos
- ✅ Zero crashes em uso normal
- ✅ Funciona sem GPS integrado
- ✅ Compatível com Waze, Google Maps, Spotify, YouTube Music
- ✅ Bateria:  consumo menor que 5% por hora em uso ativo
- ✅ UI responsiva mesmo com múltiplos widgets ativos

---

## 🤝 Contribuições

Seguindo o roadmap?  Contribuições são bem-vindas! 
- Cada fase pode ser desenvolvida de forma independente
- Crie issues para bugs ou sugestões
- Pull requests devem seguir o padrão do projeto (veja ARCHITECTURE.md)

**Vamos construir o melhor launcher automotivo juntos!  🚗💨**
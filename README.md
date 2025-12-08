# Cannon Game

## Autores
- Santiago Paiva

## Descrição do Módulo

Cannon Game é um jogo mobile desenvolvido em Android que implementa um sistema de tiro com canhão. O jogador controla o ângulo do canhão através de toques na tela e deve acertar alvos móveis dentro de um tempo limitado.

O projeto foi desenvolvido seguindo as diretrizes do Capítulo 6, utilizando componentes fundamentais do desenvolvimento Android para jogos 2D.

## Recursos Implementados

### Core Game Engine
- **SurfaceView + SurfaceHolder**: Sistema de renderização de baixo nível para desenho eficiente
- **Game Loop em Thread Separada**: Loop de atualização rodando a 60 FPS em thread dedicada
- **Animação Quadro a Quadro**: Sistema de animação fluida com controle de tempo

### Elementos de Jogo
- **Canhão**: Elemento controlável pelo jogador com rotação baseada em ângulo
- **Projéteis (Cannonball)**: Sistema de física com gravidade e trajetória realista
- **Alvos (Target)**: Alvos móveis com movimento vertical
- **Obstáculos (Blocker)**: Elementos que bloqueiam projéteis

### Mecânicas de Jogo
- **Sistema de Pontuação**: 10 pontos por alvo destruído
- **Temporizador**: 30 segundos de jogo
- **Detecção de Colisões**: Sistema preciso de colisão círculo-retângulo
- **Spawn Automático**: Geração periódica de alvos a cada 1.5 segundos

### Interação
- **Controle por Toque**:
  - Arrastar para ajustar ângulo do canhão
  - Soltar para disparar projétil
  - Tocar após game over para reiniciar
- **Linha de Mira**: Indicador visual semi-transparente da trajetória

### Áudio
- **SoundManager**: Sistema de gerenciamento de efeitos sonoros
- **Suporte a Múltiplos Sons**: Fire, Hit, Miss (estrutura pronta para arquivos de áudio)
- **SoundPool**: Reprodução eficiente de efeitos sonoros

### Interface
- **HUD**: Exibição de pontuação e tempo restante
- **Tela Cheia**: Modo imersivo sem barras de sistema
- **Orientação Landscape**: Jogo otimizado para modo paisagem

### Renderização com Canvas/Paint
- Desenho customizado de todos elementos
- Sistema de cores configurável via resources
- Anti-aliasing para gráficos suaves
- Múltiplas camadas de renderização

## Como Executar

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- Android SDK API 21 (Android 5.0) ou superior
- Dispositivo Android ou emulador

### Passos para Execução

1. **Clone o repositório**
   ```bash
   git clone git@github.com:sant125/trab-dudu.git
   cd trab-dudu
   ```

2. **Abra o projeto no Android Studio**
   - File → Open → Selecione a pasta do projeto

3. **Sincronize as dependências**
   - O Android Studio automaticamente sincronizará o Gradle

4. **Execute o aplicativo**
   - Conecte um dispositivo Android via USB (com depuração USB ativada) ou inicie um emulador
   - Clique em "Run" (Shift + F10) ou no ícone de play verde

5. **Como Jogar**
   - Arraste o dedo na tela para ajustar o ângulo do canhão
   - Solte o dedo para disparar
   - Acerte os alvos vermelhos móveis
   - Evite desperdiçar munição nos obstáculos pretos
   - Faça a maior pontuação possível em 30 segundos
   - Toque na tela após game over para jogar novamente

## Estrutura do Projeto

```
app/src/main/
├── java/com/santiago/cannongame/
│   ├── MainActivity.java           # Activity principal
│   ├── CannonGameView.java         # View principal do jogo
│   ├── Cannon.java                 # Classe do canhão
│   ├── Cannonball.java             # Classe do projétil
│   ├── Target.java                 # Classe do alvo
│   ├── Blocker.java                # Classe do obstáculo
│   └── SoundManager.java           # Gerenciador de sons
├── res/
│   ├── values/
│   │   ├── strings.xml             # Strings do app
│   │   └── colors.xml              # Definições de cores
│   └── layout/                     # Layouts (não utilizados no jogo)
└── AndroidManifest.xml             # Configurações do app
```

## Tecnologias Utilizadas

- **Linguagem**: Java
- **SDK**: Android SDK 34
- **Build System**: Gradle 8.2.0
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 34 (Android 14)

## Próximos Passos

- [ ] Adicionar arquivos de áudio reais para efeitos sonoros
- [ ] Implementar sistema de níveis de dificuldade
- [ ] Adicionar efeitos visuais de partículas
- [ ] Criar sistema de high scores com persistência
- [ ] Implementar power-ups e diferentes tipos de projéteis
- [ ] Adicionar música de fundo
- [ ] Criar menu inicial e tela de configurações

## Licença

Este é um projeto acadêmico desenvolvido para fins educacionais.

---

🎮 Desenvolvido seguindo as diretrizes do Capítulo 6

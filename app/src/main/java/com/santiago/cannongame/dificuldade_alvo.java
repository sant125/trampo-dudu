package com.santiago.cannongame;

public class DificuldadeAlvo {

    public enum NivelDificuldade {
        FACIL,
        MEDIO,
        DIFICIL,
        EXTREMO
    }

    private NivelDificuldade nivelAtual;
    private double tempoDecorrido;

    public DificuldadeAlvo() {
        this.nivelAtual = NivelDificuldade.FACIL;
        this.tempoDecorrido = 0;
    }

    // TODO: calcular velocidade baseada no nível atual
    public float calcularVelocidadeAlvo() {
        return 100.0f;
    }

    // TODO: ajustar intervalo de spawn por dificuldade
    public float calcularIntervaloSpawn() {
        return 1.5f;
    }

    // TODO: aumentar dificuldade conforme tempo passa
    public void atualizarDificuldade(double deltaTime) {
        tempoDecorrido += deltaTime;
    }

    public void resetar() {
        nivelAtual = NivelDificuldade.FACIL;
        tempoDecorrido = 0;
    }

    public NivelDificuldade getNivelAtual() {
        return nivelAtual;
    }
}

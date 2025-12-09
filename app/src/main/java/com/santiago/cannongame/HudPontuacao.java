package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;

public class HudPontuacao {

    private Paint textoPaint;
    private Paint barraPaint;
    private Paint fundoPaint;

    private int pontuacao;
    private double tempoRestante;
    private int comboAtual;
    private int alvosDestruidos;
    private int projeteisDeparados;

    public HudPontuacao() {
        inicializarPaints();
        resetar();
    }

    private void inicializarPaints() {
        textoPaint = new Paint();
        textoPaint.setColor(0xFF000000);
        textoPaint.setTextSize(40);
        textoPaint.setAntiAlias(true);

        barraPaint = new Paint();
        barraPaint.setAntiAlias(true);

        fundoPaint = new Paint();
        fundoPaint.setAntiAlias(true);
    }

    // TODO: desenhar barra de tempo no topo
    public void desenharBarraTempo(Canvas canvas, int larguraTela, int alturaTela) {
    }

    // TODO: painel de pontuação com fundo e ícone
    public void desenharPainelPontuacao(Canvas canvas) {
        canvas.drawText("Pontos: " + pontuacao, 30, 50, textoPaint);
    }

    // TODO: mostrar combo quando > 1
    public void desenharCombo(Canvas canvas, int larguraTela) {
        if (comboAtual > 1) {
            // desenhar com escala aumentada
        }
    }

    // TODO: tela de estatísticas finais
    public void desenharEstatisticasFinais(Canvas canvas, int larguraTela, int alturaTela) {
        // pontuação, alvos destruídos, precisão
    }

    public void incrementarCombo() {
        comboAtual++;
    }

    public void resetarCombo() {
        comboAtual = 0;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setTempoRestante(double tempo) {
        this.tempoRestante = tempo;
    }

    public void incrementarAlvosDestruidos() {
        this.alvosDestruidos++;
    }

    public void incrementarProjeteisDeparados() {
        this.projeteisDeparados++;
    }

    public void resetar() {
        pontuacao = 0;
        comboAtual = 0;
        alvosDestruidos = 0;
        projeteisDeparados = 0;
    }

    public float calcularPrecisao() {
        if (projeteisDeparados == 0) return 0;
        return (float) alvosDestruidos / projeteisDeparados * 100;
    }
}

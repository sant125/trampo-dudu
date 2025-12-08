package com.santiago.cannongame;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedbackVisual {

    private List<Particula> particulas;
    private List<TextoFlutuante> textosFlututantes;
    private Paint particulaPaint;

    public FeedbackVisual() {
        particulas = new ArrayList<>();
        textosFlututantes = new ArrayList<>();
        particulaPaint = new Paint();
        particulaPaint.setAntiAlias(true);
    }

    // TODO: criar explosão com múltiplas partículas
    public void criarExplosao(float x, float y, int cor) {
        // for (int i = 0; i < 20; i++) { ... }
    }

    // TODO: adicionar texto que sobe e desaparece
    public void adicionarTextoFlutuante(float x, float y, String texto) {
    }

    public void atualizar(double deltaTime) {
        Iterator<Particula> it = particulas.iterator();
        while (it.hasNext()) {
            Particula p = it.next();
            p.atualizar(deltaTime);
            if (p.vida <= 0) {
                it.remove();
            }
        }
    }

    public void desenhar(Canvas canvas) {
        for (Particula p : particulas) {
            canvas.drawCircle(p.x, p.y, 5, particulaPaint);
        }
    }

    private static class Particula {
        float x, y;
        float velocidadeX, velocidadeY;
        int cor;
        float vida;

        void atualizar(double deltaTime) {
            x += velocidadeX * deltaTime;
            y += velocidadeY * deltaTime;
            vida -= deltaTime;
        }
    }

    private static class TextoFlutuante {
        float x, y;
        String texto;
        float alpha;
    }
}

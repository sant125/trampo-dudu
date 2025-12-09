package com.santiago.cannongame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CannonGameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private volatile boolean running = false;

    // Dimensões da tela
    private int screenWidth;
    private int screenHeight;

    // Elementos do jogo
    private Cannon cannon;
    private List<Target> targets;
    private List<Cannonball> cannonballs;
    private List<Blocker> blockers;
    private DificuldadeAlvo dificuldade;
    private FeedbackVisual feedbackVisual;
    private HudPontuacao hudPontuacao;

    // Estado do jogo
    private int score;
    private double timeRemaining;
    private boolean gameOver;
    private Random random;

    // Constantes
    private static final double GAME_TIME = 30.0; // 30 segundos
    private static final float TARGET_SPAWN_INTERVAL = 1.5f;
    private static final int TARGET_WIDTH = 40;
    private static final int TARGET_HEIGHT = 80;
    private static final float CANNONBALL_VELOCITY = 700f;

    // Controle de spawn
    private double timeSinceLastSpawn;

    // Paint objects
    private Paint backgroundPaint;
    private Paint textPaint;
    private Paint aimLinePaint;

    // Som
    private SoundManager soundManager;
    private Context context;

    public CannonGameView(Context context) {
        super(context);
        this.context = context;

        // Registrar callback do SurfaceHolder
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // Inicializar listas
        targets = new ArrayList<>();
        cannonballs = new ArrayList<>();
        blockers = new ArrayList<>();
        random = new Random();

        // Inicializar som
        soundManager = new SoundManager(context);
        dificuldade = new DificuldadeAlvo();
        feedbackVisual = new FeedbackVisual();
        hudPontuacao = new HudPontuacao();

        // Inicializar Paint
        initializePaint();

        // Criar thread do jogo
        gameThread = new GameThread();
    }

    private void initializePaint() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xFF87CEEB); // Sky blue

        textPaint = new Paint();
        textPaint.setColor(0xFF000000); // Black
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);

        aimLinePaint = new Paint();
        aimLinePaint.setColor(0x88FF0000); // Semi-transparent red
        aimLinePaint.setStrokeWidth(3);
        aimLinePaint.setAntiAlias(true);
        aimLinePaint.setStyle(Paint.Style.STROKE);
    }

    private void initializeGame() {
        // Criar canhão
        cannon = new Cannon(screenWidth / 10, screenHeight - 100, 50);

        // Resetar estado
        score = 0;
        timeRemaining = GAME_TIME;
        gameOver = false;
        timeSinceLastSpawn = 0;

        // Limpar listas
        targets.clear();
        cannonballs.clear();
        blockers.clear();

        dificuldade.resetar();
        hudPontuacao.resetar();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Surface foi criada, podemos começar a desenhar
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;

        // Inicializar jogo quando tela estiver pronta
        if (cannon == null) {
            initializeGame();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface foi destruída, parar o loop
        pause();
    }

    public void resume() {
        if (!running) {
            running = true;
            gameThread = new GameThread();
            gameThread.start();
        }
    }

    public void pause() {
        running = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseResources() {
        pause();
        if (soundManager != null) {
            soundManager.release();
        }
    }

    private void update(double elapsedTime) {
        if (gameOver || cannon == null) {
            return;
        }

        // Atualizar tempo
        timeRemaining -= elapsedTime;
        if (timeRemaining <= 0) {
            gameOver = true;
            timeRemaining = 0;
            return;
        }

        // Spawn de alvos
        dificuldade.atualizarDificuldade(elapsedTime);
        timeSinceLastSpawn += elapsedTime;
        float intervalo = dificuldade.calcularIntervaloSpawn();
        if (timeSinceLastSpawn >= intervalo) {
            spawnTarget();
            timeSinceLastSpawn = 0;
        }

        // Atualizar alvos
        Iterator<Target> targetIterator = targets.iterator();
        while (targetIterator.hasNext()) {
            Target target = targetIterator.next();
            target.update(elapsedTime);

            // Remover alvos fora da tela
            if (target.isOutOfBounds(screenHeight)) {
                targetIterator.remove();
            }
        }

        // Atualizar blockers
        Iterator<Blocker> blockerIterator = blockers.iterator();
        while (blockerIterator.hasNext()) {
            Blocker blocker = blockerIterator.next();
            blocker.update(elapsedTime);

            // Remover blockers fora da tela
            if (blocker.isOutOfBounds(screenHeight)) {
                blockerIterator.remove();
            }
        }

        // Atualizar projéteis
        Iterator<Cannonball> ballIterator = cannonballs.iterator();
        while (ballIterator.hasNext()) {
            Cannonball ball = ballIterator.next();
            ball.update(elapsedTime);

            // Remover projéteis fora da tela
            if (ball.isOutOfBounds(screenWidth, screenHeight)) {
                ballIterator.remove();
            }
        }

        // Verificar colisões
        checkCollisions();

        feedbackVisual.atualizar(elapsedTime);
        hudPontuacao.setPontuacao(score);
        hudPontuacao.setTempoRestante(timeRemaining);
    }

    private void spawnTarget() {
        // Spawnar alvo na direita da tela
        int x = screenWidth - TARGET_WIDTH - 50;
        int y = random.nextInt(screenHeight - TARGET_HEIGHT - 200) + 100;
        float velocidadeBase = dificuldade.calcularVelocidadeAlvo();
        float velocityY = (random.nextFloat() * velocidadeBase - velocidadeBase/2);

        targets.add(new Target(x, y, TARGET_WIDTH, TARGET_HEIGHT, velocityY));
    }

    private void checkCollisions() {
        for (Cannonball ball : cannonballs) {
            // Verificar colisão com alvos
            for (Target target : targets) {
                if (target.checkCollision(ball)) {
                    ball.deactivate();
                    score += 10;
                    hudPontuacao.incrementarAlvosDestruidos();
                    hudPontuacao.incrementarCombo();
                    feedbackVisual.criarExplosao(ball.getPosition().x, ball.getPosition().y, 0xFFFF0000);
                    soundManager.playSound(SoundManager.SOUND_HIT);
                    break;
                }
            }

            // Verificar colisão com blockers
            for (Blocker blocker : blockers) {
                if (blocker.checkCollision(ball)) {
                    ball.deactivate();
                    break;
                }
            }
        }
    }

    public void fireCannonball() {
        if (!gameOver && cannon != null) {
            Point cannonPos = cannon.getPosition();
            float startX = cannonPos.x + (float) (Math.cos(cannon.getAngle()) * cannon.getBarrelLength());
            float startY = cannonPos.y - (float) (Math.sin(cannon.getAngle()) * cannon.getBarrelLength());

            Cannonball ball = new Cannonball(startX, startY, (float) cannon.getAngle(), CANNONBALL_VELOCITY, 15);
            cannonballs.add(ball);

            hudPontuacao.incrementarProjeteisDeparados();

            // Reproduzir som de disparo
            soundManager.playSound(SoundManager.SOUND_FIRE);
        }
    }

    private void draw() {
        Canvas canvas = null;
        SurfaceHolder holder = getHolder();

        try {
            canvas = holder.lockCanvas();

            if (canvas != null) {
                synchronized (holder) {
                    // Desenhar fundo
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

                    // Desenhar linha de mira
                    if (cannon != null && !gameOver) {
                        drawAimLine(canvas);
                    }

                    // Desenhar alvos
                    for (Target target : targets) {
                        target.draw(canvas);
                    }

                    feedbackVisual.desenhar(canvas);

                    // Desenhar blockers
                    for (Blocker blocker : blockers) {
                        blocker.draw(canvas);
                    }

                    // Desenhar projéteis
                    for (Cannonball ball : cannonballs) {
                        ball.draw(canvas);
                    }

                    // Desenhar canhão
                    if (cannon != null) {
                        cannon.draw(canvas);
                    }

                    // Desenhar HUD (pontuação e tempo)
                    drawHUD(canvas);
                }
            }
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawHUD(Canvas canvas) {
        hudPontuacao.desenharPainelPontuacao(canvas);
        hudPontuacao.desenharBarraTempo(canvas, screenWidth, screenHeight);
        hudPontuacao.desenharCombo(canvas, screenWidth);

        if (gameOver) {
            hudPontuacao.desenharEstatisticasFinais(canvas, screenWidth, screenHeight);
        }
    }

    private void drawAimLine(Canvas canvas) {
        Point cannonPos = cannon.getPosition();
        double angle = cannon.getAngle();

        // Desenhar linha tracejada representando a trajetória
        float startX = cannonPos.x + (float) (Math.cos(angle) * cannon.getBarrelLength());
        float startY = cannonPos.y - (float) (Math.sin(angle) * cannon.getBarrelLength());

        // Desenhar linha até a borda da tela
        float endX = startX + (float) (Math.cos(angle) * screenWidth);
        float endY = startY - (float) (Math.sin(angle) * screenWidth);

        canvas.drawLine(startX, startY, endX, endY, aimLinePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (cannon == null) {
            return true;
        }

        int action = event.getActionMasked();
        float touchX = event.getX();
        float touchY = event.getY();

        // Se game over, reiniciar ao tocar
        if (gameOver && action == MotionEvent.ACTION_UP) {
            resetGame();
            return true;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // Calcular ângulo baseado na posição do toque
                updateCannonAngle(touchX, touchY);
                break;

            case MotionEvent.ACTION_UP:
                // Disparar quando soltar o toque
                fireCannonball();
                break;
        }

        return true;
    }

    private void updateCannonAngle(float touchX, float touchY) {
        Point cannonPos = cannon.getPosition();

        // Calcular diferença entre toque e posição do canhão
        float dx = touchX - cannonPos.x;
        float dy = cannonPos.y - touchY; // Inverter Y porque cresce para baixo

        // Calcular ângulo em radianos
        double angle = Math.atan2(dy, dx);

        // Definir ângulo do canhão
        cannon.setAngle(angle);
    }

    public void resetGame() {
        initializeGame();
    }

    // Thread do jogo com loop de atualização
    private class GameThread extends Thread {
        private static final long TARGET_FPS = 60;
        private static final long FRAME_PERIOD = 1000000000L / TARGET_FPS; // nanosegundos

        @Override
        public void run() {
            long lastTime = System.nanoTime();

            while (running) {
                long currentTime = System.nanoTime();
                long elapsedTime = currentTime - lastTime;

                // Atualizar lógica do jogo
                update(elapsedTime / 1000000000.0); // converter para segundos

                // Desenhar frame
                draw();

                lastTime = currentTime;

                // Controlar FPS
                long frameTime = System.nanoTime() - currentTime;
                long sleepTime = (FRAME_PERIOD - frameTime) / 1000000; // converter para milissegundos

                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

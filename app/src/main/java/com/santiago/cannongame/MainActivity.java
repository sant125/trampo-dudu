package com.santiago.cannongame;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CannonGameView cannonGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar tela cheia
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Criar e configurar a view do jogo
        cannonGameView = new CannonGameView(this);
        setContentView(cannonGameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cannonGameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cannonGameView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cannonGameView.releaseResources();
    }
}

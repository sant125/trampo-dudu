package com.santiago.cannongame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private SoundPool soundPool;
    private Map<String, Integer> soundMap;
    private boolean soundEnabled = true;

    // IDs dos sons
    public static final String SOUND_FIRE = "fire";
    public static final String SOUND_HIT = "hit";
    public static final String SOUND_MISS = "miss";

    public SoundManager(Context context) {
        // Criar SoundPool com atributos de áudio
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        soundMap = new HashMap<>();

        // Nota: Em um projeto real, você carregaria arquivos de som reais da pasta res/raw
        // Por exemplo:
        // soundMap.put(SOUND_FIRE, soundPool.load(context, R.raw.cannon_fire, 1));
        // soundMap.put(SOUND_HIT, soundPool.load(context, R.raw.target_hit, 1));
        // soundMap.put(SOUND_MISS, soundPool.load(context, R.raw.cannon_miss, 1));

        // Como não temos arquivos de áudio reais, vamos simular os IDs
        // Em produção, remova estas linhas e descomente as acima
        soundMap.put(SOUND_FIRE, -1);
        soundMap.put(SOUND_HIT, -1);
        soundMap.put(SOUND_MISS, -1);
    }

    public void playSound(String soundId) {
        if (soundEnabled && soundMap.containsKey(soundId)) {
            Integer sound = soundMap.get(soundId);
            if (sound != null && sound != -1) {
                soundPool.play(sound, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        }
    }

    public void playSound(String soundId, float volume) {
        if (soundEnabled && soundMap.containsKey(soundId)) {
            Integer sound = soundMap.get(soundId);
            if (sound != null && sound != -1) {
                soundPool.play(sound, volume, volume, 1, 0, 1.0f);
            }
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}

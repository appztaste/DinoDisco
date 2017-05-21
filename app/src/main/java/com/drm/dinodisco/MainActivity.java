package com.drm.dinodisco;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple fun app for my Son :)
 *
 * COPYRIGHT DISCLAIMER:
 * Images and audio were taken from internet via Google search. I don't own copyright for any of these.
 *
 * @author Dibya Ranjan Mallick
 */
public class MainActivity extends AppCompatActivity {
    private int curImg = 0;
    private AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
    private AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
    private MediaPlayer mediaPlayer;
    private Context context;

    private int[] dinos = {R.drawable.trex, R.drawable.tricera, R.drawable.stego
            , R.drawable.ptero, R.drawable.maia, R.drawable.bronto, R.drawable.tricera2
            , R.drawable.raptors, R.drawable.pachy, R.drawable.para, R.drawable.ajeeb
            , R.drawable.bronto2, R.drawable.ankylo, R.drawable.argentino, R.drawable.pentacera
            , R.drawable.spino, R.drawable.stego2, R.drawable.pachy2};

    private Map<Integer, String> dinoNames = new HashMap<>();
    private Map<Integer, Integer> dinoRoars = new HashMap<>();

    /**
     * Switch through all dinosaurs
     * @param view
     */
    public void switchImage(View view) {
        ImageView dinoImage = (ImageView) findViewById(R.id.dinoImage);
        final TextView dinoName = (TextView) findViewById(R.id.dinoName);
        dinoImage.startAnimation(fadeOut);
        dinoName.startAnimation(fadeOut);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        prepareDinos();
        initializeMediaPlayer();
        firstRoar();
    }

    /**
     * Release first roar on app load
     */
    private void firstRoar() {
        try {
            Uri roar = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.trex);
            mediaPlayer.setDataSource(context, roar);
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if(mediaPlayer != null) {
            mediaPlayer.prepareAsync();
        }
    }

    /**
     * Initialize media player with listeners
     */
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("Dino", "Error: " + mp.getDuration() + ", " + what + ", " + extra);
                return false;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d("Dino", "MP prepared");
                mediaPlayer.start();
            }
        });
    }

    /**
     * Animate imageView and associate image resource and names
     */
    private void prepareDinos() {
        populateDinoNames();
        populateDinoRoars();
        final ImageView dinoImage = (ImageView) findViewById(R.id.dinoImage);
        final TextView dinoName = (TextView) findViewById(R.id.dinoName);
        fadeIn.setDuration(1000);
        fadeOut.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(curImg == dinos.length - 1) curImg = -1;
                dinoImage.setImageResource(dinos[++curImg]);
                dinoName.setText(dinoNames.get(dinos[curImg]));
                dinoImage.startAnimation(fadeIn);
                dinoName.startAnimation(fadeIn);
                mediaPlayer.reset();
                try {
                    Uri roar = Uri.parse("android.resource://" + getPackageName() + "/" + dinoRoars.get(dinos[curImg]));
                    mediaPlayer.setDataSource(context, roar);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.prepareAsync();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void populateDinoRoars() {
        dinoRoars.put(R.drawable.ajeeb, R.raw.raptor);
        dinoRoars.put(R.drawable.ankylo, R.raw.ankylo);
        dinoRoars.put(R.drawable.argentino, R.raw.argentino);
        dinoRoars.put(R.drawable.bronto, R.raw.bronto);
        dinoRoars.put(R.drawable.bronto2, R.raw.bronto2);
        dinoRoars.put(R.drawable.maia, R.raw.maia);
        dinoRoars.put(R.drawable.pachy, R.raw.pachy);
        dinoRoars.put(R.drawable.pachy2, R.raw.pachy2);
        dinoRoars.put(R.drawable.para, R.raw.para);
        dinoRoars.put(R.drawable.pentacera, R.raw.pentacera);
        dinoRoars.put(R.drawable.ptero, R.raw.ptero);
        dinoRoars.put(R.drawable.raptors, R.raw.raptor);
        dinoRoars.put(R.drawable.spino, R.raw.spino);
        dinoRoars.put(R.drawable.stego, R.raw.stego);
        dinoRoars.put(R.drawable.stego2, R.raw.stego2);
        dinoRoars.put(R.drawable.trex, R.raw.trex);
        dinoRoars.put(R.drawable.tricera, R.raw.tricera);
        dinoRoars.put(R.drawable.tricera2, R.raw.tricera);
    }

    /**
     * Populate dinosaurs' names
     */
    private void populateDinoNames() {
        dinoNames.put(R.drawable.ajeeb, "Velociraptor");
        dinoNames.put(R.drawable.ankylo, "Ankylosaurus");
        dinoNames.put(R.drawable.argentino, "Argentinosaurus");
        dinoNames.put(R.drawable.bronto, "Brontosaurus");
        dinoNames.put(R.drawable.bronto2, "Brontosaurus");
        dinoNames.put(R.drawable.maia, "Maiasaura");
        dinoNames.put(R.drawable.pachy, "Pachycephalosaurus");
        dinoNames.put(R.drawable.pachy2, "Pachycephalosaurus");
        dinoNames.put(R.drawable.para, "Parasaurolophus");
        dinoNames.put(R.drawable.pentacera, "Pentaceratops");
        dinoNames.put(R.drawable.ptero, "Pterodactyl");
        dinoNames.put(R.drawable.raptors, "Velociraptor");
        dinoNames.put(R.drawable.stego, "Stegosaurus");
        dinoNames.put(R.drawable.stego2, "Stegosaurus");
        dinoNames.put(R.drawable.tricera, "Triceratops");
        dinoNames.put(R.drawable.tricera2, "Triceratops");
        dinoNames.put(R.drawable.trex, "Tyrannosaurus");
        dinoNames.put(R.drawable.spino, "Spinosaurus");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

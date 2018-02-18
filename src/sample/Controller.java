package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Label lable;
    @FXML
    private MediaView mv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File[] videos = new File("D:/Video/").listFiles();
        String vURL = ("file:/"+videos[new Random().nextInt(videos.length)].getAbsolutePath()).replace('\\','/');

        //String vURL = "file:/D:/Video/1.mp4";
        Media media = new Media(vURL);
        MediaPlayer player = new MediaPlayer(media);
        mv.setMediaPlayer(player);
        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                player.play();
                currentTimeProperty(player);
            }
        });
    }

    private void currentTimeProperty(MediaPlayer player) {
        SimpleDateFormat tm = new SimpleDateFormat("mm:ss");
        long time = new Date().getTime()+300_000L;

        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                lable.setText(tm.format((time-(new Date().getTime()))));
            }
        });
    }
}

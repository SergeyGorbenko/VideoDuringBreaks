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
    private File[] videos = new File("D:/Video/").listFiles();
    private long time = new Date().getTime()+300_000L;

    private MediaPlayer getVideo(){
        String vURL = ("file:/"+videos[new Random().nextInt(videos.length)].getAbsolutePath()).replace('\\','/');
        Media media = new Media(vURL);
        MediaPlayer player = new MediaPlayer(media);
        mv.setMediaPlayer(player);
        return player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MediaPlayer video = getVideo();
        video.setOnReady(()-> {
                video.play();
                currentTimeProperty(video);
        });

    }

    private void currentTimeProperty(MediaPlayer video) {
        SimpleDateFormat tm = new SimpleDateFormat("mm:ss");
        video.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            Long currentTime = time - (new Date().getTime());
            if(currentTime > 0 && currentTime < 500)
                video.stop();
            lable.setText(tm.format(currentTime));
            video.setOnEndOfMedia(()-> {
                    video.stop();
                    initialize(null,null);
            });

            });



    }
}

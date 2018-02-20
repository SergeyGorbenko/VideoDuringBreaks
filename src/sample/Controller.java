package sample;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable {
    @FXML
    private Label lable;
    @FXML
    private MediaView mv;
    @FXML
    private ImageView iv;

    private File[] videos = new File("D:/Video/").listFiles();
    private long time = new Date().getTime()+300_000L;
    private Long partTime;
    private Long duration;

    private Image setInfo(){
            Image image = new Image("file:/D:/1.bmp");
            iv.setImage(image);
            iv.setVisible(true);
            return image;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String vURL = ("file:/"+videos[new Random().nextInt(videos.length)].getAbsolutePath()).replace('\\','/');
        Media media = new Media(vURL);
        MediaPlayer video = new MediaPlayer(media);
        mv.setMediaPlayer(video);
        video.setOnReady(()-> {
                duration = (long) video.getTotalDuration().toMillis();
                partTime = (300_000L-duration)/2;
                System.out.println(partTime);
                mv.setVisible(false);
                setInfo();
                video.play();
                video.setVolume(0);
                currentTimeProperty(video);
        });

    }

    private void currentTimeProperty(MediaPlayer video) {
        SimpleDateFormat tm = new SimpleDateFormat("mm:ss");

        video.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            Long currentTime = time - (new Date().getTime());
            if(currentTime > -500 && currentTime < 500) {
                video.stop();
                System.exit(0);
            }
            else {
                    video.setOnEndOfMedia(() -> {
                        video.seek(new Duration(0));
                        video.play();
                        video.setVolume(0);
                    });
                    if (currentTime > 300_000L - partTime - 500 && currentTime < 300_000L - partTime + 500) {
                        iv.setVisible(false);
                        mv.setVisible(true);
                        video.seek(new Duration(0));
                        video.play();
                        video.setVolume(100);
                    }
                    if (currentTime > partTime - 500 && currentTime < partTime + 500) {
                        iv.setVisible(true);
                        mv.setVisible(false);
                    }
                    lable.setText(tm.format(currentTime));
            }
         });



    }
}

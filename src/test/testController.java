package test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class testController {
    public static void main(String[] args) {
        File[] videos = new File("D:/Video/").listFiles();
        String vURL = ("file:/"+videos[new Random().nextInt(videos.length)].getAbsolutePath()).replace('\\','/');
        System.out.println(vURL);

    }
}

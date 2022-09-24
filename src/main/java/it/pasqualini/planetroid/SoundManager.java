package it.pasqualini.planetroid;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

public class SoundManager {
    private javax.sound.sampled.Line.Info lineInfo;

    private Vector<AudioFormat> afs;
    private Vector<Integer> sizes;
    private Vector<DataLine.Info> infos;
    private Vector<byte[]> audios;
    private int num = 0;
    private static int rate = 0;
    private static int channels = 0;

    private static byte[] readContentIntoByteArray(File file) {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length() + (int)file.length() % 4];
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }


    public SoundManager() {
        afs = new Vector<>();
        sizes = new Vector<>();
        infos = new Vector<>();
        audios = new Vector<byte[]>();
    }

    public int getCount() {
        return num;
    }

    public void addClip(String s) {
        try {
            File f = new File(getClass().getResource("sounds/giorgio.wav").toURI());

            byte[] audio = readContentIntoByteArray(f);

            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            AudioFormat format = stream.getFormat();
            int sampleRate = (int) format.getSampleRate();



            AudioFormat audioFormat = new AudioFormat
                    (
                            (float) sampleRate, format.getSampleSizeInBits(),
                            format.getChannels() * 2,
                            true,  // PCM_Signed
                                false  // littleEndian
                    );

            AudioFormat newFormat=new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    format.getSampleRate(),
                    16,
                    format.getChannels(),
                    format.getChannels() * 2,
                    format.getSampleRate(),
                    false);

            DataLine.Info info = new DataLine.Info
                    (
                            Clip.class,
                            format
                    );

            afs.add(newFormat);
            sizes.add(audio.length);
            infos.add(info);
            audios.add(audio);

            num++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void playSound(int x) {
        try {
            if (x > num) {
                System.out.println("playSound: sample nr[" + x + "] is not available");
            } else {
                Clip clip = (Clip) AudioSystem.getLine(infos.elementAt(x));
                clip.open(afs.elementAt(x), audios.elementAt(x), 0, sizes.elementAt(x) +  sizes.elementAt(x) % 4);
                clip.start();
            }
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        }
    }


}

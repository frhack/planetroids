package it.pasqualini.planetroid.audio;

import com.sun.tools.javac.Main;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static it.pasqualini.util.Util.println;


public class AudioClipBuilder {



    public AudioClip BuildFromWAVFile(String filename, float volume) throws IOException {


        InputStream inputStream = getInputStreamFromResources(filename);
        byte[] audio = readContentIntoByteArray(inputStream, 4);

        AudioInputStream streamx = null;
        try {
            streamx = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audio));
        } catch (UnsupportedAudioFileException e) {
            println("ERRORE");
            throw new RuntimeException(e);
        }


        AudioFormat format = new AudioFormat(
                streamx.getFormat().getEncoding(),
                streamx.getFormat().getSampleRate(),
                16,
                streamx.getFormat().getChannels(),
                streamx.getFormat().getChannels() * 2,
                streamx.getFormat().getSampleRate(),
                false);

        DataLine.Info info = new DataLine.Info(Clip.class, format);
        return new AudioClip(info, format, audio, volume);

    }






/*
    public AudioClip BuildFromWAVFile(String filename) throws FileNotFoundException {

        //"sounds/giorgio.wav"
        File file = getFileFromResources(filename);
        byte[] audio = readContentIntoByteArray(file, 4);

        AudioInputStream stream = getAudioInputStream(file);


        AudioFormat format = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                stream.getFormat().getSampleRate(),
                16,
                stream.getFormat().getChannels(),
                stream.getFormat().getChannels() * 2,
                stream.getFormat().getSampleRate(),
                false);

        DataLine.Info info = new DataLine.Info(Clip.class, format);

        return new AudioClip(info, format, audio, format.getFrameSize());
    }
*/

    private AudioInputStream getAudioInputStream(InputStream fileInputStream) {
        try {
            return AudioSystem.getAudioInputStream(fileInputStream);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStreamFromResources(String file)  {
        return Main.class.getClassLoader().getResourceAsStream(file);
        //return new File(getClass().getResource("sounds/giorgio.wav").toURI());
        //return new File(getClass().getResource(file).toURI());
    }

    private static byte[] readContentIntoByteArray(InputStream fileInputStream, int frameSize) throws IOException {

        byte[] bFileT = fileInputStream.readAllBytes();

        byte[] bFile = java.util.Arrays.copyOf(bFileT ,bFileT.length + bFileT.length % frameSize);
        //byte[] bFile = java.util.Arrays.copyOf(bFileT ,bFileT.length + 0);

        //FileInputStream fileInputStream = new FileInputStream(file);
        try {
            fileInputStream.read(bFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bFile;
    }


    // Get all paths from a folder that inside the JAR file
    private List<Path> getPathsFromResourceJAR(String folder)
            throws URISyntaxException, IOException {




        List<Path> result;

        // get path of the current running JAR
        String jarPath = getClass().getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        System.out.println("JAR Path :" + jarPath);

        // file walks JAR
        URI uri = URI.create("jar:file:" + jarPath  );
        String jar = System.getProperty("java.class.path");
        try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
            result = Files.walk(fs.getPath(folder))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }

        return result;

    }





}

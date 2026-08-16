package cn.yuang2714.dagou_recyclebin;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioHandler {
    public static AudioHandler instance;
    
    private enum Audios {
        DAGOU("/dagou_pcm16.wav", 500),
        BARK("/bark_pcm16.wav", 2260);
        
        public final String name;
        public final int length;

        Audios(String name, int length) {
            this.name = name;
            this.length = length;
        }
    }
    private final List<Audios> queue = new CopyOnWriteArrayList<>();
    
    private final Clip audio = AudioSystem.getClip();
    public void playDagou() { queue.add(Audios.DAGOU); }
    public void playBark() { queue.add(Audios.BARK); }
    private void play(Audios work) throws Exception {
        Utils.logln("Playing " + work.name);
        audio.close();
        audio.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(work.name))));
        audio.start();
        Thread.sleep(work.length);
    }
    
    public void stop() { audio.stop(); }
    
    @SuppressWarnings("BusyWait")
    private void run() {
        while (true) {
            try { Thread.sleep(50); } catch (InterruptedException _) {}
            if (!queue.isEmpty()) {
                Audios audio = queue.getFirst();
                try {
                    play(audio);
                } catch (Exception e) {
                    Utils.logln("Audio play failed: " + Arrays.toString(e.getStackTrace()));
                } finally {
                    queue.removeFirst();
                    queue.forEach(a -> {
                            if (a.equals(audio)) queue.remove(a);
                    });
                }
            }
        }
    }
    
    public AudioHandler() throws Exception {
        Thread worker = new Thread(this::run, "Audio player Thread");
        worker.start();
    }
    
    public static void init() throws Exception {
        instance = new AudioHandler();
    }
}
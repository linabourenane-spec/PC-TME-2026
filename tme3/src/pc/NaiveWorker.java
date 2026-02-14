package pc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

class NaiveWorker implements Runnable {
    private final File file;
    private final long start, end;
    private final Map<String, Integer> map;
    private final long[] totalWords; 

    public NaiveWorker(File file, long start, long end, Map<String, Integer> map, long[] totalWords) {
      this.file = file;
      this.start = start;
      this.end = end;
      this.map = map;
      this.totalWords = totalWords;
    }

    @Override
    public void run() {
    
      try (Scanner scanner = new Scanner(FileUtils.getRange(file, start, end))) {
        while (scanner.hasNext()) {
          String word = WordFrequency.cleanWord(scanner.next());
          if (!word.isEmpty()) {
          
            totalWords[0]++; 
            map.compute(word, (w, c) -> c == null ? 1 : c + 1);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
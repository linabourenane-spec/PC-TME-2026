package pc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class WordFrequency {
	
	private static class WordCount implements Comparable<WordCount>{
			private String mot ;
			private int cpt;
			public WordCount (String mot) {
				this.mot=mot;
				this.cpt=1;
			}
			@Override
			public int compareTo(WordCount arg) {
				int j=Integer.compare(arg.cpt,cpt);
					 
					if(j==0) {
						j =mot.compareTo(arg.mot);
					}
				return j;
			}
			@Override
			public String toString() {
				return "WordCount [mot=" + mot + ", cpt=" + cpt + "]";
			}
			
			
			
	}
    public static void main(String[] args) throws IOException {
        // Allow filename as optional first argument, default to WarAndPeace.txt
        // Optional second argument is mode (e.g., "list" or "listfreq").
        String filename = args.length > 0 ? args[0] : "data/WarAndPeace.txt";
        String mode = args.length > 1 ? args[1] : "count";

        // Check if file is readable
        File file = new File(filename);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Could not open '" + filename + "'. Please provide a readable text file as the first argument.");
            System.err.println("Usage: java WordFrequency [path/to/textfile] [mode]");
            System.exit(2);
        }

        long fileSize = file.length();

        System.out.println("Preparing to parse " + filename + " (mode=" + mode + "), containing " + fileSize + " bytes");

        long start = System.nanoTime();

        Scanner scanner = new Scanner(file);

        if (mode.equals("count")) {
            long totalWords = 0;
        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    // TODO : ici on peut agir sur le mot lu
                }
            }
        	System.out.println("Total words: " + totalWords);
        } else if (mode.equals("list")) {
            long totalWords = 0;
            List<String> words = new ArrayList<>();

        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    
                    if (!words.contains(word)){
                    	words.add(word);
                    }

                    
                }
            }
        	System.out.println("Total words: " + totalWords);
        	System.out.println("Unique words: " + words.size());
        } else if (mode.equals("listfreq")) {
        	long totalWords = 0;
            List<WordCount> words = new ArrayList<>();
            boolean t =false ;
        	while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    for (WordCount w : words) {
                    		if (w.mot.equals(word)) {
                    			w.cpt++;
                    			t=true;
                    			break;
                    		}
                    }
                    if(!t) {
                    	WordCount w =new WordCount(word);
                    	words.add(w);
                    	
                    }
                    t=false;
                }
            }
        	System.out.println("Total words: " + totalWords);
        	System.out.println("Unique words: " + words.size());
        	Collections.sort(words);
        	for (int i=0;i<5;i++) {
        		System.out.println(words.get(i)+"\n");
        	}
        	
        } else if (mode.equals("tree")) {
        	long totalWords = 0;
            Map<String, Integer> map = new TreeMap<>();
            while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    if (map.get(word)==null) {
                    	map.put(word,1);
                    }
                    else {
                    	map.put(word,(map.get(word))+1);
                    }
                }
            }
            List<Map.Entry<String,Integer>> l= new ArrayList<>(map.entrySet());
            Collections.sort(l,(e1,e2)->{
            	int res=Integer.compare(e2.getValue(), e1.getValue());
            	if (res ==0) {
            			res = e1.getKey().compareTo(e2.getKey());
            	}
            	return res ;
            });
            System.out.println("Total words: " + totalWords);
            System.out.println("Unique words: " + map.size());
            for (int i = 0; i < 5; i++) {
                Map.Entry<String, Integer> e = l.get(i);
                System.out.println(e.getKey() + " : " + e.getValue());
            }

            
            // TODO : extraire le map dans une ArrayList
            // trier la liste par fréquence décroissante puis ordre alphabétique croissant
        	// puis afficher les 5 mots les plus fréquents avec leur fréquence

        } else if (mode.equals("hash")) {
        	long totalWords = 0;
        	Map<String, Integer> map = new HashMap<>();
            while (scanner.hasNext()) {
                String word = cleanWord(scanner.next());
                if (!word.isEmpty()) {
                    totalWords++;
                    map.put(word, map.getOrDefault(word, 0) + 1);
                }
            }
            System.out.println("Total words: " + totalWords);
            System.out.println("Unique words: " + map.size());

         // 1. Extraire les entrées de la map dans une liste
            List<Map.Entry<String, Integer>> l = new ArrayList<>(map.entrySet());

            // 2. Trier la liste
            Collections.sort(l, (e1, e2) -> {
                // Comparaison des fréquences (décroissant)
                int res = e2.getValue().compareTo(e1.getValue());
                
                // Si même fréquence, tri alphabétique (croissant)
                if (res == 0) {
                    res = e1.getKey().compareTo(e2.getKey());
                }
                return res;
            });

            // 3. Afficher les 5 premiers
            System.out.println("--- Top 5 ---");
            int limit = Math.min(5, l.size());
            for (int i = 0; i < limit; i++) {
                System.out.println(l.get(i).getKey() + " : " + l.get(i).getValue());
            }

        } else {
            System.err.println("Unknown mode '" + mode + "'. Supported modes: count, list, listfreq, tree, hash");
            System.exit(1);
        }

        scanner.close();

        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Total runtime (wall clock) : " + durationMs + " ms for mode " + mode);
    }

    private static String cleanWord(String word) {
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

}
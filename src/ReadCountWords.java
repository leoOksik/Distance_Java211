import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

public class ReadCountWords implements FileReadWrite {

    private String path;
    private Map<String, Long> countWords;
    private List<String> readWord;
    private Map<String, Long> sortMap;

    ReadCountWords() {
        countWords = new HashMap<>();
        readWord = new ArrayList<>();
        sortMap = new TreeMap<>();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Long> getCountWords() {
        return countWords;
    }

    public List<String> ReadFiles(String path) {

        setPath(path);

        try {
            // в след работах учту -> чтение построчно
            readWord = Files.readAllLines(Paths.get(getPath()));

            String strRegex = "\\p{Punct}";

            readWord = readWord.stream()
                    .map(str -> str.split(" "))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());

            readWord = readWord.stream()
                    .map(str -> str.replaceAll(strRegex, ""))
                    .collect(Collectors.toList());

            readWord.replaceAll(String::toLowerCase);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (readWord.isEmpty()) {
            return Collections.emptyList();
     }
        return readWord;
    }

    public Map<String, Long> wordsAll(String path) {

        ReadFiles(path);

        countWords = readWord.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return countWords;
    }

    public String wordsMaxCount() {

        String str = String.valueOf(countWords.entrySet()
                .stream()
                .max(comparingLong((value -> value.getValue()))).get());
        return "\nMax count: " + str;
    }

    @Override
    public void writeToFile() {

        try {
            Path path = Paths.get("data/write.txt");

            Files.write(path, sortMap.entrySet().stream()
                    .map(entry -> "Word: " + entry.getKey() +
                            ", count: " + entry.getValue())
                    .collect(Collectors.toList()));

            Files.write(path, Collections.singleton(wordsMaxCount()), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void printWord(String path) {
        wordsAll(path);

        sortMap.putAll(getCountWords());

        for (Map.Entry<String, Long> entry : sortMap.entrySet())
            System.out.println("Word: " + entry.getKey() +
                    ", count: " + entry.getValue());

        System.out.println(wordsMaxCount());

    }
}


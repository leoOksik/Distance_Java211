
public class Main {
    public static void main(String[] args) {

        /* TODO: У вас имеется текстовый файл text.txt.
                 Посчитайте в этом тексте, как часто встречается
                 каждое слово, затем выведите в отдельный файл result.txt данную информацию.
                 Также, в конец этого файла выведите самое часто встречаемое слово и его частоту.
        */

        String path = "data/text.txt";
        ReadCountWords readCountWords = new ReadCountWords();
        readCountWords.printWord(path);
        readCountWords.writeToFile();

    }
}

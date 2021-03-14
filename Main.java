package readability;
import java.util.Scanner;
import java.io.*;


public class Main {
    public static void main(String[] args) {

        int charCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        int syllablesCount = 0;
        int polysyllablesCount = 0;

        double score = 0;
        double averAge = 0;
        String text = "";
        int[] grades = {6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24};

        Scanner sc = new Scanner(System.in);
        File file = new File(args[0]);

        try (Scanner scanner = new Scanner(file)) {

            StringBuilder input = new StringBuilder();

            while (scanner.hasNext()) {

                input.append(scanner.nextLine());

            }

            text = input.toString();

        } catch (FileNotFoundException e) {

            System.out.println("No file found: " + file);

        }

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println("");
        String[] sentences = text.split("\\.{1,3}|\\!|\\?");

        for (String a : sentences) {

            String[] words = a.trim().split("\\s+");

            for (String w : words) {

                int wsyl = 0;

                for (int i = 0; i < w.length() - 1; i++) {

                    if (Character.toString(w.charAt(i)).matches("[aeiouyAEIOUY]")) {

                        wsyl++;

                        if (i < w.length() - 1 && Character.toString(w.charAt(i + 1)).matches("[aeiouyAEIOUY]")) {

                            wsyl--;

                        }

                        if (i == w.length() - 1 && Character.toString(w.charAt(i)).matches("[eE]")) {

                            wsyl--;

                        }
                    }
                }

                syllablesCount += wsyl > 0 ? wsyl : 1;

                if (wsyl > 2) {

                    polysyllablesCount++;
                    wsyl = 1;

                }
            }

            wordCount += words.length;

        }

        text = text.replaceAll("\\s+", "");
        charCount = text.length();
        sentenceCount = sentences.length;

        System.out.println("Words: " + wordCount);
        System.out.println("Sentences: " + sentenceCount);
        System.out.println("Characters: " + charCount);
        System.out.println("Syllables: " + syllablesCount);
        System.out.println("Polysyllables: " + polysyllablesCount);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

        String command = sc.next();

        if (command.equals("ARI") || command.equals("all")) {

            score = 4.71 * charCount / wordCount + 0.5 * wordCount / sentenceCount - 21.43;
            averAge += score < grades.length ? grades[(int) score - 1] : grades[12];

            System.out.println(score < grades.length
                    ? "Automated Readability Index: " + score + " (about " + grades[(int) score - 1] + " year olds)."
                    : "Automated Readability Index: " + score + " (about " + grades[12] + " year olds).");
        }

        if (command.equals("FK") || command.equals("all")) {

            score = 0.39 * wordCount / sentenceCount + 11.8 * syllablesCount / wordCount - 15.59;
            averAge += score < grades.length ? grades[(int) score - 1] : grades[12];

            System.out.println(score < grades.length
                    ? "Flesch–Kincaid readability tests: " + score + " (about " + grades[(int) score - 1] + " year olds)."
                    : "Flesch–Kincaid readability tests: " + score + " (about " + grades[12] + " year olds).");
        }

        if (command.equals("SMOG") || command.equals("all")) {

            score = 1.043 * Math.sqrt(polysyllablesCount * 30 / sentenceCount) + 3.1291;
            averAge += score < grades.length ? grades[(int) score - 1] : grades[12];

            System.out.println(score < grades.length
                    ? "Simple Measure of Gobbledygook: " + score + " (about " + grades[(int) score - 1] + " year olds)."
                    : "Simple Measure of Gobbledygook: " + score + " (about " + grades[12] + " year olds).");

        }

        if (command.equals("CL") || command.equals("all")) {

            double avChar = charCount / wordCount * 100;
            double senChar = sentenceCount / wordCount * 100;

            score = 0.0588 * avChar - 0.296 * senChar - 15.8;
            averAge += score < grades.length ? grades[(int) score - 1] : grades[12];

            System.out.println(score < grades.length
                    ? "Coleman–Liau index: " + score + " (about " + grades[(int) score - 1] + " year olds)."
                    : "Coleman–Liau index: " + score + " (about " + grades[12] + " year olds).");
        }

        System.out.println("");

        if (command.equals("all")) {

            System.out.println("This text should be understood in average by " + averAge / 4 + " year olds.");
        }
    }
}
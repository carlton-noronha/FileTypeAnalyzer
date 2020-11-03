package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Command line syntax: java classname algorithm-type filename pattern result");
            System.exit(0);
        }

        File patternDatabase = new File("./" + args[1]);
        try (Scanner scanner = new Scanner(patternDatabase)) {

            List<Patterns> patterns = new ArrayList<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineElements = line.split(";");
                patterns.add(new Patterns(Integer.parseInt(lineElements[0]), lineElements[1].replace("\"", "")
                        , lineElements[2].replace("\"", "")));
            }

            MergeSort.mergesort(patterns, 0, patterns.size());


            File folder = new File("./" + args[0]);
            File[] files = folder.listFiles();

            StringMatcherInitializer algorithmInitializer = new StringMatcherInitializer();

            /* For Naive Algorithm
            algorithmInitializer.setAlgorithm(new NaiveAlgorithm());
             */

            /* For KMP Algorithm
            algorithmInitializer.setAlgorithm(new KMPAlgorithm());
             */

            /* For Rabin Karp Algorithm */
            algorithmInitializer.setAlgorithm(new RabinKarpAlgorithm());

            ExecutorService executor = Executors.newCachedThreadPool();

            if (files != null) {

                List<Future<Boolean>> typeOfSuccessfulMatch = new ArrayList<>();
                boolean hasMatchedSuccessfully = false;

                for (File file : files) {

                    for (Patterns patternToMatch : patterns) {
                        Future<Boolean> future = executor.submit(() -> algorithmInitializer.invokeAlgorithm(
                                readFromFile(file.getPath()), patternToMatch.getPattern()));
                        typeOfSuccessfulMatch.add(future);
                    }

                    for (int i = 0; i < typeOfSuccessfulMatch.size(); ++i) {
                        try {
                            if (typeOfSuccessfulMatch.get(i).get()) {
                                System.out.println(file.getName() + ": " + patterns.get(i).getType());
                                hasMatchedSuccessfully = true;
                                break;
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            //System.out.println("Failed to get result");
                        }
                    }

                    if (!hasMatchedSuccessfully) {
                        System.out.println(file.getName() + ": " + "Unknown file type");
                    }

                    hasMatchedSuccessfully = false;
                    typeOfSuccessfulMatch.clear();
                }

            }

            executor.shutdown();

        } catch (FileNotFoundException e) {
            System.out.println("File not found! Kindly check the path and name of the file");
            System.exit(1);
        }
    }

    private static String readFromFile(String filePath) throws IOException {
        byte[] readBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(readBytes);
    }
}

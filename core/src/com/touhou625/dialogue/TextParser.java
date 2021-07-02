package com.touhou625.dialogue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextParser {

    private final Scanner sc;

    public TextParser(String name) throws FileNotFoundException {

        File file = new File(name);
        sc = new Scanner(file);
    }

    public List<String> parseText() {

        boolean startParsing = false;

        StringBuilder tmp = new StringBuilder(50);
        String currentBlock;
        ArrayList<String> result = new ArrayList<>();

        while (sc.hasNextLine()) {
            currentBlock = sc.nextLine();

            if (startParsing) {
                if ("".equals(currentBlock)) {
                    result.add(tmp.toString());
                    tmp.delete(0, tmp.capacity());
                } else {
                    tmp.append(currentBlock.concat("\n"));
                }
            } else {
                if ("**".equals(currentBlock)) {
                    startParsing = true;
                }
            }
        }

        sc.close();
        return result;
    }
}

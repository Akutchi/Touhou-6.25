package com.touhou625.dialogue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextParser {

    private final Scanner sc;

    public TextParser(String name) throws FileNotFoundException {

        File file = new File(name);
        sc = new Scanner(file);
    }

    public ArrayList<String> parseText() {

        String tmp = "";
        String currentBlock;
        ArrayList<String> result = new ArrayList<>();

        while (sc.hasNextLine()) {

            currentBlock = sc.nextLine();

            if (currentBlock.equals("")) {
                result.add(tmp);
                tmp = "";
            }

            tmp = tmp.concat(currentBlock);

        }

        return result;
    }
}

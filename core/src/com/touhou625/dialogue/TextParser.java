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

        StringBuilder tmp = new StringBuilder(50);
        String currentBlock;
        ArrayList<String> result = new ArrayList<>();

        while (sc.hasNextLine() && !sc.nextLine().contains("#")) {

            currentBlock = sc.nextLine();
            
            if (currentBlock.equals("")) {
                result.add(tmp.toString());
                tmp.delete(0, tmp.capacity());
            }

            tmp.append(currentBlock.concat("\n"));
        }

        return result;
    }
}

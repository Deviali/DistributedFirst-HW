package com.AliP;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Launcher {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        if (args.length != 1) {
            System.out.println("Please specify modality ( 0 - single thread, 1 - multi thread )");
        } else if (args[0].equals("0") || args[0].equals("1")) {
            Helper.execute(Integer.parseInt(args[0]));
        } else System.out.println("Incorrect mode selected!");
    }
}

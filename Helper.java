package com.AliP;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Helper {
    public static ExecutorService poolExecutor = Executors.newFixedThreadPool(10);

    private static long process(URL url) throws IOException {
        long start = System.currentTimeMillis();
        URLConnection connection = url.openConnection();
        if (!connection.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new IOException("FAILED.\n[Sorry. This is not a PDF: "+url.toString()+" ]");
        } else {
            byte[] heap = new byte[1024];
            BufferedInputStream reader = new BufferedInputStream(connection.getInputStream());
            while (reader.read(heap) != -1) {
                /*Work viewer (debug mode)*/
                // for (byte b : heap) {
                //    System.out.print(b);
                // }
                // System.out.println();

                // No saving implementation needed so i'll leave this blank
            }
            reader.close();
        }
        return System.currentTimeMillis() - start;
    }

    public static void execute(int modality) throws IOException, ExecutionException, InterruptedException {
        URL[] urls = new URL[] {
                new URL("http://www.ubicomp.org/ubicomp2003/adjunct_proceedings/proceedings.pdf"),
                new URL("https://www.hq.nasa.gov/alsj/a17/A17_FlightPlan.pdf"),
                new URL("https://ars.els-cdn.com/content/image/1-s2.0-S0140673617321293-mmc1.pdf"),
                new URL("http://www.visitgreece.gr/deployedFiles/StaticFiles/maps/Peloponnese_map.pdf"),
        };
        if (modality == 0) {
            for (URL url : urls) {
                System.out.println("File: "+url+" done in + "+process(url));
            }
        } else if (modality == 1) {
            for (URL url : urls) {
                System.out.println("File: "+url+" done in + "+poolExecutor.submit(() -> {
                    try {
                        System.out.println("File: "+url+" done in + "+process(url));
                    } catch (IOException e) {
                        System.out.println("Cannot establish connection with: "+url);
                    }
                }).get());
            }
        }
    }

}

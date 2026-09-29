package com.example.piratesgames.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class CompetitionResultsIO {

    private CompetitionResultsIO() {}

    public static void writeCompetitionHistory(
            List<Competition> history,
            Path outputFile
    ) throws IOException {

        try (BufferedWriter w = Files.newBufferedWriter(
                outputFile,
                StandardCharsets.UTF_8
        )) {

            w.write("# Competition history\n");
            w.write("# generated: " + LocalDateTime.now() + "\n\n");

            int step = 0;
            for (Competition c : history) {

                w.write("=== COMPETITION STEP " + step + " ===\n");

                writeMatrix(w, "OLD MATRIX", c.getOldMatrix());
                writeMatrix(w, "NEIGHBOR MATRIX", c.getNb());
                writeMatrix(w, "NEW MATRIX", c.getNewMatrix());

                w.write("\n");
                step++;
            }
        }
    }


    private static void writeMatrix(
            BufferedWriter w,
            String title,
            double[][] m
    ) throws IOException {

        w.write(title + ":\n");

        if (m == null) {
            w.write("(null)\n\n");
            return;
        }

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                w.write(String.format(Locale.US, "%.6f ", m[i][j]));
            }
            w.write("\n");
        }
        w.write("\n");
    }
}
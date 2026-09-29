package com.example.piratesgames.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BoatResultsIO {

    private BoatResultsIO() {}

    /** Czyści pliki i zapisuje nagłówki (wywołaj RAZ na start zapisu). */
    public static void initCommonFiles(Path resultsDir,
                                       Map<String, Object> config) throws IOException {
        Files.createDirectories(resultsDir);

        Path fileA = resultsDir.resolve("results_single_boat_a.txt");
        Path fileB = resultsDir.resolve("results_single_boat_b.txt");

        try (BufferedWriter a = Files.newBufferedWriter(fileA, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
             BufferedWriter b = Files.newBufferedWriter(fileB, StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            a.write("# Generated: " + LocalDateTime.now() + "\n");
            if (config != null) {
                for (var e : config.entrySet()) {
                    a.write("# " + e.getKey() + " = " + e.getValue() + "\n");
                }
            }
            a.write("# ------------------------------------------------------------\n\n");

            b.write("# Generated: " + LocalDateTime.now() + "\n");
            if (config != null) {
                for (var e : config.entrySet()) {
                    b.write("# " + e.getKey() + " = " + e.getValue() + "\n");
                }
            }
            b.write("# columns: boatId game envy egal prop ir totalF\n\n");
        }
    }

    /** Dopisuje do wspólnych plików wyniki JEDNEJ łodzi. */
    public static void appendBoatHistoryToCommonFiles(Boat boat, Path resultsDir) throws IOException {
        if (boat == null) return;

        Files.createDirectories(resultsDir);

        Path fileA = resultsDir.resolve("results_single_boat_a.txt");
        Path fileB = resultsDir.resolve("results_single_boat_b.txt");

        try (BufferedWriter a = Files.newBufferedWriter(fileA, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
             BufferedWriter b = Files.newBufferedWriter(fileB, StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            a.write("Boat " + boat.getId() + "\n");
            a.write("============================================================\n");

            int gameIndex = 1;
            List<SingleGame> history = boat.getGamesHistory();

            for (SingleGame g : history) {

                Map<String, Double> m = g.getMetrics();
                double envy = m.getOrDefault("envy_freeness", 0.0);
                double egal = m.getOrDefault("egalitarian", 0.0);
                double prop = m.getOrDefault("proportionality", 0.0);
                double ir   = m.getOrDefault("individual_rationality", 0.0);
                double total = g.getTotalFairenss();

                // A: szczegółowo
                a.write("Boat " + boat.getId() + " | Game " + gameIndex + "\n");
                a.write("Payoffs: " + joinInts(g.getPayments()) + "\n");
                a.write("LastRound: " + g.getLastRound() + "\n");
                if (g.getBoatDNA() != null) {
                    a.write("BoatDNA: " + g.getBoatDNA() + "\n");
                }
                a.write(String.format(Locale.US, "envy_freeness = %.10f%n", envy));
                a.write(String.format(Locale.US, "egalitarian = %.10f%n", egal));
                a.write(String.format(Locale.US, "proportionality = %.10f%n", prop));
                a.write(String.format(Locale.US, "individual_rationality = %.10f%n", ir));
                a.write(String.format(Locale.US, "Total_F = %.10f%n", total));
                a.write("------------------------------------------------------------\n\n");

                // B: kolumnowo (z boatId)
                b.write(String.format(Locale.US,
                        "%d %d %.10f %.10f %.10f %.10f %.10f%n",
                        boat.getId(), gameIndex, envy, egal, prop, ir, total));

                gameIndex++;
            }

            a.write("\n");
        }
    }
    public static void writeGnuplotScript(Path resultsDir,
                                          String dataFileName,
                                          int boatIdToPlot
    ) throws IOException {
        Files.createDirectories(resultsDir);

        Path gp = resultsDir.resolve("plot_single_boat.gp");

        try (BufferedWriter w = Files.newBufferedWriter(gp, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            w.write("# Auto-generated gnuplot script\n");
            w.write("datafile = \"" + dataFileName + "\"\n");
            w.write("boat = " + boatIdToPlot + "\n\n");

            // --- TotalF plot (PNG) ---
            w.write("set terminal pngcairo size 1200,700\n");
            w.write("set output \"totalF_boat\".boat.\".png\"\n");
            w.write("set title \"Total fairness (boat \".boat.\")\"\n");
            w.write("set xlabel \"game\"\n");
            w.write("set ylabel \"totalF\"\n");
            w.write("set grid\n");
            w.write("plot datafile using ( $1==boat ? $2 : 1/0 ):( $7 ) with linespoints title \"totalF\"\n");
            w.write("set output\n\n");

            // --- Metrics plot (PNG) ---
            w.write("set output \"metrics_boat\".boat.\".png\"\n");
            w.write("set title \"Fairness metrics (boat \".boat.\")\"\n");
            w.write("set xlabel \"game\"\n");
            w.write("set ylabel \"value\"\n");
            w.write("set grid\n");
            w.write("plot \\\n");
            w.write("  datafile using ( $1==boat ? $2 : 1/0 ):( $3 ) with linespoints title \"envy\", \\\n");
            w.write("  datafile using ( $1==boat ? $2 : 1/0 ):( $4 ) with linespoints title \"egal\", \\\n");
            w.write("  datafile using ( $1==boat ? $2 : 1/0 ):( $5 ) with linespoints title \"prop\", \\\n");
            w.write("  datafile using ( $1==boat ? $2 : 1/0 ):( $6 ) with linespoints title \"IR\"\n");
            w.write("set output\n\n");

            // (opcjonalnie) powrót do okna
            w.write("set terminal wxt\n");
            w.write("set title \"Total fairness (boat \".boat.\")\"\n");
            w.write("plot datafile using ( $1==boat ? $2 : 1/0 ):( $7 ) with linespoints title \"totalF\"\n");
        }
    }


    /** Wygodne wywołanie: zapisuje historię wielu łodzi do wspólnych plików. */
    public static void writeAllBoatsToCommonFiles(List<Boat> boats,
                                                  Path resultsDir,
                                                  Map<String, Object> config) throws IOException {
        initCommonFiles(resultsDir, config);
        if (boats == null) return;
        for (Boat boat : boats) {
            appendBoatHistoryToCommonFiles(boat, resultsDir);
        }
    }

    private static String joinInts(int[] arr) {
        if (arr == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}

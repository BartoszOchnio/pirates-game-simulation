package com.example.piratesgames;

import com.example.piratesgames.core.Boat;
import com.example.piratesgames.core.BoatDNA;
import com.example.piratesgames.core.SingleGame;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;


import java.util.*;

public class ResultsController {
    private static final List<String> STRATEGIES = List.of("H11","H3","H4","P21","P41","V5","V6");

    private int dnaY(String strategy) {
        if (strategy.startsWith("H")) return 1;
        if (strategy.startsWith("P")) return 2;
        if (strategy.startsWith("V")) return 3;
        return 0;
    }
    @FXML
    private ComboBox<Boat> boatSelector;

    @FXML
    private LineChart<Number, Number> roundChart;
    @FXML private ScatterChart<Number, Number> dnaChart;
    @FXML private NumberAxis dnaYAxis;
    @FXML
    private NumberAxis roundYAxis;

    @FXML
    private LineChart<Number, Number> fairnessChart;

    private List<Boat> boats;

    public void initialize() {
        boatSelector.setOnAction(e ->
                showBoat(boatSelector.getValue())
        );
        boatSelector.setOnAction(e -> showBoat(boatSelector.getValue()));

        setupRoundAxis();
        setupDnaAxis();
        boatSelector.setOnAction(e -> showBoat(boatSelector.getValue()));
    }
    private void setupRoundAxis() {
        roundYAxis.setAutoRanging(true);
        roundYAxis.setTickUnit(1);        // co 1
        roundYAxis.setMinorTickCount(0); // bez podziałek pośrednich
        roundYAxis.setForceZeroInRange(false);
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;

        boatSelector.getItems().setAll(boats);

        if (boats == null || boats.isEmpty()) {
            return;
        }

        boatSelector.getSelectionModel().selectFirst();

        showBoat(boatSelector.getValue());
    }


    private void showBoat(Boat boat) {
        if (boat == null) return;

        drawRoundChart(boat);
        drawDNAChart(boat);
        drawFairnessChart(boat);
    }
    private void setupDnaAxis() {
        dnaYAxis.setAutoRanging(false);
        dnaYAxis.setLowerBound(0.5);
        dnaYAxis.setUpperBound(3.5);
        dnaYAxis.setTickUnit(1);

        dnaYAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override public String toString(Number n) {
                int v = n.intValue();
                return switch (v) {
                    case 1 -> "H";
                    case 2 -> "P";
                    case 3 -> "V";
                    default -> "";
                };
            }
            @Override public Number fromString(String s) { return 0; }
        });
    }

    private void drawRoundChart(Boat boat) {
        roundChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Last round");

        int gameIndex = 1;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (SingleGame g : boat.getGamesHistory()) {
            int lr = g.getLastRound();
            series.getData().add(new XYChart.Data<>(gameIndex++, lr));

            if (lr < min) min = lr;
            if (lr > max) max = lr;
        }

        roundChart.getData().add(series);

        if (min == Integer.MAX_VALUE) {
            // brak danych
            roundYAxis.setAutoRanging(true);
            return;
        }

        if (min == max) {
            min = Math.max(1, min - 1);
            max = max + 1;
        }
        int padding = 2;

        roundYAxis.setAutoRanging(false);
        roundYAxis.setLowerBound(Math.max(1, min));
        roundYAxis.setUpperBound(max + padding);
        roundYAxis.setTickUnit(1);
        roundYAxis.setMinorTickCount(0);
        roundYAxis.setForceZeroInRange(false);
    }

    private String extractType(String gene) {
        if (gene == null) return "";
        if (gene.startsWith("H")) return "H";
        if (gene.startsWith("P")) return "P";
        if (gene.startsWith("V")) return "V";
        return "";
    }
    private void drawDNAChart(Boat boat) {
        dnaChart.getData().clear();

        Map<String, XYChart.Series<Number, Number>> seriesMap = new LinkedHashMap<>();
        for (String s : STRATEGIES) {
            XYChart.Series<Number, Number> ser = new XYChart.Series<>();
            ser.setName(s);
            seriesMap.put(s, ser);
        }

        int game = 1;
        for (SingleGame g : boat.getGamesHistory()) {
            BoatDNA dna = g.getBoatDNA();

            String h = dna.gethCurr().replace("'", "");
            String p = dna.getpCurr().replace("'", "");
            String v = dna.getvCurr().replace("'", "");

            seriesMap.get(h).getData().add(new XYChart.Data<>(game, dnaY(h)));
            seriesMap.get(p).getData().add(new XYChart.Data<>(game, dnaY(p)));
            seriesMap.get(v).getData().add(new XYChart.Data<>(game, dnaY(v)));

            game++;
        }

        dnaChart.getData().addAll(seriesMap.values());
    }


    private void drawFairnessChart(Boat boat) {
        fairnessChart.getData().clear();

        Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();

        int i = 1;
        for (SingleGame g : boat.getGamesHistory()) {

            for (var entry : g.getMetrics().entrySet()) {
                seriesMap
                        .computeIfAbsent(entry.getKey(), k -> {
                            XYChart.Series<Number, Number> s = new XYChart.Series<>();
                            s.setName(k);
                            return s;
                        })
                        .getData()
                        .add(new XYChart.Data<>(i, entry.getValue()));
            }

            // totalFairness
            seriesMap
                    .computeIfAbsent("total", k -> {
                        XYChart.Series<Number, Number> s = new XYChart.Series<>();
                        s.setName("Total");
                        return s;
                    })
                    .getData()
                    .add(new XYChart.Data<>(i, g.getTotalFairenss()));

            i++;
        }

        fairnessChart.getData().addAll(seriesMap.values());
    }



}

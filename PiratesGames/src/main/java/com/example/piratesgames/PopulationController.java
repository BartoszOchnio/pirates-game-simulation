package com.example.piratesgames;

import com.example.piratesgames.core.Boat;
import com.example.piratesgames.core.BoatDNA;
import com.example.piratesgames.core.SingleGame;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;
import java.util.Locale;

public class PopulationController {

    @FXML private Spinner<Integer> gameSpinner;
    @FXML private Button prevBtn;
    @FXML
    private Button nextBtn;

    @FXML private GridPane lastRoundGrid;
    @FXML private GridPane ftotalGrid;
    @FXML private GridPane hGrid;
    @FXML private GridPane pGrid;
    @FXML private GridPane vGrid;

    @FXML private LineChart<Number, Number> popFChart;

    private List<Boat> boats;
    private int T;
    private int gridSize;

    public void initialize() {
        prevBtn.setOnAction(e -> gameSpinner.getValueFactory().setValue(gameSpinner.getValue() - 1));
        nextBtn.setOnAction(e -> gameSpinner.getValueFactory().setValue(gameSpinner.getValue() + 1));

        gameSpinner.valueProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) renderGame(newV);
        });
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;

        int K = boats.size();
        this.gridSize = (int) Math.round(Math.sqrt(K));
        this.T = boats.isEmpty() ? 0 : boats.get(0).getGamesHistory().size();

        gameSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Math.max(1, T), 1));
        prevBtn.disableProperty().bind(gameSpinner.valueProperty().isEqualTo(1));
        nextBtn.disableProperty().bind(gameSpinner.valueProperty().isEqualTo(T));

        buildEmptyGrids();
        drawPopulationFChart();
        renderGame(1);
    }

    private void buildEmptyGrids() {
        initGrid(lastRoundGrid);
        initGrid(ftotalGrid);
        initGrid(hGrid);
        initGrid(pGrid);
        initGrid(vGrid);
    }

    private void initGrid(GridPane grid) {
        grid.getChildren().clear();
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        for (int r = 0; r < gridSize; r++) {
            RowConstraints rc = new RowConstraints(28);
            grid.getRowConstraints().add(rc);
        }
        for (int c = 0; c < gridSize; c++) {
            ColumnConstraints cc = new ColumnConstraints(60);
            grid.getColumnConstraints().add(cc);
        }


        for (int i = 0; i < boats.size(); i++) {
            int r = i / gridSize;
            int c = i % gridSize;

            Label cell = new Label("");
            cell.setMinSize(60, 28);
            cell.setMaxSize(60, 28);
            cell.setAlignment(Pos.CENTER);

            grid.add(cell, c, r);
        }
    }

    private void renderGame(int gameIndex1Based) {
        int gi = gameIndex1Based - 1;

        for (int i = 0; i < boats.size(); i++) {
            Boat b = boats.get(i);
            SingleGame g = b.getGamesHistory().get(gi);

            setCellText(lastRoundGrid, i, String.valueOf(g.getLastRound()));
            setCellText(ftotalGrid, i, format2(g.getTotalFairenss()));

            BoatDNA dna = g.getBoatDNA();
            setCellText(hGrid, i, clean(dna.gethCurr()));
            setCellText(pGrid, i, clean(dna.getpCurr()));
            setCellText(vGrid, i, clean(dna.getvCurr()));
        }
    }

    private void setCellText(GridPane grid, int index, String text) {
        Node n = grid.getChildren().get(index); // bo dodaliśmy w tej samej kolejności
        if (n instanceof Label lbl) lbl.setText(text);
    }

    private String clean(String s) {
        return s == null ? "" : s.replace("'", "");
    }

    private String format2(double v) {
        return String.format(Locale.US, "%.2f", v);
    }

    private void drawPopulationFChart() {
        popFChart.getData().clear();

        XYChart.Series<Number, Number> best = new XYChart.Series<>();
        best.setName("Best F total");

        XYChart.Series<Number, Number> avg = new XYChart.Series<>();
        avg.setName("Avg F total");

        for (int game = 1; game <= T; game++) {
            int gi = game - 1;
            double sum = 0.0;
            double max = Double.NEGATIVE_INFINITY;

            for (Boat b : boats) {
                double f = b.getGamesHistory().get(gi).getTotalFairenss();
                sum += f;
                if (f > max) max = f;
            }

            best.getData().add(new XYChart.Data<>(game, max));
            avg.getData().add(new XYChart.Data<>(game, sum / boats.size()));
        }

        popFChart.getData().addAll(best, avg);
    }
}

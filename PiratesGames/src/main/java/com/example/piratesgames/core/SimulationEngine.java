package com.example.piratesgames.core;

import com.example.piratesgames.PopulationController;
import com.example.piratesgames.ResultsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class SimulationEngine {
    private int [] h11;
    private StrategyMap strategyMap = new StrategyMap();
    private Map<String, Double> metrics = new HashMap<>();
    private Map<String, Boolean> debugs = new HashMap<>();
    private List<String> hAlgosList = new ArrayList<>();
    private List<String> vAlgosList = new ArrayList<>();
    List<Boat> boats = new ArrayList<>();
    private List<Competition> competitionHistory = new ArrayList<>();

    public void startSimulation(
            int k, int m, int n, int c, int t, int del_T, int del_t,

            double hFrom, double hTo, double hStep,
            double pFrom, double pTo, double pStep,
            double vFrom, double vTo, double vStep,

            Map<String, Double> selectedMetrics,
            Map<String, Boolean> debugs,
            int hContinuation,
            int pStart, int pContinuation,
            int prStart, int prContinuation,
            int vStart, int vContinuation,

            List<String> hAlgos, List<String> pAlgos, List<String> prAlgos, List<String> vAlgos,

            int v5KFrom, int v5KTo, int v6KFrom, int v6KTo
    ) throws IOException {
        System.out.println("Silnik");
        this.debugs = debugs;
        System.out.println(this.getDebugs().size());
        try {
            loadAllStrategies(hAlgos, pAlgos, prAlgos, vAlgos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path out = Path.of("../../Debug data/debug.txt");
        String fileName = "Rand_num.txt";
        double[][] M = MatrixLoader.readMatrixFromFile(fileName, m, n);;
        for (Map.Entry<String, Boolean> entry : this.debugs.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                System.out.println("DEBUG ON: " + entry.getKey());
                switch (entry.getKey()){
                    case "test_1" ->{
                        k = 16;
                        m = 4;
                        n = 4;
                        t = 5;
                        fileName = "Rand_16_1.txt";
                        M = MatrixLoader.readMatrixFromFile(fileName, m, n);
                        out = Path.of("../../Debug data/debug-exp-1.txt");
                        break;
                    }
                    case "test_2" ->{
                        k = 16;
                        m = 4;
                        n = 4;
                        t = 4;
                        fileName = "Rand_16_2.txt";
                        M = MatrixLoader.readMatrixFromFile(fileName, m, n);
                        out = Path.of("../../Debug data/debug-exp-2.txt");
                        break;
                    }
                    case "test_3" ->{
                        k = 9;
                        m = 3;
                        n = 3;
                        t = 4;
                        fileName = "Rand_9_1.txt";
                        M = MatrixLoader.readMatrixFromFile(fileName, m, n);
                        out = Path.of("../../Debug data/debug-exp-3.txt");
                        break;
                    }
                    case "test_4" ->{
                        k = 25;
                        m = 5;
                        n = 5;
                        t = 4;
                        fileName = "Rand_25_1.txt";
                        M = MatrixLoader.readMatrixFromFile(fileName, m, n);
                        out = Path.of("../../Debug data/debug-exp-4.txt");
                        break;
                    }
                }
            }
        }
        createBoats(k, c);
        showAllBoats();


        // tworzenie i granie gier ?
        for(int i=0; i<t; i++){
            for (Boat boat : boats) {
                BoatDNA boatDNA = null;
                if(i == 0) {
                    boatDNA = randomBoatDNA(
                            hAlgos, pAlgos, prAlgos, vAlgos,
                            hFrom, hTo, hStep,
                            pFrom, pTo, pStep,
                            vFrom, vTo, vStep,
                            del_t, del_T);


                    SingleGame singleGame = createSingleGame(boat, boatDNA);
                    switch (singleGame.getBoatDNA().getvCurr()) {
                        case "V5" -> {
                            int[] ks = new int[9];
                            for (int j = 0; j < 9; j++) {
                                ks[j] = randomIntInclusive(v5KFrom, v5KTo);
                            }
                            singleGame.setvK(ks);
                        }
                        case "V6" -> {
                            int[] ks = new int[9];
                            for (int j = 0; j < 9; j++) {
                                ks[j] = randomIntInclusive(v6KFrom, v6KTo);
                            }
                            singleGame.setvK(ks);
                        }
                    }
                    singleGame.playSingleGamev2(getStrategyMap(), selectedMetrics);
                    boat.getGamesHistory().add(singleGame);
                }else {

                    BoatDNA prevDNA =
                            boat.getGamesHistory()
                                    .get(boat.getGamesHistory().size() - 1)
                                    .getBoatDNA();

                    BoatDNA dnaCopy = new BoatDNA(prevDNA);

                    SingleGame singleGame = createSingleGame(boat, dnaCopy);
                    chngeStrategiesIfNecessary(singleGame.getBoatDNA(), i, hAlgos, pAlgos, prAlgos, vAlgos);


                    switch (singleGame.getBoatDNA().getvCurr()) {
                        case "V5" -> {
                            int[] ks = new int[9];
                            for (int j = 0; j < 9; j++) {
                                ks[j] = randomIntInclusive(v5KFrom, v5KTo);
                            }
                            singleGame.setvK(ks);
                        }
                        case "V6" -> {
                            int[] ks = new int[9];
                            for (int j = 0; j < 9; j++) {
                                ks[j] = randomIntInclusive(v6KFrom, v6KTo);
                            }
                            singleGame.setvK(ks);
                        }
                    }
                    singleGame.playSingleGamev2(getStrategyMap(), selectedMetrics);
                    boat.getGamesHistory().add(singleGame);
                }


            }

                Competition competition = null;
                if (i == 0) {
                    competition = new Competition(M);
                    competition.setNewMatrix(competition.getOldMatrix());
                    competitionHistory.add(competition);
                    competition = new Competition(competition);
                    competition.playSingleCompetition();
                } else {
                    competition = new Competition(competitionHistory.get(competitionHistory.size() - 1));
                }
                competition.playSingleCompetition();
                competitionHistory.add(competition);

        }

        saveGames(boats, selectedMetrics, k ,m, n, c, t, del_T, del_t);
        System.out.println("Zapisano pomyślnie");


       openGamesView(boats);
       openPopulationView(boats);


       CompetitionResultsIO.writeCompetitionHistory(competitionHistory, out);
       System.out.println("Competition zapisano pomyślnie");

    }
    private void openPopulationView(List<Boat> boats) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/piratesgames/population_view.fxml")
            );

            Parent root = loader.load();

            PopulationController controller = loader.getController();
            controller.setBoats(boats);

            Scene scene = new Scene(root, 1400, 900);

            URL css = getClass().getResource("/com/example/piratesgames/charts.css");
            if (css != null) scene.getStylesheets().add(css.toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Pirates Game – Population View");
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openGamesView(List<Boat> boats) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/piratesgames/results_view.fxml")
        );

        Parent root = loader.load();

        ResultsController controller = loader.getController();
        controller.setBoats(boats);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(
                getClass().getResource("/com/example/piratesgames/charts.css").toExternalForm()
        );

        Stage stage = new Stage();
        stage.setTitle("Pirates Game – Results");
        stage.setScene(scene);
        stage.show();
    }

    public void saveGames(List<Boat> boats, Map<String, Double> selectedMetrics, int k, int m, int n, int c, int t, int del_T, int del_t) throws IOException {

        Map<String, Object> config = new java.util.LinkedHashMap<>();
        config.put("K", k);
        config.put("M", m);
        config.put("N", n);
        config.put("C", c);
        config.put("T", t);
        config.put("del_T", del_T);
        config.put("del_t", del_t);
        for (Map.Entry<String, Double> entry : selectedMetrics.entrySet()) {
            config.put(entry.getKey(), entry.getValue().toString());
        }
        config.put("boat_DNA", "boat_DNA.txt");

        BoatResultsIO.writeAllBoatsToCommonFiles(boats, Path.of("../../RESULTS"), config);
        BoatResultsIO.writeGnuplotScript(Path.of("../../gnu-Results"),
                "../RESULTS/results_single_boat_b.txt",
                0
        );
    }

    private SingleGame createSingleGame(Boat boat, BoatDNA boatDNA){
        boolean[] zeroIteration = new boolean[] {
                false, false, false, false, false, false, false, false, true
        };
        SingleGame singleGame = new SingleGame(boat.getPlayers(), boatDNA, zeroIteration);
        return singleGame;
    }

    public void chngeStrategiesIfNecessary(BoatDNA boatDNA, int iter, List<String> hAlgos,List<String> pAlgos,List<String> prAlgos,List<String> vAlgos) throws IOException {
        int delt = boatDNA.getDel_t();
        if (delt <= 1) delt = 1;

        if((iter) % delt == 0){
            boatDNA.sethCurr(boatDNA.gethNext());
            boatDNA.sethNext(randH(hAlgos));
            // dodać modyfikację hierarchii
            switch (boatDNA.gethCurr()){
                case "H3" ->{

                }
                case "H4" ->{
                    if (this.h11 == null || this.h11.length == 0) return;

                    int first = this.h11[0];
                    System.arraycopy(this.h11, 1, this.h11, 0, this.h11.length - 1);
                    this.h11[this.h11.length - 1] = first;
                }
                case  "H11" ->{
                    int[][] H11 = MatrixLoader.loadMatrix("h/11.txt", 1, 9);
                }
            }
        }

        int detT = boatDNA.getDet_T();
        if (detT <= 1) detT = 1;

        if((iter) % detT == 0){
            boatDNA.setpCurr(boatDNA.getpNext());
            boatDNA.setpNext(randP(pAlgos));

            boatDNA.setPrCurr(boatDNA.getPrNext());
            boatDNA.setPrNext(randPr(prAlgos));

            boatDNA.setvCurr(boatDNA.getvNext());
            boatDNA.setvNext(randV(vAlgos));
        }

    }
    public String randH(List<String> hAlgos){

        return  randomAlgoFromList(hAlgos, "H");
    }
    public String randP(List<String>pAlgos){

        return  randomAlgoFromList(pAlgos, "P");
    }
    public String randPr(List<String>prAlgos){

        return  randomAlgoFromList(prAlgos, "PR");
    }
    public String randV(List<String>vAlgos){

        return  randomAlgoFromList(vAlgos, "V");
    }

    private BoatDNA randomBoatDNA(
            List<String> hAlgos,
            List<String> pAlgos,
            List<String> prAlgos,
            List<String> vAlgos,

            double hFrom, double hTo, double hStep,
            double pFrom, double pTo, double pStep,
            double vFrom, double vTo, double vStep,

            int del_t,
            int del_T
    ) {

        // --- H ---
        String hAlgoNext = randomAlgoFromList(hAlgos, "H");
        double hMut = randomFromStep(hFrom, hTo, hStep);

        // --- P ---
        String pAlgo     = randomAlgoFromList(pAlgos, "P");
        String pAlgoNext = randomAlgoFromList(pAlgos, "P");
        double pMut      = randomFromStep(pFrom, pTo, pStep);

        // --- PR ---
        String prAlgo     = randomAlgoFromList(prAlgos, "PR");
        String prAlgoNext = randomAlgoFromList(prAlgos, "PR");
        double prMut      = randomFromStep(pFrom, pTo, pStep);

        // --- V ---
        String vAlgo     = randomAlgoFromList(vAlgos, "V");
        String vAlgoNext = randomAlgoFromList(vAlgos, "V");
        double vMut      = randomFromStep(vFrom, vTo, vStep);

        return new BoatDNA(
                "H11",
                del_t,
                hAlgoNext, hMut,
                pAlgo, pAlgoNext, pMut,
                prAlgo, prAlgoNext, prMut,
                vAlgo, vAlgoNext, vMut,
                del_T
        );
    }


    private String randomAlgoFromList(List<String> algos, String name) {
        if (algos == null || algos.isEmpty()) {
            throw new IllegalArgumentException("Lista algorytmów jest pusta: " + name);
        }
        int idx = ThreadLocalRandom.current().nextInt(algos.size());
        return algos.get(idx);
    }

    public void createBoats(int k, int c){
        System.out.println("Tworzenie łodzi");
        for(int i=0; i<k; i++){
            //Tworzenie łodzi
            int [] players = this.h11;
            GameHistory gameHistory = new GameHistory();
            Boat boat = new Boat(i, 9, players, c);
            boats.add(boat);
        }
    }
    public void showAllBoats() {
        System.out.println("===== BOATS =====");
        for (Boat boat : boats) {
            System.out.println(boat);
        }
        System.out.println("=================");
    }


    public void loadAllStrategies(List<String> hAlgos, List<String> pAlgos, List<String> prAlgos, List<String> vAlgos) throws IOException {
        hAlgosList.clear();
        vAlgosList.clear();
        strategyMap.getP_pr_v_map().clear();

        if (hAlgos != null) {
            hAlgosList.addAll(hAlgos);
        }

        if (vAlgos != null) {
            vAlgosList.addAll(vAlgos);
        }

        // --- H ---
        System.out.println("Tutaj działa");
        int[][] H11 = MatrixLoader.loadMatrix("h/11.txt", 1, 9);
        System.out.println("Tutaj już nie działa");
        Matrix matrixH11 = new Matrix(1, 9, H11);
        matrixH11.printMatrix();

        // --- P ---
        if (pAlgos.contains("P21")) {
            int[][] p21 = MatrixLoader.loadMatrix("p/21.txt", 9, 9);
            Matrix matrixP21 = new Matrix(9, 9, p21);
            strategyMap.getP_pr_v_map().put("P21", matrixP21.getData());
            matrixP21.printMatrix();
        }

        if (pAlgos.contains("P41")) {
            int[][] p41 = MatrixLoader.loadMatrix("p/41.txt", 9, 9);
            Matrix matrixP41 = new Matrix(9, 9, p41);
            strategyMap.getP_pr_v_map().put("P41", matrixP41.getData());
            matrixP41.printMatrix();
        }

        // --- PR ---
        if (prAlgos.contains("PR11")) {
            int[][] pr11 = MatrixLoader.loadMatrix("pr/11.txt", 9, 9);
            Matrix matrixPr11 = new Matrix(9, 9, pr11);
            strategyMap.getP_pr_v_map().put("PR11", matrixPr11.getData());
            matrixPr11.printMatrix();
        }

        this.h11 = matrixH11.getData()[0];
    }

    private double randomFromStep(double from, double to, double step) {
        if (step <= 0) throw new IllegalArgumentException(": step musi być > 0");
        if (from > to) throw new IllegalArgumentException(": from nie może być > to");

        int count = (int) Math.floor((to - from) / step) + 1;
        if (count <= 0) throw new IllegalArgumentException(": zły zakres/step");

        int idx = ThreadLocalRandom.current().nextInt(count);
        double value = from + idx * step;
        
        value = Math.round(value * 1000.0) / 1000.0;
        
        return Math.min(value, to);
    }

    private int randomIntInclusive(int from, int to) {
        if (from > to) throw new IllegalArgumentException(": from nie może być > to");
        return ThreadLocalRandom.current().nextInt(from, to + 1);
    }

    public StrategyMap getStrategyMap() {
        return strategyMap;
    }

    public void setStrategyMap(StrategyMap strategyMap, Map<String, Double> metrics, Map<String, Boolean> debugs) {
        this.strategyMap = strategyMap;
        this.metrics = metrics;
        this.debugs = debugs;
    }

    public int[] getH11() {
        return h11;
    }

    public void setH11(int[] h11) {
        this.h11 = h11;
    }

    public void setStrategyMap(StrategyMap strategyMap) {
        this.strategyMap = strategyMap;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Double> metrics) {
        this.metrics = metrics;
    }

    public Map<String, Boolean> getDebugs() {
        return debugs;
    }

    public void setDebugs(Map<String, Boolean> debugs) {
        this.debugs = debugs;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    public List<String> gethAlgosList() {
        return hAlgosList;
    }

    public void sethAlgosList(List<String> hAlgosList) {
        this.hAlgosList = hAlgosList;
    }

    public List<String> getvAlgosList() {
        return vAlgosList;
    }

    public void setvAlgosList(List<String> vAlgosList) {
        this.vAlgosList = vAlgosList;
    }

    // pomocniczy rekord/klasa
    private static class ParsedGene {
        String curr;
        int del;
        String next;
        double mut;
        ParsedGene(String curr, int del, String next, double mut) {
            this.curr = curr; this.del = del; this.next = next; this.mut = mut;
        }
    }

    private static ParsedGene parseGeneSegment(String seg) {
        // seg np: "H4', 2, H4', 0.2"
        // usuń apostrofy '
        seg = seg.replace("'", "").trim();

        // rozbij po przecinkach
        String[] t = seg.split(",");
        if (t.length < 4) {
            throw new IllegalArgumentException("Zły segment DNA: [" + seg + "], oczekuję 4 elementów (curr, del, next, mut)");
        }

        String curr = t[0].trim();
        int del = Integer.parseInt(t[1].trim());
        String next = t[2].trim();
        double mut = Double.parseDouble(t[3].trim().replace(",", ".")); // na wszelki wypadek

        return new ParsedGene(curr, del, next, mut);
    }
}

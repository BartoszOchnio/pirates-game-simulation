package com.example.piratesgames;

import com.example.piratesgames.conf.ConfigIO;
import com.example.piratesgames.conf.SimulationConfig;
import com.example.piratesgames.core.SimulationEngine;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PiratesGamesController {

    // --- Run settings: seed ---
    @FXML private RadioButton seedClockRadio;
    @FXML private RadioButton seedCustomRadio;
    @FXML private TextField customSeedField;

    @FXML private Spinner<Integer> kSpinner;
    @FXML private Spinner<Integer> mSpinner;
    @FXML private Spinner<Integer> nSpinner;
    @FXML private Spinner<Integer> coinsSpinner;
    @FXML private Spinner<Integer> qStepSpinner;
    @FXML private Spinner<Integer> gamesSpinner;
    @FXML private Spinner<Integer> delTSpinner;
    @FXML private Spinner<Integer> delSmallTSpinner;

    // --- Life awareness ---
    @FXML private Spinner<Integer> singleGameCostSpinner;
    @FXML private Spinner<Integer> initialCreditSpinner;

    // --- Algo checkboxes ---
    @FXML private CheckBox h1CheckBox;
    @FXML private CheckBox h3CheckBox;
    @FXML private CheckBox h4CheckBox;

    @FXML private CheckBox p2CheckBox;
    @FXML private CheckBox p4CheckBox;

    @FXML private CheckBox pr1CheckBox;

    @FXML private CheckBox v5CheckBox;
    @FXML private CheckBox v6CheckBox;

    @FXML private TextField crossoverNameField;
    @FXML private TextField crossoverFromField;
    @FXML private TextField crossoverToField;
    @FXML private TextField crossoverStepField;

    @FXML private TextField hMutNameField;
    @FXML private TextField hMutFromField;
    @FXML private TextField hMutToField;
    @FXML private TextField hMutStepField;

    @FXML private TextField pMutNameField;
    @FXML private TextField pMutFromField;
    @FXML private TextField pMutToField;
    @FXML private TextField pMutStepField;

    @FXML private TextField prMutNameField;
    @FXML private TextField prMutFromField;
    @FXML private TextField prMutToField;
    @FXML private TextField prMutStepField;

    @FXML private TextField vMutNameField;
    @FXML private TextField vMutFromField;
    @FXML private TextField vMutToField;
    @FXML private TextField vMutStepField;


    // --- Metrics of fairness (FXML) ---
    @FXML private CheckBox envyCheckBox;
    @FXML private TextField envyWeightField;

    @FXML private CheckBox egalitarianCheckBox;
    @FXML private TextField egalitarianWeightField;

    @FXML private CheckBox proportionalityCheckBox;
    @FXML private TextField proportionalityWeightField;

    @FXML private CheckBox individualRationalityCheckBox;
    @FXML private TextField individualRationalityWeightField;


    @FXML private CheckBox utilitarianCheckBox;
    @FXML private TextField utilitarianWeightField;


    @FXML private TextField p1, p2, p3, p4, p5, p6, p7, p8, p9;
    @FXML private TextField del1, del2, del3, del4, del5, del6, del7, del8, del9;
    @FXML private TextField alpha1, alpha2, alpha3, alpha4, alpha5, alpha6, alpha7, alpha8, alpha9;
    @FXML private TextField t1, t2, t3, t4, t5, t6, t7, t8, t9;

    @FXML private TextField alpEmaField;
    @FXML private TextField betaField;
    @FXML private TextField gammaField;
    @FXML private TextField etaField;
    @FXML private TextField delTAggrField;

    @FXML private TextField fairFloorField;
    @FXML private TextField ffFromField;
    @FXML private TextField ffToField;
    @FXML private TextField ffStepField;

    @FXML private TextField probBossExecField;
    @FXML private TextField pbeFromField;
    @FXML private TextField pbeToField;
    @FXML private TextField pbeStepField;


    @FXML private CheckBox test1CheckBox;
    @FXML private CheckBox test2CheckBox;
    @FXML private CheckBox test3CheckBox;
    @FXML private CheckBox test4CheckBox;

    @FXML private CheckBox h5CheckBox;
    @FXML private GridPane h5ParamsPane;

    @FXML private ChoiceBox<String> hStartChoice;

    @FXML private Spinner<Integer> hContinuationSpinner;

    @FXML private Spinner<Integer> pStartSpinner;
    @FXML private Spinner<Integer> pContinuationSpinner;

    @FXML private Spinner<Integer> prStartSpinner;
    @FXML private Spinner<Integer> prContinuationSpinner;

    @FXML private Spinner<Integer> vStartSpinner;
    @FXML private Spinner<Integer> vContinuationSpinner;

    @FXML private Spinner<Integer> v5KFromSpinner;
    @FXML private Spinner<Integer> v5KToSpinner;

    @FXML private Spinner<Integer> v6KFromSpinner;
    @FXML private Spinner<Integer> v6KToSpinner;


    @FXML private Button startButton;
    @FXML private Label resultLabel;

    @FXML
    public void initialize() {
        ToggleGroup seedGroup = new ToggleGroup();
        seedClockRadio.setToggleGroup(seedGroup);
        seedCustomRadio.setToggleGroup(seedGroup);

        customSeedField.setDisable(!seedCustomRadio.isSelected());
        seedCustomRadio.selectedProperty().addListener((obs, wasSelected, isSelected) ->
                customSeedField.setDisable(!isSelected)
        );
        hStartChoice.getSelectionModel().selectFirst();
        hStartChoice.setDisable(true);

        envyWeightField.disableProperty().bind(envyCheckBox.selectedProperty().not());
        egalitarianWeightField.disableProperty().bind(egalitarianCheckBox.selectedProperty().not());
        proportionalityWeightField.disableProperty().bind(proportionalityCheckBox.selectedProperty().not());
        individualRationalityWeightField.disableProperty().bind(individualRationalityCheckBox.selectedProperty().not());
        utilitarianWeightField.disableProperty().bind(utilitarianCheckBox.selectedProperty().not());


        h5ParamsPane.visibleProperty().bind(h5CheckBox.selectedProperty());
        h5ParamsPane.managedProperty().bind(h5CheckBox.selectedProperty());
    }

    private double readDouble(TextField tf, String name) {
        String raw = tf.getText();
        if (raw == null) throw new IllegalArgumentException("Brak wartości: " + name);

        raw = raw.trim().replace(',', '.'); // na wypadek "0,1"
        if (raw.isEmpty()) throw new IllegalArgumentException("Puste pole: " + name);

        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Niepoprawna liczba w polu " + name + ": \"" + tf.getText() + "\"");
        }
    }

    private int readInt(TextField field, String fieldName) {
        String text = field.getText();
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Puste pole: " + fieldName);
        }

        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nieprawidłowa liczba całkowita w polu: " + fieldName + " = " + text);
        }
    }

    private double readWeight(TextField field, double defaultValue) {
        if (field == null) return defaultValue;
        String s = field.getText();
        if (s == null) return defaultValue;

        s = s.trim().replace(',', '.');
        if (s.isEmpty()) return defaultValue;

        try {
            double v = Double.parseDouble(s);
            if (Double.isNaN(v) || Double.isInfinite(v)) return defaultValue;
            return Math.max(0.0, v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Map<String, Double> readSelectedFairnessMetrics() {
        Map<String, Double> metrics = new java.util.LinkedHashMap<>();

        if (envyCheckBox.isSelected()) {
            metrics.put("envy_freeness", readWeight(envyWeightField, 0.0));
        }
        if (egalitarianCheckBox.isSelected()) {
            metrics.put("egalitarian", readWeight(egalitarianWeightField, 0.0));
        }
        if (proportionalityCheckBox.isSelected()) {
            metrics.put("proportionality", readWeight(proportionalityWeightField, 0.0));
        }
        if (individualRationalityCheckBox.isSelected()) {
            metrics.put("individual_rationality", readWeight(individualRationalityWeightField, 0.0));
        }

        if (utilitarianCheckBox.isSelected()) {
            metrics.put("utilitarian", readWeight(utilitarianWeightField, 0.0));
        }


        return metrics;
    }

    private Map<String, Boolean> readDebugFlags() {
        Map<String, Boolean> debugs = new java.util.LinkedHashMap<>();
        debugs.put("test_1", test1CheckBox.isSelected());
        debugs.put("test_2", test2CheckBox.isSelected());
        debugs.put("test_3", test3CheckBox.isSelected());
        debugs.put("test_4", test4CheckBox.isSelected());
        return debugs;
    }

    private void validateRange(double from, double to, double step, String name) {
        if (from < 0 || to < 0 || from > 1 || to > 1)
            throw new IllegalArgumentException(name + " musi być w zakresie [0..1].");
        if (from > to)
            throw new IllegalArgumentException(name + ": from nie może być > to.");
        if (step <= 0)
            throw new IllegalArgumentException(name + ": step musi być > 0.");
    }
    private int readSpinnerInt(Spinner<Integer> sp, String name) {

        var vf = sp.getValueFactory();
        if (sp.isEditable()) {
            String text = sp.getEditor().getText();
            try {
                int val = Integer.parseInt(text.trim());
                vf.setValue(val);
            } catch (Exception e) {
                throw new IllegalArgumentException("Niepoprawna liczba w spinnerze " + name + ": \"" + text + "\"");
            }
        }
        return vf.getValue();
    }

    @FXML
    private void onSaveConfigClicked() {
        try {
            SimulationConfig cfg = readConfigFromGui();

            Path configDir = Path.of("../../gui-config").toAbsolutePath().normalize();
            Files.createDirectories(configDir);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

            Path file = configDir.resolve("simulation_config_" + timestamp + ".json");

            ConfigIO.saveToJson(cfg, file.toFile());

            System.out.println("Config zapisany do: " + file);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd zapisu konfiguracji: " + e.getMessage(), e);
        }
    }

    private SimulationConfig readConfigFromGui() {
        SimulationConfig cfg = new SimulationConfig();

        if (seedCustomRadio.isSelected()) {
            String s = customSeedField.getText();
            cfg.setSeed(Long.parseLong(s.trim()));
        } else {
            cfg.setSeed(System.currentTimeMillis());
        }

        cfg.setK(kSpinner.getValue());
        cfg.setM(mSpinner.getValue());
        cfg.setN(nSpinner.getValue());
        cfg.setC(coinsSpinner.getValue());
        cfg.setqStep(qStepSpinner.getValue());
        cfg.setT(gamesSpinner.getValue());
        cfg.setDelT(delTSpinner.getValue());
        cfg.setDelSmallT(delSmallTSpinner.getValue());
        cfg.setSingleGameCost(singleGameCostSpinner.getValue());
        cfg.setInitialCredit(initialCreditSpinner.getValue());

        // crossover
        cfg.setcName(readDouble(crossoverNameField, "cross_prob (name)"));
        cfg.setcFrom(readDouble(crossoverFromField, "cross_prob (from)"));
        cfg.setcTo(readDouble(crossoverToField, "cross_prob (to)"));
        cfg.setcStep(readDouble(crossoverStepField, "cross_prob (step)"));

        // mutation H
        cfg.sethName(readDouble(hMutNameField, "prob_H_mut (name)"));
        cfg.sethFrom(readDouble(hMutFromField, "prob_H_mut (from)"));
        cfg.sethTo(readDouble(hMutToField, "prob_H_mut (to)"));
        cfg.sethStep(readDouble(hMutStepField, "prob_H_mut (step)"));

        // mutation P
        cfg.setpName(readDouble(pMutNameField, "prob_P_mut (name)"));
        cfg.setpFrom(readDouble(pMutFromField, "prob_P_mut (from)"));
        cfg.setpTo(readDouble(pMutToField, "prob_P_mut (to)"));
        cfg.setpStep(readDouble(pMutStepField, "prob_P_mut (step)"));

        // mutation PR
        cfg.setPrName(readDouble(prMutNameField, "prob_PR_mut (name)"));
        cfg.setPrFrom(readDouble(prMutFromField, "prob_PR_mut (from)"));
        cfg.setPrTo(readDouble(prMutToField, "prob_PR_mut (to)"));
        cfg.setPrStep(readDouble(prMutStepField, "prob_PR_mut (step)"));

        // mutation V
        cfg.setvName(readDouble(vMutNameField, "prob_V_mut (name)"));
        cfg.setvFrom(readDouble(vMutFromField, "prob_V_mut (from)"));
        cfg.setvTo(readDouble(vMutToField, "prob_V_mut (to)"));
        cfg.setvStep(readDouble(vMutStepField, "prob_V_mut (step)"));

        // starts / continuation
        String hStart = hStartChoice.getValue();
        hStart = "H1";
        if (hStart == null) {
            throw new IllegalArgumentException("Nie wybrano start dla H algos.");
        }
        cfg.sethStart(hStart);

        cfg.sethContinuation(readSpinnerInt(hContinuationSpinner, "H continuation"));

        cfg.setpStart(readSpinnerInt(pStartSpinner, "P start"));
        cfg.setpContinuation(readSpinnerInt(pContinuationSpinner, "P continuation"));

        cfg.setPrStart(readSpinnerInt(prStartSpinner, "PR start"));
        cfg.setPrContinuation(readSpinnerInt(prContinuationSpinner, "PR continuation"));

        cfg.setvStart(readSpinnerInt(vStartSpinner, "V start"));
        cfg.setvContinuation(readSpinnerInt(vContinuationSpinner, "V continuation"));

        // Risk model
        cfg.setP(new double[] {
                readDouble(p1, "p1"),
                readDouble(p2, "p2"),
                readDouble(p3, "p3"),
                readDouble(p4, "p4"),
                readDouble(p5, "p5"),
                readDouble(p6, "p6"),
                readDouble(p7, "p7"),
                readDouble(p8, "p8"),
                readDouble(p9, "p9")
        });

        cfg.setDel(new double[] {
                readDouble(del1, "del1"),
                readDouble(del2, "del2"),
                readDouble(del3, "del3"),
                readDouble(del4, "del4"),
                readDouble(del5, "del5"),
                readDouble(del6, "del6"),
                readDouble(del7, "del7"),
                readDouble(del8, "del8"),
                readDouble(del9, "del9")
        });

        cfg.setAlpha(new double[] {
                readDouble(alpha1, "alpha1"),
                readDouble(alpha2, "alpha2"),
                readDouble(alpha3, "alpha3"),
                readDouble(alpha4, "alpha4"),
                readDouble(alpha5, "alpha5"),
                readDouble(alpha6, "alpha6"),
                readDouble(alpha7, "alpha7"),
                readDouble(alpha8, "alpha8"),
                readDouble(alpha9, "alpha9")
        });

        cfg.setT(new int[] {
                readInt(t1, "t1"),
                readInt(t2, "t2"),
                readInt(t3, "t3"),
                readInt(t4, "t4"),
                readInt(t5, "t5"),
                readInt(t6, "t6"),
                readInt(t7, "t7"),
                readInt(t8, "t8"),
                readInt(t9, "t9")
        });

        // P-score
        cfg.setAlpEma(readDouble(alpEmaField, "alp_EMA"));
        cfg.setBeta(readDouble(betaField, "beta"));
        cfg.setGamma(readDouble(gammaField, "gamma"));
        cfg.setEta(readDouble(etaField, "eta"));
        cfg.setDelTAggr(readInt(delTAggrField, "del_T_aggr"));

        double weightsSum = cfg.getBeta() + cfg.getGamma() + cfg.getEta();
        if (Math.abs(weightsSum - 1.0) > 0.0001) {
            throw new IllegalArgumentException(
                    "beta + gamma + eta musi sumować się do 1.0, aktualnie = " + weightsSum
            );
        }

        // Additional parameters
        cfg.setFairFloor(readDouble(fairFloorField, "fair_floor"));
        cfg.setFfFrom(readDouble(ffFromField, "f_f_from"));
        cfg.setFfTo(readDouble(ffToField, "f_f_to"));
        cfg.setFfStep(readDouble(ffStepField, "f_f_step"));

        cfg.setProbBossExec(readDouble(probBossExecField, "prob_boss_exec"));
        cfg.setPbeFrom(readDouble(pbeFromField, "prob_boss_from"));
        cfg.setPbeTo(readDouble(pbeToField, "prob_boss_to"));
        cfg.setPbeStep(readDouble(pbeStepField, "prob_boss_step"));

        // algos
        List<String> hAlgos = new ArrayList<>();
        if (h1CheckBox.isSelected()) hAlgos.add("H11");
        if (h3CheckBox.isSelected()) hAlgos.add("H3");
        if (h4CheckBox.isSelected()) hAlgos.add("H4");
        cfg.sethAlgos(hAlgos);

        List<String> pAlgos = new ArrayList<>();
        if (p2CheckBox.isSelected()) pAlgos.add("P21");
        if (p4CheckBox.isSelected()) pAlgos.add("P41");
        cfg.setpAlgos(pAlgos);

        List<String> prAlgos = new ArrayList<>();
        if (pr1CheckBox.isSelected()) prAlgos.add("PR11");
        cfg.setPrAlgos(prAlgos);

        List<String> vAlgos = new ArrayList<>();
        if (v5CheckBox.isSelected()) vAlgos.add("V5");
        if (v6CheckBox.isSelected()) vAlgos.add("V6");
        cfg.setvAlgos(vAlgos);

        // V5
        if (v5CheckBox.isSelected()) {
            Integer v5KFrom = readSpinnerInt(v5KFromSpinner, "V5 k from");
            Integer v5KTo = readSpinnerInt(v5KToSpinner, "V5 k to");

            if (v5KFrom > v5KTo) {
                throw new IllegalArgumentException("V5: k from nie może być > k to");
            }

            cfg.setV5KFrom(v5KFrom);
            cfg.setV5KTo(v5KTo);
        } else {
            cfg.setV5KFrom(-1);
            cfg.setV5KTo(-1);
        }

        // V6
        if (v6CheckBox.isSelected()) {
            Integer v6KFrom = readSpinnerInt(v6KFromSpinner, "V6 k from");
            Integer v6KTo = readSpinnerInt(v6KToSpinner, "V6 k to");

            if (v6KFrom > v6KTo) {
                throw new IllegalArgumentException("V6: k from nie może być > k to");
            }

            cfg.setV6KFrom(v6KFrom);
            cfg.setV6KTo(v6KTo);
        } else {
            cfg.setV6KFrom(-1);
            cfg.setV6KTo(-1);
        }

        cfg.setSelectedMetrics(readSelectedFairnessMetrics());
        cfg.setDebugFlags(readDebugFlags());

        return cfg;
    }

    @FXML
    private void onStartClicked() throws IOException {
        // 1) seed
        long seed;
        if (seedCustomRadio.isSelected()) {
            String s = customSeedField.getText();
            seed = Long.parseLong(s.trim());
        } else {
            seed = System.currentTimeMillis();
        }

        // 2) parametry gry z GUI
        int K = kSpinner.getValue();
        int M = mSpinner.getValue();
        int N = nSpinner.getValue();
        int C = coinsSpinner.getValue();
        int qStep = qStepSpinner.getValue();
        int T = gamesSpinner.getValue();
        int delT = delTSpinner.getValue();
        int delSmallT = delSmallTSpinner.getValue();
        int singleGameCost = singleGameCostSpinner.getValue();
        int initialCredit = initialCreditSpinner.getValue();

        double cName = readDouble(crossoverNameField, "cross_prob (name)");
        double cFrom = readDouble(crossoverFromField, "cross_prob (from)");
        double cTo   = readDouble(crossoverToField,   "cross_prob (to)");
        double cStep = readDouble(crossoverStepField, "cross_prob (step)");

        double hName = readDouble(hMutNameField, "prob_H_mut (name)");
        double hFrom = readDouble(hMutFromField, "prob_H_mut (from)");
        double hTo   = readDouble(hMutToField,   "prob_H_mut (to)");
        double hStep = readDouble(hMutStepField, "prob_H_mut (step)");

        double pName= readDouble(pMutNameField, "prob_P_mut (name)");
        double pFrom = readDouble(pMutFromField, "prob_P_mut (from)");
        double pTo   = readDouble(pMutToField,   "prob_P_mut (to)");
        double pStep = readDouble(pMutStepField, "prob_P_mut (step)");

        double prName= readDouble(prMutNameField, "prob_PR_mut (name)");
        double prFrom = readDouble(prMutFromField, "prob_PR_mut (from)");
        double prTo   = readDouble(prMutToField,   "prob_PR_mut (to)");
        double prStep = readDouble(prMutStepField, "prob_PR_mut (step)");

        double vName = readDouble(vMutNameField, "prob_V_mut (name)");
        double vFrom = readDouble(vMutFromField, "prob_V_mut (from)");
        double vTo   = readDouble(vMutToField,   "prob_V_mut (to)");
        double vStep = readDouble(vMutStepField, "prob_V_mut (step)");

        String hStart = hStartChoice.getValue();
        hStart = "H1";
        if (hStart == null) throw new IllegalArgumentException("Nie wybrano start dla H algos.");
        System.out.println("****************   "+  hStart +"   ***************");
        int hContinuation = readSpinnerInt(hContinuationSpinner, "H continuation");

        int pStart = readSpinnerInt(pStartSpinner, "P start");
        int pContinuation = readSpinnerInt(pContinuationSpinner, "P continuation");

        int prStart = readSpinnerInt(prStartSpinner, "PR start");
        int prContinuation = readSpinnerInt(prContinuationSpinner, "PR continuation");

        int vStart = readSpinnerInt(vStartSpinner, "V start");
        int vContinuation = readSpinnerInt(vContinuationSpinner, "V continuation");

        double[] p = {
                readDouble(p1, "p1"),
                readDouble(p2, "p2"),
                readDouble(p3, "p3"),
                readDouble(p4, "p4"),
                readDouble(p5, "p5"),
                readDouble(p6, "p6"),
                readDouble(p7, "p7"),
                readDouble(p8, "p8"),
                readDouble(p9, "p9")
        };

        double[] del = {
                readDouble(del1, "del1"),
                readDouble(del2, "del2"),
                readDouble(del3, "del3"),
                readDouble(del4, "del4"),
                readDouble(del5, "del5"),
                readDouble(del6, "del6"),
                readDouble(del7, "del7"),
                readDouble(del8, "del8"),
                readDouble(del9, "del9")
        };

        double[] alpha = {
                readDouble(alpha1, "alpha1"),
                readDouble(alpha2, "alpha2"),
                readDouble(alpha3, "alpha3"),
                readDouble(alpha4, "alpha4"),
                readDouble(alpha5, "alpha5"),
                readDouble(alpha6, "alpha6"),
                readDouble(alpha7, "alpha7"),
                readDouble(alpha8, "alpha8"),
                readDouble(alpha9, "alpha9")
        };

        int[] t = {
                readInt(t1, "t1"),
                readInt(t2, "t2"),
                readInt(t3, "t3"),
                readInt(t4, "t4"),
                readInt(t5, "t5"),
                readInt(t6, "t6"),
                readInt(t7, "t7"),
                readInt(t8, "t8"),
                readInt(t9, "t9")
        };

        List<String> hAlgos = new ArrayList<>();
        if (h1CheckBox.isSelected()) hAlgos.add("H11");
        if (h3CheckBox.isSelected()) hAlgos.add("H3");
        if (h4CheckBox.isSelected()) hAlgos.add("H4");

        List<String> pAlgos = new ArrayList<>();
        if (p2CheckBox.isSelected()) pAlgos.add("P21");
        if (p4CheckBox.isSelected()) pAlgos.add("P41");

        List<String> prAlgos = new ArrayList<>();
        if (pr1CheckBox.isSelected()) prAlgos.add("PR11");

        List<String> vAlgos = new ArrayList<>();
        if (v5CheckBox.isSelected()) vAlgos.add("V5");
        if (v6CheckBox.isSelected()) vAlgos.add("V6");

        Integer v5KFrom = null;
        Integer v5KTo = null;
        if (v5CheckBox.isSelected()) {
            v5KFrom = readSpinnerInt(v5KFromSpinner, "V5 k from");
            v5KTo   = readSpinnerInt(v5KToSpinner,   "V5 k to");

            if (v5KFrom > v5KTo) {
                throw new IllegalArgumentException("V5: k from nie może być > k to");
            }
        }
        else{
            v5KFrom = -1;
            v5KTo   = -1;
        }

        Integer v6KFrom = null;
        Integer v6KTo = null;
        if (v6CheckBox.isSelected()) {
            v6KFrom = readSpinnerInt(v6KFromSpinner, "V6 k from");
            v6KTo   = readSpinnerInt(v6KToSpinner,   "V6 k to");

            if (v6KFrom > v6KTo) {
                throw new IllegalArgumentException("V6: k from nie może być > k to");
            }
        }
        else{
            v6KFrom = -1;
            v6KTo   = -1;
        }

        //DEBUG PRINT
        System.out.println("========== START CLICKED ==========");
        System.out.println("Seed              = " + seed);
        System.out.println("K (boats)         = " + K);
        System.out.println("M                 = " + M);
        System.out.println("N                 = " + N);
        System.out.println("C (coins)         = " + C);
        System.out.println("qStep             = " + qStep);
        System.out.println("T (games)         = " + T);
        System.out.println("del_T             = " + delT);
        System.out.println("del_t             = " + delSmallT);
        System.out.println("singleGameCost    = " + singleGameCost);
        System.out.println("initialCredit     = " + initialCredit);
        System.out.println("===================================");

        Map<String, Double> selectedMetrics = readSelectedFairnessMetrics();
        Map<String, Boolean> debugFlags = readDebugFlags();

        System.out.println("=== FAIRNESS METRICS (selected) ===");
        selectedMetrics.forEach((name, weight) ->
                System.out.println(name + " -> " + weight)
        );

        System.out.println("=== DEBUG FLAGS ===");
        debugFlags.forEach((name, enabled) ->
                System.out.println(name + " -> " + enabled)
        );
        System.out.println("\n================= FORM PARAMETERS =================");

        System.out.printf(
                "H mutation: from=%.3f  to=%.3f  step=%.3f%n",
                hFrom, hTo, hStep
        );

        System.out.printf(
                "P mutation: from=%.3f  to=%.3f  step=%.3f%n",
                pFrom, pTo, pStep
        );

        System.out.printf(
                "V mutation: from=%.3f  to=%.3f  step=%.3f%n",
                vFrom, vTo, vStep
        );

        System.out.println();
        System.out.println("H algos:");
        System.out.println("  start        = " + hStart);
        System.out.println("  continuation = " + hContinuation);

        System.out.println();
        System.out.println("P algos:");
        System.out.println("  start        = " + pStart);
        System.out.println("  continuation = " + pContinuation);

        System.out.println();
        System.out.println("PR projection algos:");
        System.out.println("  start        = " + prStart);
        System.out.println("  continuation = " + prContinuation);

        System.out.println();
        System.out.println("V algos:");
        System.out.println("  start        = " + vStart);
        System.out.println("  continuation = " + vContinuation);

        System.out.println("===================================================\n");

        System.out.println("\n=========== V5/V6 K RANGE ===========");
        System.out.println("V5 selected: " + v5CheckBox.isSelected()
                + " | kFrom=" + v5KFrom + " | kTo=" + v5KTo);
        System.out.println("V6 selected: " + v6CheckBox.isSelected()
                + " | kFrom=" + v6KFrom + " | kTo=" + v6KTo);
        System.out.println("=====================================\n");


        SimulationEngine engine = new SimulationEngine();
        engine.startSimulation(
                K, M, N, C, T, delT, delSmallT,

                hFrom, hTo, hStep,
                pFrom, pTo, pStep,
                vFrom, vTo, vStep,

                selectedMetrics,
                debugFlags,

                hContinuation,
                pStart, pContinuation,
                prStart, prContinuation,
                vStart, vContinuation,

                hAlgos, pAlgos, prAlgos, vAlgos,

                v5KFrom, v5KTo, v6KFrom, v6KTo
        );
    }



}

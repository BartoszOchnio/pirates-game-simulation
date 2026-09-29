package com.example.piratesgames.core;

import java.util.HashMap;
import java.util.Map;

public class StrategyMap {
    private Map<String, int[][]> p_pr_v_map;
    public StrategyMap(Map<String, int[][]> p_pr_v_map) {

        this.p_pr_v_map = p_pr_v_map;
    }

    public StrategyMap() {

        this.p_pr_v_map = new HashMap<>();
    }

    public Map<String, int[][]> getP_pr_v_map() {

        return p_pr_v_map;
    }
    public void setP_pr_v_map(Map<String, int[][]> p_pr_v_map) {

        this.p_pr_v_map = p_pr_v_map;
    }


}

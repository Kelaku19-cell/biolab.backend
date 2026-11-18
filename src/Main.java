package com.biolab.backend;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== BioLab Backend Initialized ===");

        // 1. Test Protein Translator
        ProteinTranslator translator = new ProteinTranslator();
        String sampleRna = "AUGUUUUUCAAAUAA";
        String proteinResult = translator.translateRna(sampleRna);
        System.out.println("Result: " + proteinResult);

        System.out.println("\n[Protein Translator]");
        System.out.println("RNA: " + sampleRna);
        // 2. Test Population Growth Simulator
        PopulationSimulator popSim = new PopulationSimulator();
        double[] popTimeline = popSim.simulateLogisticGrowth(100.0, 1000.0, 0.1, 5);
        System.out.println("\n[Population Growth Simulator]");
        System.out.println("Initial Population: 100 | Carrying Capacity: 1000");
        System.out.println("Population after 5 time steps: " + Math.round(popTimeline[5]));

        // 3. Test Punnett Square Calculator
        PunnettSquareCalculator punnett = new PunnettSquareCalculator();
        Map<String, Integer> crossResult = punnett.calculateMonohybrid('A', 'a', 'A', 'a');
        System.out.println("\n[Punnett Square Calculator]");
        System.out.println("Monohybrid Cross (Aa x Aa) Results: " + crossResult);

        // 4. Test Epidemic SIR Model
        EpidemicSirModel sirModel = new EpidemicSirModel();
        double[][] sirResults = sirModel.simulateSir(990, 10, 0, 0.3, 0.1, 5);
        System.out.println("\n[Epidemic SIR Model]");
        System.out.println("Day 5 - Susceptible: " + Math.round(sirResults[0][5]) +
                " | Infectious: " + Math.round(sirResults[1][5]) +
                " | Recovered: " + Math.round(sirResults[2][5]));
    }
}
package com.biolab.backend;

public class PopulationSimulator {

    public double[] simulateLogisticGrowth(double initialPop, double carryingCapacity, double growthRate, int timeSteps) {
        if (!Double.isFinite(initialPop) || initialPop < 0) {
            throw new IllegalArgumentException("Initial population must be finite and non-negative.");
        }
        if (!Double.isFinite(carryingCapacity) || carryingCapacity <= 0) {
            throw new IllegalArgumentException("Carrying capacity must be finite and greater than zero.");
        }
        if (!Double.isFinite(growthRate) || growthRate < 0) {
            throw new IllegalArgumentException("Growth rate must be finite and non-negative.");
        }
        if (timeSteps < 0) {
            throw new IllegalArgumentException("Time steps cannot be negative.");
        }

        double[] populationTimeline = new double[timeSteps + 1];
        populationTimeline[0] = initialPop;

        for (int t = 1; t <= timeSteps; t++) {
            double prevPop = populationTimeline[t - 1];
            // Logistic growth equation: P_t = P_{t-1} + r * P_{t-1} * (1 - P_{t-1} / K)
            double nextPop = prevPop + (growthRate * prevPop * (1.0 - (prevPop / carryingCapacity)));
            if (!Double.isFinite(nextPop)) {
                throw new ArithmeticException("Population simulation exceeded the numeric range.");
            }
            populationTimeline[t] = Math.max(0, nextPop); // Ensure population doesn't drop below 0
        }

        return populationTimeline;
    }
}
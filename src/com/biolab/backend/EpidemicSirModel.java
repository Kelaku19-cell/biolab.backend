package com.biolab.backend;

public class EpidemicSirModel {

    // Returns a 2D array: Row 0 -> Susceptible, Row 1 -> Infectious, Row 2 -> Recovered over time
    public double[][] simulateSir(double s0, double i0, double r0, double beta, double gamma, int days) {
        validateInputs(s0, i0, r0, beta, gamma, days);
        return simulate(s0, i0, r0, beta, gamma, days);
    }

    public double[][] simulateSirWithVaccination(
            double s0, double i0, double r0, double beta, double gamma, int days,
            double vaccinationCoverage) {
        validateInputs(s0, i0, r0, beta, gamma, days);
        if (!Double.isFinite(vaccinationCoverage) || vaccinationCoverage < 0 || vaccinationCoverage > 1) {
            throw new IllegalArgumentException("Vaccination coverage must be between 0 and 1.");
        }

        double vaccinated = s0 * vaccinationCoverage;
        return simulate(s0 - vaccinated, i0, r0 + vaccinated, beta, gamma, days);
    }

    private void validateInputs(double s0, double i0, double r0, double beta, double gamma, int days) {
        if (!Double.isFinite(s0) || s0 < 0 || !Double.isFinite(i0) || i0 < 0 ||
                !Double.isFinite(r0) || r0 < 0) {
            throw new IllegalArgumentException("Initial population groups must be finite and non-negative.");
        }
        if (!Double.isFinite(s0 + i0 + r0) || s0 + i0 + r0 <= 0) {
            throw new IllegalArgumentException("Total population must be finite and greater than zero.");
        }
        if (!Double.isFinite(beta) || beta < 0 || !Double.isFinite(gamma) || gamma < 0) {
            throw new IllegalArgumentException("Infection and recovery rates must be finite and non-negative.");
        }
        if (days < 0) {
            throw new IllegalArgumentException("Days cannot be negative.");
        }
    }

    private double[][] simulate(double s0, double i0, double r0, double beta, double gamma, int days) {
        double[][] results = new double[3][days + 1];
        results[0][0] = s0;
        results[1][0] = i0;
        results[2][0] = r0;

        double totalPopulation = s0 + i0 + r0;

        for (int t = 0; t < days; t++) {
            double S = results[0][t];
            double I = results[1][t];

            // Limit daily transitions so numerical steps cannot create negative groups.
            double newInfections = Math.min(S, (beta * S * I) / totalPopulation);
            double newRecoveries = Math.min(I, gamma * I);

            results[0][t + 1] = S - newInfections;
            results[1][t + 1] = I + newInfections - newRecoveries;
            results[2][t + 1] = results[2][t] + newRecoveries;
        }

        return results;
    }
}
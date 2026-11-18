package com.biolab.backend;

import java.util.LinkedHashMap;
import java.util.Map;

public class PunnettSquareCalculator {

    public Map<String, Integer> calculateMonohybrid(char p1Allele1, char p1Allele2, char p2Allele1, char p2Allele2) {
        validateAllele(p1Allele1);
        validateAllele(p1Allele2);
        validateAllele(p2Allele1);
        validateAllele(p2Allele2);
        validateSameGene(p1Allele1, p1Allele2);
        validateSameGene(p1Allele1, p2Allele1);
        validateSameGene(p1Allele1, p2Allele2);

        Map<String, Integer> genotypeCounts = new LinkedHashMap<>();
        char[] parent1 = {p1Allele1, p1Allele2};
        char[] parent2 = {p2Allele1, p2Allele2};

        for (char a1 : parent1) {
            for (char a2 : parent2) {
                String genotype = formatGenotype(a1, a2);
                genotypeCounts.put(genotype, genotypeCounts.getOrDefault(genotype, 0) + 1);
            }
        }

        return genotypeCounts;
    }

    private void validateAllele(char allele) {
        if (!((allele >= 'A' && allele <= 'Z') || (allele >= 'a' && allele <= 'z'))) {
            throw new IllegalArgumentException("Alleles must be alphabetic characters from A to Z.");
        }
    }

    private void validateSameGene(char first, char second) {
        if (Character.toLowerCase(first) != Character.toLowerCase(second)) {
            throw new IllegalArgumentException("All four alleles must represent the same gene.");
        }
    }

    private String formatGenotype(char c1, char c2) {
        // Standardize sorting so dominant alleles (capital letters) appear first
        if (Character.isLowerCase(c1) && Character.isUpperCase(c2)) {
            return "" + c2 + c1;
        }
        return "" + c1 + c2;
    }
}
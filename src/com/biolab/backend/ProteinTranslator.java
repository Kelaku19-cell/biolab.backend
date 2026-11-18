package com.biolab.backend;

import java.util.HashMap;
import java.util.Map;

public class ProteinTranslator {
    private static final Map<String, String> CODON_TABLE = new HashMap<>();

    static {
        addCodons("Phe", "UUU", "UUC");
        addCodons("Leu", "UUA", "UUG", "CUU", "CUC", "CUA", "CUG");
        addCodons("Ser", "UCU", "UCC", "UCA", "UCG", "AGU", "AGC");
        addCodons("Tyr", "UAU", "UAC");
        addCodons("Stop", "UAA", "UAG", "UGA");
        addCodons("Cys", "UGU", "UGC");
        addCodons("Trp", "UGG");
        addCodons("Pro", "CCU", "CCC", "CCA", "CCG");
        addCodons("His", "CAU", "CAC");
        addCodons("Gln", "CAA", "CAG");
        addCodons("Arg", "CGU", "CGC", "CGA", "CGG", "AGA", "AGG");
        addCodons("Ile", "AUU", "AUC", "AUA");
        addCodons("Met", "AUG");
        addCodons("Thr", "ACU", "ACC", "ACA", "ACG");
        addCodons("Asn", "AAU", "AAC");
        addCodons("Lys", "AAA", "AAG");
        addCodons("Val", "GUU", "GUC", "GUA", "GUG");
        addCodons("Ala", "GCU", "GCC", "GCA", "GCG");
        addCodons("Asp", "GAU", "GAC");
        addCodons("Glu", "GAA", "GAG");
        addCodons("Gly", "GGU", "GGC", "GGA", "GGG");
    }

    private static void addCodons(String aminoAcid, String... codons) {
        for (String codon : codons) {
            CODON_TABLE.put(codon, aminoAcid);
        }
    }

    public String translateRna(String rnaSequence) {
        if (rnaSequence == null) {
            throw new IllegalArgumentException("RNA sequence cannot be null.");
        }

        String cleanSequence = rnaSequence.toUpperCase().replaceAll("\\s+", "");
        if (cleanSequence.isEmpty()) {
            throw new IllegalArgumentException("RNA sequence cannot be empty.");
        }
        for (int i = 0; i < cleanSequence.length(); i++) {
            char base = cleanSequence.charAt(i);
            if (base != 'A' && base != 'C' && base != 'G' && base != 'U') {
                throw new IllegalArgumentException(
                        "Invalid RNA base '" + base + "' at position " + (i + 1) + ".");
            }
        }
        if (cleanSequence.length() % 3 != 0) {
            throw new IllegalArgumentException("RNA sequence length must be a multiple of 3.");
        }

        StringBuilder protein = new StringBuilder();
        for (int i = 0; i < cleanSequence.length(); i += 3) {
            String codon = cleanSequence.substring(i, i + 3);
            String aminoAcid = CODON_TABLE.get(codon);

            if (protein.length() > 0) {
                protein.append(' ');
            }
            protein.append(aminoAcid);
            if ("Stop".equals(aminoAcid)) {
                break;
            }
        }

        return protein.toString();
    }
}
package it.pasqualini.util;

import java.util.Random;

public class Normal {
    double average;

    public double getAverage() {
        return average;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    float standardDeviation;


    public Normal(float average, float standardDeviation) {
        this.average = average;
        this.standardDeviation = standardDeviation;
    }


    public double sample() {
        double r = new Random().nextGaussian();
        return r * standardDeviation + average;
    }

}

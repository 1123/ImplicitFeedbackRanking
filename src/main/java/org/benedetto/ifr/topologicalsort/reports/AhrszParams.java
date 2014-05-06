package org.benedetto.ifr.topologicalsort.reports;

public class AhrszParams {

    int items;
    float randomness;
    int iterations;
    int clicks;
    int pageSize;

    AhrszParams(int items, float randomness, int iterations, int clicks, int pageSize) {
        this.items = items;
        this.randomness = randomness;
        this.iterations = iterations;
        this.clicks = clicks;
        this.pageSize = pageSize;
    }
}

package org.benedetto.ifr.expedia;

import org.junit.Test;

/**
 * This class reads
 */
public class ClickStatisticsComputerTest {

    @Test
    public void testRead() throws Exception {
        ClickStatisticsComputer clickStatisticsComputer = new ClickStatisticsComputer();
        clickStatisticsComputer.read();
    }
}

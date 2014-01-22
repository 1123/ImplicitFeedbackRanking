package org.benedetto.ifr.expedia;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: linse
 * Date: 1/19/14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class ExpediaReaderTest {

    @Test
    public void testRead() throws Exception {
        ExpediaReader reader = new ExpediaReader();
        reader.read();
    }
}

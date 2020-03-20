package com.bartolomeo.SecondVariant.service;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.Assert.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HaversineModellTest {

    private HaversineModell distanceCalc;

    @BeforeAll
    public void beforeAll(){distanceCalc = new HaversineModell();}

    @Test
    public void calculateRoundTest() {
        assertEquals(8662, distanceCalc.calculateRound(40.730610, -73.935242, 0, 0));
    }
}

package com.kelseymckenna.yunit;

import org.junit.Test;

import static com.kelseymckenna.yunit.Yunit.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Kelsey McKenna on 17/10/16.
 */
public class YunitTest {
    private static final double DELTA = 1e-8;

    @Test
    public void valueDefaultsToFirstUnit() {
        assertEquals(1, MILLI.value(), DELTA);
        assertEquals(1, CENTI.value(), DELTA);
        assertEquals(1, INCH.value(), DELTA);
        assertEquals(1, FOOT.value(), DELTA);
        assertEquals(1, YARD.value(), DELTA);
        assertEquals(1, METRE.value(), DELTA);
    }

    @Test
    public void convertFromMilli() {
        assertEquals(1, MILLI.to(MILLI), DELTA);
        assertEquals(0.1, MILLI.to(CENTI), DELTA);
        assertEquals(0.03937007874, MILLI.to(INCH), DELTA);
        assertEquals(0.003280839895, MILLI.to(FOOT), DELTA);
        assertEquals(0.001093613298, MILLI.to(YARD), DELTA);
        assertEquals(0.001, MILLI.to(METRE), DELTA);
    }

    @Test
    public void convertFromCenti() {
        assertEquals(10, CENTI.to(MILLI), DELTA);
        assertEquals(1, CENTI.to(CENTI), DELTA);
        assertEquals(0.3937007874, CENTI.to(INCH), DELTA);
        assertEquals(0.03280839895, CENTI.to(FOOT), DELTA);
        assertEquals(0.01093613298, CENTI.to(YARD), DELTA);
        assertEquals(0.01, CENTI.to(METRE), DELTA);
    }

    @Test
    public void convertFromInch() {
        assertEquals(25.4, INCH.to(MILLI), DELTA);
        assertEquals(2.54, INCH.to(CENTI), DELTA);
        assertEquals(1, INCH.to(INCH), DELTA);
        assertEquals(0.083333333, INCH.to(FOOT), DELTA);
        assertEquals(0.027777778, INCH.to(YARD), DELTA);
        assertEquals(0.0254, INCH.to(METRE), DELTA);
    }

    @Test
    public void convertFromFoot() {
        assertEquals(304.8, FOOT.to(MILLI), DELTA);
        assertEquals(30.48, FOOT.to(CENTI), DELTA);
        assertEquals(12, FOOT.to(INCH), DELTA);
        assertEquals(1, FOOT.to(FOOT), DELTA);
        assertEquals(0.333333333, FOOT.to(YARD), DELTA);
        assertEquals(0.3048, FOOT.to(METRE), DELTA);
    }

    @Test
    public void convertFromYard() {
        assertEquals(914.4, YARD.to(MILLI), DELTA);
        assertEquals(91.44, YARD.to(CENTI), DELTA);
        assertEquals(36, YARD.to(INCH), DELTA);
        assertEquals(3, YARD.to(FOOT), DELTA);
        assertEquals(1, YARD.to(YARD), DELTA);
        assertEquals(0.9144, YARD.to(METRE), DELTA);
    }

    @Test
    public void convertFromMetre() {
        assertEquals(1000, METRE.to(MILLI), DELTA);
        assertEquals(100, METRE.to(CENTI), DELTA);
        assertEquals(39.37007874, METRE.to(INCH), DELTA);
        assertEquals(3.280839895, METRE.to(FOOT), DELTA);
        assertEquals(1.093613298, METRE.to(YARD), DELTA);
        assertEquals(1, METRE.to(METRE), DELTA);
    }

    @Test
    public void convertArea() {
        final UnitFacade m2 = new Yunit(1, 1, 2);
        final UnitFacade cm2 = new Yunit(0.01, 1, 2);

        assertEquals(100 * 100, m2.to(cm2), DELTA);
        assertEquals(1E-4, cm2.to(m2), DELTA);
    }

    @Test
    public void measureUsesRatioAndMagnitude() {
        assertEquals(2, measure(2, CENTI).value(), DELTA);
        assertEquals(402, measure(402, INCH).value(), DELTA);

        assertEquals(20, measure(20, MILLI).value(), DELTA);
        assertEquals(0.001, measure(20, MILLI).ratioToMetres(), DELTA);

        assertEquals(2, measure(2, CENTI).value(), DELTA);
        assertEquals(0.01, measure(2, CENTI).ratioToMetres(), DELTA);
    }

    @Test
    public void map() {
        assertEquals(3, METRE.map(i -> i + 2).value(), DELTA);
        assertEquals(6, new Yunit(1, 3).map(i -> i * 2).value(), DELTA);
    }

    @Test
    public void multiplyDefaultsToFirstUnit() {
        assertEquals(0.0001, new Yunit(0.01, 10).multiply(new Yunit(1, 10)).ratioToMetres(), DELTA);
        assertEquals(1, new Yunit(1, 123).multiply(new Yunit(1221, 5)).ratioToMetres(), DELTA);
    }

    @Test
    public void multiply2D() {
        assertEquals(60_000, new Yunit(0.01, 300).multiply(new Yunit(1, 2)).value(), DELTA); // 300 cm * 2 m = 300 cm * 200 cm = 60000 cm^2
        assertEquals(6, new Yunit(1, 2).multiply(new Yunit(0.01, 300)).value(), DELTA);
    }

    @Test
    public void multiply3D() {
        final UnitFacade oneM = new Yunit(1, 1);
        final UnitFacade twoCm = new Yunit(0.01, 2);
        final UnitFacade threeMm = new Yunit(0.001, 3);

        assertEquals(0.00006, oneM.multiply(twoCm).multiply(threeMm).value(), DELTA); // 1m * 2cm * 3mm = 1m * 0.02m * 0.003m = 0.00006m^3
        assertEquals(0.00006, oneM.multiply(twoCm.multiply(threeMm)).value(), DELTA);

        assertEquals(60_000, threeMm.multiply(twoCm).multiply(oneM).value(), DELTA);
        assertEquals(60_000, threeMm.multiply(twoCm.multiply(oneM)).value(), DELTA);
    }

    @Test
    public void multiply4D() {
        final UnitFacade area1m = new Yunit(1, 4, 2);
        final UnitFacade area2m = new Yunit(1, 9, 2);

        assertEquals(36, area1m.multiply(area2m).value(), DELTA);
        assertEquals(36, new Yunit(1, 2, 1).multiply(new Yunit(1, 18, 3)).value(), DELTA);
    }

    @Test
    public void areaTest() {
        assertEquals(1, area(4, METRE).ratioToMetres(), DELTA);
        assertEquals(4, area(4, METRE).value(), DELTA);
    }

    @Test
    public void integration() {
        assertEquals(1000, METRE.to(MILLI), DELTA);
        assertEquals(2, measure(20, MILLI).to(CENTI), DELTA);
        assertEquals(5, measure(3, METRE).map(i -> i + 2).value(), DELTA);
        assertEquals(6, measure(300, CENTI).multiply(measure(2, METRE)).to(METRE), DELTA);
        assertEquals(1, area(10_000, CENTI).to(METRE), DELTA);
        assertEquals(40_000, measure(2, METRE).square().to(CENTI), DELTA);
        assertEquals(1_000_000, METRE.lift(3).to(CENTI), DELTA);
    }
}

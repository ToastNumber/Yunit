package com.kelseymckenna.yunit;

import java.util.function.Function;

/**
 * Created by Kelsey McKenna on 18/10/16.
 */
public class Yunit implements UnitFacade {
    public static Yunit MILLI = new Yunit(1 / 1000.);
    public static Yunit CENTI = new Yunit(1 / 100.);
    public static Yunit INCH = new Yunit(0.0254);
    public static Yunit FOOT = new Yunit(0.3048);
    public static Yunit YARD = new Yunit(0.9144);
    public static Yunit METRE = new Yunit(1);

    private final double ratioToMetres;
    private final double value;
    private final int dimension;

    public Yunit(final double ratioToMetres) {
        this.ratioToMetres = ratioToMetres;
        this.value = 1;
        this.dimension = 1;
    }

    public Yunit(final double ratioToMetres, final double value) {
        this.ratioToMetres = ratioToMetres;
        this.value = value;
        this.dimension = 1;
    }

    public Yunit(final double ratioToMetres, final double value, final int dimension) {
        this.ratioToMetres = ratioToMetres;
        this.value = value;
        this.dimension = dimension;
    }

    @Override public double value() {
        return value;
    }

    @Override public double ratioToMetres() {
        return Math.pow(ratioToMetres, dimension);
    }

    @Override public int dimension() {
        return dimension;
    }

    @Override public double to(final UnitFacade target) {
        final UnitFacade targetMatchDimension = target.lift(this.dimension);
        return this.value * (ratioToMetres() / targetMatchDimension.ratioToMetres()) * targetMatchDimension.value();
    }

    @Override public UnitFacade map(final Function<Double, Double> f) {
        return new Yunit(ratioToMetres, f.apply(value), dimension);
    }

    @Override public UnitFacade multiply(final UnitFacade other) {
        return new Yunit(
                ratioToMetres,
                this.value * other.to(new Yunit(ratioToMetres, 1, other.dimension())),
                dimension + 1);
    }

    @Override public UnitFacade square() {
        if (this.dimension != 1) {
            throw new IllegalArgumentException(String.format("Cannot square a %d-dimensional unit", this.dimension));
        }
        return this.multiply(this);
    }

    @Override public UnitFacade lift() {
        return lift(dimension + 1);
    }

    @Override public UnitFacade lift(int dimension) {
        return new Yunit(ratioToMetres, value, dimension);
    }

    public static UnitFacade measure(final double magnitude, final UnitFacade unit) {
        return new Yunit(unit.ratioToMetres(), magnitude);
    }

    public static UnitFacade area(final double magnitude, final UnitFacade unit) {
        return unit.map(i -> magnitude).lift(2);
    }
}

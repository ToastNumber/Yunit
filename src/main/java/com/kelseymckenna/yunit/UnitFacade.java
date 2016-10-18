package com.kelseymckenna.yunit;

import java.util.function.Function;

/**
 * Created by Kelsey McKenna on 18/10/16.
 */
public interface UnitFacade {
    /**
     * @return the magnitude, e.g. for an instance representing 5 centimetres, <code>value()</code> would return <code>5</code>
     */
    double value();

    /**
     * @return the ratio of this unit to metres, for example <code>CENTI.ratioToMetres()</code> would return 0.01
     */
    double ratioToMetres();

    int dimension();

    /**
     * @param target the target unit
     * @return the value of the current instance after being converted to the unit represented by <code>target</code>.
     * <b>Node</b>: the dimension of the target unit is ignored
     */
    double to(UnitFacade target);

    /**
     * @param f the function to apply to the magnitude
     * @return an instance with the value modified by the supplied function
     */
    UnitFacade map(Function<Double, Double> f);

    /**
     * @param other the other unit to multiply by
     * @return an instance representing the area found by multiplying <code>this</code> with <code>other</code>
     */
    UnitFacade multiply(UnitFacade other);

    /**
     * Multiples a 1-dimension unit by itself
     * @return a unit representing the square of the 1-dimension unit
     */
    UnitFacade square();

    /**
     * @return an instance with the same properties, but in the next dimension
     */
    UnitFacade lift();

    /**
     * @param dimension the target dimension for this unit
     * @return an instance representing the unit in the specified dimension.
     * For example, METRE.lift(3) represents the unit "metres cubed".
     */
    UnitFacade lift(int dimension);
}

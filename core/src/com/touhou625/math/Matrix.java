package com.touhou625.math;

import static java.lang.Math.*;

public class Matrix {

    private double angle;
    private final double[] values = new double[4];

    public Matrix() {

    }

    public void initalizeMatrix(double angle) {
        double angleInRadian = angle * PI / 180f;
        values[0] = cos(angleInRadian);
        values[1] = -sin(angleInRadian);
        values[2] = sin(angleInRadian);
        values[3] = cos(angleInRadian);
    }

    public double[] rotation(double[] direction) {
        direction[0] = direction[0] * values[0] + direction[1] * values[1];
        direction[1] = direction[0] * values[2] + direction[1] * values[3];

        return direction;
    }
}

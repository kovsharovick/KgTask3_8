package com.cgvsu.math;

public class Matrix4x4 {

    private final double[][] values;

    public Matrix4x4(double[][] values) {
        if (values.length != 4 || values[0].length != 4) {
            throw new IllegalArgumentException("Matrix size must be 4x4.");
        }
        this.values = values;
    }

    public static Matrix4x4 identity() {
        return new Matrix4x4(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
    }

    public void multiply(Vector3f v) {
        v.x = (float) (values[0][0] * v.x + values[0][1] * v.y + values[0][2] * v.z + values[0][3]);
        v.y = (float) (values[1][0] * v.x + values[1][1] * v.y + values[1][2] * v.z + values[1][3]);
        v.z = (float) (values[2][0] * v.x + values[2][1] * v.y + values[2][2] * v.z + values[2][3]);
    }


    public Vector4f multiply(Vector4f v) {
        return new Vector4f(
                (float) (values[0][0] * v.x + values[0][1] * v.y + values[0][2] * v.z + values[0][3] * v.w),
                (float) (values[1][0] * v.x + values[1][1] * v.y + values[1][2] * v.z + values[1][3] * v.w),
                (float) (values[2][0] * v.x + values[2][1] * v.y + values[2][2] * v.z + values[2][3] * v.w),
                (float) (values[3][0] * v.x + values[3][1] * v.y + values[3][2] * v.z + values[3][3] * v.w)
        );
    }

    public Matrix4x4 multiply(Matrix4x4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.values[i][0] * other.values[0][j] +
                        this.values[i][1] * other.values[1][j] +
                        this.values[i][2] * other.values[2][j] +
                        this.values[i][3] * other.values[3][j];
            }
        }
        return new Matrix4x4(result);
    }


}

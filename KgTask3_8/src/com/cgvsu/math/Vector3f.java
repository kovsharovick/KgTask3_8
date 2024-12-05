package com.cgvsu.math;

public class Vector3f {

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void vector3fto4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public boolean equals(Vector3f other) {
        final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }

    public float x, y, z;
}

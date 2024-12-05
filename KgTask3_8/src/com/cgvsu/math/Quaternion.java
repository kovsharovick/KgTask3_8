package com.cgvsu.math;

public class Quaternion {

    public double w, x, y, z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Quaternion fromEuler(double radX, double radY, double radZ) {
        double cy = Math.cos(radZ * 0.5);
        double sy = Math.sin(radZ * 0.5);
        double cr = Math.cos(radX * 0.5);
        double sr = Math.sin(radX * 0.5);
        double cp = Math.cos(radY * 0.5);
        double sp = Math.sin(radY * 0.5);

        Quaternion q = new Quaternion(0, 0, 0, 0);
        q.w = cr * cp * cy + sr * sp * sy;
        q.x = sr * cp * cy - cr * sp * sy;
        q.y = cr * sp * cy + sr * cp * sy;
        q.z = cr * cp * sy - sr * sp * cy;

        return q;
    }

    public Matrix4x4 toRotationMatrix() {
        return new Matrix4x4(new double[][]{
                {1 - 2 * (y * y + z * z), 2 * (x * y - z * w), 2 * (x * z + y * w), 0},
                {2 * (x * y + z * w), 1 - 2 * (x * x + z * z), 2 * (y * z - x * w), 0},
                {2 * (x * z - y * w), 2 * (y * z + x * w), 1 - 2 * (x * x + y * y), 0},
                {0, 0, 0, 1}
        });
    }

    public static Quaternion multiplyQuaternions(Quaternion q1, Quaternion q2) {
        double w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
        double x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y;
        double y = q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x;
        double z = q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w;
        return new Quaternion(w, x, y, z);
    }
}


package com.cgvsu.afinka;

import com.cgvsu.math.Matrix4x4;
import com.cgvsu.math.Quaternion;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import com.cgvsu.model.Model;

import static com.cgvsu.math.Quaternion.multiplyQuaternions;

public class AffineTransform {

    public static void affineTransform(Model model, int sX, int sY, int sZ,
                                       int rX, int rY, int rZ, String order,
                                       int tX, int tY, int tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, order);
        Matrix4x4 S = scale(sX, sY, sZ);
        Matrix4x4 affineTransform = S.multiply(R).multiply(T);
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = affineTransform.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = affineTransform.multiply(v4);
            v.vector3fto4f(res);
        }
    }

    public static void affineTransform(Model model, int sX, int sY, int sZ,
                                       int rX, int rY, int rZ,
                                       int tX, int tY, int tZ) {
        Matrix4x4 T = translation(tX, tY, tZ);
        Matrix4x4 R = rotate(rX, rY, rZ, "xyz");
        Matrix4x4 S = scale(sX, sY, sZ);
        Matrix4x4 affineTransform = S.multiply(R).multiply(T);
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = affineTransform.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = affineTransform.multiply(v4);
            v.vector3fto4f(res);
        }
    }


    public static void scale(Model model, double sX, double sY, double sZ) {
        Matrix4x4 scale = new Matrix4x4(new double[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = scale.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = scale.multiply(v4);
            v.vector3fto4f(res);
        }
    }

    public static void rotate(Model model, int rX, int rY, int rZ, String order) {
        double radX = Math.toRadians(rX);
        double radY = Math.toRadians(rY);
        double radZ = Math.toRadians(rZ);
        Quaternion qX = Quaternion.fromEuler(radX, 0, 0);
        Quaternion qY = Quaternion.fromEuler(0, radY, 0);
        Quaternion qZ = Quaternion.fromEuler(0, 0, radZ);
        Quaternion q = new Quaternion(1, 0, 0, 0);

        for (char ch : order.toCharArray()) {
            q = switch (ch) {
                case 'x' -> multiplyQuaternions(q, qX);
                case 'y' -> multiplyQuaternions(q, qY);
                case 'z' -> multiplyQuaternions(q, qZ);
                default -> q;
            };
        }
        Matrix4x4 rotate = q.toRotationMatrix();
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = rotate.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = rotate.multiply(v4);
            v.vector3fto4f(res);
        }
    }


    public static void rotateEuler(Model model, int rX, int rY, int rZ, String order) {
        double radX = Math.toRadians(rX);
        double radY = Math.toRadians(rY);
        double radZ = Math.toRadians(rZ);
        Matrix4x4 rotateX = new Matrix4x4(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(radX), Math.sin(radX), 0},
                {0, -Math.sin(radX), Math.cos(radX), 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotateY = new Matrix4x4(new double[][]{
                {Math.cos(radY), 0, -Math.sin(radY), 0},
                {0, 1, 0, 0},
                {Math.sin(radY), 0, Math.cos(radY), 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotateZ = new Matrix4x4(new double[][]{
                {Math.cos(radZ), Math.sin(radZ), 0, 0},
                {-Math.sin(radZ), Math.cos(radZ), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });
        Matrix4x4 rotate;
        switch (order) {
            case "zyx" -> rotate = rotateX.multiply(rotateY).multiply(rotateZ);
            case "yzx" -> rotate = rotateX.multiply(rotateZ).multiply(rotateY);
            case "zxy" -> rotate = rotateY.multiply(rotateX).multiply(rotateZ);
            case "xzy" -> rotate = rotateY.multiply(rotateZ).multiply(rotateX);
            case "yxz" -> rotate = rotateZ.multiply(rotateX).multiply(rotateY);
            case "xyz" -> rotate = rotateZ.multiply(rotateY).multiply(rotateX);
            default -> throw new IllegalStateException("You should input order such as 'xyz', 'yzx' or 'zxy'...");
        }
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = rotate.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = rotate.multiply(v4);
            v.vector3fto4f(res);
        }
    }

    public static void translation(Model model, int tX, int tY, int tZ) {
        Matrix4x4 translation = new Matrix4x4(new double[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
        Vector4f v4;
        for (Vector3f v : model.vertices) {
            v4 = new Vector4f(v.x, v.y, v.z, 1);
            Vector4f res = translation.multiply(v4);
            v.vector3fto4f(res);
        }
        for (Vector3f v : model.normals) {
            v4 = new Vector4f(v.x, v.y, v.z, 0);
            Vector4f res = translation.multiply(v4);
            v.vector3fto4f(res);
        }
    }

    private static Matrix4x4 translation(int tX, int tY, int tZ) {
        return new Matrix4x4(new double[][]{
                {1, 0, 0, tX},
                {0, 1, 0, tY},
                {0, 0, 1, tZ},
                {0, 0, 0, 1}
        });
    }

    private static Matrix4x4 rotate(int rX, int rY, int rZ, String order) {
        double radX = Math.toRadians(rX);
        double radY = Math.toRadians(rY);
        double radZ = Math.toRadians(rZ);
        Quaternion qX = Quaternion.fromEuler(radX, 0, 0);
        Quaternion qY = Quaternion.fromEuler(0, radY, 0);
        Quaternion qZ = Quaternion.fromEuler(0, 0, radZ);
        Quaternion q = new Quaternion(1, 0, 0, 0);

        for (char ch : order.toCharArray()) {
            q = switch (ch) {
                case 'x' -> multiplyQuaternions(q, qX);
                case 'y' -> multiplyQuaternions(q, qY);
                case 'z' -> multiplyQuaternions(q, qZ);
                default -> q;
            };
        }
        return q.toRotationMatrix();
    }

    private static Matrix4x4 scale(int sX, int sY, int sZ) {
        return new Matrix4x4(new double[][]{
                {sX, 0, 0, 0},
                {0, sY, 0, 0},
                {0, 0, sZ, 0},
                {0, 0, 0, 1}
        });
    }
}

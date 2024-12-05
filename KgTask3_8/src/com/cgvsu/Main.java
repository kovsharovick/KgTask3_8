package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.cgvsu.afinka.AffineTransform.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("3DModels/Faceform/WrapHand.obj");
        String fileContent = Files.readString(fileName);

        System.out.println("Loading model ...");
        Model model = ObjReader.read(fileContent);

        System.out.println("Vertices: " + model.vertices.size());
        System.out.println("Texture vertices: " + model.textureVertices.size());
        System.out.println("Normals: " + model.normals.size());
        System.out.println("Polygons: " + model.polygons.size());

        ObjWriter.write(model, new BufferedWriter(new FileWriter("1.obj")));
        affineTransform(model, 1, 1, 2, 90, 180, 30, "xyz", 10, 100, 100);
        //translation(model, 10, 100,100);
        //rotate(model, 90,180, 30, "xyz");
        //scale(model, 1, 1, 2);

        ObjWriter.write(model, new BufferedWriter(new FileWriter("3.obj")));

    }
}

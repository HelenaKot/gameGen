package com.fancytank.gamegen.data;

import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class DataManager {
    private static String saveDir = "saved";
    private static File directory;

    public static List<String> getFileNames(String absolutePath) {
        LinkedList<String> names = new LinkedList<>();
        try {
            for (String filename : getDirectory(absolutePath).list())
                names.add(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public static ProgrammingBlockSavedInstance[] loadBlocks(String absolutePath, String projectName) {
        try {
            return loadFile(absolutePath, projectName).blocks;
        } catch (Exception e) {
            e.printStackTrace();
            return new ProgrammingBlockSavedInstance[0];
        }
    }

    public static void saveBlocks(String absolutePath, String projectName, ProgrammingBlockSavedInstance[] workspace) {
        try {
            saveFile(absolutePath, projectName, new SaveInstance(projectName, workspace));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SaveInstance loadFile(String absolutePath, String projectName) throws IOException, ClassNotFoundException {
        File file = new File(getDirectory(absolutePath).getAbsolutePath(), projectName);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object output = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return (SaveInstance) output;
        }
        return null;
    }

    private static void saveFile(String absolutePath, String projectName, SaveInstance save) throws IOException {
        File file = new File(getDirectory(absolutePath).getAbsolutePath(), projectName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(save);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
    }

    private static File getDirectory(String absolutePath) throws IOException {
        directory = new File(absolutePath, saveDir);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.createNewFile();
        }
        return directory;
    }

}

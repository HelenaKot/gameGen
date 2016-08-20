package com.fancytank.gamegen.data;

import com.fancytank.gamegen.SaveInstance;
import com.fancytank.gamegen.SaveListAdapter;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.CustomActorToInit;
import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class DataManager {
    private static String saveDir = "saved";
    private static File directory;

    public static File[] getFiles(String patch) {
        try {
            return getDirectory(patch).listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File[0];
    }

    public static ProgrammingBlockSavedInstance[] loadWorkspace(String absolutePath, String projectName) {
        try {
            SaveInstance save = loadFile(absolutePath, projectName);
            loadActors(save);
            BoardManager.setInstance(save.boards);
            return save.blocks;
        } catch (Exception e) {
            e.printStackTrace();
            return new ProgrammingBlockSavedInstance[0];
        }
    }

    private static void loadActors(SaveInstance save) {
        for (TileType tile : save.tiles)
            ActorInitializer.addActorClass(new CustomActorToInit(tile));
    }

    public static void saveBlocks(String absolutePath, String projectName, ProgrammingBlockSavedInstance[] workspace) {
        try {
            saveFile(absolutePath, projectName, new SaveInstance(projectName, workspace, fetchActors(), BoardManager.getBoards()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static TileType[] fetchActors() {
        LinkedList<TileType> list = ActorInitializer.getCustomActors();
        return list.toArray(new TileType[list.size()]);
    }

    public static void deleteProject(String absolutePath, String projectName) {
        try {
            File file = new File(getDirectory(absolutePath).getAbsolutePath(), projectName);
            if (file.exists())
                file.delete();
            notifyListAdapter();
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

    private static void notifyListAdapter() {
        if (SaveListAdapter.instance != null) {
            SaveListAdapter.instance.notifyDataSetChanged();
        }
    }

}

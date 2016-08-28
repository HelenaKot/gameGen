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
import java.util.HashMap;
import java.util.LinkedList;

public class DataManager {
    private static String saveDir = "saved";
    private String projectName, absolutePath;
    private SaveInstance saveInstance;

    public DataManager(String projectName, String absolutePath) {
        this.projectName = projectName;
        this.absolutePath = absolutePath;
    }

    public SaveInstance loadWorkspace() {
        if (saveInstance == null)
            try {
                saveInstance = loadFile();
                loadActors();
                BoardManager.setInstance(saveInstance.boards);
            } catch (Exception e) {
                saveInstance = new SaveInstance(projectName, new ProgrammingBlockSavedInstance[0], null, new HashMap<>());
            }
        return saveInstance;
    }

    public void saveWorkspace(ProgrammingBlockSavedInstance[] workspace) {
        try {
            saveFile(new SaveInstance(projectName, workspace, fetchActors(), BoardManager.getBoards()));
            saveInstance = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadActors() {
        for (TileType tile : saveInstance.tiles)
            ActorInitializer.addActorClass(new CustomActorToInit(tile));
    }

    private TileType[] fetchActors() {
        LinkedList<TileType> list = ActorInitializer.getCustomActors();
        return list.toArray(new TileType[list.size()]);
    }

    private SaveInstance loadFile() throws IOException, ClassNotFoundException {
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

    private void saveFile(SaveInstance save) throws IOException {
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

    public static void deleteProject(String absolutePath, String projectName) {
        try {
            File file = new File(getDirectory(absolutePath).getAbsolutePath(), projectName);
            if (file.exists())
                file.delete();
            //  notifyListAdapter(); TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File[] getFiles(String patch) {
        try {
            return getDirectory(patch).listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File[0];
    }

    private static File getDirectory(String absolutePath) throws IOException {
        File directory = new File(absolutePath, saveDir);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.createNewFile();
        }
        return directory;
    }

    private void notifyListAdapter() {
        if (SaveListAdapter.instance != null) {
            SaveListAdapter.instance.notifyDataSetChanged();
        }
    }

}

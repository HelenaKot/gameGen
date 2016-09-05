package com.fancytank.gamegen.data;

import android.content.Context;

import com.fancytank.gamegen.SaveInstance;
import com.fancytank.gamegen.SaveListAdapter;
import com.fancytank.gamegen.game.actor.ActorInitializer;
import com.fancytank.gamegen.game.actor.CustomActorToInit;
import com.fancytank.gamegen.game.actor.TileType;
import com.fancytank.gamegen.game.map.BoardManager;
import com.fancytank.gamegen.programming.blocks.BlockManager;
import com.fancytank.gamegen.programming.data.ProgrammingBlockSavedInstance;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class DataManager {
    private static String saveDir = "saved", jsonDir = "export", absolutePath;
    private String projectName;
    private SaveInstance saveInstance;

    public DataManager(String projectName, Context context) {
        this.projectName = projectName;
        if (absolutePath == null)
            this.absolutePath = context.getExternalFilesDir(null).toString();
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

    public void saveWorkspace() {
        try {
            if (BlockManager.getWorkspaceItemsToSave().length > 0) // todo quickfix
                saveFile(new SaveInstance(projectName, BlockManager.getWorkspaceItemsToSave(), ActorInitializer.getCustomActors(), BoardManager.getBoards()));
            saveInstance = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadActors() {
        for (TileType tile : saveInstance.tiles)
            ActorInitializer.addActorClass(new CustomActorToInit(tile));
    }

    private SaveInstance loadFile() throws IOException, ClassNotFoundException {
        File file = new File(getDirectory(absolutePath, saveDir).getAbsolutePath(), projectName);
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
        File file = new File(getDirectory(absolutePath, saveDir).getAbsolutePath(), projectName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(save);
        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();
        saveFileForExport(save);
    }

    private void saveFileForExport(SaveInstance save) throws IOException {
        Gson gson = new Gson();
        String serialized = gson.toJson(save.convertForJsonExport());
        File file = new File(getDirectory(absolutePath, jsonDir).getAbsolutePath(), projectName + "_json");
        FileOutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        outputStream.write(serialized.getBytes());
        outputStream.close();
    }

    public static void deleteProject(String projectName) {
        try {
            File file = new File(getDirectory(absolutePath, saveDir).getAbsolutePath(), projectName);
            file.delete();
            SaveListAdapter.instance.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File[] getFiles(Context context) {
        try {
            return getDirectory(absolutePath = context.getExternalFilesDir(null).toString(), saveDir).listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File[0];
    }

    private static File getDirectory(String absolutePath, String dir) throws IOException {
        File directory = new File(absolutePath, dir);
        if (!directory.exists()) {
            directory.mkdirs();
            directory.createNewFile();
        }
        return directory;
    }

}

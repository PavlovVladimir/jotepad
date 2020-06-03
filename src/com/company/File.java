package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;

class File {
    public String loadFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                return new String(bytes);
            } catch (IOException e) {
                return "";
            }
        }
        return "";
    }

    public void saveFile(String content) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = jfc.getSelectedFile();
            try (
                    final BufferedWriter writer = Files.newBufferedWriter(selectedFile.toPath());
            ) {
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                System.out.println("Cant save file!!!" + e);
            }
        }
    }
}

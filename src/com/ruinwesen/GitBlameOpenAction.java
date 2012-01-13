package com.ruinwesen;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.File;
import java.io.IOException;

/**
 * User: Manuel Odendahl <wesen@ruinwesen.com>
 * Date: 13/1/12
 */
public class GitBlameOpenAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        if (editor == null ||
            file == null) {
            return;
        }

        Document document = editor.getDocument();
        int offset = editor.getCaretModel().getOffset();
        int line = document.getLineNumber(offset);

        String path = file.getPath();
        File _file = new File(path);

        try {
            String cmd = "/usr/local/bin/git-hub-blame " + _file.getAbsolutePath() + " " + line;
            File parentDir = new File(file.getParent().getPath());
            Process p = Runtime.getRuntime().exec(cmd, new String[] { }, parentDir);
            p.waitFor();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}

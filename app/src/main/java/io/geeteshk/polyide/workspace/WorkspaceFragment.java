package io.geeteshk.polyide.workspace;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.geeteshk.polyide.R;
import io.geeteshk.polyide.util.PolyLog;

public class WorkspaceFragment extends Fragment {

    String mProject, mFilename;

    public WorkspaceFragment() {
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workspace, container, false);

        final Workspace workspace = (Workspace) rootView.findViewById(R.id.file_content);

        if (mFilename.endsWith(".html")) {
            workspace.setType(Workspace.CodeType.HTML);
        } else if (mFilename.endsWith(".css")) {
            workspace.setType(Workspace.CodeType.CSS);
        } else if (mFilename.endsWith(".js")) {
            workspace.setType(Workspace.CodeType.JS);
        }

        workspace.setTextHighlighted(getContents());
        workspace.mOnTextChangedListener = new Workspace.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                try {
                    writeContents(workspace.getText().toString());
                } catch (IOException e) {
                    PolyLog.log(e.getMessage(), 'e');
                }
            }
        };

        return rootView;
    }

    private String getContents() {
        try {
            InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory() + "/Polyide/" + mProject + "/" + mFilename);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append('\n');
            }

            return builder.toString();
        } catch (Exception e) {
            PolyLog.log(e.getMessage(), 'e');
        }

        return null;
    }

    private void writeContents(String contents) throws IOException {
        FileOutputStream stream = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/Polyide/" + mProject + "/" + mFilename));
        try {
            stream.write(contents.getBytes());
        } finally {
            stream.close();
        }
    }

    public void setProject(String project) {
        this.mProject = project;
    }

    public void setFilename(String filename) {
        this.mFilename = filename;
    }
}

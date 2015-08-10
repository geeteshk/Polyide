package io.geeteshk.polyide;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectHandler {

    public static void init() {
        if (!new File(Environment.getExternalStorageDirectory() + File.separator + "Polyide").exists()) {
            new File(Environment.getExternalStorageDirectory() + File.separator + "Polyide").mkdir();
        }
    }

    public static Project[] getProjects() {
        List<Project> projects = new ArrayList<>();
        String[] files = new File(Environment.getExternalStorageDirectory() + File.separator + "Polyide").list();

        if (files != null) {
            for (String file : files) {
                projects.add(new Project(file, file /* TODO: Replace this with the project description */));
            }
        }

        if (projects.isEmpty()) {
            return new Project[]{new Project("Welcome to Polyide!", "Looks like you don't have any projects yet. Why don't you create one by pressing the button in the bottom right corner?")};
        }

        Project[] projectArray = new Project[projects.size()];
        return projects.toArray(projectArray);
    }
}

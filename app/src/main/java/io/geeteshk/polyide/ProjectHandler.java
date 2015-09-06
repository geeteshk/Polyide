package io.geeteshk.polyide;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectHandler {

    private static final String POLYIDE_DIR = Environment.getExternalStorageDirectory() + File.separator + "Polyide";

    private static final String INDEX_HTML = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>@name</title>\n" +
            "    <meta name=\"author\" content=\"@author\">\n" +
            "    <meta name=\"description\" content=\"@description\">\n" +
            "    <meta name=\"keywords\" content=\"@keywords\">\n" +
            "    <link rel=\"shortcut icon\" href=\"images/favicon.ico\" type=\"image/vnd.microsoft.icon\">\n" +
            "    <link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\">\n" +
            "    <script src=\"main.js\" type=\"text/javascript\"></script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <h1>Hello World!</h1>\n" +
            "  </body>\n" +
            "</html>";

    private static final String STYLE_CSS = "/* Add all your styles here */";

    private static final String MAIN_JS = "// Add all your JS here";

    public static void init() {
        if (!new File(Environment.getExternalStorageDirectory() + File.separator + "Polyide").exists()) {
            new File(Environment.getExternalStorageDirectory() + File.separator + "Polyide").mkdir();
        }
    }

    public static Project[] getProjects() {
        List<Project> projects = new ArrayList<>();
        String[] files = new File(POLYIDE_DIR).list();
        List<File> projectFiles = new ArrayList<>();

        if (files != null) {
            for (String file : files) {
                projectFiles.add(new File(POLYIDE_DIR + File.separator + file + File.separator + file + ".poly"));
            }
        }

        if (files != null) {
            for (String file : files) {
                String description = "";
                try {
                    FileInputStream fileInputStream = new FileInputStream(projectFiles.get(Arrays.asList(files).indexOf(file)));
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while ((ch = fileInputStream.read()) != -1) {
                        builder.append((char) ch);
                    }

                    JSONObject projectJson = new JSONObject(builder.toString());
                    description = projectJson.getString("description");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                projects.add(new Project(file, description));
            }
        }

        if (projects.isEmpty()) {
            return new Project[]{new Project("Welcome to Polyide!", "Looks like you don't have any projects yet. Why don't you create one by pressing the button in the bottom right corner?")};
        }

        Project[] projectArray = new Project[projects.size()];
        return projects.toArray(projectArray);
    }

    public static int createProject(Project project) {
        File projectDir = new File(POLYIDE_DIR + File.separator + project.getTitle());
        File projectFile = new File(projectDir.getPath() + File.separator + project.getTitle() + ".poly");
        File indexHtml = new File(projectDir.getPath() + File.separator + "index.html");
        File styleCss = new File(projectDir.getPath() + File.separator + "style.css");
        File mainJs = new File(projectDir.getPath() + File.separator + "main.js");
        JSONObject projectJson = new JSONObject();

        if (!projectDir.exists()) {
            projectDir.mkdir();
        } else {
            return 1;
        }

        try {
            projectJson.put("title", project.getTitle());
            projectJson.put("description", project.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(projectFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert fileOutputStream != null;
        try {
            fileOutputStream.write(projectJson.toString(4).getBytes());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream = new FileOutputStream(indexHtml);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.write(INDEX_HTML.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream = new FileOutputStream(styleCss);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.write(STYLE_CSS.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream = new FileOutputStream(mainJs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.write(MAIN_JS.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}

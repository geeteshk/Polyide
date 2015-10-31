package io.geeteshk.polyide.project;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
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

import io.geeteshk.polyide.SetupActivity;
import io.geeteshk.polyide.element.ElementsHolder;
import io.geeteshk.polyide.util.GetComponentsTask;

public class ProjectHandler {

    private static final String POLYIDE_DIR = Environment.getExternalStorageDirectory() + File.separator + "Polyide";

    private static final String INDEX_HTML = "<!doctype html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <title>@name</title>\n" +
            "\n" +
            "    <meta name=\"description\" content=\"@description\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\">\n" +
            "    <meta name=\"mobile-web-app-capable\" content=\"yes\">\n" +
            "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
            "\n" +
            "    @imports\n" +
            "    <script src=\"bower_components/webcomponentsjs/webcomponents-lite.min.js\"></script>" +
            "\n" +
            "    <link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\">\n" +
            "  </head>\n" +
            "  <body unresolved>\n" +
            "    <h1>Hello World!</h1>\n" +
            "    <script src=\"main.js\" />" +
            "  </body>\n" +
            "</html>";

    private static final String STYLE_CSS = "/* Add all your styles here */";

    private static final String MAIN_JS = "// Add all your scripts here";

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

        Project[] projectArray = new Project[projects.size()];
        return projects.toArray(projectArray);
    }

    public static int createProject(Project project, boolean configure, Context context) {
        if (!configure) {
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
                JSONArray array = new JSONArray();
                for (int i = 0; i < ElementsHolder.getInstance().getElements().size(); i++) {
                    array.put(ElementsHolder.getInstance().getElements().get(i));
                }

                projectJson.put("title", project.getTitle());
                projectJson.put("description", project.getDescription());
                projectJson.put("elements", array);
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
                String newIndex = INDEX_HTML.replace("@name", project.getTitle())
                        .replace("@description", project.getDescription());
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < ElementsHolder.getInstance().getElements().size(); i++) {
                    builder.append("    <link rel=\"import\" href=\"bower_components/")
                            .append(ElementsHolder.getInstance().getElements().get(i))
                            .append("/")
                            .append(ElementsHolder.getInstance().getElements().get(i))
                            .append(".html\">\n");
                }

                newIndex = newIndex.replace("@imports", builder.toString());
                fileOutputStream.write(newIndex.getBytes());
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
        }

        new GetComponentsTask(SetupActivity.mProgressBar, SetupActivity.mProgressText, project, context).execute(SetupActivity.getComponentsUrl());
        return 0;
    }

    public static void deleteProject(String title) {
        GetComponentsTask.deleteDir(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + title));
    }
}

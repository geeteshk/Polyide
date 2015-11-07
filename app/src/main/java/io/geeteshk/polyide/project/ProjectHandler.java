package io.geeteshk.polyide.project;

import android.content.Context;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.geeteshk.polyide.SetupActivity;
import io.geeteshk.polyide.util.GetComponentsTask;
import io.geeteshk.polyide.util.PolyLog;

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
        if (!new File(Environment.getExternalStorageDirectory().toString() + "/Polyide").exists()) {
            if (new File(Environment.getExternalStorageDirectory().toString() + "/Polyide").mkdir()) {
                PolyLog.log("Created home directory.", 'i');
            } else {
                PolyLog.log("Unable to create home directory.", 'e');
            }
        } else {
            PolyLog.log("Home directory already exists.", 'w');
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
                    PolyLog.log(e.getMessage(), 'e');
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
                if (projectDir.mkdir()) {
                    PolyLog.log("Created project directory.", 'i');
                } else {
                    PolyLog.log("Unable to create project directory.", 'e');
                }
            } else {
                PolyLog.log("Project already exists!", 'w');
            }

            try {
                projectJson.put("title", project.getTitle());
                projectJson.put("description", project.getDescription());
            } catch (JSONException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(projectFile);
            } catch (FileNotFoundException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            assert fileOutputStream != null;
            try {
                fileOutputStream.write(projectJson.toString(4).getBytes());
            } catch (IOException | JSONException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream = new FileOutputStream(indexHtml);
            } catch (FileNotFoundException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                String newIndex = INDEX_HTML.replace("@name", project.getTitle())
                        .replace("@description", project.getDescription());
                fileOutputStream.write(newIndex.getBytes());
            } catch (IOException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream = new FileOutputStream(styleCss);
            } catch (FileNotFoundException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream.write(STYLE_CSS.getBytes());
            } catch (IOException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream = new FileOutputStream(mainJs);
            } catch (FileNotFoundException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream.write(MAIN_JS.getBytes());
            } catch (IOException e) {
                PolyLog.log(e.getMessage(), 'e');
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                PolyLog.log(e.getMessage(), 'e');
            }
        }

        new GetComponentsTask(SetupActivity.mProgressBar, SetupActivity.mProgressText, project, context).execute(SetupActivity.getComponentsUrl());

        return 0;
    }

    public static void deleteProject(String title) {
        GetComponentsTask.deleteDir(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + title));
    }

    public static void setupImports(Project project) {
        String[] files = new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + project.getTitle() + "/bower_components").list();
        String index = getContents(project.getTitle(), "index.html");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].equals("webcomponentsjs")) {
                builder.append("    <link rel=\"import\" href=\"bower_components/")
                        .append(files[i])
                        .append("/")
                        .append(files[i])
                        .append(".html\">\n");
            }
        }

        assert index != null;
        index = index.replace("@imports", builder.toString());

        try {
            writeContents(project.getTitle(), "index.html", index);
        } catch (IOException e) {
            PolyLog.log(e.getMessage(), 'e');
        }
    }

    private static String getContents(String project, String filename) {
        try {
            InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory() + "/Polyide/" + project + "/" + filename);
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

    private static void writeContents(String project, String filename, String contents) throws IOException {
        FileOutputStream stream = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/Polyide/" + project + "/" + filename));
        try {
            stream.write(contents.getBytes());
        } finally {
            stream.close();
        }
    }
}

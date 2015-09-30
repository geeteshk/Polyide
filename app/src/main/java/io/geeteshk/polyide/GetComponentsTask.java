package io.geeteshk.polyide;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GetComponentsTask extends AsyncTask<String, String, String> {

    ProgressBar mProgressBar;
    Project mProject;
    TextView mProgressText;

    public GetComponentsTask(ProgressBar progressBar, TextView progressText, Project project) {
        mProgressBar = progressBar;
        mProject = project;
        mProgressText = progressText;
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressText.setText("Downloading components...");
        mProgressBar.setIndeterminate(false);
    }

    @Override
    protected String doInBackground(String... fileUrl) {
        int count;
        try {
            URL url = new URL(fileUrl[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/components.zip");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();

            output.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onProgressUpdate(String... progress) {
        mProgressBar.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String fileUrl) {
        mProgressText.setText("Setting up components...");
        mProgressBar.setIndeterminate(true);

        unpackComponents();
        organizeFiles();

        mProgressText.setText("Finished!");
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(100);
    }

    private void organizeFiles() {
        File componentsFolder = new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/components/bower_components");
        File newFolder = new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/bower_components");

        newFolder.mkdir();
        if (componentsFolder.isDirectory()) {
            File[] content = componentsFolder.listFiles();
            for (File aContent : content) {
                aContent.renameTo(new File(newFolder, aContent.getName()));
            }
        }

        deleteDir(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/components"));
        deleteDir(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/components.zip"));
    }

    private void unpackComponents() {
        InputStream inputStream;
        ZipInputStream zipInputStream;
        try {
            String filename;
            inputStream = new FileInputStream(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/components.zip");
            zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
            ZipEntry zipEntry;
            byte[] buffer = new byte[1024];
            int count;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                filename = zipEntry.getName();

                if (zipEntry.isDirectory()) {
                    File file = new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/" + filename);
                    file.mkdirs();
                    continue;
                }

                FileOutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + mProject.getTitle() + "/" + filename);

                while ((count = zipInputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, count);
                }

                fileOutputStream.close();
                zipInputStream.closeEntry();
            }

            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

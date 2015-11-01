package io.geeteshk.polyide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import io.geeteshk.polyide.element.ElementsHolder;
import io.geeteshk.polyide.project.Project;
import io.geeteshk.polyide.project.ProjectHandler;
import io.geeteshk.polyide.util.Theme;

public class SetupActivity extends AppCompatActivity {

    public static ProgressBar mProgressBar;
    public static TextView mProgressText;

    public static String getComponentsUrl() {
        ArrayList<String> elements = ElementsHolder.getInstance().getElements();
        StringBuilder builder = new StringBuilder("http://bowerarchiver.appspot.com/archive?");
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).startsWith("google") || elements.get(i).startsWith("firebase")) {
                builder.append(elements.get(i))
                        .append("=GoogleWebComponents%2F")
                        .append(elements.get(i))
                        .append("%23%5E1.0.0&");
            } else {
                builder.append(elements.get(i))
                        .append("=PolymerElements%2F")
                        .append(elements.get(i))
                        .append("%23%5E1.0.0&");
            }
        }

        return builder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getThemeId(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressText = (TextView) findViewById(R.id.progress_text);

        Project project = new Project(ElementsHolder.getInstance().getProject().getTitle(), ElementsHolder.getInstance().getProject().getDescription());
        mProgressText.setText("Creating project files...");
        mProgressBar.setIndeterminate(true);
        ProjectHandler.createProject(project, ElementsHolder.getInstance().isConfigure(), SetupActivity.this);
    }
}

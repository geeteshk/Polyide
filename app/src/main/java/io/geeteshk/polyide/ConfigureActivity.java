package io.geeteshk.polyide;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;

import io.geeteshk.polyide.catalog.CatalogActivity;
import io.geeteshk.polyide.element.Elements;
import io.geeteshk.polyide.element.ElementsHolder;
import io.geeteshk.polyide.project.Project;
import io.geeteshk.polyide.project.ProjectHandler;
import io.geeteshk.polyide.util.PolyClient;
import io.geeteshk.polyide.util.Theme;

public class ConfigureActivity extends AppCompatActivity {

    Button mAddElement;
    EditText mDeleteProjectTitle;
    ImageButton mDeleteProject;
    LinearLayout mElementsList;
    TextView mProjectSize;
    WebView mProjectPreview;

    public static long getFolderSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getThemeId(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("project_title"));
        }

        mProjectPreview = (WebView) findViewById(R.id.project_configure_preview);
        mProjectPreview.setWebViewClient(new PolyClient());
        mProjectPreview.loadUrl(getIntent().getStringExtra("project_url"));

        mProjectSize = (TextView) findViewById(R.id.project_size);
        mProjectSize.setText(String.format("%.2f", (float) getFolderSize(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + getIntent().getStringExtra("project_title"))) / (1024 * 1024)) + " MB");

        mElementsList = (LinearLayout) findViewById(R.id.elements_configure_list);
        String[] elements = getElements(new File(Environment.getExternalStorageDirectory().toString() + "/Polyide/" + getIntent().getStringExtra("project_title")));
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout[] relativeLayouts = new RelativeLayout[elements.length];
        for (int i = 0; i < elements.length; i++) {
            relativeLayouts[i] = (RelativeLayout) inflater.inflate(R.layout.element_configure_item, null, false);
            boolean addToLayout = false;
            if (elements[i].startsWith("iron") && !elements[i].equals("iron-checked-element-behavior")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.IRON_DESCRIPTIONS[Arrays.asList(Elements.IRON_TITLES).indexOf(elements[i].substring(5))]);
                addToLayout = true;
            } else if (elements[i].startsWith("paper")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.PAPER_DESCRIPTIONS[Arrays.asList(Elements.PAPER_TITLES).indexOf(elements[i].substring(6))]);
                addToLayout = true;
            } else if (elements[i].startsWith("google")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.GOOGLE_DESCRIPTIONS[Arrays.asList(Elements.GOOGLE_TITLES).indexOf(elements[i].substring(7))]);
                addToLayout = true;
            } else if (elements[i].startsWith("firebase")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.GOOGLE_DESCRIPTIONS[Arrays.asList(Elements.GOOGLE_TITLES).indexOf(elements[i].substring(9))]);
                addToLayout = true;
            } else if (elements[i].startsWith("gold")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.GOLD_DESCRIPTIONS[Arrays.asList(Elements.GOLD_TITLES).indexOf(elements[i].substring(5))]);
                addToLayout = true;
            } else if (elements[i].startsWith("neon")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.NEON_DESCRIPTIONS[Arrays.asList(Elements.NEON_TITLES).indexOf(elements[i].substring(5))]);
                addToLayout = true;
            } else if (elements[i].startsWith("platinum")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.PLATINUM_DESCRIPTIONS[Arrays.asList(Elements.PLATINUM_TITLES).indexOf(elements[i].substring(9))]);
                addToLayout = true;
            } else if (elements[i].startsWith("marked")) {
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_check)).setText(elements[i]);
                ((TextView) relativeLayouts[i].findViewById(R.id.element_configure_description)).setText(Elements.MOLECULES_DESCRIPTIONS[Arrays.asList(Elements.MOLECULES_TITLES).indexOf(elements[i].substring(7))]);
                addToLayout = true;
            }

            if (addToLayout) {
                relativeLayouts[i].findViewById(R.id.element_configure_delete).setOnClickListener(new DeleteClickListener(ConfigureActivity.this, Environment.getExternalStorageDirectory().toString() + "/Polyide/" + getIntent().getStringExtra("project_title") + "/bower_components/" + elements[i]));
                mElementsList.addView(relativeLayouts[i]);
            }
        }

        mAddElement = (Button) findViewById(R.id.add_element);
        mAddElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigureActivity.this, CatalogActivity.class);
                startActivity(intent);
                ElementsHolder.getInstance().setProject(new Project(getIntent().getStringExtra("project_title"), getIntent().getStringExtra("project_description")));
                ElementsHolder.getInstance().setConfigure(true);
            }
        });

        mDeleteProjectTitle = (EditText) findViewById(R.id.project_delete_title);
        mDeleteProject = (ImageButton) findViewById(R.id.project_delete);
        mDeleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteProjectTitle.getText().toString().equals(getIntent().getStringExtra("project_title"))) {
                    ProjectHandler.deleteProject(getIntent().getStringExtra("project_title"));
                    Intent intent = new Intent(ConfigureActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    mDeleteProjectTitle.setError("Please enter the full name of your project first.");
                }
            }
        });
    }

    public String[] getElements(File dir) {
        return new File(dir.getPath() + "/bower_components").list();
    }

    private boolean removeElement(File element) {
        if (element.isDirectory()) {
            for (File child : element.listFiles()) {
                removeElement(child);
            }
        }

        return element.delete();
    }

    private class DeleteClickListener implements View.OnClickListener {

        AppCompatActivity mActivity;
        String mPath;

        public DeleteClickListener(AppCompatActivity activity, String path) {
            mActivity = activity;
            mPath = path;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Remove element?");
            builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (removeElement(new File(mPath))) {
                        Toast.makeText(mActivity, "Element removed.", Toast.LENGTH_SHORT).show();
                        mActivity.recreate();
                    }
                }
            });

            builder.setNegativeButton("CANCEL", null);
            builder.create().show();
        }
    }
}

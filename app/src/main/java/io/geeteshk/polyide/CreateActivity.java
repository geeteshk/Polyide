package io.geeteshk.polyide;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton mFinishCreate;
    TextInputLayout mTitleInput, mDescriptionInput;
    EditText mTitleText, mDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mFinishCreate = (FloatingActionButton) findViewById(R.id.finish_create);

        mTitleInput = (TextInputLayout) findViewById(R.id.title_input);
        mDescriptionInput = (TextInputLayout) findViewById(R.id.description_input);

        mTitleText = (EditText) findViewById(R.id.title_text);
        mDescriptionText = (EditText) findViewById(R.id.description_text);

        mFinishCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_create:
                if (mTitleText.getText().toString().isEmpty()) {
                    mTitleInput.setError("Please enter a title for your project.");
                } else {
                    if (mDescriptionText.getText().toString().isEmpty()) {
                        mDescriptionInput.setError("Please enter a description for your project.");
                    } else {
                        Project project = new Project(mTitleText.getText().toString(), mDescriptionText.getText().toString());
                        ProjectHandler.createProject(project);
                        finish();
                    }
                }

                break;
        }
    }
}

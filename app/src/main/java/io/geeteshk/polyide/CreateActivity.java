package io.geeteshk.polyide;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.geeteshk.polyide.catalog.CatalogActivity;
import io.geeteshk.polyide.element.ElementsHolder;
import io.geeteshk.polyide.project.Project;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    Button mNext;
    TextInputLayout mTitleInput, mDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mTitleInput = (TextInputLayout) findViewById(R.id.title_input);
        mDescriptionInput = (TextInputLayout) findViewById(R.id.description_input);

        mNext = (Button) findViewById(R.id.create_next);
        mNext.getBackground().setColorFilter(0xff2196f3, PorterDuff.Mode.MULTIPLY);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_next:
                mTitleInput.setErrorEnabled(false);
                mDescriptionInput.setErrorEnabled(false);
                if (mTitleInput.getEditText().getText().toString().isEmpty()) {
                    mTitleInput.setError("Please enter a title for your project.");
                } else {
                    if (mDescriptionInput.getEditText().getText().toString().isEmpty()) {
                        mDescriptionInput.setError("Please enter a description for your project.");
                    } else {
                        Intent intent = new Intent(CreateActivity.this, CatalogActivity.class);
                        startActivity(intent);
                        ElementsHolder.getInstance().setProject(new Project(mTitleInput.getEditText().getText().toString(), mDescriptionInput.getEditText().getText().toString()));
                        ElementsHolder.getInstance().setElements(new ArrayList<String>());
                    }
                }

                break;
        }
    }
}

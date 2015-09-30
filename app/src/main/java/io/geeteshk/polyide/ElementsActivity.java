package io.geeteshk.polyide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ElementsActivity extends AppCompatActivity {

    private RecyclerView mElementsList;
    private RecyclerView.Adapter mElementsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);

        mElementsList = (RecyclerView) findViewById(R.id.elements_list);
        mElementsList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mElementsList.setLayoutManager(mLayoutManager);

        mElementsAdapter = new ElementsAdapter(getIntent().getIntExtra("element_id", -1));
        mElementsList.setAdapter(mElementsAdapter);
    }
}

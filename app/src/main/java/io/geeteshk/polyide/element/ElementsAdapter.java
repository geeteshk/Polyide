package io.geeteshk.polyide.element;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import io.geeteshk.polyide.R;

public class ElementsAdapter extends RecyclerView.Adapter<ElementsAdapter.Holder> {

    int mCatalogId;
    private ArrayList<String> mCheckedItems;

    public ElementsAdapter(int id) {
        mCatalogId = id;
        mCheckedItems = ElementsHolder.getInstance().getElements();
    }

    @Override
    public ElementsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(ElementsAdapter.Holder holder, int position) {
        switch (mCatalogId) {
            case 0:
                holder.mElementBox.setText("iron-" + Elements.IRON_TITLES[position]);
                holder.mElementDescription.setText(Elements.IRON_DESCRIPTIONS[position]);
                break;
            case 1:
                holder.mElementBox.setText("paper-" + Elements.PAPER_TITLES[position]);
                holder.mElementDescription.setText(Elements.PAPER_DESCRIPTIONS[position]);
                break;
            case 2:
                if (!Elements.GOOGLE_TITLES[position].equals("firebase-element")) {
                    holder.mElementBox.setText("google-" + Elements.GOOGLE_TITLES[position]);
                } else {
                    holder.mElementBox.setText(Elements.GOOGLE_TITLES[position]);
                }

                holder.mElementDescription.setText(Elements.GOOGLE_DESCRIPTIONS[position]);
                break;
            case 3:
                holder.mElementBox.setText("gold-" + Elements.GOLD_TITLES[position]);
                holder.mElementDescription.setText(Elements.GOLD_DESCRIPTIONS[position]);
                break;
            case 4:
                holder.mElementBox.setText("neon-" + Elements.NEON_TITLES[position]);
                holder.mElementDescription.setText(Elements.NEON_DESCRIPTIONS[position]);
                break;
            case 5:
                holder.mElementBox.setText("platinum-" + Elements.PLATINUM_TITLES[position]);
                holder.mElementDescription.setText(Elements.PLATINUM_DESCRIPTIONS[position]);
                break;
            case 6:
                holder.mElementBox.setText(Elements.MOLECULES_TITLES[position]);
                holder.mElementDescription.setText(Elements.MOLECULES_DESCRIPTIONS[position]);
                break;
        }

        holder.mElementBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckedItems.add(buttonView.getText().toString());
                } else {
                    mCheckedItems.remove(buttonView.getText().toString());
                }

                ElementsHolder.getInstance().setElements(mCheckedItems);
            }
        });

        for (int i = 0; i < mCheckedItems.size(); i++) {
            holder.mElementBox.setChecked(holder.mElementBox.getText().toString().equals(mCheckedItems.get(i)));
        }
    }

    @Override
    public int getItemCount() {
        switch (mCatalogId) {
            case 0:
                return Elements.IRON_TITLES.length;
            case 1:
                return Elements.PAPER_TITLES.length;
            case 2:
                return Elements.GOOGLE_TITLES.length;
            case 3:
                return Elements.GOLD_TITLES.length;
            case 4:
                return Elements.NEON_TITLES.length;
            case 5:
                return Elements.PLATINUM_TITLES.length;
            case 6:
                return Elements.MOLECULES_TITLES.length;
        }

        return 0;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public CheckBox mElementBox;
        public TextView mElementDescription;

        public Holder(View view) {
            super(view);
            mElementBox = (CheckBox) view.findViewById(R.id.element_check);
            mElementDescription = (TextView) view.findViewById(R.id.element_description);
        }
    }
}

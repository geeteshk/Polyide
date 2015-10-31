package io.geeteshk.polyide.element;

import java.util.ArrayList;

import io.geeteshk.polyide.project.Project;

public class ElementsHolder {

    private static final ElementsHolder holder = new ElementsHolder();
    private ArrayList<String> mElements = new ArrayList<>();
    private Project mProject;
    private boolean mConfigure;

    public static ElementsHolder getInstance() {
        return holder;
    }

    public ArrayList<String> getElements() {
        return mElements;
    }

    public void setElements(ArrayList<String> elements) {
        mElements = elements;
    }

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        mProject = project;
    }

    public boolean isConfigure() {
        return mConfigure;
    }

    public void setConfigure(boolean configure) {
        mConfigure = configure;
    }
}

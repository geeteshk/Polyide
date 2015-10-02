package io.geeteshk.polyide.element;

import java.util.ArrayList;

import io.geeteshk.polyide.project.Project;

public class ElementsHolder {

    private static final ElementsHolder holder = new ElementsHolder();
    private ArrayList<String> mElements = new ArrayList<>();
    private Project mProject;

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
}

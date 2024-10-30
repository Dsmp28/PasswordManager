package org.java.passwordmanager.objectControllers;

import org.java.passwordmanager.objects.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagController {

    public List<Tag> tags = new ArrayList<Tag>();

    public void addTag(Tag tag) {
        tags.add(tag);
    }
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void editTag(Tag oldTag, Tag newTag) {
        int index = tags.indexOf(oldTag);
        if (index != -1) {
            tags.set(index, newTag);
        }else {
            System.out.println("Tag no encontrado.");
        }
    }

    public Tag getTag(int index) {
        return tags.get(index);
    }
    public List<Tag> getTags() {
        return tags;
    }

    public int getSize() {
        return tags.size();
    }

    //Elimina todos los tags
    public void clearTags() {
        tags.clear();
    }
}

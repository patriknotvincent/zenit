package zenit.zencodearea.codecompletion;

import java.util.List;

/**
 * Listener for changes in classes in project.
 */
public interface ExistingClassesListener {
    void onExistingClassesChanged(List<String> existingClasses);
}

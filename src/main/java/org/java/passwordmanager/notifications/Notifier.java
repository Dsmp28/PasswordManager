package org.java.passwordmanager.notifications;

public interface Notifier {
    void showSuccess(String message);
    void showError(String message);
    void showInfo(String message);
    void showWarning(String message);
    boolean showDecision(String message);
}

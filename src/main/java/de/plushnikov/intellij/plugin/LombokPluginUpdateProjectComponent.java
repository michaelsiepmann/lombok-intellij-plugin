package de.plushnikov.intellij.plugin;

import com.intellij.notification.*;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import de.plushnikov.intellij.plugin.settings.LombokSettings;
import org.jetbrains.annotations.NotNull;

/**
 * Shows update notification
 */
public class LombokPluginUpdateProjectComponent extends AbstractProjectComponent {

  protected LombokPluginUpdateProjectComponent(Project project) {
    super(project);
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "LombokPluginUpdateProjectComponent";
  }

  @Override
  public void projectOpened() {
    final LombokSettings settings = LombokSettings.getInstance();
    boolean updated = !Version.PLUGIN_VERSION.equals(settings.getVersion());
    if (updated) {
      settings.setVersion(Version.PLUGIN_VERSION);

      NotificationGroup group = new NotificationGroup(Version.PLUGIN_NAME, NotificationDisplayType.STICKY_BALLOON, true);
      Notification notification = group.createNotification(
        LombokBundle.message("daemon.donate.title", Version.PLUGIN_VERSION),
        LombokBundle.message("daemon.donate.content"),
        NotificationType.INFORMATION,
        new NotificationListener.UrlOpeningListener(false)
      );

      Notifications.Bus.notify(notification, myProject);
    }
  }

}

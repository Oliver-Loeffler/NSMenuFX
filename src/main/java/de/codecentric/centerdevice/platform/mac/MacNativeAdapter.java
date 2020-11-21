package de.codecentric.centerdevice.platform.mac;

import de.codecentric.centerdevice.platform.NativeAdapter;
import de.codecentric.centerdevice.platform.mac.convert.NSMenuFX;
import de.jangassen.jfa.appkit.NSApplication;
import de.jangassen.jfa.appkit.NSMenu;
import de.jangassen.jfa.appkit.NSMenuItem;
import de.jangassen.jfa.appkit.NSWorkspace;
import de.jangassen.jfa.foundation.Foundation;
import de.jangassen.jfa.foundation.ID;
import javafx.application.Platform;
import javafx.scene.control.Menu;

public class MacNativeAdapter implements NativeAdapter {

  private final NSApplication sharedApplication;
  private final NSWorkspace sharedWorkspace;

  private boolean forceQuitOnCmdQ = true;

  public MacNativeAdapter() {
    sharedApplication = NSApplication.sharedApplication();
    sharedWorkspace = NSWorkspace.sharedWorkspace();
  }

  public static boolean isAvailable() {
    return Foundation.isAvailable();
  }

  public void setApplicationMenu(Menu menu) {
    NSMenu nsMenu = sharedApplication.mainMenu();
    NSMenuItem mainMenu = NSMenuItem.alloc().initWithTitle("", null, "");
    mainMenu.setSubmenu(NSMenuFX.convert(menu));

    nsMenu.removeItemAtIndex(0);
    nsMenu.insertItem(mainMenu, 0);
  }

  public void hide() {
    sharedApplication.hide(ID.NIL);
  }

  public void hideOtherApplications() {
    sharedWorkspace.hideOtherApplications();
  }

  public void showAllWindows() {
    sharedApplication.unhide(ID.NIL);
  }

  public void quit() {
    if (forceQuitOnCmdQ) {
      Platform.exit();
    }
  }

  public void setForceQuitOnCmdQ(boolean forceQuit) {
    this.forceQuitOnCmdQ = forceQuit;
  }

}

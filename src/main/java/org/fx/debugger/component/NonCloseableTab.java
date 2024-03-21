package org.fx.debugger.component;

import javafx.scene.control.Tab;

public class NonCloseableTab extends Tab {
  {
    setClosable(false);
  }
}

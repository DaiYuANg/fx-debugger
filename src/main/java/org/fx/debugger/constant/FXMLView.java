package org.fx.debugger.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FXMLView {
  LAYOUT("Layout");

  private final String view;
}

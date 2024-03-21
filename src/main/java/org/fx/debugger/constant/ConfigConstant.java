package org.fx.debugger.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfigConstant {
  SPLIT_PANE_DIVIDER_POSITION("split_pane_divider_position");

  private final String key;
}

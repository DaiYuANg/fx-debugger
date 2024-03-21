package org.fx.debugger.constant;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ThemeConstant {
  PRIMER_LIGHT(PrimerLight::new),

  PRIMER_DARK(PrimerDark::new);

  private final Supplier<atlantafx.base.theme.Theme> themeSupplier;
}

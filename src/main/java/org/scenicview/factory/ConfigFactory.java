package org.scenicview.factory;

import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Group;
import com.dlsc.preferencesfx.model.Setting;
import io.avaje.inject.Bean;
import io.avaje.inject.Factory;
import io.avaje.inject.Lazy;
import javafx.beans.property.*;
import org.scenicview.model.Config;

@Factory
public class ConfigFactory {

  @Bean
  @Lazy
  PreferencesFx preferencesFx() {
    StringProperty stringProperty = new SimpleStringProperty("String");
    BooleanProperty booleanProperty = new SimpleBooleanProperty(true);
    IntegerProperty integerProperty = new SimpleIntegerProperty(12);
    DoubleProperty doubleProperty = new SimpleDoubleProperty(6.5);
    PreferencesFx preferencesFx =
        PreferencesFx.of(
            Config.class, // Save class (will be used to reference saved values of Settings to)
            Category.of(
                "Category title 1",
                Setting.of("Setting title 1", stringProperty), // creates a group automatically
                Setting.of("Setting title 2", booleanProperty) // which contains both settings
                ),
            Category.of("Category title 2")
                .expand() // Expand the parent category in the tree-view
                .subCategories( // adds a subcategory to "Category title 2"
                    Category.of(
                        "Category title 3",
                        Group.of("Group title 1", Setting.of("Setting title 3", integerProperty)),
                        Group.of( // group without title
                            Setting.of("Setting title 3", doubleProperty)))));

    return preferencesFx;
  }
}

package org.fx.debugger;

import static java.lang.System.exit;

import javafx.application.Application;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.fx.debugger.view.ScenicView;
import picocli.CommandLine;

@CommandLine.Command(name = "Visual", mixinStandardHelpOptions = true, helpCommand = true)
@RequiredArgsConstructor
@Slf4j
public class FxDebugger implements Runnable {

  private final String[] args;

  @Override
  public void run() {
    Application.launch(ScenicView.class, args);
  }

  @SneakyThrows
  public static void main(String[] args) {
    log.atDebug().log("Visual Start");
    val commandLine = new FxDebugger(args);
    val exitCode = new CommandLine(commandLine).execute(args);
    exit(exitCode);
  }
}

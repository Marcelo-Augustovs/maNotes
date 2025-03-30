package dev_marcelo.maNotes;

import dev_marcelo.maNotes.infra.security.interface_grafica.util.AppManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MaNotesApplication extends Application {
	private static ConfigurableApplicationContext springContext;

	@Override
	public void init() throws Exception {
		// Inicializa o Spring antes de carregar o JavaFX
		springContext = new SpringApplicationBuilder(MaNotesApplication.class).run();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AppManager.setPrimaryStage(primaryStage);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml")); // Caminho para o FXML da tela de login
		Scene scene = new Scene(loader.load());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Login - MaNotes");
		primaryStage.show();
	}
	@Override
	public void stop() throws Exception {
		// Fecha o contexto Spring ao encerrar o aplicativo
		springContext.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

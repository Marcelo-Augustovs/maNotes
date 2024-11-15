package dev_marcelo.maNotes;

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
	public void start(Stage stage) throws Exception {
		// Carrega a interface FXML usando o contexto do Spring
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MaNotes.fxml"));
		fxmlLoader.setControllerFactory(springContext::getBean); // Injeta dependências Spring nos controladores

		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("MaNotes Application");
		stage.setScene(scene);
		stage.show();
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

package com.spec.api_sugflora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.spec.api_sugflora.service.ComunsService;

@SpringBootApplication
public class ApiSugfloraApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	private static volatile boolean carregando = true;

	private static Thread loadingThread = new Thread(ApiSugfloraApplication::loading);

	public static void main(String[] args) {
		loadingThread.start();
		SpringApplication.run(ApiSugfloraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		carregando = false;
		loadingThread.interrupt();

		ComunsService.limparTerminal();

		// Obtendo o perfil ativo corretamente
		String profile = environment.getActiveProfiles().length > 0
				? environment.getActiveProfiles()[0]
				: environment.getDefaultProfiles()[0];

		String port = environment.getProperty("server.port", "8080");

		System.out.println("\n" +
				"╔═════════════════════════════════════════════════════╗\n" +
				"║          SISTEMA ÚNICO DE GESTÃO - FLORA            ║\n" +
				"╠═════════════════════════════════════════════════════╣\n" +
				"  Profile Ativo: " + profile + "                              \n" +
				"╚═════════════════════════════════════════════════════╝");

		System.err.println("\u001B[32m  Aplicação Iniciada\u001B[0m" +
				"\n  Rodando no endereço: http://localhost:" + port +
				"\n  Swagger: http://localhost:" + port + "/swagger-ui/index.html" +
				"\n╚═════════════════════════════════════════════════════╝");
	}

	private static void loading() {
		String baseMensagem = "Carregando Aplicação Sug-Flora";
		String[] sufixos = { ".", "..", "...", "...." };

		int i = 0;
		while (carregando && !Thread.currentThread().isInterrupted()) {
			// Limpa o terminal (Windows e Linux)
			ComunsService.limparTerminal();

			// Exibe a mensagem com a animação
			System.out.println(baseMensagem + sufixos[i % sufixos.length]);

			// Aguarda um pouco antes da próxima iteração
			try {
				Thread.sleep(1000); // Meio segundo
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}

			i++;
		}
	}

}

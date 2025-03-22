package com.spec.api_sugflora;

import java.time.LocalDateTime;

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

	// private static volatile boolean carregando = true;

	// private static Thread loadingThread = new
	// Thread(ApiSugfloraApplication::loading);

	public static void main(String[] args) {
		ComunsService.limparTerminal();
		System.out.println("\n" +
				"╔═════════════════════════════════════════════════════╗\n" +
				"║          SISTEMA ÚNICO DE GESTÃO - FLORA            ║\n" +
				"╠═════════════════════════════════════════════════════╣\n" +
				"  Iniciando aplicação...                            \n" +
				"╚═════════════════════════════════════════════════════╝");

		// loadingThread.start();
		SpringApplication.run(ApiSugfloraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// carregando = false;
		// loadingThread.interrupt();

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
				"\n  Data e Hr: " + LocalDateTime.now() +
				"\n╚═════════════════════════════════════════════════════╝");
	}

}

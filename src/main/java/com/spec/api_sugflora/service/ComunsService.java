package com.spec.api_sugflora.service;

import org.springframework.stereotype.Service;

@Service
public class ComunsService {

    @SuppressWarnings("deprecation")
    public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.pecodigos.literalura;

import com.pecodigos.literalura.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
    public class LiterAluraApplication implements CommandLineRunner {

        @Autowired
        UI ui;

        public static void main(String[] args) {
            SpringApplication.run(com.pecodigos.literalura.LiterAluraApplication.class, args);
        }

        @Override
        public void run(String... args) {
            ui.showMenu();
        }
    }

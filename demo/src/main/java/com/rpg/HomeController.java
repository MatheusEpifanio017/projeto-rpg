
package com.rpg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Mythbound</title>
                    </head>
                    <body style="font-family: Arial; text-align: center; margin-top: 100px; background: #101827; color: white;">
                        <h1>Mythbound</h1>
                        <p>Servidor iniciado com sucesso!</p>
                        <p>Conexão com o banco de dados configurada.</p>
                    </body>
                </html>
                """;
    }
}

package br.com.fiap.ms.educamais.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI educamaisOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("EducaMais API")
                .version("1.0.0")
                .description("""
                        API REST do EducaMais, plataforma de educação corporativa do Instituto Eurofarma.

                        A API expõe o catálogo de trilhas de treinamento, as matrículas dos colaboradores com \
                        progresso individual por aula, as salas de estudo síncronas com controle de lotação, \
                        os alertas preditivos de evasão e a tutora EdIA.

                        A EdIA segue a regra de zero alucinação: toda resposta baseada no material de \
                        treinamento cita a fonte no formato Curso > Módulo > Aula. Quando não há fonte \
                        indexada para a pergunta, ela informa que não encontrou a informação em vez de responder.

                        Desenvolvida na disciplina Microservice and Web Engineering & IT Services, \
                        Sistemas de Informação, FIAP — Challenge Eurofarma 2026.""")
                .contact(new Contact().name("Equipe EducaMais"))
                .license(new License().name("Uso acadêmico")));
    }
}

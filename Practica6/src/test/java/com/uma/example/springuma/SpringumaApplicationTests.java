//Grupo B: Daniel Linares Bernal y Julian David Lemus

package com.uma.example.springuma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.uma.example.springuma.model.Medico;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringumaApplicationTests {

    @LocalServerPort
    private Integer port;

    private WebTestClient webTestClient;

    private Medico medico;

    private Paciente paciente;

    @PostConstruct
    public void init() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofMillis(30000)).build();
        medico = new Medico();
        medico.setId(1);
        medico.setDni("12345678A");
        medico.setNombre("Dr. House");
        medico.setEspecialidad("Oncología");
    }

    @Test
    public void createMedicoPost_isObtainedWithGet() {
        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        FluxExchangeResult<Medico> result = webTestClient.get().uri("/medico/1")
                .exchange()
                .expectStatus().isOk().returnResult(Medico.class);
        Medico medicoObtenido = result.getResponseBody().blockFirst();
        assertEquals(medico.getDni(), medicoObtenido.getDni());
    }

    @Test
    public void createMedicoPost_DeleteitCorrectly(){
        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        webTestClient.get().uri("/medico/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.dni").isEqualTo(medico.getDni());

        webTestClient.method(HttpMethod.DELETE).uri("/medico/1")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().is2xxSuccessful();

        webTestClient.get().uri("/medico/1")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void createMedicoPost_UpdateItCorrectly() {
        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        Medico medicoASettear = webTestClient.get().uri("/medico/dni/" + medico.getDni())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Medico.class)
                .returnResult()
                .getResponseBody();

        medicoASettear.setNombre("Paco");
        webTestClient.put().uri("/medico")
                .body(Mono.just(medicoASettear), Medico.class)
                .exchange()
                .expectStatus().isNoContent();

        Medico medicoObtenido = webTestClient.get().uri("/medico/dni/" + medico.getDni())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Medico.class)
                .returnResult()
                .getResponseBody();

        assertEquals("Paco", medicoObtenido.getNombre());
    }

    //Asociar y editar pacientes a médicos.
    //Así como el cambio de médico y detección del cambio.
    @Test
    public void createPacientePost_asociarMedico() {
        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("12345678B");
        paciente.setNombre("Anibe Malnacido");
        paciente.setMedico(medico);

        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        Paciente paciente1 = webTestClient.get()
                .uri("/paciente/" + paciente.getId())
                .exchange()
                .expectStatus().isOk().expectBody(Paciente.class)
                .returnResult()
                .getResponseBody();

        assertEquals(paciente1.getMedico().getDni(), medico.getDni());
    }

    @Test
    public void changeMedico_isObtainedWithChange() {
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("12345678B");
        paciente.setNombre("Anibe Malnacido");

        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        Medico medico2 = new Medico();
        medico2.setDni("26857817Q");
        medico2.setNombre("Dr. PenHouse");
        medico2.setId(2);

        webTestClient.post().uri("/medico")
                .body(Mono.just(medico2), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        paciente.setMedico(medico);
        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();
        assertEquals(medico.getDni(), paciente.getMedico().getDni());

        paciente.setMedico(medico2);

        webTestClient.put().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isNoContent();

        Paciente pacienteAux2 = webTestClient.get().uri("paciente/"+paciente.getId())
                .exchange()
                .expectStatus().isOk().expectBody(Paciente.class)
                .returnResult()
                .getResponseBody();
        assertEquals(medico2.getDni(), pacienteAux2.getMedico().getDni());
    }

    @Test
    public void subirImagenes_Correctamente() {
        Imagen imagen1 = new Imagen();
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("12345678B");
        paciente.setNombre("Anibe Malnacido");

        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        imagen1.setPaciente(paciente);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imagen1)
                .header("Content-Disposition", "form-data; name=image; filename=fake.jpg");

        builder.part("paciente", paciente);

        webTestClient.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange().expectStatus().isOk().expectBody().returnResult();
    }

    @Test
    public void prediccion_Correcta(){
        Imagen imagen1 = new Imagen();
        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("12345678B");
        paciente.setNombre("Anibe Malnacido");

        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        imagen1.setPaciente(paciente);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imagen1)
                .header("Content-Disposition", "form-data; name=image; filename=fake.jpg");

        builder.part("paciente", paciente);

        webTestClient.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange().expectStatus().isOk().expectBody().returnResult();

        //get prediccion
        String prediccion = webTestClient.get().uri("/imagen/predict/"+imagen1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    public void creacionInforme_Correctamente(){
        Imagen imagen1 = new Imagen();
        imagen1.setId(1);

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("12345678B");
        paciente.setNombre("Anibe Malnacido");

        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        imagen1.setPaciente(paciente);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imagen1)
                .header("Content-Disposition", "form-data; name=image; filename=fake.jpg");

        builder.part("paciente", paciente);

        webTestClient.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange().expectStatus().isOk().expectBody().returnResult();

        //get prediccion
        String prediccion = webTestClient.get().uri("/imagen/predict/"+imagen1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();

        //obtener imagen
        Imagen imagenAux= webTestClient.get().uri("/imagen/info/"+imagen1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Imagen.class)
                .returnResult().getResponseBody();

        Informe informe = new Informe();
        informe.setId(1);
        informe.setPrediccion(prediccion);
        informe.setContenido("pepe");
        informe.setImagen(imagenAux);

        webTestClient.post().uri("/informe")
                .body(Mono.just(informe), Informe.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();

        webTestClient.delete()
                .uri("/informe/" + informe.getId())
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get().uri("/informe/" + informe.getId())
                .exchange()
                .expectStatus().isOk() // porque devuelve 200 aunque no exista
                .expectBody().isEmpty();
    }
}

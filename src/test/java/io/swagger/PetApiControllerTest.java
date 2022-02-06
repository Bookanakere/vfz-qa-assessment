package io.swagger;

import io.swagger.api.PetApiController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class)
@AutoConfigureMockMvc
public class PetApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetApiController petApiController;

    @Test
    public void contextLoads() {
        assertThat(petApiController).isNotNull();
    }

    @Test
    public void shouldGetStatusOkForFindPetByStatusWithValidStatus() throws Exception {
        this.mockMvc.perform(get("/pet/findByStatus?status=\"available\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetContentBodyForFindPetByStatusWithValidStatus() throws Exception {
        String response = this.mockMvc.perform(get("/pet/findByStatus?status=\"available\"")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        assertFalse(response.isEmpty());
    }

    @Test
    public void shouldGetStatusInvalidStatusValueForFindPetByStatusWithInvalidStatus() throws Exception {
            this.mockMvc.perform(get("/pet/findByStatus?status=\"test\"")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGetStatusCreatedForAddPetWithValidInput() throws Exception {
        this.mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": { \"id\": 6, \"name\": \"name\"}, \"id\": 0, \"name\": \"doggie\", \"photoUrls\": [ \"photoUrls\", \"photoUrls\"],\"status\": \"available\",\"tags\": [{\"id\": 1,\"name\": \"name\"},{\"id\": 1,\"name\": \"name\"}]}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetStatusNotFoundForAddPetWithInvalidInput() throws Exception {
        this.mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": { \"id\": , \"name\": \"name\"}, \"id\": 0, \"name\": \"doggie\", \"photoUrls\": [ \"photoUrls\", \"photoUrls\"],\"status\": \"available\",\"tags\": [{\"id\": 1,\"name\": \"name\"},{\"id\": 1,\"name\": \"name\"}]}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGetStatusOKForUpdatePetWithValidID() throws Exception {
        this.mockMvc.perform(put("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": { \"id\": 6, \"name\": \"name\"}, \"id\": 0, \"name\": \"doggie\", \"photoUrls\": [ \"photoUrls\", \"photoUrls\"],\"status\": \"available\",\"tags\": [{\"id\": 1,\"name\": \"name\"},{\"id\": 1,\"name\": \"name\"}]}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStatusInvalidIDForUpdatePetWithInvalidID() throws Exception {
        this.mockMvc.perform(put("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": { \"id\": a23, \"name\": \"name\"}, \"id\": 0, \"name\": \"doggie\", \"photoUrls\": [ \"photoUrls\", \"photoUrls\"],\"status\": \"available\",\"tags\": [{\"id\": 1,\"name\": \"name\"},{\"id\": 1,\"name\": \"name\"}]}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldGetStatusPetNotFoundForUpdatePetWithIncorrectID() throws Exception {
        this.mockMvc.perform(put("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"category\": { \"id\": 99999999999, \"name\": \"name\"}, \"id\": 0, \"name\": \"doggie\", \"photoUrls\": [ \"photoUrls\", \"photoUrls\"],\"status\": \"available\",\"tags\": [{\"id\": 1,\"name\": \"name\"},{\"id\": 1,\"name\": \"name\"}]}"))
                .andExpect(status().isNotFound());
    }
}

package com.example.assignment5;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.assignment5.model.Author;
import com.example.assignment5.service.AuthorServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Assignment5Application.class)
@AutoConfigureMockMvc
public class AuthorServiceTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllAuthors() throws Exception {
        when(authorService.readAllAuthors()).thenReturn(List.of(new Author(1L, "Artem", "Belov", null)));

        mockMvc.perform(get("/author/getAll"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAuthorById() throws Exception {
        when(authorService.findAuthorById(1)).thenReturn(new Author(1L, "Artem", "Belov", null));

        mockMvc.perform(get("/author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Artem")))
                .andExpect(jsonPath("$.surname", Matchers.equalTo("Belov")))
                .andReturn();
    }

    @Test
    public void createAuthor() throws Exception {

        String name = "Artem";
        String surname = "Belov";

        when(authorService.saveAuthor(any())).thenReturn(new Author(1L, "Artem", "Belov", null));

        String body =
                """
                      {
                        "name":"%s",
                        "surname":"%s"
                      }
                    """
                        .formatted(name, surname);

        MvcResult mvcResult = mockMvc.perform(post("/author/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andExpect(status().isCreated())
                .andReturn();
        System.out.println();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println();
    }

    @Test
    public void deleteAuthor() throws Exception {
        when(authorService.deleteAuthor(1)).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post("/author/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        System.out.println();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println();

        boolean actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
        assertTrue(actual);
    }

    @Test
    public void deleteNonexistentAuthor() throws Exception {
        when(authorService.deleteAuthor(1)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/author/delete/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println();

        boolean actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());

        assertFalse(actual);
    }

    private <T> T parseResponse(MvcResult mvcResult, Class<T> c) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}

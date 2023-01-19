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
import com.example.assignment5.model.Book;
import com.example.assignment5.service.BookServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(
        webEnvironment = WebEnvironment.MOCK,
        classes = Assignment5Application.class)
@AutoConfigureMockMvc
public class BookServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllBooks() throws Exception {
        when(bookService.readAllBooks()).thenReturn(List.of(new Book(1L, "123", new Author(1L, "Artem", "Belov", null))));

        mockMvc.perform(get("/book/getAll"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getBookById() throws Exception {
        when(bookService.findBookById(1)).thenReturn(new Book(1L, "123", new Author(1L, "Artem", "Belov", null)));

        mockMvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookName", Matchers.equalTo("123")))
                .andReturn();
    }

    @Test
    public void createBook() throws Exception {

        String bookName = "15155";
        String authorId = "1";

        when(bookService.saveBook(any())).thenReturn(new Book(1L, bookName, new Author(1L, "Artem","Belov", null)));

        String body =
                """
                      {
                        "bookName":"%s",
                        "authorId":"%s"
                      }               
                    """
                        .formatted( bookName, authorId);

        MvcResult mvcResult = mockMvc.perform(post("/book/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                ).andExpect(status().isCreated())
                .andReturn();
        System.out.println();
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println();
    }

    @Test
    public void deleteOrder() throws Exception {
        when(bookService.deleteBook(1)).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post("/book/delete/1")
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
    public void deleteNonexistentOrder() throws Exception {
        when(bookService.deleteBook(1)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(post("/book/delete/2")
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
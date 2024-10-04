package faang.school.accountservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static faang.school.accountservice.util.TestDataFactory.ACCOUNT_NUMBER;
import static faang.school.accountservice.util.TestDataFactory.createAccountDtoForSaving;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerIT extends AbstractionBaseTest {
    @Value("${test.x-user-id-header}")
    private String X_USER_ID_HEADER;
    @Value("${test.user-id}")
    private String USER_ID;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAccountDetailsWhenAccountNumberIsValid() throws Exception {
        // given - precondition
        // when - action
        var response = mockMvc.perform(get("/accounts")
                .param("number", ACCOUNT_NUMBER)
                .header(X_USER_ID_HEADER, USER_ID)
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(ACCOUNT_NUMBER))
                .andDo(print());
    }

    @Test
    void givenValidAccountWhenOpenAccountThenReturnSavedAccount() throws Exception {
        // given - precondition
        var accountDto = createAccountDtoForSaving();
        String accountDtoJson = objectMapper.writeValueAsString(accountDto);

        // when - action
        var response = mockMvc.perform(post("/accounts")
                .header(X_USER_ID_HEADER, USER_ID)
                .content(accountDtoJson)
                .contentType("application/json"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(accountDto.number()))
                .andExpect(jsonPath("$.type").value(equalToIgnoringCase(accountDto.type())))
                .andExpect(jsonPath("$.currency").value(equalToIgnoringCase(accountDto.currency())))
                .andExpect(jsonPath("$.status").value(equalToIgnoringCase(accountDto.status())))
                .andExpect(jsonPath("$.version").value(accountDto.version()))
                .andDo(print());
    }

    @Test
    void givenValidAccountNumberWhenBlockAccountThenAccountIsBlocked() throws Exception {
        // given - precondition
        // when - action
        var result = mockMvc.perform(patch("/accounts/block")
                        .param("number", ACCOUNT_NUMBER)
                        .header(X_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then - verify the output
        var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody.isEmpty()).isTrue();
    }

    @Test
    void givenValidAccountNumberWhenCloseAccountThenAccountIsClosed() throws Exception {
        // given - precondition
        // when - action
        var result = mockMvc.perform(delete("/accounts", ACCOUNT_NUMBER)
                        .param("number",  ACCOUNT_NUMBER)
                        .header(X_USER_ID_HEADER, USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then - verify the output
        var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody.isEmpty()).isTrue();
    }
}
package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CreateBookingRequestTest {

    @Autowired
    private JacksonTester<CreateBookingRequest> json;

    @Test
    @DisplayName("Протестировать json создаваемого заказа")
    void testCreateBookingRequest() throws Exception {
        CreateBookingRequest createBookingRequest = new CreateBookingRequest(1L, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        JsonContent<CreateBookingRequest> result = json.write(createBookingRequest);

        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).hasJsonPathValue("$.start").hasJsonPathValue("$.end");
    }
}
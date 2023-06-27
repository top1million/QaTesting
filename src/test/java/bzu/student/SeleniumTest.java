package bzu.student;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("SeleniumTest")
@Nested
class SeleniumTest {
    String apikey = "34ba08f8907e96e96067390b";

    @DisplayName("Test Selenium Conversion")
    @ParameterizedTest
    @ValueSource(strings = {"USD:USD:100", "USD:EUR:100", "EUR:USD:100", "EUR:EUR:100" , "ILS:ILS:100", "ILS:USD:100", "USD:ILS:100", "EUR:ILS:100", "ILS:EUR:100"})
    void testSelenium(String strings) {
        String fromCurrency = strings.split(":")[0];
        String toCurrency = strings.split(":")[1];
        float amount = Float.parseFloat(strings.split(":")[2]);
        Selenium selenium = new Selenium();
        float expected_result = expectedResult(fromCurrency, toCurrency, amount);
        assertEquals(expected_result, selenium.webTestConversion(amount,fromCurrency, toCurrency));
    }

    @DisplayName("Test Empty Amount Selenium Conversion")
    @Test
    void testEmptyAmountSelenium() {
        Selenium selenium = new Selenium();
        assertEquals(0.0, selenium.webTestConversion(0,"USD", "EUR"));
    }

    public float expectedResult(String fromCurrency, String toCurrency, double amount){
        String url = "https://v6.exchangerate-api.com/v6/"+apikey+"/pair/"+fromCurrency+"/"+toCurrency+"/"+amount;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try{
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        String jsonString = response.body();
        Gson gson = new Gson();
        ApiResponse res = gson.fromJson(jsonString, ApiResponse.class);
        float expected_result = res.getConversionResult();
        return expected_result;

    }
    static class ApiResponse {
        private String result;
        private String documentation;
        private String terms_of_use;
        private long time_last_update_unix;
        private String time_last_update_utc;
        private long time_next_update_unix;
        private String time_next_update_utc;
        private String base_code;
        private String target_code;
        private float conversion_rate;
        private float conversion_result;

        public float getConversionRate() {
            return conversion_rate;
        }
        public float getConversionResult(){
            return conversion_result;
        }
    }
}
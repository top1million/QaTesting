package bzu.student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class TaskOne {
    static Connection connection ;
    static String api_key = "34ba08f8907e96e96067390b";
    static PreparedStatement pst;
    public void connect() throws SQLException {
//        System.out.println("Connecting to the database...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Currency_Conversion","root","dontwant123");
//            System.out.println("Connected to the database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void insert(CurrencyConversionRate currencyConversionRate) throws SQLException {
        String sql = "INSERT INTO currency_conversion_rate (from_currency, to_currency, rate) VALUES (?, ?, ?)";
        pst = connection.prepareStatement(sql);
        pst.setString(1, currencyConversionRate.getFromCurrency());
        pst.setString(2, currencyConversionRate.getToCurrency());
        pst.setDouble(3, currencyConversionRate.getRate());
        pst.executeUpdate();
    }
    public void update(CurrencyConversionRate currencyConversionRate) throws SQLException {
        String sql = "UPDATE currency_conversion_rate SET rate = ? WHERE from_currency = ? AND to_currency = ?";
        pst = connection.prepareStatement(sql);
        pst.setDouble(1, currencyConversionRate.getRate());
        pst.setString(2, currencyConversionRate.getFromCurrency());
        pst.setString(3, currencyConversionRate.getToCurrency());
        pst.executeUpdate();
    }
    public void delete(CurrencyConversionRate currencyConversionRate) throws SQLException {
        String sql = "DELETE FROM currency_conversion_rate WHERE from_currency = ? AND to_currency = ?";
        pst = connection.prepareStatement(sql);
        pst.setString(1, currencyConversionRate.getFromCurrency());
        pst.setString(2, currencyConversionRate.getToCurrency());
        pst.executeUpdate();
    }
    public static double getConversionRate(String fromCurrency, String toCurrency) throws SQLException {

        String sql = "SELECT rate FROM currency_conversion_rate WHERE from_currency = ? AND to_currency = ?";
        pst = connection.prepareStatement(sql);
        pst.setString(1, fromCurrency);
        pst.setString(2, toCurrency);
        ResultSet x = pst.executeQuery();
        while (x.next()) {
            return x.getDouble("rate");
        }
        return 0;
    }
    public static void populateDataBase(String currency) throws SQLException, IOException {
        String url = "https://v6.exchangerate-api.com/v6/" + api_key + "/latest/"+currency;
        URL urlx = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlx.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Read the response from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Print the response

        //fill database with conversion_rates from response
        String conversion_rates = response.toString().split("conversion_rates")[1];
        conversion_rates = conversion_rates.substring(3, conversion_rates.length() - 2);
        String[] conversion_rates_array = conversion_rates.split(",");
        ArrayList<CurrencyConversionRate> currencyConversionRates = new ArrayList<>();
        System.out.println(conversion_rates);
        for (String conversion_rate : conversion_rates_array) {
            String[] conversion_rate_array = conversion_rate.split(":");
            CurrencyConversionRate currencyConversionRate = new CurrencyConversionRate();
            currencyConversionRate.setFromCurrency(currency);
            currencyConversionRate.setToCurrency(conversion_rate_array[0].substring(3, conversion_rate_array[0].length() - 1));
            currencyConversionRate.setRate(Double.parseDouble(conversion_rate_array[1]));
            currencyConversionRates.add(currencyConversionRate);
        }
        TaskOne taskOne = new TaskOne();
        taskOne.connect();
        for (CurrencyConversionRate currencyConversionRate : currencyConversionRates) {
            taskOne.insert(currencyConversionRate);
        }
        taskOne.close();

    }
    public void close() throws SQLException {
        connection.close();
    }
    public static void main(String[] args) throws SQLException, IOException {
//        populateDataBase("USD");
//        populateDataBase("EUR");
//        populateDataBase("GBP");
//        populateDataBase("CAD");
//        populateDataBase("JPY");
//        populateDataBase("AUD");
//        populateDataBase("CHF");
//        populateDataBase("ILS");
//        TaskOne taskOne = new TaskOne();
//        taskOne.connect();
//        Double answer = TaskOne.returnRate("USD", "USD");
//        System.out.println(answer);
    }

}

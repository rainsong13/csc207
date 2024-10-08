package edu.csc207.fall2024;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class StatementPrinterTests {

    private static String loadString(String path) {
        try {
            return new String(Objects.requireNonNull(StatementPrinterTests.class
                            .getClassLoader()
                            .getResourceAsStream(path))
                    .readAllBytes());
        }
        catch (IOException exception) {
                fail("resource file could not be loaded prior to test executing");
            }
        return "";
    }

    @Test
    public void exampleStatementTest() {

        String expected = loadString("ExampleStatement.txt");

        JSONObject a = new JSONObject(loadString("plays.json"));

        Map<String, Play> plays = new HashMap<>();

        for (String s : a.keySet()) {
            JSONObject play = (JSONObject) a.get(s);
            plays.put(s, new Play(play.getString("name"), play.getString("type")));
        }

        JSONArray ja = new JSONArray(loadString("invoices.json"));

        for (Object jo : ja) {
            JSONObject jinvoice = (JSONObject) jo;
            String customer = jinvoice.getString("customer");
            JSONArray jperformances = jinvoice.getJSONArray("performances");
            List<Performance> performances = new ArrayList<>();
            for (Object s : jperformances) {
                JSONObject performance = (JSONObject) s;
                performances.add(new Performance(performance.getString("playID"),
                        performance.getInt("audience")));
            }

            Invoice invoice = new Invoice(customer, performances);

            StatementPrinter statementPrinter = new StatementPrinter(invoice, plays);
            String result = statementPrinter.statement();

            assertEquals(String.format("Actual output:%n%s%nExpected:%s", result, expected), expected, result);
        }

    }
}

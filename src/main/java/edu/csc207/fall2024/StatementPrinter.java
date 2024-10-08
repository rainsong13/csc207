package edu.csc207.fall2024;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {
    public Invoice invoice;
    public Map<String, Play> plays;

    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Returns a formatted statement of the invoice associated with this printer.
     * @return the formatted statement
     * @throws RuntimeException if one of the play types is not known
     */
    public String statement() {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.getCustomer() + "\n";

        NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance p : invoice.getPerformances()) {
            Play play = plays.get(p.playID);
            final int thisAmount = getAmount(p, play);

            // add volume credits
            volumeCredits += Math.max(p.audience - Constants.BASE_VOLUME_CREDIT_THRESHOLD, 0);
            // add extra credit for every five comedy attendees
            if ("comedy".equals(play.type)) volumeCredits += p.audience / Constants.COMEDY_EXTRA_VOLUME_FACTOR;

            // print line for this order
            result += String.format("  %s: %s (%s seats)%n", play.name, frmt.format(thisAmount / 100), p.audience);
            totalAmount += thisAmount;
        }
        result += String.format("Amount owed is %s%n", frmt.format(totalAmount / 100));
        result += String.format("You earned %s credits\n", volumeCredits);
        return result;
    }

    private static int getAmount(Performance performance, Play play) {
        int thisAmount = 0;

        switch (play.type) {
            case "tragedy":
                thisAmount = 40000;
                if (performance.audience > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
                    thisAmount += 1000 * (performance.audience - 30);
                }
                break;
            case "comedy":
                thisAmount = Constants.COMEDY_BASE_AMOUNT;
                if (performance.audience > Constants.COMEDY_AUDIENCE_THRESHOLD) {
                    thisAmount += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                            + (Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                            * (performance.audience - Constants.COMEDY_AUDIENCE_THRESHOLD));
                }
                thisAmount += Constants.COMEDY_AMOUNT_PER_AUDIENCE * performance.audience;
                break;
            default:
                throw new RuntimeException(String.format("unknown type: %s", play.type));
        }
        return thisAmount;
    }

}

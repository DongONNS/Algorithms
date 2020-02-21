package edu.csu;

import java.util.Comparator;
import java.util.Date;

public class Transaction implements Comparable<Transaction> {
    private final String who;      // customer
    private final Date when;     // date
    private final double amount;   // amount


    /**
     * Initializes a new transaction from the given arguments.
     *
     * @param who    the person involved in this transaction
     * @param when   the date of this transaction
     * @param amount the amount of this transaction
     * @throws IllegalArgumentException if {@code amount}
     *                                  is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY},
     *                                  or {@code Double.NEGATIVE_INFINITY}
     */
    public Transaction(String who, Date when, double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    /**
     * Initializes a new transaction by parsing a string of the form NAME DATE AMOUNT.
     *
     * @param transaction the string to parse
     * @throws IllegalArgumentException if {@code amount}
     *                                  is {@code Double.NaN}, {@code Double.POSITIVE_INFINITY},
     *                                  or {@code Double.NEGATIVE_INFINITY}
     */
    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");
        who = a[0];
        when = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
        if (Double.isNaN(amount) || Double.isInfinite(amount))
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
    }

    /**
     * Returns the name of the customer involved in this transaction.
     *
     * @return the name of the customer involved in this transaction
     */
    public String who() {
        return who;
    }

    /**
     * Returns the date of this transaction.
     *
     * @return the date of this transaction
     */
    public Date when() {
        return when;
    }

    /**
     * Returns the amount of this transaction.
     *
     * @return the amount of this transaction
     */
    public double amount() {
        return amount;
    }

    /**
     * Returns a string representation of this transaction.
     *
     * @return a string representation of this transaction
     */
    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }

    /**
     * Compares two transactions by amount.
     *
     * @param that the other transaction
     * @return { a negative integer, zero, a positive integer}, depending
     * on whether the amount of this transaction is { less than,
     * equal to, or greater than } the amount of that transaction
     */
    public int compareTo(Transaction that) {
        return Double.compare(this.amount, that.amount);
    }

    /**
     * Compares this transaction to the specified object.
     *
     * @param other the other transaction
     * @return true if this transaction is equal to {@code other}; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Transaction that = (Transaction) other;
        return (this.amount == that.amount) && (this.who.equals(that.who))
                && (this.when.equals(that.when));
    }


    /**
     * Returns a hash code for this transaction.
     *
     * @return a hash code for this transaction
     */
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
        // return Objects.hash(who, when, amount);
    }

    /**
     * Compares two transactions by customer name.
     */
    public static class WhoOrder implements Comparator<Transaction> {


        public int compare(Transaction v, Transaction w) {
            return v.who.compareTo(w.who);
        }
    }

    /**
     * Compares two transactions by date.
     */
    public static class WhenOrder implements Comparator<Transaction> {

        public int compare(Transaction v, Transaction w) {
            return v.when.compareTo(w.when);
        }
    }

    /**
     * Compares two transactions by amount.
     */
    public static class HowMuchOrder implements Comparator<Transaction> {

        public int compare(Transaction v, Transaction w) {
            return Double.compare(v.amount, w.amount);
        }
    }
}
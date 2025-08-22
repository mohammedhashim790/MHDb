package com.mhdb.mhdb.Bloc.TransactionHandler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TransactionHandler {

    private final Integer id = new Random(255).nextInt(255);

    Queue<TransactionParams> queue = new LinkedList<>();

    public TransactionHandler() {
    }

    public boolean isLocked() {
        return !queue.isEmpty();
    }

    /**
     * Begins a new transaction by adding the transaction parameters to the queue.
     * <p>
     * This method adds a new {@code TransactionParams} object containing the command, parts,
     * and transaction ID to the queue, indicating the start of a new transaction.
     *
     * @param command The command associated with the transaction.
     * @param parts   An array of strings representing the parts of the command for the transaction.
     */
    public void begin(String command, String[] parts) {
        queue.add(new TransactionParams(command, parts, this.id));
    }

    /**
     * Rolls back the most recent transaction by removing it from the queue.
     * <p>
     * This method removes the last transaction from the queue, effectively canceling the transaction.
     */
    public void rollback() {
        queue.remove();
    }

    /**
     * Commits the most recent transaction by removing it from the queue and returning the transaction parameters.
     * <p>
     * This method removes the last transaction from the queue and returns the corresponding {@code TransactionParams}.
     *
     * @return The {@code TransactionParams} of the committed transaction.
     */
    public TransactionParams commit() {
        return queue.remove();
    }

    public Integer getId() {
        return id;
    }

}

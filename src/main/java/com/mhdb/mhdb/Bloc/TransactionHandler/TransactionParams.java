package com.mhdb.mhdb.Bloc.TransactionHandler;

public class TransactionParams {

    private String command;
    private String[] parts;

    private Integer transactionID;


    /**
     * TransactionParams consists commands, transaction id to execute by transaction handler.
     * @param command
     * @param parts
     * @param transactionID
     */
    public TransactionParams(String command, String[] parts, Integer transactionID) {
        this.command = command;
        this.parts = parts;
        this.transactionID = transactionID;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] parts) {
        this.parts = parts;
    }

    public Integer getTransactionID() {
        return transactionID;
    }
}

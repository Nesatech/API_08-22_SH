package com.test.api_0822_sh.exceptions.models;

/**
 * Custom error details
 *
 * @param timestamp
 * @param message custom message
 * @param details method details
 */
public record ErrorDetails(String timestamp, String message, String details) {

}

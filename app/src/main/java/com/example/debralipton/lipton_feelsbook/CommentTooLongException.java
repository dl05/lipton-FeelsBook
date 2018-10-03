package com.example.debralipton.lipton_feelsbook;

public class CommentTooLongException extends Exception {
    CommentTooLongException() {
        super("Comment must be less than 100 characters");
    }
}

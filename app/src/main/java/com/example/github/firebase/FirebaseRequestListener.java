package com.example.github.firebase;

public interface FirebaseRequestListener<T> {
    void OnFirebaseRequest(T data);
}

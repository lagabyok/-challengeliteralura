package com.example.service;

public interface IDataConverter{
    <T> T getData(String json, Class<T> classe);
}
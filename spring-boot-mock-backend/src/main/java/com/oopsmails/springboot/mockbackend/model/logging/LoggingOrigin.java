package com.oopsmails.springboot.mockbackend.model.logging;

public enum LoggingOrigin {
    // Generic
    GenericMockController,

    // Employee
    EmployeeController,

    // GitHubUser
    GitHubUserController,

    Controller,
    Service,
    Dao;
}

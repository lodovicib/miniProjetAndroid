package com.m2dl.miniprojetpointinteret.model;

public interface IUserDao {

    boolean store(User user);

    boolean delete(User user);
}

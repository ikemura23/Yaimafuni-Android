package com.ikmr.banbara23.yaeyama_liner_checker.api;

import com.ikmr.banbara23.yaeyama_liner_checker.model.CompanyStatus;

import retrofit2.http.GET;

public interface ApiService {
    @GET("users/{user}/repos")
    CompanyStatus listRepos(String user);
}

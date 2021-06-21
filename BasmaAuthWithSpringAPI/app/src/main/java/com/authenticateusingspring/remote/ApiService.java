package com.authenticateusingspring.remote;

import com.authenticateusingspring.responseClasses.ResponseClass;
import com.authenticateusingspring.responseClasses.ResponseRegisterClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    //Interface to call the REST API
    //PUT & POST Annotations for getting the users from DB & inserting new users into DB
    //Both methods will have a Json Body having name/pass from EditText
    @PUT("/addUser")
    Call<ResponseClass> addUser(@Body ResponseRegisterClass responseRegisterClass);

    @POST("/getUser")
    Call<ResponseClass> getUser(@Body ResponseRegisterClass responseRegisterClass);
}

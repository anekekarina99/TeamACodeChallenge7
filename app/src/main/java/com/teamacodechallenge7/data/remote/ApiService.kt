package com.teamacodechallenge7.data.remote

import com.blank.ch6_retrofit.data.model.SignUpMsg
import com.blank.ch6_retrofit.data.model.SignUpRequest
import com.teamacodechallenge7.data.model.GetBattle
import com.teamacodechallenge7.data.model.Users
import com.teamacodechallenge7.data.model.LoginMsg
import com.teamacodechallenge7.data.model.LoginRequest
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
//    @GET("/api/posts")
//    fun getPost(): Single<PostMsg>
//
    @POST("/api/v1/auth/login")
    fun loginAction(@Body requestLogin: LoginRequest): Single<LoginMsg>

    
    @POST("/api/v1/auth/register")
    fun signUp(@Body requestRegister: SignUpRequest): Single<SignUpMsg>
//
//    @GET("/api/v1/auth/me")
//    fun me(@Header("Authorization") authorization: String): Single<MeMsg>
//
//    @PUT("/api/v1/users")
//    fun me(@Header("Authorization") authorization: String): Single<MeMsg>

    @GET("/api/v1/battle")
    fun getBattle(
        @Header("Authorization") authorization: String): Observable<List<GetBattle>>

    @GET("/api/v1/users")
    fun getUsers(
        @Header("Authorization") authorization: String): Single<Users>

    @Multipart
    @PUT("/api/v1/users")
    fun uploadImage(
        @Header("Authorization") authorization: String,
        @Part("username") usernameBody: RequestBody,
        @Part("email") bodyEmail: RequestBody,
        @Part multipartBody: MultipartBody.Part
    ): Single<Users>
}
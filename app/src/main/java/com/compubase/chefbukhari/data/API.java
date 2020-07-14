package com.compubase.chefbukhari.data;

import com.compubase.chefbukhari.models.CategoriesProResponse;
import com.compubase.chefbukhari.models.FavouritesResponse;
import com.compubase.chefbukhari.models.OrderDetails;
import com.compubase.chefbukhari.models.OrdersAgentResponse;
import com.compubase.chefbukhari.models.OrdersDetailsResponse;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.models.ProductsModel;
import com.compubase.chefbukhari.models.SliderModel;
import com.compubase.chefbukhari.models.UserDatum;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> register (
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("img") String img,
            @Field("city") String city,
            @Field("area") String area
    );

    @FormUrlEncoded
    @POST("login_user")
    Call<ResponseBody>UserLogin(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("selecte_all_order_details")
    Call<ResponseBody>orderDetails(
            @Field("id_order") int id_order);

    @FormUrlEncoded
    @POST("insert_copoun_code")
    Call<ResponseBody>coupon(
            @Field("code") String code,
            @Field("totle_price") int totle_price
    );

    @FormUrlEncoded
    @POST("update_user")
    Call<ResponseBody>updateProfile(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String pass,
            @Field("phone") String phone,
            @Field("img") String img,
            @Field("city") String city,
            @Field("area") String area,
            @Field("id_user") String id
    );

    @GET("selecte_all_category")
    Call<ResponseBody>selecte_all_category();

    @FormUrlEncoded
    @POST("select_all_product")
    Call<List<ProductsModel>>viewProducts(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("selecte_all_orders_by_agent")
    Call<List<OrdersAgentResponse>>getAgentOreders(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("insert_fav")
    Call<ResponseBody>insertFav(
            @Field("id_user") String id_user,
            @Field("id_product") String id_product
    );



    @FormUrlEncoded
    @POST("selecte_all_fav")
    Call<List<FavouritesResponse>>selectFave(
            @Field("id_user") String id
    );


    @FormUrlEncoded
    @POST("deliver_order")
    Call<ResponseBody>deliver_order(
            @Field("code") String code,
            @Field("id_order") String id_order
    );

    @FormUrlEncoded
    @POST("selecte_all_orders")
    Call<List<OrdersResponse>>selecte_all_orders(
            @Field("id_user") String id
    );

    @FormUrlEncoded
    @POST("forgete_password_by_email")
    Call<ResponseBody>forgete_password_by_email(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("selecte_all_product_of_orders")
    Call<List<OrdersDetailsResponse>>selecte_all_product_of_orders(
            @Field("id_order") String id_order
    );

    @FormUrlEncoded
    @POST("user_profile")
    Call<ResponseBody>user_profile(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("select_all_product_by_category")
    Call<List<ProductsModel>>select_all_product_by_category(
            @Field("category") String category,
            @Field("id_user") String id_user
    );

    @GET("select_slider")
    Call<List<SliderModel>>slider();
}

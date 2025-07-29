package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.user.util.RetrofitUserRequestBuilder

class UserRemoteDataSourceImplRetrofit(
    private val retrofitRequestBuilder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
) : UserRemoteDataSource {


}

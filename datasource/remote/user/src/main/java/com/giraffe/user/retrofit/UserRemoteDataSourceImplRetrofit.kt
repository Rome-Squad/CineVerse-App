package com.giraffe.user.retrofit

import com.giraffe.repository.datasource.remote.UserRemoteDataSource
import com.giraffe.user.util.RetrofitUserRequestBuilder
import javax.inject.Inject

class UserRemoteDataSourceImplRetrofit @Inject constructor(
    private val retrofitRequestBuilder: RetrofitUserRequestBuilder<UserApiServiceRetrofit>
) : UserRemoteDataSource {


}

package com.helloworld.di;

import com.helloworld.data.local.SpaceDao;
import com.helloworld.data.remote.RetrofitServiceInterface;
import com.helloworld.data.repo.SpacexLaunchRepoImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitApiModule {
    @Provides
    public RetrofitServiceInterface provideInterface(Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient) {
        return retrofitBuilder.client(okHttpClient).build().create(RetrofitServiceInterface.class);
    }

    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        // interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()/*.addInterceptor(interceptor)*/.build();
    }

    @Provides
    public Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(RetrofitServiceInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    public SpacexLaunchRepoImpl provideRepository(RetrofitServiceInterface serviceInterface, SpaceDao dao) {
        return new SpacexLaunchRepoImpl(serviceInterface, dao);
    }
}

package com.Solver.Solver.Fragment;


import com.Solver.Solver.Notifications.MyResponse;
import com.Solver.Solver.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {


    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA8_44_8w:APA91bE3DDuHxC-32d2fWZSilNwWIVw2KOuYVp_-XBCy_ApfN3g4lxBOW45AySX6tkve75P5iIsvpGx9Gq7AZXhnDUlUn_TgJvTlei4RPPyyTWXwQKy-1VyttPH5xmbuHIiSdseW2HlO"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

/*Authorization:key=AAAA8_44_8w:APA91bE3DDuHxC-32d2fWZSilNwWIVw2KOuYVp_-XBCy_ApfN3g4lxBOW45AySX6tkve75P5iIsvpGx9Gq7AZXhnDUlUn_TgJvTlei4RPPyyTWXwQKy-1VyttPH5xmbuHIiSdseW2HlO*//*


*/

}

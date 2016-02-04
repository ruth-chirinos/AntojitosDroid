package com.delicoin.antojitos.utilities;

import com.delicoin.antojitos.model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RUTH on 16/01/03.
 */
public class FirebaseUtil
{
    public final static String FIREBASE_URL = "https://antojitos.firebaseio.com";


    /*Save Data*/
    public static void saveLoginUserData(final User user)
    {
        Firebase ref = new Firebase(FIREBASE_URL+"/users");
        ref.child(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Firebase ref = new Firebase(FIREBASE_URL+"/users/" + user.getId());
                if (dataSnapshot.exists()) {
                    Long countLogin = dataSnapshot.child("countLogin") != null ?
                            Long.parseLong(dataSnapshot.child("countLogin").getValue().toString()) : 0L;
                    Map<String, Object> countLoginMap = new HashMap<String, Object>();
                    Long count = countLogin + 1L;
                    countLoginMap.put("countLogin", count + "");
                    ref.updateChildren(countLoginMap);
                } else {
                    user.setCountLogin(1L);
                    ref.setValue(user);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

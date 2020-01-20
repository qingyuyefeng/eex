package com.eex.common.util;

import com.eex.home.bean.LoginUser;
import com.eex.home.bean.User;

import java.util.List;


public class UserUtil {

    private static User user;

    private static final String TAG = "UserUtil";

    public static void saveUser(User newUser) {
        if (newUser == null) {
            return;
        }
        user = newUser;
        String userString = JsonUtils.toJson(newUser);
        SharedPreferencesUtils.putShareData(TAG, userString);

    }


    public static User getUser() {
        if (user == null) {
            String userString = SharedPreferencesUtils.getShareData(TAG);
            User newUser = JsonUtils.fromJson(userString, User.class);
            user = newUser;
        }
        return user;
    }



    public static void clear() {
        user = null;
        SharedPreferencesUtils.putShareData(TAG, "");
    }


    public static String getID() {
        return getUser() == null ? null : getUser().getId();
    }

    public static String getTokenId() {
        return getUser() == null ? null : getUser().getTokenId();
    }

    public static String getUsername() {
        return getUser() == null ? null : getUser().getUsername();
    }

    public static String getPassword() {
        return getUser() == null ? null : getUser().getPassword();
    }

    public static String getAccountPassWord() {
        return getUser() == null ? null : getUser().getAccountPassWord();
    }

    public static int getStatus() {
        return getUser() == null ? null : getUser().getStatus();
    }

    public static String getEmail() {
        return getUser() == null ? null : getUser().getEmail();
    }

    public static Integer getRealName() {
        return getUser() == null ? null : getUser().getRealName();
    }

    public static String getSalt() {
        return getUser() == null ? null : getUser().getSalt();
    }

    public static String getUserCode() {
        return getUser() == null ? null : getUser().getUserCode();
    }

    public static int getHasEmail() {
        return getUser() == null ? null : getUser().getHasEmail();
    }

    public static int getHasPhone() {
        return getUser() == null ? null : getUser().getHasPhone();
    }

    public static String getPhone() {
        return getUser() == null ? null : getUser().getPhone();
    }

    public static List<LoginUser> getLogDTO() {
        return getUser() == null ? null : getLogDTO();
    }

    public static String getInvateCode() {
        return getUser() == null ? null : getUser().getInvateCode();
    }

    public static int getGoogleState() {
        return getUser() == null ? null : getUser().getGoogleState();
    }

    public static Integer getExamState() {
        return getUser() == null ? null : getUser().getExamState();
    }

    public static String getGoogleKey() {
        return getUser() == null ? null : getUser().getGoogleKey();
    }

    public static int getMerchant() {
        return getUser() == null ? null : getUser().getMerchant();
    }


}

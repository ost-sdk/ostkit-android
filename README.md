[![](https://jitpack.io/v/tinhuit89/OSTKitWrapperSDK.svg)](https://jitpack.io/#tinhuit89/OSTKitWrapperSDK) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

OSTKit is an api wrapper written in java and kotlin for [ost kit](https://ost.com/). The complete blockchain toolkit for business



**Volley:** [ https://developer.android.com/training/volley/](https://developer.android.com/training/volley/)

**Kotlin** [https://developer.android.com/kotlin/](https://developer.android.com/kotlin/)

**OST KIT⍺ API:** [https://dev.ost.com/docs/api.html](https://dev.ost.com/docs/api.html)


# OSTKitWrapperSDK

<img align='right' src='https://github.com/tinhuit89/OSTKitWrapperSDK/blob/master/art/OST-SDK.png' height='128'/>

==========

OST KIT⍺ API

Simple SDK solution.

### Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency

```groovy
dependencies {
	implementation 'com.github.tinhuit89:OSTKitWrapperSDK:1.0.2'
}
```

### Usage

### Initialize SDK

```java
OstWrapperSdk ostWrapperSdk = new OstWrapperSdk(context, API_KEY, SECRET);
```

### Use SDK function example

```java
ostWrapperSdk.getUserWrapper().getListUser(nextPage, new VolleyRequestCallback() {
            @Override
            public void callback(Context context, Boolean isSuccess, String result) {
                Log.d("tag", result);
                if (isSuccess) {
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        Boolean isSuccessApi = jsonResult.getBoolean("success");
                        if (isSuccessApi) {

                            JSONObject jsonData = jsonResult.getJSONObject("data");
                            JSONObject jsonMetaData = jsonData.getJSONObject("meta");
                            JSONObject jsonNextPagePayload = jsonMetaData.getJSONObject("next_page_payload");

                            ArrayList<UserModel> listUser = new ArrayList<>();
                            JSONArray arrayData = jsonData.getJSONArray("economy_users");
                            for (int i = 0; i < arrayData.length(); i++) {
                                UserModel itemModel = new UserModel();
                                itemModel.setData(arrayData.getJSONObject(i));
                                listUser.add(itemModel);
                            }

                            addListItem(listUser);

                            if (Utilities.checkKeyValid(jsonNextPagePayload, "page_no")) {
                                nextPage = jsonNextPagePayload.getInt("page_no");
                            } else {

                            }
                        } else {
                            
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                } else {
                   
                }
            }
        });

```

### UserWrapper:

```
- createUser
- editUser
- get
- ListUser
```

### TransactionTypeWrapper
```
- createTransactionType
- editTransactionType
- getListTransactionType
- executeTransactionType
- statusTransactionType
```

### AirDropWrapper

```
- executeAirdrop
- retrieveAirdrop
- getListAirDrop

```

## Author

OSTKit is owned by @vanductai and maintained by @tinhuit89

You can contact me at email [tinhvc89@gmail.com]()

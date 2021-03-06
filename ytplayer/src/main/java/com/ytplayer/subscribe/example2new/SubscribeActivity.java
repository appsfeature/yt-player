//package com.ytplayer.subscribe.example2new;
//
//import android.accounts.AccountManager;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
//import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
//import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.ExponentialBackOff;
//import com.google.api.services.youtube.YouTube;
//import com.google.api.services.youtube.model.ResourceId;
//import com.google.api.services.youtube.model.Subscription;
//import com.google.api.services.youtube.model.SubscriptionListResponse;
//import com.google.api.services.youtube.model.SubscriptionSnippet;
//import com.ytplayer.R;
//import com.ytplayer.util.YTConstant;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
//public class SubscribeActivity extends AppCompatActivity {
//    String TAG = SubscribeActivity.class.getSimpleName();
//
//    Context context;
//    ProgressBar progressBar;
//    GoogleAccountCredential mCredential;
//
//    static final int REQUEST_ACCOUNT_PICKER = 1000;
//    static final int REQUEST_AUTHORIZATION = 1001;
//    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
//    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
//
//    String youTubeAPIChannelId;
//    private static final String[] SCOPES = {"https://www.googleapis.com/auth/plus.me",
//            "https://www.googleapis.com/auth/youtube.force-ssl",
//            "https://www.googleapis.com/auth/youtubepartner",
//            "https://www.googleapis.com/auth/youtube"};
//
//    Button btnSubscribe;
//    private Type mRequestType;
//    private Subscription mSubscription;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube);
//        getBundle(getIntent());
//        try {
//            context = this;
//            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
//            btnSubscribe = (Button) findViewById(R.id.btn_subscribe);
//            btnSubscribe.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (btnSubscribe.getText().equals("Subscribe")) {
//                        mRequestType = Type.INSERT;
//                        subscribe();
//                    } else {
//                        mRequestType = Type.DELETE;
//                        subscribe();
//                    }
//                }
//            });
//
//            mCredential = GoogleAccountCredential.usingOAuth2(
//                    context.getApplicationContext(), Arrays.asList(SCOPES))
//                    .setBackOff(new ExponentialBackOff());
//
//        } catch (Exception ex) {
//            Log.e(TAG, ex.getMessage());
//        }
//        mRequestType = Type.LIST;
//        subscribe();
//    }
//
//    private void getBundle(Intent intent) {
//        try {
//            youTubeAPIChannelId = intent.getStringExtra(YTConstant.CHANNEL_ID);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void subscribe() {
//        if(!TextUtils.isEmpty(youTubeAPIChannelId)) {
//            if (!isGooglePlayServicesAvailable()) {
//                acquireGooglePlayServices();
//            } else if (mCredential.getSelectedAccountName() == null) {
//                chooseAccount();
//            } else if (!isDeviceOnline()) {
//                Log.d(TAG, "No network connection available.");
//            } else {
//                new MakeRequestTask(mCredential, mRequestType).execute();
//            }
//        }else {
//            Toast.makeText(this, "Invalid Youtube Channel Id", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability apiAvailability =
//                GoogleApiAvailability.getInstance();
//        final int connectionStatusCode =
//                apiAvailability.isGooglePlayServicesAvailable(context);
//        return connectionStatusCode == ConnectionResult.SUCCESS;
//    }
//
//    private void acquireGooglePlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
//        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
//        }
//    }
//
//    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        Dialog dialog = apiAvailability.getErrorDialog((Activity) context, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES);
//        dialog.show();
//    }
//
//    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
//    private void chooseAccount() {
//        if (EasyPermissions.hasPermissions(context, android.Manifest.permission.GET_ACCOUNTS)) {
//            startActivityForResult(mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
//        } else {
//            EasyPermissions.requestPermissions(this,
//                    "This app needs to access your Google account (via Contacts).",
//                    REQUEST_PERMISSION_GET_ACCOUNTS,
//                    android.Manifest.permission.GET_ACCOUNTS);
//        }
//    }
//
//    @SuppressLint("MissingPermission")
//    private boolean isDeviceOnline() {
//        ConnectivityManager connMgr =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_GOOGLE_PLAY_SERVICES:
//                if (resultCode != RESULT_OK) {
//                    Log.e(TAG, "This app requires Google Play Services. Please install " +
//                            "Google Play Services on your device and relaunch this app.");
//                } else {
//                    subscribe();
//                }
//                break;
//            case REQUEST_ACCOUNT_PICKER:
//                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
//                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
//                    if (accountName != null) {
//                        mCredential.setSelectedAccountName(accountName);
//                        subscribe();
//                    }
//                }
//                break;
//            case REQUEST_AUTHORIZATION:
//                if (resultCode == RESULT_OK) {
//                    subscribe();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    enum Type {
//        INSERT, DELETE, LIST
//    }
//
//    private class MakeRequestTask extends AsyncTask<Void, Void, Subscription> {
//        private YouTube mService = null;
//        private Exception mLastError = null;
//        private Type requestType;
//
//        MakeRequestTask(GoogleAccountCredential credential, Type requestType) {
//            this.requestType = requestType;
//            HttpTransport transport = AndroidHttp.newCompatibleTransport();
//            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//            mService = new YouTube.Builder(
//                    transport, jsonFactory, credential)
//                    .setApplicationName("Commodity Alert")
//                    .build();
//        }
//
//        @Override
//        protected Subscription doInBackground(Void... params) {
//            try {
//                Subscription subscription = getDataFromApi(requestType);
//                return subscription;
//            } catch (Exception e) {
//                mLastError = e;
//                Log.e(TAG, "Error: " + e.toString());
//                cancel(true);
//                return null;
//            }
//        }
//
//
//        private Subscription getDataFromApi(Type type) throws IOException {
//            ResourceId resourceId = new ResourceId();
//            resourceId.setChannelId(youTubeAPIChannelId);
//            resourceId.setKind("youtube#channel");
//
//            SubscriptionSnippet snippet = new SubscriptionSnippet();
//            snippet.setResourceId(resourceId);
//
//            Subscription subscription = new Subscription();
//            subscription.setSnippet(snippet);
//
//            Subscription returnedSubscription = null;
//            if (type == Type.INSERT) {
//                YouTube.Subscriptions.Insert subscriptionInsert = mService.subscriptions().insert("snippet,contentDetails", subscription);
//                returnedSubscription = subscriptionInsert.execute();
//            } else if (type == Type.DELETE) {
//                YouTube.Subscriptions.Delete subscriptionInsert = mService.subscriptions().delete(mSubscription.getId());
//                subscriptionInsert.execute();
//            } else if (type == Type.LIST) {
//                YouTube.Subscriptions.List channels = mService.subscriptions().list("id,snippet,contentDetails");
//                channels.setMine(true);
//                SubscriptionListResponse res = channels.execute();
//                List<Subscription> subscriptionList = res.getItems();
//                for (Subscription subs : subscriptionList) {
//                    if (subs.getSnippet().getChannelId().equalsIgnoreCase(youTubeAPIChannelId)) {
//                        returnedSubscription = subs;
//                        break;
//                    }
//                }
//            }
//
//
//            return returnedSubscription;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            showProgress();
//        }
//
//        @Override
//        protected void onPostExecute(Subscription output) {
//            hideProgress();
//            if (output == null || output.size() == 0) {
//                Log.e(TAG, "Not Subscribed");
//                if (requestType == Type.LIST) {
//                    btnSubscribe.setText("Subscribe");
//                }else {
//                    btnSubscribe.setText("UnSubscribed");
//                }
//            } else {
//                if (requestType == Type.LIST) {
//                    mSubscription = output;
//                    btnSubscribe.setText("Subscribed " + output.getSnippet().getTitle());
//                } else {
//                    btnSubscribe.setText("Subscribed " + output.getSnippet().getTitle());
//                    btnSubscribe.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                }
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            hideProgress();
//            if (mLastError != null) {
//                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
//                    showGooglePlayServicesAvailabilityErrorDialog(
//                            ((GooglePlayServicesAvailabilityIOException) mLastError)
//                                    .getConnectionStatusCode());
//                } else if (mLastError instanceof UserRecoverableAuthIOException) {
//                    startActivityForResult(((UserRecoverableAuthIOException) mLastError).getIntent(), REQUEST_AUTHORIZATION);
//                } else if (mLastError instanceof GoogleJsonResponseException) {
//                    Toast.makeText(context, "GoogleJsonResponseException code: "
//                            + ((GoogleJsonResponseException) mLastError).getDetails().getCode() + " : "
//                            + ((GoogleJsonResponseException) mLastError).getDetails().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.e(TAG, "The following error occurred:\n" + mLastError.getMessage());
//                }
//            } else {
//                Log.e(TAG, "Request cancelled.");
//            }
//        }
//
//    }
//
//    protected void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    protected void hideProgress() {
//        progressBar.setVisibility(View.GONE);
//    }
//
//}
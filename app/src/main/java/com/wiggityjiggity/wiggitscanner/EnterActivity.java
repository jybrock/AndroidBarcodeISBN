package com.wiggityjiggity.wiggitscanner;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.Drive;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
/**
 * Created by Joey Brock on 3/23/15.
 */
public class EnterActivity extends MainActivity implements OnClickListener {

    //goback, enter buttons
    private Button gobackBtn, enterBtn;
    //enter text field
    private EditText enterEdit;
    //author, title, description, date and rating count text views
    //private TextView authorText, titleText, descriptionText, dateText, ratingCountText, priceText, identifierText;
    //layout for star rating
    //private LinearLayout starLayout;
    //thumbnail
    //private ImageView thumbView;
    //star views
    //private ImageView[] starViews;
    //thumbnail bitmap
    //private Bitmap thumbImg;
    //coverpage bitmap
    //private Bitmap coverImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enter);
        gobackBtn = (Button)findViewById(R.id.goback_button);
        gobackBtn.setOnClickListener(this);
        enterBtn = (Button)findViewById(R.id.enter_button);
        enterBtn.setOnClickListener(this);
        enterEdit = (EditText)findViewById(R.id.enter_edit);

    }

    public void onClick(View v){
        //check for goback button
        if(v.getId()==R.id.goback_button){
            //instantiate ZXing integration class
            //IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //start scanning
            //scanIntegrator.initiateScan();
        }
        else if(v.getId()==R.id.enter_button){
            //get the url tag
        //    String tag = (String)v.getTag();
            //launch the url
        //    Intent webIntent = new Intent(Intent.ACTION_VIEW);
        //    webIntent.setData(Uri.parse(tag));
        //    startActivity(webIntent);
        }
        else if(v.getId()==R.id.enter_edit){
        //    String tag = (String)v.getTag();
            //launch preview
        //    Intent intent = new Intent(this, EmbeddedBook.class);
        //    intent.putExtra("isbn", tag);
        //    startActivity(intent);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    //class to fetch book info
    private class GetBookInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... bookURLs) {
            StringBuilder bookBuilder = new StringBuilder();
            for (String bookSearchURL : bookURLs) {
                HttpClient bookClient = new DefaultHttpClient();
                try {
                    //get the data
                    HttpGet bookGet = new HttpGet(bookSearchURL);
                    HttpResponse bookResponse = bookClient.execute(bookGet);
                    StatusLine bookSearchStatus = bookResponse.getStatusLine();
                    if (bookSearchStatus.getStatusCode()==200) {
                        //we have a result
                        HttpEntity bookEntity = bookResponse.getEntity();
                        InputStream bookContent = bookEntity.getContent();
                        InputStreamReader bookInput = new InputStreamReader(bookContent);
                        BufferedReader bookReader = new BufferedReader(bookInput);
                        String lineIn;
                        while ((lineIn=bookReader.readLine())!=null) {
                            bookBuilder.append(lineIn);
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return bookBuilder.toString();
        }
        protected void onPostExecute(String result) {
            try{
                //parse results
                //previewBtn.setVisibility(View.VISIBLE);
                //saveBtn.setVisibility(View.VISIBLE);
                //priceText.setEnabled(true);
                JSONObject resultObject = new JSONObject(result);
                JSONArray bookArray = resultObject.getJSONArray("items");
                JSONObject bookObject = bookArray.getJSONObject(0);
                JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

                //try for title
                //try{ titleText.setText("TITLE: "+volumeObject.getString("title")); }
                //catch(JSONException jse){
                //    titleText.setText("");
                //    jse.printStackTrace();
                //}
                //try for retail price

                //try{
                //    priceText.setText("PRICE: "+bookObject.getJSONObject("retailPrice").getString("amount"));
                //    if(priceText.toString()==""){
                //        priceText.setText("PRICE: ");
                //    }
                //}
                //catch(JSONException jse){
                //    priceText.setText("");
                //    jse.printStackTrace();
                //}
                //author can be multiple
                StringBuilder authorBuild = new StringBuilder("");
                try{
                    JSONArray authorArray = volumeObject.getJSONArray("authors");
                    for(int a=0; a<authorArray.length(); a++){
                        if(a>0) authorBuild.append(", ");
                        authorBuild.append(authorArray.getString(a));
                    }
                //    authorText.setText("AUTHOR(S): "+authorBuild.toString());
                }
                catch(JSONException jse){
                //    authorText.setText("");
                    jse.printStackTrace();
                }
                //publication date
                //try{ dateText.setText("PUBLISHED: "+volumeObject.getString("publishedDate")); }
                //catch(JSONException jse){
                //    dateText.setText("");
                //    jse.printStackTrace();
                //}
                //book description
                //try{ descriptionText.setText("DESCRIPTION: "+volumeObject.getString("description")); }
                //catch(JSONException jse){
                //    descriptionText.setText("");
                //    jse.printStackTrace();
                //}
                //star rating display
                //try{
                //    double decNumStars = Double.parseDouble(volumeObject.getString("averageRating"));
                //    int numStars = (int)decNumStars;
                //    starLayout.setTag(numStars);
                //    starLayout.removeAllViews();
                //    for(int s=0; s<numStars; s++){
                //        starViews[s].setImageResource(R.drawable.star);
                //        starLayout.addView(starViews[s]);
                //    }
                //}
                //catch(JSONException jse){
                //    starLayout.removeAllViews();
                //    jse.printStackTrace();
                //}
                //rating count
                //try{ //ratingCountText.setText(" - "+volumeObject.getString("ratingsCount")+" ratings"); }
                //catch(JSONException jse){
                //    ratingCountText.setText("");
                //    jse.printStackTrace();
                //}
                //preview availability
                try{
                    boolean isEmbeddable = Boolean.parseBoolean
                            (bookObject.getJSONObject("accessInfo").getString("embeddable"));
                //    if(isEmbeddable) previewBtn.setEnabled(true);
                //    else previewBtn.setEnabled(false);
                }
                catch(JSONException jse){
                //    previewBtn.setEnabled(false);
                    jse.printStackTrace();
                }
                //link button
                //try{
                //    linkBtn.setTag(volumeObject.getString("infoLink"));
                //    linkBtn.setVisibility(View.VISIBLE);
                //}
                //catch(JSONException jse){
                //    linkBtn.setVisibility(View.GONE);
                //    jse.printStackTrace();
                //}
                //image thumbnail
                try{
                    JSONObject imageInfo = volumeObject.getJSONObject("imageLinks");
                    new GetBookThumb().execute(imageInfo.getString("smallThumbnail")); }
                catch(JSONException jse){
                //    thumbView.setImageBitmap(null);
                    jse.printStackTrace();
                }
                //image coverpage
                try{
                    JSONObject coverInfo = volumeObject.getJSONObject("imageLinks");
                    new GetBookCover().execute(coverInfo.getString("extraLarge")); }
                catch(JSONException jse){
                //    thumbView.setImageBitmap(null);
                    jse.printStackTrace();
                }

            }
            catch (Exception e) {
                //no result
                e.printStackTrace();
            //    identifierText.setText("Oops! An Error Occurred :(");
            //    titleText.setText("NOT FOUND");
            //    authorText.setText("");
            //    descriptionText.setText("");
            //    dateText.setText("");
            //    priceText.setText("");
            //    starLayout.removeAllViews();
            //    ratingCountText.setText("");
            //    thumbView.setImageBitmap(null);
            //    previewBtn.setVisibility(View.GONE);
            //    saveBtn.setVisibility(View.GONE);
            }
        }
    }

    //class to fetch coverpage
    private class GetBookCover extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... coverURLs) {
            try{
                //attempt to download coverpage image using passed URL
                URL coverURL = new URL(coverURLs[0]);
                URLConnection coverConn = coverURL.openConnection();
                coverConn.connect();
                InputStream coverIn = coverConn.getInputStream();
                BufferedInputStream coverBuff = new BufferedInputStream(coverIn);
            //    coverImg = BitmapFactory.decodeStream(coverBuff);

                coverBuff.close();
                coverIn.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        protected void onPostExecute(String result) {
            //show the image
            //thumbView.setImageBitmap(coverImg);
            //thumbView.setImageBitmap(thumbImg);
        }
    }

    //class to fetch thumbnail
    private class GetBookThumb extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... thumbURLs) {
            try{
                //attempt to download thumbnail image using passed URL
                URL thumbURL = new URL(thumbURLs[0]);
                URLConnection thumbConn = thumbURL.openConnection();
                thumbConn.connect();
                InputStream thumbIn = thumbConn.getInputStream();
                BufferedInputStream thumbBuff = new BufferedInputStream(thumbIn);
            //    thumbImg = BitmapFactory.decodeStream(thumbBuff);

                thumbBuff.close();
                thumbIn.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        protected void onPostExecute(String result) {
            //show the image
        //    thumbView.setImageBitmap(thumbImg);
        }
    }
    //save state
    protected void onSaveInstanceState(Bundle savedBundle) {
    //    savedBundle.putString("identifier", ""+identifierText.getText());
    //    savedBundle.putString("title", ""+titleText.getText());
    //    savedBundle.putString("author", ""+authorText.getText());
    //    savedBundle.putString("description", ""+descriptionText.getText());
    //    savedBundle.putString("date", ""+dateText.getText());
    //    savedBundle.putString("ratings", ""+ratingCountText.getText());
    //    savedBundle.putString("price", ""+priceText.getText());
    //    savedBundle.putParcelable("thumbPic", thumbImg);
    //    savedBundle.putParcelable("coverpage", coverImg);

    //    if(starLayout.getTag()!=null)
    //        savedBundle.putInt("stars", Integer.parseInt(starLayout.getTag().toString()));
    //    savedBundle.putBoolean("isEmbed", previewBtn.isEnabled());
    //    savedBundle.putBoolean("isSave", saveBtn.isEnabled());
    //    savedBundle.putInt("isLink", linkBtn.getVisibility());
    //    if(previewBtn.getTag()!=null)
    //        savedBundle.putString("isbn", previewBtn.getTag().toString());

    }
}

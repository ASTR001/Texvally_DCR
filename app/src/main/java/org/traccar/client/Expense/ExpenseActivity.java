package org.traccar.client.Expense;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.traccar.client.DBHelper;
import org.traccar.client.MainActivity;
import org.traccar.client.R;
import org.traccar.client.Start;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ExpenseActivity extends AppCompatActivity {

    Spinner spin, AccLocationspinner;
    CardView AccommodationCard, TravelCard, myCard, misCard, courierCard;
    TextInputEditText IndateText, OutdateText, travelDate, fooddateText, courierdateText, simdateText;
    private DatePickerDialog mDatePickerDialog;
    private DatePickerDialog mDatePickerDialog2;
    private DatePickerDialog mDatePickerDialog3;
    private DatePickerDialog mDatePickerDialog4;
    private DatePickerDialog mDatePickerDialog5;
    private DatePickerDialog mDatePickerDialog6;
    String[] country = { "Select Expense","Accommodation", "Travel", "Food", "Courier", "Miscellaneous"};
    String[] ALocation = { "Select Location","Erode", "Tripur", "Salem"};
    Toolbar toolbar;
    EditText hotelTxt, locTxt, amtText, noteText;
    Button imageViewAccoum;
    Button btn001;
    String typee ="";
    String eid, hName, locc, amtt, nottt = "", idate, odate, reasonn;

    String type  = "type" ;
    String login_id  = "login_id" ;
    String hotel_name  = "hotel_name" ;
    String location  = "location" ;
    String check_in  = "check_in" ;
    String check_out  = "check_out" ;
    String amount  = "amount" ;
    String notes  = "notes" ;
    String image_data  = "image_data" ;
    String mode_of_travel  = "mode_of_travel" ;
    String date  = "date" ;
    String from_place  = "from_place" ;
    String to_place  = "to_place" ;
    String reason  = "reason" ;

    DBHelper dbHelper;
    String id,na,pa;


    EditText travelFloc,travelTloc,travenot, travelamt;
    Button imageViewTravel, TravelButton;
    private RadioGroup radioGup;
    private RadioButton radio_bus, radio_car, radio_two;
    String Traddd,TFlocc,TTlocc,Tnottt,Tamtt,Tdate, radiooo;


    EditText foodAmt, foodnott;
    Button imageViewFood, FoodButton;


    EditText courierReason, courierAmt, courierNott;
    Button courierImageBtn, courierBtn;

    EditText simReason, simAmount, simNott;
    Button simImageBtn, simBtn;


//    Button GetImageFromGalleryButton, UploadImageOnServerButton;

//    ImageView ShowSelectedImage;

    Bitmap FixBitmap;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    String GetImageNameFromEditText = "001";

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Expenses");

        spin = (Spinner) findViewById(R.id.spinner);
        radioGup=(RadioGroup)findViewById(R.id.radGupp);
        radio_bus=findViewById(R.id.radio_bus);
        radio_car=findViewById(R.id.radio_car);
        radio_two=findViewById(R.id.radio_two);
        AccLocationspinner = (Spinner) findViewById(R.id.AccLocationspinner);
        courierdateText = findViewById(R.id.courierdateText);
        simdateText = findViewById(R.id.simdateText);
        simBtn = findViewById(R.id.simBtn);
        simNott = findViewById(R.id.simNott);
        simImageBtn = findViewById(R.id.simImageBtn);
        simReason = findViewById(R.id.simReason);
        simAmount = findViewById(R.id.simAmount);
        courierImageBtn = findViewById(R.id.courierImageBtn);
        courierBtn = findViewById(R.id.courierBtn);
        imageViewAccoum = findViewById(R.id.imageView);
        imageViewTravel = findViewById(R.id.imageViewTravel);
        imageViewFood = findViewById(R.id.imageViewFood);
        courierReason = findViewById(R.id.courierReason);
        courierAmt = findViewById(R.id.courierAmt);
        courierNott = findViewById(R.id.courierNott);
        btn001 = findViewById(R.id.btn001);
        TravelButton = findViewById(R.id.TravelButton);
        fooddateText = findViewById(R.id.fooddateText);
        FoodButton = findViewById(R.id.FoodButton);
        locTxt = findViewById(R.id.locTxt);
        foodnott = findViewById(R.id.foodnott);
        foodAmt = findViewById(R.id.foodAmt);
        travelFloc = findViewById(R.id.travelFloc);
        travelTloc = findViewById(R.id.travelTloc);
        travelamt = findViewById(R.id.travelamt);
        travenot = findViewById(R.id.travenot);
        noteText = findViewById(R.id.noteText);
        hotelTxt = findViewById(R.id.hotelTxt);
        amtText = findViewById(R.id.amtText);
        TravelCard = findViewById(R.id.TravelCard);
        courierCard = findViewById(R.id.courierCard);
        misCard = findViewById(R.id.misCard);
        AccommodationCard = findViewById(R.id.AccommodationCard);
        myCard = findViewById(R.id.myCard);
        IndateText = findViewById(R.id.indateText);
        OutdateText = findViewById(R.id.outdateText);
        travelDate = findViewById(R.id.travelDate);


        dbHelper=new DBHelper(getApplicationContext());
        Cursor res = dbHelper.GetSQLiteDatabaseRecords();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

//        Toast.makeText(this, ""+na, Toast.LENGTH_SHORT).show();

        setDateTimeField();
        IndateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

        setDateTimeField2();
        OutdateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog2.show();
                return false;
            }
        });

        setDateTimeField3();
        travelDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog3.show();
                return false;
            }
        });

        setDateTimeField4();
        fooddateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog4.show();
                return false;
            }
        });

        setDateTimeField5();
        courierdateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog5.show();
                return false;
            }
        });

        setDateTimeField6();
        courierdateText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog6.show();
                return false;
            }
        });

        imageViewAccoum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        imageViewTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        courierImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        simImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
        
        

        btn001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hName = hotelTxt.getText().toString();
                hName = hName.replaceAll(" ", "%20");
                locc = locTxt.getText().toString();
                locc = locc.replaceAll(" ", "%20");
                nottt = noteText.getText().toString();
                nottt = nottt.replaceAll(" ", "%20");
                amtt = amtText.getText().toString();
                idate = IndateText.getText().toString();
                odate = OutdateText.getText().toString();
                UploadImageToServer();
            }
        });

        if (ContextCompat.checkSelfPermission(ExpenseActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }


        TravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radio_bus.isChecked()) {
                    radiooo = "Bus";
                } else if (radio_car.isChecked()) {
                    radiooo = "Car";
                } else if (radio_two.isChecked()) {
                    radiooo = "Two wheeler";
                    radiooo = radiooo.replaceAll(" ", "%20");
                }

                Tdate = travelDate.getText().toString();
                TFlocc = travelFloc.getText().toString();
                TFlocc = TFlocc.replaceAll(" ", "%20");
                TTlocc = travelTloc.getText().toString();
                TTlocc = TTlocc.replaceAll(" ", "%20");
                Tnottt = travenot.getText().toString();
                Tnottt = Tnottt.replaceAll(" ", "%20");
                Tamtt = travelamt.getText().toString();
                UploadImageToServerTravel(radiooo);
            }
        });

        FoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tdate = fooddateText.getText().toString();
                Tnottt = foodnott.getText().toString();
                Tnottt = Tnottt.replaceAll(" ", "%20");
                Tamtt = foodAmt.getText().toString();
                UploadImageToServerFood();
            }
        });

        courierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tdate = courierdateText.getText().toString();
                Tnottt = courierNott.getText().toString();
                Tnottt = Tnottt.replaceAll(" ", "%20");
                reasonn = courierReason.getText().toString();
                reasonn = reasonn.replaceAll(" ", "%20");
                Tamtt = courierAmt.getText().toString();
                UploadImageToServerCourier();
            }
        });

        simBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tdate = simdateText.getText().toString();
                Tnottt = simNott.getText().toString();
                Tnottt = Tnottt.replaceAll(" ", "%20");
                reasonn = simReason.getText().toString();
                reasonn = reasonn.replaceAll(" ", "%20");
                Tamtt = simAmount.getText().toString();
                UploadImageToServerSim();
            }
        });

        spin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.BLACK);

                if (spin.getSelectedItem().equals("Accommodation")) {
                    AccommodationCard.setVisibility(View.VISIBLE);
                    TravelCard.setVisibility(View.GONE);
                    myCard.setVisibility(View.GONE);
                    courierCard.setVisibility(View.GONE);
                    misCard.setVisibility(View.GONE);
                    typee = "1";
//                    Toast.makeText(ExpenseActivity.this, "1", Toast.LENGTH_SHORT).show();
                } else if (spin.getSelectedItem().equals("Travel")) {
                    AccommodationCard.setVisibility(View.GONE);
                    TravelCard.setVisibility(View.VISIBLE);
                    courierCard.setVisibility(View.GONE);
                    misCard.setVisibility(View.GONE);
                    myCard.setVisibility(View.GONE);
                    typee = "2";
//                    Toast.makeText(ExpenseActivity.this, "2", Toast.LENGTH_SHORT).show();
                }  else if (spin.getSelectedItem().equals("Food")) {
                    AccommodationCard.setVisibility(View.GONE);
                    TravelCard.setVisibility(View.GONE);
                    myCard.setVisibility(View.VISIBLE);
                    courierCard.setVisibility(View.GONE);
                    misCard.setVisibility(View.GONE);
                    typee = "3";
//                    Toast.makeText(ExpenseActivity.this, "3", Toast.LENGTH_SHORT).show();
                } else if (spin.getSelectedItem().equals("Courier")) {
                    AccommodationCard.setVisibility(View.GONE);
                    TravelCard.setVisibility(View.GONE);
                    myCard.setVisibility(View.GONE);
                    courierCard.setVisibility(View.VISIBLE);
                    misCard.setVisibility(View.GONE);
                    typee = "4";
//                    Toast.makeText(ExpenseActivity.this, "4", Toast.LENGTH_SHORT).show();
                } else if (spin.getSelectedItem().equals("Miscellaneous")) {
                    AccommodationCard.setVisibility(View.GONE);
                    TravelCard.setVisibility(View.GONE);
                    myCard.setVisibility(View.GONE);
                    courierCard.setVisibility(View.GONE);
                    misCard.setVisibility(View.VISIBLE);
                    typee = "5";
//                    Toast.makeText(ExpenseActivity.this, "5", Toast.LENGTH_SHORT).show();
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        AccLocationspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.BLACK);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ALocation);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        AccLocationspinner.setAdapter(aaa);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                IndateText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField2() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                OutdateText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField3() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog3 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                travelDate.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField4() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog4 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                fooddateText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField5() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog5 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                courierdateText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void setDateTimeField6() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog6 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                simdateText.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
//                    ShowSelectedImage.setImageBitmap(FixBitmap);
//                    UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ExpenseActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
//            ShowSelectedImage.setImageBitmap(FixBitmap);
//            UploadImageOnServerButton.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadImageToServer(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ExpenseActivity.this,"Process Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

//                Toast.makeText(MediaActivity.this,string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(type , typee);
                HashMapParams.put(login_id, na);
                HashMapParams.put(hotel_name, hName);
                HashMapParams.put(location, locc);
                HashMapParams.put(check_in, idate);
                HashMapParams.put(check_out, odate);
                HashMapParams.put(amount, amtt);
                HashMapParams.put(notes, nottt);
                HashMapParams.put(image_data, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest("https://b2b.texvalleyb2b.in/api_dcr/send_expenses.php?", HashMapParams);

                Log.e("hbwkje1", String.valueOf(HashMapParams));

                Intent intenttt = new Intent(ExpenseActivity.this, Start.class);
                startActivity(intenttt);
//                Toast.makeText(MediaActivity.this, ""+FinalData, Toast.LENGTH_SHORT).show();

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void UploadImageToServerTravel(final String mthodd) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ExpenseActivity.this,"Process Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

//                Toast.makeText(MediaActivity.this,string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams2 = new HashMap<String,String>();

                HashMapParams2.put(type , typee);
                HashMapParams2.put(login_id, na);
                HashMapParams2.put(from_place, TFlocc);
                HashMapParams2.put(to_place, TTlocc);
                HashMapParams2.put(mode_of_travel, mthodd);
                HashMapParams2.put(amount, Tamtt);
                HashMapParams2.put(notes, Tnottt);
                HashMapParams2.put(date, Tdate);
                HashMapParams2.put(image_data, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest("https://b2b.texvalleyb2b.in/api_dcr/send_expenses.php?", HashMapParams2);

                Log.e("hbwkje1", String.valueOf(HashMapParams2));
//                Toast.makeText(MediaActivity.this, ""+FinalData, Toast.LENGTH_SHORT).show();

                Intent intenttt = new Intent(ExpenseActivity.this, Start.class);
                startActivity(intenttt);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }
    
    
    public void UploadImageToServerFood(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ExpenseActivity.this,"Process Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

//                Toast.makeText(MediaActivity.this,string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(type , typee);
                HashMapParams.put(login_id, na);
                HashMapParams.put(amount, Tamtt);
                HashMapParams.put(notes, Tnottt);
                HashMapParams.put(date, Tdate);
                HashMapParams.put(image_data, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest("https://b2b.texvalleyb2b.in/api_dcr/send_expenses.php?", HashMapParams);

                Log.e("hbwkje1", String.valueOf(HashMapParams));
//                Toast.makeText(MediaActivity.this, ""+FinalData, Toast.LENGTH_SHORT).show();
                Intent intenttt = new Intent(ExpenseActivity.this, Start.class);
                startActivity(intenttt);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }


    public void UploadImageToServerCourier(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ExpenseActivity.this,"Process Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

//                Toast.makeText(MediaActivity.this,string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(type , typee);
                HashMapParams.put(login_id, na);
                HashMapParams.put(reason, reasonn);
                HashMapParams.put(amount, Tamtt);
                HashMapParams.put(notes, Tnottt);
                HashMapParams.put(date, Tdate);
                HashMapParams.put(image_data, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest("https://b2b.texvalleyb2b.in/api_dcr/send_expenses.php?", HashMapParams);

                Log.e("hbwkje1", String.valueOf(HashMapParams));
                Intent intenttt = new Intent(ExpenseActivity.this, Start.class);
                startActivity(intenttt);
//                Toast.makeText(MediaActivity.this, ""+FinalData, Toast.LENGTH_SHORT).show();

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void UploadImageToServerSim(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(ExpenseActivity.this,"Process Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

//                Toast.makeText(MediaActivity.this,string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(type , typee);
                HashMapParams.put(login_id, na);
                HashMapParams.put(reason, reasonn);
                HashMapParams.put(amount, Tamtt);
                HashMapParams.put(notes, Tnottt);
                HashMapParams.put(date, Tdate);
                HashMapParams.put(image_data, ConvertImage);


                String FinalData = imageProcessClass.ImageHttpRequest("https://b2b.texvalleyb2b.in/api_dcr/send_expenses.php?", HashMapParams);

                Log.e("hbwkje1", String.valueOf(HashMapParams));
                Intent intenttt = new Intent(ExpenseActivity.this, Start.class);
                startActivity(intenttt);
//                Toast.makeText(MediaActivity.this, ""+FinalData, Toast.LENGTH_SHORT).show();

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }




    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            }
            else {

                Toast.makeText(ExpenseActivity.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
package com.Solver.Solver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowUserDetails extends AppCompatActivity {

    CircleImageView imageView;
    TextView nametv,professiontv,emailtv,phonetv,departmentTv;
    Button callbtn,smsbtn,emailbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_details);

        nametv=findViewById(R.id.user_show_name);
        departmentTv=findViewById(R.id.user_show_DepartmentId);
        professiontv=findViewById(R.id.user_show_passion);
        emailtv=findViewById(R.id.user_show_email);
        phonetv=findViewById(R.id.user_show_phone);
        callbtn=findViewById(R.id.usercallBtnId);
        smsbtn=findViewById(R.id.usersmsBtnId);
        emailbtn=findViewById(R.id.useremailBtnId);
        imageView=findViewById(R.id.user_imageId);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String   passion=intent.getStringExtra("designation");
        String   department=intent.getStringExtra("department");
        final String email=intent.getStringExtra("email");
        final String phone=intent.getStringExtra("phone");
        String image=intent.getStringExtra("image");

        Glide.with(getApplicationContext()).load(image).into(imageView);
        nametv.setText(name);
        professiontv.setText(passion);
        emailtv.setText(email);
        phonetv.setText(phone);
        departmentTv.setText(department);

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + phone.trim() ;
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(uri));
                startActivity(i);

            }
        });

        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone.trim(); // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.fromParts("sms", number, null)));

            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress=email.trim();
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailAddress});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");
//need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

    }
}

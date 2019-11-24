package com.Solver.Solver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientShowDetails extends AppCompatActivity {

    private TextView comNameTv;
    private TextView comAddressTv;
    private TextView consultantTv;
    private TextView emailTv;
    private TextView Phone;
    private Button callBtn;
    private Button smsBtn;
    private Button emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_show_details);
        comNameTv=findViewById(R.id.company_name);
        comAddressTv=findViewById(R.id.companyAddressId);
        consultantTv=findViewById(R.id.consultId);
        emailTv=findViewById(R.id.companyEmail);
        Phone=findViewById(R.id.companyPhoneId);
        callBtn=findViewById(R.id.comCallBtnId);
        smsBtn=findViewById(R.id.comSmsBtnId);
        emailBtn=findViewById(R.id.comEmailBtnId);

        Intent intent=getIntent();
        String comName=intent.getStringExtra("comName");
        final String comAddress=intent.getStringExtra("comAddress");
        String comConsultant=intent.getStringExtra("consultant");
        final String comEmail=intent.getStringExtra("comEmail");
        final String comPhone=intent.getStringExtra("comPhone");

        comNameTv.setText(comName);
        comAddressTv.setText(comAddress);
        comAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent locationIntent=new Intent(Intent.ACTION_VIEW);
                    locationIntent.setData(Uri.parse("geo:0,0?q="+comAddress));
                    startActivity(locationIntent);
            }
        });
        consultantTv.setText(comConsultant);
        emailTv.setText(comEmail);
        Phone.setText(comPhone);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel:" + comPhone.trim() ;
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(uri));
                startActivity(i);
            }
        });

        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = comPhone.trim(); // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.fromParts("sms", number, null)));

            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress=comEmail.trim();
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

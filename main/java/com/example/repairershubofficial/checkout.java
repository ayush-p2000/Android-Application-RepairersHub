package com.example.repairershubofficial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class checkout extends AppCompatActivity {
    TextView service, name, email, address, contact, problem;
    Button confirm;
    int pageWidth = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        service = findViewById(R.id.serviceTxt);
        name = findViewById(R.id.customername);
        email = findViewById(R.id.email);
        address = findViewById(R.id.fulladdress);
        contact = findViewById(R.id.phonenumber);
        problem = findViewById(R.id.problem);
        confirm = findViewById(R.id.button);


        Intent in = getIntent();
        String num = in.getStringExtra("NUMBER");
        String nam = in.getStringExtra("NAME");
        String addr = in.getStringExtra("ADDRESS");
        String mail = in.getStringExtra("EMAIL");
        String pdes = in.getStringExtra("DES");
        String ser = in.getStringExtra("SERVICE");

        service.setText(ser);
        name.setText(nam);
        email.setText(mail);
        address.setText(addr);
        contact.setText(num);
        problem.setText(pdes);

        String _txtMessage = "Name : " + nam + "\n\n" + "Email : " + mail + "\n\n" + "Address : " + addr + "\n\n" + "Service : " + ser + "\n\n" + "Problem : " + pdes + "\n\n" + "Contact :" + num + "\n\n";

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String senderEmail = "repairershub@gmail.com";
                    final String password = "repairershub123";
                    String recieverEmail = mail;
                    String stringHost = "smtp.gmail.com";
                    Properties properties = System.getProperties();

                    properties.put("mail.smtp.host", stringHost);
                    properties.put("mail.smtp.port", "465");
                    properties.put("mail.smtp.ssl.enable", "true");
                    properties.put("mail.smtp.auth", "true");

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, password);
                        }
                    });

                    MimeMessage mimeMessage = new MimeMessage(session);

                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmail));

                    mimeMessage.setSubject("Thankyou For Choosing Repairer's Hub!");
                    mimeMessage.setText("Your Booking Details \n\n\n" + _txtMessage + "\n\n" + "Your Repairer will shortly contact you. We are happy to serve you :) !!");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Transport.send(mimeMessage);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    Intent in = new Intent(getApplicationContext(), Thankyou.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Thread.sleep(3500);
                    startActivity(in);
                    Toast.makeText(checkout.this, "Booking Successful!", Toast.LENGTH_SHORT).show();

                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
package com.example.gizem.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    static final int PICK_CONTACT_REQUEST=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContactDB db = new ContactDB(getApplicationContext());
        final List<Contact> contactList;


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Touch to Add for New", Snackbar.LENGTH_LONG)
                        .setAction("'Contact'", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                            }
                        }).show();

            }
        });


        list = (ListView)findViewById(R.id.listView);

        db = getAllContacts(db);
        Log.e("on create ici","selam");
       contactList = showAllContacts(list, db);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                araDialog dialog = new araDialog(MainActivity.this, contactList.get(position).getNumber());
                //  Log.e("No :",contactArrayList.get(position).getNumber());
                dialog.show();
            }
        });


    }

    public ContactDB getAllContacts(ContactDB db)
    {
        db.getWritableDatabase();
        db.deleteRows();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (phones.moveToNext())
        {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ;
            Cursor managerCursor = managedQuery(CallLog.Calls.CONTENT_URI,null,null,null,null);
            int duration = managerCursor.getColumnIndex(CallLog.Calls.DURATION);
            int numbers = managerCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managerCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managerCursor.getColumnIndex(CallLog.Calls.DATE);

            while (managerCursor.moveToNext()) {
                String phNum = managerCursor.getString(numbers);
                String callType = managerCursor.getString(type);
                String callDuration = managerCursor.getString(duration);
                int callcode = Integer.parseInt(callType);
                switch (callcode)
                {
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callType = "INCOMING";
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "MISSED";
                }
                Log.e("type",String.valueOf(callType));
                Log.e("number: ",String.valueOf(phNum));
                Log.e("duration",String.valueOf(callDuration));

            }

            //System.out.println(number);
            // int i = Integer.parseInt(number);
            //  System.out.println(i);
            Contact c = new Contact(name,number);
            db.addContact(c);


        }

        phones.close();
        return db;
    }

    public List<Contact> showAllContacts(ListView list, ContactDB db)
    {
        List<Contact> contactList = db.getAllContactList();
        Log.e("metod ici","BOK");

        Log.e("contact list",String.valueOf(contactList));
        ContactAdapter adapter =new ContactAdapter(this,contactList);
        Log.e("metod ici : ", String.valueOf(adapter.getCount()));
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        return contactList;

    }

    public class araDialog extends Dialog {
        String number;
        ImageView call,message,statistics;

        public araDialog(Context context, final String number) {
            super(context);
            this.number=number;
            setContentView(R.layout.dialog_layout);

            final LinearLayout sendLayout = (LinearLayout)findViewById(R.id.sendlayout);
            final LinearLayout choiceLayout = (LinearLayout)findViewById(R.id.choiceLayout);

            call= (ImageView)findViewById(R.id.button);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("number", number);
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + number));
                    Uri no = intent.getData();
                    System.out.println(no);
                    startActivity(intent);
                }
            });
            message = (ImageView)findViewById(R.id.button2);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sendLayout.getVisibility()==View.INVISIBLE)
                        sendLayout.setVisibility(View.VISIBLE);
                    choiceLayout.setVisibility(View.INVISIBLE);
                    Button send = (Button)findViewById(R.id.sentButton);
                    final EditText editText = (EditText)findViewById(R.id.editText);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(number, null, editText.getText().toString(), null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_SHORT).show();
                            sendLayout.setVisibility(View.INVISIBLE);
                            choiceLayout.setVisibility(View.VISIBLE);
                          /*  Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("smsto:"+number));
                            intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                            startActivity(intent);*/

                        }
                    });

                }
            });

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


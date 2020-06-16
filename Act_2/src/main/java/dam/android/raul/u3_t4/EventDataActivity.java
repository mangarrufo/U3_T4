package dam.android.raul.u3_t4;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private TextView tvEventName;
    private RadioGroup rgPriority;
    private Button btAccept;
    private Button btCancel;
    private Button btsetTime;
    private Button btsetData;
    private EditText etPlace;
    private String priority ="Normal";
    private TimePickerFragment tpDialog;
    private DataPickerFragment dpDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        setUI();

        Bundle inputData = getIntent().getExtras();
        tvEventName.setText(inputData.getString("EventName"));

    }


    private void setUI()
    {
        tvEventName = findViewById(R.id.tvEventName);
        rgPriority = findViewById(R.id.rgPriority);

        btAccept=findViewById(R.id.btaccept);
        btCancel= findViewById(R.id.btCancel);
        btsetTime=findViewById(R.id.btsetTime);
        btsetData=findViewById(R.id.btsetdata);
        btAccept.setOnClickListener(this);
        btCancel.setOnClickListener(this);

        etPlace= findViewById(R.id.etPlace);

        rgPriority.setOnCheckedChangeListener(this);
        rgPriority.check(R.id.rbnormal);

        tpDialog = new TimePickerFragment();
        dpDialog = new DataPickerFragment();
    }


    @Override
    public void onClick(View v)
    {
        Intent activityResult=new Intent();
        Bundle eventData = new Bundle();
        switch(v.getId())
        {
            case R.id.btaccept:
                Resources res = getResources();
                String[] month = res.getStringArray(R.array.month);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    eventData.putString("EventData","Place:"+etPlace.getText()+"\nPriority: "+priority+"\n "+"Month: "+month[dpDialog.getMonth()]+"\n "+"Day: "+dpDialog.getDay()+"\n "+"Year: "+dpDialog.getYear()+"\nHour: "+tpDialog.getHour()+":"+tpDialog.getMinute());
                }else{
                    eventData.putString("EventData","Place:"+etPlace.getText()+"\nPriority: "+priority+"\n "+"Month: "+month[dpDialog.getMonth()]+"\n "+"Day: "+dpDialog.getDay()+"\n "+"Year: "+dpDialog.getYear());
                }
                setResult(RESULT_OK,activityResult);
                break;
            case R.id.btCancel:
                setResult(RESULT_CANCELED,activityResult);
                break;
        }
        activityResult.putExtras(eventData);

        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch(checkedId)
        {
            case R.id.rblow:
                priority=getString(R.string.rbLow);
                break;
            case R.id.rbnormal:
                priority=getString(R.string.rbNormal);
                break;
            case R.id.rbhigh:
                priority=getString(R.string.rbHigh);
                break;
        }
    }

    public void showTimePickerDialog(View v)
    {
        boolean canceld = true;
        tpDialog.show(getSupportFragmentManager(), "timePicker");
        btsetTime.setText(tpDialog.getHour()+":"+tpDialog.getMinute());
    }

    public void showDatePickerDialog(View v)
    {
        dpDialog.show(getSupportFragmentManager(), "datePicker");
        btsetData.setText(dpDialog.getDay()+"/"+dpDialog.getMonth()+"/"+dpDialog.getYear());
    }

    //Todo: Aqui hacemos que al dar la vuelta a nuestro activity guarde los datos
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       int [] calendario = {dpDialog.getYear(),dpDialog.getMonth(),dpDialog.getDay()};
        outState.putIntArray("momento",calendario);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int [] hora = {tpDialog.getHour(),tpDialog.getMinute()};
            outState.putIntArray("hora",hora);
        }
    }

    //Todo: Aqui hacemos que nos restaure los datos del calendario y la hora
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int [] calendario = savedInstanceState.getIntArray("momento");
        dpDialog.setDay(calendario[2]);
        dpDialog.setMonth(calendario[1]);
        dpDialog.setYear(calendario[0]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int [] hora = savedInstanceState.getIntArray("hora");
            tpDialog.setHour(hora[0]);
            tpDialog.setMinute(hora[1]);
        }

    }
}

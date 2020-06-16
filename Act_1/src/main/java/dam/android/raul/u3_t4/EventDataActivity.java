package dam.android.raul.u3_t4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private TextView tvEventName;
    private RadioGroup rgPriority;
    private DatePicker dpDate;
    private TimePicker tpTime;
    private Button btAccept;
    private Button btCancel;
    private EditText etPlace;
    private String priority = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        setUI();

        Bundle inputData = getIntent().getExtras();
        tvEventName.setText(inputData.getString("EventName"));

    }


    private void setUI() {
        tvEventName = findViewById(R.id.tvEventName);
        rgPriority = findViewById(R.id.rgPriority);
        dpDate = findViewById(R.id.dpDate);
        tpTime = findViewById(R.id.tptime);
        tpTime.setIs24HourView(true);
        btAccept = findViewById(R.id.btaccept);
        btCancel = findViewById(R.id.btCancel);
        etPlace = findViewById(R.id.etPlace);

        btAccept.setOnClickListener(this);
        btCancel.setOnClickListener(this);

        rgPriority.setOnCheckedChangeListener(this);
        rgPriority.check(R.id.rbnormal);
    }


    @Override
    public void onClick(View v) {
        Intent activityResult = new Intent();
        Bundle eventData = new Bundle();
        switch (v.getId()) {
            case R.id.btaccept:
                Resources res = getResources();
                String[] month = res.getStringArray(R.array.month);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    eventData.putString("EventData", "Place:" + etPlace.getText() + "\nPriority: " + priority + "\n " + "Month: " + month[dpDate.getMonth()] + "\n " + "Day: " + dpDate.getDayOfMonth() + "\n " + "Year: " + dpDate.getYear() + "\nHour: " + tpTime.getHour() + ":" + tpTime.getMinute());
                } else {
                    eventData.putString("EventData", "Place:" + etPlace.getText() + "\nPriority: " + priority + "\n " + "Month: " + month[dpDate.getMonth()] + "\n " + "Day: " + dpDate.getDayOfMonth() + "\n " + "Year: " + dpDate.getYear());
                }
                setResult(RESULT_OK, activityResult);
                break;
            case R.id.btCancel:
                setResult(RESULT_CANCELED, activityResult);
                break;
        }
        activityResult.putExtras(eventData);

        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rblow:
                priority = getString(R.string.rbLow);
                break;
            case R.id.rbnormal:
                priority = getString(R.string.rbNormal);
                break;
            case R.id.rbhigh:
                priority = getString(R.string.rbHigh);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int[] calendario = {dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth()};
        outState.putIntArray("momento", calendario);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int[] hora = {tpTime.getHour(), tpTime.getMinute()};
            outState.putIntArray("hora", hora);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int[] calendario = savedInstanceState.getIntArray("momento");
        dpDate.init(calendario[0], calendario[1], calendario[2], null);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int[] hora = savedInstanceState.getIntArray("hora");
            tpTime.setHour(hora[0]);
            tpTime.setMinute(hora[1]);
        }

    }
}

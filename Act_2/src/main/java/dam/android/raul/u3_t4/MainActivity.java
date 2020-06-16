package dam.android.raul.u3_t4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private final int REQUEST=0;

    private EditText etEventName;
    private TextView tvCurrentData;
    private  boolean restaurar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    private void setUI()
    {
        etEventName = findViewById(R.id.etEventName);
        tvCurrentData= findViewById(R.id.tvCurrentDataTittle);
        restaurar=false;
    }

    public void editEventData(View v)
    {
        Intent intent = new Intent(this,EventDataActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString("EventName",etEventName.getText().toString());
        intent.putExtras(bundle);

        startActivityForResult(intent,REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST && resultCode == RESULT_OK){
            tvCurrentData.setText(data.getStringExtra("EventData"));


            restaurar=true;
        } else{
            restaurar = false;
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("Current",tvCurrentData.getText().toString());

    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        if(!restaurar)
            tvCurrentData.setText(savedInstanceState.getString("Current"));
    }
}

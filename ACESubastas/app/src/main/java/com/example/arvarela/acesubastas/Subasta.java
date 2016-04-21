package com.example.arvarela.acesubastas;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Subasta extends AppCompatActivity {
    Button buttonOfertar;
    TextView textView, et2,et3;
    int resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subasta);

        buttonOfertar=(Button) findViewById(R.id.buttonOfertarSubasta);

        textView=(TextView) findViewById(R.id.textViewCronometroSubasta);
        textView.setText("00:00:10");

        final CounterClass timer= new CounterClass(10000,1000);
        buttonOfertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                Suma();

            }
        });
    }

    public void Suma(){
        et3 = (TextView) findViewById(R.id.textViewValorAcumulado);
        et2 = (TextView) findViewById(R.id.textViewValorOfertarSubasta);

        int aux3 = Integer.valueOf(et3.getText().toString());
        int aux2 = Integer.valueOf(et2.getText().toString());

        resultado = aux2 + aux3;
        et3.setText(String.valueOf(resultado));

    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long millis=millisUntilFinished;
            String hms=String.format("%02d:%02d:%02d", TimeUnit.MICROSECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.print(hms);
            textView.setText(hms);
        }
        @Override
        public void onFinish() {
            textView.setText("Has Ganado la Subasta en: "+resultado);
            buttonOfertar.setEnabled(false);
        }
    }
}

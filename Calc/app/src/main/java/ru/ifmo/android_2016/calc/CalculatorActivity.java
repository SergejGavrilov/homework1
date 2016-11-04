package ru.ifmo.android_2016.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alexey.nikitin on 13.09.16.
 */

public final class CalculatorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Button button = (Button) findViewById(R.id.d0);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        int childCount = gridLayout.getChildCount();
        ButtonClickHandler buttonClickHandler = new ButtonClickHandler();
        for (int i = 0; i < childCount; i++) {
            View v = gridLayout.getChildAt(i);
            if (v instanceof Button) {
                v.setOnClickListener(buttonClickHandler);
            }

        }
    }

    public class ButtonClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView textOutputScreen = (TextView) findViewById(R.id.textViewOutputScreen);
            if (view instanceof Button) {
                Button buttonClicked = (Button) view;

                //Check If Clear is pressed
                if (view.getId() == R.id.clear) {
                    textOutputScreen.setText("0");

                    //Check If Equal is pressed
                } else if (view.getId() == R.id.eqv) {
                    try {
                        double calcResult = CalcUtils.eval(textOutputScreen.getText().toString());
                        textOutputScreen.setText(Double.toString(calcResult));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        textOutputScreen.setText("0");
                    }
                    //Show message in case of wrong input, where two math operators stand together in a row
                } else if (textOutputScreen.length() > 0 &&
                        CalcUtils.isOperator(textOutputScreen.getText().charAt(textOutputScreen.length() - 1)) &&
                        CalcUtils.isOperator(buttonClicked.getText().charAt(0))) {

                    String error = "Two operators in a row: " +
                            textOutputScreen.getText().charAt(textOutputScreen.length() - 1) +
                            "  and  " + buttonClicked.getText();
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                } else {
                    if (textOutputScreen.getText().equals("0") &&
                            !CalcUtils.isOperator(buttonClicked.getText().charAt(0))) {
                        textOutputScreen.setText("");
                    }

                    String outputTextNew = textOutputScreen.getText().toString() + buttonClicked.getText().toString();
                    textOutputScreen.setText(outputTextNew);
                }
            }
        }
    }
}
//Todo Add more comments
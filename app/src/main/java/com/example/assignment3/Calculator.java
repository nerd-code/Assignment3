package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean flag;
    double result;
    String compute;
    Button[] digits, operations;
    TextView expression, previous;
    Button clear_screen, clear, equal_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = 0;
        flag = false;
        compute = "";
        digits = new Button[11];
        operations = new Button[4];

        clear = (Button)findViewById(R.id.clear);
        previous = (TextView)findViewById(R.id.previous);
        expression = (TextView)findViewById(R.id.expression);
        clear_screen = (Button)findViewById(R.id.clear_screen);

        digits[0] = (Button)findViewById(R.id.zero);
        digits[1] = (Button)findViewById(R.id.one);
        digits[2] = (Button)findViewById(R.id.two);
        digits[3] = (Button)findViewById(R.id.three);
        digits[4] = (Button)findViewById(R.id.four);
        digits[5] = (Button)findViewById(R.id.five);
        digits[6] = (Button)findViewById(R.id.six);
        digits[7] = (Button)findViewById(R.id.seven);
        digits[8] = (Button)findViewById(R.id.eight);
        digits[9] = (Button)findViewById(R.id.nine);
        digits[10] = (Button)findViewById(R.id.point);

        equal_btn = (Button)findViewById(R.id.equal_btn);
        operations[0] = (Button)findViewById(R.id.add_btn);
        operations[1] = (Button)findViewById(R.id.sub_btn);
        operations[2] = (Button)findViewById(R.id.mul_btn);
        operations[3] = (Button)findViewById(R.id.div_btn);

        View.OnClickListener clear_screen_function = new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                expression.setText("");
                previous.setText("");
                result = 0;
            }
        };

        View.OnClickListener clear_function = new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                expression.setText("");
            }
        };

        View.OnClickListener digits_function = new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                String exp = expression.getText().toString();

                if (flag || exp.contains("Error!") || exp.equals("0")) {
                    expression.setText("");
                    previous.setText("");
                    exp = "";
                }

                flag = false;
                Button btn = (Button)vw;
                String num = btn.getText().toString();

                if (num.equals(".")) {
                    if(!exp.contains(".")) {
                        expression.setText(exp + num);
                    }
                }
                else {
                    expression.setText(exp + num);
                }
            }
        };

        View.OnClickListener operator_function = new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Button bttn = (Button)vw;

                try {
                    if (result != 0)
                        equal_btn.performClick();
                    else
                        result = Double.parseDouble(expression.getText().toString());

                    compute = bttn.getText().toString();
                    previous.setText(result + " " + compute);
                    flag = true;
                }
                catch (NumberFormatException e) {
                    expression.setText("Syntax Error!");
                }
            }
        };

        View.OnClickListener equal_function = new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                try {
                    switch(compute) {
                        case "x":
                            expression.setText(Double.toString(result * Double.parseDouble(expression.getText().toString())));
                            break;

                        case "+":
                            expression.setText(Double.toString(result + Double.parseDouble(expression.getText().toString())));
                            break;

                        case "÷":
                            expression.setText(Double.toString(result / Double.parseDouble(expression.getText().toString())));
                            break;

                        case "-":
                            expression.setText(Double.toString(result - Double.parseDouble(expression.getText().toString())));
                            break;

                        default:
                            break;
                    }

                    if (expression.getText().toString().equals("∞")) {
                        result = 0;
                        expression.setText("Math Error!");
                    }
                    else {
                        result = Double.parseDouble(expression.getText().toString());
                    }
                    previous.setText("");
                }
                catch (ArithmeticException e)
                {
                    expression.setText("Math Error!");
                }
                catch(Exception e)
                {
                    expression.setText("Syntax Error!");
                }
            }
        };

        clear.setOnClickListener(clear_function);
        equal_btn.setOnClickListener(equal_function);
        clear_screen.setOnClickListener(clear_screen_function);

        for (int i = 0; i <= 10; i++) {
            digits[i].setOnClickListener(digits_function);
        }

        for (int i = 0; i < 4; i++) {
            operations[i].setOnClickListener(operator_function);
        }
    }
}
package com.vanibansal.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    String currentInput = "";
    String operator = "";
    double firstValue = 0;
    boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9};

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v -> {
                Button btn = (Button) v;
                if (isOperatorPressed) {
                    currentInput = "";
                    isOperatorPressed = false;
                }
                currentInput += btn.getText().toString();
                tvDisplay.setText(currentInput);
            });
        }

        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if (!currentInput.contains(".")) {
                if (currentInput.isEmpty()) currentInput = "0";
                currentInput += ".";
                tvDisplay.setText(currentInput);
            }
        });

        View.OnClickListener operatorListener = v -> {
            Button btn = (Button) v;
            if (!currentInput.isEmpty()) {
                firstValue = Double.parseDouble(currentInput);
            }
            operator = btn.getText().toString();
            isOperatorPressed = true;
        };

        findViewById(R.id.btnPlus).setOnClickListener(operatorListener);
        findViewById(R.id.btnMinus).setOnClickListener(operatorListener);
        findViewById(R.id.btnMultiply).setOnClickListener(operatorListener);
        findViewById(R.id.btnDivide).setOnClickListener(operatorListener);

        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            if (currentInput.isEmpty() || operator.isEmpty()) return;
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;
            switch (operator) {
                case "+": result = firstValue + secondValue; break;
                case "−": result = firstValue - secondValue; break;
                case "×": result = firstValue * secondValue; break;
                case "÷":
                    if (secondValue == 0) {
                        tvDisplay.setText("Error");
                        currentInput = "";
                        operator = "";
                        return;
                    }
                    result = firstValue / secondValue;
                    break;
            }
            String resultStr = (result == (long) result)
                    ? String.valueOf((long) result)
                    : String.valueOf(result);
            tvDisplay.setText(resultStr);
            currentInput = resultStr;
            operator = "";
        });

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            firstValue = 0;
            isOperatorPressed = false;
            tvDisplay.setText("0");
        });

        findViewById(R.id.btnBackspace).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                tvDisplay.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });

        findViewById(R.id.btnSign).setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !currentInput.equals("0")) {
                if (currentInput.startsWith("-")) {
                    currentInput = currentInput.substring(1);
                } else {
                    currentInput = "-" + currentInput;
                }
                tvDisplay.setText(currentInput);
            }
        });
    }
}
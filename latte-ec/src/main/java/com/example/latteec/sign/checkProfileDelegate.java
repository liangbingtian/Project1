package com.example.latteec.sign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latteec.R;
import com.example.latteec.R2;
import com.example.latteec.main.EcBottomDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liangbingtian on 2018/5/8.
 */

public class checkProfileDelegate extends LatteDelegate {

    @BindView(R2.id.edit_check_question)
    TextInputEditText checkQuestion = null;
    @BindView(R2.id.edit_check_answer)
    TextInputEditText checkAnswer = null;
    @BindView(R2.id.edit_check_email)
    TextInputEditText checkEmail = null;
    @BindView(R2.id.edit_password_new)
    TextInputEditText checkPasswordNew = null;

    @OnClick(R2.id.btn_check_profile)
    void onClickBtn() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://172.20.10.8:8088/userProfile/checkAnswer.do")
                    .params("email", checkEmail.getText().toString())
                    .params("question", checkQuestion.getText().toString())
                    .params("answer", checkAnswer.getText().toString())
                    .params("passwordNew",checkPasswordNew.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
                            if (profileJson != null) {
                                Toast.makeText(getContext(), "验证成功，重置密码成功", Toast.LENGTH_SHORT).show();
                                getSupportDelegate().startWithPop(new SignInDelegate());
                            } else {
                                Toast.makeText(getContext(), "验证失败，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .build()
                    .post();

        }
    }


    private boolean checkForm() {
        final String question = checkQuestion.getText().toString();
        final String answer = checkAnswer.getText().toString();
        final String email = checkEmail.getText().toString();
        final String passwordNew = checkPasswordNew.getText().toString();

        boolean isPass = true;


        if (question.isEmpty()) {
            checkQuestion.setError("问题不能为空");
            isPass = false;
        } else {
            checkQuestion.setError(null);
        }

        if (answer.isEmpty()) {
            checkAnswer.setError("问题答案不能为空");
            isPass = false;
        } else {
            checkQuestion.setError(null);
        }

        if (email.isEmpty()) {
            checkEmail.setError("用户的邮箱不能为空");
            isPass = false;
        } else {
            checkEmail.setError(null);
        }

        if (passwordNew.isEmpty()) {
            checkPasswordNew.setError("用户的邮箱不能为空");
            isPass = false;
        } else {
            checkPasswordNew.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_check_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }
}

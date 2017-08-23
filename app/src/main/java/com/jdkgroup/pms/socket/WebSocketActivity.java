package com.jdkgroup.pms.socket;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jdkgroup.baseclasses.BaseActivity;
import com.jdkgroup.pms.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.appTvWsOutput)
    AppCompatTextView appTvWsOutput;
    @BindView(R.id.appBtnWsSubmit)
    AppCompatButton appBtnWsSubmit;

    private OkHttpClient client;

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("Hello, ");
            webSocket.send("What's up? ");
            webSocket.send(ByteString.decodeHex("12"));
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye! Love You. ");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            hideProgressDialog();
            output("Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            hideProgressDialog();
            output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            hideProgressDialog();
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            hideProgressDialog();
            output("Error : " + t.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws);

        hideSoftKeyboard();

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        toolBarSetFont(toolBar);
        setTitle(getString(R.string.title_socket));

        client = new OkHttpClient();
        appBtnWsSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                start();
            }
        });
    }

    private void start() {
        Request request = new Request.Builder().url("ws://echo.websocket.org").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService();

        //client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appTvWsOutput.setText(appTvWsOutput.getText().toString() + "\n\n" + txt);
            }
        });
    }
}
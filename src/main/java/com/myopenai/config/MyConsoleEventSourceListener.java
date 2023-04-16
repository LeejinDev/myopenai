package com.myopenai.config;

import com.alibaba.fastjson.JSONObject;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.sse.ConsoleEventSourceListener;
import okhttp3.sse.EventSource;

import java.util.concurrent.CountDownLatch;


public class MyConsoleEventSourceListener extends ConsoleEventSourceListener {
    private StringBuilder result = new StringBuilder();
    private CountDownLatch countDownLatch;

    public String getResult() {
        return result.toString();
    }

    public void onEvent(EventSource eventSource, String id, String type, String data) {
        //{
        //	"id": "cmpl-73GFHGm59sqc5vMXnkOlcMqjIK6Cm",
        //	"object": "text_completion",
        //	"created": 1681011787,
        //	"choices": [{
        //		"text": "\u4e3a",
        //		"index": 0,
        //		"logprobs": null,
        //		"finish_reason": null
        //	}],
        //	"model": "text-davinci-003"
        //} {"id":"chatcmpl-73HblD47Hq0uAEgOJtprYkybiIaTn","object":"chat.completion.chunk","created":1681017025,"model":"gpt-3.5-turbo-0301","choices":[{"delta":{"role":"assistant"},"index":0,"finish_reason":null}]}
        // {"id":"chatcmpl-73HYFk9nFjuU7R7NmYGdxK7AUjxZY","object":"chat.completion.chunk","created":1681016807,"model":"gpt-3.5-turbo-0301","choices":[{"delta":{"content":"你"},"index":0,"finish_reason":null}]}

        if (!data.equals("[DONE]")) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            JSONObject delta = jsonObject.getJSONObject("choices").getJSONObject("delta");
            if (delta != null) {
                String str = delta.getString("content");
                result.append(str);
            }
        } else {
            countDownLatch.countDown();
        }
    }

    public MyConsoleEventSourceListener() {
    }

    public MyConsoleEventSourceListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}

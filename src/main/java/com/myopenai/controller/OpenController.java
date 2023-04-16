/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.myopenai.controller;

import com.alibaba.fastjson2.JSON;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.completions.Completion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外接口
 *
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class OpenController {

    @Autowired
    private OpenAiClient openAiClient;

    @Autowired
    private Completion completion;

    @Autowired
    private ChatCompletion chatCompletion;

    /**
     * {"model":"gpt-3.5-turbo","messages":[{"role":"user","content":"1"}]}
     * {"choices":[{"finish_reason":"stop","index":0,"text":"你好啊\n\n你好！"}],"created":1681624896,"id":"cmpl-75pk8JA3h4GHA4I8scC9DF7flo5Sm","model":"text-davinci-003","object":"text_completion","usage":{"completion_tokens":9,"prompt_tokens":7,"total_tokens":16}}
     *
     * @param request
     * @return
     */
    @PostMapping("/chat/completions")
    public String creditQuery(@RequestBody ChatCompletion completion) {
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(completion);
        String content = chatCompletionResponse.getChoices().get(0).getMessage().getContent();

        System.out.println(JSON.toJSONString(chatCompletionResponse));
        return JSON.toJSONString(chatCompletionResponse);
    }
}

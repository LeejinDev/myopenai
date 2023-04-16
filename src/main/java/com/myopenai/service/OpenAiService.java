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
package com.myopenai.service;

import com.myopenai.bean.dto.OpenAiRequest;
import com.myopenai.bean.dto.OpenAiResult;
import com.myopenai.websocket.WebSocketServer;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.completions.Completion;
import com.unfbx.chatgpt.entity.images.ImageResponse;
import com.unfbx.chatgpt.entity.images.Item;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @description OpenAi接口实现类
 * @date 2023-02-15
 **/
@Slf4j
@Service
@AllArgsConstructor
public class OpenAiService {
    @Autowired
    private OpenAiClient openAiClient;

    @Autowired
    private Completion completion;

    @Autowired
    private OpenAiStreamClient openAiStreamClient;

    @Autowired
    private ChatCompletion chatCompletion;

    public OpenAiResult creditQuery(OpenAiRequest openAiDto) {
        return null;
    }

    public void communicate(OpenAiRequest openAiDto, WebSocketServer webSocketServer) throws Exception {
        try {
            switch (openAiDto.getId()) {
                // 文本问答
                case 1:
                    textQuiz(openAiDto, webSocketServer);
                    break;
                // 图片生成
                case 2:
                    imageQuiz(openAiDto, webSocketServer);
                    break;
                // 默认
                default:
                    webSocketServer.sendMessage("出错了：未知的请求类型");
            }
        } catch (Exception e) {
            e.printStackTrace();
            webSocketServer.sendMessage("出错了：" + e.getMessage());
        }
    }

    /**
     * 文本问答
     *
     * @param openAiRequest   请求参数
     * @param webSocketServer /
     */
    private void textQuiz(OpenAiRequest openAiRequest, WebSocketServer webSocketServer) throws Exception {
        String question = openAiRequest.getText();
        chatCompletion.setMessages(Arrays.asList(Message.builder().role(Message.Role.USER).content(question).build()));
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        String content = chatCompletionResponse.getChoices().get(0).getMessage().getContent();
        webSocketServer.sendMessage(content);
    }

    /**
     * 图片请求
     *
     * @param openAiRequest   请求参数
     * @param webSocketServer /
     */
    private void imageQuiz(OpenAiRequest openAiRequest, WebSocketServer webSocketServer) throws IOException {
        String question = openAiRequest.getText();
        ImageResponse imageResponse = openAiClient.genImages(question);
        List<Item> data = imageResponse.getData();
        String join = String.join("\n", data.stream().map(Item::getUrl).collect(Collectors.toList()));
        webSocketServer.sendMessage(join);
    }

}

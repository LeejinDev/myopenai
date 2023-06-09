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

import com.myopenai.service.OpenAiService;
import com.myopenai.bean.dto.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/openai")
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping
    public ResponseEntity<Object> creditQuery(@RequestBody OpenAiRequest openAiDto) {
        return new ResponseEntity<>(openAiService.creditQuery(openAiDto), HttpStatus.OK);
    }
}

/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.domain.Word;
import party.threebody.skean.dict.service.WordSearchEngine;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/words")
public class WordController extends SinglePKUriVarCrudRestController<Word, String> {
    @Autowired WordDao wordDao;
    @Autowired WordService wordService;

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Word, String> builder) {
        builder.fromSinglePKCrudDAO(wordDao);
    }

    @GetMapping("--disabled--")
    @Override
    public ResponseEntity<Word> httpReadOne(@PathVariable String text) {
        return null;
    }

    @GetMapping("/{text}")
    public ResponseEntity<Word> httpReadOne(@PathVariable String text, @RequestParam(required = false) String mode) {
        Word word;
        if (mode != null && "basic".equals(mode)) {
            word = wordService.getWord(text);
        } else {
            word = wordService.getWordWithRels(text);
        }
        return ResponseEntity.ok().body(word);
    }



}

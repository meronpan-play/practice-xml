package com.example.batch.xml;

import com.example.batch.exception.BatchException;
import com.example.batch.model.Item;
import com.example.batch.model.ItemListWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class XmlItemReader {

    private final XmlMapper xmlMapper;

    public XmlItemReader() {
        this.xmlMapper = new XmlMapper();

        // LocalDate 用モジュール
        xmlMapper.registerModule(new JavaTimeModule());

        // 日付を timestamp としてではなく文字列として扱う
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 未知フィールドがあっても無視
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public List<Item> read(Path xmlFilePath) throws BatchException {

        if (!Files.exists(xmlFilePath)) {
            throw new BatchException("XMLファイルが存在しません: " + xmlFilePath);
        }

        try {
            // XML を Javaクラスに変換
            ItemListWrapper wrapper =
                    xmlMapper.readValue(xmlFilePath.toFile(), ItemListWrapper.class);

            if (wrapper.getItems() == null) {
                return Collections.emptyList();
            }

            return wrapper.getItems();

        } catch (IOException e) {
            throw new BatchException("XMLの読み込みに失敗しました: " + xmlFilePath, e);
        }
    }
}

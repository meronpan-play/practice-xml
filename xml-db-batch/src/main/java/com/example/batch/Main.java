package com.example.batch;

import com.example.batch.dao.ItemDao;
import com.example.batch.exception.BatchException;
import com.example.batch.model.Item;
import com.example.batch.xml.XmlItemReader;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --- 1. 引数チェック ---
        if (args.length != 1) {
            System.err.println("使い方: java -jar xml-db-batch.jar <XMLファイルパス>");
            System.exit(1);
        }

        Path xmlPath = Path.of(args[0]);

        // --- 2. XML 読み込み ---
        XmlItemReader reader = new XmlItemReader();
        List<Item> items;

        try {
            items = reader.read(xmlPath);
        } catch (BatchException e) {
            System.err.println("[ERROR] XML読み込みに失敗: " + e.getMessage());
            e.printStackTrace();
            System.exit(2);
            return;
        }

        // --- 3. DB INSERT ---
        ItemDao dao = new ItemDao();
        int success = 0;
        int failure = 0;

        for (Item item : items) {
            try {
                dao.insert(item);
                success++;
            } catch (Exception e) {
                System.err.println("[ERROR] DB登録に失敗 (id = " + item.getId() + "): " + e.getMessage());
                e.printStackTrace();
                failure++;
            }
        }

        // --- 4. 結果表示 ---
        System.out.println("=== バッチ処理完了 ===");
        System.out.println("成功件数: " + success);
        System.out.println("失敗件数: " + failure);

        // --- 5. 終了コード ---
        if (failure > 0) {
            System.exit(3);
        } else {
            System.exit(0);
        }
    }
}

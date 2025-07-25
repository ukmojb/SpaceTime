package com.wdcftgg.spacetime.gui.book.json;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.BookHelper;
import com.wdcftgg.spacetime.gui.book.STBookGui;
import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.gui.book.data.ChapterData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.element.BookElement;
import com.wdcftgg.spacetime.gui.book.element.ElementText;
import com.wdcftgg.spacetime.gui.book.element.data.TextData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static net.minecraft.util.datafix.fixes.SignStrictJSON.GSON_INSTANCE;

public class JsonFileLoader {

    public static Map<String, List<ChapterData>> bookDataListMap = new HashMap<>();
    public static Map<String, List<PageData>> chapterDataListMap = new HashMap<>();
    public static List<BookData> bookDataList = new ArrayList<>();

    public static JsonObject fromFile(File path) throws IOException, JsonParseException {
        BufferedReader reader = Files.newBufferedReader(path.toPath());
        return GSON_INSTANCE.fromJson(reader, JsonObject.class);
    }



    private static List<ResourceLocation> findAllJsonsRecursive(String basePath) {
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        List<ResourceLocation> results = new ArrayList<>();

        // 1. 首先获取所有可能的资源域（通常是modid）
        for (String domain : manager.getResourceDomains()) {
            if (!domain.equals(SpaceTime.MODID)) continue; // 只查找当前mod的资源

            // 2. 使用"dummy"技巧获取目录下的资源
            try {
                // 注意：这里用"dummy"作为文件名，但实际会返回目录下所有文件
                Collection<ResourceLocation> allResources = manager.getResourceDomains().stream()
                        .flatMap(d -> {
                            try {
                                return manager.getAllResources(
                                        new ResourceLocation(d, basePath + "/dummy.json")
                                ).stream();
                            } catch (IOException e) {
                                return Stream.empty();
                            }
                        })
                        .map(IResource::getResourceLocation)
                        .collect(Collectors.toList());

                // 3. 递归检查子目录
                for (ResourceLocation rl : allResources) {
                    String path = rl.getPath();
                    if (path.endsWith(".json")) {
                        results.add(rl);
                    }
                    // 如果是目录，递归查找（注意：1.12.2需要特殊处理）
                    else if (!path.contains(".")) {
                        results.addAll(findAllJsonsRecursive( path));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private static List<ResourceLocation> getAllBookJsonsUniversal() {
        List<ResourceLocation> jsonFiles = new ArrayList<>();
        String targetPath = "assets/spacetime/books/";

        // 获取当前mod容器
        ModContainer mod = Loader.instance().activeModContainer();
        if (mod == null) return jsonFiles;

        // 获取mod文件（JAR或目录）
        File source = mod.getSource();
        if (source == null) return jsonFiles;

        if (source.isDirectory()) {
            // 开发环境：直接读取目录
            File booksDir = new File(source, targetPath);
            if (booksDir.exists() && booksDir.isDirectory()) {
                addJsonFilesFromDirectory(booksDir, targetPath, jsonFiles);
            }
        } else {
            // 生产环境：读取JAR文件
            try (ZipFile zip = new ZipFile(source)) {
                Enumeration<? extends ZipEntry> entries = zip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.startsWith(targetPath) && name.endsWith(".json")) {
                        jsonFiles.add(new ResourceLocation(
                                "spacetime",
                                name.substring("assets/spacetime/".length())
                        ));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonFiles;
    }

    // 递归添加目录中的JSON文件
    private static void addJsonFilesFromDirectory(File dir, String basePath, List<ResourceLocation> result) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                addJsonFilesFromDirectory(file, basePath, result);
            } else if (file.getName().endsWith(".json")) {
                String relativePath = file.getAbsolutePath()
                        .substring(new File(basePath).getAbsolutePath().length() + 1)
                        .replace(File.separatorChar, '/');
                result.add(new ResourceLocation("spacetime", relativePath));
            }
        }
    }

//    public static void loadAllJsons() {
//        try {
//            // 获取所有 JSON 文件
//            List<ResourceLocation> jsonFiles = getAllBookJsonsUniversal();
//
//            for (ResourceLocation res : jsonFiles) {
//                try (IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(res);
//                     BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
//
//                    // 解析 JSON
//                    JsonParser parser = new JsonParser();
//                    JsonObject json = parser.parse(reader).getAsJsonObject();
//
////                    System.out.println(json.getAsString() + "aaaaa");
//                    // 读取第一个层级的 type 字段
//                    jsonBookInit(json);
//                    jsonBookInitFinish();
//                } catch (Exception e) {
//                    System.err.println("Error reading JSON file: " + res);
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void loadAllJsons(String folderPath) {
        List<ResourceLocation> allJsons = getAllBookJsonsUniversal();
//        List<ResourceLocation> allJsons = findAllJsonsRecursive("assets/" + SpaceTime.MODID + "/" + folderPath);

        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        for (ResourceLocation rl : allJsons) {
            try (IResource res = manager.getResource(rl);
                 InputStream is = res.getInputStream()) {

                JsonObject json = new JsonParser().parse(
                        new InputStreamReader(is, StandardCharsets.UTF_8)
                ).getAsJsonObject();

                System.out.println("Loaded JSON: " + rl);
                jsonBookInit(json);
                jsonBookInitFinish();

            } catch (Exception e) {
                System.err.println("Failed to load: " + rl);
                e.printStackTrace();
            }
        }
    }

    private static void jsonBookInit(JsonObject json) {
        try {
            String type = json.get("type").getAsString();
            int mode = 0;

            switch (type) {
                case "page":
                    mode = 1;
                    break;
                case "chapter":
                    mode = 2;
                    break;
                case "book":
                    mode = 3;
                    break;
                case "communication":
                    mode = 4;
                    break;
            }

            //page
            if (mode == 1) {
                String title = json.get("title").getAsString();
                String parentChapter = json.get("parent").getAsString();
                PageData pageData = new PageData(title);

                List<BookElement> elements = new ArrayList<>();

                for (JsonElement element : json.getAsJsonArray("elements")) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        String elementType = "";
                        if (jsonObject.has("type")) elementType = jsonObject.get("type").getAsString();

                        int elementMode = 0;

                        switch (elementType) {
                            case "text":
                                elementMode = 1;
                                break;
                        }

                        if (elementMode == 1) {
                            int x = jsonObject.get("x").getAsInt();
                            int y = jsonObject.get("y").getAsInt();
                            int width = jsonObject.get("width").getAsInt();
                            int height = jsonObject.get("height").getAsInt();
                            String text = jsonObject.get("text").getAsString();

                            ElementText elementText = new ElementText(x, y, width, height, text);
                            elements.add(elementText);
                        }
                    }
                }

                pageData.initElements(elements);

                if (chapterDataListMap.containsKey(parentChapter)) {
                    List<PageData> list = chapterDataListMap.get(parentChapter);
                    list.add(pageData);
                } else {
                    chapterDataListMap.put(parentChapter, Collections.singletonList(pageData));
                }
            }

            //chapter
            if (mode == 2) {
                String id = json.get("id").getAsString();
                String parentBook = json.get("parent").getAsString();
                ChapterData chapterData = new ChapterData(id);

                if (bookDataListMap.containsKey(parentBook)) {
                    List<ChapterData> list = bookDataListMap.get(parentBook);
                    list.add(chapterData);
                } else {
                    bookDataListMap.put(parentBook, Collections.singletonList(chapterData));
                }
            }

            //book
            if (mode == 3) {
                String id = json.get("id").getAsString();
                BookData bookData = new BookData(id);

                bookDataList.add(bookData);
            }

            //communication
            if (mode == 4) {
                int maxHeight = STBookGui.PAGE_HEIGHT;
                String showText = "";

                for (JsonElement element : json.getAsJsonArray("communications")) {
                    if (element.isJsonObject()) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        String person = "";
                        Map<Integer, String> optionMap = new HashMap<>();
                        String text = "";
                        if (jsonObject.has("person")) person = jsonObject.get("person").getAsString();

                        for (JsonElement element1 : json.getAsJsonArray("options")) {
                            if (element1.isJsonObject()) {
                                JsonObject jsonObject1 = element1.getAsJsonObject();

                                String option = jsonObject1.get("option").getAsString();
                                int optionMode = jsonObject1.get("optionMode").getAsInt();

                                optionMap.put(optionMode, option);

                            }
                        }

                        text = jsonObject.get("text").getAsString();



                    }
                }
            }
        } catch (Exception e) {
            throw new JsonSyntaxException("Failed to loaded JSON: " + json, e);
        }
    }

    private static void jsonBookInitFinish() {

        //book
        SpaceTime.Log("Loading json bookData");
        BookHelper.books.addAll(bookDataList);

        //chapter
        SpaceTime.Log("Loading json chapterData");
        for (Map.Entry <String, List<ChapterData>> entry : bookDataListMap.entrySet()) {
            BookData bookData = BookHelper.getBookFromId(entry.getKey());

            if (bookData != null) {
                bookData.initChapters(entry.getValue());
            } else {
                SpaceTime.LogWarning("There is no \"" + entry.getKey() + "\" bookData");
            }
        }

        //page
        SpaceTime.Log("Loading json pageData");
        for (Map.Entry <String, List<PageData>> entry : chapterDataListMap.entrySet()) {
            BookData bookData = BookHelper.getBookFromChapterId(entry.getKey());

            if (bookData != null) {
                ChapterData chapterData = BookHelper.getChapter(entry.getKey(), bookData);
                if (chapterData != null) {
                    for (PageData pageData : entry.getValue()) {
                        System.out.println(pageData.getTitle());

                        for (BookElement bookElement : pageData.getBookElements()) {
                            if (bookElement instanceof ElementText) {
                                ElementText elementText = (ElementText) bookElement;
                                System.out.println(pageData.getTitle() + "--" + elementText.text[0].text);
                            }
                        }
                    }
                    chapterData.initPages(entry.getValue());
                } else {
                    SpaceTime.LogWarning("There is no \"" + entry.getKey() + "\" chapterData");
                }
            }
        }
    }
}

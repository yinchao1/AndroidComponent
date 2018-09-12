package com.core.network.builder;

import com.core.network.request.PostFormRequest;
import com.core.network.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/2.
 */

public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    private List<FileInput> files = new ArrayList<>();

    public PostFormBuilder files(String key, Map<String, File> files){
        for(String filename : files.keySet()){
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file){
        files.add(new FileInput(name, filename, file));
        return this;
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if(this.params == null){
            this.params = new LinkedHashMap<>();
        }
        this.params.put(key, val);
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFormRequest(url, tag, params, headers, files,id).build();
    }

    public static class FileInput{
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file){
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
}

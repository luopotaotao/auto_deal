package com.tt.util;

import org.apache.commons.httpclient.NameValuePair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by tt on 2016/12/23.
 */
public class KeyValMap {
    private Map<String,String> map = new HashMap<>();

    public KeyValMap add(String key,String val){
        map.put(key,val);
        return this;
    }


    public String serialize(String eq,String s){
        if(map.isEmpty()){
            return "";
        }else{
            StringBuilder sb = new StringBuilder();
            map.entrySet().forEach(item->sb.append(item.getKey()).append(eq).append(item.getValue()).append(s));
            return sb.toString();
        }
    }
    public String serialize(){
        return serialize("=",";");
    }

    public boolean isEmpty(){
        return this.map.isEmpty();
    }

    public int size(){
        return this.map.size();
    }

    public NameValuePair[] toNameValuePairArray(){
        NameValuePair[] ret=null;
        if(isEmpty()){
            ret = new NameValuePair[0];
        }else{
            ret = new NameValuePair[size()];
            int i=0;
            for(Map.Entry<String,String> entry:map.entrySet()){
                ret[i++]=new NameValuePair(entry.getKey(),entry.getValue().toString());
            }
        }
        return ret;
    }

    public void forEach(BiConsumer consumer){
        if(isEmpty()){
            return;
        }
        map.forEach(consumer);
    }

    public Map<String, String> getMap() {
        return map;
    }
}
